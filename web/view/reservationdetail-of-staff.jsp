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
       String reserdId = request.getAttribute("reserdid") + "";
       Reservation reservation = reservationDAO.getReservationByID(Integer.parseInt(reserdId));
       CategoryServiceDAO categoryServiceDAO = new CategoryServiceDAO();
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
                <%Children thisChild = childrenDAO.getChildrenByChildrenId(reservation.getChildID()+"");
                User thisUser = userDAO.getUserByID(reservation.getUserID());%>
                <!-- Blank Start -->
                <div class="container-fluid pt-4 px-4">
                    <div
                        class="row bg-light rounded align-items-center justify-content-center mx-0"
                        >
                        <div class="col-md-12 p-0">
                            <div class="d-flex mb-4 px-4 py-3 border-bottom d-flex justify-content-between align-items-center">
                                <h4>RESERVATION DETAIL</h4>
                                <a href="staff?event=send-to-history-examination&childid=<%=thisChild.getChildID()%>&reserdid=<%=reserdId%>" class="ms-text-primary font-weight-bold">Add Medical Record</a>
                            </div>
                            <div class="row px-4 justify-content-between">
                                <div class="col-md-5 d-flex flex-column justify-content-center align-items-start">
                                    
                                    <div class="d-flex justify-content-center"><h3>Customer</h3></div>

                                    <div class="d-flex">
                                        <img src="<%=thisUser.getProfileImage()%>" alt="dr" class="rounded-3 object-cover me-3" width="100px" height="100px"/>
                                        <div>
                                            <h5><%=thisUser.getLastName()+" " +thisUser.getFirstName()%></h5>
                                            <p class="m-0 text-black-50"><i class="fas fa-envelope"></i>  <%=thisUser.getEmail()%></p>
                                            <p class="m-0 text-black-50"><i class="fas fa-phone"></i>  <%=thisUser.getPhoneNumber()%></p>
                                        </div>
                                    </div>
                                    
                                    <div class="d-flex justify-content-center"><h3 class="mt-3">Patient</h3></div>
                                    <div class="d-flex">
                                        <img src="<%=thisChild.getImage()%>" alt="dr" class="rounded-3 object-cover me-3" width="100px" height="100px"/>
                                        <div>
                                            <h5><%=thisChild.getChildName()%></h5>
                                            <p class="m-0 text-black-50"><%=thisChild.getAge()%> years old</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-4 d-flex flex-column justify-content-center align-items-center">
                                    <%Staff thisStaff = staffDAO.getStaffByStaffId(reservation.getStaffID());%>
                                    <img src="<%=thisStaff.getProfileImage()%>" alt="dr" class="rounded-circle object-cover" width="100px" height="100px"/>
                                    <h4>Dr: <%=thisStaff.getFullName()%></h4>
                                    <p class="m-0 text-black-50"><i class="fas fa-envelope"></i>  <%=thisStaff.getEmail()%></p>
                                    <p class="m-0 text-black-50"><i class="fas fa-phone"></i>  <%=thisStaff.getPhoneNumber()%></p>
                                    <p class="text-black-50"><%=thisStaff.getGender()%></p>
                                </div>
                            </div>
                            <div class="row px-4 justify-content-between mt-4">
                                <div class="col-md-8">
                                    <p><strong>Service:</strong><%=serviceDAO.getServiceByID(reservation.getServiceID()+"").getTitle()%></p>
                                    <p><strong>Cost:</strong><%=serviceDAO.getServiceByID(reservation.getServiceID()+"").getSalePrice()>0 ? serviceDAO.getServiceByID(reservation.getServiceID()+"").getSalePrice() : serviceDAO.getServiceByID(reservation.getServiceID()+"").getOriginalPrice()%></p>
                                    <p><strong>Payment Method:</strong> COD</p>
                                </div>
                                <div class="col-md-4">
                                    <p><strong>Reservation ID:</strong> <%=reservation.getReservationID()%></p>
                                    <p><strong>Reservation date:</strong> <%=reservation.getCreatedDate()%></p>
                                    <p><strong>Confirm Date: </strong><%=reservation.getReservationDate()%></p>
                                    <p><strong>Status: </strong><%=reservation.getStatus()%></p>
                                </div>
                            </div>
                            <div class="table-responsive p-4 mt-2">
                                <%if(curStaff!=null){%>
                                <table class="table table-striped table-hover">
                                    <thead class="text-light" style="background: #1977cc;">
                                        <tr>
                                            <th scope="col">Service name</th>
                                            <th scope="col">Category</th>
                                            <th scope="col">Cost</th>
                                            <th scope="col">Number Of Person</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%
                                        List<Integer> serviceIDList = reservationDAO.getListServiceIDByUserAndStaff(reservation.getUserID()+"",curStaff.getStaffID()+"");
                                        if(serviceIDList!=null){
                                        for (Integer integer : serviceIDList) {
                                        Service service = serviceDAO.getServiceByID(integer+"");
                                        %>
                                        <tr>
                                            <th scope="row">
                                                <div class="d-flex align-items-center">
                                                    <img src="<%=service.getThumbnail()%>" class="me-2 rounded-3 object-cover" width="30px" height="30px"/>
                                                    <p class="m-0"><%=service.getTitle()%></p>
                                                </div>
                                            </th>
                                            <td><%=categoryServiceDAO.getCategoryServiceByID(service.getCategoryID()+"").getCategoryName()%></td>
                                            <td><%if(service.getSalePrice()<=0){%>
                                                <span class="price"> $<%=service.getOriginalPrice()%> </span>
                                                <%}else{%>
                                                <span class="price"> $<%=service.getSalePrice()%> </span>
                                                <%}%></td>
                                            <td><%=reservationDAO.countReservationsByStaffAndService(curStaff.getStaffID()+"",service.getServiceID()+"")%></td>
                                        </tr>
                                        <%}}%>

                                    </tbody>
                                </table>
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

