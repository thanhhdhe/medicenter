<%-- 
    Document   : fillter
    Created on : 02-Oct-2023, 23:26:18
    Author     : pc
--%>

<%@page import = "model.*" %>
<%@page import = "Database.*" %>
<%@page import = "java.util.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
            crossorigin="anonymous"
            />
        <style>
            .lv {
                padding: 10px;
                margin-left: 80px;
            }
        </style>
    </head>
    <body>
        <!-- Fillter -->
        <div class="d-flex justify-content">
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <div class="container-fluid">


                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                            <!-- dropdown  -->
                            <li class="nav-item dropdown">
                                <a style="color: black" class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-filter" viewBox="0 0 16 16">
                                    <path d="M6 10.5a.5.5 0 0 1 .5-.5h3a.5.5 0 0 1 0 1h-3a.5.5 0 0 1-.5-.5zm-2-3a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zm-2-3a.5.5 0 0 1 .5-.5h11a.5.5 0 0 1 0 1h-11a.5.5 0 0 1-.5-.5z"/>
                                    </svg>
                                </a>
                                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                    <li><a class="dropdown-item" href="feedback">Default</a></li>
                                    <!-- service -->
                                    <li class="nav-item dropdown">
                                        <a style="color: black" class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                            Service
                                        </a>
                                        <ul class="dropdown-menu lv" aria-labelledby="navbarDropdown">
                                            <c:forEach var="ser" items="${requestScope.servicelistfill}">
                                                <li><a style="color: black" class="dropdown-item" href="feedback?event=fillterservice&fillservice=${ser.getServiceID()}">${ser.getTitle()}</a></li> 
                                                </c:forEach>
                                            <li><a class="dropdown-item" href="feedback?event=fillterservice&fillservice=Physical3Exam3">Physical3Exam3</a></li>
                                        </ul>
                                    </li>
                                    <!-- rate star -->
                                    <li class="nav-item dropdown">
                                        <a style="color: black" class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                            rate star
                                        </a>
                                        <ul class="dropdown-menu lv" aria-labelledby="navbarDropdown">
                                            <li><a style="color: black" class="dropdown-item" href="feedback?event=fillterrate&fillrate=1">1</a></li>
                                            <li><a style="color: black" class="dropdown-item" href="feedback?event=fillterrate&fillrate=2">2</a></li>                              
                                            <li><a style="color: black" class="dropdown-item" href="feedback?event=fillterrate&fillrate=3">3</a></li>
                                            <li><a style="color: black" class="dropdown-item" href="feedback?event=fillterrate&fillrate=4">4</a></li>
                                            <li><a style="color: black" class="dropdown-item" href="feedback?event=fillterrate&fillrate=5">5</a></li>                              
                                        </ul>
                                    </li>
                                    <!-- status -->
                                    <li class="nav-item dropdown">
                                        <a style="color: black" class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                            Status
                                        </a>
                                        <ul class="dropdown-menu lv" aria-labelledby="navbarDropdown">
                                            <li><a class="dropdown-item" href="feedback?event=fillterstatus&fillstatus=Pending">Pending</a></li>
                                            <li><a class="dropdown-item" href="feedback?event=fillterstatus&fillstatus=Reviewed">Reviewed</a></li>                              
                                            <li><a class="dropdown-item" href="feedback?event=fillterstatus&fillstatus=Appored">Appored</a></li>
                                            <li><a class="dropdown-item" href="feedback?event=fillterstatus&fillstatus=Unresolved">Unresolved</a></li>
                                            <li><a class="dropdown-item" href="feedback?event=fillterstatus&fillstatus=Resolved">Resolved</a></li>                              

                                        </ul>
                                    </li>
                                    <!-- status -->
                                    <li class="nav-item dropdown">
                                        <a style="color: black" class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                            Sort
                                        </a>
                                        <ul class="dropdown-menu lv" aria-labelledby="navbarDropdown">
                                            <li><a class="dropdown-item" href="feedback?event=sort&sortby=fullname">Sort by Full Name</a></li>
                                            <li><a class="dropdown-item" href="feedback?event=sort&sortby=servicename">Sort by Service Name</a></li>                              
                                            <li><a class="dropdown-item" href="feedback?event=sort&sortby=ratestar">Sort by rate star</a></li>
                                            <li><a class="dropdown-item" href="feedback?event=sort&sortby=status">Sort by status</a></li>                                                                             
                                        </ul>
                                    </li>
                                </ul>
                            </li>

                        </ul>

                    </div>
                </div>
            </nav>
        </div>
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
        <script>
            $(document).ready(function () {
                // Sự kiện onmouseenter cho "Fillter"
                $('.nav-item.dropdown').on('mouseenter', function () {
                    $(this).find('.dropdown-menu').first().stop(true, true).slideDown(200);
                });

                // Sự kiện onmouseleave để ẩn dropdown menu
                $('.nav-item.dropdown').on('mouseleave', function () {
                    $(this).find('.dropdown-menu').first().stop(true, true).slideUp(200);
                });
            });

        </script>
    </body>
</html>
