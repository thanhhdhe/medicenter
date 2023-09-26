package controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import Database.CategoryServiceDAO;
import Database.ServiceDAO;
import Database.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import model.Service;
import model.Staff;

/**
 *
 * @author Admin
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 10, // 10 KB
        maxFileSize = 1024 * 300, // 300 KB
        maxRequestSize = 1024 * 1024 // 1 MB 
)
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
        switch (event) {
            case "service-list":
                // If 'event' is equal to "service-list", forward the request to the service-list.jsp page
                request.getRequestDispatcher("./view/service-list.jsp").forward(request, response);
                break;
            case "service-list-userchoose":
                // If 'event' is equal to "service-list-userchoose", call the renderServiceListByOption method
                renderServiceListByOption(request, response);
                break;
            case "detail":
                String id = request.getParameter("id");
                ServiceDAO serviceDAO = new ServiceDAO();
                Service services = serviceDAO.getServiceByID(id);
                request.setAttribute("service", services);
                StaffDAO staffDAO = new StaffDAO();
                List<Staff> staffList = staffDAO.getDoctorByServices(id);
                request.setAttribute("doctor", staffList);
                request.getRequestDispatcher("./view/servicedetail.jsp").forward(request, response);
                break;
            case "manage":
                // If 'event' is equal to "manage", forward the request to the service-manage.jsp page
                request.getRequestDispatcher("./view/service-manage.jsp").forward(request, response);
                break;
            case "service-list-managerchoose":
                renderServiceListByManagerOption(request, response);
                break;
            case "edit":
                edit(request, response);
                break;
            case "sent-to-add":
                request.getRequestDispatcher("./view/add-service.jsp").forward(request, response);
                break;
            case "to-detail-manage":
                toDetailManage(request, response);
                break;
            default:
                break;
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
        String event = request.getParameter("event");
        switch (event) {
            case "hide":
                hideService(request, response);
                break;
            case "show":
                showService(request, response);
                break;
            case "edit-service":
                editService(request, response);
                break;
            case "add-service":
                addService(request, response);
                break;
                
        }
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
        for (int i = 1; i <= (serviceDAO.getCountOfServicesUserChoose(serviceTitle, serviceType, staff) + 4) / 5; i++) {
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
                    + "                                <a href=\"service?event=detail&id=" + service.getServiceID() + "\" class=\"btn btn-primary btn-block\"> Details </a>\n"
                    + "                            </p>\n"
                    + "                        </div>\n"
                    + "                    </div>");
        }
        out.flush();
        out.close();
    }

    protected void renderServiceListByManagerOption(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        ServiceDAO serviceDAO = new ServiceDAO();
        CategoryServiceDAO categoryServiceDAO = new CategoryServiceDAO();

        // Get the values of the parameters and handle null values
        String keyword = (request.getParameter("keyword") + "").equals("null") ? "" : (request.getParameter("keyword") + "");
        String sortType = (request.getParameter("sortType") + "").equals("null") ? "" : (request.getParameter("sortType") + "");
        int page = (request.getParameter("page") + "").equals("page") ? 1 : Integer.parseInt(request.getParameter("page") + "");

        // Get the sorted and paged services based on the options
        List<Service> serviceList = serviceDAO.getPaginatedSortedAndFilteredServices((page - 1) * 5, 5, sortType, keyword);

        // Generate the pagination HTML
        String paginationHtml = "";
        for (int i = 1; i <= (serviceDAO.countFilteredServices(keyword) + 4) / 5; i++) {
            paginationHtml += "<button class=\"pagination-btn ms-2 " + (i == page ? "active" : "inactive")
                    + "\" data-page=\"" + i + "\" onclick=\"loadPageServices(" + i + ")\">" + i + "</button>";
        }
        // Add the pagination HTML to the response header
        response.addHeader("pagination", paginationHtml);

        // Render the service list
        for (Service service : serviceList) {
            out.print("<div id=\"" + service.getServiceID() + "\" class=\"service row p-3");
            if (!service.getStatus()) {
                out.print(" inactives");
            }
            out.print("\">\n"
                    + "                        <div class=\"col-md-3\">\n"
                    + "                            <img src=\"" + service.getThumbnail() + "\" alt=\"ìmg\" class=\"w-100 h-100 object-contain\" />\n"
                    + "                        </div>\n"
                    + "                         <div class=\"col-md-6\">\n"
                    + "                            <div>\n"
                    + "                                <h3>ID: " + service.getServiceID() + "</h3>\n"
                    + "                                <h3>" + service.getTitle() + "</h3>\n"
                    + "                            </div>\n"
                    + "                            <div class=\"d-flex mt-1 mb-2 align-items-end\">\n"
                    + "                                <h5 class=\"pe-2\">Category:<p class=\"fw-normal\">" + categoryServiceDAO.getCategoryServiceByID(service.getCategoryID() + "").getCategoryName() + "</p></h5>\n"
                    + "                            </div>\n"
                    + "                            \n"
                    + "                            <div class=\"text-black-50\">\n"
                    + "                                <p class=\"clamp\">\n"
                    + "                                    " + service.getBrief() + "\n"
                    + "                                </p>\n"
                    + "                            </div>\n"
                    + "                        </div>"
                    + "                        <div class=\"info-aside col-md-3\">\n"
                    + "                            <div class=\"price-wrap\">");
            if (service.getSalePrice() <= 0) {
                out.print("<span class=\"price h5\"> $" + service.getOriginalPrice() + " </span>");
            } else {
                out.print("<span class=\"price h5\"> $" + service.getSalePrice() + " </span>\n"
                        + "                                <del class=\"price-old\"> $" + service.getOriginalPrice() + "</del>");
            }
            out.print("</div>\n");
            if (service.getStatus()) {
                out.print("<h5 class=\"status text-success mt-2\">Active</h5>");
            } else {
                out.print("<h5 class=\"status text-black-50 mt-2\">Inactive</h5>");
            }
            out.print("<br />\n"
                    + "       <div class=\"d-flex h-50 align-content-center flex-wrap\" >                      <div class=\"d-flex\">\n");
            if (service.getStatus()) {
                out.print("<button class=\"button-icon me-2 showhide hide-service-button\" data-service-id=\"" + service.getServiceID() + "\" onclick=\"handleUpdate()\" ><img src=\"resources/img/icon/hide.png\" alt=\"alt\"/></button>");
            } else {
                out.print(" <button class=\"button-icon me-2 showhide show-service-button\" data-service-id=\"" + service.getServiceID() + "\" onclick=\"handleUpdate()\"  ><img src=\"resources/img/icon/visual.png\" alt=\"alt\"/></button> ");
            }

            out.print(" <button class=\"button-icon me-2\"><a href=\"service?event=detail&id="+service.getServiceID()+"\"><img src=\"resources/img/icon/detail.png\" alt=\"alt\"/></a></button>\n" +
"                                <button class=\"button-icon\"><a href=\"service?event=edit&id="+service.getServiceID()+"\"><img src=\"resources/img/icon/pen.png\" alt=\"alt\"/></a></button>"
                    + "                            </div> </div>"
                    + "                        </div>\n"
                    + "                    </div>");
        }
        out.flush();
        out.close();
    }

    protected void hideService(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServiceDAO serviceDAO = new ServiceDAO();
        String ID = (String) request.getParameter("serviceId");
        Service service = serviceDAO.getServiceByID(ID);
        service.setStatus(false);
        serviceDAO.update(service);
    }

    protected void showService(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServiceDAO serviceDAO = new ServiceDAO();
        String ID = (String) request.getParameter("serviceId");
        Service service = serviceDAO.getServiceByID(ID);
        service.setStatus(true);
        serviceDAO.update(service);
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        ServiceDAO serviceDAO = new ServiceDAO();
        Service service = serviceDAO.getServiceByID(id);
        request.setAttribute("service", service);
        request.setAttribute("ServiceID", id);
        request.getRequestDispatcher("./view/service-edit.jsp").forward(request, response);
    }

    protected void toDetailManage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        ServiceDAO serviceDAO = new ServiceDAO();
        Service service = serviceDAO.getServiceByID(id);
        request.setAttribute("service", service);
        request.setAttribute("ServiceID", id);
        request.getRequestDispatcher("./view/service-detail-manage.jsp").forward(request, response);
    }
    
    protected void editService(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServiceDAO serviceDAO = new ServiceDAO();
        String serviceID = request.getParameter("ServiceID");
        String title = request.getParameter("Title");
        String brief = request.getParameter("Brief");
        String status = request.getParameter("status");
        String categoryID = request.getParameter("serviceType");
        String originalPrice = request.getParameter("OriginalPrice");
        String salePrice = request.getParameter("SalePrice");
        String description = request.getParameter("Description");
        Service service = serviceDAO.getServiceByID(serviceID);
        String newImg = request.getParameter("serviceURL") + "";
        String imageURL = service.getThumbnail();
        LocalDate currentDate = LocalDate.now();

        // Chuyển đổi thành java.sql.Date
        Date updateDate = Date.valueOf(currentDate);

        try {
            Part filePart = request.getPart("serviceImage");
            String fileName = filePart.getSubmittedFileName();

            // Lưu tệp vào đường dẫn cụ thể trên server
            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String url = uploadPath + File.separator + fileName;
            if (fileName.length() > 0) {
                imageURL = "./uploads/" + fileName;
            } else {
                throw new IOException();
            }
            filePart.write(url);
        } catch (Exception e) {
            if (newImg.length() > 0) {
                imageURL = newImg;
            }
        }
        
        //validate input
        boolean check = true;
        if (title.isEmpty()) {
            check = false;
            request.setAttribute("titleErr", "*Title can not be left blank!");
        }

        if (originalPrice.isEmpty()) {
            check = false;
            request.setAttribute("originalPriceErr", "*Original Price can not be left blank!");
        } else if (Double.parseDouble(originalPrice) < 0) {
            check = false;
            request.setAttribute("originalPriceErr", "*Original Price can not less than 0!");
        }
        if (!salePrice.isEmpty()) {
            if (Double.parseDouble(salePrice) < 0) {
                check = false;
                request.setAttribute("salePriceErr", "*Sale Price can not less than 0!");
            }
        }
        if (!check) {
            request.setAttribute("validate", check);
            request.setAttribute("service", service);
            request.getRequestDispatcher("./view/service-edit.jsp").forward(request, response);
        } else {
            Service newService = new Service(Integer.parseInt(serviceID + ""), title, brief, imageURL, Integer.parseInt(categoryID), Double.parseDouble(originalPrice), Double.parseDouble(salePrice), description, updateDate, Boolean.getBoolean(status));
            serviceDAO.update(newService);
            response.sendRedirect("service?event=manage");
        }
    }
    
    protected void addService(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServiceDAO serviceDAO = new ServiceDAO();
        String title = request.getParameter("Title");
        String brief = request.getParameter("Brief");
        String status = request.getParameter("status");
        String categoryID = request.getParameter("serviceType");
        String originalPrice = request.getParameter("OriginalPrice");
        String salePrice = request.getParameter("SalePrice");
        String description = request.getParameter("Description");
        String newImg = request.getParameter("serviceURL")+"";
        String imageURL = "resources/img/image1.jpg";
        LocalDate currentDate = LocalDate.now();

        // Chuyển đổi thành java.sql.Date
        Date updateDate = Date.valueOf(currentDate);

        try {
            Part filePart = request.getPart("serviceImage");
            String fileName = filePart.getSubmittedFileName();

            // Lưu tệp vào đường dẫn cụ thể trên server
            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String url = uploadPath + File.separator + fileName;
            if (fileName.length() > 0) {
                imageURL = "./uploads/" + fileName;
            } else {
                throw new IOException();
            }

            filePart.write(url);
        } catch (Exception e) {
            if (newImg.length() > 0) {
                imageURL = newImg;
            }
        }
        //validate input
        boolean check = true;
        if (title.isEmpty()) {
            check = false;
            request.setAttribute("titleErr", "*Title can not be left blank!");
        }

        if (originalPrice.isEmpty()) {
            check = false;
            request.setAttribute("originalPriceErr", "*Original Price can not be left blank!");
        } else if (Double.parseDouble(originalPrice) < 0) {
            check = false;
            request.setAttribute("originalPriceErr", "*Original Price can not less than 0!");
        }
        if (!salePrice.isEmpty()) {
            if (Double.parseDouble(salePrice) < 0) {
                check = false;
                request.setAttribute("salePriceErr", "*Sale Price can not less than 0!");
            }
        }
        if (!check) {
            System.out.println("aa");
            request.setAttribute("validate", check);
            request.setAttribute("title", title);
            request.setAttribute("brief", brief);
            request.setAttribute("categoryID", categoryID);
            request.setAttribute("originalPrice", originalPrice);
            request.setAttribute("salePrice", salePrice);
            request.setAttribute("description", description);
            request.getRequestDispatcher("./view/add-service.jsp").forward(request, response);
        } else {
            Service newService = new Service(title, brief, imageURL, Integer.parseInt(categoryID), Double.parseDouble(originalPrice), Double.parseDouble(salePrice), description, updateDate, Boolean.getBoolean(status));
            serviceDAO.insert(newService);
            response.sendRedirect("service?event=manage");
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

}
