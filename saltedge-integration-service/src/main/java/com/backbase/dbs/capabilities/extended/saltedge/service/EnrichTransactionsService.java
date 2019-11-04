package com.backbase.dbs.capabilities.extended.saltedge.service;


import com.backbase.dbs.capabilities.extended.rest.spec.v1.enrichtransactions.Transaction;

import java.util.List;

public interface EnrichTransactionsService {

    List<Transaction> enrichTransactions(List<Transaction> transactions);
}
