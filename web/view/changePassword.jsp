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
                width: 250px;
                background-color: #555;
                color: #fff;
                text-align: center;
                border-radius: 6px;
                padding: 8px 0;
                position: absolute;
                z-index: 1;
                top: 100%;
                left: 100%;
                margin-left: -80px;
            }

            /* Popup arrow */
            .popup .popuptext::after {
                content: "";
                position: absolute;
                bottom: 100%;
                left: 50%;
                margin-left: -5px;
                border-width: 5px;
                border-style: solid;
                border-color: #555 transparent transparent transparent;
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
        <form action="" method="POST">
            <input type="button" value="ChangePassword" onclick="myFunction()"/>
            <div class="popup" > 
                <div class="popuptext" id="myPopup"> 
                    <div><h1>Change PASSWORD</h1></div> 
                    <p>
                    <div>
                        New Password:<input type="text" name="newPassword" value="" />
                    </div>     
                    <p>
                    <div>
                        Confirm password:<input type="text" name="conPassword" value=""/>
                    </div>
                    <p>
                    <div>
                        <input type="submit" value="ENTER" onclick="myFunction()" />
                    </div>
                    <p>
                    <div>
                        <input type="button" value="CANCEL" onclick="myFunction()" />
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

            
        </script>
    </body>
</html>
