<%-- 
    Document   : confirm-reservation
    Created on : 12-Oct-2023, 03:19:56
    Author     : Admin
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.nio.charset.StandardCharsets"%>
<%@page import="com.vnpay.common.Config"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>

<%@ page import="model.Reservation" %>
<%@ page import="Database.ReservationDAO" %>
<!DOCTYPE html>
<html lang="vi">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Confirm Information</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" />


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
                font-weight: 600;
                line-height: normal;
            }

            .title-info {
                color: #b1b1b1;
                font-size: 16px;
                font-weight: 300;
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

            .border-m {
                border: none;
                border-radius: 8px;
            }

            .btn-continue {
                border: none;
                background: linear-gradient(83.63deg, #00b5f1 33.34%, #00e0ff 113.91%) !important;
                border-radius: 8px;

            }
            .btn-transparent {
                background-color: transparent;
                border: none;
                color: #08b4e4;
                cursor: pointer;
                font-weight: 600;
            }
        </style>
    </head>

    <body>
        <jsp:include page="layout/Header.jsp"/>


        <div class="container">
            <div class="row justify-content-center mt-5">
                <div class="col-md-4 mb-5">
                    <div class="card border-m mb-5">
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
                        <div class="card-header bg-primary text-white text-center bold">
                            Medical examination information
                        </div>

                        <div class="card-body">
                            <table class="table custom-table">
                                <tbody>
                                    <!-- Children Information -->
                                <div id="fail-lg" class="text-danger flex-column align-items-center">
                                    <i class="far fa-circle-xmark fa-6x mb-3"></i>
                                    <label for="">
                                        <p class="text-uppercase">
                                            <small>CANCEL</small>
                                        </p>
                                    </label>
                                </div>
                                <div id="success-lg" class="text-success flex-column align-items-center">
                                    <i class="far fa-circle-check fa-6x mb-3"></i>
                                    <label for="">
                                        <p class="text-uppercase">
                                            <small>WAITTING FOR EXAMINATION</small>
                                        </p>
                                    </label>
                                </div>
                                <div id="pending-lg" class="text-primary flex-column align-items-center">
                                    <i class="far fa-clock fa-6x mb-3"></i>
                                    <label for="">
                                        <p class="text-uppercase">
                                            <small>PENDING (PAY FAILED)</small>
                                        </p>
                                    </label>
                                </div>
                                <tr>
                                    <td class="detail-info">Reservation ID</td>
                                    <td id="reservationID">${reservation.reservationID}</td>
                                </tr>
                                <tr>
                                    <td class="detail-info">Fullname of child</td>
                                    <td>${children.childName}</td>
                                </tr>
                                <tr>
                                    <td class="detail-info">Date of birth</td>
                                    <td>${children.birthday}</td>
                                </tr>
                                <tr>
                                    <td class="detail-info">Parent's phone</td>
                                    <td>${children.user.phoneNumber}</td>
                                </tr>
                                <tr>
                                    <td class="detail-info">Gender</td>
                                    <td>${children.gender}</td>
                                </tr>
                                <tr>
                                    <td class="detail-info">Parent's email</td>
                                    <td>${children.user.email}</td>
                                </tr>
                                <tr>
                                    <td class="detail-info">Address</td>
                                    <td>${children.user.address}</td>
                                </tr>
                                <tr>
                                    <td class="detail-info">Doctor</td>
                                    <td>${doctor.staffName}</td>
                                </tr>
                                <tr>
                                    <td class="detail-info">Specialty</td>
                                    <td>${service.title}</td>
                                </tr>

                                <tr>
                                    <td class="detail-info">Appointment Time</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${reservation.reservationSlot eq 1}">7:00 - 8:00</c:when>
                                            <c:when test="${reservation.reservationSlot eq 2}">8:00 - 9:00</c:when>
                                            <c:when test="${reservation.reservationSlot eq 3}">9:00 - 10:00</c:when>
                                            <c:when test="${reservation.reservationSlot eq 4}">10:00 - 11:00</c:when>
                                            <c:when test="${reservation.reservationSlot eq 5}">14:00 - 15:00</c:when>
                                            <c:when test="${reservation.reservationSlot eq 6}">15:00 - 16:00</c:when>
                                            <c:otherwise>Unknown</c:otherwise>
                                        </c:choose>

                                    </td>
                                </tr>
                                <tr>
                                    <td class="detail-info">Appointment Date</td>
                                    <td>${reservation.reservationDate}
                                    </td>
                                </tr>
                                <tr>
                                    <td class="detail-info">Consultation Fee</td>
                                    <td>${service.salePrice}$</td>
                                </tr>
                                <tr>
                                    <td class="detail-info">Method Payment</td>
                                    <td>${reservation.payment}</td>
                                </tr>

                                </tbody>

                            </table>
                            <div class="d-flex justify-content-around">
                                <button id="cancelButton" type="button" class="btn btn-none btn-block border-m btn-transparent" onclick="cancelInvoice()">
                                    <span class="d-flex align-items-center bold">
                                        Cancel
                                    </span>
                                </button>
                                <button type="button" class="btn btn-none btn-block border-m btn-continue">
                                    <a href="myreservation" class="text-decoration-none">
                                        <span class="text-white d-flex align-items-center bold">
                                            My reservation
                                        </span>
                                    </a>
                                </button>
                            </div>     
                        </div>
                    </div>
                </div>
            </div>
        </div>






        <jsp:include page="layout/footer.jsp"/>
        <!--<script src="https://pay.vnpay.vn/lib/vnpay/vnpay.min.js"></script>-->
        <script>
            var reservationStatus = "${reservation.status}";
            var cancelButton = document.getElementById("cancelButton");

            if (reservationStatus === "cancel") {
                // Nếu reservation.status là "cancel," tắt nút (disable)
                cancelButton.disabled = true;
            }
            // Lấy giá trị của reservation.status
            var reservationStatus = "${reservation.status}";
            console.log("log " + reservationStatus);
            // Lấy phần tử có id "fail-lg" và "success-lg"
            var failElement = document.getElementById("fail-lg");
            var successElement = document.getElementById("success-lg");
            var pendingElement = document.getElementById("pending-lg");

            // Kiểm tra giá trị của reservation.status và hiển thị tùy thuộc vào kết quả
            if (reservationStatus === "cancel") {
                failElement.style.display = "flex";
                successElement.style.display = "none";
                pendingElement.style.display = "none";
            } else if (reservationStatus === "waiting for examination") {
                failElement.style.display = "none";
                successElement.style.display = "flex";
                pendingElement.style.display = "none";
            } else {
                failElement.style.display = "none";
                successElement.style.display = "none";
                pendingElement.style.display = "flex";
            }
        </script>
        <script>
            function cancelInvoice() {
                // Retrieve reservationID from the HTML element with id "reservationID"
                const reservationID = document.getElementById("reservationID").textContent;
                console.log("Data being sent:", reservationID); // Log the data you're sending
                $.ajax({
                    type: "POST",
                    url: "./myreservation?action=cancel",
                    data: {invoiceId: reservationID},
                    dataType: 'json',
                    success: function (response) {
                        if (response.success) {
                            Swal.fire({
                                title: 'Are you sure?',
                                text: "You won't be able to revert this!",
                                icon: 'warning',
                                showCancelButton: true,
                                confirmButtonColor: '#3085d6',
                                cancelButtonColor: '#d33',
                                confirmButtonText: 'Yes, cancel it!'
                            }).then((result) => {
                                if (result.isConfirmed) {
                                    Swal.fire(
                                            'Canceled!',
                                            'Your file has been canceled.',
                                            'success'
                                            )
                                    if (failElement) {
                                        failElement.style.display = "flex";
                                        successElement.style.display = "none";
                                        pendingElement.style.display = "none";
                                        cancelButton.disabled = true;
                                    }
                                }
                            })



                        } else {
                            alert("Failed to cancel invoice. Error: " + response.error);
                        }
                    },
                    error: function (xhr, status, error) {
                        // Handle errors if any
                        alert("An error occurred: " + error);
                    }
                });
            }

            function getReservationTime(id) {
                switch (id) {
                    case 1:
                        return "7:00 - 8:00";
                    case 2:
                        return "8:00 - 9:00";
                    case 3:
                        return "9:00 - 10:00";
                    case 4:
                        return "10:00 - 11:00";
                    case 5:
                        return "14:00 - 15:00";
                    case 6:
                        return "15:00 - 16:00";
                    default:
                        return "Unknown";
                }
            }
            function goBack() {
                window.history.back();
            }
        </script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    </body>

</html>
