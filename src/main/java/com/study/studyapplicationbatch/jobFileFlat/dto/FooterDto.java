package com.study.studyapplicationbatch.jobFileFlat.dto;

import static java.lang.String.format;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;

@Service
public class FooterDto implements FlatFileFooterCallback {

    private static int count = 0;

    @Override
    public void writeFooter(Writer writer) throws IOException {
        writer.write(format("\n\n\nTotal Clientes.: %s - STUDY APPLICATION BATCH", count));
    }

    @BeforeWrite
    public void beforeWrite() {
        count++;
    }

    @AfterChunk
    public void afterChunk(ChunkContext context) {
        count = 0;
    }
}
