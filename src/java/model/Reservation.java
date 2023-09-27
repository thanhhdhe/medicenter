/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author hbich
 */
public class Reservation {

    private int ReservationID, UserID, ServiceID;
    private Date ReservationDate;
    private int ReservationSlot;
    private Date CreatedDate;
    private float Cost;
    private String Status;
    private int StaffID;

    public Reservation() {
        
    }

    public Reservation(int ReservationID, int UserID, int ServiceID, Date ReservationDate, int ReservationSlot, Date CreatedDate, float Cost, String Status, int StaffID) {
        this.ReservationID = ReservationID;
        this.UserID = UserID;
        this.ServiceID = ServiceID;
        this.ReservationDate = ReservationDate;
        this.ReservationSlot = ReservationSlot;
        this.CreatedDate = CreatedDate;
        this.Cost = Cost;
        this.Status = Status;
        this.StaffID = StaffID;
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

    public Date getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(Date CreatedDate) {
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

    public int getStaffID() {
        return StaffID;
    }

    public void setStaffID(int StaffID) {
        this.StaffID = StaffID;
    }

}
