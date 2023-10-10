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
                String rank = rs.getString("Rank");
                String specialty = rs.getString("Specialty");
                String introduction = rs.getString("Introduction");
                Staff staff = new Staff(staffID, staffName, password, email, fullName, gender, phoneNumber, profileImage, role, rank, specialty, introduction);
                staffList.add(staff);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staffList;
    }
    
    public List<Staff> getDoctorByServices(String id) {
        List<Staff> doctorList = new ArrayList<>();
        xSql = "select s.StaffID, s.StaffName, s.Email, s.Email, s.Password, s.FullName,s.Gender, s.PhoneNumber, s.ProfileImage, s.StaffRole\n"
                + "		   from Staff s inner join ServiceStaff ss on s.StaffID = ss.StaffID where s.StaffRole = 'doctor' AND ServiceID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, id);
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
                String rank = rs.getString("Rank");
                String specialty = rs.getString("Specialty");
                String introduction = rs.getString("Introduction");
                Staff staff = new Staff(staffID, staffName, password, email, fullName, gender, phoneNumber, profileImage, role, rank, specialty, introduction);
                doctorList.add(staff);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doctorList;
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
                String rank = rs.getString("Rank");
                String specialty = rs.getString("Specialty");
                String introduction = rs.getString("Introduction");
                Staff staff = new Staff(staffID, staffName, password, email, fullName, gender, phoneNumber, profileImage, role, rank, specialty, introduction);
                staffList.add(staff);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staffList;
    }
    
    public Staff getStaffByStaffEmail(String staffEmail) {
        Staff staff = null;
        xSql = "SELECT *  FROM [dbo].[Staff] where Email = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, staffEmail);
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
                String rank = rs.getString("Rank");
                String specialty = rs.getString("Specialty");
                String introduction = rs.getString("Introduction");
                staff = new Staff(staffID, staffName, password, email, fullName, gender, phoneNumber, profileImage, role, rank, specialty, introduction);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staff;
    }
    
    public Staff getStaffByStaffId(int staffID) {
        Staff staff = null;
        xSql = "SELECT *  FROM [dbo].[Staff] where StaffID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, staffID);
            rs = ps.executeQuery();
            while (rs.next()) {
                String staffName = rs.getString("StaffName");
                String password = rs.getString("Password");
                String email = rs.getString("Email");
                String fullName = rs.getString("FullName");
                String gender = rs.getString("Gender");
                String phoneNumber = rs.getString("PhoneNumber");
                String profileImage = rs.getString("ProfileImage");
                String role = rs.getString("StaffRole");
                String rank = rs.getString("Rank");
                String specialty = rs.getString("Specialty");
                String introduction = rs.getString("Introduction");
                staff = new Staff(staffID, staffName, password, email, fullName, gender, phoneNumber, profileImage, role, rank, specialty, introduction);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staff;
    }
    
    public static void main(String[] args) {
        StaffDAO staffDAO = new StaffDAO();
//        List<Staff> staffList = staffDAO.getDoctorByServices("3");
//        for (Staff staff : staffList) {
//            System.out.println(staff.getFullName());
//        }
        System.out.println(staffDAO.getStaffByStaffEmail("lethanglrf@gmail.com").getFullName());
        
    }
}
