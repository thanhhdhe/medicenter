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
public class StaffSchedule {

    private int ScheduleID;
    private int StaffID;
    private int DayOfWeek;
    private int Slot;

    public StaffSchedule() {
    }

    public StaffSchedule(int ScheduleID, int StaffID, int DayOfWeek, int Slot) {
        this.ScheduleID = ScheduleID;
        this.StaffID = StaffID;
        this.DayOfWeek = DayOfWeek;
        this.Slot = Slot;
    }

    public int getScheduleID() {
        return ScheduleID;
    }

    public void setScheduleID(int ScheduleID) {
        this.ScheduleID = ScheduleID;
    }

    public int getStaffID() {
        return StaffID;
    }

    public void setStaffID(int StaffID) {
        this.StaffID = StaffID;
    }

    public int getDayOfWeek() {
        return DayOfWeek;
    }

    public void setDayOfWeek(int DayOfWeek) {
        this.DayOfWeek = DayOfWeek;
    }

    public int getSlot() {
        return Slot;
    }

    public void setSlot(int Slot) {
        this.Slot = Slot;
    }

}
