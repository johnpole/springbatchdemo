package com.john.SpringBatchHelloWorld.reader;

import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.support.AbstractItemStreamItemReader;

import java.util.Arrays;
import java.util.List;

public class InMemoryReader extends AbstractItemStreamItemReader {

    Integer[] intArray = {1,2,3,4,5,6,7,8,9,0};

    List<Integer> myList= Arrays.asList(intArray);

    int index =0;

    @Override
    public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        Integer next = null;

        if(index < myList.size()){
            next = myList.get(index);
            index++;
        } else {
            index =0;
        }

        return next;
    }
}
