package com.example.prog4.repository.config;


import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@RequiredArgsConstructor
@EnableJpaRepositories(
        transactionManagerRef = "db1TransactionManager",
        entityManagerFactoryRef = "db1EntityManagerFactory",
        basePackages = {"com.example.prog4.repository.db1"}
)
public class ConfigDb1 {

    private final Environment env;

    // Configuration de Flyway pour la migration de la base de données db1
    @Bean(initMethod = "migrate")
    @ConfigurationProperties(prefix = "spring.flyway.db1")
    public Flyway flywayDb1() {
        return new Flyway(
                Flyway.configure()
                        .baselineOnMigrate(true)
                        .locations("classpath:/db/migration/db1")
                        .dataSource(
                                env.getRequiredProperty("spring.datasource.url"),
                                env.getRequiredProperty("spring.datasource.username"),
                                env.getRequiredProperty("spring.datasource.password")
                        )
        );
    }

    // Configuration du DataSource pour la base de données db1
    @Bean(name = "db1Datasource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource db1DataSource() {
        return DataSourceBuilder.create().build();
    }

    // Configuration de l'EntityManagerFactory pour la base de données db1
    @Bean(name = "db1EntityManagerFactoryBuilder")
    public EntityManagerFactoryBuilder db1EntityManagerFactoryBuilder(
            @Qualifier("db1Datasource") DataSource dataSource
    ) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.url", env.getRequiredProperty("spring.datasource.url"));
        properties.put("javax.persistence.jdbc.user", env.getRequiredProperty("spring.datasource.username"));
        properties.put("javax.persistence.jdbc.password", env.getRequiredProperty("spring.datasource.password"));

        return new EntityManagerFactoryBuilder(
                new HibernateJpaVendorAdapter(),
                properties,
                null
        );
    }

    // Configuration de l'EntityManagerFactory pour la base de données db1
    @Primary
    @Bean(name = "db1EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean db1EntityManagerFactory(
            @Qualifier("db1EntityManagerFactoryBuilder") final EntityManagerFactoryBuilder builder,
            @Qualifier("db1Datasource") final DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages("com.example.prog4.repository.db1.entity")
                .build();
    }

    // Configuration du gestionnaire de transactions pour la base de données db1
    @Primary
    @Bean(name = "db1TransactionManager")
    public PlatformTransactionManager db1PlatformTransactionManager(
            @Qualifier("db1EntityManagerFactory") final EntityManagerFactory db1EntityManagerFactory
    ) {
        return new JpaTransactionManager(db1EntityManagerFactory);
    }
}
