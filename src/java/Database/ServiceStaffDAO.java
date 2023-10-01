/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

/**
 *
 * @author hbich
 */
public class ServiceStaffDAO extends MyDAO{
    public boolean checkExist(String StaffID, String ServiceID) {
        xSql= "SELECT * FROM [dbo].[ServiceStaff] WHERE StaffID = ? AND ServiceID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, StaffID);
            ps.setString(2, ServiceID);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
