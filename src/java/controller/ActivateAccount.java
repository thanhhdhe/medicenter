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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String email = request.getParameter("key1");
        String firstname = request.getParameter("key2");
        String lastname = request.getParameter("key3");
        String phonenumber = request.getParameter("key4");
        String password = request.getParameter("key5");
        String hashCode = request.getParameter("key6");
        String address = request.getParameter("key7");
        String gender = request.getParameter("key8");
        if (hashCode.equals(DigestUtils.md5Hex(email))) {
            UserDAO u = new UserDAO();
            User user = new User();
            user.setEmail(email);
            user.setFirstName(firstname);
            user.setLastName(lastname);
            user.setPhoneNumber(phonenumber);
            user.setPassword(password);
            user.setAddress(address);
            user.setGender(gender);
            u.insert(user);
        }
        response.sendRedirect("index.jsp");
    }
}
