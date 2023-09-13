/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import model.CategoryService;

/**
 *
 * @author Admin
 */
public class CategoryServiceDAO extends MyDAO{
        
    public List<CategoryService> getAllCategoryServices() {
        List<CategoryService> categoryServiceList = new ArrayList<>();
        xSql = "SELECT *  FROM [dbo].[CategoryService]";
        try {
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int CategoryServiceID = rs.getInt("CategoryID");
                String categoryName = rs.getString("CategoryName");
                String description = rs.getString("Description");
                CategoryService categoryService = new CategoryService(CategoryServiceID, categoryName, description);
                categoryServiceList.add(categoryService);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryServiceList;
    }

    public CategoryService getCategoryServiceByID(String ID) {
        CategoryService categoryService = null;
        xSql = "select * from [dbo].[CategoryService] where CategoryID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, ID);
            rs = ps.executeQuery();
            if (rs.next()) {
                int CategoryServiceID = rs.getInt("CategoryID");
                String categoryName = rs.getString("CategoryName");
                String description = rs.getString("Description");
                categoryService = new CategoryService(CategoryServiceID, categoryName, description);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryService;
    }

    public static void main(String[] args) {
        CategoryServiceDAO categoryServiceDAO = new CategoryServiceDAO();
        List<CategoryService> categoryServiceList = categoryServiceDAO.getAllCategoryServices();
        for (CategoryService categoryService : categoryServiceList) {
            System.out.println(categoryService.getCategoryID());
        }
    }
            
}
