package controller;

import Database.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Map;
import model.User;

public class ActivateAccount extends HttpServlet {

    protected static final Map<String, Long> activationLinks = RegisterController.activationLinks;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            String data = request.getParameter("data");
            String email = new String(Base64.getDecoder().decode(data));

            // Check if the token exists and is not expired
            if (activationLinks.containsKey(data)) {
                long timestamp = activationLinks.get(data);
                long currentTime = System.currentTimeMillis();
                long expirationTime = timestamp + (6 * 60 * 60 * 1000); // 6 hours in milliseconds

                // Check the Token is expired or not
                if (currentTime <= expirationTime) {
                    // Set the status of user is active
                    UserDAO userDAO = new UserDAO();
                    User user = userDAO.getUser(email);
                    userDAO.updateStatus(true, user.getUserID());

                    // Remove the tokens from list
                    activationLinks.remove(data);

                    request.setAttribute("email", email);
                    request.setAttribute("verifyStatus", "You have successfully verified and you can login right now.");
                    request.getRequestDispatcher("view/verification-popup.jsp").include(request, response);
                } else {
                    // Token is expired
                    request.setAttribute("email", email);
                    request.setAttribute("verifyStatus", "Activation link has expired.");
                    request.getRequestDispatcher("view/verification-popup.jsp").include(request, response);
                }
            } else {
                // Token is not found
                request.setAttribute("email", email);
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
