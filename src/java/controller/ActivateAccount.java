package controller;

import Database.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import model.User;

public class ActivateAccount extends HttpServlet {

    protected static final Map<String, Long> activationLinks = RegisterController.activationLinks;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            String data = request.getParameter("data");
            String decode_data = java.net.URLDecoder.decode(data, "UTF-8");
            String[] value = decode_data.split("&");
            User user = new User();
            user.setEmail(value[0]);
            user.setFirstName(value[1]);
            user.setLastName(value[2]);
            user.setPhoneNumber(value[3]);
            user.setPassword(value[4]);
            user.setAddress(value[5]);
            user.setGender(value[6]);
            user.setRole("user");
            user.setProfileImage("resources/img/avatar.png");

            String token = value[7]; // Get the token from the data

            // Check if the token exists and is not expired
            if (activationLinks.containsKey(token)) {
                long timestamp = activationLinks.get(token);
                long currentTime = System.currentTimeMillis();
                long expirationTime = timestamp + (6 * 60 * 60 * 1000); // 6 hours in milliseconds

                // Check the Token is expired or not
                if (currentTime <= expirationTime) {
                    // Insert the User
                    UserDAO u = new UserDAO();
                    u.insert(user);

                    // Remove the tokens from list
                    activationLinks.remove(token);

                    request.setAttribute("email", value[0]);
                    request.setAttribute("verifyStatus", "You have successfully verified and you can login right now.");
                    request.getRequestDispatcher("view/verification-popup.jsp").include(request, response);
                } else {
                    // Token is expired
                    request.setAttribute("email", value[0]);
                    request.setAttribute("verifyStatus", "Activation link has expired.");
                    request.getRequestDispatcher("view/verification-popup.jsp").include(request, response);
                }
            } else {
                // Token is not found
                request.setAttribute("email", value[0]);
                request.setAttribute("verifyStatus", "Activation link is invalid.");
                request.getRequestDispatcher("view/verification-popup.jsp").include(request, response);
            }
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
