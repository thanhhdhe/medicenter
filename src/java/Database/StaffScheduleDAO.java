/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import model.StaffSchedule;

/**
 *
 * @author hbich
 */
public class StaffScheduleDAO extends MyDAO {
    public List<StaffSchedule> getStaffSchedulesByStaffID(String staffID) {
        List<StaffSchedule> staffScheduleList = new ArrayList<>();
        xSql = "SELECT *  FROM [dbo].[StaffSchedules] where StaffID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, staffID);
            rs = ps.executeQuery();
            while (rs.next()) {
                int ScheduleID = rs.getInt("ScheduleID");
                int DayOfWeek = rs.getInt("DayOfWeek");
                int Slot = rs.getInt("Slot");
                StaffSchedule ss = new StaffSchedule(ScheduleID, Slot, DayOfWeek, Slot);
                staffScheduleList.add(ss);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staffScheduleList;
    }
    public List<Integer> getWorkSlots(String selectedDate, String staffID) {
        List<Integer> slot = new ArrayList<>();
        xSql = "SELECT *  FROM [dbo].[StaffSchedules] where StaffID = ? and DayOfWeek = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, staffID);
            ps.setString(2, selectedDate);
            rs = ps.executeQuery();
            while (rs.next()) {
                int slotInt = rs.getInt("Slot");
                slot.add(slotInt);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return slot;
    }
    public static void main(String args[]) {
//        StaffScheduleDAO ssd = new StaffScheduleDAO();
//        for (int i : ssd.getWorkSlots("3", "4")) {
//            System.out.println(i);
//        }
    }
}
