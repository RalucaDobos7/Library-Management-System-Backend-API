package com.library.management.system.config;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PostgresContainerExtension implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {

    private static final Lock LOCK = new ReentrantLock();
    private static volatile boolean started = false;

    private PostgreSQLContainer<?> postgreSQLContainer;

    @Override
    public void beforeAll(ExtensionContext extensionContext) {

        LOCK.lock();
        try {
            if(!started) {
                started = true;

                var username = "postgres";
                var password = "1234";
                var dbName = "test";

                postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.3")
                        .withDatabaseName(dbName)
                        .withUsername(username)
                        .withPassword(password)
                        .withExposedPorts(5432);

                postgreSQLContainer.setPortBindings(List.of("5454:5432"));
                postgreSQLContainer.start();

                String jdbcUrl = String.format("jdbc:postgresql://localhost:%d/" + dbName, postgreSQLContainer.getFirstMappedPort());
                System.setProperty("spring.datasource.url", jdbcUrl);
                System.setProperty("spring.datasource.username", username);
                System.setProperty("spring.datasource.password", password);

            }
        } finally {
            LOCK.unlock();
        }
    }

    @Override
    public void close() {
        postgreSQLContainer.stop();
    }
}
