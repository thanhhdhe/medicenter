/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import Database.FeedBackDAO;
import Database.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Mail;
import model.MedicalExamination;
import model.User;

/**
 *
 * @author pc
 */
public class FeedBackController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        // Get the 'action' parameter from the request
        String action = request.getParameter("action");
        // Determine the servlet action based on the 'action' parameter
        if (action.equals("accessfeedback")) {
            // Check if the user is logged in
            HttpSession session = request.getSession();
            String email = (String) session.getAttribute("email");
             // Display a login required message as a popup overlay
            if (email == null) {
                out.println("<html><head><title>Login Required</title>");
                out.println("<style>");
                out.println("  .overlay {");
                out.println("    position: fixed;");
                out.println("    top: 0;");
                out.println("    left: 0;");
                out.println("    width: 100%;");
                out.println("    height: 100%;");
                out.println("    background-color: rgb(124 177 167 / 50%);");
                out.println("    display: flex;");
                out.println("    justify-content: center;");
                out.println("    align-items: center;");
                out.println("    z-index: 1;");
                out.println("  }");
                out.println("  .popup {");
                out.println("    background-color: white;border-radius: 5px;");
                out.println("    padding: 20px;");
                out.println("    text-align: center;width: 300px;height: 150px;");
                out.println("    z-index: 2;");
                out.println("  }");
                out.println("</style>");
                out.println("</head><body>");
                out.println("<div class='overlay'>");
                out.println("  <div class='popup'>");
                out.println("    <h2 style=\"color: red\">Login Required</h2>");
                out.println("    <p>You must log in to access this page.</p>");
                out.println("    <button style=\"padding: 10px;"
                        + "background: #0089ff;"
                        + "color: white;"
                        + "border: 0px;"
                        + "border-radius: 5px;\" onclick='closePopup()'>Cancel</button>");
                out.println("  </div>");
                out.println("</div>");
                out.println("<script>");
                out.println("  function closePopup() {");
                out.println("    var overlay = document.querySelector('.overlay');");
                out.println("    overlay.style.display = 'none';");
                out.println("    window.location.href = 'index.jsp';");
                out.println("  }");
                out.println("</script>");
                out.println("</body></html>");
            } else {
                // Get user information
                UserDAO userdao = new UserDAO();
                User user = userdao.getUser(email);
                System.out.println(user.getEmail());
                 // Get medical examinations awaiting feedback
                System.out.println(user.getUserID());
                FeedBackDAO dao = new FeedBackDAO();
                // Set the 'medical' attribute for rendering in FeedBack.jsp
                List<MedicalExamination> medical = dao.getMedicalExamination(user.getUserID());
                request.setAttribute("medical", medical);
                request.getRequestDispatcher("/view/FeedBack.jsp").forward(request, response);
                
            }
            
        } else if (action.equals("sendfeedback")) {
              // Get user information
            HttpSession session = request.getSession();
            String email = (String) session.getAttribute("email");
            UserDAO userdao = new UserDAO();
            User user = userdao.getUser(email);
            System.out.println(user.getEmail());
            // Get feedback details from the request
            System.out.println(user.getUserID());
            FeedBackDAO dao = new FeedBackDAO();
            String ratestar = request.getParameter("rate");
            int rate = Integer.parseInt(ratestar);
            
            String content = request.getParameter("content");
            String medicalID = request.getParameter("medical");
            
            int medicalId = Integer.parseInt(medicalID);
            // Insert the feedback into the database
            dao.InsertFeedBack(user.getUserID(), medicalId, content, rate);
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
                    + "    <div class=\"container\">\n"
                    + "        <h1>Thank you</h1>\n"
                    + "        <p>FeedBack have been sent.</p>\n"
                    + " <button style=\" background-color: blue; border: 0px; border-radius: 5px;padding: 10px;\"> "
                    + " <a style=\"color: white; padding: 10px;text-decoration: none;\" href=\"http://localhost:9999/ChildrenCare/\">HOME</a> </button>"
                    + "    </div>\n"
                    + "</body>\n"
                    + "</html>");
        } else {
            // Get user's email from the session
            HttpSession session = request.getSession();
            String email = (String) session.getAttribute("email");
              // Send an email with a feedback request
            Mail.sendEmail(email, "THANK TO USE SERVICE", "Thank you for using our service\n"
                    + "Please give us feedback about the service by clicking on feedback in the header on the homepage on the website");
            request.getRequestDispatcher("/view/FeedBack.jsp").forward(request, response);

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
