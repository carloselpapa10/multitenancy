package com.example.multitenancy.databasepertenant;

import com.example.multitenancy.constant.MultiTenantConstants;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    @Autowired
    private DataSource defaultDataSource;
    @Autowired
    private ApplicationContext applicationContext;
    private Map<String, DataSource> map = new HashMap<>();
    private boolean init = false;

    @PostConstruct
    public void load() {
        map.put(MultiTenantConstants.DEFAULT_TENANT_ID, defaultDataSource);
    }

    @Override
    protected DataSource selectAnyDataSource() {
        return map.get(MultiTenantConstants.DEFAULT_TENANT_ID);
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        if (!init) {
            init = true;
            TenantDataSource tenantDataSource = applicationContext.getBean(TenantDataSource.class);
            map.putAll(tenantDataSource.getAll());
        }
        return map.get(tenantIdentifier) != null ? map.get(tenantIdentifier) : map.get(MultiTenantConstants.DEFAULT_TENANT_ID);
    }
}
