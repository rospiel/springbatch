package com.study.studyapplicationbatch.jobFileFlat.reader;

import com.study.studyapplicationbatch.jobFileFlat.domain.Client;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.validation.BindException;

import java.io.IOException;

@Configuration
public class ReaderMultipleFilesClientAddress {

    @Value("classpath:/filesprocess/*")
    private Resource[] resources;

    @Bean
    public MultiResourceItemReader multipleFilesClientAddressReader(FlatFileItemReader fileFlatReader)
            throws IOException {
        return new MultiResourceItemReaderBuilder<>()
                .name("multipleFilesClientAddressReader")
                .resources(resources)
                .delegate(new ReaderClientAddress(fileFlatReader))
                .build();
    }

    private FieldSetMapper<Client> fieldSetClientMapper() {
        return new FieldSetMapper<Client>() {
            @Override
            public Client mapFieldSet(FieldSet fieldSet) throws BindException {
                Client client = new Client();
                client.setAge(fieldSet.readInt("age"));
                return client;
            }
        };
    }

}
