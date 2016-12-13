package com.projects.salon.config;

import com.mysql.cj.jdbc.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@Configuration
public class DatabaseConfig {

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

        org.apache.tomcat.jdbc.pool.DataSource dataSource
                = new org.apache.tomcat.jdbc.pool.DataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    @Profile("mysql")
    public DataSource local() {
        SimpleDriverDataSource dataSource = null;
        try {
            dataSource = new SimpleDriverDataSource();
            dataSource.setDriver(new Driver());
            dataSource.setUrl("URL");
            dataSource.setUsername("USERNAME");
            dataSource.setPassword("PASSWORD");
        } catch (SQLException ignore) {
            // NOP
        }
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
