package com.demo.bookstore.repository;

import com.demo.bookstore.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User findAllByEmail(String email);
    List<User> findAll();

}
