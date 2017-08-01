package com.blch.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: cxdong
 * Date: 5/9/17
 * Time: 11:33 AM
 * To change this template use File | Settings | File Templates.
 */

@Configuration
@EnableTransactionManagement
public class EntityManagerFactoriesAndTransactionConfiguration {

  // Private fields

  @Autowired
  private Environment env;

  @Autowired
  private DataSource dataSource;

  @Autowired
  private LocalContainerEntityManagerFactoryBean entityManagerFactory;

  /**
   * Declare the JPA entity manager factory.
   */
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean entityManagerFactory =
      new LocalContainerEntityManagerFactoryBean();

    entityManagerFactory.setDataSource(dataSource);

    // Classpath scanning of @Component, @Service, etc annotated class
    entityManagerFactory.setPackagesToScan("com.blch");
    System.out.println("spring.jpa.hibernate.ddl-auto: "+env.getProperty("spring.jpa.hibernate.ddl-auto"));

    // Vendor adapter
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    entityManagerFactory.setJpaVendorAdapter(vendorAdapter);

    // Hibernate properties
    Properties additionalProperties = new Properties();
    //additionalProperties.put("hibernate.dialect",env.getProperty("spring.jpa.properties.hibernate.dialect"));
    //additionalProperties.put("hibernate.show_sql",env.getProperty("hibernate.show_sql"));
    additionalProperties.put("hibernate.hbm2ddl.auto",env.getProperty("spring.jpa.hibernate.ddl-auto"));
    additionalProperties.put("hibernate.id.new_generator_mappings",env.getProperty("spring.jpa.hibernate.use-new-id-generator-mappings"));
    entityManagerFactory.setJpaProperties(additionalProperties);

    return entityManagerFactory;
  }

  /**
   * Declare the transaction manager.
   */
  @Bean
  public JpaTransactionManager transactionManager() {
    JpaTransactionManager transactionManager =
      new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(
      entityManagerFactory.getObject());
    return transactionManager;
  }

  /**
   * PersistenceExceptionTranslationPostProcessor is a bean post processor
   * which adds an advisor to any bean annotated with Repository so that any
   * platform-specific exceptions are caught and then rethrown as one
   * Spring's unchecked data access exceptions (i.e. a subclass of
   * DataAccessException).
   */
  @Bean
  public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
    return new PersistenceExceptionTranslationPostProcessor();
  }

}
