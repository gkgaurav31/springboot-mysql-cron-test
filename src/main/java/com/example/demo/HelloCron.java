package com.example.demo;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Component
@EnableScheduling
public class HelloCron {

    private final DataSource dataSource;

    public HelloCron() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://hostname:3306/testdb");
        config.setUsername("username");
        config.setPassword("password");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        this.dataSource = new HikariDataSource(config);
    }

    @Scheduled(fixedDelay = 10000) // check every 10 seconds
    public void checkMySQLVersion() {
        try (Connection conn = dataSource.getConnection()) {
            String version = conn.getMetaData().getDatabaseProductVersion();
            System.out.println("MySQL version: " + version);
        } catch (SQLException e) {
            System.out.println("Error checking MySQL version: " + e.getMessage());
        }
    }
}