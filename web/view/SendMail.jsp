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
                margin-top: 10px;
                width: 100%;
            }
            .sendmail input[type="submit"] {
                font-family: fantasy;
                background-color: #3399ff;
                padding: 10px;
                width: 20%;
                border: 0;
                color: #f3e4df;
                border-radius: 10px;
            }
            .sendmail a {
                width: 100%;
            }
            .sendmail input[type="button"] {
                margin-left: 20px;
                font-family: fantasy;
                color: #3399ff; 
                background-color:#f3e4df;
                padding: 10px;
                width: 15%;
                border: 1px solid #3399ff ;
                
                border-radius: 10px;
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
                <h1 style="text-align: left; margin-bottom: 2rem; border-bottom: solid 1px black">Find Your Account </h1>
                <h5>Please enter your email address to search for your account.</h5>
                <input type="text" name="Mail" value="" placeholder="Email"/>
                <div class="d-flex w-50" >
                    <div class="col-md-6">
                        <input style="width: 100%" type="submit"  value="Send" />
                    </div>
                    <div class="col-md-6">
                        <a href="home"><input style="width: 100%" type="button"  value="Cancel" /></a>
                    </div>
                    
                </div>
                
                <h2>${requestScope.notify}</h2>
            </form>
        </div>
        <jsp:include page="layout/footer.jsp" />    
    </body>
</html>
