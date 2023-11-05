<%-- 
    Document   : HomePage
    Created on : 12-Sep-2023, 20:29:49
    Author     : Admin
--%>
<%@page import = "model.*" %>
<%@page import = "Database.*" %>
<%@page import = "java.util.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <title>Staff dashboard</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport" />
        <meta content="" name="keywords" />
        <meta content="" name="description" />

        <!-- Favicon -->
        <link href="img/favicon.ico" rel="icon" />

        <!-- Google Web Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com" />
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
        <link
            href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600;700&display=swap"
            rel="stylesheet"
            />

        <!-- Icon Font Stylesheet -->
        <link
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css"
            rel="stylesheet"
            />
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
            rel="stylesheet"
            />

        <!-- Libraries Stylesheet -->
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
            crossorigin="anonymous"
            />

        <!-- Customized Bootstrap Stylesheet -->
        <link href="css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="./resources/css/staff-dashboard.css">
        <link rel="stylesheet" href="./resources/css/services-style.css">
    </head>

    <body>
        <%
       String email = (String) session.getAttribute("email");
       StaffDAO staffDAO = new StaffDAO();
       Staff curStaff = staffDAO.getStaffByStaffEmail(email);
       boolean isManager = false;
        boolean isStaff = false;
        %>
        <div class="container-fluid position-relative bg-white d-flex p-0">
            <%if(curStaff!=null){ 
            if(curStaff.getRole().equals("manager")) isManager=true;            
            if(curStaff.getRole().equals("doctor")||curStaff.getRole().equals("nurse")) isStaff=true;%>
           <!-- Sidebar Start -->
            <div class="sidebar pe-4 pb-3">
                <nav class="navbar navbar-light">
                    <a href="staff?event=sent-to-home" class="navbar-brand mx-4 mb-3">
                        <h3 class="text-light">
                            <i class="fa fa-hashtag me-2"></i>Medilab
                        </h3>
                    </a>
                    <div class="d-flex align-items-center ms-4 mb-4">
                        <div class="position-relative">
                            <img
                                class="rounded-circle"
                                src="<%=curStaff.getProfileImage()%>"
                                alt=""
                                style="width: 40px; height: 40px"
                                />
                            <div
                                class="bg-success rounded-circle border border-2 border-white position-absolute end-0 bottom-0 p-1"
                                ></div>
                        </div>
                        <div class="ms-3 text-light">
                            <h6 class="mb-0"><%=curStaff.getStaffName()%></h6>
                            <span><%=curStaff.getRole()%></span>
                        </div>
                    </div>
                    <%if(isStaff){%>    
                    <div class="navbar-nav w-100  text-light">
                        <a href="staff?event=send-to-reservations-list" class="nav-item nav-link"
                           ><i class="fas fa-list-alt"></i>Reservations List</a
                        >
                    </div>  
                    <div class="navbar-nav w-100  text-light">
                        <a href="staff?event=send-to-medical-examination" class="nav-item nav-link"
                           ><i class="far fa-check-square"></i>Medical examination</a
                        >
                    </div>
                    <%}%>
                    <%if(isManager){%>
                    <div class="navbar-nav w-100  text-light">
                        <a href="staff?event=send-to-medical-examination-manage" class="nav-item nav-link"
                           ><i class="far fa-check-square"></i>Medical examination</a
                        >
                    </div>
                    <div class="navbar-nav w-100  text-light">
                        <a href="reservationcontactmanager?event=reservation-list" class="nav-item nav-link"
                           ><i class="fas fa-list-alt"></i>Reservations Manager</a
                        >
                    </div>
                    <div class="navbar-nav w-100 text-light">
                        <a href="user?action=search" class="nav-item nav-link"
                           ><i class="bi bi-people-fill"></i>User</a
                        >
                    </div>
                    <div class="navbar-nav w-100 text-light">
                        <a href="feedback" class="nav-item nav-link"
                           ><i class="far fa-file-alt"></i>Feedback</a
                        >
                    </div>
                    <div class="navbar-nav w-100 text-light">
                        <a href="service?event=manage" class="nav-item nav-link active"
                           ><i class="fas fa-stethoscope"></i>Services</a
                        >
                    </div>
                    <div class="navbar-nav w-100 text-light">
                        <a href="postManage" class="nav-item nav-link"
                           ><i class="fas fa-stethoscope"></i>Post</a
                        >
                    </div>
                    <%}%>
                </nav>
            </div>
            <!-- Sidebar End -->
            <%}%>
            <!-- Content Start -->
            <div class="content <%if(curStaff==null){%>ms-0 w-100<%}%>">
                <!-- Navbar Start -->
                <nav class="navbar navbar-expand navbar-light sticky-top px-4 py-0" style="background-color: #1977cc;">

                    <a href="#" class="sidebar-toggler flex-shrink-0 text-decoration-none text-light">
                        <i class="fa fa-bars"></i>
                    </a>
                    <form class="d-none d-md-flex ms-4">
                        <input
                            class="form-control border-0"
                            type="search"
                            placeholder="Search"
                            />
                    </form>
                    <div class="navbar-nav align-items-center ms-auto">
                        <div class="nav-item dropdown">
                            <a
                                href="#"
                                class="nav-link dropdown-toggle"
                                data-bs-toggle="dropdown"
                                >
                                <i class="fa fa-envelope me-lg-2"></i>
                                <span class="d-none d-lg-inline-flex">Message</span>
                            </a>
                            <div
                                class="dropdown-menu dropdown-menu-end bg-light border-0 rounded-0 rounded-bottom m-0"
                                >
                            </div>
                        </div>
                        <div class="nav-item dropdown">
                            <a
                                href="#"
                                class="nav-link dropdown-toggle"
                                data-bs-toggle="dropdown"
                                >
                                <i class="fa fa-bell me-lg-2"></i>
                                <span class="d-none d-lg-inline-flex">Notification</span>
                            </a>
                            <div
                                class="dropdown-menu dropdown-menu-end bg-light border-0 rounded-0 rounded-bottom m-0"
                                >
                            </div>
                        </div>
                        <%if(curStaff!=null){%>
                        <div class="nav-item dropdown">
                            <a
                                href="#"
                                class="nav-link dropdown-toggle"
                                data-bs-toggle="dropdown"
                                >
                                <img
                                    class="rounded-circle me-lg-2"
                                    src="<%=curStaff.getProfileImage()%>"
                                    alt=""
                                    style="width: 40px; height: 40px"
                                    />
                                <span class="d-none d-lg-inline-flex"><%=curStaff.getStaffName()%></span>
                            </a>
                            <div
                                class="dropdown-menu dropdown-menu-end bg-light border-0 rounded-0 rounded-bottom m-0"
                                >
                                <a href="#" class="dropdown-item">My Profile</a>
                                <a href="#" class="dropdown-item">Settings</a>
                                <a href="logout" class="dropdown-item">Log Out</a>
                            </div>
                        </div>
                        <%}else{%>
                        <a href="staff?event=sent-to-login" id="login" class="btn btn-outline-primary ms-3 bg-light rounded-pill text-decoration-none"><span class="d-none d-md-inline">Login</a>
                        <%}%>
                    </div>
                </nav>
                <!-- Navbar End -->

                <!-- Blank Start -->
                <div class="container-fluid pt-4 px-4">
                    <div
                        class="row bg-light rounded align-items-center justify-content-center mx-0"
                        >
                        <div class="mb-4 px-4 py-3 border-bottom d-flex justify-content-between align-items-center">
                            <h4>SERVICE DETAIL</h4>
                            <a href="service?event=sent-to-add" class="ms-text-primary font-weight-bold">Add Service</a>
                        </div>
                        <div class="col-md-12">
                            <%ServiceDAO serviceDAO = new ServiceDAO();
                            Service service = (Service) request.getAttribute("service");
                            CategoryServiceDAO categoryServiceDAO = new CategoryServiceDAO();
                            ReservationDAO reservationDAO = new ReservationDAO();%>   
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
                                                class="nav-link other active"
                                                id="ex1-tab-1"
                                                data-mdb-toggle="tab"
                                                href="#ex1-tabs-1"
                                                role="tab"
                                                >Service Description</a
                                            >
                                        </li>
                                        <li class="nav-item" role="presentation">
                                            <a
                                                class="nav-link other"
                                                id="ex1-tab-2"
                                                data-mdb-toggle="tab"
                                                href="#ex1-tabs-2"
                                                role="tab"
                                                >Doctor</a
                                            >
                                        </li>
                                        <li class="nav-item" role="presentation">
                                            <a
                                                class="nav-link other"
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
                    </div>
                </div>
                <!-- Blank End -->

                <!-- Footer Start -->
                <div class="mt-4">
                    <jsp:include page="layout/footer.jsp" />
                </div>
                <!-- Footer End -->
            </div>
            <!-- Content End -->

        </div>

        <!-- JavaScript Libraries -->
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

        <!-- Template Javascript -->
        <script>
                                                            document.querySelector('.sidebar-toggler').addEventListener('click', function () {
                                                                var sidebar = document.querySelector('.sidebar');
                                                                var content = document.querySelector('.content');

                                                                sidebar.classList.toggle('open');
                                                                content.classList.toggle('open');

                                                                return false;
                                                            });
        </script>
    </body>
</html>

