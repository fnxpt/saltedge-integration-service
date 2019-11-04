package com.backbase.dbs.capabilities.extended.saltedge.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.backbase.buildingblocks.presentation.errors.BadRequestException;
import com.backbase.buildingblocks.presentation.errors.InternalServerErrorException;
import com.backbase.dbs.capabilities.extended.saltedge.service.EnrichTransactionsService;
import com.backbase.dbs.capabilities.extended.rest.spec.serviceapi.v1.enrichtransactions.EnrichTransactionsApi;
import com.backbase.dbs.capabilities.extended.rest.spec.v1.enrichtransactions.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Processes requests sent by a peer service to the integration service's /service-api/* endpoint.
 */
@RestController
public class EnrichTransactionsController implements EnrichTransactionsApi {

    private static final Logger log = LoggerFactory.getLogger(EnrichTransactionsController.class);

    @Autowired
    private EnrichTransactionsService enrichTransactionsService;

    public EnrichTransactionsController() {
        log.info("EnrichTransactionsController");
    }

    @Override
    public List<Transaction> postEnrichTransactions(@Valid List<Transaction> transactions, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws BadRequestException, InternalServerErrorException {
        return enrichTransactionsService.enrichTransactions(transactions);
    }
}
