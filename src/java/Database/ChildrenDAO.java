/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import model.Children;

/**
 *
 * @author Admin
 */
public class ChildrenDAO extends MyDAO{
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
                int age = rs.getInt("Age");
                String status = rs.getString("Status");
                children = new Children(childID, userID, childName, age, status);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return children;
    }
    public static void main(String[] args) {
        ChildrenDAO childrenDAO = new ChildrenDAO();
        System.out.println(childrenDAO.getChildrenByChildrenId(1+"").getChildName());
    }
}
