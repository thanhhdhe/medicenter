/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.awt.BorderLayout;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import model.Reservation;

/**
 *
 * @author hbich
 */
public class ReservationDAO extends MyDAO {

    public List<Reservation> getSpecificReservation(String staffID, String day, String serviceID) {
        List<Reservation> list = new ArrayList<>();
        xSql = "SELECT * from [dbo].[Reservations] where StaffID = ? and DAY(ReservationDate) = ? and ServiceID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, staffID);
            ps.setInt(2, Integer.parseInt(day));
            ps.setString(3, serviceID);
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

    public static void main(String args[]) {
        ReservationDAO rd = new ReservationDAO();

        List<Integer> a = new ArrayList<>();
        a.add(1);
        a.add(2);
        a.add(3);
        for (Reservation r : rd.getSpecificReservation("3", "21", "1")) {
            a.remove(Integer.valueOf(r.getReservationSlot()));
        }
        for (int b : a) {
            System.out.println(b);
        }
    }
}
