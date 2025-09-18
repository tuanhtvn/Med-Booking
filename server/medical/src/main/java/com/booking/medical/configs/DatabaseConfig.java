package com.booking.medical.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class DatabaseConfig extends AbstractMongoClientConfiguration {

    @Autowired
    private EnvConfig envConfig;

    @Override
    protected String getDatabaseName() {
        return envConfig.getDbName();
    }

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create(envConfig.getConnString());
    }
}