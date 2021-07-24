package com.study.studyapplicationbatch.jobFileFlat.step;

import static java.util.Collections.EMPTY_LIST;

import com.study.studyapplicationbatch.jobFileFlat.domain.Client;
import com.study.studyapplicationbatch.jobFileFlat.dto.FooterDto;
import com.study.studyapplicationbatch.jobFileFlat.reader.ReaderClientAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.MultiResourceItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@RequiredArgsConstructor
@Configuration
public class StepFileFlatConfig {

    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step stepFileFlat(FlatFileItemReader fileFlatReader, MultiResourceItemWriter<Client> multiResourceClientItemWriter,
                             MultiResourceItemReader<Client> multipleFilesClientAddressReader,
                             FooterDto footer) {
        return stepBuilderFactory
                .get("stepFileFlat")
                /* de quando em quando terá commit, neste caso de 1 em 1 */
                /* entra e sai a entidade client */
                .<Client, Client>chunk(1)

                .reader(multipleFilesClientAddressReader)
                .writer(multiResourceClientItemWriter)
                .listener(footer)
                .build();
    }
}
