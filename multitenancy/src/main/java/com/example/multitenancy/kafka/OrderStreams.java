package com.example.multitenancy.kafka;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.SubscribableChannel;

public interface OrderStreams {

  String INPUT = "order-in";
  String OUTPUT_CREATED = "order-creates-out";
  String OUTPUT_COMPLETED = "order-completed-out";

  @Input(INPUT)
  SubscribableChannel orders();

  @Output(OUTPUT_CREATED)
  MessageChannel orderCreatedEvents();

  @Output(OUTPUT_COMPLETED)
  MessageChannel orderCompletedEvents();
}
