/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import Database.FeedBackDAO;
import Database.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

/**
 *
 * @author pc
 */
public class FeedBackController extends HttpServlet {
   
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
        PrintWriter out = response.getWriter();
        // lay du lieu tu jsp
        String ratestar = request.getParameter("rating");
        int rate= Integer.parseInt(ratestar);
        String content = request.getParameter("content");
        
        //insert vao database
        // lay ve UserID dang dang nhap
        HttpSession session = request.getSession();
        String email= (String) session.getAttribute("email");
        UserDAO userdao = new UserDAO();
        User user= userdao.getUser(email);
        
        // lay medical ID tra ve qua url
        
        FeedBackDAO dao = new FeedBackDAO();
        dao.InsertFeedBack(user.getUserID(), 2, content, rate);
        out.println("<!DOCTYPE html>\n"
                        + "<html>\n"
                        + "<head>\n"
                        + "    <meta charset=\"UTF-8\">\n"
                        + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                        + "    <style>\n"
                        + "        body {\n"
                        + "            background-color: #f2f2f2;\n"
                        + "            font-family: Arial, sans-serif;\n"
                        + "            display: flex;\n"
                        + "            justify-content: center;\n"
                        + "            align-items: center;\n"
                        + "            height: 100vh;\n"
                        + "            margin: 0;\n"
                        + "        }\n"
                        + "        \n"
                        + "        .container {\n"
                        + "            text-align: center;\n"
                        + "            background-color: #ffffff;\n"
                        + "            border-radius: 10px;\n"
                        + "            padding: 20px;\n"
                        + "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n"
                        + "        }\n"
                        + "        \n"
                        + "        h1 {\n"
                        + "            font-size: 36px;\n"
                        + "            color: #333;\n"
                        + "        }\n"
                        + "        \n"
                        + "        p {\n"
                        + "            font-size: 18px;\n"
                        + "            color: #666;\n"
                        + "        }\n"
                        + "    </style>\n"
                        + "</head>\n"
                        + "<body>\n"
                        + "    <div class=\"container\">\n"
                        + "        <h1>Thank you</h1>\n"
                        + "        <p>FeedBack have been sent.</p>\n"
                        + "    </div>\n"
                        + "</body>\n"
                        + "</html>");
           
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
