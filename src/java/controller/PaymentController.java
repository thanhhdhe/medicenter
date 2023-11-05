/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import Database.CategoryServiceDAO;
import Database.ChildrenDAO;
import Database.ReservationDAO;
import Database.ServiceDAO;
import Database.StaffDAO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vnpay.common.Config;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import static java.lang.System.out;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import model.CategoryService;
import model.Children;
import model.Mail;
import model.Reservation;
import model.Service;
import model.Staff;
import model.User;

/**
 *
 * @author Admin
 */
public class PaymentController extends HttpServlet {

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
        HttpSession session = request.getSession();
        User users = (User) session.getAttribute("user");
        String payment = request.getParameter("payment");
        int reservationID = Integer.parseInt(request.getParameter("reservation"));
        ReservationDAO reservationDAO = new ReservationDAO();
        reservationDAO.updateDatabase();
        Reservation reservation = reservationDAO.getReservationByID(reservationID);
        ServiceDAO serviceDAO = new ServiceDAO();
        Service service = serviceDAO.getServiceByID(String.valueOf(reservation.getServiceID()));
        StaffDAO staffDAO = new StaffDAO();
        Staff doctor = staffDAO.getStaffByStaffId(reservation.getStaffID());
        ChildrenDAO childrenDAO = new ChildrenDAO();
        Children children = childrenDAO.getChildrenByChildrenId(String.valueOf(reservation.getChildID()));
        CategoryServiceDAO cateDAO = new CategoryServiceDAO();
        CategoryService cate = cateDAO.getCategoryServiceByID(String.valueOf(service.getServiceID()));
        request.setAttribute("reservation", reservation);
        request.setAttribute("service", service);
        request.setAttribute("doctor", doctor);
        request.setAttribute("children", children);
        request.setAttribute("cate", cate);
//        long amount = (int) Math.round(Double.parseDouble(request.getParameter("amount")) * 100 * 24000);
        if (payment.equals("vnpay")) {
            String vnp_Version = "2.1.0";
            String vnp_Command = "pay";
            String orderType = "other";
            long amount = (int) Math.round(Double.parseDouble(request.getParameter("amount")) * 100 * 24000);
            String bankCode = request.getParameter("bankCode");

            String vnp_TxnRef = Config.getRandomNumber(6) + "" + reservation.getReservationID();
            String vnp_IpAddr = Config.getIpAddress(request);

            String vnp_TmnCode = Config.vnp_TmnCode;

            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", vnp_Version);
            vnp_Params.put("vnp_Command", vnp_Command);
            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf(amount));
            vnp_Params.put("vnp_CurrCode", "VND");

            if (bankCode != null && !bankCode.isEmpty()) {
                vnp_Params.put("vnp_BankCode", bankCode);
            }
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_OrderInfo", "Pay for services:" + vnp_TxnRef);
            vnp_Params.put("vnp_OrderType", orderType);

            String locate = request.getParameter("language");
            if (locate != null && !locate.isEmpty()) {
                vnp_Params.put("vnp_Locale", locate);
            } else {
                vnp_Params.put("vnp_Locale", "vn");
            }
            vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

            cld.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

            List fieldNames = new ArrayList(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            Iterator itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName = (String) itr.next();
                String fieldValue = (String) vnp_Params.get(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    //Build hash data
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    if (itr.hasNext()) {
                        query.append('&');
                        hashData.append('&');
                    }
                }
            }
            String queryUrl = query.toString();
            String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
            queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
            String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
            com.google.gson.JsonObject job = new JsonObject();
            if (reservation.getStatus().equals("pending")) {
                job.addProperty("code", "00");
                job.addProperty("message", "success");
                job.addProperty("data", paymentUrl);
                Gson gson = new Gson();
                response.getWriter().write(gson.toJson(job));
                reservation.setPayment("VNPay");
                reservationDAO.update(reservation);
            } else {
                job.addProperty("code", "01");
                job.addProperty("message", "You have the same examinaton!");
                job.addProperty("data", paymentUrl);
                Gson gson = new Gson();
                response.getWriter().write(gson.toJson(job));
            }
        } else if (payment.equals("offline")) {
            com.google.gson.JsonObject job = new JsonObject();
            if (reservation.getStatus().equals("pending")) {
                job.addProperty("code", "00");
                job.addProperty("message", "success");
                reservation.setPayment("Pay at Center");
                reservationDAO.update(reservation);
                String paymentUrl = getServletContext().getContextPath() + "/check?method=off&reservation=" + reservationID;
                job.addProperty("data", paymentUrl);
                Gson gson = new Gson();
                try {
                    response.getWriter().write(gson.toJson(job));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                job.addProperty("code", "01");
                job.addProperty("message", "You have the same examinaton!");
                String paymentUrl = getServletContext().getContextPath() + "/check?method=off&reservation=" + reservationID;
                job.addProperty("data", paymentUrl);
                Gson gson = new Gson();
                try {
                    response.getWriter().write(gson.toJson(job));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

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
