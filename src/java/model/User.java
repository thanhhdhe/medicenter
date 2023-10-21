/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;


/**
 *
 * @author pc
 */
public class User {
    private int userID;
    private String address;
    private String email;
    private String password; 
    private String firstName;
    private String lastName;
    private String gender;
    private String phoneNumber;
    private String profileImage; 
    private boolean status;
    private Date createdDate;
    private String role;

    public User() {
    }

    public User(int userID, String address, String email, String password, String firstName, String lastName, String gender, String phoneNumber, String profileImage, boolean status, Date createdDate) {
        this.userID = userID;
        this.address = address;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
        this.status = status;
        this.createdDate = createdDate;
    }

    public User(int userID, String address, String email, String password, String firstName, String lastName, String gender, String phoneNumber, String profileImage, Date createdDate) {
        this.userID = userID;
        this.address = address;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
        this.createdDate = createdDate;
    }

    public User(int userID, String email, String firstName, String gender, String phoneNumber, String profileImage, boolean status, String role) {
        this.userID = userID;
        this.email = email;
        this.firstName = firstName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
        this.status = status;
        this.role = role;
    }

    public User(String address, String email, String password, String firstName, String lastName, String gender, String phoneNumber, String profileImage, boolean status, Date createdDate) {
        this.address = address;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
        this.status = status;
        this.createdDate = createdDate;
    }
    

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    
    
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}