package com.demo.bookstore.repository;

import com.demo.bookstore.domain.security.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {

}
