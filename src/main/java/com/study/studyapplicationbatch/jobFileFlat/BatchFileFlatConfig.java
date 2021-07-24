package com.study.studyapplicationbatch.jobFileFlat;

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
public class BatchFileFlatConfig {

    private final JobBuilderFactory jobBuilderFactory;

    @Bean
    public Job fileFlatJob(Step stepFileFlat) {
        return jobBuilderFactory
                .get("fileFlatJob")
                .start(stepFileFlat)
                .incrementer(new RunIdIncrementer())
                .build();
    }

}
