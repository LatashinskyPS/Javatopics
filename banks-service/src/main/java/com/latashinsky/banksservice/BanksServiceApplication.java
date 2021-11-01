package com.latashinsky.banksservice;

import com.latashinsky.banksservice.interfaces.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class BanksServiceApplication {

    public static void main(String... args) {
        SpringApplication.run(BanksServiceApplication.class);
        Application.run();
    }
}




