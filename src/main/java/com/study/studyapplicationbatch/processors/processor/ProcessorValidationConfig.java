package com.study.studyapplicationbatch.processors.processor;


import static java.lang.Boolean.TRUE;
import static java.lang.String.format;

import com.study.studyapplicationbatch.processors.domain.ClientProcessor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;


@Configuration
public class ProcessorValidationConfig {

    @Bean
    public ItemProcessor<ClientProcessor, ClientProcessor> validationProcessor2() throws Exception {
       return new CompositeItemProcessorBuilder<ClientProcessor, ClientProcessor>()
               .delegates(beanValidator(), customValidator())
               .build();
    }

    private BeanValidatingItemProcessor<ClientProcessor> beanValidator() throws Exception {
        BeanValidatingItemProcessor<ClientProcessor> processor = new BeanValidatingItemProcessor();
        processor.setFilter(TRUE);
        processor.afterPropertiesSet();
        return processor;
    }

    private ValidatingItemProcessor<ClientProcessor> customValidator() {
        ValidatingItemProcessor<ClientProcessor> processor = new ValidatingItemProcessor<>();
        processor.setValidator(validator());
        processor.setFilter(TRUE);
        return processor;
    }

    private Validator<? super ClientProcessor> validator() {
        return new Validator<ClientProcessor>() {
            @Override
            public void validate(ClientProcessor clientProcessor) throws ValidationException {
                Optional.ofNullable(clientProcessor.getAddress().getNumber())
                        .orElseThrow(() -> new ValidationException(format("Cliente.: %s esta com endereço incompleto",
                                clientProcessor.getFirstName())));
            }
        };
    }
}
