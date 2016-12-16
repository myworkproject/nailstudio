package com.projects.salon.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@Configuration
public class DatabaseConfig {
    private static final int DATABASE_POOL_SIZE = 2;

    @Bean
    @Profile("heroku")
    public DataSource heroku() {
        String databaseUrl = System.getenv("DATABASE_URL");

        URI dbUri;
        try {
            dbUri = new URI(databaseUrl);
        } catch (URISyntaxException ignore) {
            return null;
        }

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':'
                + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setJdbcUrl(dbUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaximumPoolSize(DATABASE_POOL_SIZE);
        return dataSource;
    }

    @Bean
    @Profile("test")
    public DataSource inMemory() {
        return new EmbeddedDatabaseBuilder()
                .setType(H2)
                .addDefaultScripts()
                .build();
    }
}
