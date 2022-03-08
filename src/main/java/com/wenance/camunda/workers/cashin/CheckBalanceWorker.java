package com.wenance.camunda.workers.cashin;

import info.novatec.micronaut.zeebe.client.feature.ZeebeWorker;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import static com.wenance.camunda.model.Variables.*;

@Singleton
@Slf4j
public class CheckBalanceWorker {


    @ZeebeWorker(type = "check-balance")
    public void execute(final JobClient client, final ActivatedJob job) {
        // Do the business logic
        log.info("Yeah, now you can orchestrate something :-)");

        log.info("Company {}", job.getVariablesAsMap().get(CASHIN_COMPANY));
        log.info("Payment {}", job.getVariablesAsMap().get(CASHIN_PAYMENT));


        // Probably read some input/output
        HashMap<String, Object> variables = new HashMap<>();
        variables.put(CASHIN_BALANCE, "-7000");

        // complete the task in the workflow engine
        client.newCompleteCommand(job.getKey())
                .variables(variables)
                .send()
                .exceptionally((throwable -> {
                    throw new RuntimeException("Could not complete job", throwable);
                }));
    }

}
