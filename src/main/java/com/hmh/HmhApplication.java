package com.hmh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication
public class HmhApplication {

    public static void main(String[] args) {
        SpringApplication.run(HmhApplication.class, args);
    }

}
