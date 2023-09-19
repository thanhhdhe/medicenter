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
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author hbich
 */
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String email = (String) request.getParameter("lemail");
        String password = (String) request.getParameter("lpassword");
        UserDAO u = new UserDAO();
        if (email.isEmpty()) {
            response.getWriter().write("You need to write your email!");
            return;
        }
        if (password.isEmpty()) {
            response.getWriter().write("You need to write your password!");
            return;
        }
        if (u.loginAccount(email, DigestUtils.md5Hex(password))) {
            HttpSession session = request.getSession(true);
            session.setAttribute("email", email);
            response.getWriter().write("Login successful!");
        } else {
            response.getWriter().write("Wrong email or password");
        }
    }
}
