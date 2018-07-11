package au.com.lifebio.lifebiocontactdetails;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@SpringBootApplication
public class LifeBioContactDetailsApplication {

//    @Value("${test.jdbc.driverClassName}")
//    String testDriverClassName;
//    @Value("${test.jdbc.url}")
//    String testURL;
//    @Value("${test.jdbc.username}")
//    String testUsername;
//    @Value("${test.jdbc.password}")
//    String testPassword;
//    @Value("${test.jdbc.hibernate.hbm2ddl.auto}")
//    String testSchemaPolicy;
//    @Value("${test.jdbc.org.hibernate.dialect}")
//    String testDialect;

	public static void main(String[] args) {
		SpringApplication.run(LifeBioContactDetailsApplication.class, args);
	}


//    @Bean
//    @Primary
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
//        entityManagerFactoryBean.setDataSource(dataSource());
//        entityManagerFactoryBean.setPackagesToScan(new String[] { "au.com.lifebio.lifebiocontactdetails.contact.model",
//                "au.com.lifebio.lifebiocontactdetails.contact.dao"});
//
//        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
//        entityManagerFactoryBean.setJpaProperties(testJPAProperties());
//
//        return entityManagerFactoryBean;
//    }
//
//    @Bean
//    public DataSource dataSource(){
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(testDriverClassName);
//        dataSource.setUrl(testURL);
//        dataSource.setUsername(testUsername);
//        dataSource.setPassword(testPassword);
//
//        return dataSource;
//    }
//
//    @Bean
//    @Primary
//    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactory);
//
//        return transactionManager;
//    }
//
//    @Bean
//    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
//        return new PersistenceExceptionTranslationPostProcessor();
//    }
//
//
//    Properties testJPAProperties() {
//        Properties properties = new Properties();
//        properties.setProperty("hibernate.hbm2ddl.auto", testSchemaPolicy);
//        properties.setProperty("org.hibernate.dialect", testDialect);
//
//        return properties;
//    }

}
