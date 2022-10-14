package com.example.campos.repository.interfaces;

import com.example.campos.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByUsername(String username);
}
