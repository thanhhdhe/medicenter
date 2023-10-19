/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import Database.ChildrenDAO;
import Database.ReservationDAO;
import Database.ServiceDAO;
import Database.StaffDAO;
import Database.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import model.Children;
import model.Reservation;
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
        Staff curStaff = (Staff) session.getAttribute("staff");
        boolean isManager = false;
        boolean isAdmin = false;
        if (curStaff != null) {
            if (curStaff.getRole().equals("manager")) {
                isManager = true;
            }
            if (curStaff.getRole().equals("admin")) {
                isAdmin = true;
            }
        }
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
                    request.setAttribute("updateerror", "Uploaded file is not an image");
                    response.sendRedirect("home?showmodal=1&status=error");
                }

            }
            if (action.equals("all")) {
                if (isManager) {
                    url = "user?action=all";
                    userList = userdao.getAllUsers();
                } else {
                    request.getRequestDispatcher("./view/403-forbidden.jsp").forward(request, response);
                }
            }
            if (action.equals("search")) {
                if (isManager) {
                    String text = request.getParameter("txt");
                    text = text.replaceFirst("^0+(?!$)", "");
                    url = "user?action=search&txt=" + text;
                    userList = userdao.search(text);
                } else {
                    request.getRequestDispatcher("./view/403-forbidden.jsp").forward(request, response);
                }
            }
            if (action.equals("filter")) {
                if (isManager) {
                    String status = request.getParameter("status");
                    request.setAttribute("status", status);
                    if (status.equals("all")) {
                        response.sendRedirect("user?action=all");
                    } else {
                        url = "user?action=filter&status=" + status;
                        userList = userdao.getFilterByStatus(status);
                    }
                } else {
                    request.getRequestDispatcher("./view/403-forbidden.jsp").forward(request, response);
                }
            }
            if (action.equals("details")) {
                if (isManager) {
                    String userIDstr = request.getParameter("userID");
                    int userID = Integer.parseInt(userIDstr);
                    User userDetail = userdao.getUserByID(userID);
                    request.setAttribute("user", userDetail);
                    ChildrenDAO childrenDAO = new ChildrenDAO();
                    List<Children> childrenList = childrenDAO.getListChildrenByUserId(userIDstr);
                    request.setAttribute("children", childrenList);
                    request.getRequestDispatcher("./view/user-details.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("./view/403-forbidden.jsp").forward(request, response);
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
                session.setAttribute("numPerPage", numPerPage);
                if (request.getParameter("txt") != null) {
                    request.setAttribute("text", request.getParameter("txt"));
                }
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
                Staff staff = null;  // Initialize to null
                if (staffIDstr != null) {
                    int staffID = Integer.parseInt(staffIDstr);
                    StaffDAO staffDAO = new StaffDAO();
                    staff = staffDAO.getStaffByStaffId(staffID);
                }
                request.setAttribute("service", service);
                ChildrenDAO cDao = new ChildrenDAO();
                List<Children> childList = cDao.getListChildrenByUserId(userID + "");
                request.setAttribute("child", childList);
                // Set the "staff" attribute if staff is not null
                if (staff != null) {
                    request.setAttribute("staff", staff);
                }
                request.getRequestDispatcher("./view/choose-children.jsp").forward(request, response);
            }

            if (action.equals("add-child")) {
                ChildrenDAO childDAO = new ChildrenDAO();
                String fullName = request.getParameter("fullname");
                String year = request.getParameter("year");
                String month = request.getParameter("month");
                String day = request.getParameter("day");
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
                    users.setAddress(address);
                    users.setPhoneNumber(phoneNumber);
                    userdao.updateProfile(users);
                    session.setAttribute("message", "Add children profile successfully");
                } else {
                    Children newChild = new Children(users, fullName, sqlDOB, gender, dfImage);
                    childDAO.createChildren(newChild);
                    session.setAttribute("message", "Add children profile successfully");
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
            if (action.equals("render-user-by-admin")) {
                renderUserByAdmin(request, response);
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
    
    protected void renderUserByAdmin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String nameSearch = (String) request.getParameter("name");
        String emailSearch = (String) request.getParameter("email");
        String mobileSearch = (String) request.getParameter("mobile");
        String role = (String) request.getParameter("role");
        String gender = (String) request.getParameter("gender");
        String status = (String) request.getParameter("status");
        int statusUser = 2;
        if(!status.isEmpty()){
            statusUser = Integer.parseInt(status.trim());
        }
        String sortBy = (String) request.getParameter("sortBy");
        String page = (String) request.getParameter("page");
        int pagination = Integer.parseInt(page.trim());
        HttpSession session = request.getSession(true);
        String email = (String) session.getAttribute("email");
        StaffDAO staffDAO = new StaffDAO();
        UserDAO userDAO = new UserDAO();
        
        // Generate the pagination HTML
        String paginationHtml = "";
        int totalRecord = userDAO.countTotalUserByAdmin(nameSearch, emailSearch, mobileSearch, gender, role, statusUser );
        int numberOfPage = (totalRecord + 9) / 10;
        if (totalRecord <= 40) {
            if (totalRecord > 0) {
                for (int i = 1; i <= numberOfPage; i++) {
                    if (i == pagination) {
                        paginationHtml += "<li class=\"pagination-btn active\"><span>" + pagination + "</span></li>";
                    } else {
                        paginationHtml += "<li class=\"pagination-btn inactive\"><a data-page=\"" + i + "\" href=\"#\" onclick=\"pagination(event, " + i + ");\">" + i + "</a></li>";
                    }
                }
            }
        } else {

            if (pagination == 1) {
                paginationHtml += "<li class=\"pagination-btn active\"><span>1</span></li>"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\" data-page=\"2\" onclick=\"pagination(event, 2);\">2</a></li>\n"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\" data-page=\"3\" onclick=\"pagination(event, 3);\">3</a></li>\n"
                        + "<span>...</span>\n"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\" onclick=\"pagination(event, " + numberOfPage + ");\" data-page=\"" + numberOfPage + "\">" + numberOfPage + "</a></li>\n"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\">&gt;</a></li>";
            } else if (pagination > numberOfPage - 4) {
                paginationHtml += "<li class=\"pagination-btn inactive\"><a href=\"#\">&lt;</a></li>"
                        + "<span>...</span>\n";
                for (int i = numberOfPage - 3; i <= numberOfPage; i++) {
                    if (i == pagination) {
                        paginationHtml += "<li class=\"pagination-btn active\"><span>" + pagination + "</span></li>";
                    } else {
                        paginationHtml += "<li class=\"pagination-btn inactive\"><a data-page=\"" + i + "\" href=\"#\" onclick=\"pagination(event, " + i + ");\">" + i + "</a></li>";
                    }
                }
                paginationHtml += "<li class=\"pagination-btn inactive\"><a href=\"#\">&gt;</a></li>";
            } else {
                paginationHtml += "<li class=\"pagination-btn inactive\"><a href=\"#\">&lt;</a></li>"
                        + "<li class=\"pagination-btn active\"><span>" + pagination + "</span></li>"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\" data-page=\"" + pagination + "\" onclick=\"pagination(event, " + pagination + ");\">" + pagination + "</a></li>\n"
                        + "                                    <li class=\"pagination-btn inactive\"><a href=\"#\" data-page=\"" + pagination + "\" onclick=\"pagination(event, " + pagination + ");\">" + pagination + "</a></li>\n"
                        + "<span>...</span>\n"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\" data-page=\"" + numberOfPage + "\" onclick=\"pagination(event, " + numberOfPage + ");\">" + numberOfPage + "</a></li>\n"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\">&gt;</a></li>";
            }

        }
        // Add the pagination HTML to the response header
        response.addHeader("pagination", paginationHtml);

        List<User> users = userDAO.getAllUsersByAdmin(pagination, 10, sortBy, nameSearch, emailSearch, mobileSearch, gender, role, statusUser);
        for (User user : users) {
            out.print("<tr>\n" +
"                           <td>"+user.getUserID()+"</td>\n" +
"                           <td>"+user.getFirstName()+"</td>\n" +
"                           <td>"+user.getGender()+"</td>\n" +
"                           <td>"+user.getEmail()+"</td>\n" +
"                           <td>"+user.getPhoneNumber()+"</td>\n" +
"                           <td>"+user.getRole()+"</td>\n" +
"                           <td>");if(user.isStatus()){out.print("Active");}else{out.print("Inactive");}out.print("</td>\n" +
"                           <td><a href=\"#\"><img src=\"resources/img/icon/detail.png\" alt=\"alt\" width=\"25px\"/></a></td>\n" +
"                       </tr>");
        }
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
