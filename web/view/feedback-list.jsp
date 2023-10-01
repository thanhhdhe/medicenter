<%-- 
    Document   : feedback-list
    Created on : 24-Sep-2023, 22:48:57
    Author     : pc
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "model.*" %>
<%@page import = "Database.*" %>
<%@page import = "java.util.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />

        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
            crossorigin="anonymous"
            />
        <link rel="stylesheet" href="./resources/css/services-style.css">
        <link rel="stylesheet" href="../resources/css/style.css">
    </head>
    <body>
        <jsp:include page="../view/layout/Header.jsp" />
        <section style="background-color: #e1dada" >
            <!-- Fillter -->
            <div class="d-flex justify-content-center">
                <nav class="navbar navbar-expand-lg navbar-light bg-light">
                    <div class="container-fluid">


                        <div class="collapse navbar-collapse" id="navbarSupportedContent">
                            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                                <!-- dropdown  -->
                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-filter" viewBox="0 0 16 16">
                                        <path d="M6 10.5a.5.5 0 0 1 .5-.5h3a.5.5 0 0 1 0 1h-3a.5.5 0 0 1-.5-.5zm-2-3a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zm-2-3a.5.5 0 0 1 .5-.5h11a.5.5 0 0 1 0 1h-11a.5.5 0 0 1-.5-.5z"/>
                                        </svg>
                                    </a>
                                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                        <li><a class="dropdown-item" href="feedback">Default</a></li>
                                        <!-- service -->
                                        <li class="nav-item dropdown">
                                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                                Service
                                            </a>
                                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                                <c:forEach var="ser" items="${requestScope.servicelistfill}">
                                                    <li><a class="dropdown-item" href="feedback?event=fillterservice&fillservice=${ser.getServiceID()}">${ser.getTitle()}</a></li> 
                                                    </c:forEach>
                                                <li><a class="dropdown-item" href="feedback?event=fillterservice&fillservice=Physical3Exam3">Physical3Exam3</a></li>
                                            </ul>
                                        </li>
                                        <!-- rate star -->
                                        <li class="nav-item dropdown">
                                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                                rate star
                                            </a>
                                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                                <li><a class="dropdown-item" href="feedback?event=fillterrate&fillrate=1">1</a></li>
                                                <li><a class="dropdown-item" href="feedback?event=fillterrate&fillrate=2">2</a></li>                              
                                                <li><a class="dropdown-item" href="feedback?event=fillterrate&fillrate=3">3</a></li>
                                                <li><a class="dropdown-item" href="feedback?event=fillterrate&fillrate=4">4</a></li>
                                                <li><a class="dropdown-item" href="feedback?event=fillterrate&fillrate=5">5</a></li>                              
                                            </ul>
                                        </li>
                                        <!-- status -->
                                        <li class="nav-item dropdown">
                                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                                Status
                                            </a>
                                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                                <li><a class="dropdown-item" href="feedback?event=fillterstatus&fillstatus=Pending">Pending</a></li>
                                                <li><a class="dropdown-item" href="feedback?event=fillterstatus&fillstatus=Reviewed">Reviewed</a></li>                              
                                                <li><a class="dropdown-item" href="feedback?event=fillterstatus&fillstatus=Appored">Appored</a></li>
                                                <li><a class="dropdown-item" href="feedback?event=fillterstatus&fillstatus=Unresolved">Unresolved</a></li>
                                                <li><a class="dropdown-item" href="feedback?event=fillterstatus&fillstatus=Resolved">Resolved</a></li>                              

                                            </ul>
                                        </li>
                                        <!-- status -->
                                        <li class="nav-item dropdown">
                                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                                Sort
                                            </a>
                                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                                <li><a class="dropdown-item" href="feedback?event=sort&sortby=fullname">Sort by Full Name</a></li>
                                                <li><a class="dropdown-item" href="feedback?event=sort&sortby=servicename">Sort by Service Name</a></li>                              
                                                <li><a class="dropdown-item" href="feedback?event=sort&sortby=ratestar">Sort by rate star</a></li>
                                                <li><a class="dropdown-item" href="feedback?event=sort&sortby=status">Sort by status</a></li>                                                                             
                                            </ul>
                                        </li>
                                    </ul>
                                </li>

                            </ul>
                            <form class="d-flex" action="feedback?event=searchfeedback" method="POST">
                                <input class="form-control me-2" type="search" name="search" value="" placeholder="Search" aria-label="Search">
                                <button class="btn btn-outline-success" type="submit">Search</button>
                            </form>
                        </div>
                    </div>
                </nav>
            </div>
            <form action="feedback" method="POST">
                <div class="container my-5 py-5 mt-0 mb-0">
                    <div class="row d-flex justify-content-center">
                        <div class="col-md-12 col-lg-10">
                            <div class="card text-dark">
                                <div class="card-body p-4" style="background-color: #e1dada">

                                    <c:forEach var="feedback" items="${requestScope.feedbacks}">
                                        <!-- get user  -->
                                        <c:set var="userID" value="${feedback.getUserID()}" />
                                        <%
                                            UserDAO u = new UserDAO();
                                            User user = u.getUserByID((int) pageContext.getAttribute("userID"));
                                        %>
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
                                        <div class="d-flex flex-start mb-4" 
                                             style="border-bottom: 1px; background-color: #ffffff; padding: 10px;
                                             border-radius: 20px;">
                                            <div class="col-md-2" style="margin-right: 10px">
                                                <img class="rounded-circle shadow-1-strong me-3 w-100"
                                                     src="<%= user.getProfileImage() %>" alt="avatar" width="60"
                                                     height="60" />
                                            </div>


                                            <div>
                                                <h5 class="fw-bold mb-1" style="color: #007BFF">Service: <%= userservice %></h5>
                                                <h6 class="fw-bold mb-1"><%= user.getFirstName() +" "+ user.getLastName() %></h6>
                                                <div class="d-flex align-items-center mb-3">
                                                    <p class="mb-0">
                                                        ${feedback.getFeedbackdate()}

                                                    </p>
                                                    <!-- Status -->
                                                    <div class="dropdown">
                                                        <button style="border: 0px; background-color: #ffffff;" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                                                            <span class="badge bg-primary"  id="statusBadge-${feedback.getFeedbackID()}">${feedback.getFstatus()}</span>
                                                        </button>
                                                        <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                                                            <li><a class="dropdown-item status-change" href="#" onclick="changestatus(this, ${feedback.getFeedbackID()})">Appored</a></li>
                                                            <li><a class="dropdown-item status-change" href="#" onclick="changestatus(this, ${feedback.getFeedbackID()})">Unresolved</a></li>
                                                            <li><a class="dropdown-item status-change" href="#" onclick="changestatus(this, ${feedback.getFeedbackID()})">Resolved</a></li>
                                                            <li><a class="dropdown-item status-change" href="#" onclick="changestatus(this, ${feedback.getFeedbackID()})">Reviewed</a></li>
                                                        </ul>
                                                        <a href="feedback?event=showdetailfeedback&FDid=${feedback.getFeedbackID()}">
                                                            <img style="width: 24px" src="./resources/img/icon/detail.png" alt="alt"/>
                                                        </a>
                                                    </div>

                                                </div>
                                                <p>
                                                    <c:forEach var="star" begin="1" end="${feedback.getRatestar()}" step="1">
                                                        <img style="width: 24px" src="./resources/img/icon/star.png" alt="alt"/>
                                                    </c:forEach>

                                                <p class="mb-0">
                                                    ${feedback.getContentSub()}
                                                </p>
                                            </div>

                                        </div>
                                    </c:forEach>
                                </div>

                                <hr class="my-0" />
                                <!-- Paging -->
                                <div style="background-color: #e1dada" class="d-flex justify-content-center">
                                    <nav aria-label="...">
                                        <ul class="pagination pagination-sm">
                                            <c:forEach begin="1" end="${requestScope.endP}" var="i">
                                                <c:set var="fillstatus" value="${requestScope.fillstatus}"/>
                                                <c:choose>
                                                    <c:when test="${empty fill}">
                                                        <li class="page-item"><a class="page-link" href="feedback?index=${i}">${i}</a></li>
                                                        </c:when>
                                                    </c:choose>
                                                    <c:if test="${not empty fill}">
                                                    <li class="page-item"><a class="page-link" href="feedback?index=${i}&event=${fillevent}&${fillparameter}=${fill}">${i}</a></li>
                                                    </c:if>

                                            </c:forEach>
                                        </ul>
                                    </nav>

                                </div>

                            </div>
                        </div>
                    </div>
                </div>

            </form> 
        </section>


        <jsp:include page="layout/footer.jsp" />
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
        <script>
            function changestatus(a, uid) {
                var text = a.textContent;
                var textchange = document.getElementById("statusBadge-"+uid);
                textchange.textContent = text;

                // Gửi yêu cầu Ajax đến servlet
                var xhr = new XMLHttpRequest();
                xhr.open("POST", "feedback?event=updatestatus&Fstatus=" + text + "&UID=" + uid, true);

                xhr.onload = function () {

                };

                xhr.onerror = function () {

                };

                xhr.send();
            }
        </script>
    </body>
</html>
