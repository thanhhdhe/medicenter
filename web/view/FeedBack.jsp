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
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Feedback Form</h1>
            <form action="feedback?action=sendfeedback" method="POST">
                <div class="form-group">
                    <label for="medical">medical</label>
                    <select id="medical" name="medical">
                        <c:forEach items="${requestScope.medical}" var="me">
                            <option value="${me.getMedicalExaminationID()}">${me.getUsedServices()}</option>   
                        </c:forEach>

                    </select>
                </div>
                <div class="form-group">
                    <label>Rate Us</label>
                    <div class="rating">
                        <span class="star" onclick="rate(1)">&#9733;</span>
                        <span class="star" onclick="rate(2)">&#9733;</span>
                        <span class="star" onclick="rate(3)">&#9733;</span>
                        <span class="star" onclick="rate(4)">&#9733;</span>
                        <span class="star" onclick="rate(5)">&#9733;</span>
                    </div>
                    <input type="text" id="rating" name="rating" value="">
                </div>

                <div class="form-group">
                    <label for="feedback">Feedback</label>
                    <textarea id="feedback" name="content" rows="4" required></textarea>
                </div>

                <div class="form-group">
                    <label for="attachment">Attachment</label>
                    <input type="file" id="attachment" name="attachment">
                </div>

                <button type="submit" class="btn-submit">Submit Feedback</button>
                <p>
                   <a class="btn-submit" style="text-decoration: none" href="home">Home</a>
                <p>
                

            </form>
        </div>
    </body>
</html>

