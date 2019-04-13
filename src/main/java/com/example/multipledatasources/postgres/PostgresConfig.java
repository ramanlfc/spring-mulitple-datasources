package com.example.multipledatasources.postgres;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
//@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.example.multipledatasources.postgres.repos"},
        transactionManagerRef = "postgresTransactionManager",
        entityManagerFactoryRef = "postgresLocalContainerEntityManagerFactoryBean")
public class PostgresConfig {


    @Bean
    public DataSource postgresDataSource(){
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:postgresql://localhost:5432/userdb");
        ds.setUsername("raman");
        ds.setPassword("jdbc");
        return ds;
    }

    @Bean
    public JpaVendorAdapter postgresJpaVendorAdapter(){
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setDatabase(Database.POSTGRESQL);
        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQL9Dialect");
        return hibernateJpaVendorAdapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean postgresLocalContainerEntityManagerFactoryBean(JpaVendorAdapter postgresJpaVendorAdapter, DataSource postgresDataSource){
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean =  new LocalContainerEntityManagerFactoryBean();
        Properties props = new Properties();
        props.put("hibernate.jdbc.lob.non_contextual_creation", "true");
        localContainerEntityManagerFactoryBean.setJpaProperties(props);
        localContainerEntityManagerFactoryBean.setDataSource(postgresDataSource);
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(postgresJpaVendorAdapter);
        localContainerEntityManagerFactoryBean.setPackagesToScan("com.example.multipledatasources.postgres");
        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager postgresTransactionManager(JpaVendorAdapter postgresJpaVendorAdapter, DataSource postgresDataSource) {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(postgresLocalContainerEntityManagerFactoryBean(postgresJpaVendorAdapter,postgresDataSource).getObject());
        return tm;
    }


}
