/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import Database.UserDAO;
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
import model.User;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author pc
 */
public class ResetPassController extends HttpServlet {

    private int counter = 0;

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
        PrintWriter out = response.getWriter();
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
            request.getRequestDispatcher("/view/SendMail.jsp").forward(request, response);
            // gui user toi trang reset

        } else if (action.equals("confirmpassword")) {

            //lay ca bien phu2 de gui lai
            String phu2 = request.getParameter("phu2");
            //lay ve mail de gui di
            String email = request.getParameter("Mail");

            // ma hoa phu2 va bien OTP de bao mat cho khach hang bien phu 2 se cong doan string la thoi gian roi ma hoa
            String OTP = MaHoa.toSHA1(request.getParameter("phu2") + "thoigian");
            // check email 
            UserDAO dao = new UserDAO();

            User user = dao.getUser(email);

            if (user != null && email.equals(user.getEmail())) {
                //reset counter
                counter = 0;
                Mail.sendEmail(email, "Verification Mail" + "",
                        "http://localhost:9999/ChildrenCare/resetpassword?phu3=" + phu2
                        + "&ID=" + user.getUserID()
                        + "&phoneNumber=" + user.getPhoneNumber()
                        + "&OTP=" + OTP + "&action=resetpass"
                        + " \n Do not Public this email for Safety account");
                out.println("<!DOCTYPE html>\n"
                        + "<html>\n"
                        + "<head>\n"
                        + "    <meta charset=\"UTF-8\">\n"
                        + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                        + "    <style>\n"
                        + "        body {\n"
                        + "            background-color: #f2f2f2;\n"
                        + "            font-family: Arial, sans-serif;\n"
                        + "            display: flex;\n"
                        + "            justify-content: center;\n"
                        + "            align-items: center;\n"
                        + "            height: 100vh;\n"
                        + "            margin: 0;\n"
                        + "        }\n"
                        + "        \n"
                        + "        .container {\n"
                        + "            text-align: center;\n"
                        + "            background-color: #ffffff;\n"
                        + "            border-radius: 10px;\n"
                        + "            padding: 20px;\n"
                        + "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n"
                        + "        }\n"
                        + "        \n"
                        + "        h1 {\n"
                        + "            font-size: 36px;\n"
                        + "            color: #333;\n"
                        + "        }\n"
                        + "        \n"
                        + "        p {\n"
                        + "            font-size: 18px;\n"
                        + "            color: #666;\n"
                        + "        }\n"
                        + "    </style>\n"
                        + "</head>\n"
                        + "<body>\n"
                        + "    <div style=\"width: 300px; height: 200px;\" class=\"container\">\n"
                        + "        <h1>Success</h1>\n"
                        + "        <p>Mail has been sent.</p>\n"
                        + " <button style=\" background-color: blue; border: 0px; border-radius: 5px;padding: 10px;\"> "
                        + " <a style=\"color: white; padding: 10px;text-decoration: none;\" href=\"http://localhost:9999/ChildrenCare/\">HOME</a> </button>"
                        + "    </div>\n"
                        + "</body>\n"
                        + "</html>");

            } else {
                //neu nhu nguoi dung nhap sai se tao ra bien token khac
                // tao ra bien phu random 
                Random ra = new Random();
                int phu1 = ra.nextInt(100);
                //ma hoa bien phu1 nham muc dich bao mat
                //chuyen phu thanh chuoi
                String strphu1 = MaHoa.toSHA1(phu1 + "");

                //kiem tra mail co hop le hay khong
                request.setAttribute("phu1", strphu1);

                request.setAttribute("notify", "You must enter the correct email address.");

                request.getRequestDispatcher("/view/SendMail.jsp").forward(request, response);
            }

        } else if (action.equals("resetpass")) {
            //lay new password 

            //tao ra 1 bien toan cuc la counter de dem so lan goi toi servlet
            //neu nhu chay action la lan dau tien se tao ra session
            // tu lan 2 se bo di dieu nay giup chay duoc tren nhieu browser
            // tao ra session o action nay khien no chay duoc tren nen tang browser bat ky do nguoi dung click
            //lay bien session de kiem tra neu qua 1000s thi bien se bi huy cung nhu link khong hop lo
            // y tuong la duong link chi ton tai trong 1000s va chi kha dung trong lan dau tien click
            counter++;
            System.out.println(counter);
            if (counter == 1) {
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(1000);
                session.setAttribute("thoigian", "thoigian");
                //lay bien phu 3 va OTP ve de check
                // xac nhan phu3 sao cho bang voi OTP 
                // phu3 + thoigian dem di ma hoa
                String strphu3 = MaHoa.toSHA1(request.getParameter("phu3") + "thoigian");
                // String phu3 = request.getParameter("phu3");
                String OTP = request.getParameter("OTP");
                System.out.println(strphu3);
                System.out.println(OTP);

                String thoigian = (String) session.getAttribute("thoigian");

                if (!OTP.equals(strphu3) || thoigian == null) {
                    // tao ra bien phu random
                    Random ra = new Random();
                    int phu1 = ra.nextInt(100);
                    //ma hoa bien phu1 nham muc dich bao mat
                    //chuyen phu thanh chuoi
                    String strphu1 = MaHoa.toSHA1(phu1 + "");

                    //gui phu1 toi reset .jsp
                    request.setAttribute("phu1", strphu1);
                    request.setAttribute("notify", "Link has expired.");
                    request.getRequestDispatcher("/view/SendMail.jsp").forward(request, response);

                } else {
                    //xoa bo session neu nhu da thanh cong
                    session.invalidate();
                    String ID = request.getParameter("ID");
                    request.setAttribute("ID", ID);
                    String phoneNumber = request.getParameter("phoneNumber");
                    request.setAttribute("phoneNumber", phoneNumber);
                    //di toi reset
                    request.getRequestDispatcher("/view/resetPassword.jsp").forward(request, response);

                }
            } else {
                //counter > 1 thi link khong hieu luc
                Random ra = new Random();
                int phu1 = ra.nextInt(100);
                //ma hoa bien phu1 nham muc dich bao mat
                //chuyen phu thanh chuoi
                String strphu1 = MaHoa.toSHA1(phu1 + "");

                //gui phu1 toi reset .jsp
                request.setAttribute("phu1", strphu1);
                request.setAttribute("notify", "Link has been used.");
                request.getRequestDispatcher("/view/SendMail.jsp").forward(request, response);
            }

        } else if (action.equals("change")) {
            //lay mat khau moi de doi
            String newpassword = request.getParameter("newPassword");
            String confirmpassword = request.getParameter("conPassword");
            // kiem tra mat khau da khop chua
            String ID = request.getParameter("ID");
            int id = Integer.parseInt(ID);
            String phoneNumber = request.getParameter("phoneNumber");
            UserDAO dao = new UserDAO();

            if (newpassword.equals(confirmpassword)) {
                dao.resetPasswordByID(DigestUtils.md5Hex(newpassword), phoneNumber, id);
                out.println("<!DOCTYPE html>\n"
                        + "<html>\n"
                        + "<head>\n"
                        + "    <meta charset=\"UTF-8\">\n"
                        + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                        + "    <style>\n"
                        + "        body {\n"
                        + "            background-color: #f2f2f2;\n"
                        + "            font-family: Arial, sans-serif;\n"
                        + "            display: flex;\n"
                        + "            justify-content: center;\n"
                        + "            align-items: center;\n"
                        + "            height: 100vh;\n"
                        + "            margin: 0;\n"
                        + "        }\n"
                        + "        \n"
                        + "        .container {\n"
                        + "            text-align: center;\n"
                        + "            background-color: #ffffff;\n"
                        + "            border-radius: 10px;\n"
                        + "            padding: 20px;\n"
                        + "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n"
                        + "        }\n"
                        + "        \n"
                        + "        h1 {\n"
                        + "            font-size: 36px;\n"
                        + "            color: #333;\n"
                        + "        }\n"
                        + "        \n"
                        + "        p {\n"
                        + "            font-size: 18px;\n"
                        + "            color: #666;\n"
                        + "        }\n"
                        + "    </style>\n"
                        + "</head>\n"
                        + "<body>\n"
                        + "    <div style=\"width: 300px; height: 200px;\" class=\"container\">\n"
                        + "        <h1>Success</h1>\n"
                        + "        <p>Password has been reset.</p>\n"
                        + " <button style=\" background-color: blue; border: 0px; border-radius: 5px;padding: 10px;\"> "
                        + " <a style=\"color: white; padding: 10px;text-decoration: none;\" href=\"http://localhost:9999/ChildrenCare/\">HOME</a> </button>"
                        + "    </div>\n"
                        + "</body>\n"
                        + "</html>");
            } else {
                request.setAttribute("ID", ID);
                request.setAttribute("notify", "Passwords do not match.");
                request.getRequestDispatcher("/view/resetPassword.jsp").forward(request, response);
            }

        } else {
            //counter > 1 thi link khong hieu luc

            //nguoi dung nhap sai link tao ra bien random moi
            Random ra = new Random();
            int phu1 = ra.nextInt(100);
            //ma hoa bien phu1 nham muc dich bao mat
            //chuyen phu thanh chuoi
            String strphu1 = MaHoa.toSHA1(phu1 + "");

            //gui phu1 toi reset .jsp
            request.setAttribute("phu1", strphu1);
            request.setAttribute("notify", "link is invaild");
            request.getRequestDispatcher("/view/SendMail.jsp").forward(request, response);
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
