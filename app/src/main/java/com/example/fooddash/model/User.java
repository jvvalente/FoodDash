package com.example.fooddash.model;

public class User {

    private String email;
    private String password;
    private String address;


    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.address = "";
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
