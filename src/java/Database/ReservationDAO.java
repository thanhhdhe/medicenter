/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.sql.Date;
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
                Date CreatedDate = rs.getDate("CreatedDate");
                float Cost = rs.getFloat("Cost");
                String Status = rs.getString("Status");
                int StaffID = rs.getInt("StaffID");
                Reservation reservation = new Reservation(ReservationID, UserID, ServiceID, ReservationDate, ReservationSlot, CreatedDate, Cost, Status, StaffID);
                list.add(reservation);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Reservation> getSpecificReservation(String staffID, String date, String month, String year, String serviceID) {
        List<Reservation> list = new ArrayList<>();
        xSql = "SELECT * from [dbo].[Reservations] where StaffID = ? and DAY(ReservationDate) = ? and MONTH(ReservationDate) = ? and YEAR(ReservationDate) = ? and ServiceID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, staffID);
            ps.setString(2, date);
            ps.setString(3, month);
            ps.setString(4, year);
            ps.setString(5, serviceID);
            rs = ps.executeQuery();
            while (rs.next()) {
                int ReservationID = rs.getInt("ReservationID");
                int UserID = rs.getInt("UserID");
                int ServiceID = rs.getInt("ServiceID");
                Date ReservationDate = rs.getDate("ReservationDate");
                int ReservationSlot = rs.getInt("ReservationSlot");
                Date CreatedDate = rs.getDate("CreatedDate");
                float Cost = rs.getFloat("Cost");
                String Status = rs.getString("Status");
                int StaffID = rs.getInt("StaffID");
                Reservation reservation = new Reservation(ReservationID, UserID, ServiceID, ReservationDate, ReservationSlot, CreatedDate, Cost, Status, StaffID);
                list.add(reservation);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Reservation> getSortedPaged(int offSetPage, int numberOfPage) {
        List<Reservation> reservationList = new ArrayList<>();
        xSql = "SELECT *  FROM [dbo].[Reservations]  "
                + "OFFSET ? ROWS "
                + "FETCH NEXT ? ROWS ONLY";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, offSetPage);
            ps.setInt(2, numberOfPage);

            rs = ps.executeQuery();
            while (rs.next()) {
                int ReservationID = rs.getInt("ReservationID");
                int UserID = rs.getInt("UserID");
                int ServiceID = rs.getInt("ServiceID");
                Date ReservationDate = rs.getDate("ReservationDate");
                int ReservationSlot = rs.getInt("ReservationSlot");
                Date CreatedDate = rs.getDate("CreatedDate");
                float Cost = rs.getFloat("Cost");
                String Status = rs.getString("Status");
                int StaffID = rs.getInt("StaffID");
                Reservation reservation = new Reservation(ReservationID, UserID, ServiceID, ReservationDate, ReservationSlot, CreatedDate, Cost, Status, StaffID);
                reservationList.add(reservation);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reservationList;
    }

    public int getTotalReservationByUserID(String userID, int number) {
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

    public static void main(String args[]) {
        ReservationDAO rd = new ReservationDAO();
//        System.out.println(rd.getTotalReservationByUserID("1", 3));
//        for (Reservation r : rd.getSpecificReservation("3", "27","9", "1")) {
//            System.out.println(r.getReservationDate());
//        }
    }
}
