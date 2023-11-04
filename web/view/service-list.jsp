<%-- 
    Document   : ServiceList
    Created on : Sep 12, 2023, 7:22:21 PM
    Author     : Admin
--%>
<%@page import = "model.*" %>
<%@page import = "Database.*" %>
<%@page import = "java.util.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <!-- Google Fonts -->
        <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Raleway:300,300i,400,400i,500,500i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
            crossorigin="anonymous"
            />
        <link rel="stylesheet" href="./resources/css/services-style.css">
        <title>Medilab</title>
    </head>
    <body>
        <jsp:include page="./layout/Header.jsp" />
        <%ServiceDAO serviceDAO = new ServiceDAO();
        CategoryServiceDAO categoryServiceDAO = new CategoryServiceDAO();
        StaffDAO staffDAO = new StaffDAO();  %>
        <div class="d-flex justify-content-center mt-5">
            <div class="container row mt-5 mb-4">
                <div class="col-md-3">
                    <div class="card p-3">
                        <input type="text" name="serviceTitle" placeholder="Search" class="form-control" />
                        <select class="form-select text-primary mt-3" name="serviceType"  class="form-select">
                            <option selected value="">Service Type</option>
                            <%List<CategoryService> categoryServiceList = categoryServiceDAO.getAllCategoryServices();
            for (CategoryService categoryService : categoryServiceList) {%>
                            <option value="<%=categoryService.getCategoryID()%>"><%=categoryService.getCategoryName()%></option>
                            <%}%>

                        </select>
                        <select class="form-select text-primary mt-3" name="staff"  class="form-select">
                            <option selected value="">Doctor</option>
                            <%List<Staff> staffList = staffDAO.getStaffsByRole("doctor");
                            for (Staff staff : staffList) {%>
                            <option value="<%=staff.getStaffID()%>"><%=staff.getFullName()%></option>
                            <%}%>
                        </select>
                        <a href="service?event=to-contact-link" class="mt-3 ms-2">Contact Us</a>
                    </div>
                </div>
                <div class="col-md-9" id="service-list">

                    <!-- Services List -->
                    <%List<Service> list = serviceDAO.getSortedActivePaged(0, 5);
                for (Service service : list) {%>
                    <div class="service row p-3">
                        <div class="col-md-3">
                            <img src="<%=service.getThumbnail()%>" alt="ìmg" class="w-100 h-100 object-contain" />
                        </div>
                        <div class="col-md-6">
                            <h3><%=service.getTitle()%></h3>
                            <div class="text-container">
                                <p class="truncate">
                                    <%=service.getBrief()%>
                                </p>
                            </div>
                        </div>
                        <div class="info-aside col-md-3">
                            <div class="price-wrap">
                                <%if(service.getSalePrice()<=0){%>
                                <span class="price h5"> $<%=service.getOriginalPrice()%> </span>
                                <%}else{%>
                                <span class="price h5"> $<%=service.getSalePrice()%> </span>
                                <del class="price-old"> $<%=service.getOriginalPrice()%></del>
                                <%}%>
                            </div>
                            <!-- info-price-detail // -->
                            <br />
                            <p>
                            <form action="service?event=detail" method="POST">
                                <input type="hidden" name="serviceID" value="<%=service.getServiceID()%>">
                                
                                <input type="submit"  class="btn btn-primary btn-block" value="Details" />
                            </form>
                            </p>
                        </div>
                    </div>
                    <%}%>


                </div>
            </div>
        </div>
        <div class="d-flex justify-content-center mb-5" id="pagination-container">
            <button class="pagination-btn ms-2 active" data-page="1">1</button>
            <%for (int i = 2; i <=(serviceDAO.getActiveServiceCount()+4)/5; i++) {%>
            <button class="pagination-btn ms-2 inactive" data-page="<%=i%>"><%=i%></button>
            <%}%>
        </div>
        <jsp:include page="layout/footer.jsp" />
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="./resources/js/service-list-script.js"></script>
        <script
            src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
            integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
            crossorigin="anonymous"
        ></script>
        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
            integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
            crossorigin="anonymous"
        ></script>
    </body>
</html>
