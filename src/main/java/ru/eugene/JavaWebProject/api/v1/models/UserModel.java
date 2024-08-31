package ru.eugene.JavaWebProject.api.v1.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", length = 64)
    private String id;
    @Column(name = "username", length = 64)
    private String username;
    @Column(name = "email", length = 120)
    private String email;
    @Column(name = "password_hash", length = 256)
    private String password_hash;
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @PrePersist
    public void Init() {
        this.setTimestamp(LocalDateTime.now());
    }

    public UserModel(String username, String email, String password) {
        this.setUsername(username);
        this.setEmail(email);
        this.setPasswordHash(password);
    }

    public UserModel(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return password_hash;
    }

    public void setPasswordHash(String password) {
        this.password_hash = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
