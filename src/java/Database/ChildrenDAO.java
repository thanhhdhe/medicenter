/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import model.Children;
import model.Relationship;
import model.User;

/**
 *
 * @author Admin
 */
public class ChildrenDAO extends MyDAO {

    public List<Children> getListChildrenByUserId(String id) {
        List<Children> children = new ArrayList<>();
        xSql = "select * from Children where UserID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                int childID = rs.getInt("ChildID");
                int userID = rs.getInt("UserID");
                UserDAO userDao = new UserDAO();
                User user = userDao.getUserByID(userID);
                String childName = rs.getString("ChildName");
                Date birthday = rs.getDate("Birthday");
                String status = rs.getString("Status");
                String gender = rs.getString("Gender");
                String avatar = rs.getString("Avatar");
                int relationshipID = rs.getInt("RelationshipID");
                RelationshipDAO re = new RelationshipDAO();
                Relationship relationship = re.getRelationByID(relationshipID);
                children.add(new Children(user, childID, childName, birthday, status, gender, avatar, relationship));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return children;
    }

    public boolean createChildren(Children children) {
        boolean success = false;
        xSql = "INSERT INTO Children (UserID, ChildName, Birthday, Gender, Avatar, RelationshipID) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, children.getUser().getUserID()); // Lấy userID từ đối tượng User
            ps.setString(2, children.getChildName());
            ps.setDate(3, children.getBirthday());
            ps.setString(4, children.getGender());
            ps.setString(5, children.getImage());
            ps.setInt(6, children.getRelationship().getRelationshipID());
            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                success = true;
            }

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public Children getChildrenByUserId(String id, String Cid) {
        Children children = null;
        xSql = "select * from Children where UserID = ? and ChildID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, id);
            ps.setString(2, Cid);
            rs = ps.executeQuery();
            if (rs.next()) {
                int childID = rs.getInt("ChildID");
                int userID = rs.getInt("UserID");
                UserDAO userDao = new UserDAO();
                User user = userDao.getUserByID(userID);
                String childName = rs.getString("ChildName");
                Date birthday = rs.getDate("Birthday");
                String status = rs.getString("Status");
                String gender = rs.getString("Gender");
                String avatar = rs.getString("Avatar");
                int relationshipID = rs.getInt("RelationshipID");
                RelationshipDAO re = new RelationshipDAO();
                Relationship relationship = re.getRelationByID(relationshipID);
                children = new Children(user, childID, childName, birthday, status, gender, avatar, relationship);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return children;
    }

    public Children getChildrenByChildrenId(String id) {
        Children children = null;
        xSql = "SELECT *  FROM [dbo].[Children] where ChildID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                int childID = rs.getInt("ChildID");
                int userID = rs.getInt("UserID");
                UserDAO userDao = new UserDAO();
                User user = userDao.getUserByID(userID);
                String childName = rs.getString("ChildName");
                Date birthday = rs.getDate("Birthday");
                String status = rs.getString("Status");
                String gender = rs.getString("Gender");
                String avatar = rs.getString("Avatar");
                int relationshipID = rs.getInt("RelationshipID");
                RelationshipDAO re = new RelationshipDAO();
                Relationship relationship = re.getRelationByID(relationshipID);
                children = new Children(user, childID, childName, birthday, status, gender, avatar, relationship);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return children;
    }

    public boolean validateChildren(String id, String userID) {
        boolean check = false;
        xSql = "SELECT *  FROM [dbo].[Children] where ChildID = ? and UserID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, id);
            ps.setString(2, userID);
            rs = ps.executeQuery();
            if (rs.next()) {
                check = true;
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public int countChildren() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM [dbo].[Children]";

        try {
            ps = con.prepareStatement(sql);
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

    public boolean deleteChild(String childID) {
        boolean success = false;
        xSql = "DELETE FROM [dbo].[Children] WHERE ChildID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, childID);
            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                success = true;
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public static void main(String[] args) {
        ChildrenDAO childrenDAO = new ChildrenDAO();

//        System.out.println("ketquadelete là : " + childrenDAO.deleteChild("5"));
    }

}
