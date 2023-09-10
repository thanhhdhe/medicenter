/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

/**
 *
 * @author pc
 */
public class UserDAO extends MyDAO{
    
    //reset password by User ID(ten dang nhap)
    public void resetPassword(String newPassword, String UserID){
        xSql="update Student set Password= ? where UserID= ?";
        try {      
        ps = con.prepareStatement(xSql);
        ps.setString(1, newPassword);
        ps.setString(2, UserID);
        ps.executeUpdate();
        ps.close();
     }
      catch(Exception e) {
        e.printStackTrace();
      }
    }
    
}
