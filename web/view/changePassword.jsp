<%-- 
    Document   : changePassword
    Created on : 10-Sep-2023, 20:16:25
    Author     : pc
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.User"%>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
            crossorigin="anonymous"
            />
        <style>
            /* Popup container - can be anything you want */
            .popup {
                position: relative;
                display: inline-block;
                cursor: pointer;
                -webkit-user-select: none;
                -moz-user-select: none;
                -ms-user-select: none;
                user-select: none;
            }

            /* The actual popup */
            .popup .popuptext {
                visibility: hidden;
                width: 450px;
                text-align: center;
                background-color: white;
                padding: 40px;
                border-radius: 5px;
                box-shadow: 0px 0px 10px 0px #000;
                position: absolute;
                z-index: 1;
                top: 100%;
                left: 100%;
                margin-top: 100px;
                margin-left: -225px;
            }
            .popuptext input[type="password"]{
                padding: 16px;
                width: 100%;
            }
            .title1 h1{
                color: #000000;
                font-family: 'Montserrat', sans-serif;
            }
            .button1 input{
                width: 50%;
                background-color: #f2f2f2;
                box-shadow: 0px 0px 10px 0px #000;
            }
            /* Toggle this class - hide and show the popup */
            .popup .show {
                visibility: visible;
                -webkit-animation: fadeIn 1s;
                animation: fadeIn 1s;
            }

            /* Add animation (fade in the popup) */
            @-webkit-keyframes fadeIn {
                from {
                    opacity: 0;
                }
                to {
                    opacity: 1;
                }
            }

            @keyframes fadeIn {
                from {
                    opacity: 0;
                }
                to {
                    opacity:1 ;
                }
            }
        </style>
        <title>JSP Page</title>
    </head>
    <body>
        <form action="test" method="POST" onsubmit="return validateForm(this);">
            <input type="button" value="ChangePassword" onclick="myFunction()"/>
            <div class="d-flex justify-content-center">
                <div class=" popup" > 
                    <div class="popuptext" id="myPopup"> 
                        <div class="title1"><h1>CHANGE PASSWORD</h1></div> 
                        <p>
                        <div>
                            <input type="password" name="currentpassword" value="" placeholder="Current Password" />
                        </div> 
                        <p>
                        <div>
                            <input type="password" name="newPassword" value="" placeholder="New Password" />
                        </div>     
                        <p>
                        <div>
                            <input type="password" name="conPassword" value="" placeholder="Confirm password"/>
                        </div>
                        <p>
                        <div class="button1">
                            <input type="submit" value="ENTER"  />
                        </div>
                        <p>
                        <div class="button1">
                            <input type="button" value="CANCEL" onclick="myFunction()" />
                        </div> 
                        <p>
                        <div id="errorMessage" style="color: red; display: none;"></div>
                        
                    </div>
                </div>
            </div>


        </form>
        <script>
// When the user clicks on div, open the popup

            function myFunction() {
                var popup = document.getElementById("myPopup");
                popup.classList.toggle("show");

            }
            function validateForm(form) {
                // Lấy giá trị của mật khẩu mới và mật khẩu xác nhận
                var newPassword = form.newPassword.value;
                var conPassword = form.conPassword.value;

                // Lấy thẻ div chứa lỗi
                var errorMessage = document.getElementById("errorMessage");
                
                // Kiểm tra xem mật khẩu mới và mật khẩu xác nhận có bằng nhau
                if (newPassword === conPassword &&  newPassword !== "") {
                    // Nếu bằng nhau, hiển thị nút "ENTER" và ẩn lỗi
                    errorMessage.style.display = "none";
                    return true; // Cho phép form submission
                } else {
                    // Nếu không bằng nhau, hiển thị lỗi và ẩn nút "ENTER"
                    errorMessage.innerText = "Mật khẩu mới và mật khẩu xác nhận không khớp.";
                    errorMessage.style.display = "block";
                    return false; // Ngăn chặn form submission
                }
            }
        </script>
    </body>
</html>
