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
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Choose children profile</title>

        <!-- Favicon -->
        <link rel="apple-touch-icon" sizes="76x76" href="./resources/favicon/apple-touch-icon.png" />
        <link rel="icon" type="image/png" sizes="32x32" href="./resources/favicon/favicon-32x32.png" />
        <link rel="icon" type="image/png" sizes="16x16" href="./resources/favicon/favicon-16x16.png" />
        <link rel="manifest" href="./resources/favicon/site.webmanifest" />
        <meta name="msapplication-TileColor" content="#da532c" />
        <meta name="theme-color" content="#ffffff" />
        <!--Font awesome-->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" 
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <style>
            body {
                background-color: #e8f2f7;
            }

            .detail-info {
                word-wrap: break-word;
                flex: 1 1;
                min-width: 0;
                padding-left: 5px;
                color: #003553;
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

            /*            .card:hover {
                            border: 1px solid #3699ff;
                            transform: translateZ(-3px);
                            cursor: pointer;
                        }*/

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
        <jsp:include page="./layout/Header.jsp" />
        <div class="container">
            <div class="justify-content-center mt-5">
                <!-- Đây là hàng (row) chứa hai cột (columns) -->
                <div class="row">
                    <!-- Cột cho thẻ card hiện có -->
                    <div class="col-md-4 mt-5">
                        <div class="card mt-4 border-m shadow">
                            <div class="card-body">
                                <h5 class="card-title">Info of examination</h5>
                                <table class="table">
                                    <tbody>
                                        <c:if test="${staff.staffID != null}">
                                            <tr>
                                                <td><i class="fas fa-user-md"></i></td>
                                                <td><strong>Dr:</strong></td>
                                                <td>${staff.staffName}</td>
                                            </tr>
                                        </c:if>
                                        <tr>
                                            <td><i class="fas fa-stethoscope"></i></td>
                                            <td><strong>Services:</strong></td>
                                            <td>${service.title}</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                    <!-- Cột cho thẻ card mới -->
                    <div class="col-md-8">
                        <div class="row choose-user justify-content-center ">
                            <h1 class="text-center">Choose Your Children Profile</h1>
                        </div>
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
                        <c:if test="${not empty requestScope.child}">
                            <c:forEach items="${requestScope.child}" var="c">
                                <div class="card mt-4 border-m shadow child-card">
                                    <div class="card-body" onclick="showFullInfo(${c.childID})">
                                        <div id="basicInfo${c.childID}">
                                            <div class="row">
                                                <div class="info-desc col-md-9">
                                                    <div class="row">
                                                        <div class="col-md-5">
                                                            <p class="title-info"><strong><i class="fa-solid fa-baby"></i> Fullname: </strong></p>
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
                                                            <p class="title-info"><i class="fas fa-venus-mars"></i> <strong>Sex:</strong>
                                                            </p>
                                                        </div>
                                                        <div class="col-md-7 detail-info">
                                                            <p>${c.gender}</p>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-5">
                                                            <p class="title-info"><i class="fa-solid fa-heart"></i><strong> Relationship with Customer:</strong>
                                                            </p>
                                                        </div>
                                                        <div class="col-md-7 detail-info">
                                                            <p>${c.relationship.relationshipName}</p>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-3">
                                                    <div class="row justify-content-center">
                                                        <img src="${c.image}" style="max-height: 85px; padding: 0.2rem;height: 85px;object-fit: cover;width: 85px;"
                                                             alt="Image profile"
                                                             class="img-thumbnail rounded-circle" />
                                                    </div>
                                                </div>
                                            </div>

                                        </div>

                                        <div id="fullInfo${c.childID}" class="col-md-9" style="display: none;">
                                            <div class="row">
                                                <div class="col-md-5">
                                                    <p class="title-info"><strong><i class="fas fa-user"></i> Parent's Name:</strong></p>
                                                </div>
                                                <div class="col-md-7 detail-info">
                                                    <p>${c.user.lastName} ${c.user.firstName}</p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-5">
                                                    <p class="title-info"><i class="fas fa-venus-mars"></i> <strong>Gender:</strong>
                                                    </p>
                                                </div>
                                                <div class="col-md-7 detail-info">
                                                    <p>${c.user.gender}</p>
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
                                            <div class="row">
                                                <div class="col-md-5">
                                                    <p class="title-info"><i class="fa-solid fa-envelope"></i> <strong>Parent's email:</strong></p>
                                                </div>
                                                <div class="col-md-7 detail-info">
                                                    <p>${c.user.email}</p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-5">
                                                    <p class="title-info"><i class="fas fa-map-marker-alt"></i> <strong>Address: </strong></p>
                                                </div>
                                                <div class="col-md-7 detail-info">
                                                    <p>${c.user.address}</p>
                                                </div>
                                            </div>
                                            <div class="card-footer mt-3">
                                                <div class="row mt-3" style="flex-wrap: nowrap;">
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
                                                                <button class="btn btn-primary btn-block border-m" data-bs-toggle="modal"
                                                                        data-bs-target="#updatePatientModal${c.childID}"><span
                                                                        class="d-flex align-items-center">
                                                                        <i class="fas fa-edit mr-2"></i> Edit
                                                                    </span></button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="">
                                                        <form action="reservationdetail?" method="POST">
                                                            <input type="hidden" name="serviceID" value="${service.serviceID}">
                                                            <c:if test="${staff.staffID != null}">
                                                                <input type="hidden" name="staffID" value="${staff.staffID}">
                                                            </c:if>
                                                            <input type="hidden" name="childID" value="${c.childID}">
                                                            <button type="submit" class="btn-continue btn btn-block text-white">
                                                                <span class="d-flex align-items-center">
                                                                    <i class="fas fa-arrow-circle-right mr-2"></i> Continue
                                                                </span>
                                                            </button>
                                                        </form>

                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal fade" id="updatePatientModal${c.childID}" tabindex="-1" role="dialog" aria-labelledby="patientModalLabel" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <form action="user?action=update-child" method="POST" enctype="multipart/form-data">
                                            <div class="modal-content border-m">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="patientModalLabel">Patient Profile</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body">
                                                    <div class="container">
                                                        <!-- Image Upload -->
                                                        <input type="hidden"  name="childID" value="${c.childID}">
                                                        <div class="form-group text-center">
                                                            <input type="file" style="display: none;" name="images"  class="inputfile" onchange="readURL(this)" accept="image/*" />
                                                            <label for="file">
                                                                <img id="img-preview" style="height: 100px; width: 100px;" class="rounded-circle mx-auto d-block image-create" name="default-image" src="https://cdn-icons-png.flaticon.com/512/3177/3177440.png" />
                                                                <i class="bi bi-pencil-square image-create"></i>
                                                            </label>
                                                        </div>
                                                        <!-- Fullname -->
                                                        <div class="form-group">
                                                            <label for="patientName">Fullname:</label>
                                                            <input required type="text" class="form-control" oninvalid="CheckFullName(this);" oninput="CheckFullName(this);"  name="fullname" placeholder="Enter fullname of your child" value="${c.childName}">
                                                        </div>
                                                        <!-- Date of Birth -->
                                                        <div class="row">
                                                            <div class="form-group">
                                                                <label for="patientDOB">Date of birth:</label>
                                                                <div class="row">
                                                                    <div class="col-md-4">
                                                                        <select required class="form-control patientDOBYear" id="patientDOBYear${c.childID}" name="year">
                                                                            <option value="">Year</option>
                                                                            <!-- Include years here -->
                                                                        </select>
                                                                    </div>
                                                                    <div class="col-md-4">
                                                                        <select required class="form-control patientDOBMonth" id="patientDOBMonth${c.childID}" name="month">
                                                                            <option value="">Month</option>
                                                                            <!-- Include months here -->
                                                                        </select>
                                                                    </div>
                                                                    <div class="col-md-4">
                                                                        <select required class="form-control patientDOBDay" id="patientDOBDay${c.childID}" name="day">
                                                                            <option value="">Day</option>
                                                                            <!-- Include days here -->
                                                                        </select>
                                                                    </div>

                                                                </div>
                                                            </div>
                                                            <div class="form-group col-md-2">
                                                                <label>Sex:</label>
                                                                <div class="form-check">
                                                                    <input required type="radio" class="form-check-input"  name="gender" value="Male" ${c.gender eq 'Male' ? 'checked' : ''}>
                                                                    <label class="form-check-label" for="maleGender">Male</label>
                                                                </div>
                                                                <div class="form-check">
                                                                    <input required type="radio" class="form-check-input"  name="gender" value="Female" ${c.gender eq 'Female' ? 'checked' : ''}>
                                                                    <label class="form-check-label" for="femaleGender">Female</label>
                                                                </div>
                                                            </div>

                                                        </div>
                                                        <!-- Relationship -->
                                                        <div class="form-group">
                                                            <label for="relaID">Relationship with Customer:</label>
                                                            <div class="row">
                                                                <div class="col-md-12">
                                                                    <select required class="form-control" name="relaID">
                                                                        <option value="">Relationship</option>
                                                                        <c:forEach var="re" items="${requestScope.relationship}">
                                                                            <c:choose>
                                                                                <c:when test="${c.relationship.relationshipID eq re.relationshipID}">
                                                                                    <option value="${re.relationshipID}" selected>${re.relationshipName}</option>
                                                                                </c:when>
                                                                                <c:otherwise>
                                                                                    <option value="${re.relationshipID}">${re.relationshipName}</option>
                                                                                </c:otherwise>
                                                                            </c:choose>
                                                                        </c:forEach>
                                                                    </select>
                                                                </div>
                                                            </div>

                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-danger border-m" data-bs-dismiss="modal">
                                                        <span class="d-flex align-items-center">
                                                            <i class="fas fa-times mr-2"></i> Close
                                                        </span>
                                                    </button>
                                                    <button type="submit" class="btn btn-continue text-white">
                                                        <span class="d-flex align-items-center">
                                                            <i class="fas fa-save mr-2"></i> Update
                                                        </span>
                                                    </button>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>                   
                            </c:forEach>
                        </c:if>
                    </div>
                </div>
            </div>
            <div class="row justify-content-center mb-5">
                <div class="footer-nav mt-5 col-md-6">
                    <div class="row" style="flex-wrap: nowrap;">
                        <div class="col-md-3">
                            <button type="button" class="btn btn-none btn-block border-m" onclick="goBack()">
                                <span class="d-flex align-items-center">
                                    <i class="fas fa-arrow-circle-left mr-2"></i> Back
                                </span>
                            </button>
                        </div>
                        <div class="col-md-6"></div>
                        <div class="">
                            <button type="button" class="btn btn-block border-m btn-continue text-white" data-bs-toggle="modal"
                                    data-bs-target="#addPatientModal">
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
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <div class="container"> 
                                    <div class="form-group text-center">
                                        <input type="file"  style=" display: none; "name="images"  class="inputfile" onchange="readURL(this)" accept="image/*"/>
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
                                        <input required type="text" class="form-control" oninvalid="CheckFullName(this);" oninput="CheckFullName(this);"
                                               name="fullname" placeholder="Enter fullname of your child">
                                    </div>
                                    <div class="row">
                                        <div class="form-group">
                                            <label for="patientDOB">Date of birth:</label>
                                            <div class="row">
                                                <div class="col-md-4">
                                                    <select required class="form-control patientDOBYear" id="patientDOBYear0" name="year">
                                                        <option value="">Year</option>
                                                        <!-- Include years here -->
                                                    </select>
                                                </div>
                                                <div class="col-md-4">
                                                    <select required class="form-control patientDOBMonth" id="patientDOBMonth0" name="month">
                                                        <option value="">Month</option>
                                                        <!-- Include months here -->
                                                    </select>
                                                </div>
                                                <div class="col-md-4">
                                                    <select required class="form-control patientDOBDay" id="patientDOBDay0" name="day">
                                                        <option value="">Day</option>
                                                        <!-- Include days here -->
                                                    </select>
                                                </div>

                                            </div>
                                        </div>
                                        <div class="form-group col-md-2">
                                            <label>Sex:</label>
                                            <div class="form-check">
                                                <input required type="radio" class="form-check-input"  name="gender" value="Male">
                                                <label class="form-check-label" for="maleGender">Male</label>
                                            </div>
                                            <div class="form-check">
                                                <input required type="radio" class="form-check-input"  name="gender" value="Female">
                                                <label class="form-check-label" for="femaleGender">Female</label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="relationship">Relationship with Customer: </label>
                                        <div class="row">
                                            <div class="col-md-12">
                                                <select required class="form-control" name="relaID">
                                                    <option value="">Relationship</option>
                                                    <c:forEach var="re" items="${requestScope.relationship}">
                                                        <option value="${re.relationshipID}">${re.relationshipName}</option>
                                                    </c:forEach>
                                                </select>
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
                                               oninvalid="CheckPhone(this);" oninput="CheckPhone(this);"
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
                                </div>
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-danger border-m" data-bs-dismiss="modal">
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
            <!-- Modal to Add or Update Patient -->



        </div>
        <jsp:include page="layout/footer.jsp" />
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" 
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
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
                                            function goBack() {
                                                window.history.back();
                                            }
                                            $(document).ready(function () {
                                                // Khởi tạo các combobox với tùy chọn mặc định
                                                initializeYearCombobox();
                                                initializeMonthCombobox();

                                                // Lắng nghe sự kiện khi thay đổi các combobox và cập nhật lại ngày tháng
                                                $('.patientDOBYear, .patientDOBMonth').change(function () {
                                                    var childID = $(this).attr('id').match(/\d+/)[0]; // Lấy childID từ ID của select box
                                                    updateDayCombobox(childID);
                                                });

                                                // Hàm khởi tạo combobox cho năm
                                                function initializeYearCombobox() {
                                                    var currentYear = new Date().getFullYear();
                                                    $('.patientDOBYear').each(function () {
                                                        var childID = $(this).attr('id').match(/\d+/)[0]; // Lấy childID từ ID của select box
                                                        for (var i = currentYear; i >= (currentYear - 18); i--) {
                                                            $(this).append($('<option>', {
                                                                value: i,
                                                                text: i
                                                            }));
                                                        }
                                                    });
                                                }

                                                // Hàm khởi tạo combobox cho tháng
                                                function initializeMonthCombobox() {
                                                    $('.patientDOBMonth').each(function () {
                                                        var childID = $(this).attr('id').match(/\d+/)[0]; // Lấy childID từ ID của select box
                                                        for (var i = 1; i <= 12; i++) {
                                                            $(this).append($('<option>', {
                                                                value: i,
                                                                text: i
                                                            }));
                                                        }
                                                    });
                                                }

                                                // Hàm cập nhật combobox cho ngày (tùy theo năm và tháng đã chọn)
                                                function updateDayCombobox(childID) {
                                                    var daySelect = $('#patientDOBDay' + childID);
                                                    daySelect.empty().append($('<option>', {
                                                        value: '',
                                                        text: 'Day'
                                                    }));

                                                    var selectedYear = $('#patientDOBYear' + childID).val();
                                                    var selectedMonth = $('#patientDOBMonth' + childID).val();
                                                    if (selectedYear && selectedMonth) {
                                                        var daysInMonth = new Date(selectedYear, selectedMonth, 0).getDate();
                                                        for (var i = 1; i <= daysInMonth; i++) {
                                                            daySelect.append($('<option>', {
                                                                value: i,
                                                                text: i
                                                            }));
                                                        }
                                                    }
                                                }
                                            });



                                            function CheckFullName(text) {
                                                var name = /^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễếệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ ]{4,}(?:[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễếệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]+){0,2}$/;
                                                if (!name.test(text.value)) {
                                                    text.setCustomValidity('Name is not valid');
                                                } else {
                                                    text.setCustomValidity('');
                                                }
                                                return true;
                                            }

                                            function CheckPhone(text) {
                                                var phone = /(84|0[3|5|7|8|9])+([0-9]{8})\b/;
                                                if (!phone.test(text.value)) {
                                                    text.setCustomValidity('Phone is not valid');
                                                } else {
                                                    text.setCustomValidity('');
                                                }
                                                return true;
                                            }


        </script>
    </body>
</html>