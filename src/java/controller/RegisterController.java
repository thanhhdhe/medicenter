package controller;

import Database.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.Base64;
import model.Mail;
import org.apache.commons.codec.digest.DigestUtils;
import java.util.HashMap;
import java.util.Map;
import model.User;

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
        UserDAO userDAO = new UserDAO();

        if (userDAO.getUser(email) != null) {
            response.getWriter().write("email existed");
            return;
        }
        // Get the current date and time
        java.util.Date utilDate = new java.util.Date();
        
        // Convert the java.util.Date to java.sql.Date
        Date sqlDate = new Date(utilDate.getTime());
        
        // Insert user into database
        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setPhoneNumber(phonenumber);
        user.setPassword(DigestUtils.md5Hex(password));
        user.setGender(gender);
        user.setAddress(address);
        user.setStatus(false);
        user.setProfileImage("resources/img/avatar.png");
        user.setCreatedDate(sqlDate);
        userDAO.insert(user);

        // Encoding the email
        byte[] encodedBytes = Base64.getEncoder().encode(email.getBytes());
        String encodedMessage = new String(encodedBytes);
        // Make sure that in hash map activationLinks not having the same email
        if (activationLinks.containsKey(encodedMessage)) {
            activationLinks.replace(encodedMessage, System.currentTimeMillis());
        } else {
            activationLinks.put(encodedMessage, System.currentTimeMillis());
        }
        response.getWriter().write("success");
        String message = "Dear " + firstname + " " + lastname + ",<br>"
                + "We are delighted to welcome you to Medilab. To ensure the security of your account and to provide you with the best possible experience, we kindly request your cooperation in verifying your email address.<br>"
                + "Please click on the link below to complete the verification process: http://localhost:9999/ChildrenCare/ActivateAccount?data=" + encodedMessage + "<br>"
                + "Verification Link Expiry: 6 hours<br>"
                + "Please note that this link is valid for a limited time, so we encourage you to complete the verification process at your earliest convenience.<br>"
                + "If you did not register for an account with Medilab, or if you believe this email was sent in error, please disregard it. Your account will not be activated until you click the verification link above.<br>"
                + "Thank you for choosing Medilab. We look forward to serving you, and if you have any questions or need assistance, please do not hesitate to contact our support team at 0373933128.<br>"
                + "Sincerely,<br>"
                + "Medilab";

        Mail.sendEmail(email, "Activate your account", message);
    }
}
