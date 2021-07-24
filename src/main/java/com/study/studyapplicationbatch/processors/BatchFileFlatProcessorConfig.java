package com.study.studyapplicationbatch.processors;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@EnableBatchProcessing
@Configuration
public class BatchFileFlatProcessorConfig {

    private final JobBuilderFactory jobBuilderFactory;

    @Bean
    public Job fileFlatProcessorJob(Step stepFileFlatProcessor) {
        return jobBuilderFactory
                .get("fileFlatProcessorJob")
                .start(stepFileFlatProcessor)
                .incrementer(new RunIdIncrementer())
                .build();
    }
}
