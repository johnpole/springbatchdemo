package com.john.SpringBatchHelloWorld.writer;

import org.springframework.batch.item.support.AbstractItemStreamItemWriter;

import javax.sound.midi.Soundbank;
import java.util.List;

public class ConsoleItemWriter extends AbstractItemStreamItemWriter {
    @Override
    public void write(List items) throws Exception {
        items.stream().forEach(System.out::println);

        System.out.println("************* Writing Each Chunk ************");
    }
}
