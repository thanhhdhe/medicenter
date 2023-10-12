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
        <style>
            #groupButton button {
                margin: 0px 5px;
            }
        </style>
    </head>
    <body>  
        <%@ page import="java.text.SimpleDateFormat"%>
        <%@ page import="model.Reservation,model.Service,model.Children"%>
        <header>
            <jsp:include page="layout/Header.jsp"/>
        </header>
        <% 
        Reservation reservation = (Reservation) request.getAttribute("reservation");
        Children children = (Children) request.getAttribute("children");
        Service service = (Service) request.getAttribute("service");
        SimpleDateFormat reservationDateFormat = new SimpleDateFormat("dd/MM/yyyy");        
        SimpleDateFormat createdDateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        %>

        <h1>Reservation information</h1>
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
                    <th><%=createdDateFormat.format(reservation.getCreatedDate())%></th>
                    <th><%=reservationDateFormat.format(reservation.getReservationDate())%></th>
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
                    <th><img style="width:64px;height:64px;" src="<%=children.getImage()%>" alt="Children avatar"/></th>
                    <th><%=children.getChildName()%></th>
                    <th><%=reservationDateFormat.format(children.getBirthday())%></th>
                    <th><%=children.getGender()%></th>
                    <th><%=children.getStatus()%></th>
                </tr>
            </tbody>
        </table>
        <h1>Service information</h1>
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
        <div id="groupButton" class="text-center" style="margin:30px;">

        </div>
        <footer>
            <jsp:include page="layout/footer.jsp"/>
        </footer>
        <script>
            var groupButton = document.getElementById("groupButton");
            var status = "<%=reservation.getStatus()%>";
            switch (status) {
                case "pending" :
                {
                    // Button checkout
                    var checkoutButton = document.createElement("button");
                    checkoutButton.className = "btn btn-warning";
                    checkoutButton.textContent = "Checkout";
                    checkoutButton.id = "checkout";
                    groupButton.appendChild(checkoutButton);
                    document.getElementById("checkout").addEventListener('click', function () {
                        checkout();
                    });
                    // Button cancel
                    var buttonCancel = document.createElement("button");
                    buttonCancel.className = "btn btn-warning";
                    buttonCancel.textContent = "Cancel this examination";
                    buttonCancel.id = "cancel";
                    groupButton.appendChild(buttonCancel);
                    document.getElementById("cancel").addEventListener('click', function () {
                        cancel();
                    });
                    // Button update
                    var updateButton = document.createElement("button");
                    updateButton.className = "btn btn-success";
                    updateButton.textContent = "Update";
                    updateButton.id = "update";
                    groupButton.appendChild(updateButton);
                    document.getElementById("update").addEventListener('click', function () {
                        update();
                    });
                    break;
                }
                case "awaiting confirmation" :
                {
                    // Button cancel
                    var cancelButton = document.createElement("button");
                    cancelButton.className = "btn btn-warning";
                    cancelButton.textContent = "Cancel this examination";
                    cancelButton.id = "cancel";
                    groupButton.appendChild(cancelButton);
                    document.getElementById("cancel").addEventListener('click', function () {
                        cancel();
                    });
                    // Button update
                    var updateButton = document.createElement("button");
                    updateButton.className = "btn btn-success";
                    updateButton.textContent = "Update";
                    updateButton.id = "update";
                    groupButton.appendChild(updateButton);
                    document.getElementById("update").addEventListener('click', function () {
                        update();
                    });
                    break;
                }
                case "waiting for examination" :
                {
                    // Button cancel
                    var cancelButton = document.createElement("button");
                    cancelButton.className = "btn btn-warning";
                    cancelButton.textContent = "Cancel this examination";
                    cancelButton.id = "cancel";
                    groupButton.appendChild(cancelButton);
                    document.getElementById("cancel").addEventListener('click', function () {
                        cancel();
                    });
                    break;
                }
                case "done" :
                {
                    // Button re-reserve
                    var rereserveButton = document.createElement("button");
                    rereserveButton.className = "btn btn-info";
                    rereserveButton.textContent = "Re-reserve";
                    rereserveButton.id = "re-reserve";
                    groupButton.appendChild(rereserveButton);
                    document.getElementById("re-reserve").addEventListener('click', function () {
                        reReserve();
                    });
                    break;
                }
                case "cancel" :
                {
                    // Button re-reserve
                    var rereserveButton = document.createElement("button");
                    rereserveButton.className = "btn btn-info";
                    rereserveButton.textContent = "Re-reserve";
                    rereserveButton.id = "re-reserve";
                    groupButton.appendChild(rereserveButton);
                    document.getElementById("re-reserve").addEventListener('click', function () {
                        reReserve();
                    });
                    break;
                }
                default :
                {
                }
            }
            // Back button
            var backButton = document.createElement("button");
            backButton.className = "btn btn-danger";
            backButton.textContent = "Back";
            backButton.id = "back";
            groupButton.appendChild(backButton);
            document.getElementById("back").addEventListener('click', function () {
                // Go back to the previous page
                window.history.back();
            });
            function checkout() {
                location.href = "reservation?action=confirm&reservationID=" +<%=reservation.getReservationID()%>;
            }
            function cancel() {
                var userChoice = confirm("Are you sure to cancel this examination ?");
                if (userChoice === true) {
                    var xhr = new XMLHttpRequest();
                    xhr.onreadystatechange = function () {
                        if (xhr.readyState === 4 && xhr.status === 200) {
                            var result = xhr.responseText;
                            if (result === "success") {
                                alert("You have been successfully cancel this reservation");
                                location.reload();
                            }
                        }
                    };
                    xhr.open("POST", "reservation", true);
                    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                    xhr.send("id=" + <%=reservation.getReservationID()%> + "&action=cancel");
                }
            }
            function update() {
                location.href = "reservationdetail?serviceID=" + <%=reservation.getServiceID()%> + "&staffID=" + <%=reservation.getStaffID()%> + "&childID=" + <%=reservation.getChildID()%> + "&action=update&reservationID=" + <%=reservation.getReservationID()%>;
            }
            function reReserve() {
                location.href = "reservationdetail?serviceID=" + <%=reservation.getServiceID()%> + "&staffID=" + <%=reservation.getStaffID()%> + "&childID=" + <%=reservation.getChildID()%>;
            }
        </script>        
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

    </body>

</html>
