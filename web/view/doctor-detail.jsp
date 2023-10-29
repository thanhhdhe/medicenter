<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : staff-list
    Created on : Oct 10, 2023, 12:44:35 AM
    Author     : quanh
--%>
<%@page import = "model.*" %>
<%@page import = "Database.*" %>
<%@page import = "java.util.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Doctor Page</title>
        <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Raleway:300,300i,400,400i,500,500i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
            crossorigin="anonymous"
            />
        <link rel="stylesheet" href="./resources/css/staff-style.css">
    </head>
    <body>
        <jsp:include page="./layout/Header.jsp" />
        <% ServiceStaffDAO ssDao= new ServiceStaffDAO(); %>
        <% ServiceDAO sDao= new ServiceDAO(); %>
        <!--start doctor detail-->
        <div class="list-doctor">
            <div class="content-page single-content">
                <div class="container">
                    <div class="row">
                        <div class="col-md-8">
                            <div class="row">
                                <div class="col-md-4"> 
                                    <img src="${staff.getProfileImage()}">
                                    <div class="text-center">
                                       <a href="service?event=doc&staffID=${staff.getStaffID()}"> <button class="more" >Make an appointment</button></a>
                                    </div>
                                </div>
                                <div class="col-md-8">
                                    <!--<input type="type" name="${staff}">-->
                                    <h1 class="page-title">${staff.getRank()} ${staff.getFullName()}</h1>
                                    <div class="list-info">
                                        <table cellpadding="10">
                                            <tbody>
                                                <tr>
                                                    <td> 
                                                        <strong>Specialist</strong>
                                                    </td>
                                                    <td>
                                                        <p class="detail">${staff.getSpecialty()}</p>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td> <strong>Education</strong></td>
                                                    <td>
                                                        <p class="detail"></p>
                                                        <p>${staff.getDepthStudy()}
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td> 
                                                        <strong>Specialized Activities</strong>
                                                    </td>
                                                    <td>
                                                        <p class="detail"></p>
                                                        <p>${staff.getSpecializedActivities()}
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td> 
                                                        <strong>introduction</strong>
                                                    </td>
                                                    <td>
                                                        <p class="detail"></p>
                                                        <p>${staff.getIntroduction()}</p><p></p>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <div class="achievement">
                                <h3>Professional Achievements</h3>
                                <div class="detail">
                                    <p><span data-sheets-value="{&quot;1&quot;:2,&quot;2&quot;:&quot;${staff.getProfessionalAchievements()}\r&quot;}" data-sheets-userformat="{&quot;2&quot;:957,&quot;3&quot;:{&quot;1&quot;:0},&quot;5&quot;:{&quot;1&quot;:[{&quot;1&quot;:2,&quot;2&quot;:0,&quot;5&quot;:{&quot;1&quot;:2,&quot;2&quot;:0}},{&quot;1&quot;:0,&quot;2&quot;:0,&quot;3&quot;:3},{&quot;1&quot;:1,&quot;2&quot;:0,&quot;4&quot;:1}]},&quot;6&quot;:{&quot;1&quot;:[{&quot;1&quot;:2,&quot;2&quot;:0,&quot;5&quot;:{&quot;1&quot;:2,&quot;2&quot;:0}},{&quot;1&quot;:0,&quot;2&quot;:0,&quot;3&quot;:3},{&quot;1&quot;:1,&quot;2&quot;:0,&quot;4&quot;:1}]},&quot;7&quot;:{&quot;1&quot;:[{&quot;1&quot;:2,&quot;2&quot;:0,&quot;5&quot;:{&quot;1&quot;:2,&quot;2&quot;:0}},{&quot;1&quot;:0,&quot;2&quot;:0,&quot;3&quot;:3},{&quot;1&quot;:1,&quot;2&quot;:0,&quot;4&quot;:1}]},&quot;8&quot;:{&quot;1&quot;:[{&quot;1&quot;:2,&quot;2&quot;:0,&quot;5&quot;:{&quot;1&quot;:2,&quot;2&quot;:0}},{&quot;1&quot;:0,&quot;2&quot;:0,&quot;3&quot;:3},{&quot;1&quot;:1,&quot;2&quot;:0,&quot;4&quot;:1}]},&quot;10&quot;:1,&quot;11&quot;:4,&quot;12&quot;:0}">${staff.getProfessionalAchievements()}<br> <br>
                                        </span></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--doctor detail end-->
                <jsp:include page="layout/footer.jsp" />
    </body>
</html>
