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
import model.User;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author hbich
 */
public class ActivateAccount extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String data = request.getParameter("data");
            String decode_data = java.net.URLDecoder.decode(data, "UTF-8");
            String[] value = decode_data.split("&");
            UserDAO u = new UserDAO();
            User user = new User();
            user.setEmail(value[0]);
            user.setFirstName(value[1]);
            user.setLastName(value[2]);
            user.setPhoneNumber(value[3]);
            user.setPassword(value[4]);
            user.setAddress(value[5]);
            user.setGender(value[6]);
            u.insert(user);
            response.sendRedirect("index.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
