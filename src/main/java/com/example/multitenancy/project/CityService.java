package com.example.multitenancy.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

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
}
