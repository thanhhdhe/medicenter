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
        <title>Confirm Information</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
        <script src="https://kit.fontawesome.com/d0c4ab4465.js" crossorigin="anonymous"></script>
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
        <%--<jsp:include page="layout/Header.jsp"/>--%>
        <div class="container">
            <div class="row justify-content-center mt-5">
                <div class="col-md-4 mb-5">
                    <div class="card border-m mb-5">
                        <div class="card-header bg-primary text-white text-center bold">
                            Medical examination information
                        </div>
                        <div class="card-body">
                            <table class="table custom-table">
                                <tbody>
                                    <!-- Children Information -->
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
                                        <td>${sessionScope.method}</td>
                                    </tr>
                                    <tr>
                                        <td class="detail-info">Status</td>
                                        <td class="mt-2 badge badge-success bg-primary">${reservation.status}</td>
                                    </tr>

                                </tbody>

                            </table>
                            <form action="/ChildrenCare/reservation?action=cancel" method="POST">                                <!-- Other form elements -->
                                <div class="text-center">
                                    <button type="submit" class="btn btn-danger rounded-circle" style="width: 50px; height: 50px;">
                                        <i class="fas fa-times"></i>
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="d-flex al justify-content-around mt-5 mb-5">
                        <button type="button" class="btn btn-none btn-block border-m btn-transparent" onclick="goBack()">
                            <span class="d-flex align-items-center bold">
                                Back
                            </span>
                        </button>
                    </div>
                </div>
            </div>
        </div>



    </div>


</div>
</div>

<jsp:include page="layout/footer.jsp"/>
<script src="https://pay.vnpay.vn/lib/vnpay/vnpay.min.js"></script>
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
<!--<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>-->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>

</html>
