package com.study.studyapplicationbatch.jobKafka.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaCityWriterConfig {

    @Bean
    public ItemWriter kafkaCityWriter() {
        return messages -> messages.forEach(System.out::println);
    }
}
