package org.sdia.billingservice.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data @ToString
public class Customer {
    private Long id;
    private String name;
    private String email;
}
