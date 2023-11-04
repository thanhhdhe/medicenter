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
        xSql = "select s.StaffID, s.StaffName, s.Email, s.Email, s.Password, s.FullName,s.Gender, s.PhoneNumber, s.ProfileImage, s.StaffRole, s.Rank, s.Specialty,s.Introduction\n"
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
                String depthStudy = rs.getString("DepthStudy");
                String professionalAchievements = rs.getString("ProfessionalAchievements");
                String specializedActivities = rs.getString("SpecializedActivities");

                staff = new Staff(staffID, staffName, password, email, fullName, gender, phoneNumber, profileImage, role, rank, specialty, introduction);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staff;
    }

    public Staff getDocByStaffId(int staffID) {
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
                String depthStudy = rs.getString("DepthStudy");
                String professionalAchievements = rs.getString("ProfessionalAchievements");
                String specializedActivities = rs.getString("SpecializedActivities");

                staff = new Staff(staffID, fullName, gender, phoneNumber, profileImage, rank, specialty, introduction, specializedActivities, professionalAchievements, depthStudy);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staff;
    }

    public List<Staff> getStaffsBySlot(String reservationDate, String slot) {
        List<Staff> staffList = new ArrayList<>();
        xSql = "SELECT DISTINCT *\n"
                + "FROM Staff S\n"
                + "WHERE S.StaffRole = 'Doctor'\n"
                + "AND S.StaffID NOT IN (\n"
                + "    SELECT R.StaffID\n"
                + "    FROM Reservations R\n"
                + "    WHERE R.ReservationDate = ?  \n"
                + "    AND R.ReservationSlot = ?  \n"
                + ")";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, reservationDate);
            ps.setString(2, slot);
            rs = ps.executeQuery();
            while (rs.next()) {
                int staffID = rs.getInt(1);
                String staffName = rs.getString(2);
                String password = rs.getString(3);
                String email = rs.getString(4);
                String fullName = rs.getString(5);
                String gender = rs.getString(6);
                String phoneNumber = rs.getString(7);
                String profileImage = rs.getString(8);
                String role = rs.getString(9);
                String rank = rs.getString(10);
                String specialty = rs.getString(11);
                String introduction = rs.getString(12);
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

    public List<Staff> getStaffListPage(int offSetPage, int numberOfPage, String keyword, String ServiceID) {
        List<Staff> staffList = new ArrayList<>();
        xSql = " select distinct s.staffID, s.FullName, s.Gender, s.PhoneNumber, s.ProfileImage, s.Rank, s.Specialty, s.Introduction, s.SpecializedActivities, s.ProfessionalAchievements,s.DepthStudy "
                + "from staff as s join ServiceStaff as ss on s.StaffID= ss.StaffID "
                + "where s.StaffRole= 'doctor' and s.FullName like ? ";

        if (!ServiceID.isEmpty()) {
            xSql += " and ss.ServiceID = ? ";
        }
        xSql += "    ORDER BY s.FullName DESC "
                + "    OFFSET ? ROWS "
                + "    FETCH NEXT ? ROWS ONLY ";
        try {
            int index = 2;
            ps = con.prepareStatement(xSql);
            ps.setString(1, "%" + keyword + "%");
            if (!ServiceID.isEmpty()) {
                ps.setString(index, ServiceID);
                index++;
            }
            ps.setInt(index, offSetPage);
            index++;
            ps.setInt(index, numberOfPage);
            rs = ps.executeQuery();
            while (rs.next()) {
                int staffID = rs.getInt("staffID");
                String fullName = rs.getString("FullName");
                String gender = rs.getString("Gender");
                String phoneNumber = rs.getString("PhoneNumber");
                String profileImage = rs.getString("ProfileImage");
                String rank = rs.getString("Rank");
                String specialty = rs.getString("Specialty");
                String introduction = rs.getString("Introduction");
                String SpecializedActivities = rs.getString("SpecializedActivities");
                String ProfessionalAchievements = rs.getString("ProfessionalAchievements");
                String DepthStudy = rs.getString("DepthStudy");
                Staff staff = new Staff(staffID, fullName, gender, phoneNumber, profileImage, rank, specialty, introduction, SpecializedActivities, ProfessionalAchievements, DepthStudy);
                staffList.add(staff);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staffList;
    }

    public int getNumOfStaffList(String keyword, String ServiceID) {
        xSql = " select COUNT(distinct s.StaffID) from staff as s join ServiceStaff as ss on s.StaffID= ss.StaffID"
                + " where s.StaffRole='doctor' and s.FullName like ? ";
        int numOfPage = 0;
        if (!ServiceID.isEmpty()) {
            xSql += " and ss.ServiceID = ? ";
        }
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, "%" + keyword + "%");
            if (!ServiceID.isEmpty()) {
                ps.setString(2, ServiceID);
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                numOfPage = rs.getInt(1);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return numOfPage;
    }

    public void addStaff(Staff staff) {
        String sql = "INSERT INTO Staff (StaffName, Email, Password, FullName, Gender, PhoneNumber, ProfileImage, StaffRole, Rank, Specialty, Introduction, SpecializedActivities, ProfessionalAchievements, DepthStudy) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, staff.getStaffName());
            ps.setString(2, staff.getEmail());
            ps.setString(3, staff.getPassword());
            ps.setString(4, staff.getFullName());
            ps.setString(5, staff.getGender());
            ps.setString(6, staff.getPhoneNumber());
            ps.setString(7, staff.getProfileImage());
            ps.setString(8, staff.getRole());
            ps.setString(9, staff.getRank());
            ps.setString(10, staff.getSpecialty());
            ps.setString(11, staff.getIntroduction());
            ps.setString(12, staff.getSpecializedActivities());
            ps.setString(13, staff.getProfessionalAchievements());
            ps.setString(14, staff.getDepthStudy());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStaff(Staff staff) {
        String sql = "UPDATE Staff SET Email = ?, Password = ?, FullName = ?, Gender = ?, PhoneNumber = ?, ProfileImage = ?, StaffRole = ?, Rank = ?, Specialty = ?, Introduction = ?, SpecializedActivities = ?, ProfessionalAchievements = ?, DepthStudy = ? WHERE StaffId = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, staff.getEmail());
            ps.setString(2, staff.getPassword());
            ps.setString(3, staff.getFullName());
            ps.setString(4, staff.getGender());
            ps.setString(5, staff.getPhoneNumber());
            ps.setString(6, staff.getProfileImage());
            ps.setString(7, staff.getRole());
            ps.setString(8, staff.getRank());
            ps.setString(9, staff.getSpecialty());
            ps.setString(10, staff.getIntroduction());
            ps.setString(11, staff.getSpecializedActivities());
            ps.setString(12, staff.getProfessionalAchievements());
            ps.setString(13, staff.getDepthStudy());
            ps.setInt(14, staff.getStaffID());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        StaffDAO staffDAO = new StaffDAO();
//        List<Staff> staffList = staffDAO.getStaffsBySlot("2023-10-14", "3");
//        for (Staff staff : staffList) {
//            System.out.println(staff.getFullName());
//        }

        //System.out.println(staffDAO.getStaffByStaffEmail("lethanglrf@gmail.com").getFullName());
//        System.out.println(staffDAO.getStaffByStaffEmail("lethanglrf@gmail.com").getFullName());
//        List<Staff> list = staffDAO.getStaffListPage(0, 6, "", "");
//        for (Staff staff : list) {
//            System.out.println(staff.getFullName());
//        }
//        Staff staff = staffDAO.getStaffByStaffId(4);
//        System.out.println(staff.getRole());
//        staff.setRole("doctor");
//        staffDAO.updateStaff(staff);
//        System.out.println(staff.getRole());
        ServiceDAO serviceDAO = new ServiceDAO();
        System.out.println(serviceDAO.getActiveServiceCount());
//        Staff staff = staffDAO.getStaffByStaffId(3);
//        System.out.println(staff.getFullName());
    }
}
