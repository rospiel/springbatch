package com.study.studyapplicationbatch.jobJdbcCursorReader.reader;

import com.study.studyapplicationbatch.jobJdbcCursorReader.domain.CityDto;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
public class JdbcCursorReaderConfig {

    @Bean
    public JdbcCursorItemReader<CityDto> jdbcCursorReader(@Qualifier("springDataSourceApplication") DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder()
                .name("jdbcCursorReader")
                .dataSource(dataSource)
                .sql("select * from city")
                .rowMapper(new BeanPropertyRowMapper<CityDto>(CityDto.class))
                .build();
    }

    @Bean
    public JdbcPagingItemReader<CityDto> jdbcPagingReader(@Qualifier("springDataSourceApplication") DataSource dataSource, PagingQueryProvider queryProvider) {
        return new JdbcPagingItemReaderBuilder<CityDto>()
                .name("jdbcPagingReader")
                .dataSource(dataSource)
                .queryProvider(queryProvider)
                .pageSize(1)
                .rowMapper(new BeanPropertyRowMapper<CityDto>(CityDto.class))
                .build();
    }

    @Bean
    public JdbcCursorItemReader<CityDto> jdbcCursorSkipReader(@Qualifier("springDataSourceApplication") DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder()
                .name("jdbcCursorReader")
                .dataSource(dataSource)
                .sql("select * from city")
                .rowMapper(rowMapper())
                .build();
    }

    private RowMapper<CityDto> rowMapper() {
        return new RowMapper<CityDto>() {
            @Override
            public CityDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                if (resultSet.getRow() == 1) {
                    throw new RuntimeException("Erro.");
                }

                return cityRowMapper(resultSet);
            }

            private CityDto cityRowMapper(ResultSet resultSet) throws SQLException {
                CityDto dto = new CityDto();
                dto.setId(resultSet.getLong("id"));
                dto.setName(resultSet.getString("name"));
                dto.setStateId(resultSet.getLong("state_id"));
                dto.setUser(resultSet.getString("user"));
                dto.setUserCreated(resultSet.getString("user_created"));
                return dto;
            }
        };
    }

    @Bean
    public SqlPagingQueryProviderFactoryBean queryProvider(@Qualifier("springDataSourceApplication") DataSource dataSource) {
        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setDataSource(dataSource);
        queryProvider.setSelectClause("select *");
        queryProvider.setFromClause("from city");
        queryProvider.setSortKey("id");
        return queryProvider;
    }
}
