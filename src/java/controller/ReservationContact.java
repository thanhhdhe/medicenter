/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

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
import java.util.ArrayList;
import java.util.List;
import model.Children;
import model.Reservation;
import model.Service;
import model.Staff;
import model.User;

/**
 *
 * @author pc
 */
public class ReservationContact extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        // Get the user's email from the session
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        // Create a UserDAO instance to interact with the user data
        StaffDAO staffdao = new StaffDAO();
        Staff staff = staffdao.getStaffByStaffEmail(email);
        //get event
        String event = request.getParameter("event");
        ReservationDAO reservationdao = new ReservationDAO();
        if (event.equals("reservation-list")) {
            //get total page
            int endP = reservationdao.getTotalReservation();
            int endPage = endP / 10;
            //paging
            if (endP % 10 != 0) {
                endPage++; // if endP not divide by 10 so that endPage + 1
            }
            List<Reservation> listreservation = reservationdao.getReservationAllByPaging(1, null);
            request.setAttribute("endP", endPage);
            request.setAttribute("Reservation", listreservation);
            request.getRequestDispatcher("/view/reservation-manager-list.jsp").forward(request, response);
        } else if (event.equals("reservation-list-paging")) {
            //get parameter
            String page = request.getParameter("page");
            String sort = request.getParameter("sortstatus");
            System.out.println(sort);
            // get list reservation
            List<Reservation> listreservation = reservationdao.getReservationAllByPaging(Integer.parseInt(page), sort);
            //get information of user
            StaffDAO staffDAO = new StaffDAO();
            ServiceDAO serviceDAO = new ServiceDAO();
            UserDAO userdao = new UserDAO();
            ChildrenDAO childrenDAO = new ChildrenDAO();
            for (Reservation reservation : listreservation) {
                Service service = serviceDAO.getServiceByID(reservation.getServiceID() + "");
                User user = userdao.getUserByID(reservation.getUserID());
                Staff staffa = staffDAO.getStaffByStaffId(reservation.getStaffID());
                Children children = childrenDAO.getChildrenByChildrenId(reservation.getChildID() + "");
                List<Staff> stafflist = staffDAO.getStaffsBySlot(String.valueOf(reservation.getReservationDate()), reservation.getReservationSlot() + "");

                out.println("<tr>\n"
                        + "                                                    <td>" + reservation.getReservationID() + "</td>\n"
                        + "                                                    <td>" + service.getTitle() + "</td>\n"
                        + "                                                    <td>" + user.getFirstName() + user.getLastName() + "</td>\n"
                        + "                                                    <td>" + children.getChildName() + "</td>\n"
                        + "                                                    <td>" + reservation.getReservationDate() + "</td>\n"
                        + "                                                    <td>" + reservation.getReservationSlot() + "</td>\n"
                        + "<td>\n"
                        + "                                                    <div class=\"dropdown\">\n"
                        + "                                                        <button style=\"border: 0px; padding: 0px\" type=\"button\" id=\"dropdownMenuButton1\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">\n"
                        + "                                                            <span class=\"badge bg-primary\"  id=\"statusBadge-" + staffa.getStaffID() + "\">" + staffa.getStaffName() + " </span>\n"
                        + "                                                        </button>\n"
                        + "                                                        <ul class=\"dropdown-menu\" aria-labelledby=\"dropdownMenuLink\">\n");
                for (Staff varstaff : stafflist) {
                    out.println(
                            "                                                                <li><a class=\"dropdown-item status-change\" href=\"#\" onclick=\"changestatus(this, ${reservation.getReservationID()})\">" + varstaff.getStaffName() + "</a></li>\n");
                }
                out.println(
                        "                                                        </ul>\n"
                        + "\n"
                        + "                                                    </div>\n"
                        + "                                                </td>"
                        + "<td><div class=\"dropdown\">\n"
                        + "                                                        <button style=\"border: 0px ; padding: 0px;\" type=\"button\" id=\"dropdownMenuButton1\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">\n"
                        + "                                                            <span class=\"badge bg-primary\"  id=\"statusBadge-" + reservation.getReservationID() + "\">" + reservation.getStatus() + "</span>\n"
                        + "                                                        </button>\n"
                        + "                                                        <ul class=\"dropdown-menu\" aria-labelledby=\"dropdownMenuLink\">\n"
                        + "                                                            <li><a class=\"dropdown-item status-change\" href=\"#\" onclick=\"changestatus(this, " + reservation.getReservationID() + ")\">Cancel</a></li>\n"
                        + "                                                            <li><a class=\"dropdown-item status-change\" href=\"#\" onclick=\"changestatus(this, " + reservation.getReservationID() + ")\">Pending</a></li>\n"
                        + "                                                            <li><a class=\"dropdown-item status-change\" href=\"#\" onclick=\"changestatus(this, " + reservation.getReservationID() + ")\">waiting for examination</a></li>\n"
                        + "                                                            <li><a class=\"dropdown-item status-change\" href=\"#\" onclick=\"changestatus(this, " + reservation.getReservationID() + ")\">waiting for examination</a></li>\n"
                        + "                                                        </ul>\n"
                        + "                                                    </div> </td>"
                        + "                                                    <td>" + reservation.getCost() + "</td>\n"
                        + " </tr>");

            }

        } else if (event.equals("updatestatus")) {
            String status = request.getParameter("status");
            String reservationID = request.getParameter("reservationID");
            reservationdao.updateStatus(status, reservationID);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
