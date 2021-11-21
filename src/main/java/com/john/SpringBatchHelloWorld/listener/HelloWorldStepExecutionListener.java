package com.john.SpringBatchHelloWorld.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldStepExecutionListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("Before Step : "+stepExecution.getJobExecution().getExecutionContext().toString());
        System.out.println("Inside Step getting Job parameters : "+stepExecution.getJobExecution().getJobParameters());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("After Step : "+stepExecution.getJobExecution().getExecutionContext().toString());
        return null;
    }
}
