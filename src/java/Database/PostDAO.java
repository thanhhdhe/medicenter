/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

/**
 *
 * @author quanh
 */
public class PostDAO extends MyDAO {

    //sort post by updated date
    public void sortPost() {
        xSql = "select * from Posts order by CreatedDate";
        try {
            ps = con.prepareStatement(xSql);
            ps.executeQuery();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //
}
