/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.springthyme.property;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 *
 * @author ayojava
 */
@Getter
@Configuration
@PropertySource(value = {"classpath:config/databaseConfig.properties"})
public class DBProperty {

    //@Value("${openshift.database}")
    @Value("#{systemEnvironment['OPENSHIFT_APP_NAME']}")
    private String openshiftDatabase;

    //@Value("${openshift.database}")
    @Value("#{systemEnvironment['OPENSHIFT_MYSQL_DB_USERNAME']}")
    private String openshiftUsername;

    //@Value("${openshift.database}")
    @Value("#{systemEnvironment['OPENSHIFT_MYSQL_DB_PASSWORD']}")
    private String openshiftPassword;

    @Value("#{systemEnvironment['OPENSHIFT_MYSQL_DB_HOST']}")
    private String openshiftHost;

    @Value("#{systemEnvironment['OPENSHIFT_MYSQL_DB_PORT']}")
    private String openshiftPort;

    @Value("#{systemEnvironment['OPENSHIFT_MYSQL_DB_URL']}")
    private String openshiftDBUrl;

    @Value("${localhost.database}")
    private String localhostDatabase;

    @Value("${localhost.username}")
    private String localhostUsername;

    @Value("${localhost.password}")
    private String localhostPassword;

    @Value("${localhost.urlAndPort}")
    private String localhostUrlPort;

    @Value("${mysql.driverClassName}")
    private String driverClassName;

    @Value("${hibernate.dialect}")
    private String hibernateDialect;

    @Value("${hibernate.show_sql}")
    private String hibernateShowSql;

    @Value("${hibernate.format_sql}")
    private String hibernateFormatSql;

    @Value("${hibernate.hbm2ddl.auto}")
    private String hibernate_hbm2ddl_auto;

    @Value("${active.profile}")
    private String activeProfile;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
