package com.john.SpringBatchHelloWorld.config;

import com.john.SpringBatchHelloWorld.listener.HelloWorldJobExecutionListener;
import com.john.SpringBatchHelloWorld.listener.HelloWorldStepExecutionListener;
import com.john.SpringBatchHelloWorld.model.Product;
import com.john.SpringBatchHelloWorld.processor.InMemoryProcessor;
import com.john.SpringBatchHelloWorld.reader.InMemoryReader;
import com.john.SpringBatchHelloWorld.writer.ConsoleItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@EnableBatchProcessing
@Configuration
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private HelloWorldJobExecutionListener helloWorldJobListener;

    @Autowired
    private HelloWorldStepExecutionListener helloWorldStepExecutionListener;

    @Autowired
    private InMemoryProcessor inMemoryProcessor;

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .listener(helloWorldStepExecutionListener)
                .tasklet(helloWorldTasklet()).build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2").<Integer, Integer>chunk(3)
                // .reader(reader())
               // .reader(flatFileItemReader(null))
                .reader(xmlItemReader(null))
                // .processor(inMemoryProcessor)
                .writer(new ConsoleItemWriter()).build();
    }


    @Bean
    public ItemReader<Integer> reader() {
        return new InMemoryReader();
    }

    @Bean
    @StepScope
    public FlatFileItemReader flatFileItemReader(
            @Value("#{jobParameters['fileinput']}") FileSystemResource inputFile
    ) {
        FlatFileItemReader flatFileItemReader = new FlatFileItemReader();

        //step1 Tell where is the resource to be read

        // flatFileItemReader.setResource(new FileSystemResource("input/product.csv"));
        flatFileItemReader.setResource(inputFile);

        //Create Line mapper
        flatFileItemReader.setLineMapper(
                new DefaultLineMapper<Product>() {
                    {
                        setLineTokenizer(new DelimitedLineTokenizer() {
                            {
                                setNames(new String[]{"productId", "productName", "productDesc", "price", "unit"});

                                setDelimiter("|");
                            }
                        });

                        setFieldSetMapper(new BeanWrapperFieldSetMapper<Product>() {
                            {
                                setTargetType(Product.class);
                            }
                        });
                    }

                });

        //to skip the first line of header
        flatFileItemReader.setLinesToSkip(1);

        return flatFileItemReader;
    }

    @Bean
    @StepScope
    public StaxEventItemReader xmlItemReader(@Value("#{jobParameters['fileinput']}") FileSystemResource inputFile) {
        StaxEventItemReader reader = new StaxEventItemReader();

        //where to read the xml file
        reader.setResource(inputFile);
//need to let reader know which tag denotes the root element
        reader.setFragmentRootElementName("product");

        //tell reader how to parse xml and which objects need to be mapped
        reader.setUnmarshaller(new Jaxb2Marshaller(){
            {
                setClassesToBeBound(Product.class);
            }
        });
        return reader;
    }

    private Tasklet helloWorldTasklet() {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println("Hello World!!!");
                return RepeatStatus.FINISHED;
            }
        };
    }

    @Bean
    public Job helloWorldJob() {
        return jobBuilderFactory
                .get("helloWorldJob")
                .incrementer(new RunIdIncrementer())
                .listener(helloWorldJobListener)
                .start(step1())
                .next(step2())
                .build();
    }
}
