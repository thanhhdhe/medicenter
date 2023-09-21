<%-- 
    Document   : SendMail
    Created on : 18-Sep-2023, 20:49:24
    Author     : pc
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html>


    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>JSP Page</title>
        
        <style>
            
            /* Đặt hình nền cho body */
            .sendmail {
                background-color: #f2f2f2; /* Màu nền của trang */
                font-family: Arial, sans-serif; /* Font chữ */
                display: flex;
                justify-content: center; /* Căn giữa theo chiều ngang */
                align-items: center; /* Căn giữa theo chiều dọc */
                height: 70vh; /* Chiều cao của toàn bộ màn hình */
            }

            /* CSS cho phần form */
            .sendmail form {
                background-color: #fff; /* Màu nền của form */
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0px 0px 10px 0px #000; /* Đổ bóng cho form */
            }

            /* CSS cho các phần tử bên trong form */
            .sendmail input[type="text"] {
                margin-bottom: 10px;
                padding: 10px;
                width: 94%;
            }
            .sendmail input[type="submit"] {
                margin-left: 80px;
                
                font-family: fantasy ;
                background-color: #3399ff;
                padding: 10px;
                width: 50%;
            }

            /* CSS cho tiêu đề */
            .sendmail h2 {
                color: #333; /* Màu chữ */
                text-align: center; /* Căn giữa tiêu đề */
            }
        </style>
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
            crossorigin="anonymous"
        />
        <link rel="stylesheet" href="../resources/css/style.css"/>
        <link rel="stylesheet" href="../resources/css/services-style.css">
    </head>
    <body>
        <jsp:include page="./layout/Header.jsp" />
        <div class="sendmail">
            <form action="resetpassword?action=confirmpassword&phu2=${requestScope.phu1}" method="POST">
                <h1 style="text-align: center;">Send Mail </h1>
                <input type="text" name="Mail" value="" />
                <input type="submit"  value="Send" />
                <h2>${requestScope.notify}</h2>
            </form>
        </div>
        <jsp:include page="layout/footer.jsp" />    
    </body>
</html>
