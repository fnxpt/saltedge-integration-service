package com.backbase.dbs.capabilities.extended.saltedge.service.impl;

import com.backbase.buildingblocks.presentation.errors.InternalServerErrorException;
import com.backbase.dbs.capabilities.extended.rest.spec.v1.enrichtransactions.Transaction;
import com.backbase.dbs.capabilities.extended.saltedge.model.EnrichTransactionData;
import com.backbase.dbs.capabilities.extended.saltedge.model.ErrorResponse;
import com.backbase.dbs.capabilities.extended.saltedge.service.EnrichTransactionsService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

/**
 * @author <a href="mailto:richardj@backbase.com">Richard Jones</a>
 * @since 2019-09-18
 */
@Component
public class EnrichTransactionsServiceImpl implements EnrichTransactionsService {
    
    private static final Logger log = LoggerFactory.getLogger(EnrichTransactionsServiceImpl.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String APP_ID_HEADER = "App-id";
    private static final String SECRET_HEADER = "Secret";

    private static final String SALTEDGE_HOST_URI = "https://www.saltedge.com/api";
    private static final String SALTEDGE_ENRICHMENT_URI = SALTEDGE_HOST_URI + "/v5/enrichment";

    private final String appId;
    private final String secret;

    @Autowired
    private RestTemplate restTemplate;

    public EnrichTransactionsServiceImpl(
        @Value("${integration.saltedge.appId}") String appId,
        @Value("${integration.saltedge.secret}") String secret) {
        this.appId = appId;
        this.secret = secret;
    }

    @Override
    public List<Transaction> enrichTransactions(List<Transaction> transactions) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(APP_ID_HEADER, appId);
        headers.set(SECRET_HEADER, secret);
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<EnrichTransactionData> entity = new HttpEntity<>(new EnrichTransactionData(transactions), headers);

        try {
            ResponseEntity<EnrichTransactionData> enrichedTransactions = restTemplate.postForEntity(
                    SALTEDGE_ENRICHMENT_URI,
                    entity,
                    EnrichTransactionData.class);
            return enrichedTransactions.getBody().getTransactions();
        } catch (HttpClientErrorException e) {
            ErrorResponse<EnrichTransactionData> errorResponse = convertToErrorResponse(e.getResponseBodyAsString());
            String message = (errorResponse == null) ? "An unknown error occurred" : errorResponse.getError().getMessage();
            throw new InternalServerErrorException(message, e);
        } catch (Exception e) {
            throw new InternalServerErrorException("An unknown error occurred", e);
        }
    }

    private static ErrorResponse<EnrichTransactionData> convertToErrorResponse(String body) {
        try {
            return OBJECT_MAPPER.readValue(
                    body,
                    new TypeReference<ErrorResponse<EnrichTransactionData>>() {});
        } catch (IOException e) {
            log.error("Unable to deserialize response", e);
        }
        return null;
    }
}
