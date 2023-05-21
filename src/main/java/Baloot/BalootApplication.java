package Baloot;


import Baloot.Entity.Employee;
import Baloot.service.MyService;

import com.mysql.cj.xdevapi.SessionFactory;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BalootApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BalootApplication.class, args);
        MyService myService = context.getBean(MyService.class);
        myService.testConnection();
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS");
            }
        };

    }
}
