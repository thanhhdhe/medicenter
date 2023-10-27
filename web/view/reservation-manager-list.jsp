<%-- 
    Document   : HomePage
    Created on : 12-Sep-2023, 20:29:49
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
    </head>

    <body>
        <%
       String email = (String) session.getAttribute("email");
       StaffDAO staffDAO = new StaffDAO();
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
                    <div class="d-flex justify-content-around">
                        <div class="" >
                            <button style="border: 0px; border-radius: 5px; background-color: #6994eb; font-family: fantasy" id="sortButton" onclick="toggleSort()">
                                Unsort
                            </button>
                        </div>
                        <div class="border-0">
                            <div class="">
                                <% List<Staff> staffList= staffDAO.getStaffsByRole("doctor"); %>
                                <c:set var="staffList" value="<%= staffList %>" />
                                <select class="dropdown-item" name="staff" id="staffid">
                                    <c:forEach var="staff" items="${staffList}">                                                                                                                  
                                        <option value="${staff.getStaffID()}">${staff.getStaffName()}</option>                                                                                                                  
                                    </c:forEach>
                                </select>

                            </div>
                        </div>
                        <div class="col-md-3 px-4 d-flex flex-column">
                            <input type="text" name="customerName" id="customerName" class="mt-2 form-control" placeholder="Search Customer Name">
                        </div>
                    </div>

                    <div
                        class="row vh-100 bg-light rounded align-items-top justify-content-center mx-0"
                        >
                        <div class="col-md-12 text-center justify-content-center">
                            <div class="table-responsive p-4 w-100"  id="reservation-list">
                                <%if(curStaff!=null){%>

                                <table class="table table-striped table-hover">
                                    <thead class="text-light" style="background: #1977cc;">
                                        <tr>
                                            <th scope="col">ID</th>
                                            <th scope="col">Service Name</th>
                                            <th scope="col">Full name</th>
                                            <th scope="col">Children</th>
                                            <th scope="col">Reservation Date</th>
                                            <th scope="col">Slot</th>
                                            <th scope="col">Doctor</th>
                                            <th scope="col">Status</th>
                                            <th scope="col">Cost</th>
                                            <th scope="col">Detail</th>

                                        </tr>
                                    </thead>

                                    <tbody id="sort-list">

                                        <c:forEach var="reservation" items="${requestScope.Reservation}"> 

                                            <tr>
                                                <td>${reservation.getReservationID()}</td>
                                                <c:set var="ServiceID" value="${reservation.getServiceID()}" />
                                                <c:set var="ServiceIDString" value="${ServiceID.toString()}" />
                                                <%
                                                    ServiceDAO serviceDAO = new ServiceDAO();
                                                    Service service = serviceDAO.getServiceByID((String) pageContext.getAttribute("ServiceIDString"));
                                                %>
                                                <td><%= service.getTitle() %></td>
                                                <c:set var="UserID" value="${reservation.getUserID()}" />
                                                <c:set var="UserIDString" value="${UserID}" />
                                                <%
                                                    UserDAO userdao = new UserDAO();
                                                    User user = userdao.getUserByID((int) pageContext.getAttribute("UserIDString"));
                                                %>
                                                <td><%= user.getFirstName()%> <%= user.getLastName()%></td>
                                                <c:set var="ChildID" value="${reservation.getChildID()}" />
                                                <c:set var="ChildIDString" value="${ChildID.toString()}" />
                                                <%
                                                    ChildrenDAO childrenDAO = new ChildrenDAO();
                                                    Children children = childrenDAO.getChildrenByChildrenId((String) pageContext.getAttribute("ChildIDString"));
                                                %>
                                                <td><%= children.getChildName()%></td>
                                                <td>${reservation.getReservationDate()}</td>
                                                <td>${reservation.getReservationSlot()}</td>
                                                <c:set var="ReservationDate" value="${reservation.getReservationDate()}" />
                                                <c:set var="ReservationDateString" value="${ReservationDate.toString()}" />
                                                <c:set var="ReservationSlot" value="${reservation.getReservationSlot()}" />
                                                <c:set var="ReservationSlotString" value="${ReservationSlot.toString()}" />
                                                <c:set var="StaffID" value="${reservation.getStaffID()}" />
                                                <c:set var="StaffIDString" value="${StaffID}" />
                                                <%
                                                    
                                                    Staff staffa = staffDAO.getStaffByStaffId((int) pageContext.getAttribute("StaffIDString"));
                                                    List<Staff> staff = staffDAO.getStaffsBySlot((String) pageContext.getAttribute("ReservationDateString"), (String) pageContext.getAttribute("ReservationSlotString"));
                                                %>
                                                <td>
                                                    <div class="dropdown">
                                                        <button style="border: 0px; padding: 0px" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                                                            <span class="badge bg-primary"  id="doctorBadge-${reservation.getReservationID()}"><%= staffa.getStaffName() %></span>
                                                        </button>
                                                        <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                                                            <c:forEach var="listdoctor" items="<%= staff %>">
                                                                <li><a class="dropdown-item status-change" href="#" onclick="changeDoctor(this, ${listdoctor.getStaffID()},${reservation.getReservationID()})">${listdoctor.getStaffName()}</a></li>
                                                                </c:forEach>
                                                        </ul>

                                                    </div>
                                                </td>

                                                <td>
                                                    <div class="dropdown">
                                                        <button style="border: 0px; padding: 0px" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                                                            <span class="badge bg-primary"  id="statusBadge-${reservation.getReservationID()}">${reservation.getStatus()}</span>
                                                        </button>
                                                        <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                                                            <li><a class="dropdown-item status-change" href="#" onclick="changestatus(this, ${reservation.getReservationID()})">cancel</a></li>
                                                            <li><a class="dropdown-item status-change" href="#" onclick="changestatus(this, ${reservation.getReservationID()})">pending</a></li>
                                                            <li><a class="dropdown-item status-change" href="#" onclick="changestatus(this, ${reservation.getReservationID()})">awaiting confirmation</a></li>
                                                            <li><a class="dropdown-item status-change" href="#" onclick="changestatus(this, ${reservation.getReservationID()})">waiting for examination</a></li>
                                                            <li><a class="dropdown-item status-change" href="#" onclick="changestatus(this, ${reservation.getReservationID()})">done</a></li>
                                                        </ul>
                                                    </div>
                                                </td>
                                                <td>${reservation.getCost()}</td>
                                                <td><a href="staff?event=send-to-reservation-manager-detail&reserdid=${reservation.getReservationID()}"><img src="resources/img/icon/detail.png" alt="alt" width="25px"/></a></td>
                                            </tr>

                                        </c:forEach>


                                    </tbody> 

                                </table>
                                <% ReservationDAO reservationdao = new ReservationDAO(); %>
                                <div class="d-flex justify-content-center mb-5" id="pagination-container">
                                    <button style="border: 0px; border-radius: 5px; background-color: #6994eb" class="pagination-btn ms-2 active" data-page="1">1</button>
                                    <%for (int i = 2; i <=(reservationdao.getTotalReservation()+9)/10; i++) {%>
                                    <button style="border: 0px; border-radius: 5px; background-color: #6994eb" class="pagination-btn ms-2 inactive" data-page="<%=i%>"><%=i%></button>
                                    <%}%>
                                </div> 
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
        <!-- Template Javascript -->
        <script>
            document.querySelector('.sidebar-toggler').addEventListener('click', function () {
                var sidebar = document.querySelector('.sidebar');
                var content = document.querySelector('.content');

                sidebar.classList.toggle('open');
                content.classList.toggle('open');

                return false;
            });
            // Khởi tạo biến để theo dõi trạng thái của nút
            var isSorted = false;
            var paginationButtons = document.querySelectorAll('.pagination-btn');
            paginationButtons.forEach(function (button) {
                button.addEventListener('click', function (event) {
                    event.preventDefault(); // Ngăn chặn trình duyệt chuyển đến URL trong thẻ 'a'

                    if (!this.classList.contains('active')) {
                        document.querySelectorAll('.pagination-btn').forEach(function (paginationBtn) {
                            if (paginationBtn.classList.contains('active')) {
                                paginationBtn.classList.remove('active');
                            }
                        });
                        this.classList.add('active');

                        var page = this.dataset.page;
                        if (isSorted) {
                            loadPageServices(page, "sort"); // Gọi hàm loadServices() để tải danh sách dịch vụ của trang được chọn
                        } else {
                            loadPageServices(page, "");
                        }

                    }
                });
            });


            // Hàm thực hiện việc thay đổi giá trị của nút và gọi loadPageServices
            function toggleSort() {
                // Lấy thẻ button bằng id
                var sortButton = document.querySelector('#sortButton');

                if (isSorted) {
                    // Nếu đã được sắp xếp (sort), thay đổi giá trị và gọi loadPageServices với giá trị "unsort"
                    sortButton.textContent = "Unsort";
                    loadPageServices(1, "");
                } else {
                    // Nếu chưa được sắp xếp (unsort), thay đổi giá trị và gọi loadPageServices với giá trị "sort"
                    sortButton.textContent = "Sort";
                    loadPageServices(1, "sort");
                }

                // Đảo ngược trạng thái của biến
                isSorted = !isSorted;
            }
            // Hàm tải dữ liệu của trang bằng Ajax
            function loadPageServices(page, sortType) {
                // Gửi yêu cầu Ajax đến Servlet với tham số trang

                var xhr = new XMLHttpRequest();
                xhr.open('POST', 'reservationcontactmanager?event=reservation-list-paging&page=' + page
                        + '&sortstatus=' + sortType);

                xhr.onload = function () {
                    if (xhr.status === 200) {
                        // Xử lý dữ liệu trả về từ máy chủ và cập nhật nội dung trang
                        document.querySelector('#sort-list').innerHTML = xhr.responseText;
                    } else {
                        console.error('Error:', xhr.status);
                    }
                };

                xhr.onerror = function () {
                    console.error('Error:', xhr.status);
                };

                xhr.send();
            }
            function changestatus(a, uid) {
                var text = a.textContent;
                var textchange = document.getElementById("statusBadge-" + uid);
                textchange.textContent = text;

                // Gửi yêu cầu Ajax đến servlet
                var xhr = new XMLHttpRequest();
                xhr.open("POST", "reservationcontactmanager?event=updatestatus&status=" + text + "&reservationID=" + uid, true);

                xhr.onload = function () {

                };

                xhr.onerror = function () {

                };

                xhr.send();
            }
            function changeDoctor(a, doctorId, reservationID) {
                var text = a.textContent;
                var textchange = document.getElementById("doctorBadge-" + reservationID);
                textchange.textContent = text;

                // Gửi yêu cầu Ajax đến servlet
                var xhr = new XMLHttpRequest();
                xhr.open("POST", "reservationcontactmanager?event=updatedoctor&doctorID=" + doctorId + "&reservationID=" + reservationID, true);

                xhr.onload = function () {

                };

                xhr.onerror = function () {

                };

                xhr.send();
            }

            document.addEventListener("DOMContentLoaded", function () {
                var searchInputs = document.querySelectorAll('select[name="staff"]');
                searchInputs.forEach(function (input) {
                    input.addEventListener('change', function () {

                        loadPageServicesBFill(1, 'fillterdoctor');


                    });
                });
                var customerNameInput = document.getElementById('customerName');
                customerNameInput.addEventListener('input', function(){loadPageServicesBFill(1, 'searchname'); });
            });


            // Hàm tải dữ liệu của trang bằng Ajax
            function loadPageServicesBFill(page, action) {
                 var customerNameInput = document.getElementById('customerName');
                // Gửi yêu cầu Ajax đến Servlet với tham số trang
                var customerName = customerNameInput.value;
                var staff = document.querySelector('select[name="staff"]').value;
                var xhr = new XMLHttpRequest();
                xhr.open('POST', 'reservationcontactmanager?event=fillter&page=' + page
                        + "&staffId=" + staff + '&action=' + action + '&name=' + customerName);

                xhr.onload = function () {
                    if (xhr.status === 200) {
                        // Xử lý dữ liệu trả về từ máy chủ và cập nhật nội dung trang
                        document.querySelector('#reservation-list').innerHTML = xhr.responseText;
                        //console.log(xhr.getResponseHeader('pagination'));
                        document.querySelector('#pagination-container').innerHTML = xhr.getResponseHeader('pagination');
                    } else {
                        console.error('Error:', xhr.status);
                    }
                };

                xhr.onerror = function () {
                    console.error('Error:', xhr.status);
                };

                xhr.send();
            }
            function paging(page, action) {
                var customerNameInput = document.getElementById('customerName');
                // Gửi yêu cầu Ajax đến Servlet với tham số trang
                var customerName = customerNameInput.value;
                var staff = document.querySelector('select[name="staff"]').value;
                // Gửi yêu cầu Ajax đến máy chủ để lấy danh sách dịch vụ
                var xhr = new XMLHttpRequest();
                xhr.open('POST', 'reservationcontactmanager?event=fillter&page=' + page
                        + "&staffId=" + staff + '&action=' + action + '&name=' + customerName);

                xhr.onload = function () {
                    if (xhr.status === 200) {
                        // Xử lý dữ liệu trả về từ máy chủ và cập nhật nội dung trang
                        document.querySelector('#reservation-list').innerHTML = xhr.responseText;
                        document.querySelector('#pagination-container').innerHTML = xhr.getResponseHeader('pagination');
                    } else {
                        console.error('Error:', xhr.status);
                    }
                };

                xhr.onerror = function () {
                    console.error('Error:', xhr.status);
                };

                xhr.send();

                var paginationButtons = document.querySelectorAll('.pagination-btn');
                paginationButtons.forEach(function (button) {
                    button.addEventListener('click', function (e) {
                        e.preventDefault();
                        if (!this.classList.contains('active')) {
                            document.querySelectorAll('.pagination-btn').forEach(function (paginationBtn) {
                                if (paginationBtn.classList.contains('active')) {
                                    paginationBtn.classList.remove('active');
                                }
                            });
                            this.classList.remove('inactive');
                            this.classList.add('active');
                        }
                    });
                });
            }
            
        </script>
    </body>
</html>

