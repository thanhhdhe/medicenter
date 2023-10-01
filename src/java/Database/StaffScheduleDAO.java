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
        xSql = "SELECT DISTINCT Slot FROM [dbo].[StaffSchedules] where StaffID = ? and DAY(Workday) = ? and MONTH(Workday) = ? and YEAR(Workday) = ?";
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

    public List<Integer> getWorkdayByServiceID(String serviceID, String month, String year) {
        List<Integer> day = new ArrayList<>();
        xSql = "WITH AvailableSlots AS (\n"
                + "    SELECT\n"
                + "        SS.Workday,\n"
                + "        COUNT(SS.Slot) AS TotalSlots,\n"
                + "        COALESCE(R.ReservedSlots, 0) AS ReservedSlots\n"
                + "    FROM StaffSchedules SS\n"
                + "    LEFT JOIN (\n"
                + "        SELECT ReservationDate, COUNT(ReservationSlot) AS ReservedSlots\n"
                + "        FROM Reservations\n"
                + "        WHERE ServiceID = ? AND Status <> 'cancel'\n"
                + "        GROUP BY ReservationDate\n"
                + "    ) R ON SS.Workday = R.ReservationDate\n"
                + "    WHERE SS.StaffID IN (\n"
                + "        SELECT StaffID\n"
                + "        FROM ServiceStaff\n"
                + "        WHERE ServiceID = ?\n"
                + "    )\n"
                + "    GROUP BY SS.Workday, R.ReservedSlots\n"
                + ")\n"
                + "SELECT DAY(Workday) as Workday\n"
                + "FROM AvailableSlots\n"
                + "WHERE (TotalSlots > ReservedSlots OR ReservedSlots IS NULL) AND MONTH(Workday) = ? AND YEAR(Workday) = ? ORDER BY Workday ASC";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, serviceID);
            ps.setString(2, serviceID);
            ps.setString(3, month);
            ps.setString(4, year);
            rs = ps.executeQuery();
            while (rs.next()) {
                int temp = rs.getInt("Workday");
                day.add(temp);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;
    }

    public List<Integer> getFullDayByServiceID(String serviceID, String month, String year) {
        List<Integer> day = new ArrayList<>();
        xSql = "WITH AvailableSlots AS (\n"
                + "    SELECT\n"
                + "        SS.Workday,\n"
                + "        COUNT(SS.Slot) AS TotalSlots,\n"
                + "        COALESCE(R.ReservedSlots, 0) AS ReservedSlots\n"
                + "    FROM StaffSchedules SS\n"
                + "    LEFT JOIN (\n"
                + "        SELECT ReservationDate, COUNT(ReservationSlot) AS ReservedSlots\n"
                + "        FROM Reservations\n"
                + "        WHERE ServiceID = ? AND Status <> 'cancel'\n"
                + "        GROUP BY ReservationDate\n"
                + "    ) R ON SS.Workday = R.ReservationDate\n"
                + "    WHERE SS.StaffID IN (\n"
                + "        SELECT StaffID\n"
                + "        FROM ServiceStaff\n"
                + "        WHERE ServiceID = ?\n"
                + "    )\n"
                + "    GROUP BY SS.Workday, R.ReservedSlots\n"
                + ")\n"
                + "SELECT DAY(Workday) as fullDay\n"
                + "FROM AvailableSlots\n"
                + "WHERE (TotalSlots = ReservedSlots) AND MONTH(Workday) = ? AND YEAR(Workday) = ? ORDER BY Workday ASC";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, serviceID);
            ps.setString(2, serviceID);
            ps.setString(3, month);
            ps.setString(4, year);
            rs = ps.executeQuery();
            while (rs.next()) {
                int temp = rs.getInt("fullDay");
                day.add(temp);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;
    }

    public List<Integer> getWorkSlotsByService(String selectedDate, String selectedMonth, String selectedYear, String serviceID) {
        List<Integer> slot = new ArrayList<>();
        xSql = "select ss1.Slot from StaffSchedules ss1\n"
                + "join ServiceStaff ss2 on ss1.StaffID=ss2.StaffID\n"
                + "where ss2.ServiceID = ? and DAY(Workday) = ? and MONTH(Workday) = ? and YEAR(Workday) = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, serviceID);
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

    public static void main(String args[]) {
        StaffScheduleDAO ssd = new StaffScheduleDAO();
        for (int i : ssd.getFullDayByServiceID("3" , "10", "2023")) {
            System.out.println(i);
        }
//        for (int i : ssd.getListDayFullSlot("3","9")) {
//            System.out.println(i);
//        }
//        for (int i : ssd.getWorkSlotsByService("3", "27", "10", "2023")) {
//            System.out.println(i);
//        }
    }
}
