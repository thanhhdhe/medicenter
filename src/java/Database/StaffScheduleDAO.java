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
        xSql = "SELECT * FROM [dbo].[StaffSchedules] where StaffID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, staffID);
            rs = ps.executeQuery();
            while (rs.next()) {
                int ScheduleID = rs.getInt("ScheduleID");
                Date Workday = rs.getDate("Workday");
                int Slot = rs.getInt("Slot");
                StaffSchedule ss = new StaffSchedule(ScheduleID, Slot, Workday, Slot);
                staffScheduleList.add(ss);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staffScheduleList;
    }
    
    public List<Integer> getWorkDay(String staffID, String month, String year) {
        List<Integer> day = new ArrayList<>();
        xSql = "select DAY(Workday) as Work from [dbo].[StaffSchedules] where StaffID = ? and MONTH(Workday) = ? and YEAR(Workday) = ? group by Workday";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, staffID);
            ps.setString(2, month);
            ps.setString(3, year);
            rs = ps.executeQuery();
            while (rs.next()) {
                int temp = rs.getInt("Work");
                day.add(temp);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;
    }

    public List<Integer> getWorkSlots(String selectedDate, String selectedMonth, String selectedYear, String staffID) {
        List<Integer> slot = new ArrayList<>();
        xSql = "SELECT * FROM [dbo].[StaffSchedules] where StaffID = ? and DAY(Workday) = ? and MONTH(Workday) = ? and YEAR(Workday) = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, staffID);
            ps.setString(2, selectedDate);
            ps.setString(3, selectedMonth);
            ps.setString(4, selectedYear);
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

    public List<Integer> getListDayFullSlot(String staffID, String month, String year) {
        List<Integer> day = new ArrayList<>();
        xSql = "SELECT DISTINCT DAY(ss.Workday) as fullDay"
                + " FROM StaffSchedules ss"
                + " WHERE ss.StaffID = ? AND MONTH(ss.Workday) = ? AND YEAR(ss.Workday) = ?"
                + " AND NOT EXISTS ("
                + "    SELECT 1"
                + "    FROM Reservations r"
                + "    WHERE r.StaffID = ss.StaffID"
                + "    AND r.ReservationDate = ss.Workday"
                + "    AND r.Status = 'Cancel'"
                + ")"
                + " AND NOT EXISTS ("
                + "    SELECT 1"
                + "    FROM StaffSchedules ss2"
                + "    WHERE ss2.StaffID = ss.StaffID"
                + "    AND ss2.Workday = ss.Workday"
                + "    AND ss2.Slot NOT IN ("
                + "        SELECT DISTINCT r2.ReservationSlot"
                + "        FROM Reservations r2"
                + "        WHERE r2.StaffID = ss.StaffID"
                + "        AND r2.ReservationDate = ss.Workday"
                + "        AND r2.Status <> 'Cancel'"
                + "    )"
                + ");";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, staffID);
            ps.setString(2, month);
            ps.setString(3, year);
            rs = ps.executeQuery();
            while (rs.next()) {
                int dayInt = rs.getInt("fullDay");
                day.add(dayInt);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;
    }

    public static void main(String args[]) {
//        StaffScheduleDAO ssd = new StaffScheduleDAO();
//        for (int i : ssd.getListDayFullSlot("3","9")) {
//            System.out.println(i);
//        }
    }
}
