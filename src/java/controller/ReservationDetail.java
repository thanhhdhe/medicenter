/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import Database.ChildrenDAO;
import Database.ReservationDAO;
import Database.ServiceDAO;
import Database.ServiceStaffDAO;
import Database.StaffDAO;
import Database.StaffScheduleDAO;
import Database.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import model.Reservation;
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
            HttpSession session = request.getSession(true);
            if (session.getAttribute("email") == null) {
                response.sendRedirect("home");
                return;
            }

            // Update the database to cancel the pending reservation exceeds 5 minutes
            ReservationDAO reservationDAO = new ReservationDAO();
            reservationDAO.updateDatabase();

            // Receive serviceID, staffID, childID, action
            String serviceID = (String) request.getParameter("serviceID");
            String staffID = (String) request.getParameter("staffID");
            String childID = (String) request.getParameter("childID");
            String action = (String) request.getParameter("action");

            UserDAO userDAO = new UserDAO();
            ChildrenDAO childrenDAO = new ChildrenDAO();
            String email = (String) session.getAttribute("email");

            // Validate children id
            if (!childrenDAO.validateChildren(childID, Integer.toString(userDAO.getUser(email).getUserID()))) {
                response.sendRedirect("404");
                return;
            }

            // Get the current date
            LocalDate currentDate = LocalDate.now();

            // Get the current month as an integer (1-12)
            int currentMonthValue = currentDate.getMonthValue();
            int currentYearValue = currentDate.getYear();

            StaffDAO staffDAO = new StaffDAO();
            StaffScheduleDAO staffscheduleDAO = new StaffScheduleDAO();
            ServiceDAO serviceDAO = new ServiceDAO();

            // Check the service id is not null
            if (serviceID == null) {
                response.sendRedirect("404");
                return;
            }

            // Check is user choose service or choose staff
            if (staffID == null) {
                if (serviceDAO.getServiceByID(serviceID) != null) {
                    // Set attribute to send to the page
                    Service service = serviceDAO.getServiceByID(serviceID);
                    request.setAttribute("service", service);

                    request.setAttribute("staff", null);
                    request.setAttribute("ChildID", childID);

                    // Process the service schedule for all staff available
                    List<Integer> Workday = staffscheduleDAO.getWorkdayByServiceID(serviceID, Integer.toString(currentMonthValue), Integer.toString(currentYearValue));
                    List<Integer> fullDay = staffscheduleDAO.getFullDayByServiceID(serviceID, Integer.toString(currentMonthValue), Integer.toString(currentYearValue));
                    request.setAttribute("Workday", Workday);
                    request.setAttribute("fullDay", fullDay);

                    request.getRequestDispatcher("/view/reservationdetail.jsp").include(request, response);
                } else {
                    response.sendRedirect("404");
                }
            } else {
                // Check the existence of the staff and services
                if (staffDAO.getStaffByStaffId(Integer.parseInt(staffID)) != null && serviceDAO.getServiceByID(serviceID) != null) {
                    // Check if the Staff is correctly working for the service
                    ServiceStaffDAO servicestaffDAO = new ServiceStaffDAO();
                    if (!servicestaffDAO.checkExist(staffID, serviceID)) {
                        response.sendRedirect("404");
                        return;
                    }

                    // Get the staff
                    Staff staff = staffDAO.getStaffByStaffId(Integer.parseInt(staffID));

                    // Set attribute to send to the page
                    Service service = serviceDAO.getServiceByID(serviceID);
                    request.setAttribute("service", service);
                    request.setAttribute("Staff", staff);
                    request.setAttribute("ChildID", childID);

                    // Process staff schedule
                    List<Integer> Workday = staffscheduleDAO.getWorkDay(staffID, Integer.toString(currentMonthValue), Integer.toString(currentYearValue)); // The variable will contain the number of workdays

                    // List of slot and day in the reservation of that staff ID
                    List<Integer> temp = staffscheduleDAO.getWorkDay(staffID, Integer.toString(currentMonthValue), Integer.toString(currentYearValue));
                    List<Integer> fullDay = staffscheduleDAO.getWorkDay(staffID, Integer.toString(currentMonthValue), Integer.toString(currentYearValue));
                    // loop to check if at least one slot for that day is available
                    for (int day : temp) {
                        // Boolean to check if the slot is available
                        boolean check = false;
                        // Select all the slot have in that day
                        for (int slot : staffscheduleDAO.getWorkSlots(Integer.toString(day), Integer.toString(currentMonthValue), Integer.toString(currentYearValue), staffID)) {
                            if (reservationDAO.checkSlotForAvailable(Integer.toString(slot), staffID, Integer.toString(day), Integer.toString(currentMonthValue), Integer.toString(currentYearValue)) == true) {
                                check = true;
                                break;
                            }

                        }
                        // If we can search the slot in the reservation with status != cancel => fullDay remove it
                        if (check == true) {
                            fullDay.remove(Integer.valueOf(day));
                        }
                    }
                    request.setAttribute("Workday", Workday);
                    request.setAttribute("fullDay", fullDay);
                    if (action != null) {
                        String reservationID = (String) request.getParameter("reservationID");
                        Reservation reservation = reservationDAO.getReservationByID(Integer.parseInt(reservationID));
                        // Check if the reservation belongs to the user 
                        if (reservation.getUserID() != userDAO.getUser(email).getUserID()) {
                            response.sendRedirect("404");
                            return;
                        }
                        request.setAttribute("dateChange", reservation.getReservationDate());
                        request.setAttribute("reservationID", reservation.getReservationID());
                        request.getRequestDispatcher("/view/reservationdetail.jsp").include(request, response);
                    } else {
                        request.getRequestDispatcher("/view/reservationdetail.jsp").include(request, response);
                    }
                } else {
                    response.sendRedirect("404");
                }
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
}
