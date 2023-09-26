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
                float Cost = rs.getFloat("Cost");
                String Status = rs.getString("Status");

                Reservation reservation = new Reservation(ReservationID, UserID, ServiceID, ReservationDate, ReservationSlot, Cost, Status);
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

    public static void main(String args[]) {
//        ReservationDAO rd = new ReservationDAO();
//        for (Reservation r : rd.getSpecificReservation("3", "27","9", "1")) {
//            System.out.println(r.getReservationDate());
//        }
    }
}
