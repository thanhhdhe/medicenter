/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import Database.SettingDAO;
import Database.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Setting;

/**
 *
 * @author pc
 */
public class SettingController extends HttpServlet {

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
        SettingDAO settingDAO = new SettingDAO();
        HttpSession session = request.getSession();
        boolean isAdmin = false;
        String adminEmail = (String) session.getAttribute("adminEmail")+"";
        if(adminEmail.contains("@"))isAdmin=true;
        if (!isAdmin) {
                    request.getRequestDispatcher("./view/403-forbidden.jsp").forward(request, response);
                    return;
                }
        //get event for settingcontroller
        String event = request.getParameter("event");
        System.out.println(event);
        if (event == null) {

            StaffDAO staffDAO = new StaffDAO();
            request.setAttribute("admin", staffDAO.getStaffByStaffEmail(adminEmail));

            request.getRequestDispatcher("./view/setting-list-admin.jsp").forward(request, response);
        } else if (event.equals("paging")) {
            //get parameter
            String page = request.getParameter("page");
            System.out.println(page);
            List<Setting> listSetting = settingDAO.getSettingList(Integer.parseInt(page));
            for (Setting setting : listSetting) {
                out.println("<tr>\n"
                        + "                                    <td>" + setting.getSettingID() + "</td>\n"
                        + "                                    <td>" + setting.getType() + "</td>\n"
                        + "                                    <td>" + setting.getSettingName() + "</td>\n"
                        + "                                    <td>" + setting.getValue().substring(0, 15) + "</td>\n"
                        + "                                    <td>" + setting.getDescription().substring(0, 15) + "" + "</td>\n"
                        + "                                    <td><div class=\"dropdown\">\n"
                        + "                                            <button style=\"border: 0px; padding: 0px\" type=\"button\" id=\"dropdownMenuButton1\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">\n"
                        + "                                                <span class=\"badge bg-primary\"  id=\"statusBadge-" + setting.getSettingID() + "\">" + setting.getStatus() + "</span>\n"
                        + "                                            </button>\n"
                        + "                                            <ul class=\"dropdown-menu\" aria-labelledby=\"dropdownMenuLink\">\n"
                        + "                                                <li><a class=\"dropdown-item status-change\" href=\"#\" onclick=\"changestatus(this, " + setting.getSettingID() + ")\">Active</a></li>\n"
                        + "                                                <li><a class=\"dropdown-item status-change\" href=\"#\" onclick=\"changestatus(this, " + setting.getSettingID() + ")\">Inactive</a></li>             \n"
                        + "                                            </ul>\n"
                        + "                                        </div></td>                                   \n"
                        + "                                    <td><a href=\"setting?event=detail&settingID=" + setting.getSettingID() + "\"><img src=\"resources/img/icon/detail.png\" alt=\"alt\" width=\"25px\"/></a></td>\n"
                        + "                                </tr>");
            }
        } else if (event.equals("updatestatus")) {
            //get information
            String status = request.getParameter("status");
            String settingID = request.getParameter("settingID");
            settingDAO.updateStatus(status, settingID);
        } else if (event.equals("detail")) {
            //get information
            String settingID = request.getParameter("settingID");
            //get setting from database
            Setting settingdetail = settingDAO.getSettingByID(settingID);
            // redirect from detail
            StaffDAO staffDAO = new StaffDAO();
            request.setAttribute("admin", staffDAO.getStaffByStaffEmail(adminEmail));
            request.setAttribute("settingDeatil", settingdetail);
            request.getRequestDispatcher("./view/setting-detail.jsp").forward(request, response);
        } else if (event.equals("update")) {
            //get information
            String settingid = request.getParameter("settingid");
            String settingName = request.getParameter("name");
            String settingType = request.getParameter("type");
            String settingDes = request.getParameter("description");
            String settingValue = (request.getParameter("avartar").equals("")||request.getParameter("avartar")==null)?request.getParameter("avartarURL"):request.getParameter("avartar");
            System.out.println(settingValue);
            String settingStatus = request.getParameter("status");
            int minLength = 5; // Độ dài tối thiểu cho mỗi tham số, bạn có thể thay đổi giá trị này.
            String error = "";
            if (settingName == null || settingName.length() < minLength
                    || settingType == null || settingType.length() < minLength
                    || settingDes == null || settingDes.length() < minLength
                    || settingValue == null || settingValue.length() < minLength) {
                // Nếu bất kỳ tham số nào là null hoặc có độ dài ngắn hơn giá trị minLength, in ra thông báo lỗi hoặc xử lý lỗi ở đây.
                error = "You must enter character more than 6";
                request.setAttribute("error", error);
                StaffDAO staffDAO = new StaffDAO();
                request.setAttribute("admin", staffDAO.getStaffByStaffEmail(adminEmail));

                request.getRequestDispatcher("./view/add-setting.jsp").forward(request, response);
            } else {
                //update new setting 
                settingDAO.updateSetting(settingStatus, settingid, settingName, settingType, settingValue, settingDes);
                StaffDAO staffDAO = new StaffDAO();
                request.setAttribute("admin", staffDAO.getStaffByStaffEmail(adminEmail));

                request.getRequestDispatcher("./view/setting-list-admin.jsp").forward(request, response);
            }
            
        } else if (event.equals("add")) {
            //get information
            String settingID = request.getParameter("settingID");
            //get setting from database
            Setting settingdetail = settingDAO.getSettingByID(settingID);
            // redirect from detail
            StaffDAO staffDAO = new StaffDAO();
            request.setAttribute("admin", staffDAO.getStaffByStaffEmail(adminEmail));
            request.setAttribute("settingDeatil", settingdetail);
            request.getRequestDispatcher("./view/add-setting.jsp").forward(request, response);
        } else if (event.equals("addnewsetting")) {
            //get information   
            String settingName = request.getParameter("name");
            String settingType = request.getParameter("type");
            String settingDes = request.getParameter("description");
            String settingValue = request.getParameter("avartar");
            String settingStatus = request.getParameter("status");
            int minLength = 5; // Độ dài tối thiểu cho mỗi tham số, bạn có thể thay đổi giá trị này.
            String error = "";
            if (settingName == null || settingName.length() < minLength
                    || settingType == null || settingType.length() < minLength
                    || settingDes == null || settingDes.length() < minLength
                    || settingValue == null || settingValue.length() < minLength) {
                // Nếu bất kỳ tham số nào là null hoặc có độ dài ngắn hơn giá trị minLength, in ra thông báo lỗi hoặc xử lý lỗi ở đây.
                error = "You must enter character more than 6";
                request.setAttribute("error", error);
                StaffDAO staffDAO = new StaffDAO();
                request.setAttribute("admin", staffDAO.getStaffByStaffEmail(adminEmail));

                request.getRequestDispatcher("./view/add-setting.jsp").forward(request, response);
            } else {
                //add new setting 
                settingDAO.InsertSetting(settingType, settingName, settingValue, settingDes, settingStatus);
                StaffDAO staffDAO = new StaffDAO();
                request.setAttribute("admin", staffDAO.getStaffByStaffEmail(adminEmail));

                request.getRequestDispatcher("./view/setting-list-admin.jsp").forward(request, response);
            }

        } else if (event.equals("fillter")) {
            //get parameter
            String page = request.getParameter("page");
            String value = (request.getParameter("value") == null) ? "" : request.getParameter("value");
            String type = (request.getParameter("type") == null) ? "" : request.getParameter("type");
            String sort = (request.getParameter("sort") == null) ? "" : request.getParameter("sort");
            System.out.println(page);
            System.out.println(type);
            System.out.println(sort);
            List<Setting> listSetting = settingDAO.getSettingListBySearch(Integer.parseInt(page), type, "", "", "", value, sort);
            for (Setting setting : listSetting) {
                out.println("<tr>\n"
                        + "                                    <td>" + setting.getSettingID() + "</td>\n"
                        + "                                    <td>" + setting.getType() + "</td>\n"
                        + "                                    <td>" + setting.getSettingName() + "</td>\n"
                        + "                                    <td>" + setting.getValue().substring(0, 15) + "</td>\n"
                        + "                                    <td>" + setting.getDescription().substring(0, 15) + "" + "</td>\n"
                        + "                                    <td><div class=\"dropdown\">\n"
                        + "                                            <button style=\"border: 0px; padding: 0px\" type=\"button\" id=\"dropdownMenuButton1\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">\n"
                        + "                                                <span class=\"badge bg-primary\"  id=\"statusBadge-" + setting.getSettingID() + "\">" + setting.getStatus() + "</span>\n"
                        + "                                            </button>\n"
                        + "                                            <ul class=\"dropdown-menu\" aria-labelledby=\"dropdownMenuLink\">\n"
                        + "                                                <li><a class=\"dropdown-item status-change\" href=\"#\" onclick=\"changestatus(this, " + setting.getSettingID() + ")\">Active</a></li>\n"
                        + "                                                <li><a class=\"dropdown-item status-change\" href=\"#\" onclick=\"changestatus(this, " + setting.getSettingID() + ")\">Inactive</a></li>             \n"
                        + "                                            </ul>\n"
                        + "                                        </div></td>                                   \n"
                        + "                                    <td><a href=\"setting?event=detail&settingID=" + setting.getSettingID() + "\"><img src=\"resources/img/icon/detail.png\" alt=\"alt\" width=\"25px\"/></a></td>\n"
                        + "                                </tr>");
            }
            String pagehtml = "";

            for (int i = 1; i <= (settingDAO.getTotalPageSettingBySearch(type, "", "", "", value) + 9) / 10; i++) {
                if (i == Integer.parseInt(page)) {
                    pagehtml += "<span style=\"color: black\" class=\"pagination-btn btn btn-primary rounded-circle ms-2 active ml-2\" data-page=\"" + i + "\" onclick=\"paging(" + i + ")\">" + i + "</span>\n";
                } else {
                    pagehtml += "<button style=\"color: black\" class=\"pagination-btn btn btn-primary rounded-circle ms-2 inactive ml-2\" data-page=\"" + i + "\" onclick=\"paging(" + i + ")\">" + i + "</button>\n";
                }
            }
            response.addHeader("pagination", pagehtml);
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
