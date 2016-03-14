package com.corinthgames.lastgalaxy.config;

import java.util.Properties;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManagerFactory;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Configures Hibernate, including hibernate properties (usually in a persistence.xml file)
 *
 * @author NWESTL
 *
 */
@Configuration
@EnableTransactionManagement
public class HibernateConfig {

	public static final int DEFAULT_BATCH_SIZE = 100;

	/**
	 * these values should never be changed except through the properties file
	 */
	@Value(value = "${schema-export-type.property}")
	private String SCHEMA_EXPORT_TYPE;
	@Value(value = "${database-access.jdbc-url}")
	private String DB_ACCESS_JDBC_URL;
	@Value(value = "${database-access.username}")
	private String DATABASE_USERNAME;
	@Value(value = "${database-access.password}")
	private String DATABASE_PASSWORD;
	@Value(value = "${database-access.max-pool-size}")
	private int DB_MAX_POOL_SIZE;
	@Value(value = "${hibernate-show-sql}")
	private boolean HIBERNATE_SHOW_SQL;

	/**
	 * Not currently used, but should be used if any JPA queries need to be executed outside of Spring Data Repos
	 */
	public static final String PERSISTENCE_UNIT_NAME = "LastGalaxyPersistence";
	private static final String DRIVER_CLASS_NAME = "org.postgresql.Driver";
	private HikariDataSource dataSource = null;

	/**
	 * Clean up the datasource
	 */
	@PreDestroy
	public void cleanUpDataSource() {
		if (dataSource != null) {
			dataSource.shutdown();
		}
	}

	/**
	 * Sets up a standard entity manager
	 *
	 * @return
	 */
	@Bean
	public EntityManagerFactory entityManagerFactory() {

		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory.setPackagesToScan(new String[] { SpringConfig.BASE_PACKAGE });
		entityManagerFactory.setPersistenceProvider(new HibernatePersistenceProvider());
		entityManagerFactory.setJpaProperties(hibernateProperties());
		entityManagerFactory.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
		entityManagerFactory.afterPropertiesSet();
		return entityManagerFactory.getObject();
	}

	/**
	 * sets up standard hibernate exception translator
	 *
	 * @return
	 */
	@Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator(){
      return new HibernateExceptionTranslator();
    }

	/**
	 * Sets up standard jpa transaction manager
	 * @return
	 */
	@Bean
	public PlatformTransactionManager transactionManager(HikariDataSource dataSource) {
		JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory());
		transactionManager.setDataSource(dataSource);
		transactionManager.setJpaDialect(new HibernateJpaDialect());
		return transactionManager;
	}

	/**
	 * sets some hibernate properties (what would exist in the JPA
	 * persistence.xml)
	 *
	 * @return
	 */
	@Bean
	public Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.hbm2ddl.auto", SCHEMA_EXPORT_TYPE);
		properties.put("hibernate.default_batch_fetch_size", DEFAULT_BATCH_SIZE);
		properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		properties.put("show_sql", HIBERNATE_SHOW_SQL);
		return properties;
	}

	/**
	 * makes the datasource for hibernate, sets up things like the database url,
	 * password, and username
	 *
	 * @return
	 */
	@Bean
	public HikariDataSource dataSource() {

		HikariConfig config = new HikariConfig();

		config.setMaximumPoolSize(DB_MAX_POOL_SIZE);
		config.setDriverClassName(DRIVER_CLASS_NAME);
		config.setJdbcUrl(DB_ACCESS_JDBC_URL);
		config.setPassword(DATABASE_PASSWORD);
		config.setUsername(DATABASE_USERNAME);

		dataSource = new HikariDataSource(config);
		return dataSource;
	}

}
