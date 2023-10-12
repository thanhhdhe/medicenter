/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import Database.CategoryServiceDAO;
import Database.FeedBackDAO;
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
import java.util.HashMap;
import java.util.Map;
import model.CategoryService;
import model.Reservation;
import model.Service;
import model.Staff;
import model.User;

/**
 *
 * @author hbich
 */
public class AdminController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        if (session.getAttribute("adminEmail") == null) {
            request.getRequestDispatcher("./view/login-admin.jsp").forward(request, response);
        } else {
            processData(7, session, request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        String event = (String) request.getParameter("event");
        switch (event) {
            case "login": {
                login(request, response);
                break;
            }
            case "changeDate": {
                String day = (String) request.getParameter("day");
                int day_number = Integer.parseInt(day);
                processData(day_number, session, request, response);
                break;
            }
            case "logout": {
                logOut(session, request, response);
                break;
            }
            default: {
                break;
            }
        }
    }

    private void logOut(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
        session.removeAttribute("adminEmail");
        response.sendRedirect("admin");
    }

    private void processData(int day, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int cancelReservationCount = 0, doneReservationCount = 0, submittedReservationCount = 0;
        int newlyUserCount = 0, newlyUserReservedCount = 0;
        float totalRevenues = 0;
        Map<String, Float> revenueCategory = new HashMap<>();
        Map<String, Float> averageStarByServiceID = new HashMap<>();
        ReservationDAO reservationDAO = new ReservationDAO();
        CategoryServiceDAO categoryServiceDAO = new CategoryServiceDAO();
        UserDAO userDAO = new UserDAO();
        FeedBackDAO feedBackDAO = new FeedBackDAO();
        ServiceDAO serviceDAO = new ServiceDAO();
        StaffDAO staffDAO = new StaffDAO();

        // Get the count of each reservation status
        for (Reservation reservation : reservationDAO.getReservationsByDay(day)) {
            if (reservation.getStatus().equals("done")) {
                doneReservationCount++;
            } else if (reservation.getStatus().equals("cancel")) {
                cancelReservationCount++;
            } else if (!reservation.getStatus().equals("pending")) {
                submittedReservationCount++;
            }
            // Get the total revenues of the reservation
            if (!reservation.getStatus().equals("cancel")) {
                totalRevenues += (reservation.getStatus().equals("cancel") ? 0.0 : reservation.getCost());
            }
        }

        // Revenues by category service
        for (CategoryService categoryService : categoryServiceDAO.getAllCategoryServices()) {
            revenueCategory.put(categoryService.getCategoryName(), reservationDAO.getRevenueByServiceCategory(categoryService.getCategoryID(), day));
        }

        // Newly created user
        for (User user : userDAO.getUsersByCreatedDate(day)) {
            if (user.isStatus()) {
                newlyUserCount++;
            }
        }
        // Newly reserved user

        // Total average star 
        float totalAverageStar = feedBackDAO.getTotalAverageStarByDay(day);

        // By service average star
        for (Service service : serviceDAO.getAllServices()) {
            averageStarByServiceID.put(service.getTitle(), feedBackDAO.getAverageStarByDayAndService(day, service.getServiceID()));
        }

        request.setAttribute("doneReservationCount", doneReservationCount);
        request.setAttribute("cancelReservationCount", cancelReservationCount);
        request.setAttribute("submittedReservationCount", submittedReservationCount);
        request.setAttribute("newlyUserCount", newlyUserCount);
        request.setAttribute("newlyUserReservedCount", newlyUserReservedCount);
        request.setAttribute("totalRevenues", totalRevenues);
        request.setAttribute("revenueCategory", revenueCategory);
        request.setAttribute("totalAverageStar", totalAverageStar);
        request.setAttribute("averageStarByServiceID", averageStarByServiceID);

        // Admin avatar
        String adminEmail = (String) session.getAttribute("adminEmail");
        request.setAttribute("admin", staffDAO.getStaffByStaffEmail(adminEmail));

        request.getRequestDispatcher("./view/admin-dashboard.jsp").forward(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = (String) request.getParameter("email");
        String password = (String) request.getParameter("password");
        if ("".equals(email.trim())) {
            request.setAttribute("errorMessage", "Your email is invalid");
            request.getRequestDispatcher("./view/login-admin.jsp").include(request, response);
            return;
        }
        if ("".equals(password.trim())) {
            request.setAttribute("errorMessage", "Your password is invalid");
            request.getRequestDispatcher("./view/login-admin.jsp").include(request, response);
            return;
        }
        StaffDAO staffDAO = new StaffDAO();
        Staff staff = staffDAO.getStaffByStaffEmail(email);
        if (staff == null) {
            request.setAttribute("errorMessage", "Your account is not in the database");
            request.getRequestDispatcher("./view/login-admin.jsp").include(request, response);
            return;
        }
        if (!staff.getPassword().equals(password)) {
            request.setAttribute("errorMessage", "Your password is incorrect");
            request.getRequestDispatcher("./view/login-admin.jsp").include(request, response);
            return;
        }
        if (!staff.getRole().equals("admin")) {
            request.setAttribute("errorMessage", "You don't have permission to login.");
            request.getRequestDispatcher("./view/login-admin.jsp").include(request, response);
            return;
        }
        HttpSession session = request.getSession(true);
        session.setAttribute("adminEmail", email);
        response.sendRedirect("admin");
    }
}
