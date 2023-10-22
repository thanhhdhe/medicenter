/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author hbich
 */
public class Reservation {

    private int ReservationID, UserID, ServiceID;
    private int StaffID;
    private int ChildID;
    private Date ReservationDate;
    private int ReservationSlot;
    private Timestamp CreatedDate;
    private float Cost;
    private String Status;
    private String payment;

    public Reservation() {
        
    }

    public Reservation(int ReservationID, int UserID, int ServiceID, int StaffID, int ChildID, Date ReservationDate, int ReservationSlot, Timestamp CreatedDate, float Cost, String Status) {
        this.ReservationID = ReservationID;
        this.UserID = UserID;
        this.ServiceID = ServiceID;
        this.StaffID = StaffID;
        this.ChildID = ChildID;
        this.ReservationDate = ReservationDate;
        this.ReservationSlot = ReservationSlot;
        this.CreatedDate = CreatedDate;
        this.Cost = Cost;
        this.Status = Status;
    }

    public Reservation(int ReservationID, int UserID, int ServiceID, int StaffID, int ChildID, Date ReservationDate, int ReservationSlot, Timestamp CreatedDate, float Cost, String Status, String payment) {
        this.ReservationID = ReservationID;
        this.UserID = UserID;
        this.ServiceID = ServiceID;
        this.StaffID = StaffID;
        this.ChildID = ChildID;
        this.ReservationDate = ReservationDate;
        this.ReservationSlot = ReservationSlot;
        this.CreatedDate = CreatedDate;
        this.Cost = Cost;
        this.Status = Status;
        this.payment = payment;
    }
    

    public int getReservationID() {
        return ReservationID;
    }

    public void setReservationID(int ReservationID) {
        this.ReservationID = ReservationID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public int getServiceID() {
        return ServiceID;
    }

    public void setServiceID(int ServiceID) {
        this.ServiceID = ServiceID;
    }

    public int getStaffID() {
        return StaffID;
    }

    public void setStaffID(int StaffID) {
        this.StaffID = StaffID;
    }

    public int getChildID() {
        return ChildID;
    }

    public void setChildID(int ChildID) {
        this.ChildID = ChildID;
    }

    public Date getReservationDate() {
        return ReservationDate;
    }

    public void setReservationDate(Date ReservationDate) {
        this.ReservationDate = ReservationDate;
    }

    public int getReservationSlot() {
        return ReservationSlot;
    }

    public void setReservationSlot(int ReservationSlot) {
        this.ReservationSlot = ReservationSlot;
    }

    public Timestamp getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(Timestamp CreatedDate) {
        this.CreatedDate = CreatedDate;
    }

    public float getCost() {
        return Cost;
    }

    public void setCost(float Cost) {
        this.Cost = Cost;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
    
}
