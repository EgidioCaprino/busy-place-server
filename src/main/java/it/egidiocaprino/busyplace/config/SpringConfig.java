package it.egidiocaprino.busyplace.config;

import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"it.egidiocaprino.busyplace"})
public class SpringConfig {

    @Autowired
    Environment environment;

    @Bean
    Environment getEnvironment() {
        return environment;
    }

    @Bean(destroyMethod = "close")
    DataSource dataSource() {
        PGPoolingDataSource dataSource = new PGPoolingDataSource();
        dataSource.setServerName(environment.getRequiredProperty("db.host"));
        dataSource.setDatabaseName(environment.getRequiredProperty("db.database"));
        dataSource.setPortNumber(environment.getRequiredProperty("db.port", Integer.class));
        dataSource.setUser(environment.getRequiredProperty("db.username"));
        dataSource.setPassword(environment.getRequiredProperty("db.password"));
        dataSource.setSsl(environment.getRequiredProperty("db.ssl", Boolean.class));
        dataSource.setSslfactory(environment.getRequiredProperty("db.sslFactory"));
        dataSource.setInitialConnections(environment.getRequiredProperty("db.startPoolSize", Integer.class));
        dataSource.setMaxConnections(environment.getRequiredProperty("db.maxPoolSize", Integer.class));
        return dataSource;
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setPackagesToScan("it.egidiocaprino.busyplace");

        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        jpaProperties.setProperty("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        jpaProperties.setProperty("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        factory.setJpaProperties(jpaProperties);

        return factory;
    }

    @Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

}
