/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Reservation;

/**
 *
 * @author hbich
 */
public class ReservationDAO extends MyDAO {

    public List<Reservation> getReservationByUserID(String userID) {
        List<Reservation> list = new ArrayList<>();
        xSql = "SELECT * from [dbo].[Reservations] where UserID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, userID);
            rs = ps.executeQuery();
            while (rs.next()) {
                int ReservationID = rs.getInt("ReservationID");
                int UserID = rs.getInt("UserID");
                int ServiceID = rs.getInt("ServiceID");
                Date ReservationDate = rs.getDate("ReservationDate");
                int ReservationSlot = rs.getInt("ReservationSlot");
                Timestamp CreatedDate = rs.getTimestamp("CreatedDate");
                float Cost = rs.getFloat("Cost");
                String Status = rs.getString("Status");
                int StaffID = rs.getInt("StaffID");
                int ChildID = rs.getInt("ChildID");
                Reservation reservation = new Reservation(ReservationID, UserID, ServiceID, StaffID, ChildID, ReservationDate, ReservationSlot, CreatedDate, Cost, Status);
                list.add(reservation);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Reservation> getSpecificReservation(String staffID, String date, String month, String year) {
        List<Reservation> list = new ArrayList<>();
        xSql = "SELECT * from [dbo].[Reservations] where StaffID = ? and DAY(ReservationDate) = ? and MONTH(ReservationDate) = ? and YEAR(ReservationDate) = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, staffID);
            ps.setString(2, date);
            ps.setString(3, month);
            ps.setString(4, year);
            rs = ps.executeQuery();
            while (rs.next()) {
                int ReservationID = rs.getInt("ReservationID");
                int UserID = rs.getInt("UserID");
                int ServiceID = rs.getInt("ServiceID");
                Date ReservationDate = rs.getDate("ReservationDate");
                int ReservationSlot = rs.getInt("ReservationSlot");
                Timestamp CreatedDate = rs.getTimestamp("CreatedDate");
                float Cost = rs.getFloat("Cost");
                String Status = rs.getString("Status");
                int StaffID = rs.getInt("StaffID");
                int ChildID = rs.getInt("ChildID");
                Reservation reservation = new Reservation(ReservationID, UserID, ServiceID, StaffID, ChildID, ReservationDate, ReservationSlot, CreatedDate, Cost, Status);
                list.add(reservation);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countReservationsForService(String serviceID) {
        int reservationCount = 0;
        String xSql = "SELECT COUNT(*) AS ReservationCount FROM Reservations WHERE ServiceID = ?";

        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, serviceID);
            rs = ps.executeQuery();

            if (rs.next()) {
                reservationCount = rs.getInt("ReservationCount");
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reservationCount;
    }

    public List<Reservation> getSortedPaged(int offSetPage, int numberOfPage, String userID) {
        List<Reservation> reservationList = new ArrayList<>();
        xSql = "SELECT * FROM [dbo].[Reservations]  "
                + "WHERE UserID = ? "
                + "ORDER BY CreatedDate DESC "
                + "OFFSET ? ROWS "
                + "FETCH NEXT ? ROWS ONLY";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, userID);
            ps.setInt(2, offSetPage);
            ps.setInt(3, numberOfPage);

            rs = ps.executeQuery();
            while (rs.next()) {
                int ReservationID = rs.getInt("ReservationID");
                int UserID = rs.getInt("UserID");
                int ServiceID = rs.getInt("ServiceID");
                Date ReservationDate = rs.getDate("ReservationDate");
                int ReservationSlot = rs.getInt("ReservationSlot");
                Timestamp CreatedDate = rs.getTimestamp("CreatedDate");
                float Cost = rs.getFloat("Cost");
                String Status = rs.getString("Status");
                int StaffID = rs.getInt("StaffID");
                int ChildID = rs.getInt("ChildID");
                Reservation reservation = new Reservation(ReservationID, UserID, ServiceID, StaffID, ChildID, ReservationDate, ReservationSlot, CreatedDate, Cost, Status);
                reservationList.add(reservation);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reservationList;
    }

    public int getTotalPagination(String userID, int number) {
        int count = 0;
        xSql = "SELECT COUNT(*) AS totalNumber FROM [dbo].[Reservations] WHERE UserID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, userID);
            rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("totalNumber");
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (count == 0) {
            return count;
        }
        if (count % number != 0) {
            count /= number;
            count++;
        } else {
            count /= number;
        }
        return count;
    }

    public void updateDatabase() {
        xSql = "UPDATE [dbo].[Reservations] "
                + "SET status = 'cancel' "
                + "WHERE DATEDIFF(minute,CreatedDate,GETDATE()) > 5 AND status ='Pending'";
        try {
            ps = con.prepareStatement(xSql);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Reservation> getListReservationByServiceID(String serviceID, String selectedDate, String selectedMonth, String selectedYear) {
        List<Reservation> list = new ArrayList<>();
        xSql = "select * from [dbo].[Reservations] r\n"
                + "where r.serviceID = ? and DAY(r.ReservationDate) = ? AND MONTH(r.ReservationDate) = ? and YEAR(r.ReservationDate) = ? and r.status <> 'cancel'";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, serviceID);
            ps.setString(2, selectedDate);
            ps.setString(3, selectedMonth);
            ps.setString(4, selectedYear);
            rs = ps.executeQuery();
            while (rs.next()) {
                int ReservationID = rs.getInt("ReservationID");
                int UserID = rs.getInt("UserID");
                int ServiceID = rs.getInt("ServiceID");
                Date ReservationDate = rs.getDate("ReservationDate");
                int ReservationSlot = rs.getInt("ReservationSlot");
                Timestamp CreatedDate = rs.getTimestamp("CreatedDate");
                float Cost = rs.getFloat("Cost");
                String Status = rs.getString("Status");
                int StaffID = rs.getInt("StaffID");
                int ChildID = rs.getInt("ChildID");
                Reservation reservation = new Reservation(ReservationID, UserID, ServiceID, StaffID, ChildID, ReservationDate, ReservationSlot, CreatedDate, Cost, Status);
                list.add(reservation);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insert(Reservation r) {
        xSql = "insert into [dbo].[Reservations](ReservationDate, ReservationSlot, ServiceID, StaffID, CreatedDate, Cost, UserID, Status)\n"
                + "values (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = con.prepareStatement(xSql);
            ps.setDate(1, r.getReservationDate());
            ps.setInt(2, r.getReservationSlot());
            ps.setInt(3, r.getServiceID());
            ps.setInt(4, r.getStaffID());
            ps.setTimestamp(5, r.getCreatedDate());
            ps.setFloat(6, r.getCost());
            ps.setInt(7, r.getUserID());
            ps.setString(8, r.getStatus());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int findReservationID(int userID, String serviceID, Date reservationDate, int slot) {
        int id = -1;
        xSql = "select * from [dbo].[Reservations] where UserID = ? and ServiceID = ? and ReservationDate = ? and ReservationSlot = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, Integer.toString(userID));
            ps.setString(2, serviceID);
            ps.setDate(3, reservationDate);
            ps.setInt(4, slot);
            rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("ReservationID");
                return id;
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public boolean checkSlotForAvailable(String slot, String staffID, String selectedDate, String selectedMonth, String selectedYear) {
        boolean result = true;
        xSql = "select * from Reservations\n"
                + "where ReservationSlot = ? and StaffID = ? and DAY(ReservationDate) = ? \n"
                + "and MONTH(ReservationDate) = ? and YEAR(ReservationDate) = ? \n"
                + "and Status <> 'cancel'";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, slot);
            ps.setString(2, staffID);
            ps.setString(3, selectedDate);
            ps.setString(4, selectedMonth);
            ps.setString(5, selectedYear);
            rs = ps.executeQuery();
            while (rs.next()) {
                result = false;
                break;
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean checkSlotThatSelfBooked(String slot, String staffID, String childID, String selectedDate, String selectedMonth, String selectedYear) {
        boolean result = false;
        xSql = "select * from Reservations\n"
                + "where ReservationSlot = ? and StaffID = ? and DAY(ReservationDate) = ? \n"
                + "and MONTH(ReservationDate) = ? and YEAR(ReservationDate) = ? \n"
                + "and Status <> 'cancel'";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, slot);
            ps.setString(2, staffID);
            ps.setString(3, selectedDate);
            ps.setString(4, selectedMonth);
            ps.setString(5, selectedYear);
            rs = ps.executeQuery();
            while (rs.next()) {
                result = false;
                break;
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Integer> getListSelfBookedSlot(String ChildID, String selectedDate, String selectedMonth, String selectedYear) {
        List<Integer> list = new ArrayList<>();
        xSql = "select ReservationSlot from Reservations \n"
                + "  where ChildID = ? and DAY(ReservationDate) = ? \n"
                + "  and MONTH(ReservationDate) = ? and YEAR(ReservationDate) = ?\n"
                + "  and Status <> 'cancel'";
         try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, ChildID);
            ps.setString(2, selectedDate);
            ps.setString(3, selectedMonth);
            ps.setString(4, selectedYear);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("ReservationSlot"));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String args[]) {
        ReservationDAO rd = new ReservationDAO();
//        String dateString = "2023-10-01 22:20:00";
//        Timestamp sqlTimestamp = Timestamp.valueOf(dateString);
//        System.out.println(rd.checkSlotForAvailable("4", "3", "26", "10", "2023"));
        for (int i : rd.getListSelfBookedSlot("1", "15", "10", "2023")) {
            System.out.println(i);
        }
//        LocalDateTime currentDateTime = LocalDateTime.now();
//        Timestamp sqlTimestamp = Timestamp.valueOf(currentDateTime);
//        Date sqlDate = null;
//        try {
//            // Define a date format for parsing
//            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
//
//            // Parse the string into a java.util.Date object
//            java.util.Date utilDate = dateFormat.parse("10-26-2023");
//            // Convert java.util.Date to java.sql.Date
//            sqlDate = new Date(utilDate.getTime());
//        } catch (Exception e) {
//
//        }
//
//        Reservation r = new Reservation();
//        r.setCost(300);
//        r.setCreatedDate(sqlTimestamp);
//        r.setReservationDate(sqlDate);
//        r.setReservationSlot(3);
//        r.setServiceID(3);
//        
////        r.setStaffID();
//        
//        r.setStatus("pending");
//        r.setUserID(1);
//        rd.insert(r);
//        System.out.println(rd.getTotalReservationByUserID("1", 3));
//        for (Reservation r : rd.getSpecificReservation("3", "27","9", "1")) {
//            System.out.println(r.getReservationDate());
//        }
    }
}
