/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author hbich
 */
public class ServiceStaff {
    private int ServiceStaffID;
    private int ServiceID;
    private int StaffID;

    public ServiceStaff() {
    }

    public ServiceStaff(int ServiceStaffID, int ServiceID, int StaffID) {
        this.ServiceStaffID = ServiceStaffID;
        this.ServiceID = ServiceID;
        this.StaffID = StaffID;
    }

    public int getServiceStaffID() {
        return ServiceStaffID;
    }

    public void setServiceStaffID(int ServiceStaffID) {
        this.ServiceStaffID = ServiceStaffID;
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
    
}
