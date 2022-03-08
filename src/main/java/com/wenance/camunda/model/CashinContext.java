package com.wenance.camunda.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
@Data
public class CashinContext {
    private final String companyCode;
    private final Product product;
    private final BigDecimal paymentAmount;
    private final LocalDate paymentDate;
    private List<Transaction> transactions;

    public BigDecimal getTransactionsAmount() {
        return transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Transaction> addTransaction(Transaction  transaction){
        transactions.add(transaction);
        return transactions;
    }

    public BigDecimal getPaymentBalance() {
        return paymentAmount.subtract(getTransactionsAmount());
    }

}