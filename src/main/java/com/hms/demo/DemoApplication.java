package com.hms.demo;

import Entity.Hospital;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.swing.text.html.parser.Entity;


@SpringBootApplication
@ComponentScan(basePackages = {"Driver"})                  // for Controller/Dao/Service
@EnableJpaRepositories(basePackages = {"Repository"})      // for Spring Data Repos
@EntityScan(basePackages = {"Entity"})                     // for @Entity classes
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }




}
