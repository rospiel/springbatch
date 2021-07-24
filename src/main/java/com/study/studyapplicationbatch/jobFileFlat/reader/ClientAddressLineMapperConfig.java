package com.study.studyapplicationbatch.jobFileFlat.reader;

import com.study.studyapplicationbatch.jobFileFlat.domain.Address;
import com.study.studyapplicationbatch.jobFileFlat.domain.Client;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ClientAddressLineMapperConfig {

    private static final String[] COLUMN_NAME_CLIENT = { "firstName", "lastName", "age", "email" };
    private static final String[] COLUMN_NAME_ADDRESS = { "street", "number", "city", "state", "postalCode" };

    @Bean
    public PatternMatchingCompositeLineMapper clientAddressLineMapper() {
        PatternMatchingCompositeLineMapper lineMapper = new PatternMatchingCompositeLineMapper();
        /* informando o mecanismo de delimitador */
        lineMapper.setTokenizers(buildTokenizers());
        /* informando o mecanismo de cast para o domain */
        lineMapper.setFieldSetMappers(buildFieldSetMappers());
        return lineMapper;
    }

    private Map<String, FieldSetMapper> buildFieldSetMappers() {
        return new HashMap<String, FieldSetMapper>() {
            {
                /* Identificador da linha para o cast e o * para firmar que qualquer coisa que vier em seguida */
                put("client*", buildFieldSetMapper(Client.class));
                put("address*", buildFieldSetMapper(Address.class));
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
                /* Identificador da linha para o delimitador e o * para firmar que qualquer coisa que vier em seguida */
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
        /* qual o delimitador */
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer("#");
        /* o nome das propriedades após o delimitador */
        lineTokenizer.setNames(COLUMN_NAME_ADDRESS);
        /* por onde começar, neste caso pulamos o 0 pois o mesmo representa o tipo da linha*/
        lineTokenizer.setIncludedFields(1, 2, 3, 4, 5);
        return lineTokenizer;
    }

}
