/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.util.ArrayList;
import java.util.List;
import model.FeedBack;
import model.MedicalExamination;

/**
 *
 * @author pc
 */
public class FeedBackDAO extends MyDAO {

    //insert feedback of user
    public void InsertFeedBack(int userID, int MedicalID, String content, int ratestar) {
        xSql = "INSERT INTO Feedbacks (UserID, MedicalExaminationID, Content, FeedbackDate, RatedStar, FStatus)\n"
                + "VALUES (?, ?, ?, GETDATE(), ?, ?);";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, userID);
            ps.setInt(2, MedicalID);
            ps.setString(3, content);
            ps.setInt(4, ratestar);
            ps.setString(5, "Pending");
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //get total feedbacks
    public int getTotalFeedback() {
        xSql = "select COUNT(*) from Feedbacks;";
        try {
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {

        }
        return 0;
    }

    //get list paging feedback 
    public List<FeedBack> getPageFeedBacks(int index) {
        List<FeedBack> feedbacks = new ArrayList<>();
        xSql = "select * from Feedbacks\n"
                + "ORDER BY FeedbackID\n"
                + "OFFSET ? Rows fetch next 5 rows ONLY;";
        try {
            ps = con.prepareStatement(xSql);
            // set index for offser (page)
            ps.setInt(1, (index - 1) * 5); //page 0 -> index 0 page 1 -> index 5
            rs = ps.executeQuery();
            while (rs.next()) {
                feedbacks.add(new FeedBack(rs.getInt(1), rs.getInt(2), rs.getInt(3),
                        rs.getString(4), rs.getString(5).substring(0, 10), rs.getInt(6),
                        rs.getString(7)));
            }
        } catch (Exception e) {

        }
        return (feedbacks);
    }
    
    // update
    public void updateStatus(String status, int FID){
        xSql = "update Feedbacks set FStatus = ? where FeedbackID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, status);
            ps.setInt(2, FID);
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
        dao.updateStatus("Appord", 2);

    }
}
