/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import Database.StaffScheduleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.text.SimpleDateFormat;
import model.StaffSchedule;

/**
 *
 * @author hbich
 */
public class StaffManageSchedule extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        StaffScheduleDAO staffScheduleDAO = new StaffScheduleDAO();
        SimpleDateFormat workDayFormat = new SimpleDateFormat("dd/MM/yyyy");

        try ( PrintWriter out = response.getWriter()) {
            String event = (String) request.getParameter("event");
            switch (event) {
                case "delete": {
                    String scheduleID = (String) request.getParameter("id");
                    if (staffScheduleDAO.getSchedule(scheduleID) != null) {
                        staffScheduleDAO.deleteSchedule(scheduleID);
                    }
                    response.sendRedirect("staff?event=send-to-schedules");
                    break;
                }
                case "add": {
                    String staffID = (String) request.getParameter("staffID");
                    Date workDay = Date.valueOf(request.getParameter("workDay"));
                    int workSlot = Integer.parseInt(request.getParameter("workSlot"));
                    StaffSchedule staffSchedule = new StaffSchedule();
                    staffSchedule.setSlot(workSlot);
                    staffSchedule.setStaffID(Integer.parseInt(staffID));
                    staffSchedule.setWorkday(workDay);
                    // validate the schedule 
                    Date compareDate = new Date(System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000));

                    if (staffScheduleDAO.getScheduleByWorkDate(workDay, workSlot, staffID) != null) {
                        request.setAttribute("staffSchedule", null);
                        request.setAttribute("action", "add");
                        request.setAttribute("errorMessage", "Cannot add new schedule because of duplicate schedule");
                        request.getRequestDispatcher("./view/edit-schedule.jsp").forward(request, response);
                    } else if (workDay.before(compareDate) && workDay.getDate() != compareDate.getDate()) {
                        request.setAttribute("staffSchedule", null);
                        request.setAttribute("action", "add");
                        request.setAttribute("errorMessage", "Please select a date that meet the conditions");
                        request.getRequestDispatcher("./view/edit-schedule.jsp").forward(request, response);
                    } else {
                        staffScheduleDAO.requestToManager(staffSchedule);
                        response.sendRedirect("staff?event=send-to-schedules");
                    }
                    break;
                }
                case "edit": {
                    String scheduleID = (String) request.getParameter("scheduleID");
                    String staffID = (String) request.getParameter("staffID");
                    Date workDay = Date.valueOf(request.getParameter("workDay"));
                    int workSlot = Integer.parseInt(request.getParameter("workSlot"));
                    StaffSchedule staffSchedule = new StaffSchedule(Integer.parseInt(scheduleID), Integer.parseInt(staffID), workDay, workSlot);
                    // validate the schedule
                    Date compareDate = new Date(System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000));

                    if (staffScheduleDAO.getScheduleByWorkDate(workDay, workSlot, staffID) != null
                            && !Integer.toString(staffScheduleDAO.getScheduleByWorkDate(workDay, workSlot, staffID).getScheduleID()).equals(scheduleID)) {
                        request.setAttribute("scheduleID", scheduleID);
                        request.setAttribute("action", "edit");
                        request.setAttribute("errorMessage", "Cannot edit the schedule because of duplicate schedule");
                        request.getRequestDispatcher("./view/edit-schedule.jsp").forward(request, response);
                    } else if (workDay.before(compareDate) && workDay.getDate() != compareDate.getDate()) {
                        request.setAttribute("scheduleID", scheduleID);
                        request.setAttribute("action", "edit");
                        request.setAttribute("errorMessage", "Please select a date that meet the conditions");
                        request.getRequestDispatcher("./view/edit-schedule.jsp").forward(request, response);
                    } else {
                        staffScheduleDAO.editScheduleByStaff(staffSchedule);
                        response.sendRedirect("staff?event=send-to-schedules");
                    }
                    break;
                }
                case "getPage": {
                    String staffID = (String) request.getParameter("staffId");
                    String pageString = (String) request.getParameter("page");
                    int page = Integer.parseInt(pageString);
                    StringBuilder stringBuilder = new StringBuilder();
                    for (StaffSchedule staffSchedule : staffScheduleDAO.getPageStaffScheduleByStaff(staffID, page, 10)) {
                        stringBuilder.append(staffSchedule.getScheduleID()).append("&");
                        stringBuilder.append(workDayFormat.format(staffSchedule.getWorkday())).append("&");
                        stringBuilder.append(staffSchedule.getSlot()).append("&");
                        stringBuilder.append(staffSchedule.getStatus());
                        stringBuilder.append("\n");
                    }
                    out.print(stringBuilder.toString());
                    break;
                }
                default: {
                    request.getRequestDispatcher("./view/403-forbidden.jsp").forward(request, response);
                }
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
