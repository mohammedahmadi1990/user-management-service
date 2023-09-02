package com.panoround.usermanagementservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {

    private String id;
    private String username;
    private String email;
    private String encryptedPassword;
    private boolean emailVerified;
    private String verificationCode;

    public User(String username, String email, String encryptedPassword) {
        this.username = username;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", encryptedPassword='" + encryptedPassword + '\'' +
                ", emailVerified=" + emailVerified +
                '}';
    }

}
