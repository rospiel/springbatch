package com.study.studyapplicationbatch.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@RequiredArgsConstructor
@Configuration
@EnableBatchProcessing
public class BatchConfig3 {

    private final JobBuilderFactory jobBuilderFactory;

    @Bean
    public Job printHello(Step printHelloStep) {
        return jobBuilderFactory
                .get("printHello")
                .start(printHelloStep)
                /* identificador do job incrementado, senão transmitido só é permitido rodar uma única vez */
                .incrementer(new RunIdIncrementer())
                .build();
    }

    private Flow stepsParalelos(Step fase1, Step fase2) {
        Flow flow1 = new FlowBuilder<Flow>("flow1")
                .start(fase1)
                .build();

        return new FlowBuilder<Flow>("flow2")
                .start(fase2)
                .split(new SimpleAsyncTaskExecutor())
                .add(flow1)
                .build();
    }
}
