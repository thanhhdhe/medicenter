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
    </head>

    <body>
        <%
       String email = (String) session.getAttribute("email");
       StaffDAO staffDAO = new StaffDAO();
       ReservationDAO reservationDAO = new ReservationDAO();
       ChildrenDAO childrenDAO = new ChildrenDAO();
       ServiceDAO serviceDAO = new ServiceDAO();
       UserDAO userDAO = new UserDAO();
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
                        <a href="staff?event=send-to-reservations-list" class="nav-item nav-link active"
                           ><i class="fas fa-list-alt"></i>Reservations List</a
                        >
                    </div>  
                    <div class="navbar-nav w-100  text-light">
                        <a href="staff?event=send-to-medical-examination" class="nav-item nav-link"
                           ><i class="far fa-check-square"></i>Medical examination</a
                        >
                    </div>
                    <div class="navbar-nav w-100  text-light">
                        <a href="staff?event=send-to-schedules" class="nav-item nav-link">
                          <i class="bi bi-calendar3"></i>Schedules
                        </a>
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
                        <a href="postManage" class="nav-item nav-link active"
                           ><i class="bi bi-file-earmark-post"></i>Post</a
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
                        <div class="col-md-12 p-0">
                            <div class="mb-4 px-4 py-3 border-bottom d-flex justify-content-between align-items-center">
                                <h4>RESERVATION LIST</h4>
                            </div>
                            <div class="row justify-content-between align-items-start mb-4 px-4 py-3 border-bottom d-flex">
                                <div class="col-md-3 px-4 d-flex flex-column">
                                    <input type="number" name="reservationId" id="reservationId" placeholder="Search Reservation Id" class="form-control">
                                    <input type="text" name="customerName" id="customerName" class="mt-2 form-control" placeholder="Search Customer Name">
                                </div>
                                <div class="col-md-4 px-4 d-flex">
                                    <div class="input-group mb-3 text-black-50">
                                        <input type="date" class="form-control" name="from" id="from">
                                        <span class="input-group-text">to</span>
                                        <input type="date" class="form-control"name="to" id="to">
                                    </div>
                                </div>
                                <div class="col-md-3 px-4">
                                    <select class="form-select" id="sort">
                                        <option selected value="">Sort By</option>
                                        <option value="customername">Customer Name</option>
                                        <option value="status">Status</option>
                                        <option value="price-high">Highest Cost</option>
                                        <option value="price-low">Lowest Cost</option>
                                        <option value="date-latest">Latest Booking Date</option> 
                                        <option value="date-earliest">Earliest Booking Date</option>
                                    </select>
                                    <select class="form-select mt-2" id="status">
                                        <option selected value="">Status</option>
                                        <option value="pending">Pending</option>
                                        <option value="awaiting confirmation">Awaiting Confirmation</option>
                                        <option value="waiting for examination">Waiting For Examination</option> 
                                        <option value="done">Done</option>
                                        <option value="cancel">Cancel</option>
                                    </select>
                                </div>
                            </div>
                            <div class="table-responsive p-4">
                                <%if(curStaff!=null){%>
                                <table class="table table-striped table-hover">
                                    <thead class="text-light" style="background: #1977cc;">
                                        <tr>
                                            <th scope="col">ID</th>
                                            <th scope="col">Reserved date</th>
                                            <th scope="col">Customer name</th>
                                            <th scope="col">Service</th>
                                            <th scope="col">Cost</th>
                                            <th scope="col">Status</th>
                                            <th scope="col">Detail</th>
                                        </tr>
                                    </thead>
                                    <tbody id="reservations-list">
                                        <%
                                        List<Reservation> reservations = reservationDAO.getPageReservationByStaffID(curStaff.getStaffID()+"",1,10);
        
                                        if(reservations!=null){
                                        for (Reservation reservation : reservations) {
                                        %>
                                        <tr>
                                            <th scope="row"><a href="staff?event=send-to-reservation-detail&reserdid=<%=reservation.getReservationID()%>" class="text-decoration-none text-dark"><%=reservation.getReservationID()%></a></th>
                                            <td><%=reservation.getReservationDate()%></td>
                                            <td>
                                                <div class="d-flex align-items-center">
                                                    <img class="rounded-circle object-cover me-3" src="<%=userDAO.getUserByID(reservation.getUserID()).getProfileImage()%>" alt="alt" width="30px" height="30px"/>
                                                    <div><%=userDAO.getUserByID(reservation.getUserID()).getFirstName()%></div>
                                                </div>
                                            </td>
                                            <td><%=serviceDAO.getServiceByID(reservation.getServiceID()+"").getTitle()%></td>
                                            <td><%=reservation.getCost()%></td>
                                            <td><%if(reservation.getStatus().equals("done")){%>
                                                   <p class="bg-success rounded-2 text-white m-0 p-1 px-2" style="width: fit-content;"><%=reservation.getStatus()%></p>
                                                <%}else if(reservation.getStatus().equals("cancel")){%>
                                                   <p class="bg-danger rounded-2 text-white m-0 p-1 px-2" style="width: fit-content;"><%=reservation.getStatus()%></p>
                                                <%}else{%>
                                                   <p class="bg-primary rounded-2 text-white m-0 p-1 px-2" style="width: fit-content;"><%=reservation.getStatus()%></p>
                                                <%}%>
                                            </td>
                                            <td><a href="staff?event=send-to-reservation-detail&reserdid=<%=reservation.getReservationID()%>"><img src="resources/img/icon/detail.png" alt="alt" width="25px"/></a></td>
                                        </tr>
                                        <%}}%>

                                    </tbody>
                                </table>
                                
                                

                                <ul id="pagination-container">
                                    <%if(reservationDAO.countReservationsByStaffID(curStaff.getStaffID()+"")<=40){%>
                                        <%if(reservationDAO.countReservationsByStaffID(curStaff.getStaffID()+"")>0){%>
                                            <li class="pagination-btn active"><span>1</span></li>
                                            <%for (int i = 2; i <= (reservationDAO.countReservationsByStaffID(curStaff.getStaffID()+"")+9)/10; i++) {%>
                                                <li class="pagination-btn inactive"><a data-page="<%=i%>" href="#"><%=i%></a></li>
                                            <%}%>
                                        <%}%>
                                    <%}else{%>
                                        <!--<li class="pagination-btn inactive">><a href="#">&lt;</a></li>-->
                                        <li class="pagination-btn active"><span>1</span></li>
                                        <li class="pagination-btn inactive"><a href="#" data-page="2">2</a></li>
                                        <li class="pagination-btn inactive"><a href="#" data-page="3">3</a></li>
                                        <span>...</span>
                                        <li class="pagination-btn inactive"><a href="#" data-page="<%=(reservationDAO.countReservationsByStaffID(curStaff.getStaffID()+"")+9)/10%>"><%=(reservationDAO.countReservationsByStaffID(curStaff.getStaffID()+"")+9)/10%></a></li>
                                        <li class="pagination-btn inactive"><a href="#">&gt;</a></li>
                                    <%}%>
                                    
                                </ul>
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
        <script src="./resources/js/reservation-staff-script.js"></script>
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

