<%@page import = "model.*" %>
<%@page import = "Database.*" %>
<%@page import = "java.util.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <jsp:include page="./layout/Head.jsp"/> 
    <link rel="stylesheet" href="./resources/css/services-style.css">

    <body>
        <jsp:include page="./layout/Header.jsp"/>

        <%ServiceDAO serviceDAO = new ServiceDAO();
   CategoryServiceDAO categoryServiceDAO = new CategoryServiceDAO();
   StaffDAO staffDAO = new StaffDAO();  %>   
        <section class="section">
            <div class="container">
                <div class="row align-items-center">

                    <div class="row mt-5 mb-5">
                        <div class="col-md-3">
                            <div class="card p-3 mt-4">
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
                            </div>
                        </div>
                        <div class="col-md-9 card mt-4 mb-4">
                            <div class="row p-4">
                                <div class="col-md-6 mt-4 mt-sm-0 pt-2 pt-sm-0">
                                    <div class="section-title ">
                                        <h4 class="title mt-4">${service.title}</h4>
                                        <p class="text-muted">${service.serviceDetail}</p>
                                        <h5 class="text-muted">$${service.salePrice}</h5>
                                        <ul class="list-unstyled text-warning h5 mb-0">
                                            <!-- Add list items here if needed -->
                                        </ul>
                                        <h5 class="mt-4 py-2">Mô tả :</h5>
                                        <p class="text-muted" style="text-align:justify">${service.brief}</p>
                                        <div class="mt-4 pt-2">
                                            <!-- Add any additional content here if needed -->
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6 mt-4">
                                    <!-- Add your image here -->
                                    <img src="${service.thumbnail}" class="img-fluid rounded" alt="">
                                </div>
                            </div>
                            <div class="text-center">
                                <button class="btn btn-primary mb-4">Book Now</button>
                            </div>
                        </div>

                    </div>


                </div>
            </div>


        </section>

        <jsp:include page="./layout/footer.jsp"/>


    </body>

</html>