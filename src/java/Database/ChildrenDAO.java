/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import model.Children;

/**
 *
 * @author Admin
 */
public class ChildrenDAO extends MyDAO{
    
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
                String childName = rs.getString("ChildName");
                Date birthday = rs.getDate("Birthday");
                String status = rs.getString("Status");
                String gender = rs.getString("Gender");
                String avatar = rs.getString("Avatar");
                children.add(new Children(childID, userID, childName, birthday, status, gender, avatar));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return children;
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
                String childName = rs.getString("ChildName");
                Date birthday = rs.getDate("Birthday");
                String status = rs.getString("Status");
                String gender = rs.getString("Gender");
                String avatar = rs.getString("Avatar");
                children = new Children(childID, userID, childName, birthday, status, gender, avatar);
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
                String childName = rs.getString("ChildName");
                Date birthday = rs.getDate("Birthday");
                String status = rs.getString("Status");
                String gender = rs.getString("Gender");
                String avatar = rs.getString("Avatar");
                children = new Children(childID, userID, childName, birthday, status, gender, avatar);
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
    public static void main(String[] args) {
        ChildrenDAO childrenDAO = new ChildrenDAO();
        System.out.println(childrenDAO.getChildrenByChildrenId(1+"").getChildName());
    }
}
