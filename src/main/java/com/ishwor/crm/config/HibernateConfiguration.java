package com.ishwor.crm.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

//@Configuration
//@PropertySource("classpath:hibernate.properties")
//@EnableTransactionManagement
public class HibernateConfiguration {
	public static Logger logger = Logger.getLogger(FrontController.class);

	@Autowired
	Environment environment;

	// hibernate configuration
	@Bean
	public LocalSessionFactoryBean sessionBean() {
		logger.info("Creating sesion bean");
		LocalSessionFactoryBean bean = new LocalSessionFactoryBean();

		bean.setDataSource(dataSource());
		bean.setPackagesToScan(new String[] { "com.ishwor.crm.entity", "com.ishwor.crm.DAO","com.ishwor.crm.DAOImpl" });
		bean.setHibernateProperties(hibernateProperties());
		logger.info("Creating sesion bean=== successfull!!!");
		return bean;
	}

	@Bean
	public DataSource dataSource() {

		logger.info("Datasource method invoked");

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
		dataSource.setUsername(environment.getRequiredProperty("jdbc.password"));
		dataSource.setUsername(environment.getRequiredProperty("jdbc.url"));

		logger.info("DataSource Invoke complete");

		return dataSource;
	}

	private Properties hibernateProperties() {
		Properties properties = new Properties();
		logger.info("setting properties for hibernated");
		properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
		properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
		properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
		properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
		logger.info("setting properties completed");
		return properties;
	}

	@Bean
	public HibernateTransactionManager getTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionBean().getObject());
		return transactionManager;
	}
}
