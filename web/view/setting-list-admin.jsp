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

            #pagination-container{
                display: flex;
                justify-content: center;
                align-items: center;
                list-style: none;
            }
            #pagination-container li{
                width: 25px;
                height: 25px;
                border-radius: 50%;
                margin-left: 0.5rem;
                margin-right: 0.5rem;
                text-align: center;
                display: flex;
                justify-content: center;
                align-items: center;
            }

            #pagination-container a{
                text-decoration: none;
                font-weight: 500;
            }

            #pagination-container .active{
                background-color: #1977cc !important;
            }

            #pagination-container .inactive{
                background-color: #fff;
            }

            #pagination-container .active span {
                color: #fff;
                font-weight: 500;
            }
            #pagination-container .inactive a{
                color: #8f8f8f;
            }
            #pagination-container button{
                border-radius: 50%;
                border: 0px;
                background-color: white
            }

        </style>
        <link
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css"
            rel="stylesheet"
            />
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
            rel="stylesheet"
            />
    </head>
    <body>
        <%@ page import="jakarta.servlet.http.HttpSession" %>
        <%@page import = "model.*" %>
        <%@page import = "Database.*" %>
        <%@page import = "java.util.*" %>
        <%
        SettingDAO settingDAO = new SettingDAO();
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
                        Hello,<%=admin.getStaffName()%>
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
                                <a class="nav-link" aria-current="page" href="admin">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-home"><path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path><polyline points="9 22 9 12 15 12 15 22"></polyline></svg>
                                    <span class="ml-2">Home</span>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="admin?action=send-to-customer-list">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-users"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path><circle cx="9" cy="7" r="4"></circle><path d="M23 21v-2a4 4 0 0 0-3-3.87"></path><path d="M16 3.13a4 4 0 0 1 0 7.75"></path></svg>
                                    <span class="ml-2">Users</span>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link active" href="#">
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
                    <div class="mb-4 py-3 border-bottom d-flex justify-content-between align-items-center">
                        <h5>Settings Manage</h5>
                        <a href="setting?event=add" class="ms-text-primary text-decoration-none"><i class="fas fa-pencil-alt ms-text-primary"></i> Add New Setting</a>
                    </div>
                    <div class="col-md-12 row justify-content-between mb-3">
                        <div class="col-md-3">
                            <input type="text" name="value" class="form-control" placeholder="Search By Value" id="valuSetting">
                        </div>
                        <div class="col-md-3">
                            <select class="form-select" id="filterSetting">
                                <option selected value="">Filtered</option>
                                <option value="type">Slider</option>
                                
                            </select>

                            <select class="form-select mt-3" id="sort">
                                <option selected value="">Sort By</option>
                                <option value="ID">Sort By ID</option>
                                <option value="Type">Sort By Type</option>
                                <option value="Value">Sort By Value</option>                            
                                <option value="Status">Sort By status</option>
                            </select>

                        </div>
                    </div>
                    <div class="col-md-12">
                        <table class="table table-striped table-hover">
                            <thead class="text-light" style="background: #1977cc;">
                                <tr>
                                    <th scope="col">ID</th>
                                    <th scope="col">Type</th>
                                    <th scope="col">Name</th>
                                    <th scope="col">Value</th>
                                    <th scope="col">Description</th>                                    
                                    <th scope="col">Status</th>
                                    <th scope="col">Detail</th>
                                </tr>
                            </thead>
                            <tbody id="service-list">
                                <%List<Setting> settings = settingDAO.getSettingList(1);
                                for (Setting setting : settings) {%>
                                <tr>
                                    <td><%=setting.getSettingID() %></td>
                                    <td><%=setting.getType() %></td>
                                    <td><%=setting.getSettingName() %></td>
                                    <td><%=setting.getValue().substring(0, 15)+"..." %></td>
                                    <td><%=setting.getDescription().substring(0, 15)+"..." %></td>
                                    
                                    <td>
                                        <div class="dropdown">
                                            <button style="border: 0px; padding: 0px" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                                                <span class="badge bg-primary"  id="statusBadge-<%=setting.getSettingID() %>"><%=setting.getStatus() %></span>
                                            </button>
                                            <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                                                <li><a class="dropdown-item status-change" href="#" onclick="changestatus(this, <%=setting.getSettingID() %>)">Active</a></li>
                                                <li><a class="dropdown-item status-change" href="#" onclick="changestatus(this, <%=setting.getSettingID() %>)">Inactive</a></li>             
                                            </ul>
                                        </div>
                                    </td>
                                    <td><a href="setting?event=detail&settingID=<%=setting.getSettingID() %>"><img src="resources/img/icon/detail.png" alt="alt" width="25px"/></a></td>
                                </tr>
                                <%} %>
                            </tbody>
                        </table>

                        <div class="d-flex justify-content-center mb-5" id="pagination-container">
                            <button style="color: black" class="pagination-btn btn btn-primary rounded-circle ms-2 active ml-2" data-page="1">1</button>
                            <%for (int i = 2; i <=(settingDAO.getTotalPageSetting()+9)/10; i++) {%>
                            <button style="color: black" class="pagination-btn btn btn-primary rounded-circle ms-2 inactive ml-2" data-page="<%=i%>"><%=i%></button>
                            <%}%>
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
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/js/bootstrap.min.js" integrity="sha384-oesi62hOLfzrys4LxRF63OJCXdXDipiYWBnvTl9Y9/TRlw5xlKIEHpNyvvDShgf/" crossorigin="anonymous"></script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <script>
                                                                var paginationButtons = document.querySelectorAll('.pagination-btn');
                                                                paginationButtons.forEach(function (button) {
                                                                    button.addEventListener('click', function (event) {
                                                                        event.preventDefault();

                                                                        if (!this.classList.contains('active')) {
                                                                            document.querySelectorAll('.pagination-btn').forEach(function (paginationBtn) {
                                                                                if (paginationBtn.classList.contains('active')) {
                                                                                    paginationBtn.classList.remove('active');
                                                                                }
                                                                            });
                                                                            this.classList.add('active');

                                                                            var page = this.dataset.page;

                                                                            loadPageServices(page);
                                                                        }
                                                                    });
                                                                });
                                                                function loadPageServices(page) {

                                                                    var xhr = new XMLHttpRequest();
                                                                    xhr.open('POST', 'setting?event=paging&page=' + page);

                                                                    xhr.onload = function () {
                                                                        if (xhr.status === 200) {

                                                                            document.querySelector('#service-list').innerHTML = xhr.responseText;
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

                                                                    // G?i yêu c?u Ajax ??n servlet
                                                                    var xhr = new XMLHttpRequest();
                                                                    xhr.open("POST", "setting?event=updatestatus&status=" + text + "&settingID=" + uid, true);

                                                                    xhr.onload = function () {

                                                                    };

                                                                    xhr.onerror = function () {

                                                                    };

                                                                    xhr.send();
                                                                }
        </script>
        <script src="./resources/js/setting-list-admin.js"></script>
    </body>
</html>
