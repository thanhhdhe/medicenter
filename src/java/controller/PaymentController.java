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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
        long amount = (int) Math.round(Double.parseDouble(request.getParameter("amount")) * 100 * 24000);

        if (payment.equals("vnpay")) {
            String vnp_Version = "2.1.0";
            String vnp_Command = "pay";
            String orderType = "270001";
//            long amount = Integer.parseInt(request.getParameter("amount")) * 100;
            String bankCode = null;

            String vnp_TxnRef = Config.getRandomNumber(8);
            String vnp_IpAddr = Config.getIpAddress(request);

            String vnp_TmnCode = Config.vnp_TmnCode;

            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", vnp_Version);
            vnp_Params.put("vnp_Command", vnp_Command);
            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf(amount));
            vnp_Params.put("vnp_CurrCode", "VND");

            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
            vnp_Params.put("vnp_OrderType", orderType);

            String locate = request.getParameter("language");
            if (locate != null && !locate.isEmpty()) {
                vnp_Params.put("vnp_Locale", locate);
            } else {
                vnp_Params.put("vnp_Locale", "vn");
            }
            vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl + reservationID);
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
            job.addProperty("code", "00");
            job.addProperty("message", "success");
            job.addProperty("data", paymentUrl);
            Gson gson = new Gson();
            response.getWriter().write(gson.toJson(job));
            reservation.setPayment("VNPay");
            reservationDAO.update(reservation);
            Thread emailThread = new Thread(() -> {
                Mail.sendEmail(users.getEmail(), "Information about your reservations in Medilab", setInfo());
            });
            emailThread.start();
        } else if (payment.equals("offline")) {
            com.google.gson.JsonObject job = new JsonObject();
            job.addProperty("code", "00");
            job.addProperty("message", "success");
            String paymentUrl = getServletContext().getContextPath() + "/pay?action=view&reservation=" + reservationID;
            job.addProperty("data", paymentUrl);
            reservation.setStatus("Waitting for examination");
            reservation.setPayment("Pay at center");
            reservationDAO.update(reservation);
            request.setAttribute("reservation", reservation);
            Thread emailThread = new Thread(() -> {
                Mail.sendEmail(users.getEmail(), "Information about your reservations in Medilab", setInfo());
            });
            emailThread.start();
            Gson gson = new Gson();
            try {
                response.getWriter().write(gson.toJson(job));
            } catch (IOException e) {
                e.printStackTrace();
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
        String action = request.getParameter("action");
        if (action.equals("view")) {
            int reservationID = Integer.parseInt(request.getParameter("reservation"));
            ReservationDAO reservationDAO = new ReservationDAO();
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
            request.getRequestDispatcher("./view/reservationstatus.jsp").forward(request, response);
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

    public String setInfo() {
        String content = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
                + "<html xmlns:v=\"urn:schemas-microsoft-com:vml\">\n"
                + "\n"
                + "<head>\n"
                + "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"
                + "    <meta name=\"viewport\" content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0;\" />\n"
                + "    <!--[if !mso]--><!-- -->\n"
                + "    <link href='https://fonts.googleapis.com/css?family=Work+Sans:300,400,500,600,700' rel=\"stylesheet\">\n"
                + "    <link href='https://fonts.googleapis.com/css?family=Quicksand:300,400,700' rel=\"stylesheet\">\n"
                + "    <!--<![endif]-->\n"
                + "\n"
                + "    <title>Material Design for Bootstrap</title>\n"
                + "\n"
                + "    <style type=\"text/css\">\n"
                + "        body {\n"
                + "            width: 100%;\n"
                + "            background-color: #ffffff;\n"
                + "            margin: 0;\n"
                + "            padding: 0;\n"
                + "            -webkit-font-smoothing: antialiased;\n"
                + "            mso-margin-top-alt: 0px;\n"
                + "            mso-margin-bottom-alt: 0px;\n"
                + "            mso-padding-alt: 0px 0px 0px 0px;\n"
                + "        }\n"
                + "\n"
                + "        p,\n"
                + "        h1,\n"
                + "        h2,\n"
                + "        h3,\n"
                + "        h4 {\n"
                + "            margin-top: 0;\n"
                + "            margin-bottom: 0;\n"
                + "            padding-top: 0;\n"
                + "            padding-bottom: 0;\n"
                + "        }\n"
                + "\n"
                + "        span.preheader {\n"
                + "            display: none;\n"
                + "            font-size: 1px;\n"
                + "        }\n"
                + "\n"
                + "        html {\n"
                + "            width: 100%;\n"
                + "        }\n"
                + "\n"
                + "        table {\n"
                + "            font-size: 14px;\n"
                + "            border: 0;\n"
                + "        }\n"
                + "        /* ----------- responsivity ----------- */\n"
                + "\n"
                + "        @media only screen and (max-width: 640px) {\n"
                + "            /*------ top header ------ */\n"
                + "            .main-header {\n"
                + "                font-size: 20px !important;\n"
                + "            }\n"
                + "            .main-section-header {\n"
                + "                font-size: 28px !important;\n"
                + "            }\n"
                + "            .show {\n"
                + "                display: block !important;\n"
                + "            }\n"
                + "            .hide {\n"
                + "                display: none !important;\n"
                + "            }\n"
                + "            .align-center {\n"
                + "                text-align: center !important;\n"
                + "            }\n"
                + "            .no-bg {\n"
                + "                background: none !important;\n"
                + "            }\n"
                + "            /*----- main image -------*/\n"
                + "            .main-image img {\n"
                + "                width: 440px !important;\n"
                + "                height: auto !important;\n"
                + "            }\n"
                + "            /* ====== divider ====== */\n"
                + "            .divider img {\n"
                + "                width: 440px !important;\n"
                + "            }\n"
                + "            /*-------- container --------*/\n"
                + "            .container590 {\n"
                + "                width: 440px !important;\n"
                + "            }\n"
                + "            .container580 {\n"
                + "                width: 400px !important;\n"
                + "            }\n"
                + "            .main-button {\n"
                + "                width: 220px !important;\n"
                + "            }\n"
                + "            /*-------- secions ----------*/\n"
                + "            .section-img img {\n"
                + "                width: 320px !important;\n"
                + "                height: auto !important;\n"
                + "            }\n"
                + "            .team-img img {\n"
                + "                width: 100% !important;\n"
                + "                height: auto !important;\n"
                + "            }\n"
                + "        }\n"
                + "\n"
                + "        @media only screen and (max-width: 479px) {\n"
                + "            /*------ top header ------ */\n"
                + "            .main-header {\n"
                + "                font-size: 18px !important;\n"
                + "            }\n"
                + "            .main-section-header {\n"
                + "                font-size: 26px !important;\n"
                + "            }\n"
                + "            /* ====== divider ====== */\n"
                + "            .divider img {\n"
                + "                width: 280px !important;\n"
                + "            }\n"
                + "            /*-------- container --------*/\n"
                + "            .container590 {\n"
                + "                width: 280px !important;\n"
                + "            }\n"
                + "            .container590 {\n"
                + "                width: 280px !important;\n"
                + "            }\n"
                + "            .container580 {\n"
                + "                width: 260px !important;\n"
                + "            }\n"
                + "            /*-------- secions ----------*/\n"
                + "            .section-img img {\n"
                + "                width: 280px !important;\n"
                + "                height: auto !important;\n"
                + "            }\n"
                + "        }\n"
                + "    </style>\n"
                + "    <!--[if gte mso 9]><style type=”text/css”>\n"
                + "        body {\n"
                + "        font-family: arial, sans-serif!important;\n"
                + "        }\n"
                + "        </style>\n"
                + "    <![endif]-->\n"
                + "</head>\n"
                + "\n"
                + "\n"
                + "<body class=\"respond\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\n"
                + "    <!-- pre-header -->\n"
                + "    <table style=\"display:none!important;\">\n"
                + "        <tr>\n"
                + "            <td>\n"
                + "                <div style=\"overflow:hidden;display:none;font-size:1px;color:#ffffff;line-height:1px;font-family:Arial;maxheight:0px;max-width:0px;opacity:0;\">\n"
                + "                    Welcome to Medilab\n"
                + "                </div>\n"
                + "            </td>\n"
                + "        </tr>\n"
                + "    </table>\n"
                + "    <!-- pre-header end -->\n"
                + "    <!-- header -->\n"
                + "    <table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"ffffff\">\n"
                + "\n"
                + "        <tr>\n"
                + "            <td align=\"center\">\n"
                + "                <table border=\"0\" align=\"center\" width=\"590\" cellpadding=\"0\" cellspacing=\"0\" class=\"container590\">\n"
                + "\n"
                + "                    <tr>\n"
                + "                        <td height=\"25\" style=\"font-size: 25px; line-height: 25px;\">&nbsp;</td>\n"
                + "                    </tr>\n"
                + "\n"
                + "                    <tr>\n"
                + "                        <td align=\"center\">\n"
                + "\n"
                + "                            <table border=\"0\" align=\"center\" width=\"590\" cellpadding=\"0\" cellspacing=\"0\" class=\"container590\">\n"
                + "\n"
                + "                                <tr>\n"
                + "                                    <td align=\"center\" height=\"70\" style=\"height:70px;\">\n"
                + "                                        <a href=\"\" style=\"display: block; border-style: none !important; border: 0 !important;\"><img width=\"100\" border=\"0\" style=\"display: block; width: 100px;\" src=\"https://mdbootstrap.com/img/logo/mdb-email.png\" alt=\"\" /></a>\n"
                + "                                    </td>\n"
                + "                                </tr>\n"
                + "                            </table>\n"
                + "                        </td>\n"
                + "                    </tr>\n"
                + "\n"
                + "                    <tr>\n"
                + "                        <td height=\"25\" style=\"font-size: 25px; line-height: 25px;\">&nbsp;</td>\n"
                + "                    </tr>\n"
                + "\n"
                + "                </table>\n"
                + "            </td>\n"
                + "        </tr>\n"
                + "    </table>\n"
                + "    <!-- end header -->\n"
                + "\n"
                + "    <!-- big image section -->\n"
                + "\n"
                + "    <table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"ffffff\" class=\"bg_color\">\n"
                + "\n"
                + "        <tr>\n"
                + "            <td align=\"center\">\n"
                + "                <table border=\"0\" align=\"center\" width=\"590\" cellpadding=\"0\" cellspacing=\"0\" class=\"container590\">\n"
                + "\n"
                + "                    <tr>\n"
                + "                        <td align=\"center\" style=\"color: #343434; font-size: 24px; font-family: Quicksand, Calibri, sans-serif; font-weight:700;letter-spacing: 3px; line-height: 35px;\"\n"
                + "                            class=\"main-header\">\n"
                + "                            <!-- section text ======-->\n"
                + "\n"
                + "                            <div style=\"line-height: 35px\">\n"
                + "\n"
                + "                                Thank you for your <span style=\"color: #5caad2;\">reservation!</span>\n"
                + "\n"
                + "                            </div>\n"
                + "                        </td>\n"
                + "                    </tr>\n"
                + "\n"
                + "                    <tr>\n"
                + "                        <td height=\"10\" style=\"font-size: 10px; line-height: 10px;\">&nbsp;</td>\n"
                + "                    </tr>\n"
                + "\n"
                + "                    <tr>\n"
                + "                        <td align=\"center\">\n"
                + "                            <table border=\"0\" width=\"40\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"eeeeee\">\n"
                + "                                <tr>\n"
                + "                                    <td height=\"2\" style=\"font-size: 2px; line-height: 2px;\">&nbsp;</td>\n"
                + "                                </tr>\n"
                + "                            </table>\n"
                + "                        </td>\n"
                + "                    </tr>\n"
                + "\n"
                + "                    <tr>\n"
                + "                        <td height=\"20\" style=\"font-size: 20px; line-height: 20px;\">&nbsp;</td>\n"
                + "                    </tr>\n"
                + "\n"
                + "                    <tr>\n"
                + "                        <td align=\"left\">\n"
                + "                            <table border=\"1\" width=\"590\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"container590\">\n"
                + "                                <tr>\n"
                + "                                    <td align=\"center\" style=\"color:  #888888; font-size: 16px; font-family: 'Work Sans', Calibri, sans-serif; line-height: 24px;\">\n"
                + "                                        <!-- section text ======-->\n"
                + "                                        <p style=\"line-height: 24px; margin-bottom: 15px;\">\n"
                + "                                            This is your reservation information:\n"
                + "                                        </p>\n"
                + "                                        <p style=\"line-height: 24px; margin-bottom: 15px;\">\n"
                + "                                            Reservation ID: <strong>18</strong>\n"
                + "                                        </p>\n"
                + "                                        <p style=\"line-height: 24px; margin-bottom: 15px;\">\n"
                + "                                            Fullname of your child: <strong>thanh</strong>\n"
                + "                                        </p>\n"
                + "                                        <p style=\"line-height: 24px; margin-bottom: 15px;\">\n"
                + "                                            Date of birth: <strong>2011-09-01</strong>\n"
                + "                                        </p>\n"
                + "                                        <p style=\"line-height: 24px; margin-bottom: 15px;\">\n"
                + "                                            Child Gender: <strong>Female</strong>\n"
                + "                                        </p>\n"
                + "                                        <p style=\"line-height: 24px; margin-bottom: 15px;\">\n"
                + "                                            Parent's phone: <strong>0834398268</strong>\n"
                + "                                        </p>\n"
                + "            \n"
                + "                                        <p style=\"line-height: 24px; margin-bottom: 15px;\">\n"
                + "                                            Parent's email: <strong>lethangd@gmail.com</strong>\n"
                + "                                        </p>\n"
                + "                                        <p style=\"line-height: 24px; margin-bottom: 15px;\">\n"
                + "                                            Address: <strong>hn</strong>\n"
                + "                                        </p>\n"
                + "                                        <p style=\"line-height: 24px; margin-bottom: 15px;\">\n"
                + "                                            Doctor: <strong>thang</strong>\n"
                + "                                        </p>\n"
                + "                                        <p style=\"line-height: 24px; margin-bottom: 15px;\">\n"
                + "                                            Services: <strong>Eye Surgery - LASIK</strong>\n"
                + "                                        </p>\n"
                + "                                        <p style=\"line-height: 24px; margin-bottom: 15px;\">\n"
                + "                                            Appointment Date: <strong>2023-11-01</strong>\n"
                + "                                        </p>\n"
                + "                                        <p style=\"line-height: 24px; margin-bottom: 15px;\">\n"
                + "                                            Consultation Fee: <strong>450.0$</strong>\n"
                + "                                        </p>\n"
                + "                                        <p style=\"line-height: 24px; margin-bottom: 15px;\">\n"
                + "                                            Method Payment: <strong>VNPay</strong>\n"
                + "                                        </p>\n"
                + "                                        \n"
                + "                                        <p style=\"line-height: 24px; margin-bottom:20px;\">\n"
                + "                                            Status: <strong>VNPay</strong>\n"
                + "                                        </p>\n"
                + "                                        <table border=\"0\" align=\"center\" width=\"180\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"5caad2\" style=\"margin-bottom:20px;\">\n"
                + "\n"
                + "                                            <tr>\n"
                + "                                                <td height=\"10\" style=\"font-size: 10px; line-height: 10px;\">&nbsp;</td>\n"
                + "                                            </tr>\n"
                + "\n"
                + "                                            <tr>\n"
                + "                                                <td align=\"center\" style=\"color: #ffffff; font-size: 14px; font-family: 'Work Sans', Calibri, sans-serif; line-height: 30px; letter-spacing: 2px;\">\n"
                + "                                                    <!-- main section button -->\n"
                + "\n"
                + "                                                    <div style=\"line-height: 22px;\">\n"
                + "                                                        <a href=\"http://localhost:9999/ChildrenCare/myreservation\" style=\"color: #ffffff; text-decoration: none;\">YOUR RESERVATION</a>\n"
                + "                                                    </div>\n"
                + "                                                </td>\n"
                + "                                            </tr>\n"
                + "\n"
                + "                                            <tr>\n"
                + "                                                <td height=\"10\" style=\"font-size: 10px; line-height: 10px;\">&nbsp;</td>\n"
                + "                                            </tr>\n"
                + "\n"
                + "                                        </table>\n"
                + "                                        <p style=\"line-height: 24px\">\n"
                + "                                            Love,</br>\n"
                + "                                            The Medilab Team\n"
                + "                                        </p>\n"
                + "\n"
                + "                                    </td>\n"
                + "                                </tr>\n"
                + "                            </table>\n"
                + "                        </td>\n"
                + "                    </tr>\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "                </table>\n"
                + "\n"
                + "            </td>\n"
                + "        </tr>\n"
                + "\n"
                + "        <tr>\n"
                + "            <td height=\"40\" style=\"font-size: 40px; line-height: 40px;\">&nbsp;</td>\n"
                + "        </tr>\n"
                + "\n"
                + "    </table>\n"
                + "\n"
                + "    <!-- end section -->\n"
                + "   \n"
                + "\n"
                + "    <!-- end section -->\n"
                + "\n"
                + "    <!-- contact section -->\n"
                + "    <table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"ffffff\" class=\"bg_color\">\n"
                + "\n"
                + "        <tr>\n"
                + "            <td height=\"60\" style=\"font-size: 60px; line-height: 60px;\">&nbsp;</td>\n"
                + "        </tr>\n"
                + "\n"
                + "        <tr>\n"
                + "            <td align=\"center\">\n"
                + "                <table border=\"0\" align=\"center\" width=\"590\" cellpadding=\"0\" cellspacing=\"0\" class=\"container590 bg_color\">\n"
                + "\n"
                + "                    <tr>\n"
                + "                        <td align=\"center\">\n"
                + "                            <table border=\"0\" align=\"center\" width=\"590\" cellpadding=\"0\" cellspacing=\"0\" class=\"container590 bg_color\">\n"
                + "\n"
                + "                                <tr>\n"
                + "                                    <td>\n"
                + "                                        <table border=\"0\" width=\"300\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\"\n"
                + "                                            class=\"container590\">\n"
                + "\n"
                + "                                            <tr>\n"
                + "                                                <!-- logo -->\n"
                + "                                                <td align=\"left\">\n"
                + "                                                    <a href=\"\" style=\"display: block; border-style: none !important; border: 0 !important;\"><img width=\"80\" border=\"0\" style=\"display: block; width: 80px;\" src=\"https://mdbootstrap.com/img/logo/mdb-email.png\" alt=\"\" /></a>\n"
                + "                                                </td>\n"
                + "                                            </tr>\n"
                + "\n"
                + "                                            <tr>\n"
                + "                                                <td height=\"25\" style=\"font-size: 25px; line-height: 25px;\">&nbsp;</td>\n"
                + "                                            </tr>\n"
                + "\n"
                + "                                            <tr>\n"
                + "                                                <td align=\"left\" style=\"color: #888888; font-size: 14px; font-family: 'Work Sans', Calibri, sans-serif; line-height: 23px;\"\n"
                + "                                                    class=\"text_color\">\n"
                + "                                                    <div style=\"color: #333333; font-size: 14px; font-family: 'Work Sans', Calibri, sans-serif; font-weight: 600; mso-line-height-rule: exactly; line-height: 23px;\">\n"
                + "\n"
                + "                                                        Email us: <br/> <a href=\"mailto:\" style=\"color: #888888; font-size: 14px; font-family: 'Hind Siliguri', Calibri, Sans-serif; font-weight: 400;\">contact@medilab.com</a>\n"
                + "\n"
                + "                                                    </div>\n"
                + "                                                </td>\n"
                + "                                            </tr>\n"
                + "\n"
                + "                                        </table>\n"
                + "\n"
                + "                                        <table border=\"0\" width=\"2\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\"\n"
                + "                                            class=\"container590\">\n"
                + "                                            <tr>\n"
                + "                                                <td width=\"2\" height=\"10\" style=\"font-size: 10px; line-height: 10px;\"></td>\n"
                + "                                            </tr>\n"
                + "                                        </table>\n"
                + "\n"
                + "                                        <table border=\"0\" width=\"200\" align=\"right\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\"\n"
                + "                                            class=\"container590\">\n"
                + "\n"
                + "                                            <tr>\n"
                + "                                                <td class=\"hide\" height=\"45\" style=\"font-size: 45px; line-height: 45px;\">&nbsp;</td>\n"
                + "                                            </tr>\n"
                + "\n"
                + "\n"
                + "\n"
                + "                                            <tr>\n"
                + "                                                <td height=\"15\" style=\"font-size: 15px; line-height: 15px;\">&nbsp;</td>\n"
                + "                                            </tr>\n"
                + "\n"
                + "                                            <tr>\n"
                + "                                                <td>\n"
                + "                                                    <table border=\"0\" align=\"right\" cellpadding=\"0\" cellspacing=\"0\">\n"
                + "                                                        <tr>\n"
                + "                                                            <td>\n"
                + "                                                                <a href=\"https://www.facebook.com/mdbootstrap\" style=\"display: block; border-style: none !important; border: 0 !important;\"><img width=\"24\" border=\"0\" style=\"display: block;\" src=\"http://i.imgur.com/Qc3zTxn.png\" alt=\"\"></a>\n"
                + "                                                            </td>\n"
                + "                                                            <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>\n"
                + "                                                            <td>\n"
                + "                                                                <a href=\"https://twitter.com/MDBootstrap\" style=\"display: block; border-style: none !important; border: 0 !important;\"><img width=\"24\" border=\"0\" style=\"display: block;\" src=\"http://i.imgur.com/RBRORq1.png\" alt=\"\"></a>\n"
                + "                                                            </td>\n"
                + "                                                            <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>\n"
                + "                                                            <td>\n"
                + "                                                                <a href=\"https://plus.google.com/u/0/b/107863090883699620484/107863090883699620484/posts\" style=\"display: block; border-style: none !important; border: 0 !important;\"><img width=\"24\" border=\"0\" style=\"display: block;\" src=\"http://i.imgur.com/Wji3af6.png\" alt=\"\"></a>\n"
                + "                                                            </td>\n"
                + "                                                        </tr>\n"
                + "                                                    </table>\n"
                + "                                                </td>\n"
                + "                                            </tr>\n"
                + "\n"
                + "                                        </table>\n"
                + "                                    </td>\n"
                + "                                </tr>\n"
                + "                            </table>\n"
                + "                        </td>\n"
                + "                    </tr>\n"
                + "                </table>\n"
                + "            </td>\n"
                + "        </tr>\n"
                + "\n"
                + "        <tr>\n"
                + "            <td height=\"60\" style=\"font-size: 60px; line-height: 60px;\">&nbsp;</td>\n"
                + "        </tr>\n"
                + "\n"
                + "    </table>\n"
                + "    <!-- end section -->\n"
                + "\n"
                + "    \n"
                + "\n"
                + "</body>\n"
                + "\n"
                + "</html>";
        return content;
    }

}
