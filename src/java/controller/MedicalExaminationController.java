/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import Database.ChildrenDAO;
import Database.MedicalExaminationDAO;
import Database.ReservationDAO;
import Database.ServiceDAO;
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
            if (curStaff.getRole().equals("doctor") || curStaff.getRole().equals("nurse")) {
                isStaff = true;
            }
        }
        
        switch (event) {
            case "delete":
                if (!isManager && !isStaff) {
                    break;
                }
                deleteMedicalExamination(request, response);
                break;
            case "get-patient-of-staff":
                if (!isManager && !isStaff) {
                    break;
                }
                renderPatientOfStaff(request, response);
                break;
            case "get-medical-examination-page":
                if (!isManager && !isStaff) {
                    break;
                }
                renderPageMedicalExaminationOfStaff(request, response);
                break;
            case "get-medical-examination-page-manager":
                if (!isManager) {
                    break;
                }
                renderPageMedicalExamination(request, response);
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
            if (curStaff.getRole().equals("doctor") || curStaff.getRole().equals("nurse")) {
                isStaff = true;
            }
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
    
    protected void renderPageMedicalExaminationOfStaff(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Logger logger = Logger.getLogger(MedicalExaminationController.class.getName());
        String childName = request.getParameter("patientName");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String service = request.getParameter("service");
        HttpSession session = request.getSession(true);
        String email = (String) session.getAttribute("email");
//        logger.info("Giá trị của biến email là: " + email);
        StaffDAO staffDAO = new StaffDAO();
        Staff curStaff = staffDAO.getStaffByStaffEmail(email);
        String page = (String) request.getParameter("page");
        int pagination = Integer.parseInt(page.trim());
        MedicalExaminationDAO medicalExaminationDAO = new MedicalExaminationDAO();
        ChildrenDAO childrenDAO = new ChildrenDAO();
        ServiceDAO serviceDAO = new ServiceDAO();
        int numberOfRecord = medicalExaminationDAO.countFilterMedicalExaminationsOfStaff(curStaff.getStaffID() + "", childName,service,fromDate,toDate);
        int numberOfPage = (numberOfRecord + 9) / 10;
        
        // Generate the pagination HTML
        String paginationHtml = "";
        if (numberOfRecord <= 40) {
            if (numberOfRecord > 0) {
                for (int i = 1; i <= numberOfPage; i++) {
                    if (i == pagination) {
                        paginationHtml += "<li class=\"pagination-btn active\"><span>" + pagination + "</span></li>";
                    } else {
                        paginationHtml += "<li class=\"pagination-btn inactive\"><button onclick=\"handlePageChange(event,"+i+")\" data-page=\"" + i + "\" href=\"#\">" + i + "</a></li>";
                    }
                }
            }
        } else {
            
            if (pagination == 1) {
                paginationHtml += "<li class=\"pagination-btn active\"><span>1</span></li>"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\" data-page=\"2\" onclick=\"handlePageChange(event, 2)\">2</a></li>\n"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\" data-page=\"3\" onclick=\"handlePageChange(event, 3)\">3</a></li>\n"
                        + "<span>...</span>\n"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\" onclick=\"handlePageChange(event, " + numberOfPage + ")\" data-page=\"" + numberOfPage + "\">" + numberOfPage + "</a></li>\n"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\" onclick=\"handlePageChange(event, " + (pagination+1) + ")\">&gt;</a></li>";
            } else if (pagination > numberOfPage - 4) {
                paginationHtml += "<li class=\"pagination-btn inactive\" ><a href=\"#\" onclick=\"handlePageChange(event, " + (pagination-1) + ")\">&lt;</a></li>"
                        + "<span>...</span>\n";
                for (int i = numberOfPage - 3; i <= numberOfPage; i++) {
                    if (i == pagination) {
                        paginationHtml += "<li class=\"pagination-btn active\"><span>" + pagination + "</span></li>";
                    } else {
                        paginationHtml += "<li class=\"pagination-btn inactive\"><a onclick=\"handlePageChange(event, " + i + ")\" data-page=\"" + i + "\" href=\"#\">" + i + "</a></li>";
                    }
                }
                paginationHtml += "<li class=\"pagination-btn inactive\"><a href=\"#\" onclick=\"handlePageChange(event, " + (pagination+1) + ")\">&gt;</a></li>";
            } else {
                int pagination1 =pagination+1,pagination2=pagination+2;
                paginationHtml += "<li class=\"pagination-btn inactive\"><a href=\"#\" onclick=\"handlePageChange(event, " + (pagination-1) + ")\">&lt;</a></li>"
                        + "<li class=\"pagination-btn active\"><span>" + pagination + "</span></li>"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\" onclick=\"handlePageChange(event, " + pagination1 + ")\" data-page=\"" + (pagination+1) + "\">" + (int)(pagination+1) + "</a></li>\n"
                        + "<li class=\"pagination-btn inactive\"><a onclick=\"handlePageChange(event, " + pagination2 + ")\" href=\"#\" data-page=\"" + (pagination+2) + "\">" + (int)(pagination+2) + "</a></li>\n"
                        + "<span>...</span>\n"
                        + "<li class=\"pagination-btn inactive\"><a onclick=\"handlePageChange(event, " + numberOfPage + ")\" href=\"#\" data-page=\"" + numberOfPage + "\">" + numberOfPage + "</a></li>\n"
                        + "<li class=\"pagination-btn inactive\"><a onclick=\"handlePageChange(event, " + numberOfPage + ")\" href=\"#\" onclick=\"handlePageChange(event, " + (pagination+1) + ")\">&gt;</a></li>";
            }
            
        }
        // Add the pagination HTML to the response header
        response.addHeader("pagination", paginationHtml);
        
        List<MedicalExamination> listMedicalExamination = medicalExaminationDAO.getPageFilterMedicalExaminationsOfStaff(curStaff.getStaffID() + "",childName, pagination, 10,service,fromDate,toDate);
        if (listMedicalExamination != null) {
            for (MedicalExamination medicalExamination : listMedicalExamination) {
                out.print("<tr>\n"
                        + "    <th scope=\"row\">"+medicalExamination.getMedicalExaminationID()+"</th>\n"
                        + "    <td>\n"
                        + "        <div class=\"d-flex\">\n"
                        + "            <img class=\"rounded-circle object-cover me-3\" src=\""+childrenDAO.getChildrenByChildrenId(medicalExamination.getMchildrenID()+"").getImage()+"\" alt=\"alt\" width=\"30px\" height=\"30px\"/>\n"
                        + "            <div>"+childrenDAO.getChildrenByChildrenId(medicalExamination.getMchildrenID()+"").getChildName()+"</div>\n"
                        + "        </div>\n"
                        + "    </td>\n"
                        + "    <td>"+childrenDAO.getChildrenByChildrenId(medicalExamination.getMchildrenID()+"").getBirthday()+"</td>\n"
                        + "    <td>"+serviceDAO.getServiceByID(medicalExamination.getMuserID()+"").getTitle()+"</td>\n"
                        + "    <td>"+medicalExamination.getExaminationDate()+"</td>\n"
                        + "    <td>"+medicalExamination.getDisease()+"</td>\n"
                        + "    <td>\n"
                        + "        <div class=\"d-flex\">\n"
                        + "            <a class=\"me-3\" href=\"staff?event=send-to-edit&id="+medicalExamination.getMedicalExaminationID()+"\"><i class=\"fas fa-pencil-alt ms-text-primary\"></i></a>\n"
                        + "            <a href=\"medical-examination?event=delete&id="+medicalExamination.getMedicalExaminationID()+"\" style=\"color: #d9534f;\"><i class=\"far fa-trash-alt ms-text-danger\"></i></a>\n"
                        + "        </div>\n"
                        + "    </td>\n"
                        + "</tr>");
            }
        }
        
    }
    
    protected void renderPageMedicalExamination(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Logger logger = Logger.getLogger(MedicalExaminationController.class.getName());
        String childName = request.getParameter("patientName");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String service = request.getParameter("service");
        HttpSession session = request.getSession(true);
        String email = (String) session.getAttribute("email");
//        logger.info("Giá trị của biến email là: " + email);
        StaffDAO staffDAO = new StaffDAO();
        Staff curStaff = staffDAO.getStaffByStaffEmail(email);
        String page = (String) request.getParameter("page");
        int pagination = Integer.parseInt(page.trim());
        MedicalExaminationDAO medicalExaminationDAO = new MedicalExaminationDAO();
        ChildrenDAO childrenDAO = new ChildrenDAO();
        ServiceDAO serviceDAO = new ServiceDAO();
        int numberOfRecord = medicalExaminationDAO.countFilterMedicalExaminations(childName,service,fromDate,toDate);
        int numberOfPage = (numberOfRecord + 9) / 10;
        
        // Generate the pagination HTML
        String paginationHtml = "";
        if (numberOfRecord <= 40) {
            if (numberOfRecord > 0) {
                for (int i = 1; i <= numberOfPage; i++) {
                    if (i == pagination) {
                        paginationHtml += "<li class=\"pagination-btn active\"><span>" + pagination + "</span></li>";
                    } else {
                        paginationHtml += "<li class=\"pagination-btn inactive\"><button onclick=\"handlePageChangex("+i+")\" data-page=\"" + i + "\" href=\"#\">" + i + "</a></li>";
                    }
                }
            }
        } else {
            if (pagination == 1) {
                paginationHtml += "<li class=\"pagination-btn active\"><span>1</span></li>"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\" data-page=\"2\" onclick=\"handlePageChange(event, 2)\">2</a></li>\n"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\" data-page=\"3\" onclick=\"handlePageChange(event, 3)\">3</a></li>\n"
                        + "<span>...</span>\n"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\" onclick=\"handlePageChange(event, " + numberOfPage + ")\" data-page=\"" + numberOfPage + "\">" + numberOfPage + "</a></li>\n"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\" onclick=\"handlePageChange(event, " + (pagination+1) + ")\">&gt;</a></li>";
            } else if (pagination > numberOfPage - 4) {
                paginationHtml += "<li class=\"pagination-btn inactive\" ><a href=\"#\" onclick=\"handlePageChange(event, " + (pagination-1) + ")\">&lt;</a></li>"
                        + "<span>...</span>\n";
                for (int i = numberOfPage - 3; i <= numberOfPage; i++) {
                    if (i == pagination) {
                        paginationHtml += "<li class=\"pagination-btn active\"><span>" + pagination + "</span></li>";
                    } else {
                        paginationHtml += "<li class=\"pagination-btn inactive\"><a onclick=\"handlePageChange(event, " + i + ")\" data-page=\"" + i + "\" href=\"#\">" + i + "</a></li>";
                    }
                }
                paginationHtml += "<li class=\"pagination-btn inactive\"><a href=\"#\" onclick=\"handlePageChange(event, " + (pagination+1) + ")\">&gt;</a></li>";
            } else {
                int pagination1 =pagination+1,pagination2=pagination+2;
                paginationHtml += "<li class=\"pagination-btn inactive\"><a href=\"#\" onclick=\"handlePageChange(event, " + (pagination-1) + ")\">&lt;</a></li>"
                        + "<li class=\"pagination-btn active\"><span>" + pagination + "</span></li>"
                        + "<li class=\"pagination-btn inactive\"><a href=\"#\" onclick=\"handlePageChange(event, " + pagination1 + ")\" data-page=\"" + (pagination+1) + "\">" + (int)(pagination+1) + "</a></li>\n"
                        + "<li class=\"pagination-btn inactive\"><a onclick=\"handlePageChange(event, " + pagination2 + ")\" href=\"#\" data-page=\"" + (pagination+2) + "\">" + (int)(pagination+2) + "</a></li>\n"
                        + "<span>...</span>\n"
                        + "<li class=\"pagination-btn inactive\"><a onclick=\"handlePageChange(event, " + numberOfPage + ")\" href=\"#\" data-page=\"" + numberOfPage + "\">" + numberOfPage + "</a></li>\n"
                        + "<li class=\"pagination-btn inactive\"><a onclick=\"handlePageChange(event, " + numberOfPage + ")\" href=\"#\" onclick=\"handlePageChange(event, " + (pagination+1) + ")\">&gt;</a></li>";
            }
            
            
        }
        // Add the pagination HTML to the response header
        response.addHeader("pagination", paginationHtml);
        
        List<MedicalExamination> listMedicalExamination = medicalExaminationDAO.getPageFilterMedicalExaminations(childName, pagination, 10,service,fromDate,toDate);
        if (listMedicalExamination != null) {
            for (MedicalExamination medicalExamination : listMedicalExamination) {
                out.print("<tr>\n"
                        + "    <th scope=\"row\">"+medicalExamination.getMedicalExaminationID()+"</th>\n"
                        + "    <td>\n"
                        + "        <div class=\"d-flex\">\n"
                        + "            <img class=\"rounded-circle object-cover me-3\" src=\""+childrenDAO.getChildrenByChildrenId(medicalExamination.getMchildrenID()+"").getImage()+"\" alt=\"alt\" width=\"30px\" height=\"30px\"/>\n"
                        + "            <div>"+childrenDAO.getChildrenByChildrenId(medicalExamination.getMchildrenID()+"").getChildName()+"</div>\n"
                        + "        </div>\n"
                        + "    </td>\n"
                        + "    <td>"+childrenDAO.getChildrenByChildrenId(medicalExamination.getMchildrenID()+"").getBirthday()+"</td>\n"
                        + "    <td>"+serviceDAO.getServiceByID(medicalExamination.getMuserID()+"").getTitle()+"</td>\n"
                        + "    <td>"+medicalExamination.getExaminationDate()+"</td>\n"
                        + "    <td>"+medicalExamination.getDisease()+"</td>\n"
                        + "    <td>\n"
                        + "        <div class=\"d-flex\">\n"
                        + "            <a class=\"me-3\" href=\"staff?event=send-to-edit&id="+medicalExamination.getMedicalExaminationID()+"\"><i class=\"fas fa-pencil-alt ms-text-primary\"></i></a>\n"
                        + "            <a href=\"medical-examination?event=delete&id="+medicalExamination.getMedicalExaminationID()+"\" style=\"color: #d9534f;\"><i class=\"far fa-trash-alt ms-text-danger\"></i></a>\n"
                        + "        </div>\n"
                        + "    </td>\n"
                        + "</tr>");
            }
        }
        
    }
    
    protected void renderPatientOfStaff(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String childName = request.getParameter("patientName");
        HttpSession session = request.getSession(true);
        String email = (String) session.getAttribute("email");
        StaffDAO staffDAO = new StaffDAO();
        Staff curStaff = staffDAO.getStaffByStaffEmail(email);
        String page = (String) request.getParameter("page");
        int pagination = Integer.parseInt(page.trim());
        ReservationDAO reservationDAO = new ReservationDAO();
        MedicalExaminationDAO medicalExaminationDAO = new MedicalExaminationDAO();
        ChildrenDAO childrenDAO = new ChildrenDAO();
        int numberOfPage = (medicalExaminationDAO.countListChildrenIDByStaff(childName, curStaff.getStaffID() + "") + 9) / 10;
        int numberOfRecord = medicalExaminationDAO.countListChildrenIDByStaff(childName, curStaff.getStaffID() + "");
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
        
        List<Integer> childrenIDList = medicalExaminationDAO.getListChildrenIDByStaff(childName, curStaff.getStaffID() + "", pagination, 10);
        if (childrenIDList != null) {
            for (Integer integer : childrenIDList) {
                Children children = childrenDAO.getChildrenByChildrenId(integer + "");
                out.print("<tr>\n"
                        + "<th scope=\"row\">" + children.getChildID() + "</th>\n"
                        + "<td class=\"d-flex align-items-center\">\n"
                        + "    <img class=\"rounded-circle object-cover me-3\" src=\"" + children.getImage() + "\" alt=\"alt\" width=\"30px\" height=\"30px\"/>\n"
                        + "    <div>" + children.getChildName() + "</div>\n"
                        + "</td>\n"
                        + "<td>" + children.getBirthday() + "</td>\n"
                        + "<td>" + children.getStatus() + "</td>\n"
                        + "<td>" + children.getGender() + "</td>\n"
                        + "<td><a href=\"staff?event=send-to-history-examination&childid=" + children.getChildID() + "\"><i class=\"fas fa-pencil-alt ms-text-primary\"></i></a></td>\n"
                        + "</tr>");
                
            }
        }
        
    }
    
    protected void addMedicalExamination(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int childID = Integer.parseInt(request.getParameter("childID") + "");
        int staffID = Integer.parseInt(request.getParameter("staffID") + "");
        PrintWriter out = response.getWriter();
        int serviceId = Integer.parseInt(request.getParameter("service") + "");
        String reserdId = request.getParameter("reserdId") + "";
        if (!reserdId.equals("null")) {
            try {
                ReservationDAO reservationDAO = new ReservationDAO();
                Reservation reservation = reservationDAO.getReservationByID(Integer.parseInt(reserdId));
                reservation.setStatus("done");
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
