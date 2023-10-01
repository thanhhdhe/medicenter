<%-- 
    Document   : login-staff
    Created on : Oct 1, 2023, 8:58:48 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- Google Fonts -->
        <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Raleway:300,300i,400,400i,500,500i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
            crossorigin="anonymous"
            />
        <title>Login for staff</title>
    </head>
    <body>
        <div class="d-flex justify-content-center align-items-center" style="background-color: rgba(0, 0, 0, 0.15);">
            <div class="col-md-10 row bg-white h-100" style="box-shadow: 0 0 120px 0 rgba(0, 0, 0, 0.15);">
                <div class="col-md-6 ps-0"style="height: 100vh; overflow: hidden;">
                    <img src="resources/img/login-thumbnail.PNG" alt="alt" class="w-100 h-100"style="object-fit: cover; object-position: bottom;"/>
                </div>
                <div class="col-md-6 d-flex justify-content-center align-items-center">
                    <div class="col-md-8">
                        <img src="resources/img/icon/pill.png" alt="alt" class="mb-4"/>
                        <h2>Login</h2>
                        <p class="text-black-50 mb-3">Login to your account</p>
                        <%String err = (request.getAttribute("err")+"").equals("null")?"":(request.getAttribute("err")+"");
                        if(!err.equals("")){%>
                        <div class="p-3 mb-3" style="color: #a9001e;font-weight: 500; background-color: #ffe5ea;"><%=err%></div>
                        <%}%>
                        <form action="staff?event=login" method="POST">
                            <p style="font-weight: 600;">Email</p>
                            <input type="email" name="email" id="email" class="form-control py-2 mb-4" placeholder="Enter your email">
                            <p style="font-weight: 600;" class="mt-3">Password</p>
                            <input type="password" name="pass" id="pass" class="form-control py-2 mb-3" placeholder="Enter your password">
                            <input type="submit" value="Log In" class="btn btn-info text-light w-100 rounded-pill py-2 mt-5"style="background-color: rgb(70,130,169);"/>
                        </form>
                        
                        
                    </div>
                </div>
            </div>
        </div>


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
