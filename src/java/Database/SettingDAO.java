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
    
    public static String getLastSegment(String inputString) {
        int lastSlashIndex = inputString.lastIndexOf('/');

        if (lastSlashIndex != -1) {
            // Trích xuất chuỗi từ vị trí cuối cùng của '/' đến cuối chuỗi
            String result = inputString.substring(lastSlashIndex + 1);
            return result;
        } else {
            // Trường hợp không tìm thấy '/'
            return inputString;
        }
    }
    
    //insert feedback of user
    public void InsertSetting(String type, String name, String value, String Description, String status) {
        xSql = "Insert Into Settings(Type,Name,Value,Description,Status)\n"
                + "Values\n"
                + "(?,?,?,?,?);";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, type);
            ps.setString(2, name);
            ps.setString(3, "resources/img/"+getLastSegment(value));
            ps.setString(4, Description);
            ps.setString(5, status);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public int getTotalPageSettingBySearch(String stype, String sname, String sdescription, String sstatus, String svalue) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Settings WHERE 1=1");

        // Create a list to store the prepared statement parameters
        List<Object> parameters = new ArrayList<>();

        if (stype != null && !stype.isEmpty()) {
            sql.append(" AND Type = ?");
            parameters.add(stype);
        }

        if (sname != null && !sname.isEmpty()) {
            sql.append(" AND Name LIKE ?");
            parameters.add("%" + sname + "%");
        }

        if (sdescription != null && !sdescription.isEmpty()) {
            sql.append(" AND Description = ?");
            parameters.add(sdescription);
        }

        if (sstatus != null && !sstatus.isEmpty()) {
            sql.append(" AND Status = ?");
            parameters.add(sstatus);
        }

        if (svalue != null && !svalue.isEmpty()) {
            sql.append(" AND Value LIKE ?");
            parameters.add("%" + svalue + "%");
        }

        try {
            ps = con.prepareStatement(sql.toString());
            int parameterIndex = 1;
            for (Object parameter : parameters) {
                if (parameter instanceof String) {
                    ps.setString(parameterIndex, (String) parameter);
                }
                parameterIndex++;
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {

        }
        return 0;
    }

    public List<Setting> getSettingListBySearch(int page, String stype, String sname, String sdescription, String sstatus, String svalue, String sortBy) {
        List<Setting> settingList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Settings WHERE 1=1");

        // Create a list to store the prepared statement parameters
        List<Object> parameters = new ArrayList<>();

        if (stype != null && !stype.isEmpty()) {
            sql.append(" AND Type = ?");
            parameters.add(stype);
        }

        if (sname != null && !sname.isEmpty()) {
            sql.append(" AND Name LIKE ?");
            parameters.add("%" + sname + "%");
        }

        if (sdescription != null && !sdescription.isEmpty()) {
            sql.append(" AND Description = ?");
            parameters.add(sdescription);
        }

        if (sstatus != null && !sstatus.isEmpty()) {
            sql.append(" AND Status = ?");
            parameters.add(sstatus);
        }

        if (svalue != null && !svalue.isEmpty()) {
            sql.append(" AND Value LIKE ?");
            parameters.add("%" + svalue + "%");
        }

        String orderBy = "SettingID";
        if (sortBy != null && !sortBy.isEmpty()) {
            switch (sortBy) {
                case "type":
                    orderBy = "Type";
                    break;
                case "Value":
                    orderBy = "Value";
                    break;
                case "Status":
                    orderBy = "Status";
                    break;
                default:
                    orderBy = "SettingID";
            }
        }
        sql.append(" ORDER BY ").append(orderBy);

        int offset = (page - 1) * 10;
        sql.append(" OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY");

        try {
            ps = con.prepareStatement(sql.toString());
            int parameterIndex = 1;
            for (Object parameter : parameters) {
                if (parameter instanceof String) {
                    ps.setString(parameterIndex, (String) parameter);
                }
                parameterIndex++;
            }
            ps.setInt(parameterIndex, offset);

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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return settingList;
    }

    // update
    public void updateSetting(String status, String settingId, String Name, String type, String value, String description) {
        xSql = "update Settings set Status = ?, Name=?, Type = ? , Description = ? , Value = ? where SettingID = ?;";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, status);
            ps.setString(2, Name);
            ps.setString(3, type);
            ps.setString(4, description);
            ps.setString(5, "resources/img/"+value);
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

    public static void main(String[] args) {
        SettingDAO dao = new SettingDAO();
//        System.out.println(getLastSegment("resources/imgimage1.jpg"));
//        dao.InsertSetting("Slider", "BOOK QUICK MEDICAL EXAMINATION", "resources/img/image1.jpg", "Book your examination quickly and economically time, safety and convenience", "Active");
//        List<Setting> settingList = dao.getSettingListBySearch(1, "", "OUR SERVICE", "", "","","");
//        System.out.println(dao.getTotalPageSettingBySearch( "", "OUR SERVICE", "", "","")+"sad");
//        for (Setting setting : settingList) {
//            System.out.println(setting.getSettingID());
//        }
    }
}
