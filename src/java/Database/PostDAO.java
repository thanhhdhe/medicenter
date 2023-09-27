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
                + "    Posts\n"
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

    public List<Post> getPostedPagedPostsBySearch(int offSetPage, int numberOfPage, String keyword) {
        List<Post> postList = new ArrayList<>();
        xSql = "SELECT *  FROM [dbo].[Posts]  "
                + "where Title like ? "
                + "ORDER BY CreatedDate DESC "
                + "OFFSET ? ROWS \n "
                + "FETCH NEXT ? ROWS ONLY;";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, "%" + keyword + "%");
            ps.setInt(2, offSetPage);
            ps.setInt(3, numberOfPage);

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

    public List<Post> getSortedPagedPostsByCategory(int offSetPage, int numberOfPage, String keyword, String postCategory) {
        List<Post> postList = new ArrayList<>();
        xSql = "SELECT *  FROM Posts "
                + "WHERE Title like ? and CategoryPost = ?"
                + "    ORDER BY CreatedDate DESC "
                + "    OFFSET ? ROWS "
                + "    FETCH NEXT ? ROWS ONLY ";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, postCategory);
            ps.setInt(3, offSetPage);
            ps.setInt(4, numberOfPage);

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

    public List<Post> getSortedPagedPostsByAuthor(int offSetPage, int numberOfPage, String keyword, int Author) {
        List<Post> postList = new ArrayList<>();
        xSql = "SELECT *  FROM Posts "
                + "WHERE Title like ? and AuthorID = ?"
                + "    ORDER BY CreatedDate DESC "
                + "    OFFSET ? ROWS "
                + "    FETCH NEXT ? ROWS ONLY ";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, "%" + keyword + "%");
            ps.setInt(2, Author);
            ps.setInt(3, offSetPage);
            ps.setInt(4, numberOfPage);

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

//    public List<Post> getSortedPagedPostsByStatus(int offSetPage, int numberOfPage, String keyword, boolean Status) {
//        List<Post> postList = new ArrayList<>();
//        xSql = "SELECT *  FROM Posts "
//                + "WHERE Title like ? and CategoryPost = ?"
//                + "    ORDER BY CreatedDate DESC "
//                + "    OFFSET ? ROWS "
//                + "    FETCH NEXT ? ROWS ONLY ";
//        try {
//            ps = con.prepareStatement(xSql);
//            ps.setString(1, "%" + keyword + "%");
//            ps.set
//            ps.setInt(3, offSetPage);
//            ps.setInt(4, numberOfPage);
//
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                int postID = rs.getInt(1);
//                String title = rs.getString(2);
//                String content = rs.getString(3);
//                String briefInfo = rs.getString(4);
//                String thumbnail = rs.getString(5);
//                int count = rs.getInt(6);
//                int authorID = rs.getInt(7);
//                int serviceID = rs.getInt(8);
//                Date createdDate = rs.getDate(9);
//                String categoryPost = rs.getString(10);
//                Post post = new Post(postID, title, content, briefInfo, thumbnail, count, authorID, serviceID, createdDate, categoryPost);
//                postList.add(post);
//            }
//            rs.close();
//            ps.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return postList;
//    }
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
        xSql = "SELECT TOP 1 *\n"
                + "FROM Posts\n"
                + "ORDER BY Counts DESC;";
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
        xSql = "INSERT INTO Posts(Title, Content, BriefInfo, Thumbnail, Counts, AuthorID, ServiceID, CreatedDate, CategoryPost) \n"
                + "     VALUES (? ,? ,? ,? ,? ,? ,?,?)";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getContent());
            ps.setString(3, post.getBriefInfo());
            ps.setString(4, post.getThumbnail());
            ps.setInt(5, post.getCount());
            ps.setInt(6, post.getAuthorID());
            ps.setInt(7, post.getServiceID());
            ps.setDate(8, post.getCreatedDate());
            ps.setString(9, post.getCategoryPost());
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
                + "   SET Title=?,Content=?,BriefInfo=?,Thumbnail=?,AuthorID=?,ServiceID+? ,CreatedDate=?, CategoryPost=?"
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
            ps.setInt(9, postID);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            // Handle exception appropriately
            System.out.println(e);
        }
    }

    public int getPostCount() {
        xSql = "SELECT COUNT(*) AS TotalRecords FROM Posts";
        int total = 0;
        try {
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt("TotalRecords");
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    public int getCountOfPostsUserChoose(String keyword, String categoryPost) {
        int count = 0;
        String sql = "SELECT COUNT(*) AS count "
                + "FROM [dbo].[Posts]"
                + "WHERE Title LIKE ? ";

        if (!categoryPost.isEmpty()) {
            sql += "AND CategoryPost = ? ";
        }

        try {
            ps = con.prepareStatement(sql);
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

    public List<Post> getPostListByUser(int authourID) {
        xSql = "select * from Posts where p.AuthorID=?";
        List<Post> postList = new ArrayList<Post>();
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, authourID);
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
        } catch (Exception e) {
        }
        return postList;
    }

    public List<Post> postsSortByTitle() {
        List<Post> postList = new ArrayList<>();
        xSql = "SELECT *  FROM [dbo].[Posts] Order by Title";
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

    public List<Post> postsSortByCategory() {
        List<Post> postList = new ArrayList<>();
        xSql = "SELECT *  FROM [dbo].[Posts] Order by CategoryPost";
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

    public static void main(String[] args) {
        PostDAO postDAO= new PostDAO();
        
        int numOfPage = postDAO.getCountOfPostsUserChoose("", "") / 6;
        System.out.println(numOfPage);
    }
}
