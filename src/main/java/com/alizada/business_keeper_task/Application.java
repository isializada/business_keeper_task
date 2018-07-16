package com.alizada.business_keeper_task;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAutoConfiguration
@EnableTransactionManagement
@EntityScan(basePackages = "com.alizada.business_keeper_task.model")
@EnableJpaRepositories(basePackages = "com.alizada.business_keeper_task.repository")
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}
