package org.sdia.billingservice.web;

import org.sdia.billingservice.entities.Bill;
import org.sdia.billingservice.entities.ProductItem;
import org.sdia.billingservice.feign.CustomerRestClient;
import org.sdia.billingservice.feign.InventoryRestClient;
import org.sdia.billingservice.model.Customer;
import org.sdia.billingservice.model.Product;
import org.sdia.billingservice.repositories.BillRepository;
import org.sdia.billingservice.repositories.ProductItemRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BillingRestController {
    private BillRepository billRepository;
    private ProductItemRepository productItemRepository;
    private CustomerRestClient customerRestClient;
    private InventoryRestClient inventoryRestClient;

    public BillingRestController(BillRepository billRepository, ProductItemRepository productItemRepository, CustomerRestClient customerRestClient, InventoryRestClient inventoryRestClient) {
        this.billRepository = billRepository;
        this.productItemRepository = productItemRepository;
        this.customerRestClient = customerRestClient;
        this.inventoryRestClient = inventoryRestClient;
    }

    @GetMapping("/bills/full/{id}")
    public Bill getBill(@PathVariable(name = "id") Long id){
        Bill bill = billRepository.findById(id).get();
        bill.setCustomer(customerRestClient.getCustomerById(bill.getCustomerID()));
        bill.getProductItems().forEach(productItem -> {
            Product product = inventoryRestClient.getProductById(productItem.getProductID());
            productItem.setProduct(product);
        });
        return bill;
    }
}
