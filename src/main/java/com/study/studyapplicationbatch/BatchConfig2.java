package com.study.studyapplicationbatch;

import static java.lang.String.format;
import static java.util.Arrays.asList;

import lombok.RequiredArgsConstructor;
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
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.function.FunctionItemProcessor;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
// @EnableBatchProcessing
public class BatchConfig2 {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    //@Bean
    public Job printEvenAndOdd() {
        return jobBuilderFactory
                .get("printHello")
                .start(printEvenAndOddStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }

    public Step printEvenAndOddStep() {
        return stepBuilderFactory
                .get("printEvenAndOddStep")
                .<Integer, String>chunk(6)
                .reader(untilSix())
                .processor(evenAndOddProcessor())
                .writer(printWriter())
                .build();
    }

    private ItemWriter<String> printWriter() {
        return itens -> itens.forEach(System.out::println);
    }

    private FunctionItemProcessor<Integer, String> evenAndOddProcessor() {
        return new FunctionItemProcessor<>(number -> number % 2 == 0 ?
                format("%s is even", number) : format("%s is odd", number));
    }

    private IteratorItemReader<Integer> untilSix() {
        return new IteratorItemReader<Integer>(asList(1, 2, 3, 4, 5, 6).iterator());
    }
}
