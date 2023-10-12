/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class Staff {

    private int staffID;
    private String staffName;
    private String password;
    private String email;
    private String fullName;
    private String gender;
    private String phoneNumber;
    private String profileImage;
    private String role;
    private String rank;
    private String specialty;
    private String introduction;
    private String SpecializedActivities;
    private String ProfessionalAchievements;
    private String DepthStudy;

    public Staff() {
    }

    public Staff(int staffID, String staffName, String password, String email, String fullName, String gender, String phoneNumber, String profileImage, String role, String rank, String specialty, String introduction) {
        this.staffID = staffID;
        this.staffName = staffName;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
        this.role = role;
        this.rank = rank;
        this.specialty = specialty;
        this.introduction = introduction;
    }

    public Staff(String staffName, String password, String email, String fullName, String gender, String phoneNumber, String profileImage, String role, String rank, String specialty, String introduction) {
        this.staffName = staffName;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
        this.role = role;
        this.rank = rank;
        this.specialty = specialty;
        this.introduction = introduction;
    }

    public Staff(int staffID, String fullName, String gender, String phoneNumber, String profileImage, String rank, String specialty, String introduction, String SpecializedActivities, String ProfessionalAchievements, String DepthStudy) {
        this.staffID = staffID;
        this.fullName = fullName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
        this.rank = rank;
        this.specialty = specialty;
        this.introduction = introduction;
        this.DepthStudy = DepthStudy;
        this.ProfessionalAchievements = ProfessionalAchievements;
        this.SpecializedActivities = SpecializedActivities;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSpecializedActivities() {
        return SpecializedActivities;
    }

    public void setSpecializedActivities(String SpecializedActivities) {
        this.SpecializedActivities = SpecializedActivities;
    }

    public String getProfessionalAchievements() {
        return ProfessionalAchievements;
    }

    public void setProfessionalAchievements(String ProfessionalAchievements) {
        this.ProfessionalAchievements = ProfessionalAchievements;
    }

    public String getDepthStudy() {
        return DepthStudy;
    }

    public void setDepthStudy(String DepthStudy) {
        this.DepthStudy = DepthStudy;
    }

}
