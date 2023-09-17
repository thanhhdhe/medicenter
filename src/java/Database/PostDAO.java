/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.sql.Date;
import java.sql.SQLException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import model.Post;

/**
 *
 * @author quanh
 */
public class PostDAO extends MyDAO {

    public List<Post> getAllPosts() {
        List<Post> postList = new ArrayList<>();
        xSql = "SELECT *  FROM [dbo].[Posts]";
        try {
            System.out.println("method");
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            while (rs.next()) {
//                System.out.println("method");
                int postID = rs.getInt(1);
                String title = rs.getString(2);
                String content = rs.getString(3);
                String briefInfo = rs.getString(4);
                String thumbnail = rs.getString(5);
                int authorID = rs.getInt(6);
                int serviceID = rs.getInt(7);
                Date createdDate = rs.getDate(8);
                String categoryPost = rs.getString(9);
//                System.out.println("method");
                Post post = new Post(postID, title, content, briefInfo, thumbnail, authorID, serviceID, createdDate, categoryPost);
                postList.add(post);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postList;
    }

    public List<Post> searchPost(String txtSearch) {
        List<Post> postList = new ArrayList<>();
        xSql = "SELECT *  FROM [dbo].[Posts] where [Title] like ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, "%" + txtSearch + "%");
            rs=ps.executeQuery();
            while (rs.next()) {
                int postID = rs.getInt(1);
                String title = rs.getString(2);
                String content = rs.getString(3);
                String briefInfo = rs.getString(4);
                String thumbnail = rs.getString(5);
                int authorID = rs.getInt(6);
                int serviceID = rs.getInt(7);
                Date createdDate = rs.getDate(8);
                String categoryPost = rs.getString(9);
                Post post = new Post(postID, title, content, briefInfo, thumbnail, authorID, serviceID, createdDate, categoryPost);
                postList.add(post);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postList;
    }

    public List<Post> searchPostByCategory(String Category) {
        List<Post> postList = new ArrayList<>();
        xSql = "SELECT *  FROM [dbo].[Posts] where [CategoryPost] like ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, "%" + Category + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                int postID = rs.getInt(1);
                String title = rs.getString(2);
                String content = rs.getString(3);
                String briefInfo = rs.getString(4);
                String thumbnail = rs.getString(5);
                int authorID = rs.getInt(6);
                int serviceID = rs.getInt(7);
                Date createdDate = rs.getDate(8);
                String categoryPost = rs.getString(9);
                Post post = new Post(postID, title, content, briefInfo, thumbnail, authorID, serviceID, createdDate, categoryPost);
                postList.add(post);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postList;
    }

    public List<String> allCategoryPost() {
        xSql = "SELECT DISTINCT CategoryPost FROM Posts";
        List<String> categoryList = new ArrayList<>();
        try {
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                categoryList.add(rs.getString(1));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    public static void main(String[] args) {
        PostDAO dao = new PostDAO();
        List<String> list= dao.allCategoryPost();
        for (String string : list) {
            System.out.println(string);
        }
        

    }

}
