package com.example.multitenancy.controller;

import com.example.multitenancy.constant.MultiTenantConstants;
import com.example.multitenancy.context.TenantContext;
import com.example.multitenancy.kafka.OrderStreams;
import com.example.multitenancy.project.Order;
import io.example.ordersample.avro.schemas.OrderCompletedEvent;
import io.example.ordersample.avro.schemas.OrderCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {

  @Autowired
  private OrderStreams orderStreams;

  @GetMapping
  public ResponseEntity<List<Order>> getOrders() {
    List<Order> orderList = Collections.singletonList(new Order(UUID.randomUUID().toString(), "desc"));
    return new ResponseEntity<>(orderList, HttpStatus.OK);
  }

  @PostMapping
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
    //processor.output().send(message);
    orderStreams.orderCreatedEvents().send(message);
    return orderId;
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Order> getOrder(@PathVariable String id) {
    Order order = new Order(UUID.randomUUID().toString(), "desc");
    return new ResponseEntity<>(order, HttpStatus.OK);
  }

  @PostMapping("/sleeping")
  public void sleeping() throws InterruptedException {
    Thread.sleep(5000);
  }

  @PostMapping("/error")
  public void error() {
    throw new RuntimeException("error");
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
    //processor.output().send(message);
    orderStreams.orderCompletedEvents().send(message);
  }

}
