<%-- 
    Document   : reservationcontact
    Created on : 05-Oct-2023, 21:23:09
    Author     : pc
--%>

<%@page import = "model.*" %>
<%@page import = "Database.*" %>
<%@page import = "java.util.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reservation Page</title>
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
            crossorigin="anonymous"
            />
        <link rel="stylesheet" href="./resources/css/services-style.css">
        <link rel="stylesheet" href="../resources/css/style.css">
    </head>
    <body>
        


        <div class="container d-flex ">


            <!-- User Information -->
            <c:set var="user" value="${requestScope.user}" />
            <div class="col-md-4 p-4 mb-3" style="background-color: #ffcc99;border-radius: 5px;">
                <div class="mb-5">
                    <!-- Replace with user's image -->
                    <img class="img-fluid rounded-circle w-50" style="box-shadow: 0px 0px 10px 0px #ffffff; margin-bottom: 10px" src="https://i.vgt.vn/2021/8/11/luu-diec-phi-canh-cao-dan-em-vuong-so-nhien-ngung-ke-fame-588-5951578.png" alt="User Image" >
                    <!-- Replace with user's name -->
                    <h4 style="font-family: monospace; margin-left: 40px">${user.getFirstName()} ${user.getLastName()}</h4>
                </div>
                <h4 style="font-family: monospace; margin-bottom: 40px">Receiver Information</h4>
                <div class="d-flex justify-content-center">
                    <div>

                        <c:set var="userID" value="${user.getUserID()}" />
                        <c:set var="userIDString" value="${userID.toString()}" />
                        <% 
                        ChildrenDAO childrenDAO = new ChildrenDAO();
                        List<Children> children1= childrenDAO.getListChildrenByUserId((String) pageContext.getAttribute("userIDString"));
                        for(Children children : children1){
                        %>
                        <form>
                            <!-- Replace with dynamic data if user is logged in -->
                            <div class="d-flex">
                                <div class="m-lg-1">
                                    <div class="mb-3">
                                        <img class="img-fluid rounded-circle" style="box-shadow: 0px 0px 10px 0px #ffffff; width: 50px;height: 50px" src="<%= children.getImage() %>" alt="User Image" >
                                    </div>
                                </div>
                                <div class="mb-3 m-lg-1">
                                    <h5><%= children.getChildName() %></h5>
                                </div>
                                <div class="mb-3 m-lg-1">
                                    <h5><%= children.getGender() %></h5>
                                </div> 
                                <div class="mb-3 m-lg-1">
                                    <h5><%= children.getBirthday() %></h5>
                                </div>
                                <div class="mb-3 m-lg-1">
                                    <h5><%= children.getStatus() %></h5>
                                </div>
                            </div>

                        </form>
                        <% }%>        
                    </div>
                </div>

            </div>

            <!-- Selected Services -->
            <div  class="col-md-6 mb-3 p-4" style="background-color: #e9f372; border-radius:5px">
                <div class="col-md-12">
                    <h2>Selected Services</h2>
                    <table class="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Title</th>
                                <th>Price</th>
                                <th>Children Name</th>
                                <th>Total Cost</th>
                            </tr>
                        </thead>

                        <tbody>                        
                            <c:forEach var="reservation" items="${requestScope.Reservation}">
                                <!-- Replace with dynamic service data -->
                                <tr>
                                    <td>${reservation.getServiceID()}</td>
                                    <c:set var="ServiceID" value="${reservation.getServiceID()}" />
                                    <c:set var="ServiceIDString" value="${ServiceID.toString()}" />
                                    <% 
                                    ServiceDAO serviceDAO = new ServiceDAO();
                                    Service service= serviceDAO.getServiceByID((String) pageContext.getAttribute("ServiceIDString"));
                                    %>
                                    <td><%= service.getTitle() %></td>
                                    <td>${reservation.getCost()}</td>
                                    <c:set var="ChildID" value="${reservation.getChildID()}" />
                                    <c:set var="ChildIDString" value="${ChildID.toString()}" />
                                    <% 
                                    ChildrenDAO childrenDAO1 = new ChildrenDAO();
                                    Children children12= childrenDAO1.getChildrenByUserId((String) pageContext.getAttribute("userIDString"),(String) pageContext.getAttribute("ChildIDString"));
                                    %>
                                    <td><%= children12.getChildName() %></td>
                                    <td>$100</td>
                                </tr>
                            </c:forEach>                            
                        </tbody>


                    </table>
                    <p>Total Reservation Price: ${requestScope.total}</p>
                    <a href="reservation-details.html" class="btn btn-primary">Change</a>
                    <button class="btn btn-success">Submit</button>
                </div>
            </div>
            <%ServiceDAO serviceDAO = new ServiceDAO();
            CategoryServiceDAO categoryServiceDAO = new CategoryServiceDAO();
            StaffDAO staffDAO = new StaffDAO();  %>

            <div class="card p-3 col-md-2 h-25">
                <form action="reservationcontactmanager?event=searchandfill" method="POST">
                    <input type="text" name="serviceTitle" placeholder="Search" class="form-control" />
                    <select class="form-select text-primary mt-3" name="serviceType" >
                        <option selected value="">Service Type</option>
                        <%List<CategoryService> categoryServiceList = categoryServiceDAO.getAllCategoryServices();
            for (CategoryService categoryService : categoryServiceList) {%>
                        <option value="<%=categoryService.getCategoryID()%>"><%=categoryService.getCategoryName()%></option>
                        <%}%>
                    </select>
                    <input type="submit" value="submit"/>
                </form>   
                <a href="service?event=to-contact-link" class="mt-3 ms-2">Contact Us</a>
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
