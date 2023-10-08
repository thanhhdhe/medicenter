/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import Database.ChildrenDAO;
import Database.MedicalExaminationDAO;
import Database.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import model.MedicalExamination;
import model.Staff;

/**
 *
 * @author Admin
 */
public class MedicalExaminationController extends HttpServlet {
   
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
            case "delete":
                deleteMedicalExamination(request, response);
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
        HttpSession session = request.getSession(true);
        String email = (String) session.getAttribute("email");
        StaffDAO staffDAO = new StaffDAO();
        Staff curStaff = staffDAO.getStaffByStaffEmail(email);
        boolean isManager = false;
        if(curStaff!=null){
            if(curStaff.getRole().equals("manager")) isManager=true;
        }
        
        switch(event){
            case "edit":
                editMedicalExamination(request, response);
                break;
            case "add-medical-examination":
                addMedicalExamination(request, response);
                break;
        }
    }
    
    protected void editMedicalExamination(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String id = request.getParameter("id");
        MedicalExaminationDAO medicalExaminationDAO = new MedicalExaminationDAO();
        MedicalExamination medicalExamination = medicalExaminationDAO.getMedicalExaminationsByID(id);
        String disease = request.getParameter("disease") + "";
        String examinationDate = request.getParameter("examinationDate") + "";
        String prescription = request.getParameter("prescription") + "";
        medicalExamination.setDisease(disease);
        medicalExamination.setExaminationDate(Date.valueOf(examinationDate));
        medicalExamination.setMedicalPrescription(prescription);
        medicalExaminationDAO.update(medicalExamination);
        request.getRequestDispatcher("./view/medical-examination.jsp").forward(request, response);
    } 

    protected void deleteMedicalExamination(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String id = request.getParameter("id");
        MedicalExaminationDAO medicalExaminationDAO = new MedicalExaminationDAO();
        medicalExaminationDAO.delete(id);
        request.getRequestDispatcher("./view/medical-examination.jsp").forward(request, response);
    } 
    
    protected void addMedicalExamination(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int childID = Integer.parseInt(request.getParameter("childID") + "");
        int staffID = Integer.parseInt(request.getParameter("staffID") + "");
        int serviceId = Integer.parseInt(request.getParameter("service") + "");
        String disease = request.getParameter("disease") + "";
        String prescription = request.getParameter("prescription") + "";
        ChildrenDAO childrenDAO = new ChildrenDAO();
        Date examinationDate = Date.valueOf(LocalDate.now());
        MedicalExamination medicalExamination = new MedicalExamination(childrenDAO.getChildrenByChildrenId(childID+"").getUser().getUserID(), childID, staffID, examinationDate, serviceId, prescription, disease);
        MedicalExaminationDAO medicalExaminationDAO = new MedicalExaminationDAO();
        medicalExaminationDAO.insert(medicalExamination);
        request.getRequestDispatcher("./view/medical-examination.jsp").forward(request, response);
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
