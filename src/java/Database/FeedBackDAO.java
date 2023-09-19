/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;



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
  
//    public static void main(String[] args) {
//        FeedBackDAO dao = new FeedBackDAO();
//       
//        dao.InsertFeedBack(1, 2, "asdkajs" , 5);
//    }
        
}
