package com.wenance.camunda.workers.cashin;

import com.wenance.camunda.model.CashinContext;
import com.wenance.camunda.model.Product;
import com.wenance.camunda.model.Transaction;
import com.wenance.camunda.model.TransactionTypeEnum;
import info.novatec.micronaut.zeebe.client.feature.ZeebeWorker;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import jakarta.inject.Singleton;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static com.wenance.camunda.model.Variables.*;

@Singleton
public class AddRepaymentWorker {



    @ZeebeWorker(type = "add-repayment-tr")
    public void listen(final JobClient client, final ActivatedJob job) {
        // Do the business logic
        System.out.println(job.getVariablesAsMap().get(CASHIN_BALANCE));

        BigDecimal newBal = new BigDecimal((String) job.getVariablesAsMap().get(CASHIN_BALANCE));

        LinkedHashMap lmp = (LinkedHashMap)job.getVariablesAsMap().get(CASHIN_PAYMENT);
        newBal = newBal.add(new BigDecimal((String) lmp.get(CASHIN_AMOUNT)));//amount
        LinkedHashMap lmprp = (LinkedHashMap)job.getVariablesAsMap().get(CASHIN_PRODUCT);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        Product product = Product.builder()
                .productGroup("pg1")
                .type((String) lmprp.get("type"))
                .id((String) lmprp.get("id"))
                .build();
        CashinContext cashinContext = CashinContext.builder()
                .companyCode("pag_fac")
                .paymentDate(LocalDate.parse((String) lmp.get(CASHIN_DATE), formatter))
                .paymentAmount(newBal)
                .product(product)
                .transactions(new ArrayList<>())
                .build();

        final Transaction transaction = Transaction.builder()
                .entityType(product.getType())
                .entityId(product.getId())
                .transactionType(TransactionTypeEnum.DEPOSIT)
                .date((String) lmp.get(CASHIN_DATE))
                .amount(new BigDecimal((String) lmp.get(CASHIN_AMOUNT)))
                .build();
        cashinContext.getTransactions().add(transaction);

        // Probably read some input/output
        HashMap<String, Object> variables = new HashMap<>();

        variables.put(CASHIN_BALANCE, newBal);
        variables.put(CASHIN_TRANSACTIONS, cashinContext.getTransactions());

        // complete the task in the workflow engine
        client.newCompleteCommand(job.getKey())
                .variables(variables)
                .send()
                .exceptionally((throwable -> {throw new RuntimeException("Could not complete job", throwable);}));
    }

}
