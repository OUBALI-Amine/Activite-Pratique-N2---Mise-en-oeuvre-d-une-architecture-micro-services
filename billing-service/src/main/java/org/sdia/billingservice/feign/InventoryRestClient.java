package org.sdia.billingservice.feign;

import org.sdia.billingservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "INVENTORY-SERVICE")
public interface InventoryRestClient {
    @GetMapping("/products")
    PagedModel<Product> pageProduct(@RequestParam(name = "page") int page,
                                    @RequestParam(name = "size") int size);
    @GetMapping("/products/{id}")
    Product getProductById(@PathVariable Long id);
}
