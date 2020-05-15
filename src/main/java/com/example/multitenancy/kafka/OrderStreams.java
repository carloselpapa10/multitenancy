package com.example.multitenancy.kafka;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface OrderStreams {

  @Input("order-details")
  SubscribableChannel orders();

  @Output
  MessageChannel orderCreatedEvents();

  @Output
  MessageChannel orderCompletedEvents();
}
