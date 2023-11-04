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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" />
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>    
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
                <div class="col-md-8 mb-5">
                    <div class="card border-m mb-5">
                        <div class="card-header bg-primary text-white text-center bold">
                            Confirmation Information
                        </div>
                        <div class="card-body">
                            <table class="table custom-table">
                                <thead>
                                    <tr>
                                        <th class="align-middle">Reservation ID</th>
                                        <th class="align-middle">Service</th>
                                        <th class="align-middle">Doctor</th>
                                        <th class="align-middle">Appointment Time</th>
                                        <th class="align-middle">Fee</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>${reservation.reservationID}</td>
                                        <td>${service.title}</td>
                                        <td>${doctor.staffName}</td>
                                        <td>Time:<c:choose>
                                                <c:when test="${reservation.reservationSlot eq 1}">7:00 - 8:00</c:when>
                                                <c:when test="${reservation.reservationSlot eq 2}">8:00 - 9:00</c:when>
                                                <c:when test="${reservation.reservationSlot eq 3}">9:00 - 10:00</c:when>
                                                <c:when test="${reservation.reservationSlot eq 4}">10:00 - 11:00</c:when>
                                                <c:when test="${reservation.reservationSlot eq 5}">14:00 - 15:00</c:when>
                                                <c:when test="${reservation.reservationSlot eq 6}">15:00 - 16:00</c:when>
                                                <c:otherwise>Unknown</c:otherwise>
                                            </c:choose><br>Date: ${reservation.reservationDate}</td>
                                        <td>${service.salePrice}$</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-header bg-primary text-white text-center bold">
                            Children Information
                        </div>
                        <div class="card-body">

                            <div class="row">
                                <div class="info-desc col-md-9">
                                    <div class="row">
                                        <div class="col-md-5">
                                            <p class="title-info"><strong><i class="fa-solid fa-baby"></i> Fullname: </strong></p>
                                        </div>
                                        <div class="col-md-7 detail-info">
                                            <p>${children.childName}</p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-5">
                                            <p class="title-info"><i class="fas fa-calendar-alt"></i> <strong>Date of birth:</strong>
                                            </p>
                                        </div>
                                        <div class="col-md-7 detail-info">
                                            <p>${children.birthday}</p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-5">
                                            <p class="title-info"><i class="fas fa-venus-mars"></i> <strong>Sex:</strong>
                                            </p>
                                        </div>
                                        <div class="col-md-7 detail-info">
                                            <p>${children.gender}</p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-5">
                                            <p class="title-info"><i class="fa-solid fa-heart"></i><strong> Relationship with Customer:</strong>
                                            </p>
                                        </div>
                                        <div class="col-md-7 detail-info">
                                            <p>${children.relationship.relationshipName}</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="row justify-content-center">
                                        <img src="${children.image}" style="height: 85px;object-fit: cover;width: 85px;padding: 0.2rem;"
                                             alt="Image profile" style="max-height: 85px;"
                                             class="img-thumbnail rounded-circle" />
                                    </div>
                                </div>
                            </div>



                            <div class="col-md-9">
                                <div class="row">
                                    <div class="col-md-5">
                                        <p class="title-info"><strong><i class="fas fa-user"></i> Parent's Name:</strong></p>
                                    </div>
                                    <div class="col-md-7 detail-info">
                                        <p>${children.user.lastName} ${children.user.firstName}</p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-5">
                                        <p class="title-info"><i class="fas fa-venus-mars"></i> <strong>Gender:</strong>
                                        </p>
                                    </div>
                                    <div class="col-md-7 detail-info">
                                        <p>${children.user.gender}</p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-5">
                                        <p class="title-info"><i class="fas fa-phone"></i> <strong>Parent's phone:</strong></p>
                                    </div>
                                    <div class="col-md-7 detail-info">
                                        <p>${children.user.phoneNumber}</p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-5">
                                        <p class="title-info"><i class="fa-solid fa-envelope"></i> <strong>Parent's email:</strong></p>
                                    </div>
                                    <div class="col-md-7 detail-info">
                                        <p>${children.user.email}</p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-5">
                                        <p class="title-info"><i class="fas fa-map-marker-alt"></i> <strong>Address: </strong></p>
                                    </div>
                                    <div class="col-md-7 detail-info">
                                        <p>${children.user.address}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <form action="pay" id="frmCreateOrder" method="post">  
                        <div class="card border-m" style="width: 100%;">
                            <div class="card-header bg-primary text-white text-center bold">
                                Payment Information
                            </div>
                            <div class="card-body">
                                <div class="mb-4">
                                    <p class="title-info"><strong>Service Name:</strong></p>
                                    <p class="detail-info">${service.title}</p>
                                </div>
                                <div class="mb-4">
                                    <div class="form-group">
                                        <p class="title-info"><strong>Total Payment:</strong></p>
                                        <input readonly type="number" class="form-control" data-val="true" data-val-number="The field Amount must be a number."
                                               data-val-required="The Amount field is required." 
                                               name="amount" value="${service.salePrice}" />
                                    </div>

                                </div>
                                <div class="form-group">
                                    <p class="title-info"><strong>Payment Method:</strong></p>
                                    <c:choose>
                                        <c:when test="${service.salePrice == 0.0}">
                                            <div class="form-check">
                                                <input class="form-check-input" type="radio" Checked="True" name="payment" value="offline">
                                                <label for="" class="form-check-label detail-info">Pay at center</label><br>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="form-check">
                                                <input class="form-check-input" type="radio" Checked="True" name="payment" value="offline">
                                                <label  for="" class="form-check-label detail-info">Pay at center</label><br>
                                            </div>
                                            <div class="form-check">
                                                <input class="form-check-input" type="radio" id="bankCode" name="payment" value="vnpay">
                                                <label  for="" class="form-check-label detail-info">Payment via VNPpay</label><br>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>

                                    <input type="hidden" id="language" name="language" value="en">
                                    <input type="hidden" id="reserv" name="reservation" value="${reservation.reservationID}">
                                </div>
                            </div>
                        </div>
                        <div class="d-flex al justify-content-around mt-5 mb-5">
                            <button type="button" class="btn btn-none btn-block border-m btn-transparent" onclick="goBack()">
                                <span class="d-flex align-items-center bold">
                                    Back
                                </span>
                            </button>

                            <button type="submit" class="btn-continue btn btn-block text-white">
                                <span class="d-flex align-items-center">
                                    Continue
                                </span>
                            </button>

                        </div>
                    </form>

                </div>
            </div>
        </div>


    </div>


</div>
</div>

<jsp:include page="layout/footer.jsp"/>
<script type="text/javascript">
                                $("#frmCreateOrder").submit(function () {
                                    var postData = $("#frmCreateOrder").serialize();
                                    var submitUrl = $("#frmCreateOrder").attr("action");
                                    console.log("Data being sent:", postData);

                                    $.ajax({
                                        type: "POST",
                                        url: submitUrl,
                                        data: postData,
                                        dataType: 'JSON',
                                        success: function (x) {
                                            if (x.code === '00') {
                                                if (x.data && x.data !== "") {
                                                    // Nếu có dữ liệu trả về từ máy chủ
                                                    if (window.vnpay) {
                                                        vnpay.open({width: 768, height: 600, url: x.data});
                                                    } else {
                                                        location.href = x.data;
                                                    }
                                                } else {
                                                    location.href = "./view/reservationstatus.jsp";
                                                }
                                            } else {
                                                Swal.fire({
                                                    icon: 'error',
                                                    title: 'Oops...',
                                                    text: x.message
                                                })
                                            }
                                        }
                                    });

                                    return false;
                                });
</script>    
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
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</body>

</html>
