/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Service;

/**
 *
 * @author Admin
 */
public class ServiceDAO extends MyDAO {

    public List<Service> getAllServices() {
        List<Service> serviceList = new ArrayList<>();
        xSql = "SELECT *  FROM [dbo].[Services]";
        try {
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int ServiceID = rs.getInt("ServiceID");
                String title = rs.getString("Title");
                String thumbnail = rs.getString("Thumbnail");
                int categoryID = rs.getInt("CategoryID");
                double originalPrice = rs.getDouble("OriginalPrice");
                double salePrice = rs.getDouble("SalePrice");
                String serviceDetail = rs.getString("ServiceDetails");
                Date updateDate = rs.getDate("UpdateDate");
                Service service = new Service(ServiceID, title, thumbnail, categoryID, originalPrice, salePrice, serviceDetail, updateDate);
                serviceList.add(service);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceList;
    }

    public List<Service> getSortedPaged(int offSetPage, int numberOfPage) {
        List<Service> serviceList = new ArrayList<>();
        xSql = "SELECT *  FROM [dbo].[Services]  "
                + "ORDER BY UpdateDate DESC "
                + "OFFSET ? ROWS \n "
                + "FETCH NEXT ? ROWS ONLY;";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, offSetPage);
            ps.setInt(2, numberOfPage);

            rs = ps.executeQuery();
            while (rs.next()) {
                int ServiceID = rs.getInt("ServiceID");
                String title = rs.getString("Title");
                String thumbnail = rs.getString("Thumbnail");
                int categoryID = rs.getInt("CategoryID");
                double originalPrice = rs.getDouble("OriginalPrice");
                double salePrice = rs.getDouble("SalePrice");
                String serviceDetail = rs.getString("ServiceDetails");
                Date updateDate = rs.getDate("UpdateDate");
                Service service = new Service(ServiceID, title, thumbnail, categoryID, originalPrice, salePrice, serviceDetail, updateDate);
                serviceList.add(service);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceList;
    }

    public List<Service> getSortedPagedServicesBySearch(int offSetPage, int numberOfPage, String keyword) {
        List<Service> serviceList = new ArrayList<>();
        xSql = "SELECT *  FROM [dbo].[Services]  "
                + "where Title like ? "
                + "ORDER BY UpdateDate DESC "
                + "OFFSET ? ROWS \n "
                + "FETCH NEXT ? ROWS ONLY;";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, "%" + keyword + "%");
            ps.setInt(2, offSetPage);
            ps.setInt(3, numberOfPage);

            rs = ps.executeQuery();
            while (rs.next()) {
                int ServiceID = rs.getInt("ServiceID");
                String title = rs.getString("Title");
                String thumbnail = rs.getString("Thumbnail");
                int categoryID = rs.getInt("CategoryID");
                double originalPrice = rs.getDouble("OriginalPrice");
                double salePrice = rs.getDouble("SalePrice");
                String serviceDetail = rs.getString("ServiceDetails");
                Date updateDate = rs.getDate("UpdateDate");
                Service service = new Service(ServiceID, title, thumbnail, categoryID, originalPrice, salePrice, serviceDetail, updateDate);
                serviceList.add(service);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceList;
    }

    public List<Service> getSortedPagedServicesByUserChoice(int offSetPage, int numberOfPage, String keyword, String categoryIDInput, String staffIDInput) {
        List<Service> serviceList = new ArrayList<>();
        xSql = "SELECT s.ServiceID, s.Title, s.Thumbnail, s.CategoryID, s.OriginalPrice, s.SalePrice, s.ServiceDetails, s.UpdateDate "
                + "FROM ( "
                + "    SELECT * FROM [dbo].[Services] "
                + "    WHERE Title LIKE ? AND CategoryID = ? "
                + ") AS s "
                + "JOIN ServiceStaff ss ON s.ServiceID = ss.ServiceID "
                + "WHERE ss.StaffID = ?"
                + "    ORDER BY s.UpdateDate DESC "
                + "    OFFSET ? ROWS "
                + "    FETCH NEXT ? ROWS ONLY ";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, categoryIDInput);
            ps.setString(3, staffIDInput);
            ps.setInt(4, offSetPage);
            ps.setInt(5, numberOfPage);
            
            rs = ps.executeQuery();
            while (rs.next()) {
                int serviceID = rs.getInt("ServiceID");
                String title = rs.getString("Title");
                String thumbnail = rs.getString("Thumbnail");
                int categoryID = rs.getInt("CategoryID");
                double originalPrice = rs.getDouble("OriginalPrice");
                double salePrice = rs.getDouble("SalePrice");
                String serviceDetail = rs.getString("ServiceDetails");
                Date updateDate = rs.getDate("UpdateDate");
                Service service = new Service(serviceID, title, thumbnail, categoryID, originalPrice, salePrice, serviceDetail, updateDate);
                serviceList.add(service);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceList;
    }

    public List<Service> getSortedPagedServicesByOption(int offSetPage, int numberOfPage, String keyword, String categoryIDInput, String staffIDInput) {
        List<Service> serviceList = new ArrayList<>();
        if (categoryIDInput.isEmpty() && staffIDInput.isEmpty()) {
            serviceList = getSortedPagedServicesBySearch(offSetPage, numberOfPage, keyword);
        } else if (staffIDInput.isEmpty()) {
            serviceList = getSortedPagedServicesByType(offSetPage, numberOfPage, keyword, categoryIDInput);
        } else if (categoryIDInput.isEmpty()) {
            serviceList = getSortedPagedServicesByDoctor(offSetPage, numberOfPage, keyword, staffIDInput);
        } else {
            serviceList = getSortedPagedServicesByUserChoice(offSetPage, numberOfPage, keyword, categoryIDInput, staffIDInput);
        }
        return serviceList;
    }

    public List<Service> getSortedPagedServicesByType(int offSetPage, int numberOfPage, String keyword, String categoryIDInput) {
        List<Service> serviceList = new ArrayList<>();
        xSql = "    SELECT * FROM [dbo].[Services] "
                + "    WHERE Title LIKE ? AND CategoryID = ? "
                + "    ORDER BY UpdateDate DESC "
                + "    OFFSET ? ROWS "
                + "    FETCH NEXT ? ROWS ONLY ";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, categoryIDInput);
            ps.setInt(3, offSetPage);
            ps.setInt(4, numberOfPage);
            rs = ps.executeQuery();
            while (rs.next()) {
                int serviceID = rs.getInt("ServiceID");
                String title = rs.getString("Title");
                String thumbnail = rs.getString("Thumbnail");
                int categoryID = rs.getInt("CategoryID");
                double originalPrice = rs.getDouble("OriginalPrice");
                double salePrice = rs.getDouble("SalePrice");
                String serviceDetail = rs.getString("ServiceDetails");
                Date updateDate = rs.getDate("UpdateDate");
                Service service = new Service(serviceID, title, thumbnail, categoryID, originalPrice, salePrice, serviceDetail, updateDate);
                serviceList.add(service);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceList;
    }

    public List<Service> getSortedPagedServicesByDoctor(int offSetPage, int numberOfPage, String keyword, String staffIDInput) {
        List<Service> serviceList = new ArrayList<>();
        xSql = "SELECT s.ServiceID, s.Title, s.Thumbnail, s.CategoryID, s.OriginalPrice, s.SalePrice, s.ServiceDetails, s.UpdateDate "
                + "FROM ( "
                + "    SELECT * FROM [dbo].[Services] "
                + "    WHERE Title LIKE ?"
                + ") AS s "
                + "JOIN ServiceStaff ss ON s.ServiceID = ss.ServiceID "
                + "WHERE ss.StaffID = ?"
                 + "    ORDER BY s.UpdateDate DESC "
                + "    OFFSET ? ROWS "
                + "    FETCH NEXT ? ROWS ONLY ";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, "%" + keyword + "%");
            ps.setInt(3, offSetPage);
            ps.setInt(4, numberOfPage);
            ps.setString(2, staffIDInput);
            rs = ps.executeQuery();
            while (rs.next()) {
                int serviceID = rs.getInt("ServiceID");
                String title = rs.getString("Title");
                String thumbnail = rs.getString("Thumbnail");
                int categoryID = rs.getInt("CategoryID");
                double originalPrice = rs.getDouble("OriginalPrice");
                double salePrice = rs.getDouble("SalePrice");
                String serviceDetail = rs.getString("ServiceDetails");
                Date updateDate = rs.getDate("UpdateDate");
                Service service = new Service(serviceID, title, thumbnail, categoryID, originalPrice, salePrice, serviceDetail, updateDate);
                serviceList.add(service);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceList;
    }

    public Service getServiceByID(String ID) {
        Service service = null;
        xSql = "select * from [dbo].[Service] where ServiceID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, ID);
            rs = ps.executeQuery();
            if (rs.next()) {
                int ServiceID = rs.getInt("ServiceID");
                String title = rs.getString("Title");
                String thumbnail = rs.getString("Thumbnail");
                int categoryID = rs.getInt("CategoryID");
                double originalPrice = rs.getDouble("OriginalPrice");
                double salePrice = rs.getDouble("SalePrice");
                String serviceDetail = rs.getString("ServiceDetails");
                Date updateDate = rs.getDate("UpdateDate");
                service = new Service(ServiceID, title, thumbnail, categoryID, originalPrice, salePrice, serviceDetail, updateDate);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return service;
    }

    public void insert(Service service) {
        xSql = "INSERT INTO [dbo].[Services] (Title, Thumbnail, CategoryID, OriginalPrice, SalePrice, ServiceDetails, UpdateDate) \n"
                + "     VALUES (? ,? ,? ,? ,? ,? ,?)";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, service.getTitle());
            ps.setString(2, service.getThumbnail());
            ps.setInt(3, service.getCategoryID());
            ps.setDouble(4, service.getOriginalPrice());
            ps.setDouble(5, service.getSalePrice());
            ps.setString(6, service.getServiceDetail());
            ps.setDate(7, service.getUpdateDate());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteByID(String ID) {
        xSql = "delete from Services where [ServiceID]=?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, ID);
            ps.executeUpdate();
            //con.commit();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Service service) {
        xSql = "UPDATE [dbo].[Services]\n"
                + "   SET [Title] = ? ,[Thumbnail] = ? ,[CategoryID] = ? ,[OriginalPrice] = ? ,[SalePrice] = ? ,[ServiceDetails] =?,[UpdateDate]=?"
                + " WHERE [ServiceID] = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, service.getTitle());
            ps.setString(2, service.getThumbnail());
            ps.setInt(3, service.getCategoryID());
            ps.setDouble(4, service.getOriginalPrice());
            ps.setDouble(5, service.getSalePrice());
            ps.setString(6, service.getServiceDetail());
            ps.setDate(7, service.getUpdateDate());
            ps.setInt(8, service.getServiceID());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            // Handle exception appropriately
            System.out.println(e);
        }
    }

    public int getServiceCount() {
        Service service = null;
        xSql = "SELECT COUNT(*) AS TotalRecords FROM Services";
        int total = 0;
        try {
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt("TotalRecords");
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }
    
    public int getCountOfServicesUserChoose(String keyword, String categoryIDInput, String staffIDInput) {
    int count = 0;
    String sql = "SELECT COUNT(*) AS count "
            + "FROM [dbo].[Services] AS s "
            + "JOIN ServiceStaff ss ON s.ServiceID = ss.ServiceID "
            + "WHERE s.Title LIKE ? ";
    
    if (!categoryIDInput.isEmpty()) {
        sql += "AND s.CategoryID = ? ";
    }
    
    if (!staffIDInput.isEmpty()) {
        sql += "AND ss.StaffID = ? ";
    }
    
    try {
        ps = con.prepareStatement(sql);
        ps.setString(1, "%" + keyword + "%");
        
        int parameterIndex = 2;
        if (!categoryIDInput.isEmpty()) {
            ps.setString(parameterIndex++, categoryIDInput);
        }
        
        if (!staffIDInput.isEmpty()) {
            ps.setString(parameterIndex++, staffIDInput);
        }
        
        rs = ps.executeQuery();
        if (rs.next()) {
            count = rs.getInt("count");
        }
        
        rs.close();
        ps.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    
    return count;
}

    public static void main(String[] args) {
        ServiceDAO serviceDAO = new ServiceDAO();
        CategoryServiceDAO categoryServiceDAO = new CategoryServiceDAO();
        List<Service> list = serviceDAO.getSortedPagedServicesByOption(0, 10, "", "", "1");
//        List<Service> list = serviceDAO.getSortedPagedServicesBySearch(5, 5, "");
        for (Service service : list) {
            System.out.println(service.getTitle());
        }
//        System.out.println(serviceDAO.getCountOfServicesUserChoose("", "", ""));
//        System.out.println((serviceDAO.getServiceCount()/5+1)*2/2);
//        for (int i = 0; i < serviceDAO.getServiceCount(); i++) {
//            
//        }
    }
}
