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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="" method="POST">
            <div class="container"> 
            <div><h1>RESET PASSWORD</h1></div> 
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
                <input type="submit" value="RESET" />
            </div>  
            <p>
            <div>
                <button onclick="window.location.href='index.html'">
                    Back to login
                </button>
            </div>
        </div>
        </form>
        
    </body>
</html>
