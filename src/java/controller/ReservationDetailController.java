/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import Database.ReservationDAO;
import Database.ServiceDAO;
import Database.ServiceStaffDAO;
import Database.StaffScheduleDAO;
import Database.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Reservation;
import model.Service;
import model.User;

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

        HttpSession session = request.getSession(true);
        String email = (String) session.getAttribute("email");
        if (email == null) {
            response.sendRedirect("home");
        }
        UserDAO userDAO = new UserDAO();

        // Update database
        ReservationDAO rdao = new ReservationDAO();
        rdao.updateDatabase();

        // Get the values
        String selectedDate = (String) request.getParameter("selectedDate");
        String selectedMonth = (String) request.getParameter("selectedMonth");
        String selectedYear = (String) request.getParameter("selectedYear");
        String staffID = (String) request.getParameter("staffID");
        String action = (String) request.getParameter("action");

        switch (action) {
            case "checkSlot": {
                String ChildID = (String) request.getParameter("ChildID");
                checkSlot(selectedDate, selectedMonth, selectedYear, staffID, ChildID, request, response);
                break;
            }
            case "changeMonth": {
                String serviceID = (String) request.getParameter("serviceID");
                changeMonth(selectedMonth, selectedYear, staffID, serviceID, request, response);
                break;
            }
            case "checkSlotForService": {
                String serviceID = (String) request.getParameter("serviceID");
                String ChildID = (String) request.getParameter("ChildID");
                checkSlotForService(selectedDate, selectedMonth, selectedYear, serviceID, ChildID, request, response);
                break;
            }
            case "save": {
                String serviceID = (String) request.getParameter("serviceID");
                String slot = (String) request.getParameter("slot");
                saveData(selectedDate, selectedMonth, selectedYear, staffID, slot, serviceID, userDAO.getUser(email), request, response);
                break;
            }
            default:
                break;
        }

    }

    private void saveData(String selectedDate, String selectedMonth, String selectedYear, String staffID, String slot, String serviceID, User user, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ReservationDAO rd = new ReservationDAO();
        ServiceDAO serviceDAO = new ServiceDAO();
        ServiceStaffDAO ssd = new ServiceStaffDAO();
        Service service = serviceDAO.getServiceByID(serviceID);
        // Get the current time
        LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp sqlTimestamp = Timestamp.valueOf(currentDateTime);
        // Get the date
        Date sqlDate = null;
        try {
            String date = selectedMonth + "-" + selectedDate + "-" + selectedYear;
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
            java.util.Date utilDate = dateFormat.parse(date);
            sqlDate = new Date(utilDate.getTime());
        } catch (Exception e) {

        }
        if (staffID.equals("all") || staffID == null) {
            // Create an reservation
            Reservation r = new Reservation();
            r.setCreatedDate(sqlTimestamp);
            r.setReservationDate(sqlDate);
            r.setReservationSlot(Integer.parseInt(slot));
            r.setServiceID(Integer.parseInt(serviceID));

            r.setStaffID(ssd.getListStaffIDCanWork(selectedDate, selectedMonth, selectedYear, slot, serviceID).get(0));

            if (service.getSalePrice() > 0) {
                r.setCost((float) service.getSalePrice());
            } else {
                r.setCost((float) service.getOriginalPrice());
            }
            r.setStatus("pending");
            r.setUserID(user.getUserID());
            rd.insert(r);
        } else {
            Reservation r = new Reservation();
            r.setCreatedDate(sqlTimestamp);
            r.setReservationDate(sqlDate);
            r.setReservationSlot(Integer.parseInt(slot));
            r.setServiceID(Integer.parseInt(serviceID));
            r.setStaffID(Integer.parseInt(staffID));
            if (service.getSalePrice() > 0) {
                r.setCost((float) service.getSalePrice());
            } else {
                r.setCost((float) service.getOriginalPrice());
            }
            r.setStatus("pending");
            r.setUserID(user.getUserID());
            rd.insert(r);
        }
        int Id = rd.findReservationID(user.getUserID(), serviceID, sqlDate, Integer.parseInt(slot));
        // Send the reservation id to the jsp
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(Integer.toString(Id));
    }

    private void changeMonth(String selectedMonth, String selectedYear, String staffID, String serviceID, HttpServletRequest request, HttpServletResponse response) throws IOException {
        StaffScheduleDAO ssd = new StaffScheduleDAO();
        List<Integer> Workday = null;
        List<Integer> fullDay = null;
        if (staffID.equals("all")) {
            Workday = ssd.getWorkdayByServiceID(serviceID, selectedMonth, selectedYear);
            fullDay = ssd.getFullDayByServiceID(serviceID, selectedMonth, selectedYear);
        } else {
            Workday = ssd.getWorkDay(staffID, selectedMonth, selectedYear);
            fullDay = ssd.getListDayFullSlot(staffID, selectedMonth, selectedYear);
        }
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

    private void checkSlot(String selectedDate, String selectedMonth, String selectedYear, String staffID, String childID, HttpServletRequest request, HttpServletResponse response) throws IOException {
        StaffScheduleDAO ssd = new StaffScheduleDAO();
        // Get the data of the work slots
        List<Integer> workSlots = ssd.getWorkSlots(selectedDate, selectedMonth, selectedYear, staffID);

        ReservationDAO rd = new ReservationDAO();
        // This is slot that booked
        List<Integer> bookedSlots = new ArrayList<>();

        for (Reservation r : rd.getSpecificReservation(staffID, selectedDate, selectedMonth, selectedYear)) {
            // Check if the reservation of the slot is not cancel
            if (!r.getStatus().equals("cancel")) {
                bookedSlots.add(r.getReservationSlot());
            }
        }

        // Get the list that self booking
        List<Integer> selfBooked = rd.getListSelfBookedSlot(childID, selectedDate, selectedMonth, selectedYear);

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

        // Append "&" to separate the ArrayLists
        sb.append("&");

        // Append elements from the second ArrayList (bookedSlots)
        for (int i = 0; i < bookedSlots.size(); i++) {
            sb.append(bookedSlots.get(i));
            // Add a comma if it's not the last element
            if (i < bookedSlots.size() - 1) {
                sb.append(",");
            }
        }
        
        // Append "&" to separate the ArrayLists
        sb.append("&");

        // Append elements from the second ArrayList (selfBooked)
        for (int i = 0; i < selfBooked.size(); i++) {
            sb.append(selfBooked.get(i));
            // Add a comma if it's not the last element
            if (i < selfBooked.size() - 1) {
                sb.append(",");
            }
        }
        
        String result = sb.toString();
        
        // Response to the jsp
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(result);
    }

    private void checkSlotForService(String selectedDate, String selectedMonth, String selectedYear, String serviceID, String childID, HttpServletRequest request, HttpServletResponse response) throws IOException {
        StaffScheduleDAO ssd = new StaffScheduleDAO();
        ReservationDAO rdao = new ReservationDAO();
        // Store list of slot that staff can work
        List<Integer> slotAvailable = ssd.getWorkSlotsByService(selectedDate, selectedMonth, selectedYear, serviceID);
        // Variable store list reservation of that service ID in that date
        List<Reservation> listReservation = rdao.getListReservationByServiceID(serviceID, selectedDate, selectedMonth, selectedYear);
        // Clone an new array to process the slots that fully booking
        List<Integer> temp = new ArrayList<>();
        for (int i : slotAvailable) {
            temp.add(i);
        }
        // Remove slot have been booked from the list
        for (Reservation reservation : listReservation) {
            temp.remove(Integer.valueOf(reservation.getReservationSlot()));
        }
        // Create an variable to store the slot don't have any slot for book
        List<Integer> fullBookSlot = new ArrayList<>();
        for (int i : slotAvailable) {
            if (!temp.contains(i)) {
                fullBookSlot.add(i);
            }
        }
        // Get the list that self booking
        List<Integer> selfBooked = rdao.getListSelfBookedSlot(childID, selectedDate, selectedMonth, selectedYear);

        // Create to string and send to the jsp
        StringBuilder sb = new StringBuilder();
        // Append elements from the first ArrayList
        for (int i = 0; i < slotAvailable.size(); i++) {
            sb.append(slotAvailable.get(i).toString());
            // Add a comma if it's not the last element
            if (i < slotAvailable.size() - 1) {
                sb.append(",");
            }
        }

        // Append "&" to separate between arraylist
        sb.append("&");

        // Append elements from the second ArrayList
        for (int i = 0; i < fullBookSlot.size(); i++) {
            sb.append(fullBookSlot.get(i));
            // Add a comma if it's not the last element
            if (i < fullBookSlot.size() - 1) {
                sb.append(",");
            }
        }

        // Append "&" to separate between arraylist
        sb.append("&");

        // Append elements from the third ArrayList
        for (int i = 0; i < selfBooked.size(); i++) {
            sb.append(selfBooked.get(i).toString());
            // Add a comma if it's not the last element
            if (i < selfBooked.size() - 1) {
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
