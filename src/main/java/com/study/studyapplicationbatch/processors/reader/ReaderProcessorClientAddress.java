package com.study.studyapplicationbatch.processors.reader;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.study.studyapplicationbatch.jobFileFlat.domain.Address;
import com.study.studyapplicationbatch.jobFileFlat.domain.Client;
import com.study.studyapplicationbatch.processors.domain.AddressProcessor;
import com.study.studyapplicationbatch.processors.domain.ClientProcessor;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.core.io.Resource;

public class ReaderProcessorClientAddress implements ItemStreamReader<ClientProcessor>, ResourceAwareItemReaderItemStream<ClientProcessor> {

    private Object object;
    private FlatFileItemReader<Object> delegate;

    public ReaderProcessorClientAddress(FlatFileItemReader<Object> delegate) {
        this.delegate = delegate;
    }

    @Override
    public ClientProcessor read() throws Exception {
        object = isNull(object) ? delegate.read() : object;

        ClientProcessor client = (ClientProcessor) object;
        object = null;

        if (nonNull(client)) {
            while (peek() instanceof AddressProcessor) {
                client.setAddress((AddressProcessor) object);
            }
        }

        return client;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        delegate.open(executionContext);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        delegate.update(executionContext);
    }

    @Override
    public void close() throws ItemStreamException {
        delegate.close();
    }

    @Override
    public void setResource(Resource resource) {
        delegate.setResource(resource);
    }

    private Object peek() throws Exception {
        return ( object = delegate.read() );
    }
}
