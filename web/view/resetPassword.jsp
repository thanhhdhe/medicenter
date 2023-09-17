<%-- 
    Document   : resetPassword
    Created on : 10-Sep-2023, 15:26:48
    Author     : pc
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.User"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
            crossorigin="anonymous"
            />
        <style>
            .khoi {
                width: 500px;
                border-radius: 5px;
                box-shadow: 0px 0px 10px 0px #000;
                position: absolute;
                padding: 40px;
                margin-top: 150px;
                margin-left: 370px;
            }
            .pass1 input{
                padding: 16px;
                width: 100%;
            }
            .button1 input{
                width: 50%;
                background-color: #f2f2f2;
                box-shadow: 0px 0px 10px 0px #000;
            }
            .button1 button{
                width: 50%;
                background-color: #f2f2f2;
                box-shadow: 0px 0px 10px 0px #000;
            }
        </style>
        <title>JSP Page</title>
    </head>
    <body>

        <div class="container">

            <form action="resetpassword?action=confirmpassword&phu2=${requestScope.phu1}" method="POST">
                <div class=" khoi">
                    <div class="d-flex justify-content-center"><h1>RESET PASSWORD</h1></div> 

                    <p>
                    <div class="d-flex justify-content-center password pass1">
                        <input type="text" name="Mail" value="" placeholder="Mail"/>
                    </div>     
                    <p>
                    <div class="d-flex justify-content-center password pass1">
                        <input type="password" name="newPassword" value="" placeholder="New Password"/>
                    </div>     
                    <p>
                    <div class="d-flex justify-content-center password pass1">
                        <input type="password" name="conPassword" value="" placeholder="Confirm password" />
                    </div>

                    <p>

                    <div class="d-flex justify-content-center button1">
                        <input type="submit" value="Enter" />
                    </div>  
                    <p>
                    <div class="d-flex justify-content-center button1">
                        <button onclick="window.location.href = 'index.html'">
                            Cancel
                        </button>
                    </div>
                    ${requestScope.success}
                    <p>
                </div>
            </form>       


        </div>


    </body>
</html>