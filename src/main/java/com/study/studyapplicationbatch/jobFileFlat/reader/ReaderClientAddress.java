package com.study.studyapplicationbatch.jobFileFlat.reader;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.study.studyapplicationbatch.jobFileFlat.domain.Address;
import com.study.studyapplicationbatch.jobFileFlat.domain.Client;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.core.io.Resource;

public class ReaderClientAddress implements ItemStreamReader<Client>, ResourceAwareItemReaderItemStream<Client> {

    private Object object;
    private FlatFileItemReader<Object> delegate;

    public ReaderClientAddress(FlatFileItemReader<Object> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Client read() throws Exception {
        object = isNull(object) ? delegate.read() : object;

        Client client = (Client) object;
        object = null;

        if (nonNull(client)) {
            while (peek() instanceof Address) {
                client.setAddress((Address) object);
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
