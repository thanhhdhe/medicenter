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
import model.User;
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
        String repeat_password = request.getParameter("rrepeat_password");
        String gender = request.getParameter("rgender");
        String address = request.getParameter("raddress");
        if (firstname.isEmpty()) {
            response.getWriter().write("You need to write your firstname!");
            return;
        }
        if (lastname.isEmpty()) {
            response.getWriter().write("You need to write your lastname!");
            return;
        }
        if (gender.isEmpty()) {
            response.getWriter().write("You need to write your gender!");
            return;
        }
        if (email.isEmpty()) {
            response.getWriter().write("You need to write your email!");
            return;
        }
        if (phonenumber.isEmpty()) {
            response.getWriter().write("You need to write your phonenumber!");
            return;
        }

        if (address.isEmpty()) {
            response.getWriter().write("You need to write your address!");
            return;
        }
        if (password.isEmpty()) {
            response.getWriter().write("You need to write your password!");
            return;
        }
        if (repeat_password.isEmpty()) {
            response.getWriter().write("You need to re-write your password!");
            return;
        }
        if (!password.matches(repeat_password)) {
            response.getWriter().write("Your re-enter password is not match with your password");
            return;
        }
        UserDAO u = new UserDAO();
        if (u.selectUser(email) != null) {
            response.getWriter().write("Your email has been used");
            return;
        }
        String newPword = DigestUtils.md5Hex(password);
        String myHash = DigestUtils.md5Hex(email);
        response.getWriter().write("Your email should receive email for verification");
        Mail.sendEmail(email, "Activate your account", "Your verification link : " + "http://localhost:9999/ChildenCare/ActivateAccount?key1="
                + email + "&key2=" + firstname + "&key3=" + lastname + "&key4=" + phonenumber + "&key5="
                + newPword + "&key6=" + myHash + "&key7=" + address + "&key8=" + gender);
    }
}
