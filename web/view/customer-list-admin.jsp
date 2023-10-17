<!DOCTYPE html>
<html lang="en">
    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/css/bootstrap.min.css" integrity="sha384-r4NyP46KrjDleawBgD5tp8Y7UzmLA05oM1iAEQ17CSuDqnUK2+k9luXQOfXJCJ4I" crossorigin="anonymous">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin dashboard</title>
        <script src="https://cdn.jsdelivr.net/npm/chart.js@3.8.0/dist/chart.min.js"></script>
        <!--<script rel="stylesheet" href="./resources/css/admin-dashboard.css"></script>-->
        <style>
            /* Custom CSS for larger circular avatar and dropdown spacing */
            .admin-avatar {
                border-radius: 50%;
                width: 48px;
                height: 48px;
                object-fit: cover;
            }
            .sidebar {
                position: fixed;
                top: 0;
                bottom: 0;
                left: 0;
                z-index: 100;
                padding: 90px 0 0;
                box-shadow: inset -1px 0 0 rgba(0, 0, 0, .1);
                z-index: 99;
            }

            @media (max-width: 767.98px) {
                .sidebar {
                    top: 11.5rem;
                    padding: 0;
                }
            }

            .navbar {
                box-shadow: inset 0 -1px 0 rgba(0, 0, 0, .1);
            }

            @media (min-width: 767.98px) {
                .navbar {
                    top: 0;
                    position: sticky;
                    z-index: 999;
                }
            }

            .sidebar .nav-link {
                color: #333;
            }

            .sidebar .nav-link.active {
                color: #0d6efd;
            }
        </style>
    </head>
    <body>
        <%@ page import="jakarta.servlet.http.HttpSession" %>
        <%@page import = "model.*" %>
        <%@page import = "Database.*" %>
        <%@page import = "java.util.*" %>
        <%
        UserDAO userDAO = new UserDAO();
        Staff admin = (Staff) request.getAttribute("admin");
        
        int maximumDayDiff = 7;
        %>
        <nav class="navbar navbar-light bg-light p-3">
            <div class="d-flex col-12 col-md-3 col-lg-2 mb-2 mb-lg-0 flex-wrap flex-md-nowrap justify-content-between">
                <a class="navbar-brand" href="#">
                    Simple Dashboard
                </a>
                <button class="navbar-toggler d-md-none collapsed mb-3" type="button" data-toggle="collapse" data-target="#sidebar" aria-controls="sidebar" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
            </div>
            <div class="col-12 col-md-4 col-lg-2">
                <input class="form-control form-control-dark" type="text" placeholder="Search" aria-label="Search">
            </div>
            <div class="col-12 col-md-5 col-lg-8 d-flex align-items-center justify-content-md-end mt-3 mt-md-0">
                <div class="dropdown">

                    <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-expanded="false">
                        Hello, <%=admin.getStaffName()%>
                        <img src="<%=admin.getProfileImage()%>" alt="Admin Avatar" class="admin-avatar">
                    </button>
                    <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                        <li><a class="dropdown-item" href="#">Settings</a></li>
                        <li><a class="dropdown-item" href="#">Messages</a></li>
                        <li><a class="dropdown-item" href="admin?action=logout">Sign out</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="container-fluid">
            <div class="row">
                <nav id="sidebar" class="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
                    <div class="position-sticky">
                        <ul class="nav flex-column">
                            <li class="nav-item">
                                <a class="nav-link active" aria-current="page" href="admin">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-home"><path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path><polyline points="9 22 9 12 15 12 15 22"></polyline></svg>
                                    <span class="ml-2">Dashboard</span>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file"><path d="M13 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V9z"></path><polyline points="13 2 13 9 20 9"></polyline></svg>
                                    <span class="ml-2">Orders</span>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-shopping-cart"><circle cx="9" cy="21" r="1"></circle><circle cx="20" cy="21" r="1"></circle><path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"></path></svg>
                                    <span class="ml-2">Products</span>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-users"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path><circle cx="9" cy="7" r="4"></circle><path d="M23 21v-2a4 4 0 0 0-3-3.87"></path><path d="M16 3.13a4 4 0 0 1 0 7.75"></path></svg>
                                    <span class="ml-2">Customers</span>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-bar-chart-2"><line x1="18" y1="20" x2="18" y2="10"></line><line x1="12" y1="20" x2="12" y2="4"></line><line x1="6" y1="20" x2="6" y2="14"></line></svg>
                                    <span class="ml-2">Reports</span>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-layers"><polygon points="12 2 2 7 12 12 22 7 12 2"></polygon><polyline points="2 17 12 22 22 17"></polyline><polyline points="2 12 12 17 22 12"></polyline></svg>
                                    <span class="ml-2">Integrations</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </nav>
                <main class="col-md-9 ml-sm-auto col-lg-10 px-md-4 py-4">
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="#">Home</a></li>
                            <li class="breadcrumb-item active" aria-current="page">Customer List</li>
                        </ol>
                    </nav>
                    <div class="col-md-12">
                        <table class="table table-striped table-hover">
                            <thead class="text-light" style="background: #1977cc;">
                                <tr>
                                    <th scope="col">ID</th>
                                    <th scope="col">Full Name</th>
                                    <th scope="col">Gender</th>
                                    <th scope="col">Email</th>
                                    <th scope="col">Mobile</th>
                                    <th scope="col">Role</th>
                                    <th scope="col">Status</th>
                                    <th scope="col">Detail</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%List<User> users = userDAO.getAllUsersByAdmin(1, 10, "", "", "", "", "", "", 1);
                                for (User user : users) {%>
                                    <tr>
                                        <td><%=user.getUserID()%></td>
                                        <td><%=user.getFirstName()%></td>
                                        <td><%=user.getGender()%></td>
                                        <td><%=user.getEmail()%></td>
                                        <td><%=user.getPhoneNumber()%></td>
                                        <td><%=user.getRole()%></td>
                                        <td><%=user.isStatus()?"Active":"Inactive"%></td>
                                        <td><a href="#"><img src="resources/img/icon/detail.png" alt="alt" width="25px"/></a></td>
                                    </tr>
                                <%}%>
                            </tbody>
                        </table>

                    </div>

                    <footer class="pt-5 d-flex justify-content-between">
                        <span>Copyright © 2023-2024 <a href="https://github.com/BlinkWork">BlinkWork</a></span>
                        <ul class="nav m-0">
                            <li class="nav-item">
                                <a class="nav-link text-secondary" aria-current="page" href="#">Privacy Policy</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link text-secondary" href="#">Terms and conditions</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link text-secondary" href="#">Contact</a>
                            </li>
                        </ul>
                    </footer>
                </main>
            </div>
        </div>
        <!-- JavaScript to create charts -->
        <script>
            function sendDatesToServlet() {
                const startDate = document.getElementById("startDate").value;
                const endDate = document.getElementById("endDate").value;

                const servletUrl = `admin?action=changeDate&startDay=${startDate}&endDay=${endDate}`;

                console.log(startDate);
                console.log(endDate);
            }

            var reservationTrendData = {
                labels: ["Day 1", "Day 2", "Day 3", "Day 4", "Day 5", "Day 6", "Day 7"],
                datasets: [
                    {
                        label: 'Successful Reservations',
                        data: [1, 2, 4, 6, 2, 3, 1], // Provide data for successful reservations for the last 7 days here
                        fill: false,
                        borderColor: 'blue'
                    },
                    {
                        label: 'All Reservations',
                        data: [5, 6, 9, 11, 5, 7, 4], // Provide data for all reservations for the last 7 days here
                        fill: false,
                        borderColor: 'green'
                    }
                ]
            };

            var reservationTrendCtx = document.getElementById('reservationTrendChart').getContext('2d');

            var reservationTrendChart = new Chart(reservationTrendCtx, {
                type: 'line',
                data: reservationTrendData,
                options: {
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
        </script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/js/bootstrap.min.js" integrity="sha384-oesi62hOLfzrys4LxRF63OJCXdXDipiYWBnvTl9Y9/TRlw5xlKIEHpNyvvDShgf/" crossorigin="anonymous"></script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

    </body>
</html>
