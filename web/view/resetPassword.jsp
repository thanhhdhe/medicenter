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
                width: 30%;
                background-color: #f2f2f2;
                box-shadow: 0px 0px 10px 0px gray;
            }
            .button1 button{
                width: 30%;
                background-color: #f2f2f2;
                box-shadow: 0px 0px 10px 0px gray;
            }
        </style>
        <title>JSP Page</title>
    </head>
    <body style="background-color: rgba(0,0,0,0.3)">

        <div class="container bg-white" >
            <div class="bg-white">
                <form action="resetpassword?action=change&ID=${requestScope.ID}&phoneNumber=${requestScope.phoneNumber}" method="POST">
                    <div class="bg-white khoi">
                        <div class="d-flex justify-content-center"><h1 style="font-family: 'Poppins';color: gray;">RESET PASSWORD</h1></div> 


                        <p>
                        <div class="d-flex justify-content-center password pass1">
                            <input class="form-control" type="password" name="newPassword" value="" placeholder="New Password" required/>
                        </div>     
                        <p>
                        <div class="d-flex justify-content-center password pass1">
                            <input class="form-control" type="password" name="conPassword" value="" placeholder="Confirm password" required/>
                        </div>

                        <p>

                        <div class="d-flex justify-content-center button1">
                            <input class="btn text-white mt-3" style="border: 0px;border-radius: 5px; background-color: #6ac3f0" type="submit" value="Enter" />
                        </div>  
                        <p>
                        <div class="d-flex justify-content-center button1">
                            <button class="btn text-white" style="border: 0px;border-radius: 5px; background-color: #6ac3f0" onclick="window.location.href = 'home'">
                                Cancel
                            </button>
                        </div>
                        <p>
                        <div style="justify-content: center; color: red;">
                            ${requestScope.notify}
                        </div>

                        <p>
                    </div>
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