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
        UserDAO userDAO = new UserDAO();
        Staff admin = (Staff) request.getAttribute("admin");
        
        int maximumDayDiff = 7;
        String validate = request.getAttribute("validate")+"";
        
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
                                <a class="nav-link" aria-current="page" href="admin">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-home"><path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path><polyline points="9 22 9 12 15 12 15 22"></polyline></svg>
                                    <span class="ml-2">Home</span>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link active" href="#">
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
                    <div class="mb-4 py-3 border-bottom d-flex justify-content-between align-items-center">
                        <h5>Add User</h5>
                    </div>
                    <%if(!validate.equals("null")&&validate.equals("false")){%>
                    <div class="alert alert-danger" role="alert">
                        Add User fail!
                    </div>
                    <%}
                        String firstName = (request.getAttribute("firstName")+"").equals("null") ? "" : (request.getParameter("firstName") + "");
                        String lastName = (request.getAttribute("lastName")+"").equals("null") ? "" : (request.getParameter("lastName") + "");
                        String email = (request.getAttribute("email")+"").equals("null") ? "" : (request.getParameter("email") + "");
                        String status = request.getAttribute("status")+"";
                        String gender = request.getAttribute("gender")+"";
                        String role = request.getAttribute("role")+"";
                        String mobile = (request.getAttribute("mobile")+"").equals("null") ? "" : (request.getParameter("mobile") + "");
                        String address = (request.getAttribute("address")+"").equals("null") ? "" : (request.getParameter("address") + "");
                        String firstNameErr = request.getAttribute("firstNameErr")+"";
                        String lastNameErr = request.getAttribute("lastNameErr")+"";
                        String emailErr = request.getAttribute("emailErr")+"";
                        String statusErr = request.getAttribute("statusErr")+"";
                        String genderErr = request.getAttribute("genderErr")+"";
                        String roleErr = request.getAttribute("roleeErr")+"";
                        String mobileErr = request.getAttribute("mobileErr")+"";
                    %>

                    <form action="user?action=add-user-byadmin" method="POST" enctype="multipart/form-data" class="d-flex flex-column align-items-center justify-content-between">
                        <div class="col-md-12 row justify-content-center">
                            <div class="col-md-4 px-2">
                                <div class="d-flex justify-content-center">
                                    <img src="resources/img/avatar.png" alt="img" class="object-contain" width="200px" />
                                </div>
                                <div class="form-group mt-3">
                                    <label for="serviceImage">Profile Image:</label>
                                    <input type="file" class="form-control-file" name="avartar">
                                </div>
                                <h5 class="d-flex justify-content-center mt-3">- Or -</h5>
                                <div class="form-group mt-3">
                                    <label for="serviceURL">Profile Image:</label>
                                    <input type="text" class="form-control" id="serviceURL" name="avartarURL">
                                </div>
                            </div>
                            <div class="col-md-6 px-5">
                                <div class="d-flex">
                                    <div class="form-group pe-4">
                                        <label for="full-name" class="detail-info">First Name</label>
                                        <input type="text" class="form-control"  name="firstname" value="<%=firstName%>">
                                        <%if(!firstNameErr.equals("null")){%>    <p class="text-danger"><%=firstNameErr%></p><%}%>
                                    </div>
                                    <div class="form-group px-4">
                                        <label for="full-name" class="detail-info">Last Name</label>
                                        <input type="text" class="form-control" name="lastname" value="<%=lastName%>">
                                        <%if(!lastNameErr.equals("null")){%>    <p class="text-danger"><%=lastNameErr%></p><%}%>
                                    </div>
                                </div>
                                <div class="form-group mt-3">
                                    <label for="email" class="detail-info">Email</label>
                                    <input type="email" class="form-control w-75" id="email" name="email" value="<%=email%>">
                                    <%if(!emailErr.equals("null")){%>    <p class="text-danger"><%=emailErr%></p><%}%>
                                </div>
                                <div class="d-flex">
                                    <div class="form-group mt-3 col-md-5 ps-5">
                                        <label for="status" class="detail-info">Status</label>
                                        <select class="form-select text-primary mt-3 mb-4 w-50" name="status">
                                            <option value="active" <%= status.equals("active") || !status.equals("inactive") ? "selected" : "" %>>Active</option>
                                            <option value="inactive" <%= status.equals("inactive") ? "selected" : "" %>>Inactive</option>
                                        </select>
                                        <% if (!statusErr.equals("null")) { %>
                                        <p class="text-danger"><%= statusErr %></p>
                                        <% } %>
                                    </div>
                                    <div class="form-group mt-3 col-md-6 px-3">
                                        <label for="gender" class="detail-info">Gender</label>
                                        <select class="form-select text-primary mt-3 mb-4 w-50" name="gender">
                                            <option value="male" <%= gender.equals("male") ? "selected" : "" %>>Male</option>
                                            <option value="female" <%= gender.equals("female") ? "selected" : "" %>>Female</option>
                                            <option value="other" <%= gender.equals("other") ? "selected" : "" %>>Other</option>
                                        </select>
                                        <% if (!genderErr.equals("null")) { %>
                                        <p class="text-danger"><%= genderErr %></p>
                                        <% } %>
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <div class="d-flex">
                                        <p class="fit-content-width ms-3">User is:</p>
                                        <select class="form-select text-primary w-50 mx-3" name="role">
                                            <option value="user" <%= role.equals("user") || (!role.equals("doctor") && !role.equals("nurse") && !role.equals("manager")) ? "selected" : "" %>>User</option>
                                            <option value="doctor" <%= role.equals("doctor") ? "selected" : "" %>>Doctor</option>
                                            <option value="nurse" <%= role.equals("nurse") ? "selected" : "" %>>Nurse</option>
                                            <option value="manager" <%= role.equals("manager") ? "selected" : "" %>>Manager</option>
                                        </select>
                                    </div>
                                    <% if (!roleErr.equals("null")) { %>
                                    <p class="text-danger"><%= roleErr %></p>
                                    <% } %>
                                </div>

                                <div class="form-group mt-3">
                                    <label for="mobile" class="detail-info">Mobile</label>
                                    <input type="tel" class="form-control w-75" id="mobile" name="mobile" value="<%=mobile%>">
                                    <%if(!mobileErr.equals("null")){%>    <p class="text-danger"><%=mobileErr%></p><%}%>
                                </div>
                                <div class="form-group mt-3">
                                    <label for="address" class="detail-info">Address</label>
                                    <input type="text" class="form-control w-75" id="address" name="address" value="<%=address%>">
                                </div>

                            </div>

                        </div> 
                        <input class="btn btn-primary mt-5 w-25" type="submit" value="Add User" />
                    </form>


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
        <script src="./resources/js/user-list-admin.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/js/bootstrap.min.js" integrity="sha384-oesi62hOLfzrys4LxRF63OJCXdXDipiYWBnvTl9Y9/TRlw5xlKIEHpNyvvDShgf/" crossorigin="anonymous"></script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

    </body>
</html>
