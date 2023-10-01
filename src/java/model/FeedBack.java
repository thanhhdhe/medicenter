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
public class FeedBack {

    private int feedbackID;
    private int userID;
    private int medicalID;
    private String content;
    private String feedbackdate;
    private int ratestar;
    private String Fstatus;
    //add anonymous variable
    private String ServiceName;
    private String FullName;
    private String UserEmail;
    private String FPhone;
    private String MedicalPrescription;

    // feedback detail
    public FeedBack(String FullName,String UserEmail,String FPhone, String content, String feedbackdate, int ratestar, String Fstatus, String ServiceName,String MedicalPrescription) {
        this.FullName = FullName;
        this.UserEmail = UserEmail;
        this.FPhone = FPhone;
        this.content = content;
        this.feedbackdate = feedbackdate;
        this.ratestar = ratestar;
        this.Fstatus = Fstatus;
        this.ServiceName = ServiceName;          
        this.MedicalPrescription = MedicalPrescription;
    }

    //contructor lay them ServiceName
    public FeedBack(int feedbackID, int userID, int medicalID, String content, String feedbackdate, int ratestar, String Fstatus, String ServiceName) {
        this.feedbackID = feedbackID;
        this.userID = userID;
        this.medicalID = medicalID;
        this.content = content;
        this.feedbackdate = feedbackdate;
        this.ratestar = ratestar;
        this.Fstatus = Fstatus;
        this.ServiceName = ServiceName;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String ServiceName) {
        this.ServiceName = ServiceName;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String UserEmail) {
        this.UserEmail = UserEmail;
    }

    public String getFPhone() {
        return FPhone;
    }

    public void setFPhone(String FPhone) {
        this.FPhone = FPhone;
    }

    public String getMedicalPrescription() {
        return MedicalPrescription;
    }

    public void setMedicalPrescription(String MedicalPrescription) {
        this.MedicalPrescription = MedicalPrescription;
    }

    public String getFstatus() {
        return Fstatus;
    }

    public void setFstatus(String Fstatus) {
        this.Fstatus = Fstatus;
    }

    public FeedBack(int feedbackID, int userID, int medicalID, String content, String feedbackdate, int ratestar, String Fstatus) {
        this.feedbackID = feedbackID;
        this.userID = userID;
        this.medicalID = medicalID;
        this.content = content;
        this.feedbackdate = feedbackdate;
        this.ratestar = ratestar;
        this.Fstatus = Fstatus;
    }

    public FeedBack() {
    }

    public int getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(int feedbackID) {
        this.feedbackID = feedbackID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getMedicalID() {
        return medicalID;
    }

    public void setMedicalID(int medicalID) {
        this.medicalID = medicalID;
    }

    public String getContent() {
        return content;
    }

    public String getContentSub() {
        return content.substring(0, 2) + "...";
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFeedbackdate() {
        return feedbackdate;
    }

    public void setFeedbackdate(String feedbackdate) {
        this.feedbackdate = feedbackdate;
    }

    public int getRatestar() {
        return ratestar;
    }

    public void setRatestar(int ratestar) {
        this.ratestar = ratestar;
    }

}
