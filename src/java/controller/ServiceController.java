package controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import Database.ServiceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Service;

/**
 *
 * @author Admin
 */
public class ServiceController extends HttpServlet {

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
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServiceController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServiceController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        // Get the 'event' parameter from the request
        String event = request.getParameter("event");

        // Check the value of 'event'
        if (event.equals("service-list")) {
            // If 'event' is equal to "service-list", forward the request to the service-list.jsp page
            request.getRequestDispatcher("./view/service-list.jsp").forward(request, response);
        } else if (event.equals("service-list-userchoose")) {
            // If 'event' is equal to "service-list-userchoose", call the renderServiceListByOption method
            renderServiceListByOption(request, response);
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
        // Call the processRequest method
        processRequest(request, response);
    }

    protected void renderServiceListByOption(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        ServiceDAO serviceDAO = new ServiceDAO();

        // Get the values of the parameters and handle null values
        String serviceTitle = (request.getParameter("serviceTitle") + "").equals("null") ? "" : (request.getParameter("serviceTitle") + "");
        String serviceType = (request.getParameter("serviceType") + "").equals("null") ? "" : (request.getParameter("serviceType") + "");
        String staff = (request.getParameter("staff") + "").equals("null") ? "" : (request.getParameter("staff") + "");
        int page = (request.getParameter("page") + "").equals("page") ? 1 : Integer.parseInt(request.getParameter("page") + "");

        // Get the sorted and paged services based on the options
        List<Service> serviceList = serviceDAO.getSortedPagedServicesByOption((page - 1) * 5, 5, serviceTitle, serviceType, staff);

        // Generate the pagination HTML
        String paginationHtml = "";
        for (int i = 1; i <= (serviceDAO.getCountOfServicesUserChoose(serviceTitle, serviceType, staff) / 5 + 1) * 2 / 2; i++) {
            paginationHtml += "<button class=\"pagination-btn ms-2 " + (i == page ? "active" : "inactive")
                    + "\" data-page=\"" + i + "\" onclick=\"loadPageServices(" + i + ")\">" + i + "</button>";
        }
        // Add the pagination HTML to the response header
        response.addHeader("pagination", paginationHtml);

        // Render the service list
        for (Service service : serviceList) {
            out.print("<div class=\"service row p-3\">\n"
                    + "                        <div class=\"col-md-3\">\n"
                    + "                            <img src=\"" + service.getThumbnail() + "\" alt=\"ìmg\" class=\"w-100 h-100 object-contain\" />\n"
                    + "                        </div>\n"
                    + "                        <div class=\"col-md-6\">\n"
                    + "                            <h3>" + service.getTitle() + "</h3>\n"
                    + "                         <div class=\"text-container\">"
                    + "                            <p class=\"truncate\">\n"
                    + "                                " + service.getBrief() + "\n"
                    + "                            </p>\n"
                    + "                         </div>\n"
                    + "                        </div>\n"
                    + "                        <div class=\"info-aside col-md-3\">\n"
                    + "                            <div class=\"price-wrap\">");
            if (service.getSalePrice() <= 0) {
                out.print("<span class=\"price h5\"> $" + service.getOriginalPrice() + " </span>");
            } else {
                out.print("<span class=\"price h5\"> $" + service.getSalePrice() + " </span>\n"
                        + "                                <del class=\"price-old\"> $" + service.getOriginalPrice() + "</del>");
            }
            out.print("</div>\n"
                    + "                            <br />\n"
                    + "                            <p>\n"
                    + "                                <a href=\"#\" class=\"btn btn-primary btn-block\"> Details </a>\n"
                    + "                            </p>\n"
                    + "                        </div>\n"
                    + "                    </div>");
        }
        out.flush();
        out.close();
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
