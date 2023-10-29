/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Contact;
import model.User;

/**
 *
 * @author Admin
 */
public class ContactDAO extends MyDAO {

    public List<Contact> getContacts() {
        List<Contact> contactList = new ArrayList<>();
        String sql = "SELECT * FROM HistoricalContact";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                UserDAO userdao = new UserDAO();
                Contact contact = new Contact(
                        rs.getInt("ContactID"),
                        userdao.getUserByID(rs.getInt("UserID")),
                        rs.getString("Address"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Gender"),
                        rs.getString("PhoneNumber"),
                        rs.getTimestamp("UpdatedDate")
                );
                contactList.add(contact);
            }

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return contactList;
    }

    public List<Contact> getContacts(int userID) {
        List<Contact> contactList = new ArrayList<>();
        String sql = "SELECT * FROM HistoricalContact WHERE UserID = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, userID);
            rs = ps.executeQuery();

            while (rs.next()) {
                UserDAO userdao = new UserDAO();
                Contact contact = new Contact(
                        rs.getInt("ContactID"),
                        userdao.getUserByID(rs.getInt("UserID")),
                        rs.getString("Address"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Gender"),
                        rs.getString("PhoneNumber"),
                        rs.getTimestamp("UpdatedDate")
                );
                contactList.add(contact);
            }

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return contactList;
    }

    public boolean addContact(Contact contact) {
        String sql = "INSERT INTO HistoricalContact (UserID, Address, FirstName, LastName, Gender, PhoneNumber) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, contact.getUser().getUserID());
            ps.setString(2, contact.getAddress());
            ps.setString(3, contact.getFirstName());
            ps.setString(4, contact.getLastName());
            ps.setString(5, contact.getGender());
            ps.setString(6, contact.getPhoneNumber());

            int rowsAffected = ps.executeUpdate();
            ps.close();

            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
