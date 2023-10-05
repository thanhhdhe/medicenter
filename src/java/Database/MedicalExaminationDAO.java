/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.MedicalExamination;

/**
 *
 * @author Admin
 */
public class MedicalExaminationDAO extends MyDAO{
    public List<MedicalExamination> getAllMedicalExaminations() {
        List<MedicalExamination> medicalExaminationList = new ArrayList<>();
        xSql = "SELECT *  FROM [dbo].[MedicalExaminations]";
        try {
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int MedicalExaminationID = rs.getInt("MedicalExaminationID");
                int MuserID = rs.getInt("UserID");
                int MchildrenID = rs.getInt("ChildrenID");
                int MstaffID = rs.getInt("StaffID");
                Date examinationDate = rs.getDate("ExaminationDate");
                int medicalExaminationID = rs.getInt("MedicalExaminationID");
                String medicalPrescription = rs.getString("MedicalPrescription");
                String disease = rs.getString("Disease");
                MedicalExamination medicalExamination = new MedicalExamination(MedicalExaminationID, MuserID, MchildrenID, MstaffID, examinationDate, medicalExaminationID, medicalPrescription, disease);
                medicalExaminationList.add(medicalExamination);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return medicalExaminationList;
    }
    
    public List<MedicalExamination> getMedicalExaminationsByStaff(String staffID) {
        List<MedicalExamination> medicalExaminationList = new ArrayList<>();
        xSql = "SELECT *  FROM [dbo].[MedicalExaminations] WHERE StaffID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, staffID);
            rs = ps.executeQuery();
            while (rs.next()) {
                int MedicalExaminationID = rs.getInt("MedicalExaminationID");
                int MuserID = rs.getInt("UserID");
                int MchildrenID = rs.getInt("ChildrenID");
                int MstaffID = rs.getInt("StaffID");
                Date examinationDate = rs.getDate("ExaminationDate");
                int medicalExaminationID = rs.getInt("MedicalExaminationID");
                String medicalPrescription = rs.getString("MedicalPrescription");
                String disease = rs.getString("Disease");
                MedicalExamination medicalExamination = new MedicalExamination(MedicalExaminationID, MuserID, MchildrenID, MstaffID, examinationDate, medicalExaminationID, medicalPrescription, disease);
                medicalExaminationList.add(medicalExamination);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return medicalExaminationList;
    }
    
    public List<MedicalExamination> getMedicalExaminationsByChild(String childID) {
        List<MedicalExamination> medicalExaminationList = new ArrayList<>();
        xSql = "SELECT *  FROM [dbo].[MedicalExaminations] WHERE ChildrenID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, childID);
            rs = ps.executeQuery();
            while (rs.next()) {
                int MedicalExaminationID = rs.getInt("MedicalExaminationID");
                int MuserID = rs.getInt("UserID");
                int MchildrenID = rs.getInt("ChildrenID");
                int MstaffID = rs.getInt("StaffID");
                Date examinationDate = rs.getDate("ExaminationDate");
                int medicalExaminationID = rs.getInt("MedicalExaminationID");
                String medicalPrescription = rs.getString("MedicalPrescription");
                String disease = rs.getString("Disease");
                MedicalExamination medicalExamination = new MedicalExamination(MedicalExaminationID, MuserID, MchildrenID, MstaffID, examinationDate, medicalExaminationID, medicalPrescription, disease);
                medicalExaminationList.add(medicalExamination);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return medicalExaminationList;
    }
    
    public MedicalExamination getMedicalExaminationsByID(String id) {
        MedicalExamination medicalExamination = null;
        xSql = "SELECT *  FROM [dbo].[MedicalExaminations] WHERE MedicalExaminationID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                int MedicalExaminationID = rs.getInt("MedicalExaminationID");
                int MuserID = rs.getInt("UserID");
                int MchildrenID = rs.getInt("ChildrenID");
                int MstaffID = rs.getInt("StaffID");
                Date examinationDate = rs.getDate("ExaminationDate");
                int medicalExaminationID = rs.getInt("MedicalExaminationID");
                String medicalPrescription = rs.getString("MedicalPrescription");
                String disease = rs.getString("Disease");
                medicalExamination = new MedicalExamination(MedicalExaminationID, MuserID, MchildrenID, MstaffID, examinationDate, medicalExaminationID, medicalPrescription, disease);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return medicalExamination;
    }
    
    
    public void update(MedicalExamination medicalExamination) {
        xSql = "UPDATE [dbo].[MedicalExaminations]\n" +
            "   SET [UserID] = ?,[ChildrenID] = ?,[StaffID] =?,[ExaminationDate] = ?,[ServiceID] = ? ,[MedicalPrescription] =? ,[Disease] = ?\n" +
            " WHERE MedicalExaminationID =?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, medicalExamination.getMuserID());
            ps.setInt(2, medicalExamination.getMchildrenID());
            ps.setInt(3, medicalExamination.getMstaffID());
            ps.setDate(4, medicalExamination.getExaminationDate());
            ps.setDouble(5, medicalExamination.getMuserID());
            ps.setString(6, medicalExamination.getMedicalPrescription());
            ps.setString(7, medicalExamination.getDisease());
            ps.setInt(8, medicalExamination.getMedicalExaminationID());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            // Handle exception appropriately
            System.out.println(e);
        }
    }
    
    public void insert(MedicalExamination medicalExamination) {
        xSql = "INSERT INTO [dbo].[MedicalExaminations] ([UserID],[ChildrenID],[StaffID] ,[ExaminationDate] ,[ServiceID],[MedicalPrescription],[Disease])\n" +
"     VALUES (?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, medicalExamination.getMuserID());
            ps.setInt(2, medicalExamination.getMchildrenID());
            ps.setInt(3, medicalExamination.getMstaffID());
            ps.setDate(4, medicalExamination.getExaminationDate());
            ps.setDouble(5, medicalExamination.getMuserID());
            ps.setString(6, medicalExamination.getMedicalPrescription());
            ps.setString(7, medicalExamination.getDisease());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            // Handle exception appropriately
            System.out.println(e);
        }
    }
    
    public void delete(String id) {
        xSql = "DELETE FROM [dbo].[MedicalExaminations]\n" +
"      WHERE MedicalExaminationID =?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            // Handle exception appropriately
            System.out.println(e);
        }
    }
    
    public static void main(String[] args) {
        MedicalExaminationDAO medicalExaminationDAO = new MedicalExaminationDAO();
//        List<MedicalExamination> listMedicalExamination = medicalExaminationDAO.getMedicalExaminationsByStaff(1+"");
//        for (MedicalExamination medicalExamination : listMedicalExamination) {
//            System.out.println(medicalExamination.getMedicalExaminationID());
//        }
        System.out.println(medicalExaminationDAO.getMedicalExaminationsByID("1").getDisease());
    }
}
