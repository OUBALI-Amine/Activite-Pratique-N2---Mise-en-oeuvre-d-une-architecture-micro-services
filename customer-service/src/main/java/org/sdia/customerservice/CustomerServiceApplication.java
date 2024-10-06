package org.sdia.customerservice;

import org.sdia.customerservice.entities.Customer;
import org.sdia.customerservice.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.stream.Stream;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            RepositoryRestConfiguration configuration){
        configuration.exposeIdsFor(Customer.class);
        return args -> {
            Stream.of("Amine", "Malak", "Anas", "Hossam", "Omar")
                    .forEach(s -> {
                        Customer customer = Customer.builder()
                                .name(s)
                                .email(s+"@gmail.com")
                                .build();
                        customerRepository.save(customer);
                    });
            customerRepository.findAll().forEach(customer -> {
                System.out.println(customer.toString());
            });
        };
    }

}
