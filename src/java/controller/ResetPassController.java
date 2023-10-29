/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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
import java.util.Random;
import model.MaHoa;
import model.Mail;
import model.User;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * Servlet to handle password reset functionality.
 *
 * @author pc
 */
public class ResetPassController extends HttpServlet {

    private int counter = 0;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private Random ra = new Random();
    
    private String generatePhu1() {
        
        int phu1 = ra.nextInt(100);
        // Encrypt phu1 for security purposes
        return MaHoa.toSHA1(String.valueOf(phu1));
    }
    

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        // Get the action parameter to determine the action to perform
        String action = request.getParameter("action");
        // Perform different actions based on the 'action' parameter
        if (action.equals("forgetpassword")) {
            // Generate phu1 using the extracted function
            String strphu1 = generatePhu1();

            // Send 'strphu1' to the reset.jsp page
            request.setAttribute("phu1", strphu1);
            request.getRequestDispatcher("/view/SendMail.jsp").forward(request, response);
        } else if (action.equals("confirmpassword")) {
            // Get 'phu2' and 'email' parameters
            String phu2 = request.getParameter("phu2");
            String email = request.getParameter("Mail");

            // Encrypt 'phu2' and create an OTP for security
            String OTP = MaHoa.toSHA1(phu2 + "thoigian");
            // Check if the provided email is valid
            UserDAO dao = new UserDAO();
            User user = dao.getUser(email);

            if (user != null && email.equals(user.getEmail())) {
                // Reset the counter
                counter = 0;
                // Send a verification email with a link
                Mail.sendEmail(email, "Verification Mail" + "",
                        "http://localhost:9999/ChildrenCare/resetpassword?phu3=" + phu2
                        + "&ID=" + user.getUserID()
                        + "&phoneNumber=" + user.getPhoneNumber()
                        + "&OTP=" + OTP + "&action=resetpass"
                        + " <br> Do not publicize this email for account safety.");
                out.println("<!DOCTYPE html>\n"
                        + "<html>\n"
                        + "<head>\n"
                        + "    <meta charset=\"UTF-8\">\n"
                        + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                        + "    <style>\n"
                        + "        body {\n"
                        + "            background-color: #f2f2f2;\n"
                        + "            font-family: Arial, sans-serif;\n"
                        + "            display: flex;\n"
                        + "            justify-content: center;\n"
                        + "            align-items: center;\n"
                        + "            height: 100vh;\n"
                        + "            margin: 0;\n"
                        + "        }\n"
                        + "        \n"
                        + "        .container {\n"
                        + "            text-align: center;\n"
                        + "            background-color: #ffffff;\n"
                        + "            border-radius: 10px;\n"
                        + "            padding: 20px;\n"
                        + "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n"
                        + "        }\n"
                        + "        \n"
                        + "        h1 {\n"
                        + "            font-size: 36px;\n"
                        + "            color: #333;\n"
                        + "        }\n"
                        + "        \n"
                        + "        p {\n"
                        + "            font-size: 18px;\n"
                        + "            color: #666;\n"
                        + "        }\n"
                        + "    </style>\n"
                        + "</head>\n"
                        + "<body>\n"
                        + "    <div style=\"width: 300px; height: 200px;\" class=\"container\">\n"
                        + "        <h1>Success</h1>\n"
                        + "        <p>Mail has been sent.</p>\n"
                        + " <button style=\" background-color: blue; border: 0px; border-radius: 5px;padding: 10px;\"> "
                        + " <a style=\"color: white; padding: 10px;text-decoration: none;\" href=\"http://localhost:9999/ChildrenCare/home\">HOME</a> </button>"
                        + "    </div>\n"
                        + "</body>\n"
                        + "</html>");
            } else {
                // Generate phu1 using the extracted function
                String strphu1 = generatePhu1();

                // Check if the provided email is invalid
                request.setAttribute("phu1", strphu1);
                request.setAttribute("notify", "You must enter the correct email address.");
                request.getRequestDispatcher("/view/SendMail.jsp").forward(request, response);
            }
        } else if (action.equals("resetpass")) {
            // Global counter to keep track of the number of times the servlet is called
            counter++;
            System.out.println(counter);
            if (counter == 1) {
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(1000);
                session.setAttribute("thoigian", "thoigian");
                // Get 'phu3' and 'OTP' parameters
                String strphu3 = MaHoa.toSHA1(request.getParameter("phu3") + "thoigian");
                String OTP = request.getParameter("OTP");
                System.out.println(strphu3);
                System.out.println(OTP);

                String thoigian = (String) session.getAttribute("thoigian");

                if (!OTP.equals(strphu3) || thoigian == null) {
                    // Generate a new random variable 'phu1' for security purposes
                    Random ra = new Random();
                    int phu1 = ra.nextInt(100);
                    // Encrypt 'phu1' and convert it to a string
                    String strphu1 = MaHoa.toSHA1(String.valueOf(phu1));

                    // Send 'strphu1' to the reset.jsp page
                    request.setAttribute("phu1", strphu1);
                    request.setAttribute("notify", "Link has expired.");
                    request.getRequestDispatcher("/view/SendMail.jsp").forward(request, response);
                } else {
                    // Invalidate the session if successful
                    session.invalidate();
                    String ID = request.getParameter("ID");
                    request.setAttribute("ID", ID);
                    String phoneNumber = request.getParameter("phoneNumber");
                    request.setAttribute("phoneNumber", phoneNumber);
                    // Go to the resetPassword.jsp page
                    request.getRequestDispatcher("/view/resetPassword.jsp").forward(request, response);
                }
            } else {
                // Counter > 1 means the link is no longer valid
                // Generate phu1 using the extracted function
                String strphu1 = generatePhu1();

                // Send 'strphu1' to the reset.jsp page
                request.setAttribute("phu1", strphu1);
                request.setAttribute("notify", "Link has been used.");
                request.getRequestDispatcher("/view/SendMail.jsp").forward(request, response);
            }
        } else if (action.equals("change")) {
            // Get 'newpassword' and 'confirmpassword' parameters
            String newpassword = request.getParameter("newPassword");
            String confirmpassword = request.getParameter("conPassword");
            // Get 'ID' and 'phoneNumber' parameters
            String ID = request.getParameter("ID");
            int id = Integer.parseInt(ID);
            String phoneNumber = request.getParameter("phoneNumber");
            UserDAO dao = new UserDAO();

            if (newpassword.equals(confirmpassword) && newpassword.length() >=6 && newpassword.length() <= 100) {
                // Reset the password by ID and phone number
                dao.resetPasswordByID(DigestUtils.md5Hex(newpassword), phoneNumber, id);
                out.println("<!DOCTYPE html>\n"
                        + "<html>\n"
                        + "<head>\n"
                        + "    <meta charset=\"UTF-8\">\n"
                        + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                        + "    <style>\n"
                        + "        body {\n"
                        + "            background-color: #f2f2f2;\n"
                        + "            font-family: Arial, sans-serif;\n"
                        + "            display: flex;\n"
                        + "            justify-content: center;\n"
                        + "            align-items: center;\n"
                        + "            height: 100vh;\n"
                        + "            margin: 0;\n"
                        + "        }\n"
                        + "        \n"
                        + "        .container {\n"
                        + "            text-align: center;\n"
                        + "            background-color: #ffffff;\n"
                        + "            border-radius: 10px;\n"
                        + "            padding: 20px;\n"
                        + "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n"
                        + "        }\n"
                        + "        \n"
                        + "        h1 {\n"
                        + "            font-size: 36px;\n"
                        + "            color: #333;\n"
                        + "        }\n"
                        + "        \n"
                        + "        p {\n"
                        + "            font-size: 18px;\n"
                        + "            color: #666;\n"
                        + "        }\n"
                        + "    </style>\n"
                        + "</head>\n"
                        + "<body>\n"
                        + "    <div style=\"width: 300px; height: 200px;\" class=\"container\">\n"
                        + "        <h1>Success</h1>\n"
                        + "        <p>Password has been reset.</p>\n"
                        + " <button style=\" background-color: blue; border: 0px; border-radius: 5px;padding: 10px;\"> "
                        + " <a style=\"color: white; padding: 10px;text-decoration: none;\" href=\"http://localhost:9999/ChildrenCare/home\">HOME</a> </button>"
                        + "    </div>\n"
                        + "</body>\n"
                        + "</html>");
            } else {
                request.setAttribute("ID", ID);
                request.setAttribute("phoneNumber", phoneNumber);
                String notify ;
                //validate password
                if(newpassword.length() <6 || newpassword.length() > 100){
                    notify = "You must enter password more than 6 and less than 100 character.";
                } else {
                    notify = "Passwords do not match.";
                }
                request.setAttribute("notify", notify);
                request.getRequestDispatcher("/view/resetPassword.jsp").forward(request, response);
            }
        } else {
            // Counter > 1 means the link is no longer valid
            // Generate phu1 using the extracted function
            String strphu1 = generatePhu1();

            // Send 'strphu1' to the reset.jsp page
            request.setAttribute("phu1", strphu1);
            request.setAttribute("notify", "Link is invalid.");
            request.getRequestDispatcher("/view/SendMail.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
