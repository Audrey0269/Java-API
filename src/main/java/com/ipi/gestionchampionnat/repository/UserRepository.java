package com.ipi.gestionchampionnat.repository;

import com.ipi.gestionchampionnat.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    @Override
    List<User> findAll();

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
    List<User> getUserFromEmailAndPassword(String email, String password);
}
