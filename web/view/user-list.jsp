<%-- 
    Document   : user-list
    Created on : 02-Oct-2023, 12:31:01
    Author     : Admin
--%>
<%@page import = "model.*" %>
<%@page import = "Database.*" %>
<%@page import = "java.util.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%><!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.3.0-alpha3/css/bootstrap.min.css">
        <!-- DataTable CSS  -->
        <!--        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">-->
        <link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/dataTables.bootstrap5.min.css">
        <link rel="stylesheet" href="https://cdn.datatables.net/responsive/2.4.1/css/responsive.bootstrap5.min.css">
        <link href="css/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="./resources/css/staff-dashboard.css">
        <link rel="stylesheet" href="./resources/css/services-style.css">
        <link
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css"
            rel="stylesheet"
            />
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
            rel="stylesheet"
            />

        <title>User manage</title>
    </head>

    <body>
        <%
             String email = (String) session.getAttribute("email");
             StaffDAO staffDAO = new StaffDAO();
             Staff curStaff = staffDAO.getStaffByStaffEmail(email);
             boolean isManager = false;
        %>
        <div class="container-fluid position-relative bg-white d-flex p-0">
            <%if(curStaff!=null){
            if(curStaff.getRole().equals("manager")) isManager=true;%>
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
                    <div class="navbar-nav w-100  text-light">
                        <a href="staff?event=send-to-medical-examination" class="nav-item nav-link"
                           ><i class="far fa-check-square"></i>Medical examination</a
                        >
                    </div>
                    <%if(isManager){%>
                    <div class="navbar-nav w-100 text-light">
                        <a href="user?action=all" class="nav-item nav-link active"
                           ><i class="bi bi-people-fill"></i>User</a
                        >
                    </div>
                    <div class="navbar-nav w-100 text-light">
                        <a href="staff?event=send-to-feedback" class="nav-item nav-link"
                           ><i class="far fa-file-alt"></i>Feedback</a
                        >
                    </div>
                    <div class="navbar-nav w-100 text-light">
                        <a href="service?event=manage" class="nav-item nav-link"
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
                    <div class="row bg-light rounded align-items-center justify-content-center mx-0">
                        <div class="container-fluid">
                            <div class="layout-specing">
                                <div class="row">
                                    <div class="col-md-5 row mt-4">
                                        <div class="search-bar p-0 d-lg-block mx-3">                                                        
                                            <div id="search" class="menu-search mb-0">
                                                <form action="user?action=search" method="POST" id="searchform" class="searchform">
                                                    <div class="d-flex">
                                                        <input type="text" value="${text}" class="form-control border rounded-pill" name="txt" id="search" placeholder="Search...">
                                                        <button class="btn btn-block btn-primary mx-3" type="submit">Search</button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-7 mt-4">
                                        <form action="user?action=filter" method="POST" onSubmit="document.getElementById('submit').disabled = true;">
                                            <div class="justify-content-md-end row">
                                                <div class="col-md-5 row align-items-center">
                                                    <div class="col-md-4">
                                                        <label class="form-label">Status</label>
                                                    </div>
                                                    <div class="col-md-8">
                                                        <select name="status" class="form-select" aria-label="Default select example">
                                                            <option <c:if test="${status == 'all'}">selected</c:if> value="all">All</option>
                                                            <option <c:if test="${status == '1'}">selected</c:if> value="1">Active</option>
                                                            <option <c:if test="${status == '0'}">selected</c:if> value="0">Disable</option>
                                                            </select>
                                                        </div>  
                                                    </div>
                                                    <div class="col-md-1 md-0">
                                                        <button type="submit" class="btn btn-primary">Filter</button>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </div>


                                    <div class="row">
                                        <div class="col-12 mt-4">
                                            <div class="table-responsive bg-white shadow rounded">
                                                <div class="col-md-2 mx-3 mt-3">
                                                    <label for="itemsPerPage" class="form-label">Items per Page:</label>
                                                    <select name="itemsPerPage" id="itemsPerPage" class="form-select">
                                                        <option value="15" <c:if test="${sessionScope.numPerPage == 15}">selected</c:if>>15</option>
                                                    <option value="25" <c:if test="${sessionScope.numPerPage == 25}">selected</c:if>>25</option>
                                                    <option value="50" <c:if test="${sessionScope.numPerPage == 50}">selected</c:if>>50</option>
                                                    </select>
                                                </div>
                                                <table id="example" class="table table-striped nowrap" style="width:100%">
                                                    <thead>
                                                        <tr>
                                                            <th class="border-bottom p-3" >ID</th>
                                                            <th class="border-bottom p-3" >Full name</th>
                                                            <th class="border-bottom p-3" >Gender</th>
                                                            <th class="border-bottom p-3" >Email</th>
                                                            <th class="border-bottom p-3" >Phone number</th>
                                                            <th class="border-bottom p-3" >Status</th>
                                                            <th class="border-bottom p-3" ></th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                    <c:forEach items="${user}" var="a">
                                                        <tr>
                                                            <td class="p-3">${a.userID}</td>
                                                            <td class="p-3">${a.lastName} ${a.firstName}</td>
                                                            <c:if test="${a.gender eq 'Male'}">
                                                                <td class="p-3">Male</td>
                                                            </c:if>
                                                            <c:if test="${a.gender eq 'Female'}">
                                                                <td class="p-3">Female</td>
                                                            </c:if>
                                                            <td class="p-3">${a.email}</td>
                                                            <td class="p-3">${a.phoneNumber}</td>
                                                            <td class="p-3">
                                                                <div class="form-check form-switch">
                                                                    <input class="form-check-input" type="checkbox" role="switch" disabled
                                                                           data-userid="${a.userID}"
                                                                           <c:if test="${a.status == true}">checked value="1"</c:if>/>
                                                                    </div>
                                                                </td>

                                                                <td class="text-end p-3">
                                                                    <form action="user?action=detail" method="POST">
                                                                        <input type="hidden" name="id" value="${a.userID}">
                                                                    <button type="submit" class="btn btn-info">Details</button>
                                                                </form>

                                                            </td>


                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                            <c:set var="page" value="${page}"/>

                                            <nav aria-label="Page navigation">
                                                <ul class="pagination pagination-sm flex-sm-wrap justify-content-center mt-4">
                                                    <c:if test="${page > 1}">
                                                        <li class="page-item">
                                                            <a class="page-link" href="${url}&page=${page - 1}" tabindex="-1">Previous</a>
                                                        </li>
                                                    </c:if>

                                                    <c:choose>
                                                        <c:when test="${num <= 7}">
                                                            <c:forEach begin="1" end="${num}" var="i">
                                                                <li class="page-item ${i==page?'active':''}">
                                                                    <a class="page-link" href="${url}&page=${i}">${i}</a>
                                                                </li>
                                                            </c:forEach>
                                                        </c:when>
                                                        <c:when test="${page < 5}">
                                                            <c:forEach begin="1" end="5" var="i">
                                                                <li class="page-item ${i==page?'active':''}">
                                                                    <a class="page-link" href="${url}&page=${i}">${i}</a>
                                                                </li>
                                                            </c:forEach>
                                                            <li class="page-item disabled"><a class="page-link" href="#">...</a></li>
                                                                <c:forEach begin="${num - 1}" end="${num}" var="i">
                                                                <li class="page-item">
                                                                    <a class="page-link" href="${url}&page=${i}">${i}</a>
                                                                </li>
                                                            </c:forEach>
                                                        </c:when>
                                                        <c:when test="${page >= num - 5}">
                                                            <c:forEach begin="1" end="2" var="i">
                                                                <li class="page-item">
                                                                    <a class="page-link" href="${url}&page=${i}">${i}</a>
                                                                </li>
                                                            </c:forEach>
                                                            <li class="page-item disabled"><a class="page-link" href="#">...</a></li>
                                                                <c:forEach begin="${num - 6}" end="${num}" var="i">
                                                                <li class="page-item ${i==page?'active':''}">
                                                                    <a class="page-link" href="${url}&page=${i}">${i}</a>
                                                                </li>
                                                            </c:forEach>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:forEach begin="1" end="2" var="i">
                                                                <li class="page-item">
                                                                    <a class="page-link" href="${url}&page=${i}">${i}</a>
                                                                </li>
                                                            </c:forEach>
                                                            <li class="page-item disabled"><a class="page-link" href="#">...</a></li>
                                                                <c:forEach begin="${page - 2}" end="${page + 2}" var="i">
                                                                <li class="page-item ${i==page?'active':''}">
                                                                    <a class="page-link" href="${url}&page=${i}">${i}</a>
                                                                </li>
                                                            </c:forEach>
                                                            <li class="page-item disabled"><a class="page-link" href="#">...</a></li>
                                                                <c:forEach begin="${num - 1}" end="${num}" var="i">
                                                                <li class="page-item">
                                                                    <a class="page-link" href="${url}&page=${i}">${i}</a>
                                                                </li>
                                                            </c:forEach>
                                                        </c:otherwise>
                                                    </c:choose>

                                                    <c:if test="${page < num}">
                                                        <li class="page-item">
                                                            <a class="page-link" href="${url}&page=${page + 1}">Next</a>
                                                        </li>
                                                    </c:if>
                                                </ul>
                                            </nav>
                                        </div>
                                    </div>

                                </div>
                                <c:forEach items="${user}" var="a">
                                    <div class="modal fade" id="edit${a.userID}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                        <div class="modal-dialog modal-lg modal-dialog-centered">
                                            <div class="modal-content">
                                                <div class="modal-header border-bottom p-3">
                                                    <h5 class="modal-title" id="exampleModalLabel">Update infomation</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body p-3 pt-4">
                                                    <form action="account?action=update" method="POST" onSubmit="document.getElementById('submit').disabled = true;">
                                                        <div class="row">
                                                            <div class="col-lg-12">
                                                                <div class="mb-3">
                                                                    <label class="form-label">ID</label>
                                                                    <input value="${a.userID}" readonly name="userID" id="userID" type="text" class="form-control">
                                                                </div>

                                                            </div>
                                                            <div class="mb-3">
                                                                <label class="form-label">Status <span class="text-danger">*</span></label>
                                                                <select name="status" class="form-select" aria-label="Default select example">
                                                                    <option <c:if test="${a.status == true}">selected</c:if> value="true">Active</option>
                                                                    <option <c:if test="${a.status == false}">selected</c:if> value="false">Disable</option>
                                                                    </select>
                                                                </div>
                                                                <div class="col-lg-12">
                                                                    <div class="d-grid">
                                                                        <button type="submit" id="submit" class="btn btn-primary">Chỉnh sửa</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                </c:forEach>
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


        <!-- DataTable JS -->
        <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
        <script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.4/js/dataTables.bootstrap5.min.js"></script>
        <script src="https://cdn.datatables.net/responsive/2.4.1/js/dataTables.responsive.min.js"></script>
        <script src="https://cdn.datatables.net/responsive/2.4.1/js/responsive.bootstrap5.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>

        <!-- Custom JS -->
        <script>
                                                        document.getElementById("itemsPerPage").addEventListener("change", function () {
                                                            var selectedValue = this.value;
                                                            // Cập nhật URL với số lượng bản ghi mới được chọn
                                                            window.location.href = "${url}&itemsPerPage=" + selectedValue;
                                                        });
                                                        $("#example").DataTable({
                                                            "search": false,
                                                            "paging": false,
                                                            dom: '<"clear">lfrtp'
                                                        });
        </script>

    </body>

</html>