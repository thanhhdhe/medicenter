/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import Database.StaffDAO;
import Database.StaffScheduleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Staff;
import model.StaffSchedule;

/**
 *
 * @author Admin
 */
public class StaffScheduleController extends HttpServlet {

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
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StaffScheduleController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StaffScheduleController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        String action = request.getParameter("action");
        HttpSession session = request.getSession(true);
        String email = (String) session.getAttribute("email");
        StaffDAO staffDAO = new StaffDAO();
        Staff curStaff = staffDAO.getStaffByStaffEmail(email);
        boolean isManager = false;
        boolean isStaff = false;
        if (curStaff != null) {
            if (curStaff.getRole().equals("manager")) {
                isManager = true;
            }
            if (curStaff.getRole().equals("doctor") || curStaff.getRole().equals("nurse")) {
                isStaff = true;
            }
        }
        if (action.equals("send-to-manage")) {
            if (!isManager) {
                request.getRequestDispatcher("./view/403-forbidden.jsp").forward(request, response);
                return;
            }
            StaffScheduleDAO staffScheduleDAO = new StaffScheduleDAO();
            String page = (request.getParameter("page") + "").equals("null") ? "1" : (request.getParameter("page") + "").trim();
            List<StaffSchedule> lists = staffScheduleDAO.getStaffUnconfirmSchedules(Integer.parseInt(page), 10);
            request.setAttribute("page", page);
            request.setAttribute("lists", lists);
            request.setAttribute("page", page);
            request.getRequestDispatcher("./view/schedule-manage.jsp").forward(request, response);
        }else if (action.equals("confirm-schedule")){
            if (!isManager) {
                request.getRequestDispatcher("./view/403-forbidden.jsp").forward(request, response);
                return;
            }
            StaffScheduleDAO staffScheduleDAO = new StaffScheduleDAO();
            String page = (request.getParameter("page") + "").equals("null") ? "1" : (request.getParameter("page") + "").trim();
            int id = Integer.parseInt(request.getParameter("id") + "");
            StaffSchedule staffSchedule = staffScheduleDAO.getStaffScheduleByID(id);
            staffSchedule.setStatus("confirm");
            staffScheduleDAO.updateStaffSchedule(staffSchedule);
            List<StaffSchedule> lists = staffScheduleDAO.getStaffUnconfirmSchedules(Integer.parseInt(page), 10);
            request.setAttribute("lists", lists);
            request.setAttribute("page", page);
            request.getRequestDispatcher("./view/schedule-manage.jsp").forward(request, response);
        }else if (action.equals("reject-schedule")){
            if (!isManager) {
                request.getRequestDispatcher("./view/403-forbidden.jsp").forward(request, response);
                return;
            }
            StaffScheduleDAO staffScheduleDAO = new StaffScheduleDAO();
            String page = (request.getParameter("page") + "").equals("null") ? "1" : (request.getParameter("page") + "").trim();
            int id = Integer.parseInt(request.getParameter("id") + "");
            StaffSchedule staffSchedule = staffScheduleDAO.getStaffScheduleByID(id);
            staffSchedule.setStatus("reject");
            staffScheduleDAO.updateStaffSchedule(staffSchedule);
            List<StaffSchedule> lists = staffScheduleDAO.getStaffUnconfirmSchedules(Integer.parseInt(page), 10);
            request.setAttribute("lists", lists);
            request.getRequestDispatcher("./view/schedule-manage.jsp").forward(request, response);
        }

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
