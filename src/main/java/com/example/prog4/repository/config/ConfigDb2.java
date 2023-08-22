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
@RequiredArgsConstructor
@EnableTransactionManagement
@EnableJpaRepositories(
        transactionManagerRef = "db2TransactionManager",
        entityManagerFactoryRef = "db2EntityManagerFactory",
        basePackages = {"com.example.prog4.repository.db2",
        }
)

public class ConfigDb2 {

    private final Environment env;

    @Bean(initMethod = "migrate")
    @ConfigurationProperties(prefix = "spring.flyway.db2")
    public Flyway flywayDb2() {
        return new Flyway(
                Flyway.configure()
                        .baselineOnMigrate(true)
                        .locations("classpath:/db/migration/db2")
                        .dataSource(
                                env.getRequiredProperty("spring.second-datasource.url"),
                                env.getRequiredProperty("spring.second-datasource.username"),
                                env.getRequiredProperty("spring.second-datasource.password")
                        )
        );
    }

    @Bean(name = "db2Datasource")
    @ConfigurationProperties(prefix = "spring.second-datasource")
    public DataSource db2DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "db2EntityManagerFactoryBuilder")
    public EntityManagerFactoryBuilder db2EntityManagerFactoryBuilder(
            @Qualifier("db2Datasource") DataSource dataSource
    ) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.url", env.getRequiredProperty("spring.second-datasource.url"));
        properties.put("javax.persistence.jdbc.user", env.getRequiredProperty("spring.second-datasource.username"));
        properties.put("javax.persistence.jdbc.password", env.getRequiredProperty("spring.second-datasource.password"));

        return new EntityManagerFactoryBuilder(
                new HibernateJpaVendorAdapter(),
                properties,
                null
        );
    }

    @Bean(name = "db2EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean db2EntityManagerFactory(
            @Qualifier("db2EntityManagerFactoryBuilder") final EntityManagerFactoryBuilder builder,
            @Qualifier("db2Datasource") final DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages("com.example.prog4.repository.db2.entity")
                .build();
    }

    @Bean(name = "db2TransactionManager")
    public PlatformTransactionManager db2PlatformTransactionManager(
            @Qualifier("db2EntityManagerFactory") final EntityManagerFactory db2EntityManagerFactory
    ) {
        return new JpaTransactionManager(db2EntityManagerFactory);
    }
}
