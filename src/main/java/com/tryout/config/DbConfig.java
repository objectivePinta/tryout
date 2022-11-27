package com.tryout.config;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;


@Configuration
@EnableJpaRepositories(basePackages = "com.tryout.repository",
                       entityManagerFactoryRef = "writingEntityManagerFactory",
                       transactionManagerRef = "writingTransactionManager"
)
@EnableTransactionManagement
public class DbConfig {

  public static final String ENTITIES_PACKAGES = "com.tryout.entities";

  public static final String PERSISTENCE_UNIT = "writing";


  @Primary
  @Bean(name = "writingDataSource")
  public DataSource writingDataSource (
      @Qualifier("writingHikariConfig") HikariConfig writingHikariConfig) {

    return new HikariDataSource(writingHikariConfig);
  }


  @Bean(name = "writingHikariConfig")
  @ConfigurationProperties(prefix = "datasource.writing.hikari")
  @Primary
  public HikariConfig writingHikariConfig () {

    return new HikariConfig();
  }


  @Primary
  @Bean(name = "writingEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean writingEntityManagerFactory (
      EntityManagerFactoryBuilder builder,
      @Qualifier("writingDataSource") DataSource writingDataSource
  ) {

    return builder
        .dataSource(writingDataSource)
        .packages(ENTITIES_PACKAGES)
        .persistenceUnit(PERSISTENCE_UNIT)
        .build();
  }


  @Primary
  @Bean(name = "writingTransactionManager")
  public PlatformTransactionManager writingTransactionManager (
      @Qualifier("writingEntityManagerFactory") EntityManagerFactory writingEntityManagerFactory
  ) {

    return new JpaTransactionManager(writingEntityManagerFactory);
  }

}
