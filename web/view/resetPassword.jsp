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
        <link rel="stylesheet" href="./resources/style/resetpassword-list-style.css">
        <title>JSP Page</title>
    </head>
    <body>

        <div class="container">
            <div class="Khoi">
               <form action="resetpassword" method="POST">
                <div class="d-flex justify-content-center"><h1>RESET PASSWORD</h1></div> 
                <p>
                <div class="d-flex justify-content-center email">
                    <input type="text" name="Email" value="" placeholder="Email"/>
                </div> 
                <p>
                <div class="d-flex justify-content-center password">
                    <input type="text" name="newPassword" value="" placeholder="New Password"/>
                </div>     
                <p>
                <div class="d-flex justify-content-center password">
                    <input type="text" name="conPassword" value="" placeholder="Confirm password" />
                </div>
                <p>
                <div class="d-flex justify-content-center button">
                    <input type="submit" value="Enter" />
                </div>  
                <p>
                <div class="d-flex justify-content-center button">
                    <button onclick="window.location.href = 'index.html'">
                        Cancel
                    </button>
                </div>
                <p>
            </form>       
            </div>
              
        </div>


    </body>
</html>