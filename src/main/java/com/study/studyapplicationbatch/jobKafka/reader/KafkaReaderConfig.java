package com.study.studyapplicationbatch.jobKafka.reader;

import static java.lang.Boolean.TRUE;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.batch.item.kafka.KafkaItemReader;
import org.springframework.batch.item.kafka.builder.KafkaItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class KafkaReaderConfig {

    //private final KafkaProperties kafkaProperties;

    //@Value("${topics.topic-name}")
    //private String topicName;

    private Properties getKafkaProperties() {
        Properties proper = new Properties();
        proper.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        proper.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        proper.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        proper.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
        proper.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return proper;
    }

    @Bean
    public KafkaItemReader<String, String> kafkaCityItemReader() {
        return new KafkaItemReaderBuilder<String, String>()
                .partitions(0)
                .consumerProperties(getKafkaProperties())
                .name("kafkaCityItemReader")
                .saveState(TRUE)
                .topic("city")
                .build();

    }
}
