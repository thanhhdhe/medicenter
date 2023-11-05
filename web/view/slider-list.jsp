<%-- 
    Document   : slider-list
    Created on : 26-Oct-2023, 10:18:47
    Author     : Admin
--%>
<%@page import = "model.*" %>
<%@page import = "Database.*" %>
<%@page import = "java.util.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Slider List</title>
        <!-- Include Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css"
              rel="stylesheet"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Include Bootstrap JavaScript and jQuery -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <link rel="stylesheet" href="./resources/css/staff-dashboard.css">
        <style>
            .table-light th{
                white-space: nowrap
            }
        </style>
    </head>
    <body>
        <%
String email = (String) session.getAttribute("email");
StaffDAO staffDAO = new StaffDAO();
Staff curStaff = staffDAO.getStaffByStaffEmail(email);
UserDAO userDAO = new UserDAO();
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
                        <a href="staff?event=send-to-reservations-list" class="nav-item nav-link"
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
                    <div class="navbar-nav w-100 text-light">
                        <a href="user?action=search" class="nav-item nav-link"
                           ><i class="bi bi-people-fill"></i>User</a
                        >
                    </div>
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
                        <a href="user?action=search" class="nav-item nav-link active"
                           ><i class="bi bi-image-fill"></i>Slider</a
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
                <div class="container mt-5">
                    <% 
        String message = (String) request.getSession().getAttribute("message");
        String alertClass = "alert-success";

        if (message != null) { 
            if (message.contains("error")) {
                alertClass = "alert-danger";
            }
                    %>
                    <div class="alert <%= alertClass %> alert-dismissible fade show" role="alert">
                        <strong><%= message %></strong>
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                    <%
                        request.getSession().removeAttribute("message");
                    }
                    %>

                    <div class="d-flex justify-content-between mb-3">
                        <div>
                            <input type="text" class="form-control" id="searchInput" placeholder="Search">
                        </div>
                        <div class="d-flex">

                            <label for="statusFilter"><span class="align-middle fw-medium mx-2 text-nowrap fs-5">Filter Status</span></label>
                            <select class="form-select" id="statusFilter" name="statusFilter">
                                <option value="">All</option>
                                <option value="Active">Active</option>
                                <option value="Inactive">Inactive</option>
                            </select>
                            <button type="button" class="btn btn-primary text-nowrap mx-3 fw-bold" data-bs-toggle="modal" data-bs-target="#addSlideModal">
                                <i class="me-2 bi bi-plus-circle-fill"></i>Add Slide
                            </button>
                        </div>
                    </div>

                    <div class="table-responsive">
                        <table class="table table-striped align-middle caption-top">
                            <caption class="fw-medium fs-5">List of Slide</caption>
                            <thead class="table-light">
                                <tr>
                                    <th scope="col" style="width: 10%;">ID
                                        <i class="bi bi-filter sort-button" data-column="ID"></i></th>
                                    <th scope="col" style="width: 20%;">Title
                                        <i class="bi bi-filter sort-button" data-column="Title"></i></th>
                                    <th scope="col" style="width: 20%;">Brief
                                        <i class="bi bi-filter sort-button" data-column="Brief"></i></th>
                                    <th scope="col" style="width: 15%;">Image</th>
                                    <th scope="col" style="width: 15%;">Backlink
                                        <i class="bi bi-filter sort-button" data-column="Backlink"></i></th>
                                    <th scope="col" style="width: 10%;">Status
                                        <i class="bi bi-filter sort-button" data-column="Status"></i></th>
                                    <th scope="col" style="width: 10%;">Actions</th>
                                </tr>
                            </thead>
                            <tbody id="sliderList" class="table-group-divider">

                            </tbody>
                        </table>
                    </div>
                    <!-- Modal "Add Slide" -->
                    <form action="slider?action=add" id="frmAddSlide" method="post" enctype="multipart/form-data">
                        <div class="modal fade" id="addSlideModal" tabindex="-1" aria-labelledby="addSlideModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="addSlideModalLabel">Add Slide</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <form id="addSlideForm">
                                            <div class="mb-3">
                                                <label for="title" class="form-label">Title:</label>
                                                <input type="text" class="form-control" id="title" name="title" placeholder="Enter title" required>
                                            </div>
                                            <div class="mb-3">
                                                <label for="brief" class="form-label">Brief:</label>
                                                <input type="text" class="form-control" id="brief" name="brief" placeholder="Enter brief" required>
                                            </div>
                                            <div class="mb-3">
                                                <label for="image" class="form-label">Upload Image:</label>
                                                <input type="file" class="form-control" id="imageUpload" name="image" accept="image/*" required>
                                            </div>
                                            <img id="imagePreview" src="#" alt="Preview" style="max-width: 100%; max-height: 200px; display: none;">
                                            <div class="mb-3">
                                                <label for="backlink" class="form-label">Backlink:</label>
                                                <input type="text" class="form-control" id="backlink" name="backlink" placeholder="Enter backlink" required>
                                            </div>
                                            <div class="mb-3">
                                                <label for="status" class="form-label">Status:</label>
                                                <select class="form-select" id="status" name="status">
                                                    <option value="active">Active</option>
                                                    <option value="inactive">Inactive</option>
                                                </select>
                                            </div>

                                    </div>

                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                        <button type="submit" class="btn btn-primary">Add</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </diV>
            </div>

        </div>
        <jsp:include page="layout/footer.jsp" />    
    </body>

    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script>
        const imageUpload = document.getElementById('imageUpload');
        const imagePreview = document.getElementById('imagePreview');

        imageUpload.addEventListener('change', function () {
            const selectedImage = imageUpload.files[0];

            if (selectedImage) {
                const reader = new FileReader();
                reader.onload = function (e) {
                    // Hiển thị trước ảnh
                    imagePreview.src = e.target.result;
                    imagePreview.style.display = 'block'; // Hiển thị ảnh khi đã có ảnh được chọn
                };
                reader.readAsDataURL(selectedImage);
            }
        });

        imageUpload.addEventListener('change', function () {
            const selectedImage = imageUpload.files[0];
            const preview = document.getElementById('imagePreview');

            if (selectedImage) {
                const reader = new FileReader();
                reader.onload = function (e) {
                    // Hiển thị trước ảnh
                    preview.src = e.target.result;
                };
                reader.readAsDataURL(selectedImage);
            }
        });
        $('.pagination a').on('click', function (event) {
            event.preventDefault();
            const page = $(this).text(); // Lấy số trang từ nút phân trang

            // Thực hiện cập nhật trang dựa trên page
            renderSliderList(page);
        });
        function renderSliderList(page) {
            const searchQuery = $('#searchInput').val().toLowerCase();
            const statusFilter = $('#statusFilter').val();
            const sortByColumn = $('.sort-button.active').attr('data-column');
            const sortOrder = $('.sort-button.active').hasClass('asc') ? 'asc' : 'desc';

            const requestData = {
                searchQuery: searchQuery,
                statusFilter: statusFilter,
                sortBy: sortByColumn,
                sortOrder: sortOrder,
                page: page
            };
            console.log(requestData);

            $.ajax({
                type: 'GET',
                url: 'slider?action=search',
                data: requestData,

                success: function (sliders) {
                    var sliderList = document.getElementById("sliderList");
                    sliderList.innerHTML = sliders;
                },
                error: function (error) {
                    console.error('Error fetching data from the servlet');
                }
            });
        }
        function renderSliderList() {
            const searchQuery = $('#searchInput').val().toLowerCase();
            const statusFilter = $('#statusFilter').val();
            const sortByColumn = $('.sort-button.active').attr('data-column');
            const sortOrder = $('.sort-button.active').hasClass('asc') ? 'asc' : 'desc';

            const requestData = {
                searchQuery: searchQuery,
                statusFilter: statusFilter,
                sortBy: sortByColumn,
                sortOrder: sortOrder,
            };
            console.log(requestData);

            $.ajax({
                type: 'GET',
                url: 'slider?action=search',
                data: requestData,

                success: function (sliders) {
                    var sliderList = document.getElementById("sliderList");
                    sliderList.innerHTML = sliders;
                },
                error: function (error) {
                    console.error('Error fetching data from the servlet');
                }
            });
        }
        $('.sort-button').on('click', function () {
            const $this = $(this);
            const isActive = $this.hasClass('active');

            if (!isActive) {
                // Set the initial state to ascending (asc)
                $this.removeClass('desc').addClass('asc');
                $this.removeClass('bi-filter').addClass('bi-sort-up');
            } else {
                // Toggle between ascending (asc) and descending (desc) when clicked again
                $this.toggleClass('asc desc');
                $this.toggleClass('bi-sort-up bi-sort-down bi-filter');
            }

            // Remove 'active' status from all other buttons
            $('.sort-button').not($this).removeClass('active');
            $('.sort-button').not($this).removeClass('bi-sort-up bi-sort-down');

            $this.addClass('active');
            renderSliderList();
        });

        // Event listeners for input changes
        $('#searchInput').on('input', renderSliderList);
        $('#statusFilter').on('change', renderSliderList);

        // Initial render
        renderSliderList();


        function changeStatus(sliderId, button) {
            $.ajax({
                type: "POST",
                url: "slider?action=changeStatus",
                data: {sliderID: sliderId},
                success: function (data) {
                    if ($(button).hasClass("btn-success")) {
                        $(button).removeClass("btn-success").addClass("btn-danger");
                        $(button).find(".bi").removeClass("bi-eye-fill").addClass("bi-eye-slash-fill");
                    } else {
                        $(button).removeClass("btn-danger").addClass("btn-success");
                        $(button).find(".bi").removeClass("bi-eye-slash-fill").addClass("bi-eye-fill");
                    }
                },
                error: function (error) {
                    console.error('Error updating status');
                }
            });
        }
        function deleteSlide(sliderId, buttonElement) {
            var row = $(buttonElement).closest('tr');
            Swal.fire({
                title: 'Are you sure?',
                text: "You won't be able to revert this!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, delete it!'
            }).then((result) => {
                if (result.isConfirmed) {
                    $.ajax({
                        url: 'slider?action=delete',
                        type: 'POST',
                        data: {sliderID: sliderId},
                        success: function (data) {
                            row.fadeOut(300, function () {
                                $(this).remove();
                            });
                        },
                        error: function () {
                            alert('Error!');
                        }
                    });
                }
            });
        }
        document.querySelector('.sidebar-toggler').addEventListener('click', function () {
            var sidebar = document.querySelector('.sidebar');
            var content = document.querySelector('.content');

            sidebar.classList.toggle('open');
            content.classList.toggle('open');

            return false;
        });
    </script>
</html>
