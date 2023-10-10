/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import Database.ChildrenDAO;
import Database.ServiceDAO;
import Database.StaffDAO;
import Database.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import model.Children;
import model.Service;
import model.Staff;
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
        HttpSession session = request.getSession();
        User users = (User) session.getAttribute("user");
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
            if (action.equals("filter")) {
                String status = request.getParameter("status");
                request.setAttribute("status", status);
                if (status.equals("all")) {
                    response.sendRedirect("user?action=all");
                } else {
                    url = "user?action=filter&status=" + status;
                    userList = userdao.getFilterByStatus(status);
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
                request.setAttribute("itemsPerPage", numPerPage);
                request.setAttribute("url", url);
                request.setAttribute("page", page);
                request.setAttribute("num", numPages);
                request.setAttribute("user", user);
                request.getRequestDispatcher("./view/user-list.jsp").forward(request, response);
            }
            if (action.equals("my-children")) {
                int userID = users.getUserID();
                String serviceID = request.getParameter("serviceID");
                ServiceDAO serviceDAO = new ServiceDAO();
                Service service = serviceDAO.getServiceByID(serviceID);
                String staffIDstr = request.getParameter("staffID");
                int staffID = Integer.parseInt(staffIDstr);
                StaffDAO staffDAO = new StaffDAO();
                Staff staff = staffDAO.getStaffByStaffId(staffID);
                request.setAttribute("staff", staff);
                request.setAttribute("service", service);
                ChildrenDAO cDao = new ChildrenDAO();
                List<Children> childList = cDao.getListChildrenByUserId(userID + "");
                request.setAttribute("child", childList);
                request.getRequestDispatcher("./view/choose-children.jsp").forward(request, response);
            }
            if (action.equals("add-child")) {
                ChildrenDAO childDAO = new ChildrenDAO();
                String fullName = request.getParameter("fullname");
                String year = request.getParameter("year");
                String month = request.getParameter("month");
                String day = request.getParameter("day");
                String email = request.getParameter("email");
                String address = request.getParameter("address");
                String gender = request.getParameter("gender");
                String phoneNumber = request.getParameter("phoneNumber");
                String dfImage = "https://cdn-icons-png.flaticon.com/512/3177/3177440.png";
                Part filePart = request.getPart("images");

                Date sqlDOB = null;
                try {
                    String date = day + "-" + month + "-" + year;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    java.util.Date utilDate = dateFormat.parse(date);
                    sqlDOB = new Date(utilDate.getTime());
                } catch (Exception e) {

                }

                String contentType = filePart.getContentType();
                if (contentType != null && contentType.startsWith("image")) {
                    String realPath = request.getServletContext().getRealPath("/resources/img");
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    filePart.write(realPath + "/" + fileName);
                    String newImg = "resources/img/" + fileName;
                    System.out.println("anh la " + newImg);
                    Children newChild = new Children(users, fullName, sqlDOB, gender, newImg);
                    childDAO.createChildren(newChild);
                    session.setAttribute("message", "Thông báo: Thêm trẻ em thành công!");
                } else {
                    Children newChild = new Children(users, fullName, sqlDOB, gender, dfImage);
                    childDAO.createChildren(newChild);
                    session.setAttribute("message", "Thông báo: Thêm trẻ em thành công!");
                }
                response.sendRedirect("user?action=my-children");
//                request.getRequestDispatcher().forward(request, response);

            }
            if (action.equals("delete-child")) {
                String childID = request.getParameter("childID");
                System.out.println(childID);
                ChildrenDAO childDAO = new ChildrenDAO();
                childDAO.deleteChild(childID);
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
