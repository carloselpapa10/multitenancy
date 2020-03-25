package com.example.multitenancy;

import com.example.multitenancy.project.City;
import com.example.multitenancy.project.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MultitenancyApplication implements CommandLineRunner {

    private final String TENANT_ID = "test1";
    @Autowired
    private CityService cityService;

    public static void main(String[] args) {
        SpringApplication.run(MultitenancyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        City city = new City();
        city.setName("Barranquilla");
        cityService.sendCity(city, TENANT_ID);
    }
}
