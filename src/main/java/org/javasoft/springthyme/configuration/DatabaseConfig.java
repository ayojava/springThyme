/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.springthyme.configuration;

import java.util.Properties;
import javax.sql.DataSource;
import org.javasoft.springthyme.property.DBProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author ayojava
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"org.javasoft.springthyme"})
@EnableJpaRepositories(basePackages = "org.javasoft.springthyme.repository")
public class DatabaseConfig {

    @Autowired
    private DBProperty dbProperty;

    @Bean
    @Profile("localhost")
    public DataSource localHostDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dbProperty.getDriverClassName());
        dataSource.setUrl(dbProperty.getLocalhostUrlPort() + dbProperty.getLocalhostDatabase());
        dataSource.setPassword(dbProperty.getLocalhostPassword());
        dataSource.setUsername(dbProperty.getLocalhostUsername());
        return dataSource;
    }

    @Bean(name = "entityManagerFactory")
    @Profile("localhost")
    public LocalContainerEntityManagerFactoryBean localHostEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan(new String[]{"org.javasoft.springmvc.entity"});
        factoryBean.setDataSource(localHostDataSource());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        factoryBean.setJpaProperties(jpaProperties());
        return factoryBean;
    }

    
    @Bean
    @Profile("openshift")
    public DataSource openshiftDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dbProperty.getDriverClassName());
        //dataSource.setUrl("jdbc:"+dbProperty.getOpenshiftDBUrl()+dbProperty.getOpenshiftDatabase());
        dataSource.setUrl("jdbc:mysql://" + dbProperty.getOpenshiftHost() + ":" + dbProperty.getOpenshiftPort() + "/" + dbProperty.getOpenshiftDatabase());
        dataSource.setPassword(dbProperty.getOpenshiftPassword());
        dataSource.setUsername(dbProperty.getOpenshiftUsername());
        return dataSource;
    }

    @Bean(name = "entityManagerFactory")
    @Profile("openshift")
    public LocalContainerEntityManagerFactoryBean openshiftEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan(new String[]{"org.javasoft.springmvc.entity"});
        factoryBean.setDataSource(openshiftDataSource());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        factoryBean.setJpaProperties(jpaProperties());
        return factoryBean;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.MYSQL);
        return jpaVendorAdapter;
    }

    private Properties jpaProperties() {
        return new Properties() {
            {
                setProperty("javax.persistence.schema-generation.create-database-schemas", "true");
                setProperty("javax.persistence.schema-generation.database.action", "drop-and-create");
                setProperty("hibernate.format_sql", dbProperty.getHibernateFormatSql());
                setProperty("hibernate.dialect", dbProperty.getHibernateDialect());
                setProperty("hibernate.show_sql", dbProperty.getHibernateShowSql());
                setProperty("hibernate.globally_quoted_identifiers", "true");//true
            }
        };
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
