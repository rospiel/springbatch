package com.study.studyapplicationbatch.processorsClassifier.processor;

import static java.lang.Boolean.TRUE;
import static java.lang.String.format;

import com.study.studyapplicationbatch.processorsClassifier.domain.AddressProcessorClassifier;
import com.study.studyapplicationbatch.processorsClassifier.domain.ClientProcessorClassifier;
import lombok.SneakyThrows;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemProcessorBuilder;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class ProcessorClassifierValidationConfig {

    @Bean
    public ItemProcessor validationProcessorClassifier() {
        return new ClassifierCompositeItemProcessorBuilder<>()
                .classifier(classifier())
                .build();
    }

    private Classifier classifier() {
        return new Classifier() {
            @SneakyThrows
            @Override
            public Object classify(Object objeto) {
                return objeto instanceof ClientProcessorClassifier ? beanValidator() : customValidator();
            }
        };
    }

    private BeanValidatingItemProcessor<ClientProcessorClassifier> beanValidator() throws Exception {
        BeanValidatingItemProcessor<ClientProcessorClassifier> processor = new BeanValidatingItemProcessor();
        processor.setFilter(TRUE);
        processor.afterPropertiesSet();
        return processor;
    }

    private ValidatingItemProcessor<AddressProcessorClassifier> customValidator() {
        ValidatingItemProcessor<AddressProcessorClassifier> processor = new ValidatingItemProcessor<>();
        processor.setValidator(validator());
        processor.setFilter(TRUE);
        return processor;
    }

    private Validator<? super AddressProcessorClassifier> validator() {
        return new Validator<AddressProcessorClassifier>() {
            @Override
            public void validate(AddressProcessorClassifier addressProcessorClassifier) throws ValidationException {
                Optional.ofNullable(addressProcessorClassifier.getNumber())
                        .orElseThrow(() -> new ValidationException(format("Rua.: %s esta com endereço incompleto",
                                addressProcessorClassifier.getStreet())));
            }
        };
    }
}
