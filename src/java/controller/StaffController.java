/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import Database.ReservationDAO;
import Database.ServiceDAO;
import Database.StaffDAO;
import Database.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Reservation;
import model.Staff;

/**
 *
 * @author Admin
 */
public class StaffController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
        String event = request.getParameter("event");
        HttpSession session = request.getSession(true);
        String email = (String) session.getAttribute("email");
        StaffDAO staffDAO = new StaffDAO();
        Staff curStaff = staffDAO.getStaffByStaffEmail(email);
        boolean isManager = false;
        boolean isStaff = false;
        boolean isAdmin = false;
        String adminEmail = (String) session.getAttribute("adminEmail") + "";
        if (adminEmail.contains("@")) {
            isAdmin = true;
        }
        if (curStaff != null) {
            if (curStaff.getRole().equals("manager")) {
                isManager = true;
            }
            if (curStaff.getRole().equals("doctor") || curStaff.getRole().equals("nurse")) {
                isStaff = true;
            }
        }

        switch (event) {
            case "sent-to-home":
                request.getRequestDispatcher("./view/staff-dashboard.jsp").forward(request, response);
                break;
            case "sent-to-login":
                request.getRequestDispatcher("./view/login-staff.jsp").forward(request, response);
                break;
            case "send-to-medical-examination":
                if (!isManager && !isStaff) {
                    request.getRequestDispatcher("./view/403-forbidden.jsp").forward(request, response);
                    break;
                }
                request.getRequestDispatcher("./view/medical-examination.jsp").forward(request, response);
                break;
            case "send-to-medical-examination-manage":
                if (!isManager) {
                    request.getRequestDispatcher("./view/403-forbidden.jsp").forward(request, response);
                    break;
                }
                request.getRequestDispatcher("./view/medical-examination-manage.jsp").forward(request, response);
                break;
            case "send-to-edit":
                if (!isManager && !isStaff) {
                    request.getRequestDispatcher("./view/403-forbidden.jsp").forward(request, response);
                    break;
                }
                String id = request.getParameter("id");
                request.setAttribute(id, id);
                request.getRequestDispatcher("./view/edit-medical-examination.jsp").forward(request, response);
                break;
            case "send-to-feedback":
                request.getRequestDispatcher("feedback").forward(request, response);
                break;
            case "send-to-children-list":
                if (!isManager && !isStaff) {
                    request.getRequestDispatcher("./view/403-forbidden.jsp").forward(request, response);
                    break;
                }
                request.getRequestDispatcher("./view/my-patient-list.jsp").forward(request, response);
                break;
            case "send-to-history-examination":
                if (!isManager && !isStaff) {
                    request.getRequestDispatcher("./view/403-forbidden.jsp").forward(request, response);
                    break;
                }

                String childId = request.getParameter("childid");
                String reserdIdst = request.getParameter("reserdid") + "";
                if (!reserdIdst.equals("null")) {
                    request.setAttribute("reserdid", reserdIdst);
                }
                request.setAttribute("childId", childId);
                request.getRequestDispatcher("./view/add-medical-examination.jsp").forward(request, response);
                break;
            case "send-to-reservations-list":
                if (!isManager && !isStaff) {
                    request.getRequestDispatcher("./view/403-forbidden.jsp").forward(request, response);
                    break;
                }
                request.getRequestDispatcher("./view/reservation-of-staff.jsp").forward(request, response);
                break;
            case "send-to-schedules": {
                if (!isManager && !isStaff) {
                    request.getRequestDispatcher("./view/403-forbidden.jsp").forward(request, response);
                    break;
                }
                request.getRequestDispatcher("./view/schedules-of-staff.jsp").forward(request, response);
                break;
            }
            case "add-schedule": {
                if (!isManager && !isStaff) {
                    request.getRequestDispatcher("./view/403-forbidden.jsp").forward(request, response);
                    break;
                }
                request.setAttribute("staffSchedule", null);
                request.setAttribute("action", "add");
                request.getRequestDispatcher("./view/edit-schedule.jsp").forward(request, response);
                break;
            }
            case "edit-schedule": {
                if (!isManager && !isStaff) {
                    request.getRequestDispatcher("./view/403-forbidden.jsp").forward(request, response);
                    break;
                }
                String scheduleID = (String) request.getParameter("scheduleID");
                request.setAttribute("scheduleID", scheduleID);
                request.setAttribute("action", "edit");
                request.getRequestDispatcher("./view/edit-schedule.jsp").forward(request, response);
                break;
            }
            case "send-to-reservation-detail":
                if (!isManager && !isStaff) {
                    request.getRequestDispatcher("./view/403-forbidden.jsp").forward(request, response);
                    break;
                }
                String reserdId = request.getParameter("reserdid");
                request.setAttribute("reserdid", reserdId);
                request.getRequestDispatcher("./view/reservationdetail-of-staff.jsp").forward(request, response);
                break;
            case "send-to-reservation-manager-detail":
                if (!isManager) {
                    request.getRequestDispatcher("./view/403-forbidden.jsp").forward(request, response);
                    break;
                }
                String reserdIdM = request.getParameter("reserdid");
                request.setAttribute("reserdid", reserdIdM);
                request.getRequestDispatcher("./view/reservationdetail-of-manager.jsp").forward(request, response);
                break;
            case "reservation-of-staff":
                if (!isManager && !isStaff) {
                    request.getRequestDispatcher("./view/403-forbidden.jsp").forward(request, response);
                    break;
                }
                renderReservationOfStaff(request, response);
                break;
            case "changerole":
                if (!isAdmin) {
                    request.getRequestDispatcher("./view/403-forbidden.jsp").forward(request, response);
                    break;
                }
                changeRole(request, response);
                break;

        }
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
        String event = request.getParameter("event");
        switch (event) {
            case "login":
                login(request, response);
                break;

        }
    }

    protected void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = (String) request.getParameter("email");
        String password = (String) request.getParameter("pass");
        StaffDAO staffDAO = new StaffDAO();
        Staff staff = staffDAO.getStaffByStaffEmail(email);
        if (staff == null) {
            request.setAttribute("err", "Incorrect username or password!");
            request.getRequestDispatcher("./view/login-staff.jsp").forward(request, response);
        } else if (!staff.getPassword().equals(password)) {
            request.setAttribute("err", "Incorrect username or password!");
            request.getRequestDispatcher("./view/login-staff.jsp").forward(request, response);
        } else if (staff.getRole().equals("admin")) {
            HttpSession session = request.getSession(true);
            session.setAttribute("adminEmail", email);
            response.sendRedirect("admin");
        } else {
            HttpSession session = request.getSession(true);
            session.setAttribute("email", email);
            session.setAttribute("staff", staff);
            request.getRequestDispatcher("./view/staff-dashboard.jsp").forward(request, response);
        }
    }

    protected void changeRole(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = (String) request.getParameter("id");
        String role = (String) request.getParameter("role");
//        Logger logger = Logger.getLogger(StaffController.class.getName());
//        logger.log(Level.INFO, "vão ò :" +id +"  "+ role);
        HttpSession session = request.getSession(true);
        StaffDAO staffDAO = new StaffDAO();
        String adminEmail = (String) session.getAttribute("adminEmail");
        Staff staff = staffDAO.getStaffByStaffId(Integer.parseInt(id.trim()));
        staff.setRole(role);
        staffDAO.updateStaff(staff);
        request.setAttribute("staff", staff);
        request.setAttribute("admin", staffDAO.getStaffByStaffEmail(adminEmail));
        request.getRequestDispatcher("./view/staff-detail-admin.jsp").forward(request, response);
    }

    protected void renderReservationOfStaff(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String reservationId = (String) request.getParameter("reservationId");
        String customerName = (String) request.getParameter("customerName");
        String fromDate = (String) request.getParameter("fromDate");
        String toDate = (String) request.getParameter("toDate");
        String sortBy = (String) request.getParameter("sortBy");
        String status = (String) request.getParameter("status");
        String page = (String) request.getParameter("page");
        int pagination = Integer.parseInt(page.trim());
        ReservationDAO reservationDAO = new ReservationDAO();
        HttpSession session = request.getSession(true);
        String email = (String) session.getAttribute("email");
        StaffDAO staffDAO = new StaffDAO();
        UserDAO userDAO = new UserDAO();
        ServiceDAO serviceDAO = new ServiceDAO();
        Staff curStaff = staffDAO.getStaffByStaffEmail(email);
        int numberOfPage = (reservationDAO.countReservationsByStaffID(curStaff.getStaffID() + "") + 9) / 10;
        // Generate the pagination HTML
        String paginationHtml = "";
        if (reservationDAO.countFilteredReservationsOfStaff(curStaff.getStaffID() + "", status, reservationId, customerName, fromDate, toDate) <= 40) {
            if (reservationDAO.countFilteredReservationsOfStaff(curStaff.getStaffID() + "", status, reservationId, customerName, fromDate, toDate) > 0) {
                for (int i = 1; i <= numberOfPage; i++) {
                    if (i == pagination) {
                        paginationHtml += "<li class=\"pagination-btn active\"><span>" + pagination + "</span></li>";
                    } else {
                        paginationHtml += "<li class=\"pagination-btn inactive\"><a data-page=\"" + i + "\" onclick=\"loadReservation(event," + i + ")\" href=\"#\">" + i + "</a></li>";
                    }
                }
            }
        } else {

            if (pagination == 1) {
                paginationHtml += "<li class=\"pagination-btn active\"><span>1</span></li>"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\" data-page=\"2\" onclick=\"loadReservation(event, 2)\">2</a></li>\n"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\" data-page=\"3\" onclick=\"loadReservation(event, 3)\">3</a></li>\n"
                        + "<span>...</span>\n"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\" onclick=\"loadReservation(event, " + numberOfPage + ")\" data-page=\"" + numberOfPage + "\">" + numberOfPage + "</a></li>\n"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\" onclick=\"loadReservation(event, " + (pagination + 1) + ")\">&gt;</a></li>";
            } else if (pagination > numberOfPage - 4) {
                paginationHtml += "<li class=\"pagination-btn inactive\" ><a href=\"#\" onclick=\"loadReservation(event, " + (pagination - 1) + ")\">&lt;</a></li>"
                        + "<span>...</span>\n";
                for (int i = numberOfPage - 3; i <= numberOfPage; i++) {
                    if (i == pagination) {
                        paginationHtml += "<li class=\"pagination-btn active\"><span>" + pagination + "</span></li>";
                    } else {
                        paginationHtml += "<li class=\"pagination-btn inactive\"><a onclick=\"loadReservation(event, " + i + ")\" data-page=\"" + i + "\" href=\"#\">" + i + "</a></li>";
                    }
                }
                paginationHtml += "<li class=\"pagination-btn inactive\"><a href=\"#\" onclick=\"loadReservation(event, " + (pagination + 1) + ")\">&gt;</a></li>";
            } else {
                int pagination1 = pagination + 1, pagination2 = pagination + 2;
                paginationHtml += "<li class=\"pagination-btn inactive\"><a href=\"#\" onclick=\"loadReservation(event, " + (pagination - 1) + ")\">&lt;</a></li>"
                        + "<li class=\"pagination-btn active\"><span>" + pagination + "</span></li>"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\" onclick=\"loadReservation(event, " + pagination1 + ")\" data-page=\"" + (pagination + 1) + "\">" + (int) (pagination + 1) + "</a></li>\n"
                        + "<li class=\"pagination-btn inactive\"><a onclick=\"loadReservation(event, " + pagination2 + ")\" href=\"#\" data-page=\"" + (pagination + 2) + "\">" + (int) (pagination + 2) + "</a></li>\n"
                        + "<span>...</span>\n"
                        + "<li class=\"pagination-btn inactive\"><a onclick=\"loadReservation(event, " + numberOfPage + ")\" href=\"#\" data-page=\"" + numberOfPage + "\">" + numberOfPage + "</a></li>\n"
                        + "<li class=\"pagination-btn inactive\"><a onclick=\"loadReservation(event, " + numberOfPage + ")\" href=\"#\" onclick=\"loadReservation(event, " + (pagination + 1) + ")\">&gt;</a></li>";
            }

        }
        // Add the pagination HTML to the response header
        response.addHeader("pagination", paginationHtml);

        List<Reservation> reservations = reservationDAO.getFilteredReservationsOfStaff(curStaff.getStaffID() + "", status, reservationId, customerName, fromDate, toDate, sortBy, Integer.parseInt(page.trim()));
        for (Reservation reservation : reservations) {
            out.print("<tr>\n"
                    + "    <th scope=\"row\"><a href=\"staff?event=send-to-reservation-detail&reserdid=" + reservation.getReservationID() + "\" class=\"text-decoration-none text-dark\">" + reservation.getReservationID() + "</a></th>\n"
                    + "    <td>" + reservation.getReservationDate() + "</td>\n"
                    + "    <td>\n"
                    + "        <div class=\"d-flex align-items-center\">\n"
                    + "            <img class=\"rounded-circle object-cover me-3\" src=\"" + userDAO.getUserByID(reservation.getUserID()).getProfileImage() + "\" alt=\"alt\" width=\"30px\" height=\"30px\"/>\n"
                    + "            <div>" + userDAO.getUserByID(reservation.getUserID()).getFirstName() + "</div>\n"
                    + "        </div>\n"
                    + "    </td>\n"
                    + "    <td>" + serviceDAO.getServiceByID(reservation.getServiceID() + "").getTitle() + "</td>\n"
                    + "    <td>" + reservation.getCost() + "</td>\n"
                    + "    <td>");
            if (reservation.getStatus().equals("done")) {
                out.print("<p class=\"bg-success rounded-2 text-white m-0 p-1 px-2\" style=\"width: fit-content;\">" + reservation.getStatus() + "</p>");
            } else if (reservation.getStatus().equals("cancel")) {
                out.print("<p class=\"bg-danger rounded-2 text-white m-0 p-1 px-2\" style=\"width: fit-content;\">" + reservation.getStatus() + "</p>");
            } else {
                out.print("<p class=\"bg-primary rounded-2 text-white m-0 p-1 px-2\" style=\"width: fit-content;\">" + reservation.getStatus() + "</p>");
            }

            out.print("</td>\n"
                    + "    <td><a href=\"staff?event=send-to-reservation-detail&reserdid=" + reservation.getReservationID() + "\"><img src=\"resources/img/icon/detail.png\" alt=\"alt\" width=\"25px\"/></a></td>\n"
                    + "</tr>");
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
