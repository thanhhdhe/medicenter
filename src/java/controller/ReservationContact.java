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
        //get parameter
        String servicetitle = request.getParameter("serviceTitle");
        String servicetype = request.getParameter("serviceType");
        //get event
        String event = request.getParameter("event");

        // Get the user's email from the session
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        // Create a UserDAO instance to interact with the user data
        UserDAO dao = new UserDAO();
        StaffDAO staffdao= new StaffDAO();
        Staff staff= staffdao.getStaffByStaffEmail(email);
        // Retrieve the user based on their email
//        User user = dao.getUser(email);
        //get list reservation by user id
        ReservationDAO rd = new ReservationDAO();
        List<Reservation> listreservation = rd.getReservationByUserID(1 + "");
        if (event == null) {
            request.setAttribute("Reservation", listreservation);
        } else if (event.equals("searchandfill")) {
            List<Reservation> listreservationsearchandfill = new ArrayList<>();
            if (servicetitle.equals("")) {
                listreservationsearchandfill = rd.getReservationSearchAndFillter(1 + "",
                        "", Integer.parseInt(servicetype));
                System.out.println("1");
            } else if (servicetype.equals("")) {
                listreservationsearchandfill = rd.getReservationSearchAndFillter(1 + "",
                        servicetitle, 0);
                System.out.println("2");
            } else {
                listreservationsearchandfill = rd.getReservationSearchAndFillter(1 + "",
                        servicetitle, Integer.parseInt(servicetype));
                System.out.println("3");
            }

            request.setAttribute("Reservation", listreservationsearchandfill);
        }
        float total = 0;
        for (Reservation list : listreservation) {
            total += list.getCost();
        }
        request.setAttribute("total", total);
//        request.setAttribute("user", user);

        request.getRequestDispatcher("/view/reservation-manager-list.jsp").forward(request, response);
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
