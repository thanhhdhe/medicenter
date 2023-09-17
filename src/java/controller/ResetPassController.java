/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Random;
import model.MaHoa;
import model.Mail;

/**
 *
 * @author pc
 */
public class ResetPassController extends HttpServlet {

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
        // lay ve action de chia hanh dong
        String action = request.getParameter("action");
        // chia tung hang dong cua servlet theo action
        if (action.equals("forgetpassword")) {
            // tao ra bien phu random
            Random ra = new Random();
            int phu1 = ra.nextInt(100);
            //ma hoa bien phu1 nham muc dich bao mat
            //chuyen phu thanh chuoi
            String strphu1 = MaHoa.toSHA1(phu1 + "");

            //gui phu1 toi reset .jsp
            request.setAttribute("phu1", strphu1);
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
            // gui user toi trang reset
            response.sendRedirect("resetPassword.jsp");
        } else if (action.equals("confirmpassword")) {
            //lay password new va confirm
            String newpassword = request.getParameter("newPassword");
            String confirmpassword = request.getParameter("conPassword");
            //lay ca bien phu2 de gui lai
            String phu2 = request.getParameter("phu2");
            //lay ve mail de gui di
            String email = request.getParameter("Mail");
            //tao 1 session nham viec tinh toan thoi gian ton tai cua link
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(10);
            session.setAttribute("thoigian", "thoigian");
            // ma hoa phu2 va bien OTP de bao mat cho khach hang bien phu 2 se cong doan string la thoi gian roi ma hoa
            String OTP = MaHoa.toSHA1(request.getParameter("phu2") + "thoigian");

            //kiem tra xem pass co hop le khong
            if (newpassword.equals(confirmpassword)) {
                Mail.sendEmail(email, System.currentTimeMillis() + "",
                        "http://localhost:9999/ChildrenCare/resetpassword?phu3=" + phu2
                        + "&OTP=" + OTP + "&action=resetpass");
                response.sendRedirect("resetPassword.jsp");
            } else {
                response.sendRedirect("resetPassword.jsp");
            }
        } else if (action.equals("resetpass")) {
            //lay bien phu 3 va OTP ve de check
            // xac nhan phu3 sao cho bang voi OTP 
            // phu3 + thoigian dem di ma hoa
            String strphu3 = MaHoa.toSHA1(request.getParameter("phu3") + "thoigian");
//            String phu3 = request.getParameter("phu3");
            String OTP = request.getParameter("OTP");
            System.out.println(strphu3);
            System.out.println(OTP);
            //lay bien session de kiem tra neu qua 10s thi bien se bi huy cung nhu link khong hop lo
            HttpSession session = request.getSession();
            String thoigian = (String) session.getAttribute("thoigian");

            if (!OTP.equals(strphu3) || thoigian == null) {
                request.setAttribute("success", "that bai");
                request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
                response.sendRedirect("resetPassword.jsp");
            } else {
                request.setAttribute("success", "thanh cong");
                request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
                response.sendRedirect("resetPassword.jsp");
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
