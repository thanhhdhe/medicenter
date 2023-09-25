/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.awt.BorderLayout;
import java.sql.Date;
import java.sql.SQLException;
import model.User;

/**
 *
 * @author pc
 */
public class UserDAO extends MyDAO {

    //reset password by User ID(ten dang nhap)
    public void resetPassword(String newPassword, int UserID) {
        xSql = "update Users set Password= ? where UserID= ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, newPassword);
            ps.setInt(2, UserID);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetPasswordByID(String newPassword, String phoneNumber, int userID) {
        xSql = "update Users set Password= ? where UserID= ? and PhoneNumber = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, newPassword);
            ps.setInt(2, userID);
            ps.setString(3, phoneNumber);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getUser(String email) {

        xSql = "select * from [dbo].[Users] where email = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                         rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
                         rs.getString(10));
                return user;
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
        xSql = "select * from [dbo].[Users] where Email = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, email);

            rs = ps.executeQuery();
            while (rs.next()) {
                int userID = rs.getInt("userID");
                String password = rs.getString("password");
                String Email = rs.getString("email");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String gender = rs.getString("gender");
                String phoneNumber = rs.getString("phoneNumber");
                String profileImage = rs.getString("profileImage");
                String role = rs.getString("role");
                String address = rs.getString("address");
                u = new User(userID, password, Email, firstName, lastName, gender, phoneNumber, profileImage, role, address);
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

    public void insert(User x) {
        xSql = "INSERT INTO [dbo].[Users]([Email],[FirstName],[LastName],[Password],[Gender],[Address],[PhoneNumber],[Role],[ProfileImage]) VALUES(N'"
                + x.getEmail() + "',N'" + x.getFirstName() + "',N'" + x.getLastName() + "','" + x.getPassword() + "',N'"
                + x.getGender() + "',N'" + x.getAddress() + "','" + x.getPhoneNumber() + "','" + x.getRole() + "','" + x.getProfileImage() +"')";
        try {
            ps = con.prepareStatement(xSql);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UpdateProfile(String firstName, String lastName, String phone, String gender, String img, String address, int UserID) {
        String xSql = "UPDATE [dbo].[Users]\n"
                + "   SET [Address] = ?\n"
                + "      ,[FirstName] = ?\n"
                + "      ,[LastName] = ?\n"
                + "      ,[Gender] = ?\n"
                + "      ,[PhoneNumber] = ?\n"
                + "      ,[ProfileImage] = ?\n"
                + " WHERE UserID = ?";
        try {
            ps = con.prepareStatement(xSql);
            System.out.println(xSql);
            ps.setString(1, address);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, gender);
            ps.setString(5, phone);
            ps.setString(6, img);
            ps.setInt(7, UserID);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        UserDAO u = new UserDAO();
//        User user = new User();
//         user.setEmail("HieuBui");
//            user.setFirstName("Hieu");
//            user.setLastName("BUI");
//            user.setPhoneNumber("0123123");
//            user.setPassword("12312124");
//            user.setAddress("HAHA");
//            user.setGender("MALE");
//            user.setRole("user");   
//            System.out.println(user.getUserID());
//        u.insert(user);
    }
}
