package com.example.multitenancy.kafka;

import com.example.multitenancy.project.City;
import com.example.multitenancy.project.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(CityStreams.class)
public class CityListener {

    private final Logger log = LoggerFactory.getLogger(CityListener.class);

    @Autowired
    private CityService cityService;

    @StreamListener(CityStreams.OUTPUT)
    public void handleCity(@Payload City city) {
        System.out.println(String.format("Received City: %s", city.toString()));

        try {
            cityService.save(city);
        } catch (Exception ex) {
            log.error("Error saving a city ==> ", ex);
        }
    }
}
