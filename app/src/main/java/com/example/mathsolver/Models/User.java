package com.example.mathsolver.Models;

import java.util.Date;

public class User {
    public String email, pass, name, right, wrong, rating;

    public User() {
    }

    public User(String email, String name, String pass, String right, String wrong, String rating) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.right = right;
        this.wrong = wrong;
        this.rating = rating;
    }

    public void setRight(String right) {
        this.right = right;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setWrong(String wrong) {
        this.wrong = wrong;
    }

    public String getRight() {
        return right;
    }

    public String getWrong() {
        return wrong;
    }

    public String getName() {
        return name;
    }

    public String getRating() {
        return rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}
