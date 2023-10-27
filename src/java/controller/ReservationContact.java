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
import model.Mail;
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
                        + "                                                            <span class=\"badge bg-primary\"  id=\"doctorBadge-" + reservation.getReservationID() + "\">" + staffa.getStaffName() + " </span>\n"
                        + "                                                        </button>\n"
                        + "                                                        <ul class=\"dropdown-menu\" aria-labelledby=\"dropdownMenuLink\">\n");
                for (Staff varstaff : stafflist) {
                    out.println(
                            "                                                                <li><a class=\"dropdown-item status-change\" href=\"#\" onclick=\"changeDoctor(this," + varstaff.getStaffID() + " , " + reservation.getReservationID() + ")\">" + varstaff.getStaffName() + "</a></li>\n");
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
                        + "                                                            <li><a class=\"dropdown-item status-change\" href=\"#\" onclick=\"changestatus(this, " + reservation.getReservationID() + ")\">awaiting confirmation</a></li>\n"
                        + "                                                            <li><a class=\"dropdown-item status-change\" href=\"#\" onclick=\"changestatus(this, " + reservation.getReservationID() + ")\">done</a></li>\n"
                        + "                                                        </ul>\n"
                        + "                                                    </div> </td>"
                        + "                                                    <td>" + reservation.getCost() + "</td>\n"
                        + "                                                    <td><a href=\"staff?event=send-to-reservation-manager-detail&reserdid=" + reservation.getReservationID() + "\"><img src=\"resources/img/icon/detail.png\" alt=\"alt\" width=\"25px\"/></a></td>"
                        + " </tr>");

            }

        } else if (event.equals("updatestatus")) {
            //get information
            String status = request.getParameter("status");
            String reservationID = request.getParameter("reservationID");
            // check for send mail
            if (status.equals("done")) {
                UserDAO userdao = new UserDAO();
                User user = userdao.getUserByID(reservationdao.getReservationByID(Integer.parseInt(reservationID)).getUserID());
                Mail.sendEmail(user.getEmail(), "THANK TO USE SERVICE", "Thank you for using our service\n"
                        + "Please give us feedback about the service by clicking on feedback in the header on the homepage on the website");
            }

            reservationdao.updateStatus(status, reservationID);
        } else if (event.equals("updatedoctor")) {
            String doctorID = request.getParameter("doctorID");
            String reservationID = request.getParameter("reservationID");
            reservationdao.updateDoctor(doctorID, reservationID);
        } else if (event.equals("fillter")) {
            //get parameter
            String page = request.getParameter("page");
            System.out.println(page);
            String staffid = request.getParameter("staffId");
            System.out.println(staffid);
            String name = (request.getParameter("name")==null)?"":request.getParameter("name");
            //get action
            List<Reservation> listreservation = new ArrayList<>();
            String action = request.getParameter("action");
            if(action.equals("fillterdoctor")){
                // get list reservation
                listreservation = reservationdao.getReservationAllByPagingFill(Integer.parseInt(page), staffid);
            } else{
                listreservation = reservationdao.getReservationAllBySearch(Integer.parseInt(page), name);
            }
                
            
            //get information of user
            StaffDAO staffDAO = new StaffDAO();
            ServiceDAO serviceDAO = new ServiceDAO();
            UserDAO userdao = new UserDAO();
            ChildrenDAO childrenDAO = new ChildrenDAO();
            out.print("<table class=\"table table-striped table-hover\">\n"
                    + "                                    <thead class=\"text-light\" style=\"background: #1977cc;\">\n"
                    + "                                        <tr>\n"
                    + "                                            <th scope=\"col\">ID</th>\n"
                    + "                                            <th scope=\"col\">Service Name</th>\n"
                    + "                                            <th scope=\"col\">Full name</th>\n"
                    + "                                            <th scope=\"col\">Children</th>\n"
                    + "                                            <th scope=\"col\">Reservation Date</th>\n"
                    + "                                            <th scope=\"col\">Slot</th>\n"
                    + "                                            <th scope=\"col\">Doctor</th>\n"
                    + "                                            <th scope=\"col\">Status</th>\n"
                    + "                                            <th scope=\"col\">Cost</th>\n"
                    + "                                            <th scope=\"col\">Detail</th>\n"
                    + "\n"
                    + "                                        </tr>\n"
                    + "                                    </thead>\n"
                    + "\n"
                    + "                                    <tbody>");
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
                        + "                                                            <span class=\"badge bg-primary\"  id=\"doctorBadge-" + reservation.getReservationID() + "\">" + staffa.getStaffName() + " </span>\n"
                        + "                                                        </button>\n"
                        + "                                                        <ul class=\"dropdown-menu\" aria-labelledby=\"dropdownMenuLink\">\n");
                for (Staff varstaff : stafflist) {
                    out.println(
                            "                                                                <li><a class=\"dropdown-item status-change\" href=\"#\" onclick=\"changeDoctor(this," + varstaff.getStaffID() + " ," + reservation.getReservationID() + ")\">" + varstaff.getStaffName() + "</a></li>\n");
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
                        + "                                                    <td><a href=\"staff?event=send-to-reservation-manager-detail&reserdid=" + reservation.getReservationID() + "\"><img src=\"resources/img/icon/detail.png\" alt=\"alt\" width=\"25px\"/></a></td>"
                        + " </tr>");

            }
            out.println( " </tbody> \n" +
"                                </table>");
            String pagehtml = "<div class=\"d-flex w-100 justify-content-center mb-5\">";
            int length= 0;
            String fillter = "";
            if(action.equals("fillterdoctor")){
                // get list reservation
                length = reservationdao.getTotalReservationByFillter(staffid);
                fillter= action;
            } else{
                length = reservationdao.getTotalReservationBySearch(name);
                fillter = action;
            }
            
            for (int i = 1; i <= (length + 9) / 10; i++) {
                if (i == Integer.parseInt(page)) {
                    pagehtml += "<span style=\"border: 0px; border-radius: 5px; background-color: #6994eb\" class=\"btn pagination-btn ms-2 active\" data-page=\"" + i + "\" onclick=\"paging(" + i + ",'"+fillter+"')\">" + i + "</span>\n";
                } else {
                    pagehtml += "<button style=\"border: 0px; border-radius: 5px; background-color: #6994eb\" class=\"btn pagination-btn ms-2 inactive\" data-page=\"" + i + "\" onclick=\"paging(" + i + ",'"+fillter+"')\">" + i + "</button>\n";
                }
            }
            pagehtml += "</div> ";
            out.println(pagehtml);
            response.addHeader("pagination", "");
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
