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
        <title>Medilab</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <link href="./resources/css/style.css" rel="stylesheet">

        <style>
            #groupButton button {
                margin: 0px 5px;
            }
        </style>
    </head>
    <body>  
        <%@ page import="java.text.SimpleDateFormat"%>
        <%@ page import="model.Reservation,model.Service,model.Children,model.User"%>
        <header>
            <jsp:include page="layout/Header.jsp"/>
        </header>
        <% 
        Reservation reservation = (Reservation) request.getAttribute("reservation");
        Children children = (Children) request.getAttribute("children");
        Service service = (Service) request.getAttribute("service");
        User user = (User) request.getAttribute("user");
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
                    <td><%=reservation.getReservationID()%></td>
                    <td><%=createdDateFormat.format(reservation.getCreatedDate())%></td>
                    <td><%=reservationDateFormat.format(reservation.getReservationDate())%></td>
                    <td><%=reservation.getReservationSlot()%></td>
                    <td><%=reservation.getCost()%></td>
                    <td><%=reservation.getStatus()%></td>
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
                    <th>Relationship</th>
                </tr>
            </thead>
            <tbody>
                <tr class="text-center"> 
                    <td><img style="width:64px;height:64px;" src="<%=children.getImage()%>" alt="Children avatar"/></td>
                    <td><%=children.getChildName()%></td>
                    <td><%=reservationDateFormat.format(children.getBirthday())%></td>
                    <td><%=children.getGender()%></td>
                    <td><%=children.getStatus()%></td>
                    <td><%=children.getRelationship().getRelationshipName()%></td>
                </tr>
            </tbody>
        </table>
        <h1>Customer information</h1>
        <table class="table align-middle mb-0 bg-white table-hover">
            <thead class="bg-light table-dark ">
                <tr class="text-center">
                    <th>Full name</th>
                    <th>Gender</th>
                    <th>Email</th>
                    <th>Phone number</th>
                </tr>
            </thead>
            <tbody>
                <tr class="text-center"> 
                    <td><%=user.getFirstName()%> <%=user.getLastName()%></td>
                    <td><%=user.getGender()%></td>
                    <td><%=user.getEmail()%></td>
                    <td><%=user.getPhoneNumber()%></td>
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
                    <td><img style="width:64px;height:64px;" src="<%=service.getThumbnail()%>" alt="Service thumbnail"/></td>
                    <td><%=service.getTitle()%></td>
                    <td style="width: 40%;"><%=service.getBrief()%></td>
                    <td><%=service.getServiceDetail()%></td>
                    <td><%if (service.getSalePrice() > 0.0) { %>
                        <del><%=service.getOriginalPrice()%></del>
                        <%=service.getSalePrice()%>
                        <% } else { %>
                        <%=service.getOriginalPrice()%>
                        <% } %>
                    </td>
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
                //                 Go back to the previous page
                //                window.history.back();

                location.href = "myreservation";
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
