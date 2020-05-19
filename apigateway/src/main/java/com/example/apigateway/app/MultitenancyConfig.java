package com.example.apigateway.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class MultitenancyConfig {

  @Value("${gateway.apps.multitenancy.uri}")
  private String multitenancyUrl;

  @Bean
  public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
    return builder.routes()
//        .route("GetMultitenancyOrders", r -> r.path("/multitenancy/order").and().method(HttpMethod.GET)
//            .filters(f -> f.rewritePath("/multitenancy/order", "/order"))
//            .uri(multitenancyUrl))
//        .route("GetMultitenancyOrderById", r -> r.path("/multitenancy/order/*").and().method(HttpMethod.GET)
//            .filters(f -> f.rewritePath("/multitenancy/order/(?<id>.*)", "/order/('${id}')"))
//            .uri(multitenancyUrl))
        .build();
  }
}
