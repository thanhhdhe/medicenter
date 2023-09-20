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
import model.Mail;
import org.apache.commons.codec.digest.DigestUtils;

public class RegisterController extends HttpServlet {

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
        String email = request.getParameter("remail");
        String firstname = request.getParameter("rfirstname");
        String lastname = request.getParameter("rlastname");
        String phonenumber = request.getParameter("rphonenumber");
        String password = request.getParameter("rpassword");
        String gender = request.getParameter("rgender");
        String address = request.getParameter("raddress");
        UserDAO u = new UserDAO();
        if (u.selectUser(email) != null) {
            response.getWriter().write("email existed");
            return;
        }
        String newPword = DigestUtils.md5Hex(password);
        String data = email + "&" + firstname + "&" + lastname + "&" + phonenumber + "&"
                + newPword + "&" + address + "&" + gender;
        data = java.net.URLEncoder.encode(data, "UTF-8");
        response.getWriter().write("success");
        Mail.sendEmail(email, "Activate your account", "Your verification link : " + "http://localhost:9999/ChildrenCare/ActivateAccount?data=" + data);
    }
}
