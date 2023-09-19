/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import Database.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author pc
 */
public class CPasswordController extends HttpServlet {

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
//        try (PrintWriter out = response.getWriter()) {
//            //change password by User ID 
//            // Get session from login
//            HttpSession session= request.getSession();
//            String email = (String) session.getAttribute("email");
//            // change database pass word
//            // get new password 
//            String currentPassword = request.getParameter("currentpassword");
//            String newPassword = request.getParameter("newPassword");
//            String conPassword = request.getParameter("conPassword");
//            
//            UserDAO userdao= new UserDAO();
//            
//            //check if newPassword == confirm password so change pass, error
//            if(newPassword.equals(conPassword) && userdao.loginAccount(email, currentPassword)){
//                
//                userdao.resetPassword(newPassword, email);
//                
//                response.sendRedirect("index.jsp");
//            } else {
//                
//                request.getRequestDispatcher("index.jsp").forward(request, response);
//            }
//            
//        }
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
        processRequest(request, response);
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
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String currentPassword = request.getParameter("currentpassword");
        String newPassword = request.getParameter("newPassword");
        String conPassword = request.getParameter("conPassword");
        String oldpassword = DigestUtils.md5Hex(currentPassword);
        //lay ve mail khach hang gui
        HttpSession session = request.getSession();
        String email= (String) session.getAttribute("email");
        UserDAO dao= new UserDAO();
        User user= dao.getUser(email);
        if (currentPassword == null || newPassword == null || conPassword == null
                || currentPassword.equals("")
                || newPassword.equals("") || conPassword.equals("")) {
            // Xử lý lỗi khi một hoặc nhiều trường không tồn tại trong form

            response.getWriter().write("Notify: You must enter complete information.");
        } else {
            if (oldpassword.equals(user.getPassword())) {
                if (newPassword.equals(conPassword)) {
                    dao.resetPassword(DigestUtils.md5Hex(newPassword), user.getEmail());
                    response.getWriter().write("Notify: Success.");
                } else {
                    response.getWriter().write("Notify: Both passwords must be identical.");
                }
            } else {
                response.getWriter().write("Notify: The current password is ineffective.");
            }
        }
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
