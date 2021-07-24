package com.study.studyapplicationbatch.processorsClassifier.step;

import com.study.studyapplicationbatch.processors.reader.ReaderProcessorClientAddress;
import com.study.studyapplicationbatch.processorsClassifier.domain.ClientProcessorClassifier;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class StepFileFlatProcessorClassifierConfig {

    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step stepFileFlatProcessorClassifier(FlatFileItemReader fileFlatProcessorClassifierReader, ItemWriter<ClientProcessorClassifier> fileFlatProcessorClassifierWriter,
                                      ItemProcessor validationProcessorClassifier) {
        return stepBuilderFactory
                .get("stepFileFlatProcessorClassifier")
                .<ClientProcessorClassifier, ClientProcessorClassifier>chunk(1)
                .reader(fileFlatProcessorClassifierReader)
                .processor(validationProcessorClassifier)
                .writer(fileFlatProcessorClassifierWriter)
                .build();
    }
}
