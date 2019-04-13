package com.example.multipledatasources.postgres.repos;

import com.example.multipledatasources.postgres.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostgresUserRepository extends JpaRepository<User, Integer> {
}
