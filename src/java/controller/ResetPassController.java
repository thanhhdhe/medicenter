/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Random;
import model.Mail;

/**
 *
 * @author pc
 */
public class ResetPassController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // get parameter send by server
        String email= request.getParameter("Email");
        String newpassword= request.getParameter("newPassword");
        String conpassword= request.getParameter("conPassword");
        /*
          use token random 
          set url pattern as resetpassword?token= tokenurl
          get tokenurl
          compare 
            if token random = token url
                redirect user to resetPassword
            else
                error
        */
        String token = request.getParameter("token");
        Random ra = new Random();
        int token1 = ra.nextInt(100);
        Mail.sendEmail(email, System.currentTimeMillis() + "", "http://localhost:9999/ChildrenCare/resetpassword?token=" + token1);
        if (token == null) {
            response.sendRedirect("HomePage.jsp");
        } else {
            try {
                int tok = Integer.parseInt(token);

                if (tok <= 100) {
                    response.sendRedirect("resetPassword.jsp");
                } else {
                    response.sendRedirect("HomePage.jsp");
                }
            } catch (Exception e) {
            }
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
