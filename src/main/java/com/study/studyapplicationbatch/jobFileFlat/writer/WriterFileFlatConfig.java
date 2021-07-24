package com.study.studyapplicationbatch.jobFileFlat.writer;

import static java.lang.Boolean.TRUE;

import com.study.studyapplicationbatch.jobFileFlat.domain.Client;
import com.study.studyapplicationbatch.jobFileFlat.dto.FooterDto;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemWriter;
import org.springframework.batch.item.file.ResourceSuffixCreator;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.builder.MultiResourceItemWriterBuilder;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.batch.item.mail.SimpleMailMessageItemWriter;
import org.springframework.batch.item.mail.builder.SimpleMailMessageItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class WriterFileFlatConfig {

    private static final String NAME_FORMATTED = "\t#######Nome.:";
    private static final String LAST_NAME_FORMATTED = "\t#######Sobrenome.:";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static int count = 1;

    //@Bean
    public ItemWriter fileFlatWriter() {
        return itens -> itens.forEach(System.out::println);
    }

    @Bean
    public MultiResourceItemWriter<Client> multiResourceClientItemWriter(FooterDto footer) {
        return new MultiResourceItemWriterBuilder<Client>()
                .name("multiResourceClientItemWriter")
                .resource(new FileSystemResource("generatedFiles/clients"))
                .delegate(fileFlatWriteFile(footer))
                .resourceSuffixCreator(suffixCreator())
                .itemCountLimitPerResource(1)
                .build();
    }

    private ResourceSuffixCreator suffixCreator() {
        return new ResourceSuffixCreator() {
            @Override
            public String getSuffix(int index) {
                return index + ".txt";
            }
        };
    }

    //@Bean
    public FlatFileItemWriter<Client> fileFlatWriteFile(FooterDto footer) {
        return new FlatFileItemWriterBuilder<Client>()
                .name("fileFlatWriteFile")
                .resource(new FileSystemResource("generatedFiles/clients.txt"))
                .shouldDeleteIfExists(TRUE)
                .shouldDeleteIfEmpty(TRUE)
                .lineAggregator(lineAggregator())
                .headerCallback(header())
                .footerCallback(footer)
                .build();
    }

    private FlatFileHeaderCallback header() {
        return new FlatFileHeaderCallback() {
            @Override
            public void writeHeader(Writer writer) throws IOException {
                writer.write("Relatório de Clientes - " +
                        DATE_FORMAT.format(new Date()) + "\n\n");
            }
        };
    }

    private LineAggregator<Client> lineAggregator() {
        return new LineAggregator<Client>() {
            @Override
            public String aggregate(Client client) {
                return String.join("", NAME_FORMATTED, client.getFirstName(),
                        "\n" + LAST_NAME_FORMATTED, client.getLastName());
            }
        };
    }
}
