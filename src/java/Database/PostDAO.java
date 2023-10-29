/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Post;

/**
 *
 * @author Admin
 */
public class PostDAO extends MyDAO {

    public List<Post> getAllPosts() {
        List<Post> postList = new ArrayList<>();
        xSql = "SELECT *  FROM [dbo].[Posts]";
        try {
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int postID = rs.getInt(1);
                String title = rs.getString(2);
                String content = rs.getString(3);
                String briefInfo = rs.getString(4);
                String thumbnail = rs.getString(5);
                int count = rs.getInt(6);
                int authorID = rs.getInt(7);
                int serviceID = rs.getInt(8);
                Date createdDate = rs.getDate(9);
                String categoryPost = rs.getString(10);
                Boolean statusPost = rs.getBoolean(11);
                Post post = new Post(postID, title, content, briefInfo, thumbnail, count, authorID, serviceID, createdDate, categoryPost, statusPost);
                postList.add(post);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postList;
    }

    public List<Post> get3HotestPost() {
        List<Post> postList = new ArrayList<>();
        xSql = "SELECT *\n"
                + "FROM\n"
                + "    Posts WHERE StatusPost = 1\n"
                + "ORDER BY\n"
                + "    Counts DESC\n"
                + "OFFSET 1 ROWS \n"
                + "FETCH NEXT 3 ROWS ONLY; ";
        try {
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int postID = rs.getInt(1);
                String title = rs.getString(2);
                String content = rs.getString(3);
                String briefInfo = rs.getString(4);
                String thumbnail = rs.getString(5);
                int count = rs.getInt(6);
                int authorID = rs.getInt(7);
                int serviceID = rs.getInt(8);
                Date createdDate = rs.getDate(9);
                String categoryPost = rs.getString(10);
                Boolean statusPost = rs.getBoolean(11);
                Post post = new Post(postID, title, content, briefInfo, thumbnail, count, authorID, serviceID, createdDate, categoryPost, statusPost);
                postList.add(post);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postList;
    }

    public List<Post> getPostedPaged(int offSetPage, int numberOfPage) {
        List<Post> postList = new ArrayList<>();
        xSql = "SELECT *  FROM [dbo].[Posts]  "
                + "ORDER BY CreatedDate DESC "
                + "OFFSET ? ROWS \n "
                + "FETCH NEXT ? ROWS ONLY;";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, offSetPage);
            ps.setInt(2, numberOfPage);

            rs = ps.executeQuery();
            while (rs.next()) {
                int postID = rs.getInt(1);
                String title = rs.getString(2);
                String content = rs.getString(3);
                String briefInfo = rs.getString(4);
                String thumbnail = rs.getString(5);
                int count = rs.getInt(6);
                int authorID = rs.getInt(7);
                int serviceID = rs.getInt(8);
                Date createdDate = rs.getDate(9);
                String categoryPost = rs.getString(10);
                Boolean statusPost = rs.getBoolean(11);
                Post post = new Post(postID, title, content, briefInfo, thumbnail, count, authorID, serviceID, createdDate, categoryPost, statusPost);
                postList.add(post);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postList;
    }

    public Post getPostByID(int ID) {
        Post post = null;
        xSql = "select * from [dbo].[Posts] where PostID = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, ID);
            rs = ps.executeQuery();
            if (rs.next()) {
                int postID = rs.getInt(1);
                String title = rs.getString(2);
                String content = rs.getString(3);
                String briefInfo = rs.getString(4);
                String thumbnail = rs.getString(5);
                int count = rs.getInt(6);
                int authorID = rs.getInt(7);
                int serviceID = rs.getInt(8);
                Date createdDate = rs.getDate(9);
                String categoryPost = rs.getString(10);
                Boolean statusPost = rs.getBoolean(11);
                post = new Post(postID, title, content, briefInfo, thumbnail, count, authorID, serviceID, createdDate, categoryPost, statusPost);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    public Post getHotestPost() {
        Post post = null;
        xSql = " SELECT TOP 1 * FROM Posts where StatusPost = 1 ORDER BY Counts DESC;";
        try {
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            if (rs.next()) {
                int postID = rs.getInt(1);
                String title = rs.getString(2);
                String content = rs.getString(3);
                String briefInfo = rs.getString(4);
                String thumbnail = rs.getString(5);
                int count = rs.getInt(6);
                int authorID = rs.getInt(7);
                int serviceID = rs.getInt(8);
                Date createdDate = rs.getDate(9);
                String categoryPost = rs.getString(10);
                Boolean statusPost = rs.getBoolean(11);
                post = new Post(postID, title, content, briefInfo, thumbnail, count, authorID, serviceID, createdDate, categoryPost, statusPost);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    public void insert(Post post) {
        xSql = "INSERT INTO Posts(Title, Content, BriefInfo, Thumbnail, AuthorID, ServiceID, CreatedDate, CategoryPost) \n"
                + "     VALUES (? ,? ,? ,? ,? ,? ,?, ?)";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getContent());
            ps.setString(3, post.getBriefInfo());
            ps.setString(4, post.getThumbnail());
            ps.setInt(5, post.getAuthorID());
            ps.setInt(6, post.getServiceID());
            ps.setDate(7, post.getCreatedDate());
            ps.setString(8, post.getCategoryPost());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteByID(int ID) {
        xSql = "delete from Posts where [PostID]=?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, ID);
            ps.executeUpdate();
            //con.commit();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(int postID, Post post) {
        xSql = "UPDATE [dbo].[Posts]\n"
                + "   SET Title=?,Content=?,BriefInfo=?,Thumbnail=?,AuthorID=?,ServiceID=? ,CreatedDate=?, CategoryPost=?, StatusPost=?"
                + " WHERE [PostID] = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getContent());
            ps.setString(3, post.getBriefInfo());
            ps.setString(4, post.getThumbnail());
            ps.setInt(5, post.getAuthorID());
            ps.setInt(6, post.getServiceID());
            ps.setDate(7, post.getCreatedDate());
            ps.setString(8, post.getCategoryPost());
            ps.setBoolean(9, post.isStatusPost());
            ps.setInt(10, postID);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            // Handle exception appropriately
            System.out.println(e);
        }
    }

    public List<Post> getSortedPagedPostsByUserChoice(int offSetPage, int numberOfPage, String keyword, String postCategory) {
        List<Post> postList = new ArrayList<>();
        xSql = "SELECT *  FROM Posts "
                + "WHERE Title like ? and StatusPost= 'true' ";
        if (!postCategory.isEmpty()) {
            xSql += " and CategoryPost = ? ";
        }
        xSql += "    ORDER BY CreatedDate DESC "
                + "    OFFSET ? ROWS "
                + "    FETCH NEXT ? ROWS ONLY ";
        try {
            int index = 2;
            ps = con.prepareStatement(xSql);
            ps.setString(1, "%" + keyword + "%");
            if (!postCategory.isEmpty()) {
                ps.setString(index, postCategory);
                index++;
            }
            ps.setInt(index, offSetPage);
            index++;
            ps.setInt(index, numberOfPage);

            rs = ps.executeQuery();
            while (rs.next()) {
                int postID = rs.getInt(1);
                String title = rs.getString(2);
                String content = rs.getString(3);
                String briefInfo = rs.getString(4);
                String thumbnail = rs.getString(5);
                int count = rs.getInt(6);
                int authorID = rs.getInt(7);
                int serviceID = rs.getInt(8);
                Date createdDate = rs.getDate(9);
                String categoryPost = rs.getString(10);
                Boolean statusPost = rs.getBoolean(11);
                Post post = new Post(postID, title, content, briefInfo, thumbnail, count, authorID, serviceID, createdDate, categoryPost, statusPost);
                postList.add(post);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postList;
    }

    public List<Post> getSortedPagedPostsByManagerChoice(int offSetPage, int numberOfPage, String keyword, String postCategory, String AuthorID, String postStatus, String sortBy) {
        List<Post> postList = new ArrayList<>();
        xSql = "SELECT *  FROM Posts "
                + "WHERE Title like ? ";
        if (!postCategory.isEmpty()) {
            xSql += " and CategoryPost = ? ";
        }
        if (!AuthorID.isEmpty()) {
            xSql += " and AuthorID = ? ";
        }
        if (!postStatus.isEmpty()) {
            xSql += "and StatusPost= ? ";
        }
        if (sortBy.isEmpty()) {
            sortBy = "Title";
        }
        xSql += " ORDER BY ";
        xSql += sortBy;
        xSql += " OFFSET ? ROWS "
                + " FETCH NEXT ? ROWS ONLY ";
        try {
            int index = 2;
            ps = con.prepareStatement(xSql);
            ps.setString(1, "%" + keyword + "%");
            if (!postCategory.isEmpty()) {
                ps.setString(index, postCategory);
                index++;
            }
            if (!AuthorID.isEmpty()) {
                int authorID = Integer.parseInt(AuthorID);
                ps.setInt(index, authorID);
                index++;
            }
            if (!postStatus.isEmpty()) {
                boolean status = Boolean.parseBoolean(postStatus);
                ps.setBoolean(index, status);
                index++;
            }
            ps.setInt(index, offSetPage);
            index++;
            ps.setInt(index, numberOfPage);

            rs = ps.executeQuery();
            while (rs.next()) {
                int postID = rs.getInt(1);
                String title = rs.getString(2);
                String content = rs.getString(3);
                String briefInfo = rs.getString(4);
                String thumbnail = rs.getString(5);
                int count = rs.getInt(6);
                int authorID = rs.getInt(7);
                int serviceID = rs.getInt(8);
                Date createdDate = rs.getDate(9);
                String categoryPost = rs.getString(10);
                Boolean statusPost = rs.getBoolean(11);
                Post post = new Post(postID, title, content, briefInfo, thumbnail, count, authorID, serviceID, createdDate, categoryPost, statusPost);
                postList.add(post);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postList;
    }

    public int getCountOfPostsUserChoose(String keyword, String categoryPost) {
        int count = 0;
        xSql = "SELECT COUNT(*) AS count "
                + "FROM [dbo].[Posts]"
                + "WHERE Title LIKE ? ";
        if (!categoryPost.isEmpty()) {
            xSql += "AND CategoryPost = ? ";
        }
        xSql += "AND StatusPost='true'";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, "%" + keyword + "%");

            if (!categoryPost.isEmpty()) {
                ps.setString(2, categoryPost);
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

    public int getCountOfPostsManagerChoose(String keyword, String categoryPost, String AuthorID, String postStatus) {
        int count = 0;
        xSql = "SELECT COUNT(*) AS count "
                + "FROM [dbo].[Posts]"
                + "WHERE Title LIKE ? ";

        if (!categoryPost.isEmpty()) {
            xSql += "AND CategoryPost = ? ";
        }
        if (!AuthorID.isEmpty()) {
            xSql += " and AuthorID = ? ";
        }
        if (!postStatus.isEmpty()) {
            xSql += "and StatusPost= ? ";
        }
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, "%" + keyword + "%");
            int index = 2;
            if (!categoryPost.isEmpty()) {
                ps.setString(index, categoryPost);
                index++;
            }
            if (!AuthorID.isEmpty()) {
                int authorID = Integer.parseInt(AuthorID);
                ps.setInt(index, authorID);
                index++;
            }
            if (!postStatus.isEmpty()) {
                boolean status = Boolean.parseBoolean(postStatus);
                ps.setBoolean(index, status);
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

    public List<String> allCategoryPost() {
        xSql = "SELECT DISTINCT CategoryPost FROM Posts ";
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

    public List<Integer> allAuthorID() {
        xSql = "select  DISTINCT AuthorID from Posts";
        List<Integer> authorList = new ArrayList<>();
        try {
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                authorList.add(rs.getInt(1));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authorList;
    }

    public String getAvatarByUserID(int authourID) {
        xSql = "select top 1 u.ProfileImage from Users as u join Posts as p on u.UserID = p.AuthorID where p.AuthorID=?";
        String avatar = "";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, authourID);
            rs = ps.executeQuery();
            if (rs.next()) {
                avatar = rs.getString(1);
            }
        } catch (Exception e) {
        }
        return avatar;
    }

    public String getNameByUserID(int authourID) {
        xSql = "select top 1 u.FirstName from Users as u join Posts as p on u.UserID = p.AuthorID where p.AuthorID=?";
        String avatar = "";
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, authourID);
            rs = ps.executeQuery();
            if (rs.next()) {
                avatar = rs.getString(1);
            }
        } catch (Exception e) {
        }
        return avatar;
    }

    public List<Post> postsSortByUserChoice(String choice) {
        List<Post> postList = new ArrayList<>();
        xSql = "SELECT *  FROM [dbo].[Posts] Order by";
        xSql += choice;
        try {
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int postID = rs.getInt(1);
                String title = rs.getString(2);
                String content = rs.getString(3);
                String briefInfo = rs.getString(4);
                String thumbnail = rs.getString(5);
                int count = rs.getInt(6);
                int authorID = rs.getInt(7);
                int serviceID = rs.getInt(8);
                Date createdDate = rs.getDate(9);
                String categoryPost = rs.getString(10);
                Boolean statusPost = rs.getBoolean(11);
                Post post = new Post(postID, title, content, briefInfo, thumbnail, count, authorID, serviceID, createdDate, categoryPost, statusPost);
                postList.add(post);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postList;
    }

    public int getLastPostID() {
        int lastID = 1;
        xSql = "select Top 1 PostID  from Posts order by PostID desc";
        try {
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            if (rs.next()) {
                lastID = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastID;
    }

    public static void main(String[] args) {
        PostDAO postDAO = new PostDAO();
        List<Post> list= postDAO.getSortedPagedPostsByUserChoice(7, 6, "a", "Health");
        for (Post post : list) {
            System.out.println(post.getTitle());
        }
    }
}
