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
        Service service = (Service) request.getAttribute("service");
   CategoryServiceDAO categoryServiceDAO = new CategoryServiceDAO();
   StaffDAO staffDAO = new StaffDAO();  ReservationDAO reservationDAO = new ReservationDAO();%>   
        <section class="section">
            <div class="container">
                <div class="row bg-light align-items-center my-5">

                    <div class="col-md-4">
                        <img src="<%=service.getThumbnail()%>" alt="img" class="w-100 h-100 object-contain" />
                    </div>
                    <div class="col-md-8 p-5">
                        <div class="row mb-3">
                            <h3 class="col-md-3 align-self-center">ID: <%=service.getServiceID()%></h3>
                            <h3 class="col-md-6 align-self-center"><%=service.getTitle()%></h3>
                            <div class="col-md-1 d-flex justify-content-center">
                                <button class="button-edit rounded-0">
                                    <a class="text-decoration-none" href="service?event=edit&id=<%=service.getServiceID()%>">
                                        <img src="resources/img/icon/edit-tool.png" alt="alt"/>
                                    </a>
                                </button>
                            </div>
                        </div>
                        <div class="d-flex mt-1  mb-3">
                            <h5 class="pe-2">Category:</h5><p class="fw-normal m-0"><%=categoryServiceDAO.getCategoryServiceByID(service.getCategoryID()+"").getCategoryName()%></p>
                        </div>
                        <div class="row mb-3">
                            <div class="price-wrap col-md-3">
                                <%if(service.getSalePrice()<=0){%>
                                <span class="price h5"> $<%=service.getOriginalPrice()%> </span>
                                <%}else{%>
                                <span class="price h5"> $<%=service.getSalePrice()%> </span>
                                <del class="price-old"> $<%=service.getOriginalPrice()%></del>
                                <%}%>
                            </div>
                            <div class="col-md-6 mb-3">
                                <div class="form-check form-switch d-flex align-items-center">
                                    <input class="form-check-input me-2" type="checkbox" id="flexSwitchCheckDefault" <%=service.getStatus()?"checked":""%> onchange="sendToDetailManage(<%=service.getServiceID()%>)" style="height: 24px;width: 47px;">
                                    <%if(service.getStatus()){%><p class="status bg-success text-white m-0 p-1 fit-content-width rounded-1 text-center">Active</p> 
                                    <%}else{%> 
                                    <p class="status bg-danger text-light m-0 p-1 fit-content-width rounded-1 text-center">Inactive</p><%}%>
                                </div>

                            </div>
                        </div>
                        <div class="mb-3">
                            <h5>QUICK OVERVIEW:</h5>
                            <p class="text-black-50">
                                <%=service.getBrief()%>
                            </p>
                        </div>

                    </div>
                </div>

                <ul class="nav nav-tabs m-0 justify-content-start" id="ex1" role="tablist">
                    <li class="nav-item" role="presentation">
                        <a
                            class="nav-link active"
                            id="ex1-tab-1"
                            data-mdb-toggle="tab"
                            href="#ex1-tabs-1"
                            role="tab"
                            >Service Description</a
                        >
                    </li>
                    <li class="nav-item" role="presentation">
                        <a
                            class="nav-link"
                            id="ex1-tab-2"
                            data-mdb-toggle="tab"
                            href="#ex1-tabs-2"
                            role="tab"
                            >Doctor</a
                        >
                    </li>
                    <li class="nav-item" role="presentation">
                        <a
                            class="nav-link"
                            id="ex1-tab-3"
                            data-mdb-toggle="tab"
                            href="#ex1-tabs-3"
                            role="tab"
                            >Number of Person</a
                        >
                    </li>
                </ul>
                <!-- Tabs navs -->
                <!-- Tabs content -->
                <div class="tab-content rounded-0" id="ex1-content">
                    <div
                        class="tab-pane fade show active"
                        id="ex1-tabs-1"
                        role="tabpanel"
                        aria-labelledby="ex1-tab-1"
                        >
                        <div class=" pt-5 pb-5  ms-3"><%=service.getServiceDetail()%></div>
                    </div>
                    <div class="tab-pane fade" id="ex1-tabs-2" role="tabpanel" aria-labelledby="ex1-tab-2">
                        <!-- doctor list of service -->
                        <div class="row pt-5 pb-5 ms-3">
                            <%List<Staff> staffList = staffDAO.getDoctorByServices(service.getServiceID()+"");
                            for (Staff staff : staffList) {%>
                            <div class="col-lg-4 col-md-6 col-12 mb-4">
                                <div class="custom-block bg-white shadow-lg">
                                    <img src="<%=staff.getProfileImage()%>" class="mt-0 custom-block-image img-fluid" alt="">
                                    <div class="d-flex mt-4">
                                        <div>
                                            <h4><strong><%=staff.getFullName()%></strong></h4>

                                            <p class="mb-0"><%=staff.getGender()%></p>
                                        </div>

                                        <span class="badge bg-design rounded-pill ms-auto">14</span>
                                    </div>
                                </div>
                            </div>
                            <%}%>

                        </div>
                    </div>
                    <div class="tab-pane fade" id="ex1-tabs-3" role="tabpanel" aria-labelledby="ex1-tab-3">
                        <div class="d-flex pt-5 pb-5 ms-3">
                            <h2>Number of Person: </h2>
                            <h2 class="text-black-50 ms-2"><%=reservationDAO.countReservationsForService(service.getServiceID()+"")%></h2>
                        </div>
                    </div>
                </div>
                <!-- Tabs content -->

            </div>
        </div>


    </section>

    <jsp:include page="./layout/footer.jsp"/>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="./resources/js/services-details-manage.js"></script>
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