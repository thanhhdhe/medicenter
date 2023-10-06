/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import Database.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import model.User;

/**
 *
 * @author Admin
 */
@MultipartConfig()
public class UserController extends HttpServlet {

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
        UserDAO userdao = new UserDAO();
        String action = request.getParameter("action");
        List<User> userList = null;
        String url = null;

        try {
            if (action.equals("profile")) {
                request.getRequestDispatcher("./view/profile.jsp").forward(request, response);
            }

            if (action.equals("updateprofile")) {
                int userId = Integer.parseInt(request.getParameter("userId"));
                User u = userdao.getUserByID(userId);
                String lastname = request.getParameter("lastname_raw");
                String firstname = request.getParameter("firstname_raw");
                String phone = request.getParameter("phone_raw");
                String gender = request.getParameter("gender");
                String address = request.getParameter("address");
                Part filePart = request.getPart("images");

                // Kiểm tra loại của tệp tải lên
                String contentType = filePart.getContentType();
                if (contentType != null && contentType.startsWith("image")) {
                    String realPath = request.getServletContext().getRealPath("/resources/img");
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

                    String newImg = null;

                    if (!filePart.getSubmittedFileName().isEmpty()) {
                        filePart.write(realPath + "/" + fileName);
                        newImg = "resources/img/" + fileName;
                    } else {
                        newImg = u.getProfileImage();
                    }

                    System.out.println("Name is" + lastname + " " + firstname);
                    userdao.UpdateProfile(firstname, lastname, phone, gender, newImg, address, userId);
                    response.sendRedirect("home?showmodal=1&status=success");
                } else {
                    // Loại tệp tải lên không phải là hình ảnh
                    request.setAttribute("updateerror", "Uploaded file is not an image");
                    response.sendRedirect("home?showmodal=1&status=error");
                }
            }
            if (action.equals("all")) {
                url = "user?action=all";
                userList = userdao.getAllUsers();
            }
            if (action.equals("search")) {
                String text = request.getParameter("txt");
                text = text.replaceFirst("^0+(?!$)", "");
                url = "user?action=search&txt=" + text;
                userList = userdao.search(text);
            }
            if (action.equals("status")) {
                int id = Integer.parseInt(request.getParameter("userid"));
                User u = userdao.getUserByID(id);
                System.out.println(id);
                System.out.println(u.isStatus());
                boolean status = u.isStatus();
                try {
                    if (status) {
                        u.setStatus(false);
                        userdao.updateStatus(Boolean.FALSE, id);
                        System.out.println("change to false");
                    } else {
                        u.setStatus(true);
                        userdao.updateStatus(Boolean.TRUE, id);
                        System.out.println("change to true");
                    }
                    // Send back a JSON object with the new status
                    response.setContentType("application/json");
                    PrintWriter out = response.getWriter();
                    out.print("{\"success\": true, \"status\": " + u.isStatus() + "}");
                    out.flush();
                } catch (Exception e) {
                    // Send back a JSON object with an error message
                    response.setContentType("application/json");
                    PrintWriter out = response.getWriter();
                    out.print("{\"success\": false, \"error\": \"" + e.getMessage() + "\"}");
                    out.flush();
                }
            }

            if (userList != null) {
                int numPerPage = 15;
                if (request.getParameter("itemsPerPage") != null) {
                    numPerPage = Integer.parseInt(request.getParameter("itemsPerPage"));
                }
                int size = userList.size();
                int numPages = (size % numPerPage == 0) ? (size / numPerPage) : (size / numPerPage) + 1;

                // Get the current page from the request parameter
                int page = 1; // Default to page 1 if not specified
                String xpage = request.getParameter("page");
                if (xpage != null) {
                    page = Integer.parseInt(xpage);
                }

                int start = (page - 1) * numPerPage;
                int end = Math.min(page * numPerPage, size);

                List<User> user = userdao.getListByPage(userList, start, end);

                request.setAttribute("url", url);
                request.setAttribute("page", page);
                request.setAttribute("num", numPages);
                request.setAttribute("user", user);
                request.getRequestDispatcher("./view/user-list.jsp").forward(request, response);
            }
        } catch (IOException | ServletException e) {
            System.out.println(e);
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
