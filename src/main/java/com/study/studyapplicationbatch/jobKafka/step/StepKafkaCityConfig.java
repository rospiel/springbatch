package com.study.studyapplicationbatch.jobKafka.step;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.kafka.KafkaItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class StepKafkaCityConfig {

    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step stepKafkaCity(KafkaItemReader kafkaCityItemReader, ItemWriter<String> kafkaCityWriter) {
        return stepBuilderFactory
                .get("stepKafkaCity")
                .<String, String>chunk(1)
                .reader(kafkaCityItemReader)
                .writer(kafkaCityWriter)
                .build();
    }
}
