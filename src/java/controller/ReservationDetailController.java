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
import java.util.ArrayList;
import java.util.List;
import model.Reservation;

/**
 *
 * @author hbich
 */
public class ReservationDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        // Get the values
        String selectedDate = (String) request.getParameter("selectedDate");
        String selectedMonth = (String) request.getParameter("selectedMonth");
        String staffID = (String) request.getParameter("staffID");
        String serviceID = (String) request.getParameter("serviceID");
        String action = (String) request.getParameter("action");

        if (action.equals("checkSlot")) {
            checkSlot(selectedDate, selectedMonth, staffID, serviceID, request, response);
        } else if (action.equals("changeMonth")) {
            changeMonth(selectedMonth, staffID, serviceID, request, response);
        }

    }

    private void changeMonth(String selectedMonth, String staffID, String serviceID, HttpServletRequest request, HttpServletResponse response) throws IOException {
        StaffScheduleDAO ssd = new StaffScheduleDAO();
        List<Integer> Workday = ssd.getWorkDay(staffID, selectedMonth);
        List<Integer> fullDay = ssd.getListDayFullSlot(staffID, selectedMonth);
        
        // Build the string that contain work day and day that fully booked
        StringBuilder sb = new StringBuilder();
        // Append elements from the first ArrayList 
        for (int i = 0; i < Workday.size(); i++) {
            sb.append(Workday.get(i));
            // Add a comma if it's not the last element
            if (i < Workday.size() - 1) {
                sb.append(",");
            }
        }

        // Append "&" to separate the two ArrayLists
        sb.append("&");

        // Append elements from the second ArrayList 
        for (int i = 0; i < fullDay.size(); i++) {
            sb.append(fullDay.get(i));
            // Add a comma if it's not the last element
            if (i < fullDay.size() - 1) {
                sb.append(",");
            }
        }
        String result = sb.toString();

        // Response to the jsp
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(result);
    }

    private void checkSlot(String selectedDate, String selectedMonth, String staffID, String serviceID, HttpServletRequest request, HttpServletResponse response) throws IOException {
        StaffScheduleDAO ssd = new StaffScheduleDAO();
        // Get the data of the work slots
        List<Integer> workSlots = ssd.getWorkSlots(selectedDate, selectedMonth, staffID);

        ReservationDAO rd = new ReservationDAO();
        // This is slot that booked
        List<Integer> bookedSlots = new ArrayList<>();

        for (Reservation r : rd.getSpecificReservation(staffID, selectedDate, selectedMonth, serviceID)) {
            // Check if the reservation of the slot is not cancel
            if (!r.getStatus().equals("cancel")) {
                bookedSlots.add(r.getReservationSlot());
            }
        }
        // Build the string that contain work slot and booked slot
        StringBuilder sb = new StringBuilder();
        // Append elements from the first ArrayList (workSlots)
        for (int i = 0; i < workSlots.size(); i++) {
            sb.append(workSlots.get(i));
            // Add a comma if it's not the last element
            if (i < workSlots.size() - 1) {
                sb.append(",");
            }
        }

        // Append "&" to separate the two ArrayLists
        sb.append("&");

        // Append elements from the second ArrayList (bookedSlots)
        for (int i = 0; i < bookedSlots.size(); i++) {
            sb.append(bookedSlots.get(i));
            // Add a comma if it's not the last element
            if (i < bookedSlots.size() - 1) {
                sb.append(",");
            }
        }
        String result = sb.toString();

        // Response to the jsp
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(result);
    }
}
