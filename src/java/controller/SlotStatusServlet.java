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
import java.util.List;

/**
 *
 * @author hbich
 */
public class SlotStatusServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String selectedDate = request.getParameter("selectedDate");
        String staffID = request.getParameter("staffID");

        StaffScheduleDAO ssd = new StaffScheduleDAO();
        List<Integer> workSlots = ssd.getWorkSlots(selectedDate, staffID);

        StringBuilder sb = new StringBuilder();
        for (Integer slot : workSlots) {
            sb.append(slot).append(",");
        }
        String workSlotsText = sb.toString();

        // remove the last comma
        if (workSlotsText.endsWith(",")) {
            workSlotsText = workSlotsText.substring(0, workSlotsText.length() - 1);
        }

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(workSlotsText);
    }

}
