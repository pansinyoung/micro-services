package com.gcit.lms;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class LMSConfig {
	
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.gcit"))
				.paths(PathSelectors.any()).build();
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan(new String[] { "com.gcit.hbm" });

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);

		return em;
	}

	@Bean(name="transactionManager")
	public PlatformTransactionManager transactionManager(
			EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);

		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	
	
	
	
	
	
	
	
	
//	@Bean
//	public JdbcTemplate template(){
//		return new JdbcTemplate(dataSource());
//	}
//	
//	@Bean
//	public PlatformTransactionManager txManager() {
//		return new DataSourceTransactionManager(dataSource());
//	}
//	
//	@Bean
//	public AllService allService() {
//		return new AllService();
//	}
//	
//	@Bean
//	public AuthorDAO adao(){
//		return new AuthorDAO();
//	}
//	
//	@Bean
//	public BookCopiesDAO bcdao(){
//		return new BookCopiesDAO();
//	}
//	
//	@Bean
//	public BookDAO bdao(){
//		return new BookDAO();
//	}
//
//	@Bean
//	public BranchDAO brdao(){
//		return new BranchDAO();
//	}
//	
//	@Bean
//	public BorrowerDAO bodao(){
//		return new BorrowerDAO();
//	}
//
//	@Bean
//	public LoanDAO ldao(){
//		return new LoanDAO();
//	}
//
//	@Bean
//	public PublisherDAO pdao(){
//		return new PublisherDAO();
//	}
//
//	@Bean
//	public GenreDAO gdao(){
//		return new GenreDAO();
//	}

}
