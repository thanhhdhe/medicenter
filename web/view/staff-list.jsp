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
        <form action="staffList" method="get">
            <!--search bar start-->
            <div class="search-group d-flex">
                <div class="form-group"> 
                    <input type="text" name="staffName" value="${staffName}" placeholder="Search by doctor name">
                </div>
                <div class="form-group">
                    <select name="serviceID">
                        <c:forEach var="s" items="${serviceList}">
                            <option value="${s.getServiceID()}"> ${s.getTitle()}</option>
                        </c:forEach>
                    </select>
                </div> 
                <button class="more">
                    <!--<i class="fa fa-search"></i>-->
                    <span class="d-block">Search</span>
                </button>
            </div>
            <!--search bar end-->
            <!--startdoctor líst-->
            <h1>${notFound}</h1>
            <div class="list-doctor">
                <%List<Staff> staffList = (List<Staff>) request.getAttribute("staffList");
                for (Staff staff : staffList) {  %>
                <div class="item-doctor d-flex">
                    <div class="thumb"> 
                        <a href="/ChildrenCare/docDetail?ID=<%=staff.getStaffID()%>"> 
                            <img src="<%=staff.getProfileImage()%>" class="w-100"> 
                        </a>
                    </div>
                    <div class="info">
                        <h3>
                            <a href="/ChildrenCare/docDetail?ID=<%=staff.getStaffID()%>"><%=staff.getRank()%> <%=staff.getFullName()%></a></h3>
                        <p><img src="https://hongngochospital.vn/wp-content/themes/hongngoc/images/icon-level.png" alt=""><%=staff.getRank()%></p>
                        <p><img src="https://hongngochospital.vn/wp-content/themes/hongngoc/images/icon-park.png" alt=""> 
                            <%List<Integer> servicesList= ssDao.getServiceByDoc(staff.getStaffID());
                                for(int ser : servicesList){%>
                            <%= sDao.getServiceNameByID(ser) %>  ,   
                            <%}%>
                        </p>
                        <p><img src="https://hongngochospital.vn/wp-content/themes/hongngoc/images/icon-map.png" alt=""> Medilab</p>
                    </div>
                </div>
                <%}%>
            </div>
            <!--doctor list end-->
            <!--page-->
            <div class="d-flex justify-content-center" id="pagination-container">
                <c:forEach var="p" begin="1" end="${numOfPage}">
                    <input type="submit" name="page" value="${p}" /> 
                </c:forEach>
            </div>
        </form>
        <jsp:include page="layout/footer.jsp" />
    </body>
</html>
