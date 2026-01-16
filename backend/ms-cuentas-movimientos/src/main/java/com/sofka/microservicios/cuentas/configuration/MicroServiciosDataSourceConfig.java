package com.sofka.microservicios.cuentas.configuration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

/**
 * IHSP-2 Create Java Spring Boot Project with basic Settings for supporting REST API Operations
 * 
 *
 * @author Aura Cristina Garzon Rodriguez (auragarzonr@gmail.com)
 * @since 15 Enero 2026 4:20 PM
 */

@Configuration
@ConfigurationProperties(prefix = "spring.microservicios-datasource")
public class MicroServiciosDataSourceConfig {

    private String url;
    private String username;
    private String password;
    private String driverClassName;

    @Autowired
    private Environment env;

    private static final Logger log = LoggerFactory.getLogger(MicroServiciosDataSourceConfig.class);

    // Getters y setters
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getDriverClassName() { return driverClassName; }
    public void setDriverClassName(String driverClassName) { this.driverClassName = driverClassName; }

    private static final Pattern PLACEHOLDER = Pattern.compile("\\$\\{([^}]+)}");

    private String resolveIfPlaceholder(String value) {
        if (value == null) return null;
        Matcher m = PLACEHOLDER.matcher(value.trim());
        if (m.matches()) {
            String key = m.group(1);
            // Try Spring Environment first, then system env vars
            String v = env.getProperty(key);
            if (v == null) v = System.getenv(key);
            return v;
        }
        // Not a simple placeholder, return original
        return value;
    }

    @Bean(name = "microserviciosDataSource")
    public DataSource dataSource() {
        String resolvedUrl = resolveIfPlaceholder(this.url);
        if (resolvedUrl == null) resolvedUrl = this.url; // fallback to original value if not placeholder
        String resolvedUsername = resolveIfPlaceholder(this.username);
        if (resolvedUsername == null) resolvedUsername = this.username;
        String resolvedPassword = resolveIfPlaceholder(this.password);
        if (resolvedPassword == null) resolvedPassword = this.password;
        String resolvedDriver = resolveIfPlaceholder(this.driverClassName);
        if (resolvedDriver == null) resolvedDriver = this.driverClassName;

        // If url still contains an unresolved placeholder or is null/empty, try common env var name as fallback
        if (resolvedUrl == null || resolvedUrl.trim().isEmpty() || resolvedUrl.contains("${")) {
            String alt = env.getProperty("MICROSERVICIOS_DB_DATASOURCE_URL");
            if (alt == null) alt = System.getenv("MICROSERVICIOS_DB_DATASOURCE_URL");
            if (alt != null && !alt.trim().isEmpty()) resolvedUrl = alt;
        }

        if (resolvedUsername == null || resolvedUsername.trim().isEmpty()) {
            String altU = env.getProperty("MICROSERVICIOS_DB_USERNAME");
            if (altU == null) altU = System.getenv("MICROSERVICIOS_DB_USERNAME");
            if (altU != null) resolvedUsername = altU;
        }

        if (resolvedPassword == null || resolvedPassword.trim().isEmpty()) {
            String altP = env.getProperty("MICROSERVICIOS_DB_PASSWORD");
            if (altP == null) altP = System.getenv("MICROSERVICIOS_DB_PASSWORD");
            if (altP != null) resolvedPassword = altP;
        }

        if (resolvedUrl == null || resolvedUrl.trim().isEmpty()) {
            throw new IllegalStateException("Database URL is not configured. Set 'MICROSERVICIOS_DB_DATASOURCE_URL' environment variable or configure 'spring.microservicios-datasource.url' in application.properties");
        }

        if (resolvedDriver == null || resolvedDriver.trim().isEmpty()) {
            resolvedDriver = "com.mysql.cj.jdbc.Driver"; // sensible default
        }

        // Log resolved values for debugging (mask password)
        try {
            String maskedUrl = resolvedUrl.replaceAll("(?<=://)([^:/]+)(?=:)", "****");
            // fallback mask if regex didn't match
            if (maskedUrl.equals(resolvedUrl)) {
                maskedUrl = resolvedUrl.replaceAll("(//).*@", "$1****@");
            }
            log.info("Resolved DB URL: {} (masked), username: {}", maskedUrl, resolvedUsername);
        } catch (Exception ex) {
            log.debug("Failed to mask DB URL for logging: {}", ex.getMessage());
        }

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(resolvedUrl);
        dataSource.setUsername(resolvedUsername);
        dataSource.setPassword(resolvedPassword);
        dataSource.setDriverClassName(resolvedDriver);

        dataSource.setMaximumPoolSize(20);
        dataSource.setMinimumIdle(20);
        dataSource.setConnectionTimeout(6000000);
        dataSource.setIdleTimeout(1000000);
        dataSource.setMaxLifetime(20000000);
        dataSource.setAutoCommit(true);

        return dataSource;
    }

    @Bean(name = "microserviciosJdbcTemplate")
    public JdbcTemplate iuventisHealthJdbcTemplate(@Qualifier("microserviciosDataSource") DataSource ds) {
        return new JdbcTemplate(ds);
    }
}
