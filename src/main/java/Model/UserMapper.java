/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Entitites.User;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Mapper class to create new users and get existing users from the database
 * @author Mellem
 */
public class UserMapper {
    Connection conn = null;

    public UserMapper() {
        try {
            conn = DBConnector.getConnection();
        } catch (Exception ex) {
            Logger.getLogger(UserMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * get a user when logged in and creates a User object.
     * @param username valid username from jsp site
     * @return User object for further use in web app
     * @throws Exception if Connection fails, or something goes wrong with
     * fetching the data form database
     */
    public User getUser(String username) throws Exception {
        User user = null;

        String sql = "SELECT * FROM User WHERE username = ?";
        PreparedStatement userPstmt = conn.prepareStatement(sql);
        userPstmt.setString(1, username);
        ResultSet rs = userPstmt.executeQuery();

        if (rs.next()) {
            String uname = rs.getString("username");
            String password = rs.getString("password");
            String email = rs.getString("email");
            int balance = rs.getInt("balance");
            int admin = rs.getInt("admin");

            user = new User(uname, email, password, balance, admin);
        }

        return user;
    }

    /**
     * Creates a new user in the database
     * @param username 
     * @param password
     * @param email
     * @throws Exception if Connection fails, or something goes wrong with
     * fetching the data form database 
     */
    public void addUser(String username, String password, String email) throws Exception {

        String sql = "INSERT INTO User VALUES( ?, ?, 1000, ?, ?);";
        PreparedStatement userPstmt = conn.prepareStatement(sql);
        userPstmt.setString(1, username);
        userPstmt.setString(2, password);
        userPstmt.setString(3, email);
        userPstmt.setInt(4, 0);
        userPstmt.executeUpdate();
    }
}
