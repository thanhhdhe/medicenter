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
                            <div id="service-list" class="overflow-auto" style="max-height: 56.4rem;">
                            </div>
                        </div>

                        <div class="col-md-9 card mt-4 mb-4">
                            <div class="row p-4">
                                <div class="col-md-6 mt-4">
                                    <!-- Add your image here -->
                                    <img src="${service.thumbnail}" class="img-fluid rounded" alt="">
                                </div>
                                <div class="col-md-6 mt-4 mt-sm-0 pt-2 pt-sm-0">
                                    <div class="section-title ">
                                        <h4 class="title mt-4">${service.title}</h4>
                                        <p class="text-muted">${service.serviceDetail}</p>
                                        <h5 class="text-muted" style="text-decoration: line-through;">$${service.originalPrice}</h5>
                                        <h5 class="text-muted">$${service.salePrice}</h5>
                                        <ul class="list-unstyled text-warning h5 mb-0">
                                            <!-- Add list items here if needed -->
                                        </ul>
                                        <h5 class="mt-4 py-2">Description:</h5>
                                        <p class="text-muted" style="text-align:justify">${service.brief}</p>
                                        <div class="mt-4 pt-2">
                                            <!-- Add any additional content here if needed -->
                                        </div>
                                    </div>
                                </div>

                            </div>
                            <div class="row p-4 mt-sm-0">
                                <h4 class="title mt-4 text-center"><strong>Doctor</strong></h4>
                                <div class="row">
                                    <c:forEach items="${requestScope.doctor}" var="doc">
                                        <div class="col-lg-4 col-md-6 col-12 mb-4">
                                            <div class="custom-block bg-white shadow-lg">
                                                <!--                                                <input class="form-check-input" type="radio" name="doctorRadio" value="no1">-->
                                                <img src="${doc.profileImage}" class="mt-0 custom-block-image img-fluid" alt="">
                                                <div class="d-flex mt-4">
                                                    <div>
                                                        <h4><strong>${doc.fullName}</strong></h4>

                                                        <p class="mb-0">${doc.gender}</p>
                                                    </div>

                                                    <span class="badge bg-design rounded-pill ms-auto">14</span>
                                                </div>
                                                <div class="text-center">
                                                    <a id="bookNowLink" href="reservationdetail?serviceID=${service.serviceID}&staffID=${doc.staffID}"><button class="btn btn-primary mb-4">Book Now</button></a>
                                                </div>


                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                            <div class="text-center">
                                <a id="bookNowLink" href="reservationdetail?serviceID=${service.serviceID}"><button class="btn btn-primary mb-4">Book an appointment now</button></a>
                            </div>

                        </div>

                    </div>


                </div>
            </div>


        </section>

        <jsp:include page="./layout/footer.jsp"/>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="./resources/js/services-details-script.js"></script>
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