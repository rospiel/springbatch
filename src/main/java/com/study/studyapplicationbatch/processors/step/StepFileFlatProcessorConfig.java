package com.study.studyapplicationbatch.processors.step;

import com.study.studyapplicationbatch.processors.domain.ClientProcessor;
import com.study.studyapplicationbatch.processors.reader.ReaderProcessorClientAddress;
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
public class StepFileFlatProcessorConfig {

    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step stepFileFlatProcessor(FlatFileItemReader fileFlatProcessorReader, ItemWriter<ClientProcessor> fileFlatProcessorWriter,
                             ItemProcessor<ClientProcessor, ClientProcessor> validationProcessor) {
        return stepBuilderFactory
                .get("stepFileFlatProcessor")
                .<ClientProcessor, ClientProcessor>chunk(1)
                .reader(new ReaderProcessorClientAddress(fileFlatProcessorReader))
                .processor(validationProcessor)
                .writer(fileFlatProcessorWriter)
                .build();
    }


}
