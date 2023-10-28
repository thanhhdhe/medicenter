<%-- 
    Document   : slider-list
    Created on : 26-Oct-2023, 10:18:47
    Author     : Admin
--%>

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
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Include Bootstrap JavaScript and jQuery -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <style>
            .table-light th{
                white-space: nowrap
            }
        </style>
    </head>
    <body>
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
                                </form>
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                <button type="submit" class="btn btn-primary">Add</button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
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
    </script>
</html>
