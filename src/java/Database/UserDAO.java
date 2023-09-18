/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.sql.Date;
import model.User;

/**
 *
 * @author pc
 */
public class UserDAO extends MyDAO {

    //reset password by User ID(ten dang nhap)
    public void resetPassword(String newPassword, String UserID) {
        xSql = "update Student set Password= ? where UserID= ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, newPassword);
            ps.setString(2, UserID);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean loginAccount(String email, String password) {
        xSql = "select * from [dbo].[Users] where email = ? and password = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public User selectUser(String email) {
        User u = null;
        xSql = "select * from [dbo].[Users] where email = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, email);

            rs = ps.executeQuery();
            while (rs.next()) {
                int userID = rs.getInt("userID");
                String userName = rs.getString("userName");
                String password = rs.getString("password");
                String Email = rs.getString("email");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String gender = rs.getString("gender");
                Date birthday = rs.getDate("birthday");
                String phoneNumber = rs.getString("phoneNumber");
                String profileImage = rs.getString("profileImage");
                String role = rs.getString("role");
                String address = rs.getString("address");
                u = new User(userID, userName, password, Email, firstName, lastName, gender, birthday, phoneNumber, profileImage, role, address);
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

    public void insert(User x) {
        xSql = "INSERT INTO [dbo].[User]([email],[firstname],[lastname],[password],[gender],[address],[phonenumber])"
                + "     VALUES(?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, x.getEmail());
            ps.setString(2, x.getFirstName());
            ps.setString(3, x.getLastName());
            ps.setString(4, x.getPassword());
            ps.setString(5, x.getGender());
            ps.setString(6, x.getAddress());
            ps.setString(7, x.getPhoneNumber());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
