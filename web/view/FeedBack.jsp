<%-- 
    Document   : FeedBack
    Created on : 18-Sep-2023, 15:40:23
    Author     : pc
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "model.*" %>
<%@page import = "Database.*" %>
<%@page import = "java.util.*" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Feedback Form</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f2f2f2;
                margin: 0;
                padding: 0;
            }

            .container {
                max-width: 700px;
                margin: auto;
                background-color: #fff;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0px 0px 10px 0px #000;
                margin-top: 20px;
            }

            h1 {
                font-size: 24px;
                color: #333;
                margin-bottom: 20px;
                text-align: center;
            }

            .form-group {
                margin-bottom: 20px;
            }

            label {
                display: block;
                font-weight: bold;
                margin-bottom: 5px;
            }

            input[type="text"],
            input[type="email"],
            input[type="tel"] {
                width: 98%;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 5px;
                font-size: 16px;
            }

            select {
                width: 98%;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 5px;
                font-size: 16px;
            }

            .rating {
                display: flex;
                align-items: center;
            }

            .star {
                color: #f1c40f; /* Màu sao đánh giá */
                font-size: 24px;
                margin-right: 5px;
            }

            textarea {
                width: 99%;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 5px;
                font-size: 16px;
                resize: vertical;
            }

            .btn-submit {
                background-color: #3498db; /* Màu nút gửi */
                color: #fff;
                padding: 10px 20px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 18px;
            }

            .btn-submit:hover {
                background-color: #2980b9; /* Màu khi di chuột qua nút */
            }
            .rate {
                float: left;
                height: 46px;
                padding: 0 10px;
            }
            .rate:not(:checked) > input {
                clip: rect(0 0 0 0);
                clip-path: inset(50%);
                height: 1px;
                overflow: hidden;
                position: absolute;
                width: 1px;
            }
            .rate:not(:checked) > label {
                float:right;
                width:1em;
                overflow:hidden;
                white-space:nowrap;
                cursor:pointer;
                font-size:30px;
                color:#ccc;
            }
            .rate:not(:checked) > label:before {
                content: '★ ';
            }
            .rate > input:checked ~ label {
                color: #ffc700;
            }
            .rate:not(:checked) > label:hover,
            .rate:not(:checked) > label:hover ~ label {
                color: #deb217;
            }
            .rate > input:checked + label:hover,
            .rate > input:checked + label:hover ~ label,
            .rate > input:checked ~ label:hover,
            .rate > input:checked ~ label:hover ~ label,
            .rate > label:hover ~ input:checked ~ label {
                color: #c59b08;
            }
        </style>
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
            crossorigin="anonymous"
            />
    </head>
    <body>

        <div class="d-flex align-items-center justify-content-center" style="height: 100vh; background-color: rgba(0,0,0,0.3)">
            
            <div class="col-md-6 container p-5 bg-white">
                <h1>Feedback Form</h1>
                <form action="feedback?action=sendfeedback" method="POST">
                    <div class="form-group">
                        <label for="medical">medical</label>
                        <select id="medical" name="medical">
                            <c:forEach items="${requestScope.medical}" var="me">
                                <c:set var="serID" value="${me.getServiceID()}" />
                                <c:set var="serIDString" value="${serID.toString()}" />
                                <% 
                                ServiceDAO serviceDAO = new ServiceDAO();
                                Service service = serviceDAO.getServiceByID((String) pageContext.getAttribute("serIDString"));
                                %>
                                <option value="${me.getMedicalExaminationID()}"> <%= service.getTitle() %> </option>   
                            </c:forEach>

                        </select>
                    </div>

                    <div class="form-group rate">
                        <input type="radio" id="star5" name="rate" value="5">
                        <label for="star5" title="text">5 stars</label>
                        <input type="radio" id="star4" name="rate" value="4">
                        <label for="star4" title="text">4 stars</label>
                        <input type="radio" id="star3" name="rate" value="3">
                        <label for="star3" title="text">3 stars</label>
                        <input type="radio" id="star2" name="rate" value="2">
                        <label for="star2" title="text">2 stars</label>
                        <input type="radio" id="star1" name="rate" value="1">
                        <label for="star1" title="text">1 star</label>
                    </div>

                    <div class="form-group">
                        <label for="feedback"></label>
                        <textarea id="feedback" name="content" rows="4" required></textarea>
                    </div>                 

                    <button type="submit" class="btn-submit mb-4">Submit Feedback</button>
                    <p>
                        <a class="btn-submit" style="text-decoration: none" href="home">Home</a>
                    <p>
                    <h4 style="color: red">
                        ${requestScope.notify}
                    </h4>    

                </form>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
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

