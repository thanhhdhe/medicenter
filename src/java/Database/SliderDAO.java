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
                String image = rs.getString("Images");
                String backlink = rs.getString("Backlink");
                String status = rs.getString("status");
                String Brief = rs.getString("Brief");
                Slider slider = new Slider(sliderId, title, image, backlink, status);
                sliderList.add(slider);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sliderList;
    }
}
