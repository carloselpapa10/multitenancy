package com.example.multitenancy.controller;

import com.example.multitenancy.constant.MultiTenantConstants;
import com.example.multitenancy.context.TenantContext;
import io.example.ordersample.avro.schemas.OrderCompletedEvent;
import io.example.ordersample.avro.schemas.OrderCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/order")
public class Controller {

  @Autowired
  private Processor processor;

  @PostMapping("/create")
  public String createOrder() {

    String orderId = UUID.randomUUID().toString();

    OrderCreatedEvent orderCreatedEvent = OrderCreatedEvent.newBuilder()
        .setId(orderId)
        .setDescription("Description")
        .setCustomerId("CustomerID")
        .setNewProperty("TODO BN")
        .build();

    Message<OrderCreatedEvent> message = MessageBuilder.withPayload(orderCreatedEvent)
        .setHeader(KafkaHeaders.MESSAGE_KEY, orderId)
        .setHeader("type", "OrderCreatedEvent")
        .setHeader(MultiTenantConstants.X_TENANT_ID, TenantContext.getCurrentTenant())
        .build();
    processor.output().send(message);

    return orderId;
  }

  @PostMapping("/complete/{orderId}")
  public void completeOrder(@PathVariable String orderId) {

    OrderCompletedEvent orderCompletedEvent = OrderCompletedEvent.newBuilder()
        .setId(orderId)
        .build();

    Message<OrderCompletedEvent> message = MessageBuilder.withPayload(orderCompletedEvent)
        .setHeader(KafkaHeaders.MESSAGE_KEY, orderId)
        .setHeader("type", "OrderCompletedEvent")
        .setHeader(MultiTenantConstants.X_TENANT_ID, TenantContext.getCurrentTenant())
        .build();
    processor.output().send(message);
  }

}
