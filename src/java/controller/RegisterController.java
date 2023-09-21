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
import java.util.UUID;
import java.util.HashMap;
import java.util.Map;

public class RegisterController extends HttpServlet {

    protected static final Map<String, Long> activationLinks = new HashMap<>();

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
        
        // Generate a unique token
        String token = UUID.randomUUID().toString();
        activationLinks.put(token, System.currentTimeMillis());
        
        String newPword = DigestUtils.md5Hex(password);
        String data = email + "&" + firstname + "&" + lastname + "&" + phonenumber + "&"
                + newPword + "&" + address + "&" + gender + "&" + token;
        data = java.net.URLEncoder.encode(data, "UTF-8");
        response.getWriter().write("success");
        
        String message = "Dear " + firstname + " " + lastname + ",<br>"
                + "We are delighted to welcome you to Medilab. To ensure the security of your account and to provide you with the best possible experience, we kindly request your cooperation in verifying your email address.<br>"
                + "Please click on the link below to complete the verification process: http://localhost:9999/ChildrenCare/ActivateAccount?data=" + data + "<br>"
                + "Verification Link Expiry: [Expiration Date and Time]<br>"
                + "Please note that this link is valid for a limited time, so we encourage you to complete the verification process at your earliest convenience.<br>"
                + "If you did not register for an account with Medilab, or if you believe this email was sent in error, please disregard it. Your account will not be activated until you click the verification link above.<br>"
                + "Thank you for choosing Medilab. We look forward to serving you, and if you have any questions or need assistance, please do not hesitate to contact our support team at 0373933128.<br>"
                + "Sincerely,<br>"
                + "Medilab";
        
        Mail.sendEmail(email, "Activate your account", message);
    }
}
