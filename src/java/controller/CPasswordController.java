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

        // Get the current password, new password, and confirmation password from the request
        String currentPassword = request.getParameter("currentpassword");
        String newPassword = request.getParameter("newPassword");
        String conPassword = request.getParameter("conPassword");

        // Hash the current password for comparison with the stored password
        String oldpassword = DigestUtils.md5Hex(currentPassword);

        // Get the user's email from the session
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        // Create a UserDAO instance to interact with the user data
        UserDAO dao = new UserDAO();

        // Retrieve the user based on their email
        User user = dao.getUser(email);

        if (currentPassword == null || newPassword == null || conPassword == null
                || currentPassword.equals("")
                || newPassword.equals("") || conPassword.equals("")) {
            // Handle the error when one or more fields are missing in the form
            response.getWriter().write("Notify: You must enter complete information.");
        } else if(newPassword.length() < 6 || newPassword.length() > 100){
            // Handle the error when one or more fields are missing in the form
            response.getWriter().write("Notify: You must enter password more than 6 and less than 100 character.");
        }else {
            if (oldpassword.equals(user.getPassword())) {
                if (newPassword.equals(conPassword)) {
                    // Reset the password to the new hashed password
                    dao.resetPassword(DigestUtils.md5Hex(newPassword), user.getUserID());
                    response.getWriter().write("Notify: Success.");
                } else {
                    response.getWriter().write("Notify: Both passwords must be mismatched.");
                }
            } else {
                response.getWriter().write("Notify: The current password is incorrect.");
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
