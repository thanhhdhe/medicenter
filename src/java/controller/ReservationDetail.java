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
import java.util.ArrayList;
import java.util.List;
import model.Service;
import model.Staff;
import model.StaffSchedule;

/**
 *
 * @author hbich
 */
public class ReservationDetail extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            String serviceID = (String) request.getParameter("serviceID");
            String staffID = (String) request.getParameter("staffID");
            StaffDAO sd = new StaffDAO();
            StaffScheduleDAO ssd = new StaffScheduleDAO();
            if (sd.getStaffByStaffId(Integer.parseInt(staffID)) != null) {
                Staff staff = sd.getStaffByStaffId(Integer.parseInt(staffID));
                List<StaffSchedule> staffScheduleList = ssd.getStaffSchedulesByStaffID(staffID);
                // Xu ly services detail
                ServiceDAO quanlyService = new ServiceDAO();
                Service service = quanlyService.getServiceByID(serviceID);
                request.setAttribute("service", service);
                // Xu ly staff schedule
                List<Integer> WorkDay = new ArrayList<>();
                for (StaffSchedule ss : staffScheduleList) {
                    WorkDay.add(ss.getDayOfWeek());
                }
                request.setAttribute("WorkDay", WorkDay);
                request.setAttribute("staffID", staffID);
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
