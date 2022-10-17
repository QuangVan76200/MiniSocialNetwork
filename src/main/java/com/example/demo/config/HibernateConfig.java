//package com.example.demo.config;
//
//import java.util.Properties;
//
//import javax.sql.DataSource;
//
//import org.hibernate.cfg.AvailableSettings;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.env.Environment;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.orm.hibernate5.HibernateTransactionManager;
//import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
//
//@Configuration
//@PropertySource("classpath:database.properties")
//public class HibernateConfig {
//	@Autowired
//	private Environment env;
//	
//	private String secretKey = "Vv@76200";
//	
//	
//	@Bean(name="entityManagerFactory")
//	public LocalSessionFactoryBean getSessionFactory() {
//		LocalSessionFactoryBean b = new LocalSessionFactoryBean();
//		b.setPackagesToScan("com.example.demo.model");
//		b.setDataSource(dataSource());
//		b.setHibernateProperties(hibernateProperties());
//		return b;
//	}
//	
//	@Bean
//	@ConfigurationProperties(prefix="ds.datasource")
//	public DataSource dataSource() {
//	    DriverManagerDataSource d= new DriverManagerDataSource();
//	    d.setDriverClassName(env.getProperty("ds.datasource.driver-class-name"));
//	    d.setUrl(env.getProperty("ds.datasource.url"));
//	    d.setUsername(env.getProperty("ds.datasource.username"));
//	    d.setPassword(env.getProperty("ds.datasource.password"));
//	    return d;
//	}
//	
//	@Bean 
//	public Properties hibernateProperties() {
//		Properties props= new Properties();
//		props.put(AvailableSettings.SHOW_SQL, env.getProperty("spring.jpa.show-sql"));
//		props.put(AvailableSettings.DIALECT, env.getProperty("spring.hibernate.dialect"));
//		
//		return props;
//	}
//	@Bean(name="transactionManager")
//	public HibernateTransactionManager hibernateTransactionManager() {
//		HibernateTransactionManager h= new HibernateTransactionManager();
//		h.setSessionFactory(this.getSessionFactory().getObject());
//		return h;
//	}
//}
