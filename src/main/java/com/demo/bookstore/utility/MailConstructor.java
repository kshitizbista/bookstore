package com.demo.bookstore.utility;

import com.demo.bookstore.domain.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class MailConstructor {

    public SimpleMailMessage constructEmailForNewUser(User user, String password) {
        String message = "\nPlease use the following credentials to log in and edit your personal information" +
                "\nUsername:" + user.getUsername() + "\nPassword:" + password;
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Le's BookStore - New User");
        email.setText(message);

        return email;
    }
}
