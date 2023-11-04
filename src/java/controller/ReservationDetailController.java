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
        // Check if user has been login
        if (email == null) {
            response.sendRedirect("home");
        }
        UserDAO userDAO = new UserDAO();

        // Update the database to cancel the pending reservation exceeds 5 minutes
        ReservationDAO reservationDAO = new ReservationDAO();
        reservationDAO.updateDatabase();

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
                String ChildID = (String) request.getParameter("ChildID");
                saveData(selectedDate, selectedMonth, selectedYear, staffID, slot, serviceID, userDAO.getUser(email), ChildID, request, response);
                break;
            }
            case "update": {
                String slot = (String) request.getParameter("slot");
                String reservationID = (String) request.getParameter("reservationID");
                updateData(staffID, selectedDate, selectedMonth, selectedYear, slot, reservationID, request, response);
                break;
            }
            default:
                break;
        }

    }

    public boolean validateSlot(String slot, String selectedDate, String selectedMonth, String selectedYear, String staffID, String childID) {
        ReservationDAO reservationDAO = new ReservationDAO();
        // This is slot that booked
        List<Integer> bookedSlots = new ArrayList<>();

        for (Reservation reservation : reservationDAO.getSpecificReservation(staffID, selectedDate, selectedMonth, selectedYear)) {
            // Check if the reservation of the slot is not cancel
            if (!reservation.getStatus().equals("cancel")) {
                bookedSlots.add(reservation.getReservationSlot());
            }
        }
        return bookedSlots.contains(Integer.valueOf(slot));
    }

    synchronized private void updateData(String staffID, String selectedDate, String selectedMonth, String selectedYear, String slot, String reservationID, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Validate slot if it booked or not
        if (validateSlot(slot, selectedDate, selectedMonth, selectedYear, staffID, staffID)) {
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("Choose date again");
        } else {
            ReservationDAO reservationDAO = new ReservationDAO();
            // Get the date
            Date sqlDate = null;
            try {
                String date = selectedMonth + "-" + selectedDate + "-" + selectedYear;
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
                java.util.Date utilDate = dateFormat.parse(date);
                sqlDate = new Date(utilDate.getTime());
            } catch (Exception e) {

            }
            Reservation reservation = reservationDAO.getReservationByID(Integer.parseInt(reservationID));
            reservation.setReservationDate(sqlDate);
            reservation.setReservationSlot(Integer.parseInt(slot));
            reservationDAO.update(reservation);
            // Send the reservation id to the jsp
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("success");
        }
    }

    synchronized private void saveData(String selectedDate, String selectedMonth, String selectedYear, String staffID, String slot, String serviceID, User user, String ChildID, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ReservationDAO reservationDAO = new ReservationDAO();
        ServiceDAO serviceDAO = new ServiceDAO();
        ServiceStaffDAO servicestaffDAO = new ServiceStaffDAO();
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
        // Check duplicate if user click two times or user book for that chilren 2 service at one slot
        try {
            int reservationID = reservationDAO.findReservationID(user.getUserID(), ChildID, serviceID, sqlDate, Integer.parseInt(slot));
            if (!reservationDAO.getReservationByID(reservationID).getStatus().equals("cancel")) {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("Duplicate reservation");
                return;
            }
            if (!reservationDAO.validateReservationByChildrenID(ChildID, Integer.parseInt(slot), sqlDate)) {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("Double book at one time");
                return;
            }
        } catch (Exception e) {

        }
        // Check the limit of reservation that child have make
        if (reservationDAO.countUserTodayReservations(Integer.toString(user.getUserID()), ChildID) >= 6) {
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("Exceed the limit");
            return;
        }

        Reservation reservation = new Reservation();
        if (staffID.equals("all") || staffID == null) {
            // Double check if there is no staff for this service
            List<Integer> listStaff = servicestaffDAO.getListStaffIDCanWork(selectedDate, selectedMonth, selectedYear, slot, serviceID);

            if (listStaff == null || listStaff.size() == 0) {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("Choose date again");
                return;
            }

            // Create an reservation
            reservation.setCreatedDate(sqlTimestamp);
            reservation.setReservationDate(sqlDate);
            reservation.setReservationSlot(Integer.parseInt(slot));
            reservation.setServiceID(Integer.parseInt(serviceID));

            reservation.setStaffID(listStaff.get(0));

            if (service.getSalePrice() > 0) {
                reservation.setCost((float) service.getSalePrice());
            } else {
                reservation.setCost((float) service.getOriginalPrice());
            }
            reservation.setStatus("pending");
            reservation.setUserID(user.getUserID());
            reservation.setChildID(Integer.parseInt(ChildID));
            reservationDAO.insert(reservation);
        } else {
            // Validate slot
            if (validateSlot(slot, selectedDate, selectedMonth, selectedYear, staffID, ChildID)) {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("Choose date again");
                return;
            } else {
                reservation.setCreatedDate(sqlTimestamp);
                reservation.setReservationDate(sqlDate);
                reservation.setReservationSlot(Integer.parseInt(slot));
                reservation.setServiceID(Integer.parseInt(serviceID));
                reservation.setStaffID(Integer.parseInt(staffID));
                if (service.getSalePrice() > 0) {
                    reservation.setCost((float) service.getSalePrice());
                } else {
                    reservation.setCost((float) service.getOriginalPrice());
                }
                reservation.setStatus("pending");
                reservation.setUserID(user.getUserID());
                reservation.setChildID(Integer.parseInt(ChildID));
                reservationDAO.insert(reservation);
            }
        }
        int Id = reservationDAO.findReservationID(user.getUserID(), ChildID, serviceID, sqlDate, Integer.parseInt(slot));
        // Send the reservation id to the jsp
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(Integer.toString(Id));
    }

    private void changeMonth(String selectedMonth, String selectedYear, String staffID, String serviceID, HttpServletRequest request, HttpServletResponse response) throws IOException {
        StaffScheduleDAO staffscheduleDAO = new StaffScheduleDAO();
        ReservationDAO reservationDAO = new ReservationDAO();
        List<Integer> Workday = null;
        List<Integer> fullDay = null;
        if (staffID.equals("all")) {
            Workday = staffscheduleDAO.getWorkdayByServiceID(serviceID, selectedMonth, selectedYear);
            fullDay = staffscheduleDAO.getFullDayByServiceID(serviceID, selectedMonth, selectedYear);
        } else {
            Workday = staffscheduleDAO.getWorkDay(staffID, selectedMonth, selectedYear);
            fullDay = staffscheduleDAO.getWorkDay(staffID, selectedMonth, selectedYear);
            for (int day : Workday) {
                // Boolean to check if the slot is available
                boolean check = false;
                // Select all the slot have in that day
                for (int slot : staffscheduleDAO.getWorkSlots(Integer.toString(day), selectedMonth, selectedYear, staffID)) {
                    if (reservationDAO.checkSlotForAvailable(Integer.toString(slot), staffID, Integer.toString(day), selectedMonth, selectedYear) == true) {
                        check = true;
                        break;
                    }
                }
                // If we can search the slot in the reservation with status != cancel => fullDay remove it
                if (check == true) {
                    fullDay.remove(Integer.valueOf(day));
                }
            }
        }
        // Build the string that contain work day and day that fully booked
        StringBuilder stringBuilder = new StringBuilder();
        // Append elements from the first ArrayList 
        for (int day = 0; day < Workday.size(); day++) {
            stringBuilder.append(Workday.get(day));
            // Add a comma if it's not the last element
            if (day < Workday.size() - 1) {
                stringBuilder.append(",");
            }
        }

        // Append "&" to separate the two ArrayLists
        stringBuilder.append("&");

        // Append elements from the second ArrayList 
        for (int day = 0; day < fullDay.size(); day++) {
            stringBuilder.append(fullDay.get(day));
            // Add a comma if it's not the last element
            if (day < fullDay.size() - 1) {
                stringBuilder.append(",");
            }
        }
        String result = stringBuilder.toString();

        // Response to the jsp
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(result);
    }

    private void checkSlot(String selectedDate, String selectedMonth, String selectedYear, String staffID, String childID, HttpServletRequest request, HttpServletResponse response) throws IOException {
        StaffScheduleDAO staffscheduleDAO = new StaffScheduleDAO();
        // Get the data of the work slots
        List<Integer> workSlots = staffscheduleDAO.getWorkSlots(selectedDate, selectedMonth, selectedYear, staffID);

        ReservationDAO reservationDAO = new ReservationDAO();
        // This is slot that booked
        List<Integer> bookedSlots = new ArrayList<>();

        for (Reservation reservation : reservationDAO.getSpecificReservation(staffID, selectedDate, selectedMonth, selectedYear)) {
            // Check if the reservation of the slot is not cancel
            if (!reservation.getStatus().equals("cancel")) {
                bookedSlots.add(reservation.getReservationSlot());
            }
        }

        // Get the list that self booking
        List<Integer> selfBooked = reservationDAO.getListSelfBookedSlot(childID, selectedDate, selectedMonth, selectedYear);

        // Build the string that contain work slot and booked slot
        StringBuilder stringBuilder = new StringBuilder();
        // Append elements from the first ArrayList (workSlots)
        for (int slot = 0; slot < workSlots.size(); slot++) {
            stringBuilder.append(workSlots.get(slot));
            // Add a comma if it's not the last element
            if (slot < workSlots.size() - 1) {
                stringBuilder.append(",");
            }
        }

        // Append "&" to separate the ArrayLists
        stringBuilder.append("&");

        // Append elements from the second ArrayList (bookedSlots)
        for (int slot = 0; slot < bookedSlots.size(); slot++) {
            stringBuilder.append(bookedSlots.get(slot));
            // Add a comma if it's not the last element
            if (slot < bookedSlots.size() - 1) {
                stringBuilder.append(",");
            }
        }

        // Append "&" to separate the ArrayLists
        stringBuilder.append("&");

        // Append elements from the second ArrayList (selfBooked)
        for (int slot = 0; slot < selfBooked.size(); slot++) {
            stringBuilder.append(selfBooked.get(slot));
            // Add a comma if it's not the last element
            if (slot < selfBooked.size() - 1) {
                stringBuilder.append(",");
            }
        }

        String result = stringBuilder.toString();

        // Response to the jsp
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(result);
    }

    private void checkSlotForService(String selectedDate, String selectedMonth, String selectedYear, String serviceID, String childID, HttpServletRequest request, HttpServletResponse response) throws IOException {
        StaffScheduleDAO staffscheduleDAO = new StaffScheduleDAO();
        ReservationDAO reservationDAO = new ReservationDAO();
        // Store list of slot that staff can work
        List<Integer> slotAvailable = staffscheduleDAO.getWorkSlotsByService(selectedDate, selectedMonth, selectedYear, serviceID);
        // Variable store list reservation of that service ID in that date
        List<Reservation> listReservation = reservationDAO.getListReservationByServiceID(serviceID, selectedDate, selectedMonth, selectedYear);
        // Clone an new array to process the slots that fully booking
        List<Integer> temp = new ArrayList<>();
        for (int slot : slotAvailable) {
            temp.add(slot);
        }
        // Remove slot have been booked from the list
        for (Reservation reservation : listReservation) {
            temp.remove(Integer.valueOf(reservation.getReservationSlot()));
        }
        // Create an variable to store the slot don't have any slot for book
        List<Integer> fullBookSlot = new ArrayList<>();
        for (int slot : slotAvailable) {
            if (!temp.contains(slot)) {
                fullBookSlot.add(slot);
            }
        }
        // Get the list that self booking
        List<Integer> selfBooked = reservationDAO.getListSelfBookedSlot(childID, selectedDate, selectedMonth, selectedYear);

        // Create to string and send to the jsp
        StringBuilder stringBuilder = new StringBuilder();
        // Append elements from the first ArrayList
        for (int slot = 0; slot < slotAvailable.size(); slot++) {
            stringBuilder.append(slotAvailable.get(slot).toString());
            // Add a comma if it's not the last element
            if (slot < slotAvailable.size() - 1) {
                stringBuilder.append(",");
            }
        }

        // Append "&" to separate between arraylist
        stringBuilder.append("&");

        // Append elements from the second ArrayList
        for (int slot = 0; slot < fullBookSlot.size(); slot++) {
            stringBuilder.append(fullBookSlot.get(slot));
            // Add a comma if it's not the last element
            if (slot < fullBookSlot.size() - 1) {
                stringBuilder.append(",");
            }
        }

        // Append "&" to separate between arraylist
        stringBuilder.append("&");

        // Append elements from the third ArrayList
        for (int slot = 0; slot < selfBooked.size(); slot++) {
            stringBuilder.append(selfBooked.get(slot).toString());
            // Add a comma if it's not the last element
            if (slot < selfBooked.size() - 1) {
                stringBuilder.append(",");
            }
        }

        String result = stringBuilder.toString();
        // Response to the jsp
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(result);
    }
}
