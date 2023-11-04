/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import Database.CategoryServiceDAO;
import Database.ChildrenDAO;
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
import model.CategoryService;
import model.Children;
import model.Reservation;
import model.Service;
import model.Staff;
import model.User;

/**
 *
 * @author hbich
 */
public class ReservationInformationController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            String id = (String) request.getParameter("id");
            String action = (String) request.getParameter("action");
            ReservationDAO reservationDAO = new ReservationDAO();

            // Update the database to cancel the pending reservation exceeds 5 minutes
            reservationDAO.updateDatabase();

            int ReservationID = -1;
            if (action == null) {
                try {
                    // ReservationID, CreatedDate, ReservationDate, Cost, Status
                    ReservationID = Integer.parseInt(id);
                    Reservation reservation = reservationDAO.getReservationByID(ReservationID);
                    request.setAttribute("reservation", reservation);

                    // Prevent others user see the reservation
                    HttpSession session = request.getSession(true);
                    String email = (String) session.getAttribute("email");
                    UserDAO userDAO = new UserDAO();
                    User user = userDAO.getUser(email);
                    if (reservation.getUserID() != user.getUserID()) {
                        throw new Exception();
                    }

                    // Children name, gender, email, mobile
                    ChildrenDAO cdao = new ChildrenDAO();
                    Children children = cdao.getChildrenByChildrenId(Integer.toString(reservation.getChildID()));
                    request.setAttribute("children", children);

                    // Service thumbnail, name, brief info, price
                    ServiceDAO sdao = new ServiceDAO();
                    Service service = sdao.getServiceByID(Integer.toString(reservation.getServiceID()));
                    request.setAttribute("service", service);

                    request.setAttribute("user", user);

                    request.getRequestDispatcher("/view/reservationinformation.jsp").forward(request, response);

                } catch (Exception e) {
                    response.sendRedirect("home");
                }
            } else if (action.equals("cancel")) {
                ReservationID = Integer.parseInt(id);
                Reservation reservation = reservationDAO.getReservationByID(ReservationID);
                if (!reservation.getStatus().equals("done")) {
                    // Get the one day after current date
                    Date compareDate = new Date(System.currentTimeMillis() + (2 * 24 * 60 * 60 * 1000));
                    Date reservationDate = reservation.getReservationDate();
                    // Check two date to satisfy the business rule
                    if (reservationDate.after(compareDate) || reservationDate.equals(compareDate)) {
                        // Cancel reservation
                        reservation.setStatus("cancel");
                        reservationDAO.update(reservation);
                        response.getWriter().write("success");
                    } else {
                        response.getWriter().write("fail");
                    }

                }
            } else if (action.equals("confirm")) {
                int reserID = Integer.parseInt(request.getParameter("reservationID"));
                Reservation reservation = reservationDAO.getReservationByID(reserID);
                ServiceDAO serviceDAO = new ServiceDAO();
                Service service = serviceDAO.getServiceByID(String.valueOf(reservation.getServiceID()));
                StaffDAO staffDAO = new StaffDAO();
                Staff doctor = staffDAO.getStaffByStaffId(reservation.getStaffID());
                ChildrenDAO childrenDAO = new ChildrenDAO();
                Children children = childrenDAO.getChildrenByChildrenId(String.valueOf(reservation.getChildID()));
                CategoryServiceDAO cateDAO = new CategoryServiceDAO();
                CategoryService cate = cateDAO.getCategoryServiceByID(String.valueOf(service.getServiceID()));
                request.setAttribute("reservation", reservation);
                request.setAttribute("service", service);
                request.setAttribute("doctor", doctor);
                request.setAttribute("children", children);
                request.setAttribute("cate", cate);
                request.getRequestDispatcher("/view/confirm-reservation.jsp").forward(request, response);

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
