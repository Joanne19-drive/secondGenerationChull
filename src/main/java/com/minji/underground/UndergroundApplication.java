package com.minji.underground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class UndergroundApplication {

    public static void main(String[] args) {
        SpringApplication.run(UndergroundApplication.class, args);
    }

}
