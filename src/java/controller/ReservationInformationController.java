/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import Database.ChildrenDAO;
import Database.ReservationDAO;
import Database.ServiceDAO;
import Database.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Children;
import model.Reservation;
import model.Service;
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
            ReservationDAO rdao = new ReservationDAO();
            int ReservationID = -1;
            if (action == null) {
                try {
                    // ReservationID, CreatedDate, ReservationDate, Cost, Status
                    ReservationID = Integer.parseInt(id);
                    Reservation reservation = rdao.getReservationByID(ReservationID);
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

                    request.getRequestDispatcher("/view/reservationinformation.jsp").forward(request, response);

                } catch (Exception e) {
                    response.sendRedirect("home");
                }
            } else {
                
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
