package com.study.studyapplicationbatch.processorsClassifier.reader;

import com.study.studyapplicationbatch.processorsClassifier.domain.AddressProcessorClassifier;
import com.study.studyapplicationbatch.processorsClassifier.domain.ClientProcessorClassifier;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ReaderFileFlatProcessorClassifierConfig {

    private static final String[] COLUMN_NAME_CLIENT = { "firstName", "lastName", "age", "email" };
    private static final String[] COLUMN_NAME_ADDRESS = { "street", "number", "city", "state", "postalCode" };

    @Bean
    public FlatFileItemReader fileFlatProcessorClassifierReader() throws IOException {
        return new FlatFileItemReaderBuilder()
                .name("fileFlatProcessorClassifierReader")
                .resource(new ClassPathResource("/fileprocessor/clientsDelimited.txt", this.getClass().getClassLoader()))
                .lineMapper(clientAddressLineMapper())
                .build();
    }

    private PatternMatchingCompositeLineMapper clientAddressLineMapper() {
        PatternMatchingCompositeLineMapper lineMapper = new PatternMatchingCompositeLineMapper();
        lineMapper.setTokenizers(buildTokenizers());
        lineMapper.setFieldSetMappers(buildFieldSetMappers());
        return lineMapper;
    }

    private Map<String, FieldSetMapper> buildFieldSetMappers() {
        return new HashMap<String, FieldSetMapper>() {
            {
                put("client*", buildFieldSetMapper(ClientProcessorClassifier.class));
                put("address*", buildFieldSetMapper(AddressProcessorClassifier.class));
            }
        };
    }

    private FieldSetMapper buildFieldSetMapper(Class entity) {
        BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper();
        fieldSetMapper.setTargetType(entity);
        return fieldSetMapper;
    }

    private Map<String, LineTokenizer> buildTokenizers() {
        return new HashMap<String, LineTokenizer>() {
            {
                put("client*", buildClientLineTokenizer());
                put("address*", buildAddressLineTokenizer());
            }
        };
    }

    private LineTokenizer buildClientLineTokenizer() {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer("#");
        lineTokenizer.setNames(COLUMN_NAME_CLIENT);
        lineTokenizer.setIncludedFields(1, 2, 3, 4);
        return lineTokenizer;
    }

    private LineTokenizer buildAddressLineTokenizer() {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer("#");
        lineTokenizer.setNames(COLUMN_NAME_ADDRESS);
        lineTokenizer.setIncludedFields(1, 2, 3, 4, 5);
        return lineTokenizer;
    }
}
