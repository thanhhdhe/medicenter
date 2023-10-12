<!DOCTYPE html>
<html lang="en">
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

        <script src="https://cdn.jsdelivr.net/npm/chart.js@3.8.0/dist/chart.min.js"></script>
        <style>
            /* Custom CSS for larger circular avatar and dropdown spacing */
            .admin-avatar {
                border-radius: 50%;
                width: 48px;
                height: 48px;
                object-fit: cover;
            }

        </style>
    </head>
    <body>
        <%@ page import="java.util.HashMap,java.util.Map,jakarta.servlet.http.HttpSession" %>
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
        
        Staff admin = (Staff) request.getAttribute("admin");
        
        int maximumDayDiff = 7;
        %>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
                <a class="navbar-brand" href="admin">Admin Dashboard</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link" href="admin">Dashboard</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Settings</a>
                        </li>
                    </ul>
                </div>
                <div class="navbar-nav">
                    <div class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <img src="<%=admin.getProfileImage()%>" alt="Admin Avatar" class="admin-avatar">
                        </a>
                        <div style="right: 0;left: auto;width: 50px;" class="dropdown-menu" aria-labelledby="userDropdown">
                            <a class="dropdown-item" href="#">Profile</a>
                            <a class="dropdown-item" href="#">Settings</a>
                            <div class="dropdown-divider"></div>  
                            <a class="dropdown-item" href="admin?action=logout">Logout</a>
                        </div>
                    </div>
                </div>
            </div>
        </nav>
        <div class="container">
            <!-- Display statistics -->
            <div class="row">
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Reservations</h5>
                            <p>Success: <%= doneReservationCount %></p>
                            <p>Cancelled: <%= cancelReservationCount %></p>
                            <p>Submitted: <%= submittedReservationCount %></p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Customers</h5>
                            <p>Newly Registered: <%= newlyUserCount %></p>
                            <p>Newly Reserved: <%= newlyUserReservedCount %></p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Revenues</h5>
                            <p>Total: $<%= totalRevenues %></p>
                            <p>By Service Categories:</p>
                            <ul>
                                <% for (Map.Entry<String, Float> entry : revenueCategory.entrySet()) { %>
                                <li><%= entry.getKey() %>: $<%= entry.getValue() %></li>
                                    <% } %>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Display feedbacks -->
            <div class="row mt-4">
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Feedbacks</h5>
                            <p>Average Star (Total): <%= totalAverageStar %></p>
                            <p>Average Star by Service:</p>
                            <ul>
                                <% for (Map.Entry<String, Float> entry : averageStarByServiceID.entrySet()) { %>
                                <li><%= entry.getKey() %>: <%= entry.getValue() %> stars</li>
                                    <% } %>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Trend of reservation counts chart -->
            <div class="row mt-4">
                <div class="col-md-12">
                    <canvas id="reservationTrendChart"></canvas>
                </div>
            </div>
        </div>

        <!-- JavaScript to create charts -->
        <script>
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

        <script src="https://cdn.jsdelivr.net/npm/popper.js@2.9.3/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

    </body>
</html>
