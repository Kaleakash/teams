package com.bean;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class DatabaseHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        boolean databaseAvailable = true;
        if (databaseAvailable) {
            return Health.up()
                    .withDetail("Database", "H2 Database is Running")
                    .withDetail("Status", "Connected")
                    .build();
        }
        return Health.down()
                .withDetail("Database", "Database Connection Failed")
                .build();

    }

}
