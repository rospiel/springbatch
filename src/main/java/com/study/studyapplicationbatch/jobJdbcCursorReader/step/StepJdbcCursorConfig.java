package com.study.studyapplicationbatch.jobJdbcCursorReader.step;

import com.study.studyapplicationbatch.jobJdbcCursorReader.domain.CityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@RequiredArgsConstructor
@Configuration
public class StepJdbcCursorConfig {

    private final StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("transactionManagerSpringDataSourceApplication")
    private PlatformTransactionManager transactionManagerApp;

    @Bean
    public Step stepJdbcCursor(JdbcCursorItemReader jdbcCursorSkipReader,
                               ClassifierCompositeItemWriter<CityDto> classifierCompositeItemCityWriter,
                               @Qualifier("fileFlatWriteFile") FlatFileItemWriter<CityDto> fileFlatWriteFile,
                               @Qualifier("fileFlatWriteFileOdd") FlatFileItemWriter<CityDto> fileFlatWriteFileOdd) {
    //public Step stepJdbcCursor(JdbcPagingItemReader jdbcPagingReader, ItemWriter<CityDto> jdbcCursorWriter) {
        return stepBuilderFactory
                .get("stepJdbcCursor")
                .transactionManager(transactionManagerApp)
                .<CityDto, CityDto>chunk(6)
                .reader(jdbcCursorSkipReader)
                .faultTolerant()
                .skip(RuntimeException.class)
                .skipLimit(1)
                .writer(classifierCompositeItemCityWriter)
                .stream(fileFlatWriteFile)
                .stream(fileFlatWriteFileOdd)
                .build();
    }
}
