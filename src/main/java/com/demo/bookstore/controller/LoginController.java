package com.demo.bookstore.controller;

import com.demo.bookstore.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/token")
    public Map<String, String> token(HttpSession session, HttpServletRequest request) {
        String remoteHost = request.getRemoteHost();
        int portNumber = request.getRemotePort();
        String remoteAddr = request.getRemoteAddr();

        System.out.println(remoteHost + ":" + portNumber);
        System.out.println(remoteAddr);

        return Collections.singletonMap("token", session.getId());
    }

    @RequestMapping("/checkSession")
    public ResponseEntity checkSession() {
        return new ResponseEntity("Session Active!", HttpStatus.OK);
    }

    @RequestMapping(value = "/user/logOut", method = RequestMethod.POST)
    public ResponseEntity logOut() {
        SecurityContextHolder.clearContext();
        return new ResponseEntity("Successfully logged Out!", HttpStatus.OK);
    }
}
