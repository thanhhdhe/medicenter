/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import Database.ChildrenDAO;
import Database.MedicalExaminationDAO;
import Database.ReservationDAO;
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
import java.util.List;
import java.util.logging.Logger;
import model.Children;
import model.MedicalExamination;
import model.Reservation;
import model.Staff;

/**
 *
 * @author Admin
 */
public class MedicalExaminationController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
        String event = request.getParameter("event");
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
            if(curStaff.getRole().equals("doctor")||curStaff.getRole().equals("nurse")) isStaff=true;
        }
        
        switch (event) {
            case "delete":
                if (!isManager && !isStaff) {
                    break;
                }
                deleteMedicalExamination(request, response);
                break;
            case "patient-of-staff":
                if (!isManager && !isStaff) {
                    break;
                }
                renderPatientOfStaff(request, response);
                break;
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
        String event = request.getParameter("event");
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
            if(curStaff.getRole().equals("doctor")||curStaff.getRole().equals("nurse")) isStaff=true;
        }
        
        switch (event) {
            case "edit":
                if (!isManager && !isStaff) {
                    break;
                }
                editMedicalExamination(request, response);
                break;
            case "add-medical-examination":
                if (!isManager && !isStaff) {
                    break;
                }
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
    
    protected void renderPatientOfStaff(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Logger logger = Logger.getLogger(MedicalExaminationController.class.getName());
        String childName = request.getParameter("patientName");
        HttpSession session = request.getSession(true);
        String email = (String) session.getAttribute("email");
        logger.info("Giá trị của biến email là: " + email);
        StaffDAO staffDAO = new StaffDAO();
        Staff curStaff = staffDAO.getStaffByStaffEmail(email);
        String page = (String) request.getParameter("page");
        int pagination = Integer.parseInt(page.trim());
        ReservationDAO reservationDAO = new ReservationDAO();
        ChildrenDAO childrenDAO = new ChildrenDAO();
            logger.info("*****"+childName+"   "+curStaff.getStaffID());       
        int numberOfPage = (reservationDAO.countListChildrenIDByUserAndStaff(childName, curStaff.getStaffID() + "") + 9) / 10;
        int numberOfRecord = reservationDAO.countListChildrenIDByUserAndStaff(childName, curStaff.getStaffID() + "");
        // Generate the pagination HTML
        String paginationHtml = "";
        if (numberOfRecord <= 40) {
            if (numberOfRecord > 0) {
                for (int i = 1; i <= numberOfPage; i++) {
                    if (i == pagination) {
                        paginationHtml += "<li class=\"pagination-btn active\"><span>" + pagination + "</span></li>";
                    } else {
                        paginationHtml += "<li class=\"pagination-btn inactive\"><a data-page=\"" + i + "\" href=\"#\">" + i + "</a></li>";
                    }
                }
            }
        } else {
            
            if (pagination == 1) {
                paginationHtml += "<li class=\"pagination-btn active\"><span>1</span></li>"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\" data-page=\"2\">2</a></li>\n"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\" data-page=\"3\">3</a></li>\n"
                        + "<span>...</span>\n"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\" data-page=\"" + numberOfPage + "\">" + numberOfPage + "</a></li>\n"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\">&gt;</a></li>";
            } else if (pagination > numberOfPage - 4) {
                paginationHtml += "<li class=\"pagination-btn inactive\"><a href=\"#\">&lt;</a></li>"
                        + "<span>...</span>\n";
                for (int i = numberOfPage - 3; i <= numberOfPage; i++) {
                    if (i == pagination) {
                        paginationHtml += "<li class=\"pagination-btn active\"><span>" + pagination + "</span></li>";
                    } else {
                        paginationHtml += "<li class=\"pagination-btn inactive\"><a data-page=\"" + i + "\" href=\"#\">" + i + "</a></li>";
                    }
                }
                paginationHtml += "<li class=\"pagination-btn inactive\"><a href=\"#\">&gt;</a></li>";
            } else {
                paginationHtml += "<li class=\"pagination-btn inactive\"><a href=\"#\">&lt;</a></li>"
                        + "<li class=\"pagination-btn active\"><span>" + pagination + "</span></li>"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\" data-page=\"" + pagination + "\">" + pagination + "</a></li>\n"
                        + "                                    <li class=\"pagination-btn inactive\"><a href=\"#\" data-page=\"" + pagination + "\">" + pagination + "</a></li>\n"
                        + "<span>...</span>\n"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\" data-page=\"" + numberOfPage + "\">" + numberOfPage + "</a></li>\n"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\">&gt;</a></li>";
            }
            
            
        }
        // Add the pagination HTML to the response header
        response.addHeader("pagination", paginationHtml);
        
        
            List<Integer> childrenIDList = reservationDAO.getListChildrenIDByUserAndStaff(childName, curStaff.getStaffID() + "", 1, 10);
            if (childrenIDList != null) {
                for (Integer integer : childrenIDList) {
                    Children children = childrenDAO.getChildrenByChildrenId(integer + "");
                    out.print("<tr>\n"
                            + "<th scope=\"row\">"+children.getChildID()+"</th>\n"
                            + "<td class=\"d-flex align-items-center\">\n"
                            + "    <img class=\"rounded-circle object-cover me-3\" src=\""+children.getImage()+"\" alt=\"alt\" width=\"30px\" height=\"30px\"/>\n"
                            + "    <div>"+children.getChildName()+"</div>\n"
                            + "</td>\n"
                            + "<td>"+children.getBirthday()+"</td>\n"
                            + "<td>"+children.getStatus()+"</td>\n"
                            + "<td>"+children.getGender()+"</td>\n"
                            + "<td><a href=\"staff?event=send-to-history-examination&childid="+children.getChildID()+"\"><i class=\"fas fa-pencil-alt ms-text-primary\"></i></a></td>\n"
                            + "</tr>");
                    
                }
            }
        
    }
    
    protected void addMedicalExamination(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int childID = Integer.parseInt(request.getParameter("childID") + "");
        int staffID = Integer.parseInt(request.getParameter("staffID") + "");
        int serviceId = Integer.parseInt(request.getParameter("service") + "");
        String reserdId = request.getParameter("reserdId") + "";
        if(!reserdId.equals("null")){
            try {
                ReservationDAO reservationDAO = new ReservationDAO();
                Reservation reservation = reservationDAO.getReservationByID(Integer.parseInt(reserdId));
                reservation.setStatus("waiting for examination");
                reservationDAO.update(reservation);
            } catch (Exception e) {
            }
        }
        String disease = request.getParameter("disease") + "";
        String prescription = request.getParameter("prescription") + "";
        ChildrenDAO childrenDAO = new ChildrenDAO();
        Date examinationDate = Date.valueOf(LocalDate.now());
        MedicalExamination medicalExamination = new MedicalExamination(childrenDAO.getChildrenByChildrenId(childID + "").getUser().getUserID(), childID, staffID, examinationDate, serviceId, prescription, disease);
        MedicalExaminationDAO medicalExaminationDAO = new MedicalExaminationDAO();
        medicalExaminationDAO.insert(medicalExamination);
        request.getRequestDispatcher("./view/medical-examination.jsp").forward(request, response);
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
