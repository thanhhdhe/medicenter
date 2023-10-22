/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.sql.Date;
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

    //get total fillter
    public int getTotalFeedbackFill(String Fill, String Fillter) {
        xSql = "select COUNT(*) from Feedbacks where " + Fillter + "= ?;";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, Fill);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {

        }
        return 0;
    }

    //get total service name
    public int getTotalFeedbackFillSer(String Fill) {
        xSql = "SELECT COUNT(*) \n"
                + "FROM Feedbacks F\n"
                + "INNER JOIN MedicalExaminations M ON F.MedicalExaminationID = M.MedicalExaminationID\n"
                + "WHERE M.ServiceID = ?;";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, Fill);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {

        }
        return 0;
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

    // fillter
    public List<FeedBack> getPageFeedBackByFill(int index, String Fill, String Fillter) {
        List<FeedBack> feedbacks = new ArrayList<>();
        if(index == -1){
            xSql = "select * from Feedbacks\n"
                + "where " + Fillter + " = ?\n"
                + "ORDER BY FeedbackID;";
        } else {
            xSql = "select * from Feedbacks\n"
                + "where " + Fillter + " = ?\n"
                + "ORDER BY FeedbackID\n"
                + "OFFSET ? Rows fetch next 10 rows ONLY;";
        }
        
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, Fill);
            // set index for offser (page)
            
            if (index != -1) {
                ps.setInt(2, (index - 1) * 10);
            } //page 0 -> index 0 page 1 -> index 10
            rs = ps.executeQuery();
            while (rs.next()) {
                feedbacks.add(new FeedBack(rs.getInt(1), rs.getInt(2), rs.getInt(3),
                        rs.getString(4), rs.getString(5), rs.getInt(6),
                        rs.getString(7)));
            }
        } catch (Exception e) {

        }
        return (feedbacks);
    }

    //get name service feedback
    public int getNameServiceFeed(String MedicalExaminationID) {
        xSql = "select ServiceID from MedicalExaminations where MedicalExaminationID = ?;";
        try {
            ps = con.prepareStatement(xSql);

            ps.setString(1, MedicalExaminationID);
            rs = ps.executeQuery();
            if (rs.next()) {
                int name = rs.getInt(1);
                return name;
            }
        } catch (Exception e) {
        }
        return 0;
    }

    //get list for fill service 
    public List<FeedBack> getPageFeedBackByFillSer(int index, String Fill) {
        List<FeedBack> feedbacks = new ArrayList<>();
        if (index == -1) {
            xSql = "SELECT F.*, M.ServiceID\n"
                    + "FROM Feedbacks F\n"
                    + "INNER JOIN MedicalExaminations M ON F.MedicalExaminationID = M.MedicalExaminationID\n"
                    + "WHERE M.ServiceID = ?\n"
                    + "ORDER BY FeedbackID;";
        } else {
            xSql = "SELECT F.*, M.ServiceID\n"
                    + "FROM Feedbacks F\n"
                    + "INNER JOIN MedicalExaminations M ON F.MedicalExaminationID = M.MedicalExaminationID\n"
                    + "WHERE M.ServiceID = ?\n"
                    + "ORDER BY FeedbackID\n"
                    + "OFFSET ? Rows fetch next 10 rows ONLY;";
        }

        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, Fill);
            // set index for offser (page)
            if (index != -1) {
                ps.setInt(2, (index - 1) * 10);
            } //page 0 -> index 0 page 1 -> index 5
            rs = ps.executeQuery();
            while (rs.next()) {
                feedbacks.add(new FeedBack(rs.getInt(1), rs.getInt(2), rs.getInt(3),
                        rs.getString(4), rs.getString(5).substring(0, 10), rs.getInt(6),
                        rs.getString(7), rs.getString(8)));
            }
        } catch (Exception e) {

        }
        return (feedbacks);
    }

    // get list for search
    public List<FeedBack> getPageFeedBacksSearch(String FullName, String Content, int index) {
        List<FeedBack> feedbacks = new ArrayList<>();
        xSql = "SELECT F.*\n"
                + "FROM Feedbacks F\n"
                + "INNER JOIN Users U ON F.UserID = U.UserID\n"
                + "WHERE CONCAT(U.FirstName, ' ', U.LastName) LIKE ? OR F.Content LIKE ?\n"
                + "ORDER BY CONCAT(U.FirstName, ' ', U.LastName), FeedbackID\n"
                + "OFFSET ? Rows fetch next 10 rows ONLY;";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, "%" + FullName + "%");
            ps.setString(2, "%" + Content + "%");
            // set index for offser (page)
            ps.setInt(3, (index - 1) * 10); //page 0 -> index 0 page 1 -> index 5
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

    // get total search
    public int getTotalFeedbackSearch(String searchN) {
        xSql = "SELECT COUNT(*) AS TotalCount\n"
                + "FROM Feedbacks F\n"
                + "INNER JOIN Users U ON F.UserID = U.UserID\n"
                + "WHERE CONCAT(U.FirstName, ' ', U.LastName) LIKE ? OR F.Content LIKE ?;";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, "%" + searchN + "%");
            ps.setString(2, "%" + searchN + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {

        }
        return 0;
    }

    //get sort by full name 
    public List<FeedBack> getSortByName(int index) {
        List<FeedBack> feedbacks = new ArrayList<>();
        xSql = "SELECT F.*\n"
                + "FROM Feedbacks F\n"
                + "INNER JOIN Users U ON F.UserID = U.UserID\n"
                + "ORDER BY CONCAT(U.FirstName, ' ', U.LastName)\n"
                + "OFFSET ? Rows fetch next 10 rows ONLY;";
        try {
            ps = con.prepareStatement(xSql);
            // set index for offser (page)
            ps.setInt(1, (index - 1) * 10); //page 0 -> index 0 page 1 -> index 5
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

    //get sort by  service
    public List<FeedBack> getSortByService(int index) {
        List<FeedBack> feedbacks = new ArrayList<>();
        xSql = "SELECT F.*, M.ServiceID\n"
                + "FROM Feedbacks F\n"
                + "INNER JOIN MedicalExaminations M ON F.MedicalExaminationID = M.MedicalExaminationID\n"
                + "Order by ServiceID\n"
                + "OFFSET ? Rows fetch next 10 rows ONLY;";
        try {
            ps = con.prepareStatement(xSql);
            // set index for offser (page)
            ps.setInt(1, (index - 1) * 10); //page 0 -> index 0 page 1 -> index 5
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

    //get sort by rate star
    public List<FeedBack> getSortByRate(int index) {
        List<FeedBack> feedbacks = new ArrayList<>();
        xSql = "select * from Feedbacks\n"
                + "ORDER BY RatedStar\n"
                + "OFFSET ? Rows fetch next 10 rows ONLY;";
        try {
            ps = con.prepareStatement(xSql);
            // set index for offser (page)
            ps.setInt(1, (index - 1) * 10); //page 0 -> index 0 page 1 -> index 5
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

    //get sort by status
    public List<FeedBack> getSortByStatus(int index) {
        List<FeedBack> feedbacks = new ArrayList<>();
        xSql = "select * from Feedbacks\n"
                + "ORDER BY FStatus\n"
                + "OFFSET ? Rows fetch next 10 rows ONLY;";
        try {
            ps = con.prepareStatement(xSql);
            // set index for offser (page)
            ps.setInt(1, (index - 1) * 10); //page 0 -> index 0 page 1 -> index 5
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

    //get list paging feedback 
    public List<FeedBack> getPageFeedBacks(int index) {
        List<FeedBack> feedbacks = new ArrayList<>();
        xSql = "select * from Feedbacks\n"
                + "ORDER BY FeedbackID\n"
                + "OFFSET ? Rows fetch next 10 rows ONLY;";
        try {
            ps = con.prepareStatement(xSql);
            // set index for offser (page)
            ps.setInt(1, (index - 1) * 10); //page 0 -> index 0 page 1 -> index 5
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

    // get feed back detail
    public FeedBack getFeedbackDetail(int feedbackid) {
        xSql = "SELECT\n"
                + "    U.FirstName AS UserFirstName,\n"
                + "    U.LastName AS UserLastName,\n"
                + "    U.Email AS UserEmail,\n"
                + "    U.PhoneNumber AS UserPhoneNumber,\n"
                + "    F.Content AS FeedbackContent,\n"
                + "    F.FeedbackDate AS FeedbackDate,\n"
                + "    F.RatedStar AS FeedbackRatedStar,\n"
                + "    F.FStatus AS FeedbackStatus,\n"
                + "    M.ServiceID AS ServiceID,\n"
                + "    M.MedicalPrescription AS MedicalPrescription\n"
                + "FROM Feedbacks F\n"
                + "INNER JOIN Users U ON F.UserID = U.UserID\n"
                + "INNER JOIN MedicalExaminations M ON F.MedicalExaminationID = M.MedicalExaminationID\n"
                + "where FeedbackID = ?;";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, feedbackid);
            rs = ps.executeQuery();
            if (rs.next()) {
                FeedBack feeback = new FeedBack(rs.getString(1) + " " + rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7),
                        rs.getString(8), rs.getString(9), rs.getString(10));
                return feeback;
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // update
    public void updateStatus(String status, int FID) {
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
                        rs.getDate(5), rs.getInt(6), rs.getString(7), rs.getString(8)));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (medical);
    }

    public float getTotalAverageStarByDay(Date startDate, Date endDate) {
        xSql = "select AVG(Cast(f.RatedStar as Float)) as AverageStar from Feedbacks f "
                //                + "left join MedicalExaminations m on f.MedicalExaminationID = m.MedicalExaminationID "
                + "where DATEDIFF(DAY, ? ,f.FeedbackDate) >= 0 AND DATEDIFF(DAY, ?, f.FeedbackDate) <= 0";
        try {
            ps = con.prepareStatement(xSql);
            ps.setDate(1, startDate);
            ps.setDate(2, endDate);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getFloat("AverageStar");
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public float getAverageStarByDayAndService(Date startDate, Date endDate, int serviceID) {
        xSql = "select AVG(Cast(f.RatedStar as Float)) as AverageStar from Feedbacks f "
                + "left join MedicalExaminations m on f.MedicalExaminationID = m.MedicalExaminationID "
                + "where DATEDIFF(DAY, ? ,f.FeedbackDate) >= 0 AND DATEDIFF(DAY, ?, f.FeedbackDate) <= 0 AND m.ServiceID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setDate(1, startDate);
            ps.setDate(2, endDate);
            ps.setInt(3, serviceID);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getFloat("AverageStar");
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        FeedBackDAO dao = new FeedBackDAO();
        //System.out.println(dao.getTotalFeedback());

//          FeedBack fe = dao.getFeedbackDetail(10);
//          System.out.println(fe.getFullName());
//        for (MedicalExamination f : feedbacks) {
//            System.out.println(f.getMchildrenID());
//        }
    }
}
