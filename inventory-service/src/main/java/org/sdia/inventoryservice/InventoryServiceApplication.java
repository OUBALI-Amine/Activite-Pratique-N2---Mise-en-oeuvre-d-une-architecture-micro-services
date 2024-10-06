package org.sdia.inventoryservice;

import org.sdia.inventoryservice.entities.Product;
import org.sdia.inventoryservice.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.stream.Stream;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(ProductRepository productRepository,
                            RepositoryRestConfiguration configuration){
        configuration.exposeIdsFor(Product.class);
        return args -> {
            Stream.of("Dell", "HP", "Lenovo")
                    .forEach(s -> {
                        Product product = Product.builder()
                                .name(s)
                                .price(Math.random() * 10000)
                                .quantity(Math.random() * 100)
                                .build();
                        productRepository.save(product);
                    });
            productRepository.findAll().forEach(product -> {
                System.out.println(product.toString());
            });
        };
    }
}
