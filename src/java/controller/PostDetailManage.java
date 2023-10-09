/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import Database.PostDAO;
import Database.StaffDAO;
import Database.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import model.Post;
import model.Staff;
import model.User;

/**
 *
 * @author quanh
 */
public class PostDetailManage extends HttpServlet {

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
            out.println("<title>Servlet postDetailManage</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet postDetailManage at cai dcm " + request.getContextPath() + "</h1>");
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
        PostDAO postDAO = new PostDAO();
        int ID = Integer.parseInt(request.getParameter("postId"));
        Post post = postDAO.getPostByID(ID);
        List<String> categoryList = postDAO.allCategoryPost();
        categoryList.remove(post.getCategoryPost());
        categoryList.add(0, post.getCategoryPost());
        request.setAttribute("author", postDAO.getNameByUserID(post.getAuthorID()));
        request.setAttribute("avatar", postDAO.getAvatarByUserID(post.getAuthorID()));
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("post", post);
        PrintWriter out = response.getWriter();
        request.getRequestDispatcher("/view/post-detail-manage.jsp").forward(request, response);
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
        PrintWriter out = response.getWriter();
        PostDAO postDAO = new PostDAO();
        int id = Integer.parseInt(request.getParameter("postID"));
        Post post = postDAO.getPostByID(id);
        String title = request.getParameter("Title");
        String content = request.getParameter("Content");
        String briefInfo = request.getParameter("Brief");
        String thumbnail = request.getParameter("Thumbnail");
        Date createdDate = Date.valueOf(LocalDate.now());
        String categoryPost = request.getParameter("postCategory");
        String status = request.getParameter("status");
        if (title.isEmpty()) {
            String errorMessage1 = "title can't be empty";
        } else {
            
        }
        if (content.isEmpty()) {
            String errorMessage2 = "content can't be empty";
        } else {

        }
        if (briefInfo.isEmpty()) {
            String errorMessage3 = "brief can't be empty";
        } else {

        }

//        if (thumbnail.isEmpty()) {
//            String errorMessage = "can't file pic";
//        } else {
//
//        }
        post.setTitle(title);
        post.setContent(content);
        post.setBriefInfo(briefInfo);
        post.setThumbnail(thumbnail);
        post.setCreatedDate(createdDate);
        post.setCategoryPost(categoryPost);

        if (status.equals("true")) {
            post.setStatusPost(true);
        }
        if (status.equals(false)) {
            post.setStatusPost(false);
        }
        postDAO.update(id, post);
        request.getRequestDispatcher("/postManage?event=").forward(request, response);

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
