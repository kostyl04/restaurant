package com.durovich.restaurant_admin.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.durovich.restaurant_admin")
public class DBconfig {
	
	
	@Bean
	public HibernateTemplate sqlServerHibernateTemplate() {
		return new HibernateTemplate(sessionFactory());
	}
	
	

	@Bean
	public SessionFactory sessionFactory() {
		LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
		lsfb.setDataSource(getDataSource());
		lsfb.setPackagesToScan("com.durovich.restaurant_admin.entity");
		lsfb.setHibernateProperties(hibernateProperties());
		try {
			lsfb.afterPropertiesSet();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lsfb.getObject();
	}

	@Bean
	public DataSource getDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11191284?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8");
		dataSource.setUsername("sql11191284");
		dataSource.setPassword("aUSstb5Lss");

		return dataSource;
	}

	@Bean
	public HibernateTransactionManager transactionManager() {

		return new HibernateTransactionManager(sessionFactory());
	}

	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.show_sql", true);
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect");
		properties.put("hibernate.charset", "UTF-8");
		properties.put("hibernate.useunicode", true);
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.format_sql", true);
		return properties;
	}
}
