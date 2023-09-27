<%-- 
    Document   : myreservation
    Created on : Sep 25, 2023, 3:19:41 PM
    Author     : hbich
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>My Reservations</title>
        <!-- Add your CSS and JavaScript files here -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"/>
        <!-- Google Fonts Roboto -->
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700;900&display=swap"/>
        
        <jsp:include page="layout/Head.jsp"/>
    </head>
    <body>
        <%@ page import="java.util.List,java.util.ArrayList,java.text.SimpleDateFormat" %>
        <%@ page import="model.Reservation,model.Service,model.Staff,model.User"%>
        <%@ page import="Database.ReservationDAO,Database.ServiceDAO,Database.StaffDAO"%>
        <header>
            <jsp:include page="layout/Header.jsp"/>
            <link rel="stylesheet" href="./resources/css/mdb.min.css" />
        </header>

        <% User user = (User) request.getAttribute("user");
        String userID = "1";
        ReservationDAO rdao = new ReservationDAO();
        List<Reservation> reservations = rdao.getReservationByUserID("1");
        ServiceDAO serviceDAO = new ServiceDAO();
        StaffDAO staffDAO = new StaffDAO();
        %>

        <main>
            <section>
                <h1 style="text-align: center;">User Reservations</h1>
                <table class="table align-middle mb-0 bg-white table-hover">
                    <thead class="bg-light table-dark ">
                        <tr>
                            <th>ID</th>
                            <th>Reservation Date</th>
                            <th>Reservation Slot</th>
                            <th>Service</th>
                            <th>Staff Name</th>
                            <th>Total Cost</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%-- Iterate over the reservations and display them --%>
                        <% for (Reservation reservation : reservations) { 
                        Service service = serviceDAO.getServiceByID(Integer.toString(reservation.getServiceID()));
                        Staff staff = staffDAO.getStaffByStaffId(reservation.getStaffID());%>
                        <tr>
                            <td><a href="reservation?id=<%=reservation.getReservationID()%>""><%=reservation.getReservationID()%></a></td>
                                <%SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                            String formattedDate = sdf.format(reservation.getReservationDate());%>
                            <td><p class="fw-normal mb-1"><%=formattedDate%></p></td>
                            <td><p class="fw-normal mb-1"><%=reservation.getReservationSlot()%></p></td>
                            <td><p class="fw-normal mb-1"><%=service.getTitle()%></p></td>
                            <td><p class="fw-normal mb-1"><%=staff.getStaffName()%></p></td>
                            <td><p class="fw-normal mb-1"><%=reservation.getCost()%></p></td>
                            <td>
                                <%if (reservation.getStatus().equals("done")) {%>
                                <span class="badge badge-success rounded-pill d-inline">Done</span>
                                <% } else if (reservation.getStatus().equals("cancel")) { %>
                                <span class="badge badge-warning rounded-pill d-inline">Cancel</span>
                                <% } else { %>
                                <span class="badge badge-primary rounded-pill d-inline">Pending</span>
                                <% } %>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
                <%-- Add pagination links here --%>
                <% for (int i = 1;i<=rdao.getTotalReservationByUserID(userID, 5);i++) {%>
                <a> <%=i%> </a>
                <% } %>
            </section>

            <aside>
                <!-- Sidebar content goes here -->
            </aside>
        </main>

        <footer>
            <jsp:include page="layout/footer.jsp"/>
        </footer>
    </body>
</html>