package com.example.multipledatasources.mysql;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
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

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
//@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.example.multipledatasources.mysql.repos"},
        transactionManagerRef = "mysqlTransactionManager"
        ,entityManagerFactoryRef = "mysqlLocalContainerEntityManagerFactoryBean")
public class MysqlConfig {

    @Bean
    public DataSource mysqlDataSource(){
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/persondb");
        ds.setUsername("root");
        ds.setPassword("jdbc");
        return ds;
    }

    @Bean
    public JpaVendorAdapter mysqlJpaVendorAdapter(){
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5InnoDBDialect");
        return hibernateJpaVendorAdapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean mysqlLocalContainerEntityManagerFactoryBean(JpaVendorAdapter mysqlJpaVendorAdapter, DataSource mysqlDataSource){
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean =  new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(mysqlDataSource);
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(mysqlJpaVendorAdapter);
        localContainerEntityManagerFactoryBean.setPackagesToScan("com.example.multipledatasources.mysql");
        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager mysqlTransactionManager(JpaVendorAdapter mysqlJpaVendorAdapter, DataSource mysqlDataSource) {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(mysqlLocalContainerEntityManagerFactoryBean(mysqlJpaVendorAdapter,mysqlDataSource).getObject());
        return tm;
    }

}
