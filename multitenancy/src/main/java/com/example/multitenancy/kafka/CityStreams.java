package com.example.multitenancy.kafka;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface CityStreams {
    String INPUT = "city-in";
    String OUTPUT = "city-out";

    @Input(INPUT)
    SubscribableChannel inboundCity();

    @Output(OUTPUT)
    MessageChannel outboundCity();
}
