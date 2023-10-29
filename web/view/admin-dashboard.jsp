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
                                <a class="nav-link" href="admin?action=send-to-customer-list">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-users"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path><circle cx="9" cy="7" r="4"></circle><path d="M23 21v-2a4 4 0 0 0-3-3.87"></path><path d="M16 3.13a4 4 0 0 1 0 7.75"></path></svg>
                                    <span class="ml-2">Users</span>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="admin?action=send-to-setting-list">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24px" height="24px" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M9.594 3.94c.09-.542.56-.94 1.11-.94h2.593c.55 0 1.02.398 1.11.94l.213 1.281c.063.374.313.686.645.87.074.04.147.083.22.127.324.196.72.257 1.075.124l1.217-.456a1.125 1.125 0 011.37.49l1.296 2.247a1.125 1.125 0 01-.26 1.431l-1.003.827c-.293.24-.438.613-.431.992a6.759 6.759 0 010 .255c-.007.378.138.75.43.99l1.005.828c.424.35.534.954.26 1.43l-1.298 2.247a1.125 1.125 0 01-1.369.491l-1.217-.456c-.355-.133-.75-.072-1.076.124a6.57 6.57 0 01-.22.128c-.331.183-.581.495-.644.869l-.213 1.28c-.09.543-.56.941-1.11.941h-2.594c-.55 0-1.02-.398-1.11-.94l-.213-1.281c-.062-.374-.312-.686-.644-.87a6.52 6.52 0 01-.22-.127c-.325-.196-.72-.257-1.076-.124l-1.217.456a1.125 1.125 0 01-1.369-.49l-1.297-2.247a1.125 1.125 0 01.26-1.431l1.004-.827c.292-.24.437-.613.43-.992a6.932 6.932 0 010-.255c.007-.378-.138-.75-.43-.99l-1.004-.828a1.125 1.125 0 01-.26-1.43l1.297-2.247a1.125 1.125 0 011.37-.491l1.216.456c.356.133.751.072 1.076-.124.072-.044.146-.087.22-.128.332-.183.582-.495.644-.869l.214-1.281z" />
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                                    </svg>
                                    <span class="ml-2">Settings</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </nav>
                <main class="col-md-9 ml-sm-auto col-lg-10 px-md-4 py-4">
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
                                    <h5 id="totalRevenues" class="card-title">Total revenues : $<%=totalRevenues%></h5>
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
                        data: <%=doneReservation%>, // Provide data for successful reservations for the last n days 
                        fill: false,
                        borderColor: 'blue'
                    },
                    {
                        label: 'All Reservations',
                        data: <%=allReservation%>, // Provide data for all reservations for the last n days
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

                // Validate the start date
                if (isNaN(dateOfStart.getTime())) {
                    alert("Start date invalid.");
                    return;
                }

                // Validate the end date
                if (isNaN(dateOfEnd.getTime())) {
                    alert("End date invalid.");
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

                        // update label
                        updateChartLabel();

                        // update data
                        updateChartData(doneReservation, allReservation);

                        document.getElementById("submittedReservation").textContent = submittedReservationCount + " reservations submitted";
                        document.getElementById("doneReservation").textContent = doneReservationCount + " reservations done";
                        document.getElementById("cancelReservation").textContent = cancelReservationCount + " reservations cancelled";
                        document.getElementById("totalRevenues").textContent = "Total revenues : $" + totalRevenues;
                        document.getElementById("newlyRegister").textContent = "Newly registered : " + newlyUserCount;
                        document.getElementById("newlyReserved").textContent = "Newly reserved : " + newlyUserReservedCount;
                        document.getElementById("totalRating").textContent = "Newly registered : " + totalAverageStar;

                        for (const category in revenueCategory) {
                            const element = document.getElementById('revenue_' + category);
                            if (element) {
                                element.textContent = category + " : $" + revenueCategory[category] + " ";
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
                                beginAtZero: true,
                                ticks: {
                                    stepSize: 1
                                }
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
