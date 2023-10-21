/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.util.ArrayList;
import java.util.List;
import model.Setting;
import model.Slider;

/**
 *
 * @author pc
 */
public class SettingDAO extends MyDAO {

    public int getTotalPageSetting() {
        xSql = "select count(*) from Settings;";
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

    public Setting getSettingByID(String settingId) {
        Setting setting;
        xSql = "select * from Settings where SettingID = ?;";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, settingId);
            rs = ps.executeQuery();
            if (rs.next()) {
                int settingID = rs.getInt(1);
                String type = rs.getString(2);
                String settingName = rs.getString(3);
                String value = rs.getString(4);
                String description = rs.getString(5);
                String status = rs.getString(6);
                setting = new Setting(settingID, type, settingName, value, description, status);
                return setting;
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Setting> getSettingList(int page) {
        List<Setting> settingList = new ArrayList<>();
        xSql = "select * from Settings\n"
                + "ORDER BY SettingID\n"
                + "OFFSET ? Rows fetch next 10 rows ONLY;";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, (page - 1) * 10);
            rs = ps.executeQuery();
            while (rs.next()) {
                int settingID = rs.getInt(1);
                String type = rs.getString(2);
                String settingName = rs.getString(3);
                String value = rs.getString(4);
                String description = rs.getString(5);
                String status = rs.getString(6);
                Setting setting = new Setting(settingID, type, settingName, value, description, status);
                settingList.add(setting);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return settingList;
    }
    
    // update
    public void updateSetting(String status, String settingId, String Name, String type , String value, String description) {
        xSql = "update Settings set Status = ?, Name=?, Type = ? , Description = ? , Value = ? where SettingID = ?;";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, status);
            ps.setString(2, Name);
            ps.setString(3, type);
            ps.setString(4, description);
            ps.setString(5, value);
            ps.setString(6, settingId);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // update
    public void updateStatus(String status, String FID) {
        xSql = "update Settings set Status = ? where SettingID = ?;";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, status);
            ps.setString(2, FID);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
