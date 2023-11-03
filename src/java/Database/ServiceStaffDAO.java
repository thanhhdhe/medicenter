/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hbich
 */
public class ServiceStaffDAO extends MyDAO {

    public boolean checkExist(String StaffID, String ServiceID) {
        xSql = "SELECT * FROM [dbo].[ServiceStaff] WHERE StaffID = ? AND ServiceID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, StaffID);
            ps.setString(2, ServiceID);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Integer> getListStaffIDCanWork(String selectedDate, String selectedMonth, String selectedYear, String slot, String serviceID) {
        List<Integer> list = new ArrayList<>();
        xSql = "select ss1.ServiceID, ss1.StaffID, ss2.Workday, ss2.Slot, r.ReservationID, r.status from ServiceStaff ss1\n"
                + "join StaffSchedules ss2 on ss1.StaffID = ss2.StaffID and ss2.Status = 'confirm'\n"
                + "left join Reservations r on r.StaffID = ss1.StaffID and r.ServiceID = ss1.ServiceID and r.ReservationDate = ss2.Workday and r.ReservationSlot = ss2.Slot and r.Status != 'cancel'\n"
                + "where DAY(ss2.Workday) = ? and MONTH(ss2.Workday) = ? and YEAR(ss2.Workday) = ? and ss1.ServiceID = ? and ss2.slot = ? and r.ReservationID is null";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, selectedDate);
            ps.setString(2, selectedMonth);
            ps.setString(3, selectedYear);
            ps.setString(4, serviceID);
            ps.setString(5, slot);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("StaffID"));
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Integer> getServiceByDoc(int DocID) {
        xSql = "Select ServiceID from ServiceStaff where StaffID = ? ";
        List<Integer> serviceIDList = new ArrayList<>();
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, DocID);
            rs = ps.executeQuery();
            while (rs.next()) {
                serviceIDList.add(rs.getInt("ServiceID"));
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceIDList;
    }

    public static void main(String args[]) {
        ServiceStaffDAO ssd = new ServiceStaffDAO();
        for (int i : ssd.getListStaffIDCanWork("29", "10", "2023", "3", "9")) {
            System.out.println(i);
        }
        
        System.out.println(ssd.getServiceByDoc(1));
    }
}
