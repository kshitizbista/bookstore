package com.demo.bookstore.controller;

import com.demo.bookstore.Service.UserService;
import com.demo.bookstore.config.SecurityUtility;
import com.demo.bookstore.domain.User;
import com.demo.bookstore.domain.security.Role;
import com.demo.bookstore.domain.security.UserRole;
import com.demo.bookstore.utility.MailConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailConstructor mailConstructor;

    @Autowired
    private JavaMailSender mailSender;

    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public ResponseEntity newUser(@RequestBody HashMap<String, String> map) {
        String username = map.get("username");
        String email = map.get("email");

        if(userService.findByUsername(username) != null) return new ResponseEntity("usernameExists", HttpStatus.BAD_REQUEST);
        if(userService.findByEmail(email) != null) return new ResponseEntity("emailExists", HttpStatus.BAD_REQUEST);

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);

        String password = SecurityUtility.randomPassword();
        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);

        user.setPassword(encryptedPassword);

        Role role = new Role();
        role.setName("ROLE_USER");
        role.setRoleId(1);

        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(user, role));

        userService.createUser(user, userRoles);
        System.out.println(password);

        SimpleMailMessage mailMessage = mailConstructor.constructEmailForNewUser(user, password);
        mailSender.send(mailMessage);

        return new ResponseEntity("User Added Successfully!", HttpStatus.OK);

    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
    public ResponseEntity forgotPassword(@RequestBody HashMap<String,String> map) {
        User user = userService.findByEmail(map.get("email"));

        if (user == null) return  new ResponseEntity("Email Not Found", HttpStatus.BAD_REQUEST);

        String password = SecurityUtility.randomPassword();
        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);

        user.setPassword(encryptedPassword);
        userService.saveUser(user);

        SimpleMailMessage email = mailConstructor.constructEmailForNewUser(user, password);
        mailSender.send(email);

        return new ResponseEntity("Email Sent", HttpStatus.OK);
    }

    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public ResponseEntity updateUserInfo(@RequestBody HashMap<String, String> map) throws Exception {
        int id = Integer.parseInt(map.get("id"));
        String username = map.get("username");
        String email = map.get("email");
        String firstName = map.get("firstName");
        String lastName = map.get("lastName");
        String currentPassword = map.get("currentPassword");
        String newPassword = map.get("newPassword");

        User currentUser = userService.findById(Long.valueOf(id));

        if (currentUser == null) {
            throw new Exception("User Not Found");
        }

        if(userService.findByUsername(username) != null) {
            if (userService.findByUsername(username).getId() != currentUser.getId()) {
                return new ResponseEntity("Username Not Found!", HttpStatus.BAD_REQUEST);
            }
        }

        if(userService.findByEmail(email) != null) {
            if(userService.findByEmail(email).getId() != currentUser.getId()) {
                return new ResponseEntity("Email not found!", HttpStatus.BAD_REQUEST);
            }
        }

        BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
        String dbPassword = currentUser.getPassword();

        if (currentPassword != null) {
            if (passwordEncoder.matches(currentPassword, dbPassword)) {
                if(newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")) {
                    currentUser.setPassword(passwordEncoder.encode(newPassword));
                }
            }else {
                return new ResponseEntity("Incorrect Current Password!", HttpStatus.BAD_REQUEST);
            }
        }


        currentUser.setFirstName(firstName);
        currentUser.setLastName(lastName);
        currentUser.setUsername(username);
        currentUser.setEmail(email);
        userService.saveUser(currentUser);

        return new ResponseEntity("Update Success", HttpStatus.OK);
    }

    @RequestMapping("/getCurrentUser")
    public User getCurrentUser(Principal principal) {
        System.out.println(principal.getName());
       User user = userService.findByUsername(principal.getName());
       return user;
    }


}
