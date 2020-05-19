package com.example.multitenancy;

import com.example.multitenancy.project.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.schema.client.ConfluentSchemaRegistryClient;
import org.springframework.cloud.stream.schema.client.EnableSchemaRegistryClient;
import org.springframework.cloud.stream.schema.client.SchemaRegistryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@EnableSchemaRegistryClient
public class MultitenancyApplication implements CommandLineRunner {

  private final String TENANT_ID = "test1";
  @Autowired
  private CityService cityService;
  @Value("${spring.cloud.stream.kafka.binder.producer-properties.schema.registry.url}")
  private String endPoint;

  public static void main(String[] args) {
    SpringApplication.run(MultitenancyApplication.class, args);
  }

  @Bean
  public SchemaRegistryClient schemaRegistryClient() {
    ConfluentSchemaRegistryClient client = new ConfluentSchemaRegistryClient();
    client.setEndpoint("http://localhost:8081");
    return client;
  }

  @Override
  public void run(String... args) throws Exception {
//        City city = new City();
//        city.setName("Barranquilla");
//        cityService.sendCity(city, TENANT_ID);
  }
}
