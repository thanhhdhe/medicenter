/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import Database.SliderDAO;
import Database.StaffDAO;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import model.Slider;
import model.Staff;

/**
 *
 * @author Admin
 */
@MultipartConfig( //        fileSizeThreshold = 1024 * 10, // 10 KB
        //        maxFileSize = 1024 * 300, // 300 KB
        //        maxRequestSize = 1024 * 1024 // 1 MB 
        )
public class SliderController extends HttpServlet {

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
        HttpSession session = request.getSession(true);
        String email = (String) session.getAttribute("email");
        StaffDAO staffDAO = new StaffDAO();
        Staff curStaff = staffDAO.getStaffByStaffEmail(email);
        if (curStaff != null) {
            if (!curStaff.getRole().equals("manager")) {
                response.sendRedirect("./view/403.jsp");
            } else {
                switch (action) {
                    case "all":
                        request.getRequestDispatcher("./view/slider-list.jsp").forward(request, response);
                        break;
                    case "search":
                        renderServiceListByOption(request, response);
                        break;
                    case "detail":
                        viewSliderDetail(request, response);
                        break;
                    default:
                        break;
                }
            }
        } else {
            response.sendRedirect("./view/403.jsp");
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
        String action = request.getParameter("action");
        HttpSession session = request.getSession(true);
        String email = (String) session.getAttribute("email");
        StaffDAO staffDAO = new StaffDAO();
        Staff curStaff = staffDAO.getStaffByStaffEmail(email);
        if (curStaff != null) {
            if (!curStaff.getRole().equals("manager")) {
                response.sendRedirect("./view/403.jsp");
            } else {
                if (action.equals("changeStatus")) {
                    changeStatus(request, response);
                } else if (action.equals("delete")) {
                    deleteSlide(request, response);
                } else if (action.equals("add")) {
                    addSlide(request, response);
                } else if (action.equals("update")) {
                    updateSlide(request, response);
                }
            }
        } else {
            response.sendRedirect("./view/403.jsp");
        }

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

    protected void renderServiceListByOption(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Filtering logic based on request parameters (searchQuery and statusFilter)
        String searchQuery = request.getParameter("searchQuery");
        String status = request.getParameter("statusFilter") + "";
        String sortBy = request.getParameter("sortBy");
        String sortOrder = request.getParameter("sortOrder");
        int page = 1;

        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                // Handle error if the page parameter is not a valid integer
            }
        }

        // Call the filterSliders method in SliderDAO
        SliderDAO sliderData = new SliderDAO();
        System.out.println("Wuery" + searchQuery + "status: " + status + " Sort: " + sortBy + " " + sortOrder);
        List<Slider> filteredSliders = sliderData.filterSliders(status, searchQuery, sortBy, sortOrder, page);
        int pageSize = 10;
        int totalRecords = sliderData.countFilteredSliders(status, searchQuery);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
        PrintWriter out = response.getWriter();
        for (Slider filteredSlider : filteredSliders) {
            out.println("<tr>\n"
                    + "                            <td>" + filteredSlider.getSliderID() + "</td>\n"
                    + "                            <td>" + filteredSlider.getTitle() + "</td>\n"
                    + "                            <td>" + filteredSlider.getBrief() + "</td>\n"
                    + "                            <td><img src=\"" + filteredSlider.getImage() + "\" width=\"\" height=\"100\" alt=\"alt\"/></td>\n"
                    + "                            <td>" + filteredSlider.getBacklink() + "</td>\n"
                    + "                            <td>");
            if ("Active".equals(filteredSlider.getStatus())) {
                out.println("        <button class=\"btn btn-success update-status\" onclick=\"changeStatus(" + filteredSlider.getSliderID() + ", this)\" data-slider-id=\"" + filteredSlider.getSliderID() + "\" data-status=\"" + filteredSlider.getStatus() + "\">");
                out.println("            <span class=\"bi bi-eye-fill\"></span>");
                out.println("        </button>");
            } else {
                out.println("        <button class=\"btn btn-danger update-status\" onclick=\"changeStatus(" + filteredSlider.getSliderID() + ", this)\" data-slider-id=\"" + filteredSlider.getSliderID() + "\" data-status=\"" + filteredSlider.getStatus() + "\">");
                out.println("            <span class=\"bi bi-eye-slash-fill\"></span>");
                out.println("        </button>");
            }
            out.println("                            <td style=\"white-space: nowrap;\">\n"
                    + "                                      <button type=\"button\" class=\"btn btn-outline-primary\" onclick=\"window.location.href='slider?action=detail&slideID=" + filteredSlider.getSliderID() + "'\">\n"
                    + "  <i class=\"bi bi-pencil-square\"></i>\n"
                    + "  <span class=\"visually-hidden\">Edit</span>\n"
                    + "</button>\n"
                    + "                                <button type=\"button\" onclick=\"deleteSlide(" + filteredSlider.getSliderID() + ", this)\"  class=\"btn btn-outline-danger\">\n"
                    + "  <i class=\"bi bi-trash3-fill\"></i>\n"
                    + "  <span class=\"visually-hidden\">Button</span>\n"
                    + "</button>\n"
                    + "                            </td>\n"
                    + "                        </tr>"
            );

        }
        out.println("<ul class=\"pagination mt-4 d-flex justify-content-center\">");
        if (page > 1) {
            out.println("<li class=\"page-item\"><button class=\"page-link rounded-circle\" type=\"button\" onclick=\"renderSliderList(" + (page - 1) + ")\">Previous</button></li>");
        }

        for (int i = 1; i <= totalPages; i++) {
            out.println("<li class=\"page-item " + (i == page ? "active" : "") + "\"><button class=\"page-link rounded-circle\" type=\"button\" onclick=\"renderSliderList(" + i + ")\">" + i + "</button></li>");
        }

        if (page < totalPages) {
            out.println("<li class=\"page-item\"><button class=\"page-link rounded-circle\" type=\"button\" onclick=\"renderSliderList(" + (page + 1) + ")\">Next</button></li>");
        }
        out.println("</ul>");

        out.println("</div>");
        out.flush();
        out.close();
    }

    protected void changeStatus(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int sliderId = Integer.parseInt(request.getParameter("sliderID"));
        SliderDAO SliderData = new SliderDAO();
        Slider slide = SliderData.getSlideByID(sliderId);
        slide.setStatus(slide.getStatus().equalsIgnoreCase("Active") ? "Inactive" : "Active");
        SliderData.updateSlider(slide);
    }

    private void deleteSlide(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int sliderId = Integer.parseInt(request.getParameter("sliderID"));
        SliderDAO SliderData = new SliderDAO();
        SliderData.deleteSlider(sliderId);
    }

    private void addSlide(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title");
        String brief = request.getParameter("brief");
        String backlink = request.getParameter("backlink");
        String status = request.getParameter("status");

        String newImg = uploadImage(request);

        if (newImg == null) {
            response.sendRedirect("slider?action=all");
            request.getSession().setAttribute("message", "Slide Image added error");
        } else {
            SliderDAO SliderData = new SliderDAO();
            boolean success = SliderData.addSlide(title, brief, newImg, backlink, status);
            if (success) {
                response.sendRedirect("slider?action=all");
                request.getSession().setAttribute("message", "Slide added successfully");
            } else {
                response.sendRedirect("slider?action=all");
                request.getSession().setAttribute("message", "Slide added error");
            }
        }
    }

    private void updateSlide(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sliderIDStr = request.getParameter("sliderID");
        int sliderID = Integer.parseInt(sliderIDStr);
        String title = request.getParameter("title");
        String brief = request.getParameter("brief");
        String backlink = request.getParameter("backlink");
        String status = request.getParameter("status");

        String newImg = uploadImage(request);

        SliderDAO SliderData = new SliderDAO();
        Slider slide = SliderData.getSlideByID(sliderID);

        // Cập nhật thông tin slide
        slide.setTitle(title);
        slide.setBrief(brief);
        slide.setBacklink(backlink);
        slide.setStatus(status);

        // Kiểm tra xem có ảnh mới không
        if (newImg != null) {
            slide.setImage(newImg);
        }

        boolean success = SliderData.updateSlider(slide);
        if (success) {
            request.getSession().setAttribute("message", "Slide updated successfully");
        } else {
            request.getSession().setAttribute("message", "Slide updated error");
        }

        response.sendRedirect("slider?action=detail&slideID=" + sliderIDStr);
    }

    private String uploadImage(HttpServletRequest request) throws ServletException, IOException {
        Part filePart = request.getPart("image");
        String contentType = filePart.getContentType();

        if (contentType != null && contentType.startsWith("image")) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            if (!fileName.isEmpty()) {
                try {
                    String realPath = getServletContext().getRealPath("/resources/img");
                    File uploadDir = new File(realPath);

                    if (!uploadDir.exists()) {
                        uploadDir.mkdir();
                    }

                    String filePath = realPath + File.separator + fileName;
                    filePart.write(filePath);

                    return "resources/img/" + fileName;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    private void viewSliderDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("slideID");
        int id = Integer.parseInt(idStr);
        SliderDAO SliderData = new SliderDAO();
        Slider slide = SliderData.getSlideByID(id);
        request.setAttribute("slider", slide);
        request.getRequestDispatcher("./view/slider-detail.jsp").forward(request, response);
    }

}
