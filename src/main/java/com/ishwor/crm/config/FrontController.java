package com.ishwor.crm.config;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.CacheControl;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan({ "com.ishwor.crm.controller,com.ishwor.crm.entity,com.ishwor.crm.DAO,com.ishwor.crm.DAOImpl" })
@PropertySource("classpath:hibenrate.properties")
@EnableTransactionManagement
public class FrontController implements WebMvcConfigurer {
	public static Logger logger = Logger.getLogger(FrontController.class);

	@Autowired
	Environment environment;

	@Bean
	InternalResourceViewResolver internalResourceViewResolver() {
		logger.info("inside view resolver function ");
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);
		logger.info("View Resolving complete ");
		return resolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		logger.info("Inside static resource handler");
		// for css and js
		registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/resources/")
				.setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
		logger.info("static resource handling complete");
	}

	// hibernate configuration
	@Bean(name = "sessionFactory")
	public LocalSessionFactoryBean sessionBean() {
		logger.info("Creating sesion bean");
		LocalSessionFactoryBean bean = new LocalSessionFactoryBean();

		bean.setDataSource(dataSource());
		bean.setPackagesToScan(
				new String[] { "com.ishwor.crm.entity", "com.ishwor.crm.DAO", "com.ishwor.crm.DAOImpl" });
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
		dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
		dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));

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
