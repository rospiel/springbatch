package com.study.studyapplicationbatch.jobJdbcCursorReader.writer;

import static java.lang.Boolean.TRUE;

import com.study.studyapplicationbatch.jobJdbcCursorReader.domain.CityDto;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemWriterBuilder;
import org.springframework.batch.item.support.builder.CompositeItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Configuration
public class JdbcCursorWriterConfig {

    //@Bean
    public ItemWriter jdbcCursorWriter() {
        return cities -> cities.forEach(System.out::println);
    }

    @Bean
    public ClassifierCompositeItemWriter<CityDto> classifierCompositeItemCityWriter(@Qualifier("springDataSourceApplication") DataSource dataSource,
        @Qualifier("fileFlatWriteFile") FlatFileItemWriter<CityDto> fileFlatWriteFile) {
        return new ClassifierCompositeItemWriterBuilder<CityDto>()
                .classifier(classifier(dataSource, fileFlatWriteFile))
                .build();
    }

    private Classifier<CityDto, ItemWriter<? super CityDto>> classifier(DataSource dataSource, FlatFileItemWriter<CityDto> fileFlatWriteFile) {
        return new Classifier<CityDto, ItemWriter<? super CityDto>>() {
            @Override
            public ItemWriter<? super CityDto> classify(CityDto cityDto) {
                return cityDto.getId() % 2 == 0 ? compositeItemCityWriter(dataSource, fileFlatWriteFile) :
                        fileFlatWriteFileOdd();
            }
        };
    }

    //@Bean
    public CompositeItemWriter<CityDto> compositeItemCityWriter(DataSource dataSource,
                                                                FlatFileItemWriter<CityDto> fileFlatWriteFile) {
        return new CompositeItemWriterBuilder<CityDto>()
                .delegates(fileFlatWriteFile, jdbcCursorWriterDatabase(dataSource))
                .build();
    }

    @Bean
    public FlatFileItemWriter<CityDto> fileFlatWriteFile() {
        return new FlatFileItemWriterBuilder<CityDto>()
                .name("fileFlatWriteFile")
                .resource(new FileSystemResource("generatedFiles/citiesEven.txt"))
                .shouldDeleteIfExists(TRUE)
                .shouldDeleteIfEmpty(TRUE)
                .delimited()
                .delimiter(";")
                .names("id", "name", "stateId", "user", "userCreated")
                .build();

    }

    @Bean
    public FlatFileItemWriter<CityDto> fileFlatWriteFileOdd() {
        return new FlatFileItemWriterBuilder<CityDto>()
                .name("fileFlatWriteFile")
                .resource(new FileSystemResource("generatedFiles/citiesOdd.txt"))
                .shouldDeleteIfExists(TRUE)
                .shouldDeleteIfEmpty(TRUE)
                .delimited()
                .delimiter(";")
                .names("id", "name", "stateId", "user", "userCreated")
                .build();

    }

    //@Bean
    public JdbcBatchItemWriter<CityDto> jdbcCursorWriterDatabase(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<CityDto>()
                .dataSource(dataSource)
                .sql("insert into city(name, state_id) values(?, (select id from state where id = ?)) ")
                .itemPreparedStatementSetter(itemPreparedStatementSetter())
                .build();
    }

    private ItemPreparedStatementSetter<CityDto> itemPreparedStatementSetter() {
        return new ItemPreparedStatementSetter<CityDto>() {
            @Override
            public void setValues(CityDto cityDto, PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, cityDto.getName());
                preparedStatement.setLong(2, cityDto.getStateId());
            }
        };
    }
}
