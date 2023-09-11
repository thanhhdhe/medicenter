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
public class ServiceDAO extends MyDAO{
    public List<Service> getServices() {
        List<Service> serviceList = new ArrayList<>();
        xSql = "SELECT *  FROM [dbo].[Services]";
        try {
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int ServiceID = rs.getInt("ServiceID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String thumbnail = rs.getString("Thumbnail");
                int categoryID = rs.getInt("CategoryID");
                double originalPrice = rs.getDouble("OriginalPrice");
                double  salePrice = rs.getDouble("SalePrice");
                String serviceDetail = rs.getString("ServiceDetails");
                Date updateDate = rs.getDate("UpdateDate");
                Service service = new Service(ServiceID, title, description, thumbnail, categoryID, originalPrice, salePrice, serviceDetail, updateDate);
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
                + "ORDER BY updated_date DESC "
                + "OFFSET ? ROWS \n "
                + "FETCH NEXT ? ROWS ONLY;";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, keyword);
            ps.setInt(2, offSetPage);
            ps.setInt(3, numberOfPage);
            
            rs = ps.executeQuery();
            while (rs.next()) {
                int ServiceID = rs.getInt("ServiceID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String thumbnail = rs.getString("Thumbnail");
                int categoryID = rs.getInt("CategoryID");
                double originalPrice = rs.getDouble("OriginalPrice");
                double  salePrice = rs.getDouble("SalePrice");
                String serviceDetail = rs.getString("ServiceDetails");
                Date updateDate = rs.getDate("UpdateDate");
                Service service = new Service(ServiceID, title, description, thumbnail, categoryID, originalPrice, salePrice, serviceDetail, updateDate);
                serviceList.add(service);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceList;
    }
    
    public List<Service> getSortedPagedServicesByType(int offSetPage, int numberOfPage, String type) {
        List<Service> serviceList = new ArrayList<>();
        xSql = "SELECT *  FROM [dbo].[Services]  "
                + "where CategoryID = ? "
                + "ORDER BY updated_date DESC "
                + "OFFSET ? ROWS \n"
                + "FETCH NEXT ? ROWS ONLY;";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, type);
            ps.setInt(2, offSetPage);
            ps.setInt(3, numberOfPage);
            
            rs = ps.executeQuery();
            while (rs.next()) {
                int ServiceID = rs.getInt("ServiceID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String thumbnail = rs.getString("Thumbnail");
                int categoryID = rs.getInt("CategoryID");
                double originalPrice = rs.getDouble("OriginalPrice");
                double  salePrice = rs.getDouble("SalePrice");
                String serviceDetail = rs.getString("ServiceDetails");
                Date updateDate = rs.getDate("UpdateDate");
                Service service = new Service(ServiceID, title, description, thumbnail, categoryID, originalPrice, salePrice, serviceDetail, updateDate);
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
                String description = rs.getString("Description");
                String thumbnail = rs.getString("Thumbnail");
                int categoryID = rs.getInt("CategoryID");
                double originalPrice = rs.getDouble("OriginalPrice");
                double  salePrice = rs.getDouble("SalePrice");
                String serviceDetail = rs.getString("ServiceDetails");
                Date updateDate = rs.getDate("UpdateDate");
                service = new Service(ServiceID, title, description, thumbnail, categoryID, originalPrice, salePrice, serviceDetail, updateDate);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return service;
    }

    public void insert(Service service) {
        xSql = "INSERT INTO [dbo].[Services] ([Title] ,[Description] ,[Thumbnail] ,[CategoryID] ,[OriginalPrice] ,[SalePrice] ,[ServiceDetails],[UpdateDate]) \n" +
"     VALUES (? ,? ,? ,? ,? ,? ,? ,?)";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1 , service.getTitle());
            ps.setString(2 , service.getDescription());
            ps.setString(3 , service.getThumbnail());
            ps.setInt(4 , service.getCategoryID());
            ps.setDouble(5 , service.getOriginalPrice());
            ps.setDouble(6 , service.getSalePrice());
            ps.setString(7 , service.getServiceDetail());
            ps.setDate(8, service.getUpdateDate());
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
        xSql = "UPDATE [dbo].[Services]\n" +
"   SET [Title] = ? ,[Description] = ? ,[Thumbnail] = ? ,[CategoryID] = ? ,[OriginalPrice] = ? ,[SalePrice] = ? ,[ServiceDetails] =?,[UpdateDate]=?"
                + " WHERE [ServiceID] = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1 , service.getTitle());
            ps.setString(2 , service.getDescription());
            ps.setString(3 , service.getThumbnail());
            ps.setInt(4 , service.getCategoryID());
            ps.setDouble(5 , service.getOriginalPrice());
            ps.setDouble(6 , service.getSalePrice());
            ps.setString(7 , service.getServiceDetail());
            ps.setDate(8, service.getUpdateDate());
            ps.setInt(9, service.getServiceID());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            // Handle exception appropriately
            System.out.println(e);
        }
    }
    
    public static void main(String[] args) {
        ServiceDAO serviceDAO = new ServiceDAO();
//        serviceDAO.insert(new Service( "fuk", "abc", "xx", 1, 1, 1, "xxxx"));
//        serviceDAO.deleteByID("2");
//            serviceDAO.update(new Service(3, "fuk", "dddd", "xx", 1, 1, 1, "xxxx"));
    }
}
