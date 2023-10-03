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
            ReservationDAO rdao = new ReservationDAO();
            rdao.updateDatabase();

            // Receive serviceID and staffID 
            String serviceID = (String) request.getParameter("serviceID");
            String staffID = (String) request.getParameter("staffID");
            String childID = (String) request.getParameter("childID");

            // Validate children
            if (childID == null) {
                response.sendRedirect("home");
                return;
            }
            UserDAO userDAO = new UserDAO();
            ChildrenDAO cdao = new ChildrenDAO();
            String email = (String) session.getAttribute("email");
            if (!cdao.validateChildren(childID, Integer.toString(userDAO.getUser(email).getUserID()))) {
                response.sendRedirect("home");
                return;
            }

            // Get the current date
            LocalDate currentDate = LocalDate.now();

            // Get the current month as an integer (1-12)
            int currentMonthValue = currentDate.getMonthValue();
            int currentYearValue = currentDate.getYear();

            StaffDAO sd = new StaffDAO();
            StaffScheduleDAO ssd = new StaffScheduleDAO();
            ServiceDAO serviceDAO = new ServiceDAO();

            if (staffID == null) {
                // Check is user choose service or choose staff
                if (serviceDAO.getServiceByID(serviceID) != null) {
                    // Set attribute to send to the page
                    Service service = serviceDAO.getServiceByID(serviceID);
                    request.setAttribute("service", service);

                    request.setAttribute("staff", null);
                    request.setAttribute("ChildID", childID);

                    // Process the service schedule for all staff available
                    List<Integer> Workday = ssd.getWorkdayByServiceID(serviceID, Integer.toString(currentMonthValue), Integer.toString(currentYearValue));
                    List<Integer> fullDay = ssd.getFullDayByServiceID(serviceID, Integer.toString(currentMonthValue), Integer.toString(currentYearValue));
                    request.setAttribute("Workday", Workday);
                    request.setAttribute("fullDay", fullDay);

                    request.getRequestDispatcher("/view/reservationdetail.jsp").include(request, response);
                } else {
                    response.sendRedirect("home");
                }
            } else {
                // Check the existence of the staff and services
                if (sd.getStaffByStaffId(Integer.parseInt(staffID)) != null && serviceDAO.getServiceByID(serviceID) != null) {

                    // Check if the Staff is correctly working for the service
                    ServiceStaffDAO servicestaffDAO = new ServiceStaffDAO();
                    if (!servicestaffDAO.checkExist(staffID, serviceID)) {
                        response.sendRedirect("home");
                        return;
                    }

                    // Get the staff
                    Staff staff = sd.getStaffByStaffId(Integer.parseInt(staffID));

                    // Set attribute to send to the page
                    Service service = serviceDAO.getServiceByID(serviceID);
                    request.setAttribute("service", service);
                    request.setAttribute("Staff", staff);
                    request.setAttribute("ChildID", childID);

                    // Process staff schedule
                    List<Integer> Workday = ssd.getWorkDay(staffID, Integer.toString(currentMonthValue), Integer.toString(currentYearValue)); // The variable will contain the number of workdays

                    // List of slot and day in the reservation of that staff ID
                    List<Integer> temp = ssd.getWorkDay(staffID, Integer.toString(currentMonthValue), Integer.toString(currentYearValue));
                    List<Integer> fullDay = ssd.getWorkDay(staffID, Integer.toString(currentMonthValue), Integer.toString(currentYearValue));
                    // Loop in workday
                    for (int day : temp) {
                        // Boolean to check if the slot is available
                        boolean check = false;
                        // Select all the slot have in that day
                        for (int slot : ssd.getWorkSlots(Integer.toString(day), Integer.toString(currentMonthValue), Integer.toString(currentYearValue), staffID)) {
                            if (rdao.checkSlotForAvailable(Integer.toString(slot), staffID, Integer.toString(day), Integer.toString(currentMonthValue), Integer.toString(currentYearValue)) == true) {
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

                    request.getRequestDispatcher("/view/reservationdetail.jsp").include(request, response);
                } else {
                    response.sendRedirect("home");
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
