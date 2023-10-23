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
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.CategoryService;
import model.Reservation;
import model.Service;

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
        String action = (String) request.getParameter("action");

        // Get the current date
        Date currentDate = new Date(System.currentTimeMillis());

        // Create a Calendar instance and set it to the current date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        // Subtract 7 days
        calendar.add(Calendar.DAY_OF_MONTH, -7);

        // Get the date seven days ago as a java.sql.Date
        Date sevenDaysAgo = new Date(calendar.getTimeInMillis());

        // Update the database to cancel the pending reservation exceeds 5 minutes
        ReservationDAO reservationDAO = new ReservationDAO();
        reservationDAO.updateDatabase();

        if (session.getAttribute("adminEmail") == null) {
            request.getRequestDispatcher("/staff?event=sent-to-login").forward(request, response);
        } else {
            if (action != null) {
                switch (action) {
                    case "logout": {
                        logOut(session, request, response);
                        break;
                    }
                    case "changeDate": {
                        String startDay = (String) request.getParameter("startDay");
                        java.sql.Date startDate = java.sql.Date.valueOf(startDay);
                        String endDay = (String) request.getParameter("endDay");
                        java.sql.Date endDate = java.sql.Date.valueOf(endDay);
                        processData(true, startDate, endDate, calendar, session, request, response);
                        break;
                    }
                    case "send-to-customer-list": {
                        sendToCustomerList(session, request, response);
                        break;
                    }
                    case "send-to-setting-list": {
                        sendToSettingList(session, request, response);
                        break;
                    }
                }
            } else {
                processData(false, sevenDaysAgo, currentDate, calendar, session, request, response);
            }
        }
    }

    private void logOut(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
        session.invalidate();
        response.sendRedirect("admin");
    }

    private void processData(Boolean isChangeDate, Date startDate, Date endDate, Calendar calendar, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int cancelReservationCount = 0, doneReservationCount = 0, submittedReservationCount = 0;
        int newlyUserCount = 0, newlyUserReservedCount = 0;
        float totalRevenues = 0;
        Map<String, Float> revenueCategory = new HashMap<>();
        Map<String, Float> averageStarByServiceID = new HashMap<>();
        List<Integer> doneReservation = new ArrayList<>();
        List<Integer> allReservation = new ArrayList<>();

        ReservationDAO reservationDAO = new ReservationDAO();
        CategoryServiceDAO categoryServiceDAO = new CategoryServiceDAO();
        UserDAO userDAO = new UserDAO();
        FeedBackDAO feedBackDAO = new FeedBackDAO();
        ServiceDAO serviceDAO = new ServiceDAO();
        StaffDAO staffDAO = new StaffDAO();

        // Get the count of each reservation status
        for (Reservation reservation : reservationDAO.getReservationsByDay(startDate, endDate)) {
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
            revenueCategory.put(categoryService.getCategoryName(), reservationDAO.getRevenueByServiceCategory(categoryService.getCategoryID(), startDate, endDate));
        }

        // Newly created user
        newlyUserCount = userDAO.getUserCountByCreatedDate(startDate, endDate);

        // Newly reserved user
        newlyUserReservedCount = reservationDAO.countNewlyReservedMember(startDate, endDate);

        // Total average star 
        float totalAverageStar = feedBackDAO.getTotalAverageStarByDay(startDate, endDate);

        // By service average star
        for (Service service : serviceDAO.getAllServices()) {
            averageStarByServiceID.put(service.getTitle(), feedBackDAO.getAverageStarByDayAndService(startDate, endDate, service.getServiceID()));
        }

        // Trending reservation
        if (startDate.getMonth() == endDate.getMonth()) {
            while (startDate.compareTo(endDate) != 0) {
                doneReservation.add(reservationDAO.getReservationDoneEachDay(startDate));
                allReservation.add(reservationDAO.getReservationTotalEachDay(startDate));

                //Adding 1 day in startDate
                calendar.setTime(startDate);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                startDate = new Date(calendar.getTimeInMillis());
            }
            doneReservation.add(reservationDAO.getReservationDoneEachDay(startDate));
            allReservation.add(reservationDAO.getReservationTotalEachDay(startDate));
        } else {
            while (startDate.getMonth() != endDate.getMonth()) {
                doneReservation.add(reservationDAO.getReservationDoneEachMonth(startDate));
                allReservation.add(reservationDAO.getReservationTotalEachMonth(startDate));

                //Adding 1 month in startDate
                calendar.setTime(startDate);
                calendar.add(Calendar.MONTH, 1);
                startDate = new Date(calendar.getTimeInMillis());
            }
            doneReservation.add(reservationDAO.getReservationDoneEachMonth(startDate));
            allReservation.add(reservationDAO.getReservationTotalEachMonth(startDate));
        }

        if (isChangeDate) {

            response.setContentType("text/plain");

            // Write the data to the response
            PrintWriter out = response.getWriter();
            out.println("cancelReservationCount=" + cancelReservationCount);
            out.println("doneReservationCount=" + doneReservationCount);
            out.println("submittedReservationCount=" + submittedReservationCount);
            out.println("newlyUserCount=" + newlyUserCount);
            out.println("newlyUserReservedCount=" + newlyUserReservedCount);
            out.println("totalRevenues=" + totalRevenues);

            // Write revenueCategory data
            for (Map.Entry<String, Float> entry : revenueCategory.entrySet()) {
                out.println("revenueCategory_" + entry.getKey() + "=" + entry.getValue());
            }

            // Write averageStarByServiceID data
            for (Map.Entry<String, Float> entry : averageStarByServiceID.entrySet()) {
                out.println("averageStarByServiceID_" + entry.getKey() + "=" + entry.getValue());
            }

            out.println("doneReservation=" + doneReservation.stream().map(Object::toString).collect(Collectors.joining(",")));
            out.println("allReservation=" + allReservation.stream().map(Object::toString).collect(Collectors.joining(",")));
            out.println("totalAverageStar=" + totalAverageStar);

            out.close();
        } else {
            request.setAttribute("doneReservationCount", doneReservationCount);
            request.setAttribute("cancelReservationCount", cancelReservationCount);
            request.setAttribute("submittedReservationCount", submittedReservationCount);
            request.setAttribute("newlyUserCount", newlyUserCount);
            request.setAttribute("newlyUserReservedCount", newlyUserReservedCount);
            request.setAttribute("totalRevenues", totalRevenues);
            request.setAttribute("revenueCategory", revenueCategory);
            request.setAttribute("totalAverageStar", totalAverageStar);
            request.setAttribute("averageStarByServiceID", averageStarByServiceID);
            request.setAttribute("doneReservation", doneReservation);
            request.setAttribute("allReservation", allReservation);

            // Admin avatar
            String adminEmail = (String) session.getAttribute("adminEmail");
            request.setAttribute("admin", staffDAO.getStaffByStaffEmail(adminEmail));

            request.getRequestDispatcher("./view/admin-dashboard.jsp").forward(request, response);
        }
    }

    private void sendToCustomerList(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StaffDAO staffDAO = new StaffDAO();
        String adminEmail = (String) session.getAttribute("adminEmail");
        request.setAttribute("admin", staffDAO.getStaffByStaffEmail(adminEmail));

        request.getRequestDispatcher("./view/customer-list-admin.jsp").forward(request, response);
    }

    private void sendToSettingList(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StaffDAO staffDAO = new StaffDAO();
        String adminEmail = (String) session.getAttribute("adminEmail");
        request.setAttribute("admin", staffDAO.getStaffByStaffEmail(adminEmail));

        request.getRequestDispatcher("./view/setting-list-admin.jsp").forward(request, response);
    }
}
