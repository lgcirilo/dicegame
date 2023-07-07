package com.lgcirilo.dicegame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DicegameApplication {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(DicegameApplication.class);
        SpringApplication.run(DicegameApplication.class, args);
    }
}
