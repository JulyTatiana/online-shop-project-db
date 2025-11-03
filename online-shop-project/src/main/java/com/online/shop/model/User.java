package com.online.shop.model;

public class User {
    private int userId;
    private String username;
    private String email;
    private String password;

    // ✅ Private constructor that accepts the builder
    private User(UserBuilder builder) {
        this.userId = builder.id;
        this.username = builder.name;
        this.email = builder.email;
        this.password = builder.password;
    }

    // Optional default constructor if you still need it
    public User() {}

    // ✅ Inner static builder class
    public static class UserBuilder {
        private int id;
        private String name;
        private String email;
        private String password;

        public UserBuilder id(int id) {
            this.id = id;
            return this;
        }

        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    // Getters
    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Setters (optional if you want immutability, you can remove them)
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
