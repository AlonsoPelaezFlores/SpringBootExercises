package org.example.springboot_postgresql;

import org.example.springboot_postgresql.model.Employee;
import org.example.springboot_postgresql.repository.EmployeeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootPostgreSqlApplication {
    private static final Logger log = LoggerFactory.getLogger(SpringBootPostgreSqlApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootPostgreSqlApplication.class, args);
    }
}
