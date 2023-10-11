<%-- 
    Document   : confirm-reservation
    Created on : 12-Oct-2023, 03:19:56
    Author     : Admin
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Xác nhận thông tin khám</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
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
        <div class="container mt-4 center-content">
            <div class="section">
                <div class="row justify-content-center mt-5">
                    <div class="d-flex justify-content-center">
                        <div class="card border-m">
                            <div class="card-header bg-primary text-white text-center bold">
                                Confirmation Information
                            </div>
                            <div class="card-body">
                                <table class="table custom-table">
                                    <thead>
                                        <tr>
                                            <th class="align-middle">Specialty</th>
                                            <th class="align-middle">Service</th>
                                            <th class="align-middle">Doctor</th>
                                            <th class="align-middle">Appointment Time</th>
                                            <th class="align-middle">Consultation Fee</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>RESPIRATORY</td>
                                            <td>${service.title}</td>
                                            <td>${doctor.staffName}</td>
                                            <td><c:choose>
                                        <c:when test="${reservation.reservationSlot eq 1}">7:00 - 8:00</c:when>
                                        <c:when test="${reservation.reservationSlot eq 2}">8:00 - 9:00</c:when>
                                        <c:when test="${reservation.reservationSlot eq 3}">9:00 - 10:00</c:when>
                                        <c:when test="${reservation.reservationSlot eq 4}">10:00 - 11:00</c:when>
                                        <c:when test="${reservation.reservationSlot eq 5}">14:00 - 15:00</c:when>
                                        <c:when test="${reservation.reservationSlot eq 6}">15:00 - 16:00</c:when>
                                        <c:otherwise>Unknown</c:otherwise>
                                    </c:choose><br>${reservation.reservationDate}</td>
                                    <td>${service.salePrice}</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>

                        </div>
                    </div>
                </div>
                <div class="row justify-content-center mt-5">
                    <div class="d-flex justify-content-center">
                        <div class="card" style="width: 52%;">
                            <div class="card-header bg-primary text-white text-center bold">
                                Children Information
                            </div>
                            <div class="card-body">

                                <div class="row">
                                    <div class="info-desc col-md-9">
                                        <div class="row">
                                            <div class="col-md-5">
                                                <p class="title-info"><strong><i class="fas fa-user"></i> Fullname:
                                                    </strong></p>
                                            </div>
                                            <div class="col-md-7 detail-info">
                                                <p>${children.childName}</p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-5">
                                                <p class="title-info"><i class="fas fa-calendar-alt"></i> <strong>Date of
                                                        birth:</strong>
                                                </p>
                                            </div>
                                            <div class="col-md-7 detail-info">
                                                <p>${children.birthday}</p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-5">
                                                <p class="title-info"><i class="fas fa-phone"></i> <strong>Parent's
                                                        phone:</strong></p>
                                            </div>
                                            <div class="col-md-7 detail-info">
                                                <p>${children.user.phoneNumber}</p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="row justify-content-center">
                                            <img src="${children.image}" style="height: 85px;object-fit: cover;width: 85px;"
                                                 alt="Image profile" style="max-height: 85px;"
                                                 class="img-thumbnail rounded-circle" />
                                        </div>
                                    </div>
                                </div>



                                <div class="row">
                                    <div class="col-md-4">
                                        <p class="title-info"><i class="fas fa-venus-mars"></i> <strong>Gender:</strong>
                                        </p>
                                    </div>
                                    <div class="col-md-8 detail-info">
                                        <p>${children.gender}</p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-4">
                                        <p class="title-info"><i class="fas fa-globe"></i> <strong>Parent's email:</strong>
                                        </p>
                                    </div>
                                    <div class="col-md-8 detail-info">
                                        <p>${children.user.email}</p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-4">
                                        <p class="title-info"><i class="fas fa-map-marker-alt"></i> <strong>Address:
                                            </strong></p>
                                    </div>
                                    <div class="col-md-8 detail-info">
                                        <p>${children.user.address}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="d-flex al justify-content-around mt-5 mb-5">
                    <button type="button" class="btn btn-none btn-block border-m btn-transparent" onclick="goBack()">
                        <span class="d-flex align-items-center bold">
                            Back
                        </span>
                    </button>
                    <form action="reservationdetail?" method="POST">
                        <input type="hidden" name="serviceID" value="${service.serviceID}">
                        <input type="hidden" name="childID" value="${children.childID}">
                        <button type="submit" class="btn-continue btn btn-block text-white">
                            <span class="d-flex align-items-center">
                                Confirm
                            </span>
                        </button>
                    </form>

                </div>
            </div>


        </div>
    </div>

    <jsp:include page="layout/footer.jsp"/>
    <script>
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
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>

</html>
