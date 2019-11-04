package com.backbase.dbs.capabilities.extended.saltedge.listener;

import com.backbase.buildingblocks.backend.communication.event.annotations.RequestListener;
import com.backbase.buildingblocks.backend.communication.event.proxy.RequestProxyWrapper;
import com.backbase.buildingblocks.backend.internalrequest.InternalRequest;
import com.backbase.buildingblocks.presentation.errors.BadRequestException;
import com.backbase.buildingblocks.presentation.errors.InternalServerErrorException;
import com.backbase.dbs.capabilities.extended.saltedge.service.EnrichTransactionsService;
import com.backbase.dbs.capabilities.extended.listener.spec.v1.enrichtransactions.EnrichTransactionsListener;
import com.backbase.dbs.capabilities.extended.rest.spec.v1.enrichtransactions.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Note that JMS RequestListeners are deprecated in Service SDK 8.2.x / DBS 2.16.0 and will be removed in Service SDK
 * 9.0.0 / DBS 2.17.0, when HTTP will be the only supported option for synchronous interservice communication.
 */
@Deprecated
@Service
@RequestListener
public class ExampleRequestListenerImpl implements EnrichTransactionsListener {

    private static final Logger log = LoggerFactory.getLogger(ExampleRequestListenerImpl.class);

    @Autowired
    private EnrichTransactionsService enrichTransactionsService;

    @Override
    public RequestProxyWrapper<List<Transaction>> postEnrichTransactions(RequestProxyWrapper<List<Transaction>> transactionsWrapper, Exchange exchange) throws BadRequestException, InternalServerErrorException {
        List<Transaction> response = enrichTransactionsService.enrichTransactions(transactionsWrapper.getRequest().getData());
        return buildPostRequestProxyWrapper(response);
    }

    private RequestProxyWrapper<List<Transaction>> buildPostRequestProxyWrapper(List<Transaction> data) {
        InternalRequest internalRequest = new InternalRequest();
        internalRequest.setData(data);
        RequestProxyWrapper result = new RequestProxyWrapper();
        result.setRequest(internalRequest);
        result.setHttpMethod("post");
        return result;
    }
}
