package com.demo.bookstore.Service.impl;

import com.demo.bookstore.Service.UserService;
import com.demo.bookstore.domain.User;
import com.demo.bookstore.domain.security.UserRole;
import com.demo.bookstore.repository.RoleRepository;
import com.demo.bookstore.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public User createUser(User user, Set<UserRole> userRoles) {
        User localUser = findByUsername(user.getUsername());
        if(localUser != null) {
            LOG.info("User with username {} already exists. Nothing will be done.", user.getUsername());
        } else {
            userRoles.forEach( ur -> {
                roleRepository.save(ur.getRole());
            });
            user.getUserRole().addAll(userRoles);
            localUser = saveUser(user);
        }

        return localUser;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findAllByEmail(email);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findOne(id);
    }
}
