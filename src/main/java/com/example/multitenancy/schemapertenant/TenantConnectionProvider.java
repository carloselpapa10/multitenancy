package com.example.multitenancy.schemapertenant;

import com.example.multitenancy.constant.MultiTenantConstants;
import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class TenantConnectionProvider implements MultiTenantConnectionProvider {

    private static Logger logger = LoggerFactory.getLogger(TenantConnectionProvider.class);
    private DataSource datasource;

    public TenantConnectionProvider(DataSource datasource) {
        this.datasource = datasource;
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return datasource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        logger.debug("Get connection for tenant {}", tenantIdentifier);
//        final Connection connection = getAnyConnection();
//        connection.setSchema(tenantIdentifier);
//        return connection;

        final Connection connection = getAnyConnection();
        try {
            if (tenantIdentifier != null) {
                connection.createStatement().execute("USE " + tenantIdentifier);
            } else {
                connection.createStatement().execute("USE " + MultiTenantConstants.DEFAULT_TENANT_ID);
            }
        } catch (SQLException e) {
            throw new HibernateException(
                    "Could not alter JDBC connection to specified schema [" + tenantIdentifier + "]", e
            );
        }
        return connection;
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        logger.debug("Release connection for tenant {}", tenantIdentifier);
        connection.setSchema(MultiTenantConstants.DEFAULT_TENANT_ID);
        releaseAnyConnection(connection);
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public boolean isUnwrappableAs(Class aClass) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        return null;
    }
}
