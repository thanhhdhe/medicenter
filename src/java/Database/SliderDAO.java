/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.util.ArrayList;
import java.util.List;
import model.Slider;

/**
 *
 * @author Admin
 */
public class SliderDAO extends MyDAO {

    public List<Slider> getAllSlide() {
        List<Slider> sliderList = new ArrayList<>();
        xSql = "SELECT *  FROM [dbo].[Slider]";
        try {
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int sliderId = rs.getInt("SliderId");
                String title = rs.getString("Title");
                String brief = rs.getString("Brief");
                String image = rs.getString("Images");
                String backlink = rs.getString("Backlink");
                String status = rs.getString("Status");
                Slider slider = new Slider(sliderId, title, brief, image, backlink, status);
                sliderList.add(slider);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sliderList;
    }

    public List<Slider> getAllSlideActive() {
        List<Slider> sliderList = new ArrayList<>();
        xSql = "SELECT *  FROM [dbo].[Slider] WHERE Status = 'Active'";
        try {
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int sliderId = rs.getInt("SliderId");
                String title = rs.getString("Title");
                String brief = rs.getString("Brief");
                String image = rs.getString("Images");
                String backlink = rs.getString("Backlink");
                String status = rs.getString("Status");
                Slider slider = new Slider(sliderId, title, brief, image, backlink, status);
                sliderList.add(slider);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sliderList;
    }

    public Slider getSlideByID(int sliderId) {
        String xSql = "SELECT * FROM [dbo].[Slider] WHERE SliderId = ?";
        Slider slider = null;

        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, sliderId);
            rs = ps.executeQuery();

            if (rs.next()) {
                String title = rs.getString("Title");
                String brief = rs.getString("Brief");
                String image = rs.getString("Images");
                String backlink = rs.getString("Backlink");
                String status = rs.getString("Status");

                slider = new Slider(sliderId, title, brief, image, backlink, status);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return slider;
    }

    public boolean updateSlider(Slider slider) {
        String xSql = "UPDATE [dbo].[Slider] SET Title = ?, Brief = ?, Images = ?, Backlink = ?, Status = ? WHERE SliderId = ?";

        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, slider.getTitle());
            ps.setString(2, slider.getBrief());
            ps.setString(3, slider.getImage());
            ps.setString(4, slider.getBacklink());
            ps.setString(5, slider.getStatus());
            ps.setInt(6, slider.getSliderID());

            int rowsUpdated = ps.executeUpdate();
            ps.close();

            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addSlide(String title, String brief, String image, String backlink, String status) {
        String insertSql = "INSERT INTO [dbo].[Slider] (Title, Brief, Images, Backlink, Status) VALUES (?, ?, ?, ?, ?)";

        try {
            ps = con.prepareStatement(insertSql);
            ps.setString(1, title);
            ps.setString(2, brief);
            ps.setString(3, image);
            ps.setString(4, backlink);
            ps.setString(5, status);

            int rowsInserted = ps.executeUpdate();
            ps.close();

            return rowsInserted > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteSlider(int sliderID) {
        String deleteSql = "DELETE FROM [dbo].[Slider] WHERE SliderId = ?";

        try {
            ps = con.prepareStatement(deleteSql);
            ps.setInt(1, sliderID);

            int rowsDeleted = ps.executeUpdate();
            ps.close();

            return rowsDeleted > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Slider> filterSliders(String status, String searchQuery, String sortBy, String sortOrder, int page) {
        List<Slider> sliders = new ArrayList<>();
        int pageSize = 10; // Number of records per page
        int offset = (page - 1) * pageSize; // Start position of data on the current page

        String sql = "SELECT * FROM Slider WHERE 1 = 1"; // 1 = 1 for easy condition adding

        // Check if status filter is applied
        if (status != null && !status.isEmpty()) {
            sql += " AND Status = ?";
        }

        // Check if search query is provided
        if (searchQuery != null && !searchQuery.isEmpty()) {
            sql += " AND (Title LIKE ? OR Backlink LIKE ? OR Brief LIKE ?)";
        }

        if (sortBy != null && !sortBy.isEmpty()) {
            sql += " ORDER BY ";
            switch (sortBy) {
                case "SliderID":
                    sql += "SliderID";
                    break;
                case "Title":
                    sql += "Title";
                    break;
                case "Brief":
                    sql += "Brief";
                    break;
                case "Backlink":
                    sql += "Backlink";
                    break;
                case "Status":
                    sql += "Status";
                    break;
                default:
                    sql += "SliderID"; // Default sorting by SliderID
            }

            if ("desc".equalsIgnoreCase(sortOrder)) {
                sql += " DESC";
            } else {
                sql += " ASC"; // Default sorting is ASC
            }
        } else {
            sql += " ORDER BY SliderID"; // Default sorting by SliderID
        }

        sql += " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try {
            ps = con.prepareStatement(sql);
            int paramIndex = 1;

            // Set status filter parameter
            if (status != null && !status.isEmpty()) {
                ps.setString(paramIndex++, status);
            }

            // Set search query parameters for Title, Backlink, and Brief
            if (searchQuery != null && !searchQuery.isEmpty()) {
                ps.setString(paramIndex++, "%" + searchQuery + "%");
                ps.setString(paramIndex++, "%" + searchQuery + "%");
                ps.setString(paramIndex++, "%" + searchQuery + "%"); // Add search parameter for Brief
            }

            // Set offset and pageSize parameters for pagination
            ps.setInt(paramIndex++, offset);
            ps.setInt(paramIndex++, pageSize);
            rs = ps.executeQuery();

            while (rs.next()) {
                // Get slider information from ResultSet and add it to the list
                int sliderID = rs.getInt("SliderID");
                String title = rs.getString("Title");
                String brief = rs.getString("Brief");
                String image = rs.getString("Images");
                String backlink = rs.getString("Backlink");
                String sliderStatus = rs.getString("Status");
                Slider slider = new Slider(sliderID, title, brief, image, backlink, sliderStatus);
                sliders.add(slider);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sliders;
    }

    public int countFilteredSliders(String status, String searchQuery) {
        int count = 0;

        String sql = "SELECT COUNT(*) FROM Slider WHERE 1 = 1";

        // Check if status filter is applied
        if (status != null && !status.isEmpty()) {
            sql += " AND Status = ?";
        }

        // Check if search query is provided
        if (searchQuery != null && !searchQuery.isEmpty()) {
            sql += " AND (Title LIKE ? OR Backlink LIKE ? OR Brief LIKE ?)";
        }

        try {
            ps = con.prepareStatement(sql);
            int paramIndex = 1;

            // Set status filter parameter
            if (status != null && !status.isEmpty()) {
                ps.setString(paramIndex++, status);
            }

            // Set search query parameters for Title, Backlink, and Brief
            if (searchQuery != null && !searchQuery.isEmpty()) {
                ps.setString(paramIndex++, "%" + searchQuery + "%");
                ps.setString(paramIndex++, "%" + searchQuery + "%");
                ps.setString(paramIndex++, "%" + searchQuery + "%"); // Add search parameter for Brief
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

}
