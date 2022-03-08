package com.wenance.camunda.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction {
    private String id;
    private String entityType;
    private String entityId;
    private TransactionTypeEnum transactionType;
    private String date;
    private BigDecimal amount;
    private String channel;
    private String note;
    private Map<String, String> extraData;
}