<%-- 
    Document   : reservationcontact
    Created on : 05-Oct-2023, 21:23:09
    Author     : pc
--%>

<%@page import = "model.*" %>
<%@page import = "Database.*" %>
<%@page import = "java.util.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reservation Page</title>
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
            crossorigin="anonymous"
            />
        <link rel="stylesheet" href="./resources/css/services-style.css">
        <link rel="stylesheet" href="../resources/css/style.css">
    </head>
    <body>
        <h1>Reservation Details</h1>
        <div class="container d-flex justify-content-center">
            

            <!-- User Information -->
            <div class="col-md-5">
                <div>
                    <!-- Replace with user's image -->
                    <img class="img-fluid rounded-circle w-50" style="box-shadow: 0px 0px 10px 0px #ffffff;" src="https://i.vgt.vn/2021/8/11/luu-diec-phi-canh-cao-dan-em-vuong-so-nhien-ngung-ke-fame-588-5951578.png" alt="User Image" class="img-fluid">
                    <!-- Replace with user's name -->
                    <h4 style="font-family: monospace">Vương Sở Nhiên</h4>
                </div>
                <div>
                    <h4>Receiver Information</h4>
                    <form>
                        <!-- Replace with dynamic data if user is logged in -->
                        <div class="d-flex">
                            <div class="col-md-5">
                                <div class="mb-3">
                                    <img class="img-fluid rounded-circle w-50" style="box-shadow: 0px 0px 10px 0px #ffffff;" src="https://i.vgt.vn/2021/8/11/luu-diec-phi-canh-cao-dan-em-vuong-so-nhien-ngung-ke-fame-588-5951578.png" alt="User Image" class="img-fluid">
                                </div>

                            </div>
                            <div class="col-md-7">
                                <div class="mb-3">
                                    <h5>Name</h5>
                                </div>
                                <div class="mb-3">
                                    <h5>Gender</h5>
                                </div> 
                            </div>
                        </div>
                        <div class="d-flex">
                            <div class="mb-3 col-md-6">
                                <h5>Birth Day</h5>
                            </div>
                            <div class="mb-3 col-md-6">
                                <h5>Status</h5>
                            </div>
                        </div>       
                    </form>
                </div>
            </div>

            <!-- Selected Services -->
            <div  class="col-md-7 m-lg-5">
                <div class="col-md-6">
                    <h2>Selected Services</h2>
                    <table class="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Title</th>
                                <th>Price</th>
                                <th>Quantity</th>
                                <th>Total Cost</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- Replace with dynamic service data -->
                            <tr>
                                <td>1</td>
                                <td>Service 1</td>
                                <td>$50</td>
                                <td>2</td>
                                <td>$100</td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>Service 2</td>
                                <td>$30</td>
                                <td>1</td>
                                <td>$30</td>
                            </tr>
                        </tbody>
                    </table>
                    <p>Total Reservation Price: $130</p>
                    <a href="reservation-details.html" class="btn btn-primary">Change</a>
                    <button class="btn btn-success">Submit</button>
                </div>
            </div>
        </div>
        <jsp:include page="layout/footer.jsp" />
        <script
            src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
            integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
            crossorigin="anonymous"
        ></script>
        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
            integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
            crossorigin="anonymous"
        ></script>
    </body>
</html>
