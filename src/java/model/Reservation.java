/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author hbich
 */
public class Reservation {

    private int ReservationID, UserID, ServiceID;
    private Date ReservationDate;
    private int ReservationSlot;
    private float Cost;
    private String Status;

    public Reservation() {
        
    }

    public Reservation(int ReservationID, int UserID, int ServiceID, Date ReservationDate, int ReservationSlot, float Cost, String Status) {
        this.ReservationID = ReservationID;
        this.UserID = UserID;
        this.ServiceID = ServiceID;
        this.ReservationDate = ReservationDate;
        this.ReservationSlot = ReservationSlot;
        this.Cost = Cost;
        this.Status = Status;
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

}
