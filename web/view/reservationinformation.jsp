<%-- 
    Document   : reservationinformation
    Created on : Oct 5, 2023, 1:11:47 PM
    Author     : hbich
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"/>
        <jsp:include page="layout/Head.jsp"/>
    </head>
    <body>  
        <%@ page import="java.text.SimpleDateFormat"%>
        <%@ page import="model.Reservation,model.Service,model.Children"%>
        <header>
            <jsp:include page="layout/Header.jsp"/>
            <link rel="stylesheet" href="./resources/css/mdb.min.css" />
        </header>
        <% 
        Reservation reservation = (Reservation) request.getAttribute("reservation");
        Children children = (Children) request.getAttribute("children");
        Service service = (Service) request.getAttribute("service");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");        
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        %>

        <h1>Reservation Information</h1>
        <table class="table align-middle mb-0 bg-white table-hover">
            <thead class="bg-light table-dark ">
                <tr class="text-center">
                    <th>ID</th>
                    <th>Created Date</th>
                    <th>Reservation Date</th>
                    <th>Reservation Slot</th>
                    <th>Total Cost</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <tr class="text-center"> 
                    <th><%=reservation.getReservationID()%></th>
                    <th><%=sdf2.format(reservation.getCreatedDate())%></th>
                    <th><%=sdf1.format(reservation.getReservationDate())%></th>
                    <th><%=reservation.getReservationSlot()%></th>
                    <th><%=reservation.getCost()%></th>
                    <th><%=reservation.getStatus()%></th>
                </tr>
            </tbody>
        </table>
        <h1>Children information</h1>
        <table class="table align-middle mb-0 bg-white table-hover">
            <thead class="bg-light table-dark ">
                <tr class="text-center">
                    <th>Avatar</th>
                    <th>Name</th>
                    <th>Birthday</th>
                    <th>Gender</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <tr class="text-center"> 
                    <th><img style="width:64px;height:64px;" src="<%=children.getAvatar()%>" alt="Children avatar"/></th>
                    <th><%=children.getChildName()%></th>
                    <th><%=sdf1.format(children.getBirthday())%></th>
                    <th><%=children.getGender()%></th>
                    <th><%=children.getStatus()%></th>
                </tr>
            </tbody>
        </table>
        <h1>Service Information</h1>
        <table class="table align-middle mb-0 bg-white table-hover">
            <thead class="bg-light table-dark ">
                <tr class="text-center">
                    <th>Thumbnail</th>
                    <th>Name</th>
                    <th>Brief information</th>
                    <th>Detail</th>
                    <th>Cost</th>
                </tr>
            </thead>
            <tbody>
                <tr class="text-center"> 
                    <th><img style="width:64px;height:64px;" src="<%=service.getThumbnail()%>" alt="Service thumbnail"/></th>
                    <th><%=service.getTitle()%></th>
                    <th style="width: 40%;"><%=service.getBrief()%></th>
                    <th><%=service.getServiceDetail()%></th>
                    <th><%if (service.getSalePrice() > 0.0) { %>
                        <del><%=service.getOriginalPrice()%></del>
                        <%=service.getSalePrice()%>
                        <% } else { %>
                        <%=service.getOriginalPrice()%>
                        <% } %>
                    </th>
                </tr>
            </tbody>
        </table>
        <div class="text-center">
            <!--Button to re-reserve-->
            <button id="re-reserve" class="btn btn-info">Re-reserve</button>
            <!--Button submit or update ( redirect to ReservationDetail with status : edit ) or cancel-->
            <button id="cancel" class="btn btn-warning">Cancel</button>
            <button id="edit" class="btn btn-success">Edit</button>
            <button id="exit" class="btn btn-danger">Exit</button>
        </div>
        <footer>
            <jsp:include page="layout/footer.jsp"/>
        </footer>
        <script>
            
        </script>
    </body>

</html>
