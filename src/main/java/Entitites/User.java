/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entitites;

/**
 * Entity class built form database with the information of the user that is logged into the web application.
 * @author Mellem
 */
public class User {
    private String username;
    private String email;
    private String password;
    private int balance;
    private int admin;

    public User(String username, String email, String password, int balance, int admin) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "User{" + "username=" + username + ", email=" + email + ", password=" + password + ", balance=" + balance + '}';
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

    public int getBalance() {
        return balance;
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

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getAdmin() {
        return admin;
    }
}
