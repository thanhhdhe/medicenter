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
                        <a href="staff?event=send-to-medical-examination" class="nav-item nav-link"
                           ><i class="far fa-check-square"></i>Medical examination</a
                        >
                    </div>
                    <div class="navbar-nav w-100  text-light">
                        <a href="reservationcontactmanager?event=reservation-list" class="nav-item nav-link"
                           ><i class="fas fa-list-alt"></i>Reservations Manager</a
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
                        <div class="mb-4 px-4 py-3 border-bottom d-flex justify-content-start">
                            <h4>ADD SERVICE</h4>
                        </div>
                        <div class="col-md-12">
                            <%  
        CategoryServiceDAO categoryServiceDAO = new CategoryServiceDAO();
        String title = (request.getParameter("title") + "").equals("null") ? "" : (request.getParameter("title") + "");
        String brief = (request.getParameter("brief") + "").equals("null") ? "" : (request.getParameter("brief") + "");
        String status = (request.getParameter("status") + "").equals("null") ? "" : (request.getParameter("status") + "");
        String categoryID = (request.getParameter("categoryID") + "").equals("null") ? "" : (request.getParameter("categoryID") + "");
        String originalPrice = (request.getParameter("originalPrice") + "").equals("null") ? "" : (request.getParameter("originalPrice") + "");
        String salePrice = (request.getParameter("salePrice") + "").equals("null") ? "" : (request.getParameter("salePrice") + "");
        String description = (request.getParameter("description") + "").equals("null") ? "" : (request.getParameter("description") + "");
        String titleErr = (request.getAttribute("titleErr") + "").equals("null") ? "" : (request.getAttribute("titleErr") + "");
        String originalPriceErr = (request.getAttribute("originalPriceErr") + "").equals("null") ? "" : (request.getAttribute("originalPriceErr") + "");
        String salePriceErr = (request.getAttribute("salePriceErr") + "").equals("null") ? "" : (request.getAttribute("salePriceErr") + "");
        String categoryIDErr = (request.getAttribute("categoryIDErr") + "").equals("null") ? "" : (request.getAttribute("categoryIDErr") + "");
                            %>
                            <div class="d-flex flex-column align-items-center justify-content-center px-5 mb-5">
                                <div class="row w-100 mb-3">
                                    <div class="d-flex justify-content-start">
                                        <%String validate = request.getAttribute("validate")+"";
                    if(!validate.equals("null")&&validate.equals("false")){%>
                                        <div class="alert alert-danger d-flex align-items-center w-100" role="alert">
                                            <i class="bi bi-exclamation-circle-fill me-3"></i>
                                            <h5 class="m-0">
                                                Add Service Faill!!
                                            </h5>
                                        </div>
                                        <%}%>
                                    </div>

                                </div>
                                <form action="service?event=add-service" method="POST" enctype="multipart/form-data">
                                    <div class="row">    
                                        <div class="col-md-4">
                                            <div>
                                                <img src="resources/img/image1.jpg" alt="img" class="w-100 h-100 object-contain" /></div>
                                            <div class="form-group mt-3">
                                                <label for="serviceImage">Service Image:</label>
                                                <input type="file" class="form-control-file" id="serviceImage" name="serviceImage">
                                            </div>
                                            <h5 class="d-flex justify-content-center mt-3">- Or -</h5>
                                            <div class="form-group mt-3">
                                                <label for="serviceURL">Service Image:</label>
                                                <input type="text" class="form-control" id="serviceURL" name="serviceURL">
                                            </div>
                                        </div>
                                        <div class="col-md-8 ps-5">

                                            <div class="d-flex align-items-baseline">
                                                <p class="text-muted me-2">Title: </p>
                                                <input class="form-control" type="text" name="Title" value="<%=title%>"  />
                                            </div>
                                            <%if(!titleErr.equals("null")){%>    <p class="text-danger"><%=titleErr%></p><%}%> 

                                            <div>
                                                <p class="text-muted">Brief: </p>
                                                <textarea class="form-control text-muted" rows="4" cols="50" name="Brief" value="<%=brief%>"></textarea>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-4">
                                                    <select class="form-select text-primary mt-3 mb-4 w-75" name="status" >
                                                        <option value="active">Status</option>
                                                        <%if(status.equals("") || status.equals("active")){%>
                                                        <option value="active" selected>Active</option>
                                                        <option value="inactive">Inactive</option>
                                                        <%}else{%>
                                                        <option value="active">Active</option>
                                                        <option value="inactive" selected>Inactive</option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                                <div class="col-md-5">
                                                    <select class="form-select text-primary mt-3 mb-4 w-100" name="serviceType"  class="form-select">
                                                        <option value="" selected>Category Service</option>
                                                        <%List<CategoryService> categoryServiceList = categoryServiceDAO.getAllCategoryServices();
                                        for (CategoryService categoryService : categoryServiceList) {
                                            if(categoryID.equals(categoryService.getCategoryID()+"")){ %>
                                                        <option value="<%=categoryService.getCategoryID()%>" selected><%=categoryService.getCategoryName()%></option>
                                                        <%}else{%>
                                                        <option value="<%=categoryService.getCategoryID()%>"><%=categoryService.getCategoryName()%></option>
                                                        <%}%>
                                                        <%}%>

                                                    </select>
                                                    <%if(!categoryIDErr.equals("null")){%>    <p class="text-danger"><%=categoryIDErr%></p><%}%>
                                                </div>   
                                            </div>
                                            <div class="d-flex">
                                                <div class="col-md-6"><p class="text-muted">Price: </p>
                                                    <input class="form-control w-50" type="number" value="<%=originalPrice%>" name="OriginalPrice" />
                                                    <%if(!originalPriceErr.equals("null")){%>    <p class="text-danger"><%=originalPriceErr%></p><%}%> 
                                                </div>
                                                <div class="col-md-6"><p class="text-muted">Sale price: </p>
                                                    <input class="form-control w-50" type="number" value="<%=salePrice%>" name="SalePrice" />
                                                    <%if(!salePriceErr.equals("null")){%>    <p class="text-danger"><%=salePriceErr%></p><%}%>  
                                                </div>
                                            </div>

                                            <div>
                                                <p class="text-muted">Description: </p>
                                                <textarea class="form-control text-muted" rows="6" cols="50" value="<%=description%>" name="Description"></textarea>
                                            </div>

                                            <div class="d-flex justify-content-center">
                                                <input class="btn btn-primary mt-3 w-25" type="submit" value="Add Service" />
                                            </div>
                                        </div>
                                </form>
                            </div>
                        </div>
                    </div>
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
    <script src="./resources/js/services-manage-script.js"></script>
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

