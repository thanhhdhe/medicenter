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
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import model.Post;
import model.Staff;
import model.User;

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
        PrintWriter out = response.getWriter();
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
            if (event == null || event.isEmpty()) {
                loadPageWithChoice(request, response);
                request.getRequestDispatcher("./view/post-list-manage.jsp").forward(request, response);
            } else {
                switch (event) {
                    case "hide":
                        hidePost(request, response);
                        loadPageWithChoice(request, response);
                        request.getRequestDispatcher("./view/post-list-manage.jsp").forward(request, response);
                        break;
                    case "show":
                        showPost(request, response);
                        loadPageWithChoice(request, response);
                        request.getRequestDispatcher("./view/post-list-manage.jsp").forward(request, response);
                        break;
                    default:
                        break;
                }
            }
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

    }

    protected void hidePost(HttpServletRequest request, HttpServletResponse response)
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

    protected void showPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PostDAO postDAO = new PostDAO();
        int ID = Integer.parseInt(request.getParameter("postId"));
        Post post = postDAO.getPostByID(ID);
        post.setStatusPost(true);
        postDAO.update(ID, post);

    }

    protected void loadPageWithChoice(HttpServletRequest request, HttpServletResponse response) {
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
        String postAuthorID;
        try {
            postAuthorID = request.getParameter("postAuthor");
            if (postAuthorID == null || postAuthorID.equals("-1")) {
                throw new Exception();
            }
        } catch (Exception e) {
            postAuthorID = "";
        }
        //get post status
        String postStatus;
        try {
            postStatus = request.getParameter("postStatus");
            if (postStatus == null || postStatus.equals("postStatus")) {
                throw new Exception();
            }
        } catch (Exception e) {
            postStatus = "";
        }
        //get page
        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }
        //get sortBy
        String sortBy;
        try {
            sortBy = request.getParameter("sortBy");
            if (sortBy == null) {
                throw new Exception();
            }
        } catch (Exception e) {
            sortBy = "Title";
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
        List<Integer> authorIDList = postDao.allAuthorID();
        List<User> authorList = new ArrayList<>();

        // Get the current date and time
        java.util.Date utilDate = new java.util.Date();
        // Convert the java.util.Date to java.sql.Date
        Date sqlDate = new Date(utilDate.getTime());

        User notuser = new User(-1, "", "", "", "Author", "Name", "", "", "", sqlDate);
        authorList.add(notuser);
        for (Integer ID : authorIDList) {
            UserDAO userDao = new UserDAO();
            User user = userDao.getUserByID(ID);
            if (!postAuthorID.isEmpty() && ID == Integer.parseInt(postAuthorID)) {
                authorList.add(0, user);
            } else {
                authorList.add(user);
            }
        }
        //get sortlist
        List<String> sortList = new ArrayList<>();

        sortList.add("Title");
        sortList.add("CategoryPost");
        sortList.add("AuthorID");
        sortList.add("StatusPost");
        sortList.remove(sortBy);
        sortList.add(0, sortBy);

        //get number of page
        int numOfPage = (postDao.getCountOfPostsManagerChoose(postTitle, postCategory, postAuthorID, postStatus) + 5) / 6;
        //get list post  
        List<Post> list = postDao.getSortedPagedPostsByManagerChoice((page - 1) * 6, 6, postTitle, postCategory, postAuthorID, postStatus, sortBy);

        request.setAttribute("postTitle", postTitle);
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("authorList", authorList);
        request.setAttribute("sortList", sortList);
        request.setAttribute("numOfPage", numOfPage);
        request.setAttribute("list", list);
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
