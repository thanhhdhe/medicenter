/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.util.ArrayList;
import java.util.List;
import model.User;

/**
 *
 * @author pc
 */
public class UserDAO extends MyDAO {

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String xSql = "SELECT * FROM [dbo].[Users]";

        try {
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User(
                        rs.getInt("UserID"),
                        rs.getString("Address"),
                        rs.getString("Email"),
                        rs.getString("Password"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Gender"),
                        rs.getString("PhoneNumber"),
                        rs.getString("ProfileImage"),
                        rs.getBoolean("Status"),
                        rs.getDate("CreatedDate")
                );
                userList.add(user);
            }

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }

    public List<User> search(String searchValue) {
        List<User> userList = new ArrayList<>();
        String xSql = "SELECT * FROM [dbo].[Users] where FirstName LIKE ? OR LastName LIKE ?  OR Email LIKE ? OR PhoneNumber LIKE ?";

        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, "%" + searchValue + "%");
            ps.setString(2, "%" + searchValue + "%");
            ps.setString(3, "%" + searchValue + "%");
            ps.setString(4, "%" + searchValue + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User(
                        rs.getInt("UserID"),
                        rs.getString("Address"),
                        rs.getString("Email"),
                        rs.getString("Password"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Gender"),
                        rs.getString("PhoneNumber"),
                        rs.getString("ProfileImage"),
                        rs.getBoolean("Status"),
                        rs.getDate("CreatedDate")
                );
                userList.add(user);
            }

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }

    public void updateStatus(boolean status, int UserID) {
        String xSql = "UPDATE [dbo].[Users]\n"
                + "   SET [Status] = ?\n"
                + " WHERE UserID = ?";
        try {
            ps = con.prepareStatement(xSql);
            System.out.println(xSql);
            ps.setInt(2, UserID);
            ps.setBoolean(1, status);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<User> getListByPage(List<User> list,
            int start, int end) {
        ArrayList<User> user = new ArrayList<>();
        for (int i = start; i < end; i++) {
            user.add(list.get(i));
        }
        return user;
    }

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
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getBoolean(10), rs.getDate(11));
                return user;
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByID(int userID) {

        xSql = "select * from [dbo].[Users] where UserID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, userID);
            rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getBoolean(10), rs.getDate(11));
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

    public void insert(User x) {
        xSql = "INSERT INTO [dbo].[Users]([Email],[FirstName],[LastName],[Password],[Gender],[Address],[PhoneNumber],[ProfileImage],[Status],[CreatedDate]) VALUES(N'"
                + x.getEmail() + "',N'" + x.getFirstName() + "',N'" + x.getLastName() + "','" + x.getPassword() + "',N'"
                + x.getGender() + "',N'" + x.getAddress() + "','" + x.getPhoneNumber() + "','" + x.getProfileImage() + "','" + (x.isStatus() ? Integer.toString(1) : Integer.toString(0)) + "','" + x.getCreatedDate().toString() + "')";
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

    public List<User> getFilterByStatus(String status) {
        List<User> filteredUsers = new ArrayList<>();
        String xSql = "SELECT * FROM [dbo].[Users] WHERE [Status] = ?";

        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, status);
            rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User(
                        rs.getInt("UserID"),
                        rs.getString("Address"),
                        rs.getString("Email"),
                        rs.getString("Password"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Gender"),
                        rs.getString("PhoneNumber"),
                        rs.getString("ProfileImage"),
                        rs.getBoolean("Status"),
                        rs.getDate("CreatedDate")
                );
                filteredUsers.add(user);
            }

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return filteredUsers;
    }

    public List<User> getUsersByCreatedDate(int day) {
        List<User> list = new ArrayList<>();
        xSql = "select * from [dbo].[Users] where DATEDIFF(DAY,GETDATE(),CreatedDate) <= ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, -day);
            rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User(
                        rs.getInt("UserID"),
                        rs.getString("Address"),
                        rs.getString("Email"),
                        rs.getString("Password"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Gender"),
                        rs.getString("PhoneNumber"),
                        rs.getString("ProfileImage"),
                        rs.getBoolean("Status"),
                        rs.getDate("CreatedDate")
                );
                list.add(user);
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        UserDAO u = new UserDAO();
        List<User> us = u.getFilterByStatus("1");

        for (User u1 : us) {
            System.out.println(u1.isStatus());
        }

    }
}
