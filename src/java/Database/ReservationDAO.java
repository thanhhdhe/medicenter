/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import model.Reservation;

/**
 *
 * @author hbich
 */
public class ReservationDAO extends MyDAO {

    //update status
    public void updateDoctor(String staffId, String reservationID) {
        xSql = "UPDATE Reservations\n"
                + "SET StaffID = ? \n"
                + "WHERE ReservationID = ?;";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, staffId);
            ps.setString(2, reservationID);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //update status
    public void updateStatus(String status, String reservationID) {
        xSql = "UPDATE Reservations\n"
                + "SET Status = ?\n"
                + "WHERE ReservationID = ?; ";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, status);
            ps.setString(2, reservationID);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //get total Reservation
    public int getTotalReservation() {
        xSql = "select COUNT(*) from Reservations;";
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

    //get total Reservation
    public int getTotalReservationByFillter(String staffId) {
        xSql = "SELECT count(*)\n"
                + "FROM Reservations\n"
                + "WHERE StaffID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, staffId);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {

        }
        return 0;
    }

    //get total Reservation
    public int getTotalReservationBySearch(String Name) {
        xSql = "SELECT Count(*)\n"
                + "FROM Reservations AS R\n"
                + "INNER JOIN Users AS U ON R.UserID = U.UserID\n"
                + "WHERE (U.FirstName LIKE ? OR U.LastName LIKE ?);";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, "%" + Name + "%");
            ps.setString(2, "%" + Name + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {

        }
        return 0;
    }

    public List<Reservation> getReservationAllBySearch(int page, String Name) {
        List<Reservation> list = new ArrayList<>();

        xSql = "SELECT R.*\n"
                + "FROM Reservations AS R\n"
                + "INNER JOIN Users AS U ON R.UserID = U.UserID\n"
                + "WHERE (U.FirstName LIKE ? OR U.LastName LIKE ?)"
                + "ORDER BY ReservationID\n"
                + "OFFSET ? Rows fetch next 10 rows ONLY;";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, "%" + Name + "%");
            ps.setString(2, "%" + Name + "%");
            ps.setInt(3, (page - 1) * 10);
            rs = ps.executeQuery();
            while (rs.next()) {
                int ReservationID = rs.getInt("ReservationID");
                int UserID = rs.getInt("UserID");
                int ServiceID = rs.getInt("ServiceID");
                Date ReservationDate = rs.getDate("ReservationDate");
                int ReservationSlot = rs.getInt("ReservationSlot");
                Timestamp CreatedDate = rs.getTimestamp("CreatedDate");
                float Cost = rs.getFloat("Cost");
                String Status = rs.getString("Status");
                int StaffID = rs.getInt("StaffID");
                int ChildID = rs.getInt("ChildID");
                Reservation reservation = new Reservation(ReservationID, UserID, ServiceID, StaffID, ChildID, ReservationDate, ReservationSlot, CreatedDate, Cost, Status);
                list.add(reservation);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Reservation> getReservationAllByPagingFill(int page, String staffId) {
        List<Reservation> list = new ArrayList<>();

        xSql = "SELECT *\n"
                + "FROM Reservations\n"
                + "WHERE StaffID = ?\n"
                + "ORDER BY ReservationID\n"
                + "OFFSET ? Rows fetch next 10 rows ONLY;";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, staffId);
            ps.setInt(2, (page - 1) * 10);
            rs = ps.executeQuery();
            while (rs.next()) {
                int ReservationID = rs.getInt("ReservationID");
                int UserID = rs.getInt("UserID");
                int ServiceID = rs.getInt("ServiceID");
                Date ReservationDate = rs.getDate("ReservationDate");
                int ReservationSlot = rs.getInt("ReservationSlot");
                Timestamp CreatedDate = rs.getTimestamp("CreatedDate");
                float Cost = rs.getFloat("Cost");
                String Status = rs.getString("Status");
                int StaffID = rs.getInt("StaffID");
                int ChildID = rs.getInt("ChildID");
                Reservation reservation = new Reservation(ReservationID, UserID, ServiceID, StaffID, ChildID, ReservationDate, ReservationSlot, CreatedDate, Cost, Status);
                list.add(reservation);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Reservation> getReservationAllByPaging(int page, String sort) {
        List<Reservation> list = new ArrayList<>();
        String sortstatus = "";

        if (sort == null || sort.equals("")) {
            sortstatus = "ReservationID";
        } else {
            sortstatus = "Status";
        }
        xSql = "select * from Reservations\n"
                + "ORDER BY " + sortstatus + "\n"
                + "OFFSET ? Rows fetch next 10 rows ONLY; ";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, (page - 1) * 10);
            rs = ps.executeQuery();
            while (rs.next()) {
                int ReservationID = rs.getInt("ReservationID");
                int UserID = rs.getInt("UserID");
                int ServiceID = rs.getInt("ServiceID");
                Date ReservationDate = rs.getDate("ReservationDate");
                int ReservationSlot = rs.getInt("ReservationSlot");
                Timestamp CreatedDate = rs.getTimestamp("CreatedDate");
                float Cost = rs.getFloat("Cost");
                String Status = rs.getString("Status");
                int StaffID = rs.getInt("StaffID");
                int ChildID = rs.getInt("ChildID");
                Reservation reservation = new Reservation(ReservationID, UserID, ServiceID, StaffID, ChildID, ReservationDate, ReservationSlot, CreatedDate, Cost, Status);
                list.add(reservation);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Reservation> getReservationByUserID(String userID) {
        List<Reservation> list = new ArrayList<>();
        xSql = "SELECT * from [dbo].[Reservations] where UserID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, userID);
            rs = ps.executeQuery();
            while (rs.next()) {
                int ReservationID = rs.getInt("ReservationID");
                int UserID = rs.getInt("UserID");
                int ServiceID = rs.getInt("ServiceID");
                Date ReservationDate = rs.getDate("ReservationDate");
                int ReservationSlot = rs.getInt("ReservationSlot");
                Timestamp CreatedDate = rs.getTimestamp("CreatedDate");
                float Cost = rs.getFloat("Cost");
                String Status = rs.getString("Status");
                int StaffID = rs.getInt("StaffID");
                int ChildID = rs.getInt("ChildID");
                String payment = rs.getString("Payment");
                Reservation reservation = new Reservation(ReservationID, UserID, ServiceID, StaffID, ChildID, ReservationDate, ReservationSlot, CreatedDate, Cost, Status, payment);
                list.add(reservation);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Integer> getListServiceIDByUserAndStaff(String userID, String staffID) {
        List<Integer> serviceIDList = new ArrayList<>();
        String sql = "SELECT DISTINCT ServiceID FROM Reservations WHERE UserID = ? AND StaffID = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, userID);
            ps.setString(2, staffID);
            rs = ps.executeQuery();
            while (rs.next()) {
                int serviceID = rs.getInt("ServiceID");
                serviceIDList.add(serviceID);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return serviceIDList;
    }

    public List<Integer> getListChildrenIDByUserAndStaff(String childName, String staffID, int page, int pageSize) {
        List<Integer> childrenIDList = new ArrayList<>();
        String sql = "SELECT DISTINCT c.ChildID FROM Reservations r  "
                + "INNER JOIN Children c ON r.ChildID = c.ChildID "
                + "WHERE c.ChildName LIKE ? AND r.StaffID = ? AND r.Status <> ? "
                + "ORDER BY c.ChildID "
                + "OFFSET ? ROWS "
                + "FETCH NEXT ? ROWS ONLY;";
        int offset = (page - 1) * pageSize;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + childName + "%");
            ps.setString(2, staffID);
            ps.setString(3, "pending");
            ps.setInt(4, offset);
            ps.setInt(5, pageSize);
            rs = ps.executeQuery();
            while (rs.next()) {
                int childID = rs.getInt("ChildID");
                childrenIDList.add(childID);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return childrenIDList;
    }

    public List<Integer> getListServiceIDByUser(String userID) {
        List<Integer> serviceIDList = new ArrayList<>();
        String sql = "SELECT DISTINCT ServiceID FROM Reservations WHERE UserID = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, userID);
            rs = ps.executeQuery();
            while (rs.next()) {
                int serviceID = rs.getInt("ServiceID");
                serviceIDList.add(serviceID);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return serviceIDList;
    }

    public int countListChildrenIDByUserAndStaff(String childName, String staffID) {
        int count = 0;
        String sql = "SELECT COUNT(*) AS RecordCount\n"
                + "FROM (SELECT DISTINCT c.ChildID FROM Reservations r  "
                + "INNER JOIN Children c ON r.ChildID = c.ChildID "
                + "WHERE c.ChildName LIKE ? AND r.StaffID = ? AND r.Status <> ? "
                + ") AS SubQuery;";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + childName + "%");
            ps.setString(2, staffID);
            ps.setString(3, "pending");
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

    public int countPatientToday(String staffID) {
        int count = 0;
        String sql = "SELECT COUNT(DISTINCT ChildID) AS DistinctChildCount "
                + "FROM Reservations "
                + "WHERE ReservationDate = CAST(GETDATE() AS DATE) AND StaffID = ?;";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, staffID);
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

    public int countApoinmentTodayOfStaff(String staffID) {
        int count = 0;
        xSql = "select COUNT(*) from Reservations WHERE ReservationDate = CAST(GETDATE() AS DATE) AND StaffID = ?;";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, staffID);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<Reservation> getApoinmentTodayOfStaff(String staffID) {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[Reservations] WHERE ReservationDate = CAST(GETDATE() AS DATE) AND StaffID = ? AND Status <> ? ";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, staffID);
            ps.setString(2, "pending");
            rs = ps.executeQuery();
            while (rs.next()) {
                // Lấy dữ liệu từ ResultSet và thêm vào danh sách
                int ReservationID = rs.getInt("ReservationID");
                int UserID = rs.getInt("UserID");
                int ServiceID = rs.getInt("ServiceID");
                Date ReservationDate = rs.getDate("ReservationDate");
                int ReservationSlot = rs.getInt("ReservationSlot");
                Timestamp CreatedDate = rs.getTimestamp("CreatedDate");
                float Cost = rs.getFloat("Cost");
                String Status = rs.getString("Status");
                int StaffID = rs.getInt("StaffID");
                int ChildID = rs.getInt("ChildID");
                Reservation reservation = new Reservation(ReservationID, UserID, ServiceID, StaffID, ChildID, ReservationDate, ReservationSlot, CreatedDate, Cost, Status);
                list.add(reservation);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Reservation> getReservationByStaffID(String staffID) {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[Reservations] WHERE StaffID = ? AND Status <> ? ";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, staffID);
            ps.setString(2, "pending");
            rs = ps.executeQuery();
            while (rs.next()) {
                // Lấy dữ liệu từ ResultSet và thêm vào danh sách
                int ReservationID = rs.getInt("ReservationID");
                int UserID = rs.getInt("UserID");
                int ServiceID = rs.getInt("ServiceID");
                Date ReservationDate = rs.getDate("ReservationDate");
                int ReservationSlot = rs.getInt("ReservationSlot");
                Timestamp CreatedDate = rs.getTimestamp("CreatedDate");
                float Cost = rs.getFloat("Cost");
                String Status = rs.getString("Status");
                int StaffID = rs.getInt("StaffID");
                int ChildID = rs.getInt("ChildID");
                Reservation reservation = new Reservation(ReservationID, UserID, ServiceID, StaffID, ChildID, ReservationDate, ReservationSlot, CreatedDate, Cost, Status);
                list.add(reservation);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Reservation> getSpecificReservation(String staffID, String date, String month, String year) {
        List<Reservation> list = new ArrayList<>();
        xSql = "SELECT * from [dbo].[Reservations] where StaffID = ? and DAY(ReservationDate) = ? and MONTH(ReservationDate) = ? and YEAR(ReservationDate) = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, staffID);
            ps.setString(2, date);
            ps.setString(3, month);
            ps.setString(4, year);
            rs = ps.executeQuery();
            while (rs.next()) {
                int ReservationID = rs.getInt("ReservationID");
                int UserID = rs.getInt("UserID");
                int ServiceID = rs.getInt("ServiceID");
                Date ReservationDate = rs.getDate("ReservationDate");
                int ReservationSlot = rs.getInt("ReservationSlot");
                Timestamp CreatedDate = rs.getTimestamp("CreatedDate");
                float Cost = rs.getFloat("Cost");
                String Status = rs.getString("Status");
                int StaffID = rs.getInt("StaffID");
                int ChildID = rs.getInt("ChildID");
                Reservation reservation = new Reservation(ReservationID, UserID, ServiceID, StaffID, ChildID, ReservationDate, ReservationSlot, CreatedDate, Cost, Status);
                list.add(reservation);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Reservation> getFilteredReservationsOfStaff(String staffId, String status, String reservationId, String customerName, String fromDate, String toDate, String sortBy, int page) {
        List<Reservation> list = new ArrayList<>();
        int pageSize = 10; // Kích thước của mỗi trang
        int offset = (page - 1) * pageSize; // Vị trí bắt đầu của dữ liệu trên trang hiện tại

        String sql = "SELECT * FROM Reservations "
                + "INNER JOIN Users ON Reservations.UserID = Users.UserID "
                + "WHERE StaffID = ? AND Reservations.Status <> ? ";
        if (status != null && !status.isEmpty()) {
            sql += "AND Reservations.Status = ? ";
        }
        if (reservationId != null && !reservationId.isEmpty()) {
            sql += "AND Reservations.ReservationID = ? ";
        }
        if (customerName != null && !customerName.isEmpty()) {
            sql += "AND (Users.FirstName COLLATE Vietnamese_CI_AI LIKE ?"
                    + "   OR Users.LastName COLLATE Vietnamese_CI_AI LIKE ?)";
        }
        if (fromDate != null && !fromDate.isEmpty()) {
            sql += "AND Reservations.ReservationDate >= ? ";
        }
        if (toDate != null && !toDate.isEmpty()) {
            sql += "AND Reservations.ReservationDate <= ? ";
        }

        sql += "ORDER BY ";
        if (sortBy != null && !sortBy.isEmpty()) {
            switch (sortBy) {
                case "customername":
                    sql += "Users.FirstName ";
                    break;
                case "status":
                    sql += "Reservations.Status ";
                    break;
                case "price-high":
                    sql += "Reservations.Cost DESC ";
                    break;
                case "price-low":
                    sql += "Reservations.Cost ";
                    break;
                case "date-latest":
                    sql += "Reservations.ReservationDate DESC ";
                    break;
                case "date-earliest":
                    sql += "Reservations.ReservationDate ";
                    break;
                default:
                    sql += "Reservations.ReservationID ";
                    break;
            }
        } else {
            sql += "Reservations.ReservationDate DESC ";
        }

        sql += "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try {
            ps = con.prepareStatement(sql);
            int paramIndex = 1;
            ps.setString(paramIndex++, staffId);
            ps.setString(paramIndex++, "pending");
            if (status != null && !status.isEmpty()) {
                ps.setString(paramIndex++, status);
            }
            if (reservationId != null && !reservationId.isEmpty()) {
                ps.setString(paramIndex++, reservationId);
            }
            if (customerName != null && !customerName.isEmpty()) {
                ps.setNString(paramIndex++, "%" + customerName + "%");
                ps.setNString(paramIndex++, "%" + customerName + "%");
            }
            if (fromDate != null && !fromDate.isEmpty()) {
                ps.setDate(paramIndex++, Date.valueOf(fromDate));
            }
            if (toDate != null && !toDate.isEmpty()) {
                ps.setDate(paramIndex++, Date.valueOf(toDate));
            }

            ps.setInt(paramIndex++, offset);
            ps.setInt(paramIndex++, pageSize);
            rs = ps.executeQuery();
            while (rs.next()) {
                // Lấy thông tin đặt chỗ từ ResultSet và thêm vào danh sách
                int ReservationID = rs.getInt("ReservationID");
                int UserID = rs.getInt("UserID");
                int ServiceID = rs.getInt("ServiceID");
                Date ReservationDate = rs.getDate("ReservationDate");
                int ReservationSlot = rs.getInt("ReservationSlot");
                Timestamp CreatedDate = rs.getTimestamp("CreatedDate");
                float Cost = rs.getFloat("Cost");
                String Status = rs.getString("Status");
                int StaffID = rs.getInt("StaffID");
                int ChildID = rs.getInt("ChildID");
                Reservation reservation = new Reservation(ReservationID, UserID, ServiceID, StaffID, ChildID, ReservationDate, ReservationSlot, CreatedDate, Cost, Status);
                list.add(reservation);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countFilteredReservationsOfStaff(String staffId, String status, String reservationId, String customerName, String fromDate, String toDate) {
        int count = 0;

        String sql = "SELECT COUNT(*) FROM Reservations "
                + "INNER JOIN Users ON Reservations.UserID = Users.UserID "
                + "WHERE StaffID = ? AND Reservations.Status <> ? ";
        if (status != null && !status.isEmpty()) {
            sql += "AND Reservations.Status = ? ";
        }
        if (reservationId != null && !reservationId.isEmpty()) {
            sql += "AND Reservations.ReservationID = ? ";
        }
        if (customerName != null && !customerName.isEmpty()) {
            sql += "AND (Users.FirstName LIKE ? OR Users.LastName LIKE ?) ";
        }
        if (fromDate != null && !fromDate.isEmpty()) {
            sql += "AND Reservations.ReservationDate >= ? ";
        }
        if (toDate != null && !toDate.isEmpty()) {
            sql += "AND Reservations.ReservationDate <= ? ";
        }

        try {
            ps = con.prepareStatement(sql);
            int paramIndex = 1;
            ps.setString(paramIndex++, staffId);
            ps.setString(paramIndex++, "pending");
            if (status != null && !status.isEmpty()) {
                ps.setString(paramIndex++, status);
            }
            if (reservationId != null && !reservationId.isEmpty()) {
                ps.setString(paramIndex++, reservationId);
            }
            if (customerName != null && !customerName.isEmpty()) {
                ps.setString(paramIndex++, "%" + customerName + "%");
                ps.setString(paramIndex++, "%" + customerName + "%");
            }
            if (fromDate != null && toDate != null && !fromDate.isEmpty() && !toDate.isEmpty()) {
                ps.setDate(paramIndex++, Date.valueOf(fromDate));
                ps.setDate(paramIndex++, Date.valueOf(toDate));
            }
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

    public List<Reservation> getPageReservationByStaffID(String staffID, int page, int pageSize) {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[Reservations] WHERE StaffID = ? AND Status <> ? "
                + "ORDER BY ReservationID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        int offset = (page - 1) * pageSize;

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, staffID);
            ps.setString(2, "pending");
            ps.setInt(3, offset);
            ps.setInt(4, pageSize);
            rs = ps.executeQuery();
            while (rs.next()) {
                // Lấy dữ liệu từ ResultSet và thêm vào danh sách
                int ReservationID = rs.getInt("ReservationID");
                int UserID = rs.getInt("UserID");
                int ServiceID = rs.getInt("ServiceID");
                Date ReservationDate = rs.getDate("ReservationDate");
                int ReservationSlot = rs.getInt("ReservationSlot");
                Timestamp CreatedDate = rs.getTimestamp("CreatedDate");
                float Cost = rs.getFloat("Cost");
                String Status = rs.getString("Status");
                int StaffID = rs.getInt("StaffID");
                int ChildID = rs.getInt("ChildID");
                Reservation reservation = new Reservation(ReservationID, UserID, ServiceID, StaffID, ChildID, ReservationDate, ReservationSlot, CreatedDate, Cost, Status);
                list.add(reservation);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countReservationsByStaffID(String staffID) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM [dbo].[Reservations] WHERE StaffID = ? AND Status <> ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, staffID);
            ps.setString(2, "pending");
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

    public int countReservationsByStaffAndService(String staffID, String serviceID) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM [dbo].[Reservations] WHERE StaffID = ? AND ServiceID = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, staffID);
            ps.setString(2, serviceID);
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

    public int countReservationsForService(String serviceID) {
        int reservationCount = 0;
        String xSql = "SELECT COUNT(*) AS ReservationCount FROM Reservations WHERE ServiceID = ?";

        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, serviceID);
            rs = ps.executeQuery();

            if (rs.next()) {
                reservationCount = rs.getInt("ReservationCount");
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reservationCount;
    }

    public List<Reservation> getSortedPaged(int offSetPage, int numberOfPage, String userID) {
        List<Reservation> reservationList = new ArrayList<>();
        xSql = "SELECT * FROM [dbo].[Reservations]  "
                + "WHERE UserID = ? "
                + "ORDER BY CreatedDate DESC "
                + "OFFSET ? ROWS "
                + "FETCH NEXT ? ROWS ONLY";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, userID);
            ps.setInt(2, offSetPage);
            ps.setInt(3, numberOfPage);

            rs = ps.executeQuery();
            while (rs.next()) {
                int ReservationID = rs.getInt("ReservationID");
                int UserID = rs.getInt("UserID");
                int ServiceID = rs.getInt("ServiceID");
                Date ReservationDate = rs.getDate("ReservationDate");
                int ReservationSlot = rs.getInt("ReservationSlot");
                Timestamp CreatedDate = rs.getTimestamp("CreatedDate");
                float Cost = rs.getFloat("Cost");
                String Status = rs.getString("Status");
                int StaffID = rs.getInt("StaffID");
                int ChildID = rs.getInt("ChildID");
                Reservation reservation = new Reservation(ReservationID, UserID, ServiceID, StaffID, ChildID, ReservationDate, ReservationSlot, CreatedDate, Cost, Status);
                reservationList.add(reservation);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reservationList;
    }

    public List<Reservation> getSortedSpecificPaged(int offSetPage, int numberOfPage, String userID, String condition, String value) {
        List<Reservation> reservationList = new ArrayList<>();
        if (condition.equals("staffName")) {
            xSql = "SELECT r.* FROM [dbo].[Reservations] r "
                    + "JOIN Staff s ON s.StaffID = r.StaffID "
                    + "WHERE UserID = ? AND s.StaffName LIKE '%" + value + "%' "
                    + "ORDER BY CreatedDate DESC "
                    + "OFFSET ? ROWS "
                    + "FETCH NEXT ? ROWS ONLY";
        } else if (condition.equals("serviceTitle")) {
            xSql = "SELECT r.* FROM [dbo].[Reservations] r "
                    + "JOIN [dbo].[Services] s ON s.ServiceID = r.ServiceID "
                    + "WHERE UserID = ? AND s.Title LIKE '%" + value + "%' "
                    + "ORDER BY CreatedDate DESC "
                    + "OFFSET ? ROWS "
                    + "FETCH NEXT ? ROWS ONLY";
        } else {
            xSql = "SELECT * FROM [dbo].[Reservations]  "
                    + "WHERE UserID = ? AND ReservationID = " + value + " "
                    + "ORDER BY CreatedDate DESC "
                    + "OFFSET ? ROWS "
                    + "FETCH NEXT ? ROWS ONLY";
        }
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, userID);
            ps.setInt(2, offSetPage);
            ps.setInt(3, numberOfPage);

            rs = ps.executeQuery();
            while (rs.next()) {
                int ReservationID = rs.getInt("ReservationID");
                int UserID = rs.getInt("UserID");
                int ServiceID = rs.getInt("ServiceID");
                Date ReservationDate = rs.getDate("ReservationDate");
                int ReservationSlot = rs.getInt("ReservationSlot");
                Timestamp CreatedDate = rs.getTimestamp("CreatedDate");
                float Cost = rs.getFloat("Cost");
                String Status = rs.getString("Status");
                int StaffID = rs.getInt("StaffID");
                int ChildID = rs.getInt("ChildID");
                Reservation reservation = new Reservation(ReservationID, UserID, ServiceID, StaffID, ChildID, ReservationDate, ReservationSlot, CreatedDate, Cost, Status);
                reservationList.add(reservation);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reservationList;
    }

    public int getTotalPagination(String userID, int number) {
        int count = 0;
        xSql = "SELECT COUNT(*) AS totalNumber FROM [dbo].[Reservations] WHERE UserID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, userID);
            rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("totalNumber");
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (count == 0) {
            return count;
        }
        if (count % number != 0) {
            count /= number;
            count++;
        } else {
            count /= number;
        }
        return count;
    }

    public int getTotalPaginationWithCondition(String userID, int number, String condition, String conditionValue) {
        int count = 0;
        switch (condition) {
            case "staffName":
                xSql = "SELECT COUNT(*) AS totalNumber FROM [dbo].[Reservations] r "
                        + "JOIN Staff s ON r.StaffID = s.StaffID "
                        + "WHERE r.UserID = ? AND s.StaffName LIKE '%" + conditionValue + "%'";
                break;
            case "serviceTitle":
                xSql = "SELECT COUNT(*) AS totalNumber FROM [dbo].[Reservations] r "
                        + "JOIN Services s ON r.ServiceID = s.ServiceID "
                        + "WHERE r.UserID = ? AND s.Title LIKE '%" + conditionValue + "%'";
                break;
            default:
                xSql = "SELECT COUNT(*) AS totalNumber FROM [dbo].[Reservations] r "
                        + "WHERE r.UserID = ? AND r.ReservationID = " + conditionValue;
                break;
        }

        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, userID);
            rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("totalNumber");
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (count == 0) {
            return count;
        }
        if (count % number != 0) {
            count /= number;
            count++;
        } else {
            count /= number;
        }
        return count;
    }

    public void updateDatabase() {
        xSql = "UPDATE [dbo].[Reservations] "
                + "SET status = 'cancel' "
                + "WHERE DATEDIFF(minute,CreatedDate,GETDATE()) > 5 AND status ='Pending'";
        try {
            ps = con.prepareStatement(xSql);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Reservation> getListReservationByServiceID(String serviceID, String selectedDate, String selectedMonth, String selectedYear) {
        List<Reservation> list = new ArrayList<>();
        xSql = "select * from [dbo].[Reservations] r\n"
                + "where r.serviceID = ? and DAY(r.ReservationDate) = ? AND MONTH(r.ReservationDate) = ? and YEAR(r.ReservationDate) = ? and r.status <> 'cancel'";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, serviceID);
            ps.setString(2, selectedDate);
            ps.setString(3, selectedMonth);
            ps.setString(4, selectedYear);
            rs = ps.executeQuery();
            while (rs.next()) {
                int ReservationID = rs.getInt("ReservationID");
                int UserID = rs.getInt("UserID");
                int ServiceID = rs.getInt("ServiceID");
                Date ReservationDate = rs.getDate("ReservationDate");
                int ReservationSlot = rs.getInt("ReservationSlot");
                Timestamp CreatedDate = rs.getTimestamp("CreatedDate");
                float Cost = rs.getFloat("Cost");
                String Status = rs.getString("Status");
                int StaffID = rs.getInt("StaffID");
                int ChildID = rs.getInt("ChildID");
                Reservation reservation = new Reservation(ReservationID, UserID, ServiceID, StaffID, ChildID, ReservationDate, ReservationSlot, CreatedDate, Cost, Status);
                list.add(reservation);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insert(Reservation r) {
        xSql = "insert into [dbo].[Reservations](ReservationDate, ReservationSlot, ServiceID, StaffID, ChildID, CreatedDate, Cost, UserID, Status)\n"
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = con.prepareStatement(xSql);
            ps.setDate(1, r.getReservationDate());
            ps.setInt(2, r.getReservationSlot());
            ps.setInt(3, r.getServiceID());
            ps.setInt(4, r.getStaffID());
            ps.setInt(5, r.getChildID());
            ps.setTimestamp(6, r.getCreatedDate());
            ps.setFloat(7, r.getCost());
            ps.setInt(8, r.getUserID());
            ps.setString(9, r.getStatus());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int findReservationID(int userID, String childID, String serviceID, Date reservationDate, int slot) {
        int id = -1;
        xSql = "select * from [dbo].[Reservations] where UserID = ? "
                + "and ServiceID = ? and ReservationDate = ? and ReservationSlot = ? and ChildID = ? and Status <> 'cancel'";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, Integer.toString(userID));
            ps.setString(2, serviceID);
            ps.setDate(3, reservationDate);
            ps.setInt(4, slot);
            ps.setString(5, childID);
            rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("ReservationID");
                return id;
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public boolean checkSlotForAvailable(String slot, String staffID, String selectedDate, String selectedMonth, String selectedYear) {
        boolean result = true;
        xSql = "select * from [dbo].[Reservations]\n"
                + "where ReservationSlot = ? and StaffID = ? and DAY(ReservationDate) = ? \n"
                + "and MONTH(ReservationDate) = ? and YEAR(ReservationDate) = ? \n"
                + "and Status <> 'cancel'";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, slot);
            ps.setString(2, staffID);
            ps.setString(3, selectedDate);
            ps.setString(4, selectedMonth);
            ps.setString(5, selectedYear);
            rs = ps.executeQuery();
            while (rs.next()) {
                result = false;
                break;
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean checkSlotThatSelfBooked(String slot, String staffID, String childID, String selectedDate, String selectedMonth, String selectedYear) {
        boolean result = false;
        xSql = "select * from [dbo].[Reservations]\n"
                + "where ReservationSlot = ? and StaffID = ? and DAY(ReservationDate) = ? \n"
                + "and MONTH(ReservationDate) = ? and YEAR(ReservationDate) = ? \n"
                + "and Status <> 'cancel'";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, slot);
            ps.setString(2, staffID);
            ps.setString(3, selectedDate);
            ps.setString(4, selectedMonth);
            ps.setString(5, selectedYear);
            rs = ps.executeQuery();
            while (rs.next()) {
                result = false;
                break;
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Integer> getListSelfBookedSlot(String ChildID, String selectedDate, String selectedMonth, String selectedYear) {
        List<Integer> list = new ArrayList<>();
        xSql = "select ReservationSlot from [dbo].[Reservations] \n"
                + "  where ChildID = ? and DAY(ReservationDate) = ? \n"
                + "  and MONTH(ReservationDate) = ? and YEAR(ReservationDate) = ?\n"
                + "  and Status <> 'cancel'";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, ChildID);
            ps.setString(2, selectedDate);
            ps.setString(3, selectedMonth);
            ps.setString(4, selectedYear);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("ReservationSlot"));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean validateReservationByChildrenID(String ChildID, int ReservationSlot, Date ReservationDate) {
        xSql = "select * from [dbo].[Reservations] where ChildID = ? and ReservationSlot = ? and ReservationDate = ? and Status <> 'cancel'";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, ChildID);
            ps.setInt(2, ReservationSlot);
            ps.setDate(3, ReservationDate);
            rs = ps.executeQuery();
            while (rs.next()) {
                return false;
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public Reservation getReservationByID(int ReservationID) {
        Reservation reservation = null;
        xSql = "SELECT * from [dbo].[Reservations] where ReservationID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, ReservationID);
            rs = ps.executeQuery();
            while (rs.next()) {
//                int ReservationID = rs.getInt("ReservationID");
                int UserID = rs.getInt("UserID");
                int ServiceID = rs.getInt("ServiceID");
                Date ReservationDate = rs.getDate("ReservationDate");
                int ReservationSlot = rs.getInt("ReservationSlot");
                Timestamp CreatedDate = rs.getTimestamp("CreatedDate");
                float Cost = rs.getFloat("Cost");
                String Status = rs.getString("Status");
                int StaffID = rs.getInt("StaffID");
                int ChildID = rs.getInt("ChildID");
                String payment = rs.getString("Payment");
                reservation = new Reservation(ReservationID, UserID, ServiceID, StaffID, ChildID, ReservationDate, ReservationSlot, CreatedDate, Cost, Status, payment);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reservation;
    }

    public List<Reservation> getReservationSearchAndFillter(String userID, String title, int carId) {
        List<Reservation> list = new ArrayList<>();
        xSql = "SELECT r.*\n"
                + "FROM [dbo].[Reservations] r\n"
                + "INNER JOIN Services s ON s.ServiceID = r.ServiceID\n"
                + "INNER JOIN Users u ON u.UserID = r.UserID\n"
                + "WHERE r.UserID = ? AND (s.Title LIKE ? OR s.CategoryID = ?);";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, userID);
            ps.setString(2, "%" + title + "%");
            ps.setInt(3, carId);
            rs = ps.executeQuery();
            while (rs.next()) {
                int ReservationID = rs.getInt("ReservationID");
                int UserID = rs.getInt("UserID");
                int ServiceID = rs.getInt("ServiceID");
                Date ReservationDate = rs.getDate("ReservationDate");
                int ReservationSlot = rs.getInt("ReservationSlot");
                Timestamp CreatedDate = rs.getTimestamp("CreatedDate");
                float Cost = rs.getFloat("Cost");
                String Status = rs.getString("Status");
                int StaffID = rs.getInt("StaffID");
                int ChildID = rs.getInt("ChildID");
                Reservation reservation = new Reservation(ReservationID, UserID, ServiceID, StaffID, ChildID, ReservationDate, ReservationSlot, CreatedDate, Cost, Status);
                list.add(reservation);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void update(Reservation reservation) {
        xSql = "update [dbo].[Reservations] set UserID = ?, ServiceID = ?, StaffID = ? "
                + " ,ChildID = ?, ReservationDate = ?, ReservationSlot = ?, CreatedDate = ?, Cost = ?, Status = ?, Payment =?  where ReservationID = ? ";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(11, reservation.getReservationID());
            ps.setInt(1, reservation.getUserID());
            ps.setInt(2, reservation.getServiceID());
            ps.setInt(3, reservation.getStaffID());
            ps.setInt(4, reservation.getChildID());
            ps.setDate(5, reservation.getReservationDate());
            ps.setInt(6, reservation.getReservationSlot());
            ps.setTimestamp(7, reservation.getCreatedDate());
            ps.setFloat(8, reservation.getCost());
            ps.setString(9, reservation.getStatus());
            ps.setString(10, reservation.getPayment());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Reservation> getReservationsByDay(Date startDate, Date endDate) {
        List<Reservation> list = new ArrayList<>();
        xSql = "select * from [dbo].[Reservations] "
                + " where DATEDIFF(DAY, ?, CreatedDate) >= 0 and DATEDIFF(DAY, ?, CreatedDate) <= 0";
        try {
            ps = con.prepareStatement(xSql);
            ps.setDate(1, startDate);
            ps.setDate(2, endDate);
            rs = ps.executeQuery();
            while (rs.next()) {
                int ReservationID = rs.getInt("ReservationID");
                int UserID = rs.getInt("UserID");
                int ServiceID = rs.getInt("ServiceID");
                Date ReservationDate = rs.getDate("ReservationDate");
                int ReservationSlot = rs.getInt("ReservationSlot");
                Timestamp CreatedDate = rs.getTimestamp("CreatedDate");
                float Cost = rs.getFloat("Cost");
                String Status = rs.getString("Status");
                int StaffID = rs.getInt("StaffID");
                int ChildID = rs.getInt("ChildID");
                Reservation reservation = new Reservation(ReservationID, UserID, ServiceID, StaffID, ChildID, ReservationDate, ReservationSlot, CreatedDate, Cost, Status);
                list.add(reservation);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public float getRevenueByServiceCategory(int categoryID, Date startDate, Date endDate) {
        xSql = "select sum(Cost) as RevenueByCategoryID from Reservations r\n"
                + " join Services s on r.ServiceID = s.ServiceID\n"
                + " where s.CategoryID = ? and r.Status <> 'cancel' and "
                + " DATEDIFF(DAY, ?, CreatedDate) >= 0 AND DATEDIFF(DAY, ?, CreatedDate) <= 0";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, categoryID);
            ps.setDate(2, startDate);
            ps.setDate(3, endDate);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getFloat("RevenueByCategoryID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countNewlyReservedMember(Date startDate, Date endDate) {
        xSql = "  select count(*) as TotalCount  from (\n"
                + "  select distinct UserID from Reservations\n"
                + "  where DATEDIFF(DAY, ?, CreatedDate) >= 0 AND DATEDIFF(DAY, ?, CreatedDate) <= 0\n"
                + "  ) as tempTable";
        try {
            ps = con.prepareStatement(xSql);
            ps.setDate(1, startDate);
            ps.setDate(2, endDate);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("TotalCount");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getReservationTotalEachDay(Date reservationDate) {
        xSql = "select count(*) as TotalCount from [dbo].[Reservations] where ReservationDate = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setDate(1, reservationDate);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("TotalCount");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getReservationTotalEachMonth(Date reservationDate) {
        xSql = "select count(*) as TotalCount from [dbo].[Reservations] where MONTH(ReservationDate) = MONTH(?) and YEAR(ReservationDate) = YEAR(?)";
        try {
            ps = con.prepareStatement(xSql);
            ps.setDate(1, reservationDate);
            ps.setDate(2, reservationDate);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("TotalCount");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getReservationDoneEachDay(Date reservationDate) {
        xSql = "select count(*) as TotalCount from [dbo].[Reservations] where ReservationDate = ? and Status = 'done'";
        try {
            ps = con.prepareStatement(xSql);
            ps.setDate(1, reservationDate);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("TotalCount");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getReservationDoneEachMonth(Date reservationDate) {
        xSql = "select count(*) as TotalCount from [dbo].[Reservations] where MONTH(ReservationDate) = MONTH(?) and YEAR(ReservationDate) = YEAR(?) and Status = 'done'";
        try {
            ps = con.prepareStatement(xSql);
            ps.setDate(1, reservationDate);
            ps.setDate(2, reservationDate);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("TotalCount");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void updatePayment(String payment, String reservationID) {
        xSql = "UPDATE Reservations\n"
                + "SET Payment = ?\n"
                + "WHERE ReservationID = ?; ";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, payment);
            ps.setString(2, reservationID);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int countUserTodayReservations(String userID, String childID) {
        xSql = "select count(*) as TotalRecord from Reservations "
                + "where UserID = ? and ChildID = ? "
                + "and datediff(DAY, GETDATE(), CreatedDate) = 0 and status <> 'cancel' ";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, userID);
            ps.setString(2, childID);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("TotalRecord");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String args[]) {
        ReservationDAO rd = new ReservationDAO();
//        List<Reservation> listreservation = rd.getReservationAllBySearch(1, "v");
//        System.out.println(rd.getTotalReservationBySearch("v"));
//        for(Reservation reservation : listreservation){
//            System.out.println(reservation.getUserID());
//        }
    }
}
