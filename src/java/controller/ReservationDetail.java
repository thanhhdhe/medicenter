/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import Database.ServiceDAO;
import Database.StaffDAO;
import Database.StaffScheduleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import model.Service;
import model.Staff;

/**
 *
 * @author hbich
 */
public class ReservationDetail extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            // Receive serviceID and staffID 
            String serviceID = (String) request.getParameter("serviceID");
            String staffID = (String) request.getParameter("staffID");

            // Get the current date
            LocalDate currentDate = LocalDate.now();

            // Get the current month as an integer (1-12)
            int currentMonthValue = currentDate.getMonthValue();
            int currentYearValue = currentDate.getYear();

            StaffDAO sd = new StaffDAO();
            StaffScheduleDAO ssd = new StaffScheduleDAO();
            ServiceDAO serviceDAO = new ServiceDAO();

            // Check the existence of the staff and services
            if (sd.getStaffByStaffId(Integer.parseInt(staffID)) != null && serviceDAO.getServiceByID(serviceID) != null) {

                // Get the staff
                Staff staff = sd.getStaffByStaffId(Integer.parseInt(staffID));

                // Process services detail
                Service service = serviceDAO.getServiceByID(serviceID);
                request.setAttribute("service", service);
                request.setAttribute("Staff", staff);

                // Process staff schedule
                List<Integer> Workday = ssd.getWorkDay(staffID, Integer.toString(currentMonthValue), Integer.toString(currentYearValue)); // The variable will contain the number of workdays

                List<Integer> fullDay = ssd.getListDayFullSlot(staffID, Integer.toString(currentMonthValue), Integer.toString(currentYearValue)); // The variable will store a day that full

                request.setAttribute("Workday", Workday);
                request.setAttribute("fullDay", fullDay);

                request.getRequestDispatcher("/view/reservationdetail.jsp").forward(request, response);
            } else {
                response.sendRedirect("home");
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
