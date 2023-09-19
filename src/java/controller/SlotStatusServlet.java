/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import Database.ReservationDAO;
import Database.StaffScheduleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Reservation;

/**
 *
 * @author hbich
 */
public class SlotStatusServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String day = (String) request.getParameter("day");
        String selectedDate = (String) request.getParameter("selectedDate");
        String staffID = (String) request.getParameter("staffID");
        String serviceID = (String) request.getParameter("serviceID");

        StaffScheduleDAO ssd = new StaffScheduleDAO();
        List<Integer> workSlots = ssd.getWorkSlots(selectedDate, staffID);

        ReservationDAO rd = new ReservationDAO();
        for (Reservation r : rd.getSpecificReservation(staffID, day, serviceID)) {
            if (!r.getStatus().equals("done")) {
                workSlots.remove(Integer.valueOf(r.getReservationSlot()));
            }
        }

        StringBuilder sb = new StringBuilder();
        for (Integer slot : workSlots) {
            sb.append(slot).append(",");
        }
        String workSlotsText = sb.toString();
        // remove the last comma
        if (workSlotsText.endsWith(",")) {
            workSlotsText = workSlotsText.substring(0, workSlotsText.length() - 1);
        }

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(workSlotsText);
    }

}
