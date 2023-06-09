package Baloot;


import Baloot.service.MyService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BalootApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BalootApplication.class, args);
        MyService myService = context.getBean(MyService.class);
        myService.testConnection();
    }
}
