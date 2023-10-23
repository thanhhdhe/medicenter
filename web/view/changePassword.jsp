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
        <style>
            /* Popup container - can be anything you want */
            .popup {
                position: fixed;
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

            /* Add a layer beneath the popup using rgba for toggling */
            .popup-overlay {
                display: none;
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.7); /* Change opacity as needed */
                z-index: 198;
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
                width: 30%;
                background-color: #f2f2f2;
                box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.2);
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
        <div class="popup-overlay" id="overlay"></div>
        <form style="z-index: 200;position: relative;" action="changepassword" method="POST" >

            <div class="d-flex justify-content-center">



                <div class=" popup" > 
                    <div class="popuptext" id="myPopup"> 
                        <div class="title1"><h1 style="font-family: 'Poppins';color: gray;">CHANGE PASSWORD</h1></div> 
                        <p>
                        <div>
                            <input class="form-control" type="password" name="currentpassword" 
                                   value="" placeholder="Current Password" required/>
                        </div> 
                        <p>
                        <div>
                            <input class="form-control" type="password" name="newPassword" 
                                   value="" placeholder="New Password" required />
                        </div>     
                        <p>
                        <div>
                            <input class="form-control" type="password" name="conPassword" 
                                   value="" placeholder="Confirm password" required/>
                        </div>
                        <p>
                        <div class="button1">
                            <input  class="btn" style="border: 0px;border-radius: 5px; background-color: #6ac3f0" type="button" value="ENTER" onclick="validatePassword();"/>
                        </div>
                        <p>
                        <div class="button1">
                            <input  class="btn" style="border: 0px; border-radius: 5px; background-color: #6ac3f0" type="button" value="CANCEL" onclick="changePassowrd()" />
                        </div> 
                        <p>
                        <div id="error" style="color: red;"></div>


                    </div>
                </div>
            </div>


        </form>



        <script>
// When the user clicks on div, open the popup

            function changePassowrd() {
                var popup = document.getElementById("myPopup");
                var overlay = document.getElementById("overlay"); // Get the overlay

                if (popup.classList.contains("show")) {
                    popup.classList.remove("show");
                    overlay.style.display = "none"; // Hide the overlay
                } else {
                    popup.classList.add("show");
                    overlay.style.display = "block"; // Show the overlay
                }

            }
            function validatePassword() {
                var currentPassword = document.getElementsByName("currentpassword")[0].value; // Lấy giá trị của trường currentpassword
                var newPassword = document.getElementsByName("newPassword")[0].value; // Lấy giá trị của trường newPassword
                var conPassword = document.getElementsByName("conPassword")[0].value;

                var xhr = new XMLHttpRequest();
                xhr.open("POST", "changepassword", true);
                xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4) {
                        if (xhr.status === 200) {
                            // Xử lý phản hồi từ servlet (thông tin báo lỗi hoặc thành công)
                            var responseText = xhr.responseText;
                            var errorMessage = document.getElementById("error");
                            errorMessage.style.color = "red";
                            errorMessage.innerHTML = responseText;

                            if (responseText.indexOf("Notify:") === 1) {
                                changePassowrd(); // Hiển thị pop-up
                            }
                        }
                    }
                };

                // Gửi dữ liệu form bằng cách chuyển đổi nó thành chuỗi URL-encoded
                var formData = "currentpassword=" + currentPassword +
                        "&newPassword=" + newPassword +
                        "&conPassword=" + conPassword;

                xhr.send(formData);
            }
        </script>

    </body>
</html>