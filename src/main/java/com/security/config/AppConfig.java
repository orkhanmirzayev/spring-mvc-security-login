package com.security.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.security" })
@EnableTransactionManagement
//@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableJpaRepositories(basePackages= {"com.security.repository"})
@PropertySource(value= {"classpath:application.properties"})
public class AppConfig extends WebMvcConfigurerAdapter {

	@Value("${spring.datasource.url}")
	private String datasourceUrl;

	@Value("${spring.datasource.driverClassName}")
	private String dbDriverClassName;

	@Value("${spring.datasource.username}")
	private String dbUsername;

	@Value("${spring.datasource.password}")
	private String dbPassword;

	@Bean
	public DataSource dataSource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(dbDriverClassName);
		dataSource.setUrl(datasourceUrl);
		dataSource.setUsername(dbUsername);
		dataSource.setPassword(dbPassword);
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
	
	@Bean
	public EntityManagerFactory entityManagerFactory(PersistenceUnitManager persistenceUnitManager) {
		final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		factoryBean.setPersistenceUnitManager(persistenceUnitManager);
		final Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("hibernate.shoq_sql", "true");
		factoryBean.setJpaProperties(properties);
		factoryBean.afterPropertiesSet();
		return factoryBean.getNativeEntityManagerFactory();
	}
	
	@Bean
	public PersistenceUnitManager persistenceUnitManager(DataSource dataSource) {
		final DefaultPersistenceUnitManager unitManager = new DefaultPersistenceUnitManager();
		unitManager.setDefaultDataSource(dataSource);
		unitManager.setPackagesToScan("com.security.model");
		return unitManager;
	}
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		final InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/view/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

}
