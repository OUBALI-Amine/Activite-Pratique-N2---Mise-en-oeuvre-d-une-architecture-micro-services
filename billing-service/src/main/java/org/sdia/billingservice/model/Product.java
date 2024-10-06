package org.sdia.billingservice.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data @ToString
public class Product {
    private Long id;
    private String name;
    private Double price;
    private Double quantity;
}
