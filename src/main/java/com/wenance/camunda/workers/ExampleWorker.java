package com.wenance.camunda.workers;

import info.novatec.micronaut.zeebe.client.feature.ZeebeWorker;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import jakarta.inject.Singleton;


@Singleton
public class ExampleWorker {

    @ZeebeWorker(type = "read-and-fail")
    public void doSomething(JobClient client, ActivatedJob job) {
        // Put your business logic here
        client.newCompleteCommand(job.getKey()).send()
                .exceptionally( throwable -> { throw new RuntimeException("Could not complete job " + job, throwable); });
    }








 /*   @ZeebeWorker(type = "add-repayment-tr")
    public void nonBlockingRestCall(final JobClient client, final ActivatedJob job) {
        System.out.println("Invoke REST call...");
        Flux<String> paymentResponseFlux = WebClient.create()
                .get().uri(PAYMENT_URL).retrieve()
                .bodyToFlux(String.class);

        // non-blocking, so we register the callbacks (for happy and exceptional case)
        paymentResponseFlux.subscribe(
                response -> {
                    System.out.println("...finished. Complete Job...");
                    client.newCompleteCommand(job.getKey()).send()
                            // non-blocking, so we register the callbacks (for happy and exceptional case)
                            .thenApply(jobResponse -> { System.out.println(counter.inc()); return jobResponse;})
                            .exceptionally(t -> {throw new RuntimeException("Could not complete job: " + t.getMessage(), t);});
                },
                exception -> {
                    System.out.println("...REST invocation problem: " + exception.getMessage());
                    client.newFailCommand(job.getKey())
                            .retries(1)
                            .errorMessage("Could not invoke REST API: " + exception.getMessage()).send()
                            .exceptionally(t -> {throw new RuntimeException("Could not fail job: " + t.getMessage(), t);});
                }
        );

    }*/


}
