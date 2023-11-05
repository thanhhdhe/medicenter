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
import com.vnpay.common.Config;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import static java.lang.System.out;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import model.CategoryService;
import model.Children;
import model.Mail;
import model.Reservation;
import model.Service;
import model.Staff;
import model.User;
import org.apache.catalina.Session;

/**
 *
 * @author Admin
 */
public class CheckResultController extends HttpServlet {

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
        //Begin process return from VNPAY
        /* Payment Notify
     * IPN URL: Ghi nhận kết quả thanh toán từ VNPAY
     * Các bước thực hiện:
     * Kiểm tra checksum 
     * Tìm giao dịch trong database
     * Kiểm tra số tiền giữa hai hệ thống
     * Kiểm tra tình trạng của giao dịch trước khi cập nhật
     * Cập nhật kết quả vào Database
     * Trả kết quả ghi nhận lại cho VNPAY
         */
        HttpSession session = request.getSession();
        if (request.getParameter("method") != null && !request.getParameter("method").isEmpty()) {
            String reservationIDStr = request.getParameter("reservation");
            int reservationID = Integer.parseInt(reservationIDStr);
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
            User users = (User) session.getAttribute("user");
            reservation.setStatus("waiting for examination");
            reservationDAO.update(reservation);
            request.getSession().setAttribute("message", "Payment successfully");
            Thread emailThread = new Thread(() -> {
                Mail.sendEmail(users.getEmail(), "Information about your reservations in Medilab", Mail.setInfo(reservation,service,doctor,children,cate));
            });
            emailThread.start();
            request.getRequestDispatcher("./view/reservationstatus.jsp").forward(request, response);
        } else {

            Map fields = new HashMap();
            for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
                String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
                String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    fields.put(fieldName, fieldValue);
                }
            }
            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            if (fields.containsKey("vnp_SecureHashType")) {
                fields.remove("vnp_SecureHashType");
            }
            if (fields.containsKey("vnp_SecureHash")) {
                fields.remove("vnp_SecureHash");
            }
            String signValue = Config.hashAllFields(fields);
            if (signValue.equals(vnp_SecureHash)) {
                boolean checkOrderId = false; // Giá trị của vnp_TxnRef tồn tại trong CSDL của merchant
                String vnp_TxnRef = request.getParameter("vnp_TxnRef");
                String reservationIDStr = vnp_TxnRef.substring(6);
                int reservationID = Integer.parseInt(reservationIDStr);
                ReservationDAO reservationDAO = new ReservationDAO();
                reservationDAO.updateDatabase();
                Reservation reservation = reservationDAO.getReservationByID(reservationID);
                if (reservation != null) {
                    checkOrderId = true;
                }
                boolean checkAmount = false; //Kiểm tra số tiền thanh toán do VNPAY phản hồi(vnp_Amount/100) với số tiền của đơn hàng merchant tạo thanh toán: giả sử số tiền kiểm tra là đúng.
                String vnp_AmountStr = request.getParameter("vnp_Amount");
                int vnp_Amount = Integer.parseInt(vnp_AmountStr);
                if (vnp_Amount == (int) Math.round(reservation.getCost()) * 100 * 24000) {
                    checkAmount = true;
                }
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
                boolean checkOrderStatus = false;
                if(reservation.getStatus().equals("pending")) {
                    checkOrderStatus = true;
                }
                if (checkOrderId) {
                    if (checkAmount) {
                        if (checkOrderStatus) {
                            if ("00".equals(request.getParameter("vnp_ResponseCode"))) {
                                User users = (User) session.getAttribute("user");
                                reservation.setStatus("waiting for examination");
                                reservationDAO.update(reservation);
                                request.getSession().setAttribute("message", "Payment successfully");
                                Thread emailThread = new Thread(() -> {
                                    Mail.sendEmail(users.getEmail(), "Information about your reservations in Medilab", Mail.setInfo(reservation,service,doctor,children,cate));
                                });
                                emailThread.start();
                            } else {
                                request.getSession().setAttribute("message", "Payment error");
                            }
                            out.print("{\"RspCode\":\"00\",\"Message\":\"Confirm Success\"}");
                        } else {
                            request.getSession().setAttribute("message", "Order already confirmed error");
                        }
                    } else {
                        request.getSession().setAttribute("message", "Invalid Amount error");
                    }
                } else {
                    request.getSession().setAttribute("message", "Order not Found error");
                }

            } else {
                request.getSession().setAttribute("message", "Invalid Checksum error");
            }
            request.getRequestDispatcher("./view/reservationstatus.jsp").forward(request, response);
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
