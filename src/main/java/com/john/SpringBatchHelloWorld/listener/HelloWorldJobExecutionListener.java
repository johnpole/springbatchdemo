package com.john.SpringBatchHelloWorld.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldJobExecutionListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("Before Job starting : Job name is : "+jobExecution.getJobInstance().getJobName());
        System.out.println("Before Job Staring : JobExection Context is : "+ jobExecution.getExecutionContext().toString());
        jobExecution.getExecutionContext().put("Name","John Pole");

        System.out.println("Before Job Staring : JobExection Context is : "+ jobExecution.getExecutionContext().toString());

    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        System.out.println("After Job started : Job Executiion Context : " +jobExecution.getExecutionContext().toString());

    }
}
