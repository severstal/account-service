package ru.iportnyagin.accountservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Service.
 *
 * @author portnyagin
 */
@SpringBootApplication
@EnableScheduling
public class Service {

    public static void main(String[] args) {
        SpringApplication.run(Service.class, args);
    }

}
