/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.util.ArrayList;
import java.util.List;
import model.MedicalExamination;

/**
 *
 * @author pc
 */
public class FeedBackDAO extends MyDAO {

    public void InsertFeedBack(int userID, int MedicalID, String content, int ratestar) {
        xSql = "INSERT INTO Feedbacks (UserID, MedicalExaminationID, Content, FeedbackDate, RatedStar)\n"
                + "VALUES (?, ?, ?, GETDATE(), ?);";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, userID);
            ps.setInt(2, MedicalID);
            ps.setString(3, content);

            ps.setInt(4, ratestar);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //lay medical chua feedback
    public List<MedicalExamination> getMedicalExamination(int userID) {
        List<MedicalExamination> medical = new ArrayList<>();
        xSql = "SELECT ME.*\n"
                + "FROM MedicalExaminations ME\n"
                + "LEFT JOIN Feedbacks F ON ME.MedicalExaminationID = F.MedicalExaminationID\n"
                + "WHERE F.MedicalExaminationID IS NULL AND ME.UserID = ?;";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, userID);
            rs = ps.executeQuery();
            while (rs.next()) {
                medical.add(new MedicalExamination(rs.getInt(1), rs.getInt(2),
                        rs.getInt(3), rs.getInt(4),
                        rs.getString(5), rs.getString(6), rs.getString(7)));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (medical);
    }

    public static void main(String[] args) {
        FeedBackDAO dao = new FeedBackDAO();
       
        List<MedicalExamination> medical = dao.getMedicalExamination(1);
        System.out.println(medical.get(0).getUsedServices());
    }
}
