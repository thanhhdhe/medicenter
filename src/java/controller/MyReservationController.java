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
            request.setAttribute("page", page);
            request.getRequestDispatcher("/view/myreservation.jsp").forward(request, response);;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ReservationDAO rdao = new ReservationDAO();
        UserDAO udao = new UserDAO();
        HttpSession session = request.getSession(true);
        String email = (String) session.getAttribute("email");

        String page = (String) request.getParameter("page");
        int numberPerPage = 5;
        int pageNumber = 1;
        if (page != null) {
            pageNumber = Integer.parseInt(page);
        }

        List<Reservation> reservations = rdao.getSortedPaged((pageNumber - 1) * numberPerPage, numberPerPage, Integer.toString(udao.getUser(email).getUserID()));

        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");        
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

        ServiceDAO serviceDAO = new ServiceDAO();
        StaffDAO staffDAO = new StaffDAO();
        
        for (Reservation reservation : reservations) {
            sb.append(reservation.getReservationID()).append(",");
            sb.append(sdf2.format(reservation.getCreatedDate())).append(",");
            sb.append(sdf1.format(reservation.getReservationDate())).append(",");
            sb.append(reservation.getReservationSlot()).append(",");
            sb.append(serviceDAO.getServiceByID(Integer.toString(reservation.getServiceID())).getTitle()).append(",");
            sb.append(staffDAO.getStaffByStaffId(reservation.getStaffID()).getStaffName()).append(",");
            sb.append(reservation.getCost()).append(",");
            sb.append(reservation.getStatus());
            sb.append("\n");
        }
        out.println(sb.toString());
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
