package com.example.multipledatasources.mysql.repos;

import com.example.multipledatasources.mysql.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MysqlJPARepository extends JpaRepository<Person, Integer> {
}
