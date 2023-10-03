/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import Database.PostDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Post;

/**
 *
 * @author quanh
 */
public class PostManageController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PostManageController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PostManageController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //get title
        String postTitle;
        try {
            postTitle = request.getParameter("postTitle");
            if (postTitle == null) {
                throw new Exception();
            }
        } catch (Exception e) {
            postTitle = "";
        }
        //get category
        String postCategory;
        try {
            postCategory = request.getParameter("postCategory");
            if (postCategory == null || postCategory.equals("Post Category")) {
                throw new Exception();
            }
        } catch (Exception e) {
            postCategory = "";
        }
        //get author
        String postAuthor;
        try {
            postAuthor = request.getParameter("postAuthor");
            if (postAuthor == null || postCategory.equals("Post Author")) {
                throw new Exception();
            }
        } catch (Exception e) {
            postAuthor = "";
        }
        //get page
        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }
        
        PostDAO postDao = new PostDAO();
        //get categorylist
        List<String> categoryList = postDao.allCategoryPost();
        categoryList.add(0, "Post Category");
        if (!postCategory.isEmpty()) {
            categoryList.remove(postCategory);
            categoryList.add(0, postCategory);
        }
        //get authorlist
        List<String> authorList= postDao.allAuthorPost();
        authorList.add(0, "Post Author");
        if (!postAuthor.isEmpty()) {
            authorList.remove(postAuthor);
            authorList.add(0, postAuthor);
        }
        //get number of page
        int numOfPage = numOfPage(postTitle, postCategory);
        //get list post
        List<Post> list = postDao.getSortedPagedPostsByUserChoice((page - 1) * 6, 6, postTitle, postCategory);
        
        
        request.setAttribute("postTitle", postTitle);
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("authorList", authorList);
        request.setAttribute("numOfPage", numOfPage);
        request.setAttribute("list", list);
        request.getRequestDispatcher("./view/post-list-manage.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String event = request.getParameter("event");
        switch (event) {
            case "hide":
                hideService(request, response);
                doGet(request, response);
                break;
            case "show":
                showService(request, response);
                doGet(request, response);
                break;
            case "update":
                request.getRequestDispatcher(event).forward(request, response);
                break;
            default:
                throw new AssertionError();
        }
    }

//    protected List<Post> getList(HttpServletRequest request, HttpServletResponse response,
//            String postTitle, String postCategory, int page)
//            throws ServletException, IOException {
//        PostDAO postDAO = new PostDAO();
//        if (postCategory == null || postCategory.isEmpty()) {
////            return postDAO.getPostedPagedPostsBySearch((page - 1) * 6, 6, postTitle, "manager");
//        } else {
//            return postDAO.getSortedPagedPostsByUserChoice((page - 1) * 6, 6, postTitle, postCategory, "manager");
//        }
//    }
    protected int numOfPage(String postTitle, String postCategory) {
        PostDAO postDAO = new PostDAO();
        int numOfPage = postDAO.getCountOfPostsUserChoose(postTitle, postCategory) / 6;
        if (postDAO.getCountOfPostsUserChoose(postTitle, postCategory) % 6 != 0) {
            numOfPage += 1;
        }
        return numOfPage;
    }

    protected void hideService(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PostDAO postDAO = new PostDAO();
        int ID = Integer.parseInt(request.getParameter("postId"));
        Post post = postDAO.getPostByID(ID);
        post.setStatusPost(false);
        postDAO.update(ID, post);
        PrintWriter out = response.getWriter();
        out.print(post.isStatusPost() + " " + post.getPostID());
        out.print(postDAO.getPostByID(ID).isStatusPost());
    }

    protected void showService(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PostDAO postDAO = new PostDAO();
        int ID = Integer.parseInt(request.getParameter("postId"));
        Post post = postDAO.getPostByID(ID);
        post.setStatusPost(true);
        postDAO.update(ID, post);

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
