package com.demo.bookstore;

import com.demo.bookstore.Service.UserService;
import com.demo.bookstore.config.SecurityUtility;
import com.demo.bookstore.domain.User;
import com.demo.bookstore.domain.security.Role;
import com.demo.bookstore.domain.security.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class BookstoreApplication implements CommandLineRunner{

    @Autowired
    UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception { 
	    User user1 = new User();
		user1.setFirstName("sushma");
		user1.setLastName("giri");
		user1.setUsername("sushma");
		user1.setPassword(SecurityUtility.passwordEncoder().encode("sushma"));
		user1.setEmail("sushma.n@gmail.com");
		user1.setPhone("9840070734");
        Set<UserRole> userRoles = new HashSet<>();
        Role role1 = new Role();
        role1.setRoleId(1);
        role1.setName("ROLE_USER");

        userRoles.add(new UserRole(user1,role1));
        user1.setUserRole(userRoles);
        userService.createUser(user1, userRoles);
        
        userRoles.clear();

        User user2 = new User();
        user2.setFirstName("kshitiz");
        user2.setLastName("bista");
        user2.setUsername("kbista");
        user2.setPassword(SecurityUtility.passwordEncoder().encode("kbista"));
        user2.setEmail("kbista.n@gmail.com");
        user2.setPhone("9840070734");
        Role role2 = new Role();
        role2.setRoleId(0);
        role2.setName("ROLE_ADMIN");
        userRoles.add(new UserRole(user2,role2));

        userService.createUser(user2, userRoles);
	}

}
