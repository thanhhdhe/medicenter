/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.sql.Date;
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
                int serviceID = rs.getInt("ServiceID");
                String medicalPrescription = rs.getString("MedicalPrescription");
                String disease = rs.getString("Disease");
                MedicalExamination medicalExamination = new MedicalExamination(MedicalExaminationID, MuserID, MchildrenID, MstaffID, examinationDate, serviceID, medicalPrescription, disease);
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
                int serviceID = rs.getInt("ServiceID");
                String medicalPrescription = rs.getString("MedicalPrescription");
                String disease = rs.getString("Disease");
                MedicalExamination medicalExamination = new MedicalExamination(MedicalExaminationID, MuserID, MchildrenID, MstaffID, examinationDate, serviceID, medicalPrescription, disease);
                medicalExaminationList.add(medicalExamination);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return medicalExaminationList;
    }
    
    public static void main(String[] args) {
        MedicalExaminationDAO medicalExaminationDAO = new MedicalExaminationDAO();
        List<MedicalExamination> listMedicalExamination = medicalExaminationDAO.getMedicalExaminationsByStaff(1+"");
        for (MedicalExamination medicalExamination : listMedicalExamination) {
            System.out.println(medicalExamination.getServiceID());
        }
    }
}
