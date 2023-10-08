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
        </style>
    </head>

    <body>
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-6 choose-user mt-5">
                    <h1 class="text-center">Thông Tin Bệnh Nhân</h1>
                    <c:forEach items="${requestScope.child}" var="c">
                        <div class="card mt-4 border-m shadow">
                            <div class="card-body" onclick="showFullInfo(1)">
                                <div id="basicInfo1">
                                    <!-- Chỉ hiển thị thông tin cơ bản ban đầu -->
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

                                <div id="fullInfo1" style="display: none;">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <p class="title-info"><i class="fas fa-venus-mars"></i> <strong>Giới tính:</strong>
                                            </p>
                                        </div>
                                        <div class="col-md-8 detail-info">
                                            <p>${c.gender}</p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-4">
                                            <p class="title-info"><i class="fas fa-globe"></i> <strong>Dân tộc:</strong></p>
                                        </div>
                                        <div class="col-md-8 detail-info">
                                            <p>Ê-đê</p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-4">
                                            <p class="title-info"><i class="fas fa-map-marker-alt"></i> <strong>Adddresss: </strong></p>
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
                                                        <button class="btn btn-danger btn-block border-m">
                                                            <span class="d-flex align-items-center">
                                                                <i class="fas fa-arrow-circle-left mr-2"></i> Xóa
                                                            </span>
                                                        </button>
                                                    </div>
                                                    <div class="col-md-4">
                                                        <button class="btn btn-primary btn-block border-m"><span
                                                                class="d-flex align-items-center">
                                                                <i class="fas fa-edit mr-2"></i> Sửa
                                                            </span></button>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="">
                                                <button class="btn-continue btn btn-block text-white">
                                                    <span class="d-flex align-items-center">
                                                        <i class="fas fa-arrow-circle-right mr-2"></i> Tiếp tục
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
                                    <i class="fas fa-arrow-circle-left mr-2"></i> Quay lại
                                </span>
                            </button>
                        </div>
                        <div class="col-md-6"></div>
                        <div class="">
                            <button type="button" class="btn btn-block border-m btn-continue text-white" data-toggle="modal"
                                    data-target="#addPatientModal">
                                <span class="d-flex align-items-center">
                                    <i class="fas fa-plus-circle mr-2"></i> Thêm hồ sơ
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
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="addPatientModalLabel">Thêm hồ sơ bệnh nhân</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <!-- Form để nhập thông tin của bệnh nhân -->
                            <form>
                                <div class="form-group">
                                    <label for="patientName">Họ và tên:</label>
                                    <input type="text" class="form-control" id="patientName" placeholder="Nhập họ và tên">
                                </div>
                                <div class="form-group">
                                    <label for="patientDOB">Ngày sinh:</label>
                                    <div class="row">
                                        <div class="col-md-4">
                                            <select class="form-control" id="patientDOBYear">
                                                <option value="">Năm</option>
                                                <!-- Thêm các tùy chọn cho năm tại đây -->
                                            </select>
                                        </div>
                                        <div class="col-md-4">
                                            <select class="form-control" id="patientDOBMonth">
                                                <option value="">Tháng</option>
                                                <!-- Thêm các tùy chọn cho tháng tại đây -->
                                            </select>
                                        </div>
                                        <div class="col-md-4">
                                            <select class="form-control" id="patientDOBDay">
                                                <option value="">Ngày</option>
                                                <!-- Thêm các tùy chọn cho ngày tại đây -->
                                            </select>
                                        </div>
                                    </div>
                                </div>

                                <!-- Thêm các trường thông tin khác tại đây -->
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger border-m" data-dismiss="modal">
                                <span class="d-flex align-items-center">
                                    <i class="fas fa-times mr-2"></i> Đóng
                                </span> 
                            </button>
                            <button type="button" class="btn btn-continue text-white" onclick="savePatient()">
                                <span class="d-flex align-items-center">
                                    <i class="fas fa-save mr-2"></i> Lưu
                                </span>
                            </button>
                        </div>
                    </div>
                </div>
            </div>

        </div>

        <!-- Thêm script của Bootstrap JS và JavaScript tùy chỉnh -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script>
                                function showFullInfo(index) {
                                    document.getElementById("basicInfo" + index).style.display = "block";
                                    document.getElementById("fullInfo" + index).style.display = "block";
                                }
                                function HideFullInfo(index) {
                                    document.getElementById("basicInfo" + index).style.display = "block";
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
                                            text: 'Ngày'
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