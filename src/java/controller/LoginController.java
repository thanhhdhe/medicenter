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
        UserDAO userDAO = new UserDAO();
        HttpSession session = request.getSession(true);
        String check = (String) session.getAttribute("email");
        if (check != null) {
            response.getWriter().write("reload");
        } else if (userDAO.loginAccount(email, DigestUtils.md5Hex(password))) {
            User user = userDAO.getUser(email);
            if (user.isStatus() == true) {
                session.setAttribute("email", email);
                User users = userDAO.getUser(email);
                session.setAttribute("user", users);
                response.getWriter().write("success");
            } else {
                response.getWriter().write("inactive");
            }
        } else {
            response.getWriter().write("wronginformation");
        }
    }
}
