package org.sdia.billingservice;

import org.sdia.billingservice.entities.Bill;
import org.sdia.billingservice.entities.ProductItem;
import org.sdia.billingservice.feign.CustomerRestClient;
import org.sdia.billingservice.feign.InventoryRestClient;
import org.sdia.billingservice.model.Customer;
import org.sdia.billingservice.repositories.BillRepository;
import org.sdia.billingservice.repositories.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(BillRepository billRepository,
							ProductItemRepository productItemRepository,
							CustomerRestClient customerRestClient,
							InventoryRestClient inventoryRestClient){
		return args -> {
			Customer customer= customerRestClient.getCustomerById(1L);
			System.out.println(customer.toString());
			Bill bill1= Bill.builder()
					.billingDate(new Date())
					.customerID(customer.getId())
					.build();
			billRepository.save(bill1);
			inventoryRestClient.pageProduct(0,10)
					.forEach(product -> {
						ProductItem build = ProductItem.builder()
								.id(product.getId())
								.price(product.getPrice())
								.quantity((double) new Random().nextInt(100))
								.bill(bill1)
								.productID(product.getId())
								.build();
						productItemRepository.save(build);
					});
		};
	}
}
