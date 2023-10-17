<!DOCTYPE html>
<html lang="en">
    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/css/bootstrap.min.css" integrity="sha384-r4NyP46KrjDleawBgD5tp8Y7UzmLA05oM1iAEQ17CSuDqnUK2+k9luXQOfXJCJ4I" crossorigin="anonymous">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin dashboard</title>
        <script src="https://cdn.jsdelivr.net/npm/chart.js@3.8.0/dist/chart.min.js"></script>
        <!--<link rel="stylesheet" href="./resources/css/admin-dashboard.css"></link>-->
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
            .star {
                color: gold !important;
                font-size: 24px;
            }
        </style>
    </head>
    <body>
        <%@ page import="java.util.HashMap,java.util.Map,jakarta.servlet.http.HttpSession,java.util.List,java.util.ArrayList" %>
        <%@ page import="model.Staff" %>
        <%
        // Retrieve data from the servlet
        int cancelReservationCount = (int) request.getAttribute("cancelReservationCount");
        int doneReservationCount = (int) request.getAttribute("doneReservationCount");
        int submittedReservationCount = (int) request.getAttribute("submittedReservationCount");
        int newlyUserCount = (int) request.getAttribute("newlyUserCount");
        int newlyUserReservedCount = (int) request.getAttribute("newlyUserReservedCount");
        float totalRevenues = (float) request.getAttribute("totalRevenues");
        Map<String, Float> revenueCategory = (Map<String, Float>) request.getAttribute("revenueCategory");
        float totalAverageStar = (float) request.getAttribute("totalAverageStar");
        Map<String, Float> averageStarByServiceID = (Map<String, Float>) request.getAttribute("averageStarByServiceID");
        
        List<Integer> doneReservation = (List<Integer>) request.getAttribute("doneReservation");
        List<Integer> allReservation = (List<Integer>) request.getAttribute("allReservation");
        
        Staff admin = (Staff) request.getAttribute("admin");
        
        int maximumDayDiff = 7;
        %>
        <nav class="navbar navbar-light bg-light p-3">
            <div class="d-flex col-12 col-md-3 col-lg-2 mb-2 mb-lg-0 flex-wrap flex-md-nowrap justify-content-between">
                <a class="navbar-brand" href="#">
                    Admin Dashboard
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
                                    <span class="ml-2">Reservation</span>
                                </a>
                            </li>
                            <!--                            <li class="nav-item">
                                                            <a class="nav-link" href="#">
                                                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-shopping-cart"><circle cx="9" cy="21" r="1"></circle><circle cx="20" cy="21" r="1"></circle><path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"></path></svg>
                                                                <span class="ml-2">Products</span>
                                                            </a>
                                                        </li>-->
                            <li class="nav-item">
                                <a class="nav-link" href="admin?action=send-to-customer-list">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-users"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path><circle cx="9" cy="7" r="4"></circle><path d="M23 21v-2a4 4 0 0 0-3-3.87"></path><path d="M16 3.13a4 4 0 0 1 0 7.75"></path></svg>
                                    <span class="ml-2">Customers</span>
                                </a>
                            </li>
                            <!--                            <li class="nav-item">
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
                                                        </li>-->
                            <!--                            <li class="nav-item">
                                                            <a class="btn btn-sm btn-secondary ml-3 mt-2" href="https://themesberg.com/blog/bootstrap/simple-bootstrap-5-dashboard-tutorial">
                                                                <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-book" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                                                <path fill-rule="evenodd" d="M1 2.828v9.923c.918-.35 2.107-.692 3.287-.81 1.094-.111 2.278-.039 3.213.492V2.687c-.654-.689-1.782-.886-3.112-.752-1.234.124-2.503.523-3.388.893zm7.5-.141v9.746c.935-.53 2.12-.603 3.213-.493 1.18.12 2.37.461 3.287.811V2.828c-.885-.37-2.154-.769-3.388-.893-1.33-.134-2.458.063-3.112.752zM8 1.783C7.015.936 5.587.81 4.287.94c-1.514.153-3.042.672-3.994 1.105A.5.5 0 0 0 0 2.5v11a.5.5 0 0 0 .707.455c.882-.4 2.303-.881 3.68-1.02 1.409-.142 2.59.087 3.223.877a.5.5 0 0 0 .78 0c.633-.79 1.814-1.019 3.222-.877 1.378.139 2.8.62 3.681 1.02A.5.5 0 0 0 16 13.5v-11a.5.5 0 0 0-.293-.455c-.952-.433-2.48-.952-3.994-1.105C10.413.809 8.985.936 8 1.783z"/>
                                                                </svg>
                                                                <span class="ml-2">Gray</span>
                                                            </a>
                                                        </li>
                                                        <li class="nav-item">
                                                            <a class="btn btn-sm btn-warning ml-3 mt-2" href="https://themesberg.com/product/admin-dashboard/volt-bootstrap-5-dashboard">
                                                                Yellow
                                                            </a>
                                                        </li>
                                                        <li class="nav-item">
                                                            <a class="btn btn-sm btn-primary ml-3 mt-2" href="https://themesberg.com">
                                                                Blue
                                                            </a>
                                                        </li>-->
                        </ul>
                    </div>
                </nav>
                <main class="col-md-9 ml-sm-auto col-lg-10 px-md-4 py-4">
                    <!--                    <nav aria-label="breadcrumb">
                                            <ol class="breadcrumb">
                                                <li class="breadcrumb-item"><a href="#">Home</a></li>
                                                <li class="breadcrumb-item active" aria-current="page">Overview</li>    
                                            </ol>
                                        </nav>-->
                    <h1 class="h2">Dashboard</h1>

                    <div class="col-md-6 offset-md-6">
                        <form class="mb-4">
                            <div class="row" style="max-width: 800px;">
                                <div class="col-sm-4">
                                    <input type="date" id="startDate" class="form-control">
                                </div>
                                <div class="col-sm-4">
                                    <input type="date" id="endDate" class="form-control">
                                </div>
                                <div class="col-sm-4 d-flex align-items-end">
                                    <button type="button" class="btn btn-primary btn-block" onclick="sendDatesToServlet()">Change date</button>
                                </div>
                            </div>
                        </form>
                    </div>

                    <p>The trend of reservation counts :</p>
                    <div class="chart"> 
                        <div class="row mt-4">
                            <div class="col-md-12">
                                <canvas id="reservationTrendChart"></canvas>
                            </div>
                        </div>
                    </div>

                    <div class="row my-4">
                        <div class="col-12 col-md-6 col-lg-3 mb-4 mb-lg-0">
                            <div class="card">
                                <h5 class="card-header">Reservation</h5>
                                <div class="card-body">
                                    <p id="submittedReservation" class="card-text text-success"><%=submittedReservationCount%> reservations submitted</p>
                                    <p id="doneReservation" class="card-text text-secondary"><%=doneReservationCount%> reservations done</p>
                                    <p id="cancelReservation" class="card-text text-danger"><%=cancelReservationCount%> reservations cancelled</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-12 col-md-6 mb-4 mb-lg-0 col-lg-3">
                            <div class="card">
                                <h5 class="card-header">Revenue</h5>
                                <div class="card-body">
                                    <h5 id="totalRevenues" class="card-title">$<%=totalRevenues%></h5>
                                    <%for (Map.Entry<String, Float> entry : revenueCategory.entrySet()) {
                                        String key = entry.getKey();
                                        Float value = entry.getValue(); %>
                                    <p class="card-text" id="revenue_<%=key%>"><%=key%> : $<%=value%></p>
                                    <% } %>
                                </div>
                            </div>
                        </div>
                        <div class="col-12 col-md-6 mb-4 mb-lg-0 col-lg-3">
                            <div class="card">
                                <h5 class="card-header">Customer</h5>
                                <div class="card-body">
                                    <p id="newlyRegister" class="card-text">Newly registered : <%=newlyUserCount%></p>
                                    <p id="newlyReserved" class="card-text text-success">Newly reserved : <%=newlyUserReservedCount%></p>
                                </div>
                            </div>
                        </div>
                        <div class="col-12 col-md-6 mb-4 mb-lg-0 col-lg-3">
                            <div class="card">
                                <h5 class="card-header">Feedback</h5>
                                <div class="card-body">
                                    <h5 id="totalRating" class="card-title">Total rating : <%=totalAverageStar%>  <span class="star">&#9733;</span></h5>
                                    <%for (Map.Entry<String, Float> entry : averageStarByServiceID.entrySet()) {
                                        String key = entry.getKey();
                                        Float value = entry.getValue(); %>
                                    <p class="card-text">
                                        <span id="serviceRating_<%=key%>" ><%=key%> : <%=value%> </span>
                                        <span class="star">&#9733;</span>
                                    </p>
                                    <% } %>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--                    <div class="row">
                                            <div class="col-12 col-xl-8 mb-4 mb-lg-0">
                                                <div class="card">
                                                    <h5 class="card-header">Latest transactions</h5>
                                                    <div class="card-body">
                                                        <div class="table-responsive">
                                                            <table class="table">
                                                                <thead>
                                                                    <tr>
                                                                        <th scope="col">Order</th>
                                                                        <th scope="col">Product</th>
                                                                        <th scope="col">Customer</th>
                                                                        <th scope="col">Total</th>
                                                                        <th scope="col">Date</th>
                                                                        <th scope="col"></th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <tr>
                                                                        <th scope="row">17371705</th>
                                                                        <td>Volt Premium Bootstrap 5 Dashboard</td>
                                                                        <td>johndoe@gmail.com</td>
                                                                        <td>61.11</td>
                                                                        <td>Aug 31 2020</td>
                                                                        <td><a href="#" class="btn btn-sm btn-primary">View</a></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <th scope="row">17370540</th>
                                                                        <td>Pixel Pro Premium Bootstrap UI Kit</td>
                                                                        <td>jacob.monroe@company.com</td>
                                                                        <td>153.11</td>
                                                                        <td>Aug 28 2020</td>
                                                                        <td><a href="#" class="btn btn-sm btn-primary">View</a></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <th scope="row">17371705</th>
                                                                        <td>Volt Premium Bootstrap 5 Dashboard</td>
                                                                        <td>johndoe@gmail.com</td>
                                                                        <td>61.11</td>
                                                                        <td>Aug 31 2020</td>
                                                                        <td><a href="#" class="btn btn-sm btn-primary">View</a></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <th scope="row">17370540</th>
                                                                        <td>Pixel Pro Premium Bootstrap UI Kit</td>
                                                                        <td>jacob.monroe@company.com</td>
                                                                        <td>153.11</td>
                                                                        <td>Aug 28 2020</td>
                                                                        <td><a href="#" class="btn btn-sm btn-primary">View</a></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <th scope="row">17371705</th>
                                                                        <td>Volt Premium Bootstrap 5 Dashboard</td>
                                                                        <td>johndoe@gmail.com</td>
                                                                        <td>61.11</td>
                                                                        <td>Aug 31 2020</td>
                                                                        <td><a href="#" class="btn btn-sm btn-primary">View</a></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <th scope="row">17370540</th>
                                                                        <td>Pixel Pro Premium Bootstrap UI Kit</td>
                                                                        <td>jacob.monroe@company.com</td>
                                                                        <td>153.11</td>
                                                                        <td>Aug 28 2020</td>
                                                                        <td><a href="#" class="btn btn-sm btn-primary">View</a></td>
                                                                    </tr>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                        <a href="#" class="btn btn-block btn-light">View all</a>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-12 col-xl-4">
                                                <div class="card">
                                                    <h5 class="card-header">Traffic last 6 months</h5>
                                                    <div class="card-body">
                                                        <div id="traffic-chart"></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>-->
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

        <script>

            var reservationTrendChart;

            var reservationTrendData = {
                labels: [],
                datasets: [
                    {
                        label: 'Successful Reservations',
                        data: [1, 3], // Provide data for successful reservations for the last n days 
                        fill: false,
                        borderColor: 'blue'
                    },
                    {
                        label: 'All Reservations',
                        data: [1, 3], // Provide data for all reservations for the last n days
                        fill: false,
                        borderColor: 'green'
                    }
                ]
            };

            function sendDatesToServlet() {
                const startDate = document.getElementById("startDate").value;
                const endDate = document.getElementById("endDate").value;

                const dateOfStart = new Date(startDate);
                const dateOfEnd = new Date(endDate);
                if (dateOfStart > dateOfEnd) {
                    alert("End date cannot be in the past of the start date.");
                    return;
                }

                const servletUrl = 'admin?action=changeDate&startDay=' + startDate + '&endDay=' + endDate;

                const xhr = new XMLHttpRequest();

                xhr.open("GET", servletUrl, true);

                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        const responseText = xhr.responseText;
                        const data = responseText.split('\n'); // Split the response into lines

                        let cancelReservationCount, doneReservationCount, submittedReservationCount;
                        let newlyUserCount, newlyUserReservedCount;
                        let totalRevenues;
                        let revenueCategory = {};
                        let averageStarByServiceID = {};
                        let totalAverageStar;

                        data.forEach((line) => {
                            const parts = line.split('=');
                            if (parts.length === 2) {
                                const key = parts[0];
                                const value = parts[1];

                                switch (key) {
                                    case "cancelReservationCount":
                                    {
                                        cancelReservationCount = parseInt(value);
                                        break;
                                    }
                                    case "doneReservationCount":
                                    {
                                        doneReservationCount = parseInt(value);
                                        break;
                                    }
                                    case "submittedReservationCount":
                                    {
                                        submittedReservationCount = parseInt(value);
                                        break;
                                    }
                                    case "newlyUserCount":
                                    {
                                        newlyUserCount = parseInt(value);
                                        break;
                                    }
                                    case "newlyUserReservedCount":
                                    {
                                        newlyUserReservedCount = parseInt(value);
                                        break;
                                    }
                                    case "totalRevenues":
                                    {
                                        totalRevenues = parseFloat(value);
                                        break;
                                    }
                                    case "totalAverageStar":
                                    {
                                        totalAverageStar = parseFloat(value);
                                        break;
                                    }
                                    case "doneReservation":
                                    {
                                        doneReservation = value.split(',').map(Number);
                                        break;
                                    }
                                    case "allReservation":
                                    {
                                        allReservation = value.split(',').map(Number);
                                        break;
                                    }
                                    default:
                                    {
                                        if (key.startsWith("revenueCategory_")) {
                                            const category = key.replace("revenueCategory_", "");
                                            revenueCategory[category] = parseFloat(value);
                                        } else if (key.startsWith("averageStarByServiceID_")) {
                                            const serviceID = key.replace("averageStarByServiceID_", "");
                                            averageStarByServiceID[serviceID] = parseFloat(value);
                                        }
                                    }
                                }
                            }
                        });

                        // Now you can use the assigned variables as needed
//                        console.log(cancelReservationCount);
//                        console.log(doneReservationCount);
//                        console.log(submittedReservationCount);
//                        console.log(newlyUserCount);
//                        console.log(newlyUserReservedCount);
//                        console.log(totalRevenues);
//                        console.log(revenueCategory);
//                        console.log(averageStarByServiceID);
//                        console.log(doneReservation);
//                        console.log(allReservation);
//                        console.log(totalAverageStar);

                        // update label
                        updateChartLabel();

                        // update data
                        updateChartData(doneReservation, allReservation);

                        document.getElementById("submittedReservation").textContent = submittedReservationCount + " reservations submitted";
                        document.getElementById("doneReservation").textContent = doneReservationCount + " reservations done";
                        document.getElementById("cancelReservation").textContent = cancelReservationCount + " reservations cancelled";
                        document.getElementById("totalRevenues").textContent = "$" + totalRevenues;
                        document.getElementById("newlyRegister").textContent = "Newly registered : " + newlyUserCount;
                        document.getElementById("newlyReserved").textContent = "Newly reserved : " + newlyUserReservedCount;
                        document.getElementById("totalRating").textContent = "Newly registered : " + totalAverageStar;

                        for (const category in revenueCategory) {
                            const element = document.getElementById('revenue_' + category);
                            if (element) {
                                element.textContent = category + " : $" + revenueCategory[category];
                            }
                        }

                        for (const serviceID in averageStarByServiceID) {
                            var serviceRatingElement = document.querySelector("span[id='serviceRating_" + serviceID + "']");
                            if (serviceRatingElement) {
                                serviceRatingElement.textContent = serviceID + " : " + averageStarByServiceID[serviceID] + " ";
                            }
                        }

                    }
                };
                xhr.send();
            }

            function updateChartLabel() {
                var startDate = new Date(document.getElementById("startDate").value);
                var endDate = new Date(document.getElementById("endDate").value);

                reservationTrendData.labels = [];
                if (endDate.getMonth() - startDate.getMonth() >= 1 || endDate.getFullYear() > startDate.getFullYear()) {
                    while (startDate <= endDate) {
                        var month = (startDate.getMonth() + 1).toString().padStart(2, "0");
                        var year = startDate.getFullYear();
                        var formattedDate = month + '/' + year;
                        reservationTrendData.labels.push(formattedDate);
                        startDate.setMonth(startDate.getMonth() + 1);
                    }
                } else {
                    while (startDate <= endDate) {
                        var day = startDate.getDate().toString().padStart(2, "0");
                        var month = (startDate.getMonth() + 1).toString().padStart(2, "0");
                        var year = startDate.getFullYear();
                        var formattedDate = day + '/' + month + '/' + year;
                        reservationTrendData.labels.push(formattedDate);
                        startDate.setDate(startDate.getDate() + 1);
                    }
                }
                reservationTrendChart.update();
            }


            function updateChartData(successfulReservationsData, allReservationsData) {
                // Check if the provided arrays have the same length
                if (successfulReservationsData.length !== allReservationsData.length) {
                    console.error("Arrays must have the same length");
                    return;
                }

                // Update the data in the "Successful Reservations" dataset
                reservationTrendData.datasets[0].data = successfulReservationsData;

                // Update the data in the "All Reservations" dataset
                reservationTrendData.datasets[1].data = allReservationsData;

                // Update the chart with the new data
                reservationTrendChart.update();
            }


            function init() {
                // Get the current date
                var currentDate = new Date();

                var endDateInput = document.getElementById("endDate");
                endDateInput.value = currentDate.toISOString().split('T')[0];

                // Calculate the date 7 days ago
                var sevenDaysAgo = new Date(currentDate);
                sevenDaysAgo.setDate(currentDate.getDate() - 7);

                // Set the startDate to 7 days ago
                var startDateInput = document.getElementById("startDate");
                startDateInput.value = sevenDaysAgo.toISOString().split('T')[0];

                for (var i = 0; i < 7; i++) {
                    var dateLabel = sevenDaysAgo.toLocaleDateString('en-GB', {day: '2-digit', month: '2-digit', year: 'numeric'});
                    reservationTrendData.labels.push(dateLabel);
                    sevenDaysAgo.setDate(sevenDaysAgo.getDate() + 1);
                }

                var reservationTrendCtx = document.getElementById('reservationTrendChart').getContext('2d');
                reservationTrendChart = new Chart(reservationTrendCtx, {
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

                updateChartData(<%=doneReservation%>, <%=allReservation%>);
            }
            window.onload = init();
        </script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/js/bootstrap.min.js" integrity="sha384-oesi62hOLfzrys4LxRF63OJCXdXDipiYWBnvTl9Y9/TRlw5xlKIEHpNyvvDShgf/" crossorigin="anonymous"></script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

    </body>
</html>
