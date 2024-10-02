package com.security.user.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity(name = "user_registration")
public class User {

    @Id
    String id;
    String name;
    String userName;
    String password;
    String otp;
    boolean isActive;
    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }

}
