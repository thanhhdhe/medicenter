/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author Admin
 */
public class Children {

    private int childID;
    private int userID;
    private String childName;
    private Date birthday;
    private String status;
    private String gender;
    private String avatar;

    public Children() {
    }

    public Children(int childID, int userID, String childName, Date birthday, String status, String gender) {
        this.childID = childID;
        this.userID = userID;
        this.childName = childName;
        this.birthday = birthday;
        this.status = status;
        this.gender = gender;
    }

    public Children(int childID, int userID, String childName, Date birthday, String status, String gender, String avatar) {
        this.childID = childID;
        this.userID = userID;
        this.childName = childName;
        this.birthday = birthday;
        this.status = status;
        this.gender = gender;
        this.avatar = avatar;
    }

    public Children(int userID, String childName, Date birthday, String status, String gender) {
        this.userID = userID;
        this.childName = childName;
        this.birthday = birthday;
        this.status = status;
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getChildID() {
        return childID;
    }

    public void setChildID(int childID) {
        this.childID = childID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
