<%-- 
    Document   : myreservation
    Created on : Sep 25, 2023, 3:19:41 PM
    Author     : hbich
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>--%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>My Reservations</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"/>
        <!-- Google Fonts Roboto -->
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700;900&display=swap"/>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <link href="./resources/css/style.css" rel="stylesheet">

    </head>
    <body>
        <%@ page import="java.util.List,java.util.ArrayList,java.text.SimpleDateFormat,jakarta.servlet.http.HttpSession" %>
        <%@ page import="model.Reservation,model.Service,model.Staff,model.User"%>
        <%@ page import="Database.ReservationDAO,Database.ServiceDAO,Database.StaffDAO,Database.UserDAO"%>
        <header>
            <jsp:include page="layout/Header.jsp"/>
        </header>

        <%  UserDAO userDAO = new UserDAO();
            User user = userDAO.getUser((String) session.getAttribute("email"));
            String userID = Integer.toString(user.getUserID());
            ReservationDAO reservationDAO = new ReservationDAO();
            ServiceDAO serviceDAO = new ServiceDAO();
            StaffDAO staffDAO = new StaffDAO();
            String myReservationPage = (String) request.getAttribute("page");
        %>

        <main>  

            <section>
                <h1 class="text-center">User Reservations</h1>
                <form id="ReservationSearch" style="margin: 5px;" class="text-lg-end" onsubmit="return false;">
                    <input type="text" id="userInput" placeholder="Leave blank to see all" maxlength="30" onkeyup="checkEnter(event)" autocomplete="off">
                    <select id="selectOption">
                        <option value="reservationID">Reservation ID</option>
                        <option value="staffName">Staff name</option>
                        <option value="serviceTitle">Service title</option>
                    </select>
                </form>

                <table class="table align-middle mb-0 bg-white table-hover">
                    <thead class="bg-light table-dark ">
                        <tr class="text-center">
                            <th>ID</th>
                            <th>Created Date</th>
                            <th>Reservation Date</th>
                            <th>Reservation Slot</th>
                            <th>Service</th>
                            <th>Staff Name</th>
                            <th>Total Cost</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody id="reservationTable">

                    </tbody>
                </table>
                <%-- pagination --%>
                <nav aria-label="..." style="margin: 20px; text-align: center;">
                    <div class="d-flex justify-content-center align-items-center">
                        <button class="btn btn-outline-secondary btn-sm border-0" type="button" id="previousPageButton">&lt;&lt;</button>

                        <select class="pagination justify-content-center " name="pageOption" id="pageOption">
                        </select>
                        <button class="btn btn-outline-secondary btn-sm border-0" type="button" id="nextPageButton">&gt;&gt;</button>

                    </div>
                </nav>
            </section>

            <aside>
                <!-- Sidebar content goes here -->
            </aside>
        </main>

        <footer>
            <jsp:include page="layout/footer.jsp"/>
        </footer>
        <script>
            let totalPagePagination = <%=reservationDAO.getTotalPagination(userID,5)%>;
            const reservationPerPage = 5;
            let pageNumber = <%=myReservationPage%>;
            let action = "viewAll";
            // Variable using for search
            let userInput = null;
            let selectedOption = null;

            const selectElement = document.getElementById('pageOption');
            selectElement.textContent = "";
            <% for (int i = 1;i<= reservationDAO.getTotalPagination(userID,5);i++) { %>
            var option = document.createElement("option");
            option.className = "text-lg-start";
            option.textContent = "Page <%=i%>";
            selectElement.appendChild(option);
            <% } %>

            //  Check if user enter
            function checkEnter(event) {
                if (event.keyCode === 13) { // Enter key pressed
                    processInput();
                }
            }
            function processInput() {
                userInput = document.getElementById("userInput").value;
                if (userInput.trim() === "") {
                    action = "viewAll";
                    totalPagePagination = <%=reservationDAO.getTotalPagination(userID,5)%>;
                    pageNumber = 1;
                    selectElement.textContent = "";
            <% for (int i = 1;i<= reservationDAO.getTotalPagination(userID,5);i++) { %>
                    var option = document.createElement("option");
                    option.className = "text-lg-start";
                    option.textContent = "Page <%=i%>";
                    selectElement.appendChild(option);
            <% } %>
                    getReservationsAndDisplayTable();
                    return;
                }
                selectedOption = document.getElementById("selectOption").value;
                action = "viewOthers";
                getSpecificPagination();
            }

            // Add event listner for previous page button
            document.getElementById("previousPageButton").addEventListener('click', function () {
                changePage(pageNumber - 1);
            });

            // Add event listner for next page button
            document.getElementById("nextPageButton").addEventListener('click', function () {
                changePage(pageNumber + 1);
            });

            // Add an event listener for the 'change' event on the select element
            selectElement.addEventListener('change', function () {
                // Get the index of the selected option
                const selectedIndex = selectElement.selectedIndex + 1;

                // Call the changePage function with the selected index
                changePage(selectedIndex);
            });

            function changePage(page) {
                if (page <= 0 || page > totalPagePagination) {
                    return;
                }
                pageNumber = page;
                getReservationsAndDisplayTable();
            }

            async function getSpecificPagination() {
                const url = "/ChildrenCare/myreservation?action=paginationNumber&condition=" + selectedOption + "&value=" + encodeURIComponent(userInput);

                const response = await fetch(url, {
                    method: "POST",
                });

                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }

                number = await response.text();
                if (number == 0) {
                    alert("There are nothing like that");
                } else {
                    totalPagePagination = parseInt(number);
                    pageNumber = 1;
                    selectElement.textContent = "";
                    for (let i = 1; i <= totalPagePagination; i++) {
                        var option = document.createElement("option");
                        option.className = "text-lg-start";
                        option.textContent = "Page " + i;
                        selectElement.appendChild(option);
                    }
                    getReservationsAndDisplayTable();
                }
            }

            async function getReservationsAndDisplayTable() {
                let url = null;
                if (action === "viewAll") {
                    url = "/ChildrenCare/myreservation?page=" + pageNumber;

                } else {
                    url = "/ChildrenCare/myreservation?page=" + pageNumber + "&condition=" + selectedOption + "&value=" + encodeURIComponent(userInput);
                }


                const response = await fetch(url, {
                    method: "POST",
                });

                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }

                const reservationsData = await response.text();

                const table = document.getElementById("reservationTable");

                // Get the table element
                const temp = document.querySelector(".table");

                // Get the tbody element
                const tbody = temp.querySelector("#reservationTable");

                // Remove all child elements of the tbody element
                tbody.innerHTML = "";

                // Enable the next and previous button
                if (pageNumber === 1 && pageNumber === totalPagePagination) {
                    document.getElementById("previousPageButton").disabled = true;
                    document.getElementById("nextPageButton").disabled = true;
                } else if (pageNumber === 1) {
                    document.getElementById("previousPageButton").disabled = true;
                    document.getElementById("nextPageButton").disabled = false;
                } else if (pageNumber === totalPagePagination) {
                    document.getElementById("previousPageButton").disabled = false;
                    document.getElementById("nextPageButton").disabled = true;
                } else {
                    document.getElementById("previousPageButton").disabled = false;
                    document.getElementById("nextPageButton").disabled = false;
                }
                selectElement.selectedIndex = pageNumber - 1;


                const reservationLines = reservationsData.split("\n");

                reservationLines.forEach((line) => {
                    const reservationAttributes = line.split(",");
                    if (reservationAttributes.length === 8) {
                        const row = document.createElement("tr");
                        row.classList.add("text-center");

                        reservationAttributes.forEach((attribute, index) => {
                            const cell = document.createElement("td");

                            if (index === 0) {
                                // First attribute: Create an <a> element with href
                                const link = document.createElement("a");
                                link.href = `reservation?id=` + attribute;
                                link.textContent = attribute;
                                cell.appendChild(link);
                            } else if (index === 7) {
                                const paragraph = document.createElement("span");
                                switch (attribute) {
                                    case "done" :
                                        paragraph.classList.add("badge", "bg-success", "rounded-pill");
                                        break;
                                    case "cancel" :
                                        paragraph.classList.add("badge", "bg-warning", "rounded-pill");
                                        break;
                                    case "pending" :
                                        paragraph.classList.add("badge", "bg-secondary", "rounded-pill");
                                        break;
                                    case "awaiting confirmation" :
                                        paragraph.classList.add("badge", "bg-info", "rounded-pill");
                                        break;
                                    case "waiting for examination" :
                                        paragraph.classList.add("badge", "bg-primary", "rounded-pill");
                                        break;
                                    default :
                                        paragraph.classList.add("badge", "bg-primary", "rounded-pill");
                                        break;
                                }
                                paragraph.textContent = attribute;
                                cell.appendChild(paragraph);

                            } else {
                                const paragraph = document.createElement("p");
                                paragraph.classList.add("fw-normal", "mb-1");
                                paragraph.textContent = attribute;
                                cell.appendChild(paragraph);
                            }

                            row.appendChild(cell);
                        });
                        table.appendChild(row);
                    }
                });
            }

            window.onload = getReservationsAndDisplayTable();
        </script>    
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

    </body>
</html>