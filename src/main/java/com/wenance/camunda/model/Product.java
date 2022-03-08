package com.wenance.camunda.model;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Product {
    String type;
    String id;
    String productGroup;
}