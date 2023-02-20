package com.example.apixml;

import com.example.apixml.dao.CustomerDao;
import com.example.apixml.entity.Customer;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class ApiXmlApplication {
    @Autowired
    private CustomerDao customerDao;

    @Bean
    public OpenAPI customOpenAPI(@Value("${app.description}")String appDescription,
                                 @Value("${app.version}")String appVersion){
        return new OpenAPI().info(new Info().title("Course Tracker API")
                .version(appVersion)
                .description(appDescription)
                .termsOfService("http://swagger.io/terms/")
                .license(new License().name("Apache 2.0")
                        .url("http://springdoc.org"))
        );
    }


    public static void main(String[] args) {
        SpringApplication.run(ApiXmlApplication.class, args);
    }

    @Bean @Transactional @Profile("dev")
    public ApplicationRunner runner(){
        return r -> {
            Customer c1 = new Customer("Meji","San","meji@gmail.com");
            Customer c2 = new Customer("Mary","James","mary@gmail.com");
            Customer c3 = new Customer("James","Bonds","james@gmail.com");
            customerDao.save(c1);
            customerDao.save(c2);
            customerDao.save(c3);
        };
    }

}
