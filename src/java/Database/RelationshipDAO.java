/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import model.Relationship;

/**
 *
 * @author Admin
 */
public class RelationshipDAO extends MyDAO{
    public Relationship getRelationByID(int relationshipID) {

        xSql = "select * from [dbo].[Relationship] where RelationshipID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, relationshipID);
            rs = ps.executeQuery();
            if (rs.next()) {
                Relationship relationship = new Relationship(rs.getInt("RelationshipID"), rs.getString("RelationshipName"));
                return relationship;
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
