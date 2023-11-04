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
                int StaffID = rs.getInt("StaffID");
                Date Workday = rs.getDate("Workday");
                int Slot = rs.getInt("Slot");
                String Status = rs.getString("Status");
                StaffSchedule ss = new StaffSchedule(ScheduleID, StaffID, Workday, Slot, Status);
                staffScheduleList.add(ss);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staffScheduleList;
    }
    
    public List<StaffSchedule> getStaffUnconfirmSchedules(int page, int pageSize) {
        List<StaffSchedule> staffScheduleList = new ArrayList<>();
        xSql = "SELECT * FROM [dbo].[StaffSchedules] where Status = 'unconfirmed'"
                + "ORDER BY StaffID DESC "
                + "OFFSET ? ROWS "
                + "FETCH NEXT ? ROWS ONLY;";
        int offset = (page - 1) * pageSize;
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, offset);
            ps.setInt(2, pageSize);
            rs = ps.executeQuery();
            while (rs.next()) {
                int ScheduleID = rs.getInt("ScheduleID");
                Date Workday = rs.getDate("Workday");
                int Slot = rs.getInt("Slot");
                String status = rs.getString("Status");
                StaffSchedule ss = new StaffSchedule(ScheduleID, Slot, Workday, Slot, status);
                staffScheduleList.add(ss);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staffScheduleList;
    }
    public void updateStaffSchedule(StaffSchedule staffSchedule) {
        try{
            String sql = "UPDATE StaffSchedules SET StaffID = ?, Workday = ?, Slot = ?, Status = ? WHERE ScheduleID = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, staffSchedule.getStaffID());
            ps.setDate(2, staffSchedule.getWorkday());
            ps.setInt(3, staffSchedule.getSlot());
            ps.setString(4, staffSchedule.getStatus());
            ps.setInt(5, staffSchedule.getScheduleID());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public StaffSchedule getStaffScheduleByID(int scheduleID) {
        StaffSchedule staffSchedule = null;

        try {
            String sql = "SELECT * FROM StaffSchedules WHERE ScheduleID = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, scheduleID);
            rs = ps.executeQuery();

            if (rs.next()) {
                int staffID = rs.getInt("StaffID");
                Date workday = rs.getDate("Workday");
                int slot = rs.getInt("Slot");
                String status = rs.getString("Status");
                staffSchedule = new StaffSchedule(scheduleID, staffID, workday, slot, status);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return staffSchedule;
    }
    
    public int countStaffUnconfirmSchedules() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM [dbo].[StaffSchedules] where Status = 'unconfirmed'";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return count;
    }

    public List<Integer> getWorkDay(String staffID, String month, String year) {
        List<Integer> day = new ArrayList<>();
        xSql = "select DAY(Workday) as Work from [dbo].[StaffSchedules] "
                + "where StaffID = ? and MONTH(Workday) = ? and YEAR(Workday) = ? and Status = 'confirm' group by Workday";
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
        xSql = "SELECT DISTINCT Slot FROM [dbo].[StaffSchedules] "
                + "where StaffID = ? and DAY(Workday) = ? and MONTH(Workday) = ? and YEAR(Workday) = ? and Status = 'confirm'";
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
                + "    ) AND SS.Status = 'confirm'\n"
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
                + "    ) AND SS.Status = 'confirm'\n"
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
                + "where ss2.ServiceID = ? and DAY(Workday) = ? and MONTH(Workday) = ? and YEAR(Workday) = ? and ss1.Status = 'confirm'";
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

    public List<Integer> getListDayInReservation(String staffID, String selectedMonth, String selectedYear) {
        List<Integer> list = new ArrayList<>();
        xSql = "select distinct DAY(ss.Workday) as Workday from StaffSchedules ss\n"
                + "left join Reservations r \n"
                + "on r.ReservationDate = ss.Workday and r.ReservationSlot = ss.Slot\n"
                + "where ss.StaffID = ? and MONTH(ss.Workday) = ? and YEAR(ss.Workday) = ? and  (r.status is null or r.status = 'cancel')";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, staffID);
            ps.setString(2, selectedMonth);
            ps.setString(3, selectedYear);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("Workday"));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<StaffSchedule> getPageStaffScheduleByStaff(String staffID, int page, int pageSize) {
        List<StaffSchedule> StaffScheduleList = new ArrayList<>();
        xSql = "SELECT * FROM [dbo].[StaffSchedules] WHERE StaffID = ? "
                + "ORDER BY Workday DESC "
                + "OFFSET ? ROWS "
                + "FETCH NEXT ? ROWS ONLY;";
        int offset = (page - 1) * pageSize;
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, staffID);
            ps.setInt(2, offset);
            ps.setInt(3, pageSize);
            rs = ps.executeQuery();
            while (rs.next()) {
                int scheduleID = rs.getInt("ScheduleID");
                int StaffID = rs.getInt("StaffID");
                Date workday = rs.getDate("Workday");
                int slot = rs.getInt("Slot");
                String status = rs.getString("Status");
                StaffSchedule staffSchedules = new StaffSchedule(scheduleID, StaffID, workday, slot, status);
                StaffScheduleList.add(staffSchedules);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StaffScheduleList;
    }

    public int countStaffSchedule(String staffID) {
        int count = 0;
        xSql = "SELECT COUNT(*) AS RecordCount FROM [dbo].[StaffSchedules] WHERE StaffID = ? ";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, staffID);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public StaffSchedule getSchedule(String scheduleID) {
        xSql = "SELECT *FROM [dbo].[StaffSchedules] WHERE ScheduleID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, scheduleID);
            rs = ps.executeQuery();
            if (rs.next()) {
                int ScheduleID = rs.getInt("ScheduleID");
                Date Workday = rs.getDate("Workday");
                int Slot = rs.getInt("Slot");
                StaffSchedule staffSchedule = new StaffSchedule(ScheduleID, Slot, Workday, Slot);
                return staffSchedule;
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteSchedule(String scheduleID) {
        String xSql = "DELETE [dbo].[StaffSchedules] WHERE ScheduleID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, scheduleID);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editScheduleByStaff(StaffSchedule staffSchedule) {
        String xSql = "UPDATE [dbo].[StaffSchedules]\n"
                + "   SET [Workday] = ?, [Slot] = ?, [Status] = 'unconfirmed'\n"
                + " WHERE ScheduleID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setDate(1, staffSchedule.getWorkday());
            ps.setInt(2, staffSchedule.getSlot());
            ps.setInt(3, staffSchedule.getScheduleID());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public StaffSchedule getScheduleByWorkDate(Date workday, int slot, String staffID) {
        String xSql = "Select * FROM [dbo].[StaffSchedules] "
                + " WHERE Workday = ? AND Slot = ? AND StaffID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setDate(1, workday);
            ps.setInt(2, slot);
            ps.setString(3, staffID);
            rs = ps.executeQuery();
            if (rs.next()) {
                 int ScheduleID = rs.getInt("ScheduleID");
                Date Workday = rs.getDate("Workday");
                int Slot = rs.getInt("Slot");
                StaffSchedule ss = new StaffSchedule(ScheduleID, Slot, Workday, Slot);
                return ss;
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void requestToManager(StaffSchedule staffSchedules) {
        xSql = "INSERT INTO [dbo].[StaffSchedules](StaffID, Workday, Slot, Status) VALUES (?,?,?, 'unconfirmed')";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, staffSchedules.getStaffID());
            ps.setDate(2, staffSchedules.getWorkday());
            ps.setInt(3, staffSchedules.getSlot());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        StaffScheduleDAO staffScheduleDAO = new StaffScheduleDAO();
        List<StaffSchedule> staffSchedules = staffScheduleDAO.getStaffUnconfirmSchedules(1, 10);
        StaffDAO staffDAO = new StaffDAO();
        for (StaffSchedule staffSchedule : staffSchedules) {
            System.out.println(staffDAO.getStaffByStaffId(staffSchedule.getStaffID()).getProfileImage());
        }
    }
}
