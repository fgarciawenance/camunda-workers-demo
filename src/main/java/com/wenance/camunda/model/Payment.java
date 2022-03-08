package com.wenance.camunda.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Payment {
    LocalDate date;
    String financialInstitution;
    String type;
    String subtype;
    BigDecimal amount;

}