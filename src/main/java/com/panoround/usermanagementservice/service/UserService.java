package com.panoround.usermanagementservice.service;

import com.panoround.usermanagementservice.entity.User;
import com.panoround.usermanagementservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User registerUser(User user){
        // Encrypt password before storing it in the DB
        user.setEncryptedPassword(passwordEncoder.encode(user.getEncryptedPassword()));

        // Generate an OTP for email verification
        String verificationCode = generateVerificationCode();

        // Send verificationCode email
        emailService.sendVerificationEmail(user.getEmail(), verificationCode);
        user.setVerificationCode(verificationCode);

        // Save the user to the DB
        return userRepository.save(user);
    }

    private String generateVerificationCode() {
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase(); // can be complex in the future
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public boolean checkPassword(User user, String rawPassword){
        return passwordEncoder.matches(rawPassword,user.getEncryptedPassword());
    }

    public Optional<User> login(String username, String password){
        Optional<User> optionalUser = findByUsername(username);
        if(optionalUser.isPresent() && checkPassword(optionalUser.get(),password)){
            return optionalUser;
        }
        return Optional.empty();
    }

    public boolean verifyCode(String username, String verificationCode) {
        Optional<User> optionalUser = findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getVerificationCode().equals(verificationCode)) {
                user.setEmailVerified(true);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

}
