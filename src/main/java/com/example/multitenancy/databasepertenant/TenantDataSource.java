package com.example.multitenancy.databasepertenant;

import com.example.multitenancy.entity.DataSourceConfig;
import com.example.multitenancy.repository.DataSourceConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TenantDataSource implements Serializable {

    private HashMap<String, DataSource> dataSources = new HashMap<>();

    @Autowired
    private DataSourceConfigRepository dataSourceConfigRepository;

    public DataSource getDataSource(String name) {
        if (dataSources.get(name) != null) {
            return dataSources.get(name);
        }

        DataSource dataSource = createDataSource(name);
        if (dataSource != null) {
            dataSources.put(name, dataSource);
        }
        return dataSource;
    }

    private DataSource createDataSource(String name) {
        DataSourceConfig config = dataSourceConfigRepository.findByName(name);
        if (config != null) {
            DataSourceBuilder factory = DataSourceBuilder
                    .create().driverClassName(config.getDriverClassName())
                    .username(config.getUsername())
                    .password(config.getPassword())
                    .url(config.getUrl());

            DataSource ds = factory.build();
            return ds;
        }
        return null;
    }

    @PostConstruct
    public Map<String, DataSource> getAll() {
        List<DataSourceConfig> configList = dataSourceConfigRepository.findAll();
        Map<String, DataSource> result = new HashMap<>();

        configList.stream().forEach(config -> {
            DataSource dataSource = getDataSource(config.getName());
            result.put(config.getName(), dataSource);
        });

        return result;
    }
}
