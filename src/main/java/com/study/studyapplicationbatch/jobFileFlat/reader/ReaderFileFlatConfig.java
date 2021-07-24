package com.study.studyapplicationbatch.jobFileFlat.reader;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class ReaderFileFlatConfig {

    @Bean
    public FlatFileItemReader fileFlatReader(LineMapper clientAddressLineMapper) throws IOException {
        return new FlatFileItemReaderBuilder()
                .name("fileFlatReader")
               // .resource(new ClassPathResource("/filesprocess/clientsDelimited.txt", this.getClass().getClassLoader()))
                .lineMapper(clientAddressLineMapper)
                .build();
    }
}
