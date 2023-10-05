/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import Database.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Staff;

/**
 *
 * @author Admin
 */
public class StaffController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String event = request.getParameter("event");
        HttpSession session = request.getSession(true);
        String email = (String) session.getAttribute("email");
        StaffDAO staffDAO = new StaffDAO();
        Staff curStaff = staffDAO.getStaffByStaffEmail(email);
        boolean isManager = false;
        if(curStaff!=null){
            if(curStaff.getRole().equals("manager")) isManager=true;
        }
        
        switch(event){
            case "sent-to-home":
                request.getRequestDispatcher("./view/staff-dashboard.jsp").forward(request, response);
                break;
            case "sent-to-login":
                request.getRequestDispatcher("./view/login-staff.jsp").forward(request, response);
                break;
            case "send-to-medical-examination":
                request.getRequestDispatcher("./view/medical-examination.jsp").forward(request, response);
                break;
            case "send-to-edit":
                String id = request.getParameter("id");
                request.setAttribute(id, id);
                request.getRequestDispatcher("./view/edit-medical-examination.jsp").forward(request, response);
                break;
            case "send-to-feedback":
                request.getRequestDispatcher("feedback").forward(request, response);
                break;
            case "send-to-children-list":
                request.getRequestDispatcher("./view/my-patient-list.jsp").forward(request, response);
                break;
            case "send-to-history-examination":
                String childId = request.getParameter("childid");
                request.setAttribute("childId", childId);
                request.getRequestDispatcher("./view/add-medical-examination.jsp").forward(request, response);
                break;
            
        }
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String event = request.getParameter("event");
        switch(event){
            case "login":
                login(request, response);
                break;
        }
    }
    
    protected void login(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String email = (String) request.getParameter("email");
        String password = (String) request.getParameter("pass");
        StaffDAO staffDAO = new StaffDAO();
        Staff staff = staffDAO.getStaffByStaffEmail(email);
        if(staff==null){
            request.setAttribute("err", "Incorrect username or password!");
            request.getRequestDispatcher("./view/login-staff.jsp").forward(request, response);
        }else if(!staff.getPassword().equals(password)){
            request.setAttribute("err", "Incorrect username or password!");
            request.getRequestDispatcher("./view/login-staff.jsp").forward(request, response);
        }else{
            HttpSession session = request.getSession(true);
            session.setAttribute("email", email);
            request.getRequestDispatcher("./view/staff-dashboard.jsp").forward(request, response);
        }
    } 
    
    

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
