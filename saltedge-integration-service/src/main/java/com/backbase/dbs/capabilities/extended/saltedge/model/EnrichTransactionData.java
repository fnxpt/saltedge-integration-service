package com.backbase.dbs.capabilities.extended.saltedge.model;

import com.backbase.dbs.capabilities.extended.rest.spec.v1.enrichtransactions.Transaction;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author <a href="mailto:richardj@backbase.com">Richard Jones</a>
 * @since 2019-09-19
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EnrichTransactionData {

    @JsonProperty("data")
    List<Transaction> transactions;
}
