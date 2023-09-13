/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Post;

/**
 *
 * @author quanh
 */
public class PostDAO extends MyDAO {

    //sort post by updated date
    public List<Post> sortPost() {
        List<Post> postList = new ArrayList<>();
        xSql = "select * from Posts order by CreatedDate";
        try {
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String title = rs.getString("Title");
                String content = rs.getString("Content");
                int authorID = rs.getInt("AuthorID");
                int serviceID = rs.getInt("ServiceID");
                Date createDate = rs.getDate("CreatedDate");
                int categoryPostID = rs.getInt("CategoryPostID");
                String briefInfo = rs.getString("BriefInfo");
                String thumbnail = rs.getString("thumbnail");
                Post post = new Post(title, content, authorID, serviceID, createDate, categoryPostID, briefInfo, thumbnail);
                postList.add(post);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postList;

    }

    public void insert(Post post) {
        xSql = "INSERT INTO [dbo].[Post] ([Title] ,[Content] ,[BriefInfo], [thumbnail], [AuthorID] ,[ServiceID] ,"
                + "[CreatedDate] ,[CategoryPostID] \n"
                + "VALUES (? ,? ,? ,? ,? ,?,?,?)";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getContent());
            ps.setString(3, post.getBriefInfo());
            ps.setString(4, post.getThumbnail());
            ps.setInt(5, post.getAuthorID());
            ps.setInt(6, post.getServiceID());
            ps.setDate(7, post.getCreatedDate());
            ps.setDouble(8, post.getCategoryPostID());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteByID(String ID) {
        xSql = "delete from Services where [PostID]=?";
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

    public void update(Post post) {
        xSql = "UPDATE [dbo].[Post]\n"
                + "SET [Title] = ? ,[Content] = ? , [BriefInfo] = ? , [thumbnail] = ?, [AuthorID] = ? ,[ServiceID] = ? ,[CreatedDate] = ? ,[CategoryPostID] = ? "
                + "WHERE [PostID] = ?";

        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getContent());
            ps.setString(3, post.getBriefInfo());
            ps.setString(4, post.getThumbnail());
            ps.setInt(5, post.getAuthorID());
            ps.setInt(6, post.getServiceID());
            ps.setDate(7, post.getCreatedDate());
            ps.setDouble(8, post.getCategoryPostID());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            // Handle exception appropriately
            System.out.println(e);
        }
    }

    // search by titlte
    public List<Post> searchByTitle(String a) {
        List<Post> postList = new ArrayList<>();
        xSql = "SELECT * FROM [dbo].[Post]"
                + "WHERE [Title] LIKE '?'"
                + "ORDER BY [CreatedDate]";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, a);
            rs = ps.executeQuery();
            while (rs.next()) {
                String title = rs.getString("Title");
                String content = rs.getString("Content");
                String briefInfo = rs.getString("BriefInfo");
                String thumbnail= rs.getString("thumbnail");
                int authorID = rs.getInt("AuthorID");
                int serviceID = rs.getInt("ServiceID");
                Date createDate = rs.getDate("CreatedDate");
                int categoryPostID = rs.getInt("CategoryPostID");
                Post post = new Post(title, content, authorID, serviceID, createDate, categoryPostID, briefInfo, thumbnail);
                postList.add(post);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postList;
    }
}
