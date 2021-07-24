package com.study.studyapplicationbatch.processors.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WriterFileFlatProcessorConfig {

    @Bean
    public ItemWriter fileFlatProcessorWriter() {
        return itens -> itens.forEach(System.out::println);
    }

}
