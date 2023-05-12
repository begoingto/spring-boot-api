package com.begoingto.springbootapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class DevOpsApi {

    public static void main(String[] args) {
        SpringApplication.run(DevOpsApi.class, args);
    }

    @GetMapping("/test")
    public String sendMail(){
        return "mail/verify";
    }

}
