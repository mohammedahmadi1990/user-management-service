package com.panoround.usermanagementservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${email.from}")
    private String fromEmail;

    @Value("${email.verification.subject}")
    private String verificationSubject;

    /**
     * Sends a verification email to the given email address.
     *
     * @param toEmail     the recipient's email address
     * @param verificationCode the code for email verification
     */
    public void sendVerificationEmail(String toEmail, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject(verificationSubject);
        message.setText("Your verification code is: " + verificationCode);
        emailSender.send(message);
    }

    /**
     * Sends a generic email.
     *
     * @param toEmail   the recipient's email address
     * @param subject   the subject of the email
     * @param body      the body of the email
     */
    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        emailSender.send(message);
    }
}
