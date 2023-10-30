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
public class MedicalExaminationDAO extends MyDAO {

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

    public List<MedicalExamination> getPageMedicalExaminationsByStaff(String staffID, int page, int pageSize) {
        List<MedicalExamination> medicalExaminationList = new ArrayList<>();
        xSql = "SELECT *  FROM [dbo].[MedicalExaminations] WHERE StaffID = ? "
                + "ORDER BY ExaminationDate DESC "
                + "OFFSET ? ROWS "
                + "FETCH NEXT ? ROWS ONLY;";
        int offset = (page - 1) * pageSize;
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, staffID);
            ps.setInt(2, offset);
            ps.setInt(3, pageSize);
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

    public List<Integer> getListServiceIDsByStaffID(int staffID) {
        List<Integer> serviceIDs = new ArrayList<>();
        xSql = "SELECT DISTINCT ServiceID FROM MedicalExaminations WHERE StaffID = ?";
        try {
            ps = connection.prepareStatement(xSql);

            ps.setInt(1, staffID);
            rs = ps.executeQuery();
            while (rs.next()) {
                int serviceID = rs.getInt("ServiceID");
                serviceIDs.add(serviceID);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace(); // Handle or log the exception as per your requirement
        }

        return serviceIDs;
    }

    public List<Integer> getListServiceIDs() {
        List<Integer> serviceIDs = new ArrayList<>();
        xSql = "SELECT DISTINCT ServiceID FROM MedicalExaminations";
        try {
            ps = connection.prepareStatement(xSql);

            rs = ps.executeQuery();
            while (rs.next()) {
                int serviceID = rs.getInt("ServiceID");
                serviceIDs.add(serviceID);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace(); // Handle or log the exception as per your requirement
        }

        return serviceIDs;
    }

    public List<MedicalExamination> getPageMedicalExaminations(int page, int pageSize) {
        List<MedicalExamination> medicalExaminationList = new ArrayList<>();
        xSql = "SELECT *  FROM [dbo].[MedicalExaminations] "
                + "ORDER BY ExaminationDate DESC "
                + "OFFSET ? ROWS "
                + "FETCH NEXT ? ROWS ONLY;";
        int offset = (page - 1) * pageSize;
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, offset);
            ps.setInt(2, pageSize);
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

    public List<MedicalExamination> getPageMedicalExaminationsByStaffAndChildName(String staffID, String childrenName, int page, int pageSize) {
        List<MedicalExamination> medicalExaminationList = new ArrayList<>();
        xSql = "SELECT * FROM MedicalExaminations me "
                + "INNER JOIN Children c ON me.ChildrenID = c.ChildID "
                + "WHERE me.StaffID = ? AND c.ChildName LIKE ? "
                + "ORDER BY me.ExaminationDate DESC "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        int offset = (page - 1) * pageSize;
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, staffID);
            ps.setString(2, "%" + childrenName + "%");
            ps.setInt(3, offset);
            ps.setInt(4, pageSize);
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

    public List<MedicalExamination> getPageFilterMedicalExaminationsOfStaff(String staffID, String childrenName, int page, int pageSize, String serviceID, String fromDate, String toDate) {
        List<MedicalExamination> medicalExaminationList = new ArrayList<>();
        xSql = "SELECT * FROM MedicalExaminations me "
                + "INNER JOIN Children c ON me.ChildrenID = c.ChildID "
                + "WHERE me.StaffID = ? AND c.ChildName LIKE ? ";
        if (serviceID != null && !serviceID.isEmpty()) {
            xSql += "AND me.ServiceID = ? ";
        }
        if (fromDate != null && !fromDate.isEmpty()) {
            xSql += "AND me.ExaminationDate >= ? ";
        }
        if (toDate != null && !toDate.isEmpty()) {
            xSql += "AND me.ExaminationDate <= ? ";
        }
        xSql += "ORDER BY me.ExaminationDate DESC "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        int offset = (page - 1) * pageSize;
        try {
            ps = con.prepareStatement(xSql);
            int paramIndex = 1;
            ps.setString(paramIndex++, staffID);
            ps.setString(paramIndex++, "%" + childrenName + "%");
            if (serviceID != null && !serviceID.isEmpty()) {
                ps.setString(paramIndex++, serviceID);
            }
            if (fromDate != null && !fromDate.isEmpty()) {
                ps.setDate(paramIndex++, Date.valueOf(fromDate));
            }
            if (toDate != null && !toDate.isEmpty()) {
                ps.setDate(paramIndex++, Date.valueOf(toDate));
            }
            ps.setInt(paramIndex++, offset);
            ps.setInt(paramIndex++, pageSize);
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

    public List<MedicalExamination> getPageFilterMedicalExaminations(String childrenName, int page, int pageSize, String serviceID, String fromDate, String toDate) {
        List<MedicalExamination> medicalExaminationList = new ArrayList<>();
        xSql = "SELECT * FROM MedicalExaminations me "
                + "INNER JOIN Children c ON me.ChildrenID = c.ChildID "
                + "WHERE c.ChildName LIKE ? ";
        if (serviceID != null && !serviceID.isEmpty()) {
            xSql += "AND me.ServiceID = ? ";
        }
        if (fromDate != null && !fromDate.isEmpty()) {
            xSql += "AND me.ExaminationDate >= ? ";
        }
        if (toDate != null && !toDate.isEmpty()) {
            xSql += "AND me.ExaminationDate <= ? ";
        }
        xSql += "ORDER BY me.ExaminationDate DESC "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        int offset = (page - 1) * pageSize;
        try {
            ps = con.prepareStatement(xSql);
            int paramIndex = 1;
            ps.setString(paramIndex++, "%" + childrenName + "%");
            if (serviceID != null && !serviceID.isEmpty()) {
                ps.setString(paramIndex++, serviceID);
            }
            if (fromDate != null && !fromDate.isEmpty()) {
                ps.setDate(paramIndex++, Date.valueOf(fromDate));
            }
            if (toDate != null && !toDate.isEmpty()) {
                ps.setDate(paramIndex++, Date.valueOf(toDate));
            }
            ps.setInt(paramIndex++, offset);
            ps.setInt(paramIndex++, pageSize);
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

    public int countMedicalExaminationsByStaffAndChildName(String staffID, String childrenName) {
        int count = 0;
        xSql = "SELECT COUNT(*) AS RecordCount\n"
                + "FROM (SELECT me.MedicalExaminationID FROM MedicalExaminations me "
                + "INNER JOIN Children c ON me.ChildrenID = c.ChildID "
                + "WHERE me.StaffID = ? AND c.ChildName LIKE ? "
                + ") AS SubQuery;";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, staffID);
            ps.setString(2, "%" + childrenName + "%");
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public int countFilterMedicalExaminationsOfStaff(String staffID, String childrenName, String serviceID, String fromDate, String toDate) {
        int count = 0;
        xSql = "SELECT COUNT(*) AS RecordCount\n"
                + "FROM (SELECT me.MedicalExaminationID FROM MedicalExaminations me "
                + "INNER JOIN Children c ON me.ChildrenID = c.ChildID "
                + "WHERE me.StaffID = ? AND c.ChildName LIKE ? ";
        if (serviceID != null && !serviceID.isEmpty()) {
            xSql += "AND me.ServiceID = ?";
        }
        if (fromDate != null && !fromDate.isEmpty()) {
            xSql += "AND me.ExaminationDate >= ? ";
        }
        if (toDate != null && !toDate.isEmpty()) {
            xSql += "AND me.ExaminationDate <= ? ";
        }
        xSql += ") AS SubQuery;";
        try {
            ps = con.prepareStatement(xSql);
            int paramIndex = 1;
            ps.setString(paramIndex++, staffID);
            ps.setString(paramIndex++, "%" + childrenName + "%");
            if (serviceID != null && !serviceID.isEmpty()) {
                ps.setString(paramIndex++, serviceID);
            }
            if (fromDate != null && !fromDate.isEmpty()) {
                ps.setDate(paramIndex++, Date.valueOf(fromDate));
            }
            if (toDate != null && !toDate.isEmpty()) {
                ps.setDate(paramIndex++, Date.valueOf(toDate));
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public int countFilterMedicalExaminations(String childrenName, String serviceID, String fromDate, String toDate) {
        int count = 0;
        xSql = "SELECT COUNT(*) AS RecordCount\n"
                + "FROM (SELECT me.MedicalExaminationID FROM MedicalExaminations me "
                + "INNER JOIN Children c ON me.ChildrenID = c.ChildID "
                + "WHERE c.ChildName LIKE ? ";
        if (serviceID != null && !serviceID.isEmpty()) {
            xSql += "AND me.ServiceID = ?";
        }
        if (fromDate != null && !fromDate.isEmpty()) {
            xSql += "AND me.ExaminationDate >= ? ";
        }
        if (toDate != null && !toDate.isEmpty()) {
            xSql += "AND me.ExaminationDate <= ? ";
        }
        xSql += ") AS SubQuery;";
        try {
            ps = con.prepareStatement(xSql);
            int paramIndex = 1;
            ps.setString(paramIndex++, "%" + childrenName + "%");
            if (serviceID != null && !serviceID.isEmpty()) {
                ps.setString(paramIndex++, serviceID);
            }
            if (fromDate != null && !fromDate.isEmpty()) {
                ps.setDate(paramIndex++, Date.valueOf(fromDate));
            }
            if (toDate != null && !toDate.isEmpty()) {
                ps.setDate(paramIndex++, Date.valueOf(toDate));
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public int countMedicalExaminations(String childrenName) {
        int count = 0;
        xSql = "SELECT COUNT(*) AS RecordCount\n"
                + "FROM (SELECT me.MedicalExaminationID FROM MedicalExaminations me "
                + "INNER JOIN Children c ON me.ChildrenID = c.ChildID "
                + "WHERE  c.ChildName LIKE ? "
                + ") AS SubQuery;";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, "%" + childrenName + "%");
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public int countMedicalExaminationsByStaff(String staffID) {
        int count = 0;
        xSql = "SELECT COUNT(*) AS RecordCount\n"
                + "FROM ( SELECT *  FROM [dbo].[MedicalExaminations] WHERE StaffID = ? "
                + ") AS SubQuery;";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, staffID);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
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

    public List<MedicalExamination> getMedicalExaminationsByUserID(int userID) {
        List<MedicalExamination> medicalExaminationList = new ArrayList<>();
        xSql = "SELECT * FROM [dbo].[MedicalExaminations] WHERE UserID = ?";

        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, userID);
            rs = ps.executeQuery();

            while (rs.next()) {
                int MedicalExaminationID = rs.getInt("MedicalExaminationID");
                int MchildrenID = rs.getInt("ChildrenID");
                int MstaffID = rs.getInt("StaffID");
                Date examinationDate = rs.getDate("ExaminationDate");
                int medicalExaminationID = rs.getInt("MedicalExaminationID");
                String medicalPrescription = rs.getString("MedicalPrescription");
                String disease = rs.getString("Disease");

                MedicalExamination medicalExamination = new MedicalExamination(MedicalExaminationID, userID, MchildrenID, MstaffID, examinationDate, medicalExaminationID, medicalPrescription, disease);
                medicalExaminationList.add(medicalExamination);

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
        xSql = "UPDATE [dbo].[MedicalExaminations]\n"
                + "   SET [UserID] = ?,[ChildrenID] = ?,[StaffID] =?,[ExaminationDate] = ?,[ServiceID] = ? ,[MedicalPrescription] =? ,[Disease] = ?\n"
                + " WHERE MedicalExaminationID =?";
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

    public List<Integer> getListChildrenIDByStaff(String childName, String staffID, int page, int pageSize) {
        List<Integer> childrenIDList = new ArrayList<>();
        String sql = "SELECT DISTINCT me.ChildrenID FROM MedicalExaminations me "
                + "INNER JOIN Children c ON me.ChildrenID = c.ChildID "
                + "WHERE me.StaffID = ? AND c.ChildName LIKE ? "
                + "ORDER BY me.ChildrenID "
                + "OFFSET ? ROWS "
                + "FETCH NEXT ? ROWS ONLY;";
        int offset = (page - 1) * pageSize;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, staffID);
            ps.setString(2, "%" + childName + "%");
            ps.setInt(3, offset);
            ps.setInt(4, pageSize);
            rs = ps.executeQuery();
            while (rs.next()) {
                int childID = rs.getInt("ChildrenID");
                childrenIDList.add(childID);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return childrenIDList;
    }

    public int countListChildrenIDByStaff(String childName, String staffID) {
        int count = 0;
        String sql = "SELECT COUNT(*) AS RecordCount\n"
                + "FROM (SELECT DISTINCT me.ChildrenID FROM MedicalExaminations me "
                + "INNER JOIN Children c ON me.ChildrenID = c.ChildID "
                + "WHERE me.StaffID = ? AND c.ChildName LIKE ?"
                + ") AS SubQuery;";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, staffID);
            ps.setString(2, "%" + childName + "%");
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    public void insert(MedicalExamination medicalExamination) {
        xSql = "INSERT INTO [dbo].[MedicalExaminations] ([UserID],[ChildrenID],[StaffID] ,[ExaminationDate] ,[ServiceID],[MedicalPrescription],[Disease])\n"
                + "     VALUES (?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, medicalExamination.getMuserID());
            ps.setInt(2, medicalExamination.getMchildrenID());
            ps.setInt(3, medicalExamination.getMstaffID());
            ps.setDate(4, medicalExamination.getExaminationDate());
            ps.setInt(5, medicalExamination.getServiceID());
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
        xSql = "DELETE FROM [dbo].[MedicalExaminations]\n"
                + "      WHERE MedicalExaminationID =?";
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
        List<MedicalExamination> listMedicalExamination = medicalExaminationDAO.getPageFilterMedicalExaminationsOfStaff("1", "", 1, 10, "1", "", "");
        for (MedicalExamination medicalExamination : listMedicalExamination) {
            System.out.println(medicalExamination.getMedicalExaminationID());
        }
//        ServiceDAO serviceDAO = new ServiceDAO();
//
//        List<Integer> list = medicalExaminationDAO.getListServiceIDsByStaffID(1);
//        for (Integer integer : list) {
//            System.out.println(serviceDAO.getServiceByID(integer + "").getTitle());
//        }
//        System.out.println(medicalExaminationDAO.countMedicalExaminationsByStaff("1"));
    }
}
