package com.demo.bookstore.Service;

import com.demo.bookstore.domain.User;
import com.demo.bookstore.domain.security.UserRole;

import java.util.Set;

public interface UserService {

    User createUser(User user, Set<UserRole> userRoles);

    User saveUser(User user);

    User findByUsername(String username);

    User findByEmail(String email);

    User findById(Long id);
}
