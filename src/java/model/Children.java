/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class Children {
    private int childID;
    private int userID;
    private String childName;
    private int age;
    private String status;

    public Children() {
    }

    public Children(int childID, int userID, String childName, int age, String status) {
        this.childID = childID;
        this.userID = userID;
        this.childName = childName;
        this.age = age;
        this.status = status;
    }
    
    public Children( int userID, String childName, int age, String status) {
        this.userID = userID;
        this.childName = childName;
        this.age = age;
        this.status = status;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
}
