<%-- 
    Document   : user-details
    Created on : 11-Oct-2023, 11:47:15
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <style>
            body {
                background-color: #e8f2f7;
            }
            .detail-info {
                word-wrap: break-word;
                flex: 1 1;
                min-width: 0;
                padding-left: 5px;
                color: #003553;
                font-size: 16px;
                font-style: normal;
                font-weight: 500;
                line-height: normal;
            }
            h1 {
                background: linear-gradient(83.63deg, #00b5f1 33.34%, #00e0ff 113.91%);
                background-clip: text;
                -webkit-background-clip: text;
                color: transparent;
                text-align: center;
                font-size: 35px;
                font-style: normal;
                line-height: normal;
                font-weight: bold;
            }
            p {
                margin-bottom: 5px;
            }
            .border-m {
                border: none;
                border-radius: 8px;
            }
            .child-table th,
            .child-table td {
                border: 1px solid #dee2e6;
                padding: 8px;
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
                        <a href="user?action=search" class="nav-item nav-link active"
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
                        <a href="user?action=search" class="nav-item nav-link"
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
                <!-- Blank Start -->
                <div class="container-fluid pt-4 px-4">
                    <h1 class="mb-5">Contact Information</h1>

                    <div class="row">
                        <div class="col-md-6">

                            <!-- Parent's Information -->
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title text-center">User information</h5>
                                    <div class="text-center">
                                        <img id="img-preview" style="height: 160px;width: 160px; object-fit: cover;" class="rounded-circle mx-auto d-block"
                                             src="${user.profileImage}" />
                                    </div>
                                    <form id="contact-form">
                                        <div class="form-group">
                                            <label for="full-name" class="detail-info">Full Name</label>
                                            <input type="text" class="form-control" id="full-name" name="full-name" readonly value="${user.lastName} ${user.firstName}">
                                        </div>
                                        <div class="form-group">
                                            <label for="gender" class="detail-info">Gender</label>
                                            <input type="text" class="form-control" id="gender" name="gender" value="${user.gender}" readonly>
                                        </div>
                                        <div class="form-group">
                                            <label for="email" class="detail-info">Email</label>
                                            <input type="email" class="form-control" id="email" name="email" value="${user.email}" readonly>
                                        </div>
                                        <div class="form-group">
                                            <label for="mobile" class="detail-info">Mobile</label>
                                            <input type="tel" class="form-control" id="mobile" name="mobile" value="${user.phoneNumber}" readonly>
                                        </div>
                                        <div class="form-group">
                                            <label for="address" class="detail-info">Address</label>
                                            <input type="text" class="form-control" id="address" name="address" value="${user.address}" readonly>
                                        </div>
                                        <div class="form-group">
                                            <label for="status" class="detail-info">Status</label>
                                            <span class="${user.status ? 'badge bg-success' : 'badge bg-danger'}">${user.status ? 'Active' : 'Inactive'}</span>
                                        </div>


                                    </form>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-6">
                            <!-- Children's Information -->
                            <div class="card">

                                <div class="card-body">
                                    <h5 class="card-title text-center">Children information</h5>
                                    <table class="child-table" style="width: 100%;">
                                        <tr>
                                            <th>ChildID</th>
                                            <th>Full Name</th>
                                            <th>Date of Birth</th>
                                            <th>Gender</th>
                                            <th>Relationship</th>
                                        </tr>
                                        <c:forEach items="${children}" var="c">
                                            <tr data-toggle="collapse" data-target="#row${c.childID}">
                                                <td>${c.childID}</td>
                                                <td>${c.childName}</td>
                                                <td>${c.birthday}</td>
                                                <td>${c.gender}</td>
                                                <td>${c.relationship.relationshipName}</td>
                                            </tr>

                                            <tr>
                                                <td colspan="5" class="hiddenRow">
                                                    <div class="accordian-body collapse" id="row${c.childID}" class="accordion-toggle">
                                                        <!-- New table to display additional information -->
                                                        <table class="table table-bordered">
                                                            <thead>
                                                                <tr>
                                                                    <th>ID</th>
                                                                    <th>Examination Date</th>
                                                                    <th>Disease</th>
                                                                    <th>Prescription</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <c:forEach items="${medical}" var="md">
                                                                    <c:if test="${md.mchildrenID eq c.childID}">
                                                                        <tr>
                                                                            <td>${md.medicalExaminationID}</td>
                                                                            <td>${md.examinationDate}</td>
                                                                            <td>${md.disease}</td>
                                                                            <td>${md.medicalPrescription}</td>
                                                                        </tr>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </td>
                                            </tr>

                                        </c:forEach>

                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Contact Changes History -->
                    <h1 class="text-center mt-5 mb-5">Contact Changes History</h1>
                    <div class="card">

                        <div class="card-body">
                            <table class="child-table" style="width: 100%;">
                                <tr>
                                    <th>Email</th>
                                    <th>Full Name</th>
                                    <th>Gender</th>
                                    <th>Mobile</th>
                                    <th>Address</th>
                                    <th>Updated Date</th>
                                </tr>
                                <c:choose>
                                    <c:when test="${empty requestScope.contact}">
                                        <tr>
                                            <td colspan="6" class="text-center">No contact records available.</td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach items="${requestScope.contact}" var="contact"> 
                                            <tr>
                                                <td>${contact.user.email}</td>
                                                <td>${contact.firstName} ${contact.lastName}</td>
                                                <td>${contact.gender}</td>
                                                <td>${contact.phoneNumber}</td>
                                                <td>${contact.address}</td>
                                                <td>${contact.updatedDate}</td>
                                            </tr>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>

                            </table>
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

        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
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