<%-- 
    Document   : choose-children
    Created on : 08-Oct-2023, 00:42:42
    Author     : Admin
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Choose children profile</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
        <style>
            body {
                background-color: #e8f2f7;
            }

            .detail-info {
                word-wrap: break-word;
                flex: 1 1;
                min-width: 0;
                padding-left: 5px;
                color: var(--primary-body-text, #003553);
                font-size: 16px;
                font-style: normal;
                font-weight: 500;
                line-height: normal;
            }

            .title-info {
                color: #b1b1b1;
                font-size: 16px;
                font-weight: 300;
            }

            .choose-user {
                min-width: 448px
            }

            .choose-user h1 {
                background: linear-gradient(83.63deg, #00b5f1 33.34%, #00e0ff 113.91%);
                background-clip: text;
                -webkit-background-clip: text;
                color: transparent;
                text-align: center;
                font-size: 25px;
                font-style: normal;
                line-height: normal;
                font-weight: bold;
            }

            p {
                margin-bottom: 5px;
            }

            .card:hover {
                border: 1px solid #3699ff;
                transform: translateZ(-3px);
                cursor: pointer;
            }

            .border-m {
                border: none;
                border-radius: 8px;
            }

            .btn {
                display: flex;
                align-items: center;
                justify-content: center;
            }

            .btn-danger {
                background-color: rgba(253, 57, 122, .1);
                color: #fd397a;
            }

            .btn-none {
                border-color: transparent;
                background: transparent;
            }

            .card-footer {
                background: none;
            }

            .btn-primary {
                background-color: rgba(54, 153, 255, .1);
                color: #3699ff;
            }

            .btn-continue {
                border: none;
                background: linear-gradient(83.63deg, #00b5f1 33.34%, #00e0ff 113.91%) !important;
                border-radius: 8px;
            }
            .image-create:hover {
                cursor: pointer;
            }
        </style>
    </head>

    <body>
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-6 choose-user mt-5">
                    <h1 class="text-center">Choose Your Children Profile</h1>
                    <%-- Kiểm tra xem có thông báo không và hiển thị nếu có --%>
                    <% String message = (String) session.getAttribute("message"); %>
                    <% if (message != null) { %>
                    <div class="alert alert-success">
                        <%= message %>
                    </div>
                    <% session.removeAttribute("message"); 
                        }%>



                    <c:forEach items="${requestScope.child}" var="c">
                        <div class="card mt-4 border-m shadow child-card">
                            <div class="card-body" onclick="showFullInfo(${c.childID})">
                                <div id="basicInfo${c.childID}">
                                    <div class="row">
                                        <div class="info-desc col-md-9">
                                            <div class="row">
                                                <div class="col-md-5">
                                                    <p class="title-info"><strong><i class="fas fa-user"></i> Fullname: </strong></p>
                                                </div>
                                                <div class="col-md-7 detail-info">
                                                    <p>${c.childName}</p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-5">
                                                    <p class="title-info"><i class="fas fa-calendar-alt"></i> <strong>Date of birth:</strong>
                                                    </p>
                                                </div>
                                                <div class="col-md-7 detail-info">
                                                    <p>${c.birthday}</p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-5">
                                                    <p class="title-info"><i class="fas fa-phone"></i> <strong>Parent's phone:</strong></p>
                                                </div>
                                                <div class="col-md-7 detail-info">
                                                    <p>${c.user.phoneNumber}</p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="row justify-content-center">
                                                <img src="${c.image}" style="height: 85px;object-fit: cover;width: 85px;"
                                                     alt="Image profile" style="max-height: 85px;"
                                                     class="img-thumbnail rounded-circle" />
                                            </div>
                                        </div>
                                    </div>

                                </div>

                                <div id="fullInfo${c.childID}" style="display: none;">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <p class="title-info"><i class="fas fa-venus-mars"></i> <strong>Gender:</strong>
                                            </p>
                                        </div>
                                        <div class="col-md-8 detail-info">
                                            <p>${c.gender}</p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-4">
                                            <p class="title-info"><i class="fas fa-globe"></i> <strong>Parent's email:</strong></p>
                                        </div>
                                        <div class="col-md-8 detail-info">
                                            <p>${c.user.email}</p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-4">
                                            <p class="title-info"><i class="fas fa-map-marker-alt"></i> <strong>Address: </strong></p>
                                        </div>
                                        <div class="col-md-8 detail-info">
                                            <p>${c.user.address}</p>
                                        </div>
                                    </div>
                                    <div class="card-footer mt-3">
                                        <div class="row mt-3">
                                            <div class="col-md-9">
                                                <div class="row">
                                                    <div class="col-md-4">
                                                        <button class="btn btn-danger btn-block border-m trash" value="${c.childID}">
                                                            <span class="d-flex align-items-center">
                                                                <i class="fas fa-arrow-circle-left mr-2"></i> Delete
                                                            </span>
                                                        </button>
                                                    </div>
                                                    <div class="col-md-4">
                                                        <button class="btn btn-primary btn-block border-m"><span
                                                                class="d-flex align-items-center">
                                                                <i class="fas fa-edit mr-2"></i> Edit
                                                            </span></button>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="">
                                                <button class="btn-continue btn btn-block text-white">
                                                    <span class="d-flex align-items-center">
                                                        <i class="fas fa-arrow-circle-right mr-2"></i> Continue
                                                    </span>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="row justify-content-center">
                <div class="footer-nav mt-5 col-md-6">
                    <div class="row">
                        <div class="col-md-3">
                            <button type="button" class="btn btn-none btn-block border-m">
                                <span class="d-flex align-items-center">
                                    <i class="fas fa-arrow-circle-left mr-2"></i> Back
                                </span>
                            </button>
                        </div>
                        <div class="col-md-6"></div>
                        <div class="">
                            <button type="button" class="btn btn-block border-m btn-continue text-white" data-toggle="modal"
                                    data-target="#addPatientModal">
                                <span class="d-flex align-items-center">
                                    <i class="fas fa-plus-circle mr-2"></i> Add profile
                                </span>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Modal -->
            <div class="modal fade" id="addPatientModal" tabindex="-1" role="dialog" aria-labelledby="addPatientModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <form action="user?action=add-child" method="POST" enctype="multipart/form-data">
                        <div class="modal-content border-m">
                            <div class="modal-header">
                                <h5 class="modal-title" id="addPatientModalLabel">Add children profile</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <!-- Form để nhập thông tin của bệnh nhân -->
                                <form class="container">
                                    <div class="form-group text-center">
                                        <input type="file"  style=" display: none; "name="images" id="file" class="inputfile" onchange="readURL(this)" accept="image/*"/>
                                        <label for="file">
                                            <img id="img-preview"  style="height: 100px;width: 100px;" 
                                                 class="rounded-circle mx-auto d-block image-create" 
                                                 name="default-image"
                                                 src="https://cdn-icons-png.flaticon.com/512/3177/3177440.png"  />
                                            <i class="bi bi-pencil-square image-create"></i>
                                        </label>
                                    </div>
                                    <div class="form-group">
                                        <label for="patientName">Fullname:</label>
                                        <input required type="text" class="form-control" id="patientName" name="fullname" placeholder="Enter fullname of your child">
                                    </div>
                                    <div class="row">
                                        <div class="form-group mx-3">
                                            <label for="patientDOB">Date of birth:</label>
                                            <div class="row">
                                                <div class="col-md-4">
                                                    <select required class="form-control" id="patientDOBYear" name="year">
                                                        <option value="">Year</option>
                                                    </select>
                                                </div>
                                                <div class="col-md-4">
                                                    <select required class="form-control" id="patientDOBMonth" name="month">
                                                        <option value="">Month</option>
                                                    </select>
                                                </div>
                                                <div class="col-md-4">
                                                    <select required class="form-control" id="patientDOBDay" name="day">
                                                        <option value="">Day</option>
                                                        <!-- Thêm các tùy chọn cho ngày tại đây -->
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group col-md-2">
                                            <label>Gender:</label>
                                            <div class="form-check">
                                                <input required type="radio" class="form-check-input" id="maleGender" name="gender" value="Male">
                                                <label class="form-check-label" for="maleGender">Male</label>
                                            </div>
                                            <div class="form-check">
                                                <input required type="radio" class="form-check-input" id="femaleGender" name="gender" value="Female">
                                                <label class="form-check-label" for="femaleGender">Female</label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="patientEmail">Parent's email:</label>
                                        <input disabled type="email" class="form-control" id="patientEmail" 
                                               name="email" placeholder="Enter email address" value="${sessionScope.user.email}">
                                    </div>
                                    <div class="form-group">
                                        <label for="patientPhoneNumber">Parent's phone:</label>
                                        <input type="text" class="form-control" id="patientPhoneNumber" 
                                               name="phoneNumber" placeholder="Enter phone number" value="${sessionScope.user.phoneNumber}">
                                    </div>
                                    <div class="form-group">
                                        <label for="patientAddress">Address:</label>
                                        <input type="text" class="form-control" id="patientAddress" 
                                               name="address" placeholder="Enter address" value="${sessionScope.user.address}">
                                    </div>
                                    <div class="alert alert-warning" style="font-size: 12px">
                                        When editing the address and phone number, profile information will change.
                                    </div>
                                </form>
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-danger border-m" data-dismiss="modal">
                                    <span class="d-flex align-items-center">
                                        <i class="fas fa-times mr-2"></i> Close
                                    </span> 
                                </button>
                                <button type="submit" class="btn btn-continue text-white">
                                    <span class="d-flex align-items-center">
                                        <i class="fas fa-save mr-2"></i> Save and Add
                                    </span>
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

        </div>

        <!-- Thêm script của Bootstrap JS và JavaScript tùy chỉnh -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script>
                                            $(".trash").click(function () {
                                                var cardElement = $(this).closest('.child-card'); // Tìm phần tử gần nhất có class 'child-card'
                                                var childID = $(this).val(); // Lấy giá trị childID từ nút xóa

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
                                                            url: '/ChildrenCare/user?action=delete-child',
                                                            type: 'GET',
                                                            data: {childID: childID},
                                                            success: function (response) {
                                                                cardElement.fadeOut(300, function () {
                                                                    $(this).remove();
                                                                });
                                                            },
                                                            error: function () {
                                                                alert('Error!');
                                                            }
                                                        });
                                                    }
                                                });
                                            });

                                            function readURL(input) {
                                                console.log(input.files);

                                                var reader = new FileReader();
                                                reader.onload = function (e) {
                                                    $("#img-preview").attr("src", e.target.result);
                                                };

                                                reader.readAsDataURL(input.files[0]);

                                            }
                                            function showFullInfo(index) {
                                                // Đóng tất cả các basicInfo và fullInfo trước khi mở cái mới
                                                var elements = document.querySelectorAll('[id^="fullInfo"]');
                                                for (var i = 0; i < elements.length; i++) {
                                                    elements[i].style.display = "none";
                                                }

                                                // Mở basicInfo và fullInfo tương ứng với index được chỉ định
                                                document.getElementById("basicInfo" + index).style.display = "block";
                                                document.getElementById("fullInfo" + index).style.display = "block";
                                            }

                                            function hideFullInfo(index) {
                                                // Đóng basicInfo và fullInfo tương ứng với index được chỉ định
                                                document.getElementById("basicInfo" + index).style.display = "none";
                                                document.getElementById("fullInfo" + index).style.display = "none";
                                            }
                                            function savePatient() {
                                                // Lấy thông tin từ các trường input trong modal
                                                const patientName = document.getElementById("patientName").value;
                                                const patientDOB = document.getElementById("patientDOB").value;

                                                // Ở đây, bạn có thể thực hiện việc lưu thông tin bệnh nhân vào cơ sở dữ liệu hoặc xử lý theo ý muốn.

                                                // Sau khi lưu hoặc xử lý xong, đóng modal
                                                $('#addPatientModal').modal('hide');
                                            }
                                            $(document).ready(function () {
                                                // Khởi tạo các combobox với tùy chọn mặc định
                                                initializeYearCombobox();
                                                initializeMonthCombobox();

                                                // Lắng nghe sự kiện khi thay đổi các combobox và cập nhật lại ngày tháng
                                                $('#patientDOBYear, #patientDOBMonth').change(function () {
                                                    updateDayCombobox();
                                                });

                                                // Hàm khởi tạo combobox cho năm
                                                function initializeYearCombobox() {
                                                    var currentYear = new Date().getFullYear();
                                                    for (var i = currentYear; i >= (currentYear - 18); i--) {
                                                        $('#patientDOBYear').append($('<option>', {
                                                            value: i,
                                                            text: i
                                                        }));
                                                    }
                                                }

                                                // Hàm khởi tạo combobox cho tháng
                                                function initializeMonthCombobox() {
                                                    for (var i = 1; i <= 12; i++) {
                                                        $('#patientDOBMonth').append($('<option>', {
                                                            value: i,
                                                            text: i
                                                        }));
                                                    }
                                                }

                                                // Hàm cập nhật combobox cho ngày (tùy theo năm và tháng đã chọn)
                                                function updateDayCombobox() {
                                                    $('#patientDOBDay').empty().append($('<option>', {
                                                        value: '',
                                                        text: 'Day'
                                                    }));

                                                    var selectedYear = $('#patientDOBYear').val();
                                                    var selectedMonth = $('#patientDOBMonth').val();
                                                    if (selectedYear && selectedMonth) {
                                                        var daysInMonth = new Date(selectedYear, selectedMonth, 0).getDate();
                                                        for (var i = 1; i <= daysInMonth; i++) {
                                                            $('#patientDOBDay').append($('<option>', {
                                                                value: i,
                                                                text: i
                                                            }));
                                                        }
                                                    }
                                                }
                                            });



        </script>
    </body>

</html>