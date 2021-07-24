package com.study.studyapplicationbatch.processorsClassifier.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WriterFileFlatProcessorClassifierConfig {

    @Bean
    public ItemWriter fileFlatProcessorClassifierWriter() {
        return itens -> itens.forEach(System.out::println);
    }
}
