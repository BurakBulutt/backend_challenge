package com.example.backend_challange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BackendChallangeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendChallangeApplication.class, args);
    }

}
