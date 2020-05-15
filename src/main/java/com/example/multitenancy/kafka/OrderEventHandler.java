package com.example.multitenancy.kafka;

import io.example.ordersample.avro.schemas.OrderCompletedEvent;
import io.example.ordersample.avro.schemas.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Processor.class)
public class OrderEventHandler {

  private static Logger logger = LoggerFactory.getLogger(OrderEventHandler.class);

  @StreamListener(value = Processor.INPUT, condition = "headers['type']=='OrderCreatedEvent'")
  public void handleOrderCreatedEvent(@Payload OrderCreatedEvent orderCreatedEvent) {
    logger.info("Handle Order Created Event");
  }

  @StreamListener(value = Processor.INPUT, condition = "headers['type']=='OrderCompletedEvent'")
  public void handleOrderCompletedEvent(@Payload OrderCompletedEvent orderCompletedEvent) {
    logger.info("Handle Order Completed Event");
  }
}
