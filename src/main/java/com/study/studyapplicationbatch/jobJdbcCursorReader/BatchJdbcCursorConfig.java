package com.study.studyapplicationbatch.jobJdbcCursorReader;

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
public class BatchJdbcCursorConfig {

    private final JobBuilderFactory jobBuilderFactory;

    @Bean
    public Job jdbcCursorJob(Step stepJdbcCursor) {
        return jobBuilderFactory
                .get("jdbcCursorJob")
                .start(stepJdbcCursor)
                .incrementer(new RunIdIncrementer())
                .build();
    }
}
