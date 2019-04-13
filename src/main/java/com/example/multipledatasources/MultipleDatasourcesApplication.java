package com.example.multipledatasources;

import com.example.multipledatasources.mysql.Person;
import com.example.multipledatasources.mysql.repos.MysqlJPARepository;
import com.example.multipledatasources.postgres.User;
import com.example.multipledatasources.postgres.repos.PostgresUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sql.DataSource;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MultipleDatasourcesApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MultipleDatasourcesApplication.class, args);
        MysqlJPARepository mysqlJPARepository = context.getBean(MysqlJPARepository.class);

        Person p = new Person();
        p.setName("John doe");

        mysqlJPARepository.save(p);

        mysqlJPARepository.findAll().stream().forEach(person -> {
            System.out.println(person);
        });

        PostgresUserRepository postgresUserRepository = context.getBean(PostgresUserRepository.class);
        User user =  new User();
        user.setEmail("peter@abc.com");

        postgresUserRepository.save(user);

        postgresUserRepository.findAll().stream().forEach(u ->{
            System.out.println(u);
        });

    }

}
