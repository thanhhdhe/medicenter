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
        if (curStaff != null) {
            if (curStaff.getRole().equals("manager")) {
                isManager = true;
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
                request.getRequestDispatcher("./view/medical-examination.jsp").forward(request, response);
                break;
            case "send-to-edit":
                String id = request.getParameter("id");
                request.setAttribute(id, id);
                request.getRequestDispatcher("./view/edit-medical-examination.jsp").forward(request, response);
                break;
            case "send-to-feedback":
                request.getRequestDispatcher("feedback").forward(request, response);
                break;
            case "send-to-children-list":
                request.getRequestDispatcher("./view/my-patient-list.jsp").forward(request, response);
                break;
            case "send-to-history-examination":
                String childId = request.getParameter("childid");
                request.setAttribute("childId", childId);
                request.getRequestDispatcher("./view/add-medical-examination.jsp").forward(request, response);
                break;
            case "send-to-reservations-list":
                request.getRequestDispatcher("./view/reservation-of-staff.jsp").forward(request, response);
                break;
            case "send-to-reservation-detail":
                String reserdId = request.getParameter("reserdid");
                request.setAttribute("reserdid", reserdId);
                request.getRequestDispatcher("./view/reservationdetail-of-staff.jsp").forward(request, response);
                break;
            case "reservation-of-staff":
                renderReservationOfStaff(request, response);
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
        } else {
            HttpSession session = request.getSession(true);
            session.setAttribute("email", email);
            request.getRequestDispatcher("./view/staff-dashboard.jsp").forward(request, response);
        }
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
                        paginationHtml += "<li class=\"pagination-btn inactive\"><a data-page=\"" + i + "\" href=\"#\">" + i + "</a></li>";
                    }
                }
            }
        } else {

            if (pagination == 1) {
                paginationHtml += "<li class=\"pagination-btn active\"><span>1</span></li>"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\" data-page=\"2\">2</a></li>\n"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\" data-page=\"3\">3</a></li>\n"
                        + "<span>...</span>\n"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\" data-page=\"" + numberOfPage + "\">" + numberOfPage + "</a></li>\n"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\">&gt;</a></li>";
            } else if (pagination > numberOfPage - 4) {
                paginationHtml += "<li class=\"pagination-btn inactive\"><a href=\"#\">&lt;</a></li>"
                        + "<span>...</span>\n";
                for (int i = numberOfPage - 3; i <= numberOfPage; i++) {
                    if (i == pagination) {
                        paginationHtml += "<li class=\"pagination-btn active\"><span>" + pagination + "</span></li>";
                    } else {
                        paginationHtml += "<li class=\"pagination-btn inactive\"><a data-page=\"" + i + "\" href=\"#\">" + i + "</a></li>";
                    }
                }
                paginationHtml += "<li class=\"pagination-btn inactive\"><a href=\"#\">&gt;</a></li>";
            } else {
                paginationHtml += "<li class=\"pagination-btn inactive\"><a href=\"#\">&lt;</a></li>"
                        + "<li class=\"pagination-btn active\"><span>" + pagination + "</span></li>"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\" data-page=\"" + pagination + "\">" + pagination + "</a></li>\n"
                        + "                                    <li class=\"pagination-btn inactive\"><a href=\"#\" data-page=\"" + pagination + "\">" + pagination + "</a></li>\n"
                        + "<span>...</span>\n"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\" data-page=\"" + numberOfPage + "\">" + numberOfPage + "</a></li>\n"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\">&gt;</a></li>";
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
                    + "    <td>" + reservation.getStatus() + "</td>\n"
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
