<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import = "model.*" %>
<%@page import = "Database.*" %>
<%@page import = "java.util.*" %>
<!DOCTYPE html>
<html lang="en">
    <head>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Slider Details</title>
        <!-- Include Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css"
              rel="stylesheet"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Include Bootstrap JavaScript and jQuery -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <link rel="stylesheet" href="./resources/css/staff-dashboard.css">
        <style>

            .carousel-caption {

                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background: rgba(0, 0, 0, .5);
                z-index: 1;
            }
            .carousel-caption h5 {
                font-size: 45px;
                text-transform: uppercase;
                letter-spacing: 2px;
                margin: 200px 15px 0;
            }
            .carousel-caption p {
                margin: 0 10px;
                width: 70%;
                margin: auto;
                font-size: 18px;
                line-height: 1.9;
            }

        </style>
    </head>
    <body>
        <%
    String email = (String) session.getAttribute("email");
    StaffDAO staffDAO = new StaffDAO();
    Staff curStaff = staffDAO.getStaffByStaffEmail(email);
    UserDAO userDAO = new UserDAO();
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
                    <div class="navbar-nav w-100 text-light">
                        <a href="user?action=search" class="nav-item nav-link"
                           ><i class="bi bi-people-fill"></i>User</a
                        >
                    </div>
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
                        <a href="feedback" class="nav-item nav-link"
                           ><i class="far fa-file-alt"></i>Feedback</a
                        >
                    </div>
                    <div class="navbar-nav w-100 text-light">
                        <a href="service?event=manage" class="nav-item nav-link"
                           ><i class="fas fa-stethoscope"></i>Services</a
                        >
                    </div>
                    <div class="navbar-nav w-100 text-light">
                        <a href="user?action=search" class="nav-item nav-link active"
                           ><i class="bi bi-image-fill"></i>Slider</a
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
                <div class="container mt-4 mb-5">
                    <h1>Slider Detail</h1>
                    <% 
                    String message = (String) request.getSession().getAttribute("message");
                    String alertClass = "alert-success";

                    if (message != null) { 
                        if (message.contains("error")) {
                            alertClass = "alert-danger";
                        }
                    %>
                    <div class="alert <%= alertClass %> alert-dismissible fade show" role="alert">
                        <strong><%= message %></strong>
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                    <%
                        request.getSession().removeAttribute("message");
                    }
                    %>
                    <div class="row">
                        <div id="carouselExampleCaptions" class="carousel slide col-md-8">
                            <div class="carousel-indicators">
                                <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                            </div>
                            <div class="carousel-inner">
                                <div class="carousel-item active">
                                    <img name="currImage" class="bd-placeholder-img bd-placeholder-img-lg d-block w-100" width="860" height="615" id="carouselImage" src="<c:out value="${slider.image}" />" alt="Slider Image" />
                                    <div class="carousel-caption d-none d-md-block">
                                        <h5 id="carouselTitle"><c:out value="${slider.title}" /></h5>
                                        <p id="carouselBrief"><c:out value="${slider.brief}" /></p>
                                        <p><a id="carouselLink" href="<c:out value="${slider.backlink}" />" class="btn btn-warning mt-3">Learn More</a></p>
                                    </div>
                                </div>
                            </div>
                            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="prev">
                                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                <span class="visually-hidden">Previous</span>
                            </button>
                            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="next">
                                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                <span class="visually-hidden">Next</span>
                            </button>
                        </div>

                        <!-- Slider Details -->
                        <div class="col-md-4">
                            <form action="slider?action=update" id="frmUpdateSlide" method="post" enctype="multipart/form-data">
                                <div class="card">
                                    <div class="card-body">
                                        <h5 class="card-title">Slider Title: <input type="text" id="titleInput" name="title" value="<c:out value="${slider.title}" />" onkeyup="updateCarousel('titleInput', 'carouselTitle');" class="form-control" /></h5>
                                        <p class="card-text">Slider ID: <input readonly type="text" id="sliderid" name="sliderID" value="<c:out value="${slider.sliderID}" />" class="form-control" /></p>
                                        <p class="card-text">Slider Brief: <input type="text" id="briefInput" name="brief" value="<c:out value="${slider.brief}" />" onkeyup="updateCarousel('briefInput', 'carouselBrief');" class="form-control" /></p>
                                        <p class="card-text">Slider Status:
                                            <select name="status" class="form-select">
                                                <option value="Active" <c:if test="${slider.status == 'Active'}">selected</c:if>>Active</option>
                                                <option value="Inactive" <c:if test="${slider.status == 'Inactive'}">selected</c:if>>Inactive</option>
                                                </select>
                                            </p>
                                            <p class="card-text">Slider Backlink: <input type="text" id="backlinkInput" name="backlink" value="<c:out value="${slider.backlink}" />" class="form-control" /></p>
                                        <input type="file" id="imageInput" name="image" accept="image/*" onchange="updateCarouselImage('imageInput');" class="form-control" />
                                        <a href="<c:out value="${slider.backlink}" />" class="btn btn-primary mt-3">Go to Backlink</a>
                                    </div>
                                </div>
                                <button type="submit" class="btn btn-primary mt-3">Update</button>
                                <a href="slider?action=all" class="btn btn-secondary mt-3">Back to List</a>
                            </form>
                        </div>

                    </div>

                </div>
                <jsp:include page="layout/footer.jsp" />
            </div>

        </div>
        <script>
            function updateCarousel(inputId, elementId) {
                const inputValue = document.getElementById(inputId).value;
                const element = document.getElementById(elementId);
                element.textContent = inputValue;
            }

            function updateCarouselImage(inputId) {
                const selectedImage = document.getElementById(inputId).files[0];
                const element = document.getElementById("carouselImage");

                if (selectedImage) {
                    const reader = new FileReader();
                    reader.onload = function (e) {
                        element.src = e.target.result;
                    };
                    reader.readAsDataURL(selectedImage);
                }
            }
            document.querySelector('.sidebar-toggler').addEventListener('click', function () {
            var sidebar = document.querySelector('.sidebar');
            var content = document.querySelector('.content');

            sidebar.classList.toggle('open');
            content.classList.toggle('open');

            return false;
        });
        </script>
        <!-- Include Bootstrap JavaScript and jQuery -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
