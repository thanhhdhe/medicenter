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
                String brief = rs.getString("BriefInfo");
                String thumbnail = rs.getString("Thumbnail");
                int categoryID = rs.getInt("CategoryID");
                double originalPrice = rs.getDouble("OriginalPrice");
                double salePrice = rs.getDouble("SalePrice");
                String serviceDetail = rs.getString("ServiceDetails");
                Date updateDate = rs.getDate("UpdateDate");
                boolean status = rs.getBoolean("Status");
                Service service = new Service(ServiceID, title, brief, thumbnail, categoryID, originalPrice, salePrice, serviceDetail, updateDate, status);
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
                String brief = rs.getString("BriefInfo");
                String thumbnail = rs.getString("Thumbnail");
                int categoryID = rs.getInt("CategoryID");
                double originalPrice = rs.getDouble("OriginalPrice");
                double salePrice = rs.getDouble("SalePrice");
                String serviceDetail = rs.getString("ServiceDetails");
                Date updateDate = rs.getDate("UpdateDate");
                boolean status = rs.getBoolean("Status");
                Service service = new Service(ServiceID, title, brief, thumbnail, categoryID, originalPrice, salePrice, serviceDetail, updateDate, status);
                serviceList.add(service);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceList;
    }
    public List<Service> getSortedActivePaged(int offSetPage, int numberOfPage) {
        List<Service> serviceList = new ArrayList<>();
        xSql = "SELECT *  FROM [dbo].[Services] where Status = 1 "
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
                String brief = rs.getString("BriefInfo");
                String thumbnail = rs.getString("Thumbnail");
                int categoryID = rs.getInt("CategoryID");
                double originalPrice = rs.getDouble("OriginalPrice");
                double salePrice = rs.getDouble("SalePrice");
                String serviceDetail = rs.getString("ServiceDetails");
                Date updateDate = rs.getDate("UpdateDate");
                boolean status = rs.getBoolean("Status");
                Service service = new Service(ServiceID, title, brief, thumbnail, categoryID, originalPrice, salePrice, serviceDetail, updateDate, status);
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
                + "where Title like ? and Status = 1 "
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
                String brief = rs.getString("BriefInfo");
                String thumbnail = rs.getString("Thumbnail");
                int categoryID = rs.getInt("CategoryID");
                double originalPrice = rs.getDouble("OriginalPrice");
                double salePrice = rs.getDouble("SalePrice");
                String serviceDetail = rs.getString("ServiceDetails");
                Date updateDate = rs.getDate("UpdateDate");
                boolean status = rs.getBoolean("Status");
                Service service = new Service(ServiceID, title, brief, thumbnail, categoryID, originalPrice, salePrice, serviceDetail, updateDate, status);
                serviceList.add(service);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceList;
    }
    
    public List<Service> getSortedPagedServicesBySearchTitleOrBrief(int offSetPage, int numberOfPage, String keyword) {
        List<Service> serviceList = new ArrayList<>();
        xSql = "SELECT *  FROM [dbo].[Services]  "
                + "where Title like ? OR BriefInfo like ?"
                + "OFFSET ? ROWS \n "
                + "FETCH NEXT ? ROWS ONLY;";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ps.setInt(3, offSetPage);
            ps.setInt(4, numberOfPage);
            
            rs = ps.executeQuery();
            while (rs.next()) {
                int ServiceID = rs.getInt("ServiceID");
                String title = rs.getString("Title");
                String brief = rs.getString("BriefInfo");
                String thumbnail = rs.getString("Thumbnail");
                int categoryID = rs.getInt("CategoryID");
                double originalPrice = rs.getDouble("OriginalPrice");
                double salePrice = rs.getDouble("SalePrice");
                String serviceDetail = rs.getString("ServiceDetails");
                Date updateDate = rs.getDate("UpdateDate");
                boolean status = rs.getBoolean("Status");
                Service service = new Service(ServiceID, title, brief, thumbnail, categoryID, originalPrice, salePrice, serviceDetail, updateDate, status);
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
        xSql = "SELECT s.ServiceID, s.Title, s.Thumbnail, s.CategoryID, s.OriginalPrice, s.SalePrice, s.ServiceDetails, s.BriefInfo, s.UpdateDate, s.Status "
                + "FROM ( "
                + "    SELECT * FROM [dbo].[Services] "
                + "    WHERE Title LIKE ? AND CategoryID = ? and Status = 1 "
                + ") AS s "
                + "JOIN ServiceStaff ss ON s.ServiceID = ss.ServiceID "
                + "WHERE ss.StaffID = ?"
                + "    ORDER BY s.UpdateDate, s.Status DESC "
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
                String brief = rs.getString("BriefInfo");
                String thumbnail = rs.getString("Thumbnail");
                int categoryID = rs.getInt("CategoryID");
                double originalPrice = rs.getDouble("OriginalPrice");
                double salePrice = rs.getDouble("SalePrice");
                String serviceDetail = rs.getString("ServiceDetails");
                Date updateDate = rs.getDate("UpdateDate");
                boolean status = rs.getBoolean("Status");
                Service service = new Service(serviceID, title, brief, thumbnail, categoryID, originalPrice, salePrice, serviceDetail, updateDate);
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
                + "    WHERE Title LIKE ? AND CategoryID = ? and Status = 1 "
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
                String brief = rs.getString("BriefInfo");
                String thumbnail = rs.getString("Thumbnail");
                int categoryID = rs.getInt("CategoryID");
                double originalPrice = rs.getDouble("OriginalPrice");
                double salePrice = rs.getDouble("SalePrice");
                String serviceDetail = rs.getString("ServiceDetails");
                Date updateDate = rs.getDate("UpdateDate");
                boolean status = rs.getBoolean("Status");
                Service service = new Service(serviceID, title, brief, thumbnail, categoryID, originalPrice, salePrice, serviceDetail, updateDate);
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
        xSql = "SELECT s.ServiceID, s.Title, s.Thumbnail, s.CategoryID, s.OriginalPrice, s.SalePrice, s.ServiceDetails, s.BriefInfo, s.UpdateDate, s.Status "
                + "FROM ( "
                + "    SELECT * FROM [dbo].[Services] "
                + "    WHERE Title LIKE ? and Status = 1 "
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
                String brief = rs.getString("BriefInfo");
                String thumbnail = rs.getString("Thumbnail");
                int categoryID = rs.getInt("CategoryID");
                double originalPrice = rs.getDouble("OriginalPrice");
                double salePrice = rs.getDouble("SalePrice");
                String serviceDetail = rs.getString("ServiceDetails");
                Date updateDate = rs.getDate("UpdateDate");
                boolean status = rs.getBoolean("Status");
                Service service = new Service(serviceID, title, brief, thumbnail, categoryID, originalPrice, salePrice, serviceDetail, updateDate);
                serviceList.add(service);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceList;
    }
    
    public List<Service> getPaginatedSortedAndFilteredServices(int offSetPage, int numberOfPage, String optionSort, String keyword) {
        List<Service> serviceList = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[Services] WHERE Title LIKE ? OR BriefInfo LIKE ? ORDER BY ";
        
        switch (optionSort) {
            case "title":
                sql += "Title";
                break;
            case "category":
                sql += "CategoryID";
                break;
            case "listPrice":
                sql += "OriginalPrice";
                break;
            case "salePrice":
                sql += "SalePrice";
                break;
            case "featured":
                sql += "Featured";
                break;
            case "status":
                sql += "Status";
                break;
            default:
                sql += "Title"; // Default sorting by title
                break;
        }
        
        sql += " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ps.setInt(3, offSetPage);
            ps.setInt(4, numberOfPage);
            
            rs = ps.executeQuery();
            while (rs.next()) {
                int ServiceID = rs.getInt("ServiceID");
                String title = rs.getString("Title");
                String brief = rs.getString("BriefInfo");
                String thumbnail = rs.getString("Thumbnail");
                int categoryID = rs.getInt("CategoryID");
                double originalPrice = rs.getDouble("OriginalPrice");
                double salePrice = rs.getDouble("SalePrice");
                String serviceDetail = rs.getString("ServiceDetails");
                Date updateDate = rs.getDate("UpdateDate");
                boolean status = rs.getBoolean("Status");
                Service service = new Service(ServiceID, title, brief, thumbnail, categoryID, originalPrice, salePrice, serviceDetail, updateDate, status);
                serviceList.add(service);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceList;
    }
    
    public int countFilteredServices(String keyword) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM [dbo].[Services] WHERE Title LIKE ? OR BriefInfo LIKE ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            
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
    
    public Service getServiceByID(String ID) {
        Service service = null;
        xSql = "select * from [dbo].[Services] where ServiceID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, ID);
            rs = ps.executeQuery();
            if (rs.next()) {
                int ServiceID = rs.getInt("ServiceID");
                String title = rs.getString("Title");
                String brief = rs.getString("BriefInfo");
                String thumbnail = rs.getString("Thumbnail");
                int categoryID = rs.getInt("CategoryID");
                double originalPrice = rs.getDouble("OriginalPrice");
                double salePrice = rs.getDouble("SalePrice");
                String serviceDetail = rs.getString("ServiceDetails");
                Date updateDate = rs.getDate("UpdateDate");
                boolean status = rs.getBoolean("Status");
                service = new Service(ServiceID, title, brief, thumbnail, categoryID, originalPrice, salePrice, serviceDetail, updateDate, status);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return service;
    }
    
    public void insert(Service service) {
        xSql = "INSERT INTO [dbo].[Services] (Title ,Thumbnail ,CategoryID ,OriginalPrice ,SalePrice ,ServiceDetails ,BriefInfo ,UpdateDate, Status) \n"
                + "     VALUES (? ,? ,? ,? ,? ,? ,?, ?, ?)";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, service.getTitle());
            ps.setString(2, service.getThumbnail());
            ps.setInt(3, service.getCategoryID());
            ps.setDouble(4, service.getOriginalPrice());
            ps.setDouble(5, service.getSalePrice());
            ps.setString(6, service.getServiceDetail());
            ps.setString(7, service.getBrief());
            ps.setDate(8, service.getUpdateDate());
            ps.setBoolean(9, service.getStatus());
            
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
                + "   SET [Title] = ? ,[Thumbnail] = ? ,[CategoryID] = ? ,[OriginalPrice] = ? ,[SalePrice] = ? ,[ServiceDetails] =?,[BriefInfo] = ?,[UpdateDate]=?,[Status] = ?"
                + " WHERE [ServiceID] = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, service.getTitle());
            ps.setString(2, service.getThumbnail());
            ps.setInt(3, service.getCategoryID());
            ps.setDouble(4, service.getOriginalPrice());
            ps.setDouble(5, service.getSalePrice());
            ps.setString(6, service.getServiceDetail());
            ps.setString(7, service.getBrief());
            ps.setDate(8, service.getUpdateDate());
            ps.setBoolean(9, service.getStatus());
            ps.setInt(10, service.getServiceID());
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
    
    public int getActiveServiceCount() {
        Service service = null;
        xSql = "SELECT COUNT(*) AS TotalRecords FROM Services where Status = '1' ";
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
        String sql = "SELECT  COUNT(DISTINCT s.ServiceID) AS count "
                + "FROM [dbo].[Services] AS s "
                + "JOIN ServiceStaff ss ON s.ServiceID = ss.ServiceID "
                + "WHERE s.Title LIKE ? and Status = 1 ";
        
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
    
    public String getServiceNameByID(int ID) {
        String serviceName = "";
        xSql = "SELECT Title  FROM [dbo].[Services] where ServiceID = ? ";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, ID);
            rs = ps.executeQuery();
            if (rs.next()) {
                String title = rs.getString("Title");
                serviceName = title;
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceName;
    }
    
    public List<String> getAllServicesName() {
        List<String> serviceNameList = new ArrayList<>();
        xSql = "SELECT Title  FROM [dbo].[Services]";
        try {
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String title = rs.getString("Title");
                serviceNameList.add(title);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceNameList;
    }
    
    public static void main(String[] args) {
        ServiceDAO serviceDAO = new ServiceDAO();
        CategoryServiceDAO categoryServiceDAO = new CategoryServiceDAO();
//        List<Service> list = serviceDAO.getSortedPagedServicesByOption(0, 20, "", "", "1");
        List<Service> list = serviceDAO.getAllServices();
//        List<Service> list = serviceDAO.getSortedPagedServicesBySearch(5, 5, "");
//            List<Service> list = serviceDAO.getSortedPaged(0, 5);
//        List<Service> list = serviceDAO.getPaginatedSortedAndFilteredServices(0, 10, "title", "");
        for (Service service : list) {
            System.out.println(service.getStatus());
        }
        
        System.out.println(serviceDAO.getServiceNameByID(1));

//            System.out.println(serviceDAO.getServiceByID(15+"").getBrief());
//        System.out.println((serviceDAO.getCountOfServicesUserChoose("", "", "")+4)/5);
//        System.out.println((serviceDAO.getServiceCount()+4)/5);
//        for (int i = 0; i < serviceDAO.getServiceCount(); i++) {
//            
//        }
//        Service service = serviceDAO.getServiceByID(15+"");
//        System.out.println(serviceDAO.getServiceByID(String.valueOf(11)));
//        service.setTitle("sss");
//        serviceDAO.update(service);
//        System.out.println(service.getTitle());
//        System.out.println(serviceDAO.getServiceByID(15+"").getStatus());
    }
}
