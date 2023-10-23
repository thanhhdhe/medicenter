/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

/**
 *
 * @author Admin
 */
public class Children {

    private User user;
    private int childID;
    private String childName;
    private Date birthday;
    private String status;
    private String gender;
    private String image;
    private Relationship relationship;

    public Children() {
    }

    public Children(User user, int childID, String childName, Date birthday, String status, String gender, String image, Relationship relationship) {
        this.user = user;
        this.childID = childID;
        this.childName = childName;
        this.birthday = birthday;
        this.status = status;
        this.gender = gender;
        this.image = image;
        this.relationship = relationship;
    }
    
    public Children(User user, int childID, String childName, Date birthday, String gender, String image) {
        this.user = user;
        this.childID = childID;
        this.childName = childName;
        this.birthday = birthday;
        this.gender = gender;
        this.image = image;
    }

    public Children(User user, String childName, Date birthday, String gender, String image,Relationship relationship ) {
        this.user = user;
        this.childName = childName;
        this.birthday = birthday;
        this.gender = gender;
        this.image = image;
        this.relationship = relationship;
    }

    public Children(User user, int childID, String childName, Date birthday, String status, String gender, String image) {
        this.user = user;
        this.childID = childID;
        this.childName = childName;
        this.birthday = birthday;
        this.status = status;
        this.gender = gender;
        this.image = image;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getChildID() {
        return childID;
    }

    public void setChildID(int childID) {
        this.childID = childID;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public int getAge() {
        LocalDate localBirthDate = this.birthday.toLocalDate();
        LocalDate currentDate = LocalDate.now();
        return Period.between(localBirthDate, currentDate).getYears();
    }

    public Relationship getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }
    

}
