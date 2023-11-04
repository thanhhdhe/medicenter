<%-- 
    Document   : HomePage
    Created on : 12-Sep-2023, 20:29:49
    Author     : Admin
--%>
<%@page import = "model.*" %>
<%@page import = "Database.*" %>
<%@page import = "java.util.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    </head>

    <body>
        <%
       String email = (String) session.getAttribute("email");
       StaffDAO staffDAO = new StaffDAO();
       Staff curStaff = staffDAO.getStaffByStaffEmail(email);
        %>

        <div class="container-fluid position-relative bg-white d-flex p-0">
            <%if(curStaff!=null){%>
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
                        <a href="slider?action=all" class="nav-item nav-link"
                           ><i class="bi bi-image-fill"></i>Slider</a
                        >
                    </div>
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
                        class="row vh-100 bg-light rounded align-items-center justify-content-center mx-0"
                        >
                        <div class="col-md-12 p-0">
                            <div class="mb-4 px-4 py-3 border-bottom d-flex justify-content-between align-items-center">
                                <h4>FEEDBACK RECORDS</h4>


                            </div>
                            <div class="d-flex justify-content-around">
                                <div class="col-md-3 ">
                                    <jsp:include page="../view/layout/fillter.jsp" />
                                </div>
                                <!-- search -->
                                <c:choose>
                                    <c:when test="${empty fill || fill eq 'searchfeedback'}">
                                        <div class="col-md-9">
                                            <form class="d-flex" style="width: 50%; margin-left: 430px; margin-top: 20px" action="feedback?event=searchfeedback" method="POST">
                                                <input class="form-control me-2" type="search" name="search" value="" placeholder="Search" aria-label="Search">
                                                <button class="btn btn-primary rounded-pill" type="submit">Search</button>
                                            </form>
                                        </div>
                                    </c:when>
                                    <c:otherwise>  

                                        <div class="col-md-9">
                                            <form class="d-flex" action="feedback?event=${fillevent}&${fillparameter}=${fill}" method="POST">
                                                <input class="form-control me-2" type="search" name="search" value="" placeholder="Search" aria-label="Search">
                                                <button style="background-color: black" class="btn btn-primary rounded-pill" type="submit">Search</button>
                                            </form>
                                        </div>
                                    </c:otherwise>
                                </c:choose>

                            </div>


                            <div class="table-responsive p-4">
                                <%if(curStaff!=null){%>
                                <form action="feedback" method="POST">
                                    <table class="table table-striped table-hover">
                                        <thead class="text-light" style="background: #1977cc;">
                                            <tr>
                                                <th scope="col">ID</th>
                                                <th scope="col">Service Name</th>
                                                <th scope="col">Full name</th>
                                                <th scope="col">Feedback Date</th>
                                                <th scope="col">Rate star</th>
                                                <th scope="col">Content</th>
                                                <th scope="col">Status</th>
                                                <th scope="col">Detail</th>

                                            </tr>
                                        </thead>
                                        <c:forEach var="feedback" items="${requestScope.feedbacks}">
                                            <tbody>
                                                <!-- get name service  -->
                                                <c:set var="medicalID" value="${feedback.getMedicalID()}" />
                                                <c:set var="medicalIDString" value="${medicalID.toString()}" />
                                                <%
                                                    FeedBackDAO dao = new FeedBackDAO();
                                                    ServiceDAO serviceDAO = new ServiceDAO();
                                                    int userserviceid = dao.getNameServiceFeed((String) pageContext.getAttribute("medicalIDString"));
                                                    Service Uservice = serviceDAO.getServiceByID(String.valueOf(userserviceid));
                                                    String userservice = Uservice.getTitle();
                                                %>
                                                <!-- get user  -->
                                                <c:set var="userID" value="${feedback.getUserID()}" />
                                                <%
                                                    UserDAO u = new UserDAO();
                                                    User user = u.getUserByID((int) pageContext.getAttribute("userID"));
                                                %>
                                                <tr>
                                                    <td>${feedback.getFeedbackID()}</td>
                                                    <td><%= userservice %></td>
                                                    <td><%= user.getFirstName() +" "+ user.getLastName() %></td>
                                                    <td>${feedback.getFeedbackdate()}</td>
                                                    <td><c:forEach var="star" begin="1" end="${feedback.getRatestar()}" step="1">
                                                            <img style="width: 24px" src="./resources/img/icon/star.png" alt="alt"/>
                                                        </c:forEach></td>
                                                    <td>${feedback.getContentSub()}</td>
                                                    <td>${feedback.getFstatus()}</td>
                                                    <td><a href="feedback?event=showdetailfeedback&FDid=${feedback.getFeedbackID()}">
                                                            <img style="width: 24px" src="./resources/img/icon/detail.png" alt="alt"/>
                                                        </a></td>
                                                </tr>

                                            </tbody>
                                        </c:forEach>

                                    </table>
                                    <div style="" class="d-flex justify-content-center">
                                        <nav aria-label="...">
                                            <ul class="pagination pagination-sm">
                                                <c:forEach begin="1" end="${requestScope.endP}" var="i">
                                                    <c:set var="fillstatus" value="${requestScope.fillstatus}"/>
                                                    <c:choose>
                                                        <c:when test="${empty fill}">
                                                            <li class="page-item"><a style="height: 25px; width: 25px" class="page-link btn-primary rounded-circle me-2 d-flex justify-content-center align-items-center" href="feedback?index=${i}">${i}</a></li>
                                                            </c:when>
                                                        </c:choose>
                                                        <c:if test="${not empty fill}">
                                                            <c:if test="${not empty search}">
                                                            <li class="page-item"><a style="height: 25px; width: 25px" class="page-link btn-primary rounded-circle me-2 d-flex justify-content-center align-items-center" href="feedback?index=${i}&event=${fillevent}&${fillparameter}=${fill}&search=${search}">${i}</a></li>
                                                            </c:if>
                                                            <c:if test="${empty search}">
                                                            <li class="page-item"><a style="height: 25px; width: 25px" class="page-link btn-primary rounded-circle me-2 d-flex justify-content-center align-items-center" href="feedback?index=${i}&event=${fillevent}&${fillparameter}=${fill}">${i}</a></li>
                                                            </c:if>
                                                        </c:if>

                                                </c:forEach>
                                            </ul>
                                        </nav>

                                    </div>
                                </form>

                                <%}%>
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
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
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

