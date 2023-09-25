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
                                        Dropdown
                                    </a>
                                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                        <li><a class="dropdown-item" href="#">Action</a></li>
                                        <li><a class="dropdown-item" href="#">Another action</a></li>

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
                                    </ul>
                                </li>

                            </ul>
                            <form class="d-flex">
                                <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
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
                                        <c:set var="userID" value="${feedback.getUserID()}" />
                                        <%
                                            UserDAO u = new UserDAO();
                                            User user = u.getUserByID((int) pageContext.getAttribute("userID"));
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
                                                <h6 class="fw-bold mb-1"><%= user.getFirstName() +" "+ user.getLastName() %></h6>
                                                <div class="d-flex align-items-center mb-3">
                                                    <p class="mb-0">
                                                        ${feedback.getFeedbackdate()}

                                                    </p>
                                                    <!-- Status -->
                                                    <div class="dropdown">
                                                        <button style="border: 0px; background-color: #ffffff;" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                                                            <span class="badge bg-primary">${feedback.getFstatus()}</span>
                                                        </button>
                                                        <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                                                            <li><a class="dropdown-item" href="feedback?event=updatestatus&Fstatus=Appored&UID=${feedback.getFeedbackID()}">Appored</a></li>
                                                            <li><a class="dropdown-item" href="feedback?event=updatestatus&Fstatus=Unresolved&UID=${feedback.getFeedbackID()}">Unresolved </a></li>
                                                            <li><a class="dropdown-item" href="feedback?event=updatestatus&Fstatus=Resolved&UID=${feedback.getFeedbackID()}">Resolved </a></li>
                                                            <li><a class="dropdown-item" href="feedback?event=updatestatus&Fstatus=Reviewed&UID=${feedback.getFeedbackID()}">Reviewed  </a></li>
                                                        </ul>
                                                    </div>                                                  
                                                </div>
                                                <p>
                                                    <c:forEach var="star" begin="1" end="${feedback.getRatestar()}" step="1">
                                                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6" style="width: 1.5rem;">
                                                        <path stroke-linecap="round" stroke-linejoin="round" 
                                                              d="M11.48 3.499a.562.562 0 011.04 0l2.125 5.111a.563.563 0 00.475.345l5.518.442c.499.040.701.663.321.988l-4.204 3.602a.563.563 0 00-.182.557l1.285 5.385a.562.562 0 01-.84.610l-4.725-2.885a.563.563 0 00-.586 0L6.982 20.54a.562.562 0 01-.84-.610l1.285-5.386a.562.562 0 00-.182-.557l-4.204-3.602a.563.563 0 01.321-.988l5.518-.442a.563.563 0 00.475-.345L11.48 3.5z" />
                                                        </svg>
                                                    </c:forEach>

                                                <p class="mb-0">
                                                    ${feedback.getContent()}
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
                                                    <c:when test="${empty fillstatus}">
                                                        <li class="page-item"><a class="page-link" href="feedback?index=${i}">${i}</a></li>
                                                        </c:when>
                                                    </c:choose>
                                                    <c:if test="${not empty fillstatus}">
                                                    <li class="page-item"><a class="page-link" href="feedback?index=${i}&event=fillterstatus&fillstatus=${fillstatus}">${i}</a></li>
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
    </body>
</html>
