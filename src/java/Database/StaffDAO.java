/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.util.ArrayList;
import java.util.List;
import model.Staff;

/**
 *
 * @author Admin
 */
public class StaffDAO extends MyDAO {

    public List<Staff> getAllStaffs() {
        List<Staff> staffList = new ArrayList<>();
        xSql = "SELECT *  FROM [dbo].[Staff]";
        try {
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int staffID = rs.getInt("StaffID");
                String staffName = rs.getString("StaffName");
                String password = rs.getString("Password");
                String email = rs.getString("Email");
                String fullName = rs.getString("FullName");
                String gender = rs.getString("Gender");
                String phoneNumber = rs.getString("PhoneNumber");
                String profileImage = rs.getString("ProfileImage");
                String role = rs.getString("StaffRole");
                Staff staff = new Staff(staffName, password, email, fullName, gender, phoneNumber, profileImage, role);
                staffList.add(staff);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staffList;
    }

    public List<Staff> getStaffsByRole(String staffRole) {
        List<Staff> staffList = new ArrayList<>();
        xSql = "SELECT *  FROM [dbo].[Staff] where StaffRole = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, staffRole);
            rs = ps.executeQuery();
            while (rs.next()) {
                int staffID = rs.getInt("StaffID");
                String staffName = rs.getString("StaffName");
                String password = rs.getString("Password");
                String email = rs.getString("Email");
                String fullName = rs.getString("FullName");
                String gender = rs.getString("Gender");
                String phoneNumber = rs.getString("PhoneNumber");
                String profileImage = rs.getString("ProfileImage");
                String role = rs.getString("StaffRole");
                Staff staff = new Staff(staffID, staffName, password, email, fullName, gender, phoneNumber, profileImage, role);
                staffList.add(staff);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staffList;
    }

    public static void main(String[] args) {
        StaffDAO staffDAO = new StaffDAO();
        List<Staff> staffList = staffDAO.getStaffsByRole("doctor");
        for (Staff staff : staffList) {
            System.out.println(staff.getStaffID());
        }
    }
}
