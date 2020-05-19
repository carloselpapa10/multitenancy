package com.example.multitenancy.project;

import com.example.multitenancy.constant.MultiTenantConstants;
import com.example.multitenancy.kafka.CityStreams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    private final CityStreams cityStreams;
    @Autowired
    private CityRepository cityRepository;

    public CityService(CityStreams cityStreams) {
        this.cityStreams = cityStreams;
    }

    public void save(City city) {
        cityRepository.save(city);
    }

    public List<City> getAll() throws SQLException {
        return cityRepository.findAll();

    }

    public City get(Long id) {
        Optional<City> city = cityRepository.findById(id);
        return city.isPresent() ? city.get() : null;
    }

    public City getByName(String name) {
        return cityRepository.findByName(name);
    }

    public void delete(String name) {
        cityRepository.deleteByName(name);
    }

    public void sendCity(final City city, String tenantId) {
        MessageChannel messageChannel = cityStreams.outboundCity();
        messageChannel.send(MessageBuilder
                .withPayload(city)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .setHeader(MultiTenantConstants.X_TENANT_ID, tenantId)
                .build());
    }
}
