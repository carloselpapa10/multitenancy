package com.example.multitenancy.project;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findById(Long id);

    City findByName(String name);

    void deleteByName(String name);
}
