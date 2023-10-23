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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.Reservation;

/**
 *
 * @author hbich
 */
public class MyReservationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        String page = (String) request.getParameter("page");
        if (page == null) {
            page = "1";
        }
        String email = (String) session.getAttribute("email");
        if (email == null) {
            response.sendRedirect("home");
        } else {
            // Update the database to cancel the pending reservation exceeds 5 minutes
            ReservationDAO reservationDAO = new ReservationDAO();
            reservationDAO.updateDatabase();

            request.setAttribute("page", page);
            request.getRequestDispatcher("/view/myreservation.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ReservationDAO reservationDAO = new ReservationDAO();
        UserDAO userDAO = new UserDAO();
        HttpSession session = request.getSession(true);
        String email = (String) session.getAttribute("email");
        String action = (String) request.getParameter("action");

        // Update the database to cancel the pending reservation exceeds 5 minutes
        reservationDAO.updateDatabase();

        if (action == null) {
            String page = (String) request.getParameter("page");
            int numberPerPage = 5;
            int pageNumber = 1;
            if (page != null) {
                pageNumber = Integer.parseInt(page);
            }
            List<Reservation> reservations = new ArrayList<>();
            // Check if there is an condition for that pagination
            String condition = (String) request.getParameter("condition");
            String value = (String) request.getParameter("value");
            if (condition == null) {
                reservations = reservationDAO.getSortedPaged((pageNumber - 1) * numberPerPage, numberPerPage, Integer.toString(userDAO.getUser(email).getUserID()));
            } else {
                value = value.replaceAll("%20", " ");
                reservations = reservationDAO.getSortedSpecificPaged((pageNumber - 1) * numberPerPage, numberPerPage, Integer.toString(userDAO.getUser(email).getUserID()), condition, value);
            }

            StringBuilder stringBuilder = new StringBuilder();
            SimpleDateFormat reservationDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat createdDateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

            ServiceDAO serviceDAO = new ServiceDAO();
            StaffDAO staffDAO = new StaffDAO();

            for (Reservation reservation : reservations) {
                stringBuilder.append(reservation.getReservationID()).append(",");
                stringBuilder.append(createdDateFormat.format(reservation.getCreatedDate())).append(",");
                stringBuilder.append(reservationDateFormat.format(reservation.getReservationDate())).append(",");
                stringBuilder.append(reservation.getReservationSlot()).append(",");
                stringBuilder.append(serviceDAO.getServiceByID(Integer.toString(reservation.getServiceID())).getTitle()).append(",");
                stringBuilder.append(staffDAO.getStaffByStaffId(reservation.getStaffID()).getStaffName()).append(",");
                stringBuilder.append(reservation.getCost()).append(",");
                stringBuilder.append(reservation.getStatus());
                stringBuilder.append("\n");
            }
            out.println(stringBuilder.toString());
        } else if (action.equals("paginationNumber")) {
            // Get the number of pagination page
            String condition = (String) request.getParameter("condition");
            String value = (String) request.getParameter("value");
            value = value.replaceAll("%20", " ");
            out.println(reservationDAO.getTotalPaginationWithCondition(Integer.toString(userDAO.getUser(email).getUserID()), 5, condition, value));
        } else if (action.equals("cancel")) {
            int reservationID = Integer.parseInt(request.getParameter("invoiceId"));
            Reservation reservation = reservationDAO.getReservationByID(reservationID);

            if (!reservation.getStatus().equals("done")) {
                // Cancel reservation
                reservation.setStatus("cancel");
                reservationDAO.update(reservation);
                response.setContentType("application/json");
                response.getWriter().write("{\"success\": true}");
            }
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
