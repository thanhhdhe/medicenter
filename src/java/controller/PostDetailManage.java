/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import Database.PostDAO;
import Database.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import model.Post;
import model.Staff;

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
        HttpSession session = request.getSession(true);
        String email = (String) session.getAttribute("email");
        StaffDAO staffDAO = new StaffDAO();
        Staff curStaff = staffDAO.getStaffByStaffEmail(email);

        boolean isManager = false;
        if (curStaff != null) {
            if (curStaff.getRole().equals("manager")) {
                isManager = true;
            }
        }
        if (!isManager) {
            request.getRequestDispatcher("./view/403-forbidden.jsp").forward(request, response);
        } else {
            String event = request.getParameter("event");
            request.setAttribute("event", event);

            PostDAO postDAO = new PostDAO();
            int ID;
            Post post = new Post();
            switch (event) {
                case "update":
                    ID = Integer.parseInt(request.getParameter("postID"));
                    post = postDAO.getPostByID(ID);
                    break;
                case "add":
                    ID = postDAO.getLastPostID() + 1;
                    post = new Post(ID, "", "", "", "", 0, /*curStaff.getStaffID() */ 1, 1, Date.valueOf(LocalDate.now()), "", true);
                    break;
                default:
                    throw new AssertionError();
            }
            loadPostDetail(request, response, post, event);
            request.getRequestDispatcher("/view/post-detail-manage.jsp").forward(request, response);
        }
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
        PostDAO postDAO = new PostDAO();
        int ID;
        Post post = new Post();
        switch (event) {
            case "update":
                ID = Integer.parseInt(request.getParameter("postID"));
                post = postDAO.getPostByID(ID);
                post = newPost(request, response, post, event);
                loadPostDetail(request, response, post, event);
                request.getRequestDispatcher("/view/post-detail-manage.jsp").forward(request, response);
                break;
            case "add":
                ID = postDAO.getLastPostID() + 1;
                post = new Post(ID, "", "", "", "", 0, /*curStaff.getStaffID() */ 1, 1, Date.valueOf(LocalDate.now()), "", true);
                post = newPost(request, response, post, event);
                loadPostDetail(request, response, post, event);
                request.getRequestDispatcher("/view/post-detail-manage.jsp").forward(request, response);
                break;
            default:
                throw new AssertionError();
        }

    }

    protected void loadPostDetail(HttpServletRequest request, HttpServletResponse response, Post post, String event) {
        PostDAO postDAO = new PostDAO();
//        Post post = postDAO.getPostByID(ID);
        List<String> categoryList = postDAO.allCategoryPost();
        categoryList.remove(post.getCategoryPost());
        categoryList.add(0, post.getCategoryPost());
        request.setAttribute("author", postDAO.getNameByUserID(post.getAuthorID()));
        request.setAttribute("avatar", postDAO.getAvatarByUserID(post.getAuthorID()));
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("post", post);
        request.setAttribute("event", event);
    }

    protected Post newPost(HttpServletRequest request, HttpServletResponse response, Post post, String event) {

        PostDAO postDAO = new PostDAO();

        String title = request.getParameter("Title");
        String content = request.getParameter("Content");
        String briefInfo = request.getParameter("Brief");
        String thumbnail = request.getParameter("Thumbnail");
        Date createdDate = Date.valueOf(LocalDate.now());
        String categoryPost = request.getParameter("postCategory");
        String status = request.getParameter("status");

        boolean validate = true;
        if (content.isEmpty()) {
            String errorMessage3 = "content can't be empty";
            request.setAttribute("errorMessage3", errorMessage3);
            validate = false;
        }
        if (title.isEmpty()) {
            String errorMessage1 = "title can't be empty";
            request.setAttribute("errorMessage1", errorMessage1);
            validate = false;

        }
        if (briefInfo.isEmpty()) {
            String errorMessage2 = "brief can't be empty";
            request.setAttribute("errorMessage2", errorMessage2);
            validate = false;

        }

        if (validate) {
            post.setTitle(title);
            post.setContent(content);
            post.setBriefInfo(briefInfo);
            post.setThumbnail(thumbnail);
            post.setCreatedDate(createdDate);
            post.setCategoryPost(categoryPost);
            if (status.equals("true")) {
                post.setStatusPost(true);
            }
            if (status.equals("false")) {
                post.setStatusPost(false);
            }
            switch (event) {
                case "add":
                    postDAO.insert(post);
                    break;
                case "update":
                    postDAO.update(post.getPostID(), post);
                    break;
                default:
                    throw new AssertionError();
            }
        }
        return post;
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
