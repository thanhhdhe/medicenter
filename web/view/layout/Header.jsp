<%-- 
    Document   : Header
    Created on : 13-Sep-2023, 08:44:54
    Author     : Admin
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="./resources/css/style.css" rel="stylesheet">
    </head>
    <!--Start Header-->
    <header id="header" class="fixed-top">
        <div class="container d-flex align-items-center">

            <h1 class="logo me-auto"><a href="index.html">Medilab</a></h1>
            <!--<a href="index.html" class="logo me-auto"><img src="assets/img/logo.png" alt="" class="img-fluid"></a>-->

            <nav id="navbar" class="navbar order-last order-lg-0">
                <ul>
                    <li><a class="nav-link scrollto" href="home">Home</a></li>
                    <li class="dropdown"><a href="/ChildrenCare/service?event=service-list"><span>Services</span> <i class="bi bi-chevron-down"></i></a>
                        <ul>
                            <c:forEach items="${requestScope.category}" var="cate">
                                <li><a href="#">${cate.categoryName}</a>

                                </li>
                            </c:forEach>

                        </ul>
                    </li>
                    <li><a class="nav-link scrollto" href="#doctors">Doctors</a></li>
                    <li class="dropdown"><a href="/ChildrenCare/postPage?event=post-list"><span>Blog</span> <i class="bi bi-chevron-down"></i></a>
                        <ul>
                            <li><a href="#">Drop Down 1</a></li>
                            <li class="dropdown"><a href="#"><span>Deep Drop Down</span> <i class="bi bi-chevron-right"></i></a>
                                <ul>
                                    <li><a href="#">Deep Drop Down 1</a></li>
                                    <li><a href="#">Deep Drop Down 2</a></li>
                                    <li><a href="#">Deep Drop Down 3</a></li>
                                    <li><a href="#">Deep Drop Down 4</a></li>
                                    <li><a href="#">Deep Drop Down 5</a></li>
                                </ul>
                            </li>
                            <li><a href="#">Drop Down 2</a></li>
                            <li><a href="#">Drop Down 3</a></li>
                            <li><a href="#">Drop Down 4</a></li>
                        </ul>
                    </li>
                    <li class="dropdown"><a href="/ChildrenCare/feedback?action=noaction"><span>Feedback</span> <i class="bi bi-chevron-down"></i></a>

                    </li>
                    <li><a onclick="myFunction()" class="nav-link scrollto" href="#contact">Contact</a></li>
                </ul>
                <i class="bi bi-list mobile-nav-toggle"></i>
            </nav><!-- .navbar -->

            <a onclick="showRegisterPopup()" class="appointment-btn scrollto"><span class="d-none d-md-inline">Register Now</a>
            <a onclick="showLoginPopup()" class="appointment-btn scrollto"><span class="d-none d-md-inline">Login</a>


        </div>
    </header>
    <!-- End Header -->
    <body>
        <jsp:include page="../login.jsp"/>
        <jsp:include page="../changePassword.jsp"/>
        <jsp:include page="../register.jsp"/>
    </body>
</html>
