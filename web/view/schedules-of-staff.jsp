<%@page import = "model.*" %>
<%@page import = "Database.*" %>
<%@page import = "java.util.*" %>
<%@page import = "java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <title>Schedules of staff</title>
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
        <style>
            .pagination-btn.inactive a {
                cursor: context-menu;
            }


        </style>
    </head>

    <body>
        <%
       String email = (String) session.getAttribute("email");
       StaffDAO staffDAO = new StaffDAO();
       StaffScheduleDAO staffScheduleDAO = new StaffScheduleDAO();
       boolean isStaff = false;
        SimpleDateFormat workDayFormat = new SimpleDateFormat("dd/MM/yyyy");
       Staff curStaff = staffDAO.getStaffByStaffEmail(email);
        %>
        <div id="myHeader" class="container-fluid position-relative bg-white d-flex p-0">
            <%if(curStaff!=null){         
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
                    <div class="navbar-nav w-100  text-light">
                        <a href="staff?event=send-to-schedules" class="nav-item nav-link active">
                            <i class="bi bi-calendar3"></i>Schedules
                        </a>
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
                        class="medical-records row bg-white rounded align-items-md-start justify-content-center mx-0"
                        >
                        <div class="col-md-12 p-0">
                            <div class="mb-4 px-4 py-3 border-bottom d-flex justify-content-between align-items-center">
                                <h4>SCHEDULE</h4>
                                <a href="staff?event=add-schedule" class="ms-text-primary font-weight-bold">Add new schedules</a>
                            </div>

                            <div class="table-responsive p-4">
                                <%if(curStaff!=null){%>
                                <table class="table table-striped table-hover">
                                    <thead class="text-light" style="background: #1977cc;">
                                        <tr>
                                            <th scope="col">Workday</th>
                                            <th scope="col">Work slot</th>
                                            <th scope="col">Status</th>
                                            <th scope="col">Action</th>
                                        </tr>
                                    </thead>
                                    <tbody id="medical-list">
                                        <%
                                            List<StaffSchedule> listStaffSchedule = staffScheduleDAO.getPageStaffScheduleByStaff(curStaff.getStaffID()+"",1,10);
                                        if(listStaffSchedule!=null){
                                        int count = 0;
                                        for (StaffSchedule staffSchedule : listStaffSchedule) {%>
                                        <tr>
                                            <td><%=workDayFormat.format(staffSchedule.getWorkday())%></td>
                                            <td><%=staffSchedule.getSlot()%></td>
                                            <td><%=staffSchedule.getStatus()%></td>
                                            <td>
                                                <div class="d-flex">
                                                    <a class="me-3" href="staff?event=edit-schedule&scheduleID=<%=staffSchedule.getScheduleID()%>"><i class="fas fa-pencil-alt ms-text-primary"></i></a>
                                                    <a href="sms?event=delete&id=<%=staffSchedule.getScheduleID()%>" style="color: #d9534f;"><i class="far fa-trash-alt ms-text-danger"></i></a>
                                                </div>
                                            </td>
                                        </tr>
                                        <% count++; %>
                                        <%}
                                        if (count < 10) {
                                        for (int i = 1;i<=10-count;i++) { %>
                                        <tr>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                        </tr>                                 
                                        <% }
                                        }
                                        
                                                                            }%>

                                    </tbody>
                                </table>

                                <ul id="pagination-container" style="padding-left: 0px;">
                                    <li id="previousButton" class="pagination-btn inactive">
                                        <a href="#" onclick="">&lt;</a>
                                    </li>
                                    <li class="pagination-btn active">
                                        <span id="pageNumber">1</span>
                                    </li>
                                    <li id="nextButton" class="pagination-btn <%=(staffScheduleDAO.countStaffSchedule(curStaff.getStaffID()+"") > 10) ? "" : "inactive"%> ">
                                        <a href="#" onclick="">&gt;</a>
                                    </li>

                                </ul>

                                <%}%>
                            </div>
                        </div>
                    </div>
                    <div class="mt-4">
                        <jsp:include page="layout/footer.jsp" />
                    </div>
                </div>

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

                // Pagination
                const previousButton = document.getElementById('previousButton');
                const nextButton = document.getElementById('nextButton');
                var currentPage = 1;
                var maximumPagination = <%=(staffScheduleDAO.countStaffSchedule(curStaff.getStaffID()+"")+9)/10%>;
                var header = document.getElementById('myHeader');

                document.getElementById('nextButton').addEventListener('click', function () {
                    if (currentPage < parseInt(maximumPagination)) {
                        currentPage++;
                        changePage(currentPage);
                    }
                });
                document.getElementById('previousButton').addEventListener('click', function () {
                    if (currentPage > 1) {
                        currentPage--;
                        changePage(currentPage);
                    }
                });

                function changePage(page) {
                    // Make an AJAX request to the specified URL
                    currentPage = parseInt(page);
                    var url = "sms?event=getPage&staffId=" + <%=curStaff.getStaffID()%> + "&page=" + page;
                    var xhr = new XMLHttpRequest();
                    xhr.open('GET', url, true);
                    xhr.onload = function () {
                        if (xhr.status === 200) {
                            header.scrollIntoView({behavior: 'smooth'});
                            // Clear the data in the table
                            document.getElementById('medical-list').innerHTML = '';
                            // Parse the response data
                            var responseLines = xhr.responseText.split('\n');
                            var count = 10;
                            responseLines.forEach(function (line) {
                                count--;
                                var attributes = line.split('&');
                                if (attributes.length >= 4) {
                                    var tr = document.createElement('tr');
                                    // Create the first <td> with a delete link
                                    var td1 = document.createElement('td');
                                    var div = document.createElement('div');
                                    var editLink = document.createElement('a');
                                    editLink.href = "staff?event=edit-schedule&scheduleID=" + attributes[0];
                                    editLink.classList.add('me-3');
                                    var pencilIcon = document.createElement('i');
                                    pencilIcon.classList.add('fas', 'fa-pencil-alt', 'ms-text-primary');
                                    editLink.appendChild(pencilIcon);
                                    div.appendChild(editLink);

                                    var deleteLink = document.createElement('a');
                                    deleteLink.href = "staffschedules?event=delete&id=" + attributes[0];
                                    deleteLink.style.color = '#d9534f';
                                    var trashIcon = document.createElement('i');
                                    trashIcon.classList.add('far', 'fa-trash-alt', 'ms-text-danger');
                                    deleteLink.appendChild(trashIcon);
                                    div.appendChild(deleteLink);
                                    td1.appendChild(div);

                                    // Create the second <td> with the second attribute
                                    var td2 = document.createElement('td');
                                    td2.textContent = attributes[1];
                                    // Create the third <td> with the third attribute
                                    var td3 = document.createElement('td');
                                    td3.textContent = attributes[2];
                                    var td4 = document.createElement('td');
                                    td4.textContent = attributes[3];
                                    tr.appendChild(td2);
                                    tr.appendChild(td3);
                                    tr.appendChild(td4);
                                    tr.appendChild(td1);
                                    document.getElementById('medical-list').appendChild(tr);
                                }
                            });
                            for (let i = 0; i <= count; i++) {
                                var tableRow = document.createElement('tr');
                                for (let j = 1; j <= 4; j++) {
                                    const tableCell = document.createElement('td');
                                    tableCell.textContent = '\u00A0';
                                    tableRow.appendChild(tableCell);
                                }
                                document.getElementById('medical-list').appendChild(tableRow);
                            }

                            const spanNumber = document.getElementById("pageNumber");
                            spanNumber.textContent = page;

                            // Change the button to paging
                            if (page !== 1) {
                                previousButton.classList.remove('inactive');
                            } else {
                                previousButton.classList.add('inactive');
                            }
                            if (page != maximumPagination) {
                                nextButton.classList.remove('inactive');
                            } else {
                                nextButton.classList.add('inactive');
                            }

                        }
                    };
                    xhr.send();
                }
            </script>
    </body>
</html>

