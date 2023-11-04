<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Reservation Detail</title>
        <link rel="stylesheet" href="./resources/css/reservation-detail-style.css">
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <link href="./resources/css/style.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="layout/Header.jsp"/>
        <%@ page import="java.sql.Date"%>
        <%@ page import="java.util.List,java.util.ArrayList,model.Service,model.Staff,model.Children" %>
        <% List<Integer> Workday = (List<Integer>) request.getAttribute("Workday");
           List<Integer> fullDay = (List<Integer>) request.getAttribute("fullDay");
           
           String ChildID = (String) request.getAttribute("ChildID");
           Service service = (Service) request.getAttribute("service");
           Staff staff = (Staff) request.getAttribute("Staff");
           String staffID = null;
           if (staff != null) {
                staffID = staff.getStaffID() + "";
           }
           Date dateChange = (Date) request.getAttribute("dateChange");
           int reservationID = -1;
           if (dateChange != null) {
                reservationID = (int) request.getAttribute("reservationID");
           }
        %>
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                    <h1><%=service.getTitle()%></h1>
                    <% if (staff != null) {%>
                    <div class="staff-info" style="display: flex;align-items: center;gap: 16px;">
                        <img style="width: 128px;height: 128px;border-radius: 50%;" src="<%=staff.getProfileImage()%>" alt="staff profile image"/>
                        <p style="margin: 0;font-weight: bold;">Staff name : <%=staff.getFullName()%> </p>
                    </div> <% } %>
                    <p>Service Details: <%=service.getServiceDetail()%></p>
                    <p>Brief Info: <%=service.getBrief()%></p>
                </div>
                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
                    <!-- Timetable code -->
                    <div class="title">
                        <h2>Choose a Date for Examination</h2>
                    </div>
                    <div class="row justify-content-center">
                        <div class="text-center">
                            <button id="previousMonth" type="button" class="previousmonth-button" disabled></button>
                            <div class="time" id="time"></div>
                            <button id="nextMonth" type="button" class="nextmonth-button"></button>
                        </div>
                    </div>

                    <div id="schedule-table"> 

                    </div>

                    <div id="time-slot-div">

                    </div>
                    <button id="btnCheckOut" disabled onclick="checkout()">Check out</button>
                </div>
            </div>  
        </div>

        <script>
            // Declare time slot hashmap
            const timeSlots = [
                "7:00 - 8:00",
                "8:00 - 9:00",
                "9:00 - 10:00",
                "10:00 - 11:00",
                "14:00 - 15:00",
                "15:00 - 16:00"
            ];

            // Determine the day of week of that day
            const currentDate = new Date();
            const realMonth = currentDate.getMonth(); // +1 to get the real month
            let currentMonth = currentDate.getMonth(); // Variable to store the month that user selected ( default : current month )
            let currentYear = currentDate.getFullYear(); // Variable to store the year that user selectect ( default : current year )
            // JS Automatically subtract 1 day, setting the date to the last day of the previous month
            let daysInMonth = new Date(currentYear, currentMonth + 1, 0).getDate(); 
            // Get the first day of the month
            let firstDayOfMonth = new Date(currentYear, currentMonth, 1).getDay();
            let workSlots = null;
            let bookedSlots = null;
            let selfBooked = null;
            let Workday = <%=Workday%>; // Variable store list of day is workday
            let fullDay = <%=fullDay%>; // Variable store list of day that is full
            const childID = <%=ChildID%>;

            // Variable store selected date and selected slot
            let selectedDate = null; // p
            let selectedMonth = currentMonth + 1; // int
            let selectedSlot = null; // int
            // initial
            document.getElementById("time").textContent = currentMonth + 1 + " / " + currentYear;
            document.getElementById("nextMonth").addEventListener("click", function () {
                // Increase the month
                changeMonth(realMonth + 2);
            });
            // add the event listener of previous month button
            document.getElementById("previousMonth").addEventListener("click", function () {
                changeMonth(realMonth + 1);
            });

            // ==============Process for update reservation=====================
            <% if (dateChange != null) { %>
            // Variable store the day of the date that be saved with the reservation
            let storedDay = <%=dateChange.getDate()%>;
            // Variable store the month of the date that be saved with the reservation
            let storedMonth = <%=dateChange.getMonth() + 1%>;
            // Variable store the year of the date that be saved with the reservation
            let storedYear = <%=dateChange.getYear() + 1900%>;
            var checkoutButton = document.getElementById("btnCheckOut");
            checkoutButton.textContent = "Update";
            checkoutButton.removeAttribute("onclick");
            checkoutButton.addEventListener('click', function () {
                updateReservation();
            });
            function updateReservation() {
                if (selectedDate && selectedSlot) {
                    // Get the selected date and time slot
                    const selectedDateValue = selectedDate.textContent;
                    // Check not null
                    if (selectedDateValue === null) {
                        return;
                    }
                    const selectedSlotValue = selectedSlot.textContent;
                    if (selectedSlotValue === null) {
                        return;
                    }
                    // Check the right condition to send the signal to the servlet
                    if (workSlots.includes(timeSlots.indexOf(selectedSlotValue) + 1) && !bookedSlots.includes(timeSlots.indexOf(selectedSlotValue) + 1)
                            && Workday.includes(parseInt(selectedDateValue, 10)) && !fullDay.includes(parseInt(selectedDateValue, 10))) {
                        // Send date and time slot information to the servlet ...
                        const xhr = new XMLHttpRequest();
                        const serviceID = <%=service.getServiceID()%>;
                        const staffID = <%=staffID%>;
                        let updateUrl = "reservationdetailcontroller?selectedDate=" + selectedDate.textContent + "&selectedMonth=" + (currentMonth + 1) + "&selectedYear=" + currentYear + "&staffID=" + staffID + "&action=update&slot=" + (timeSlots.indexOf(selectedSlotValue) + 1) + "&reservationID=" + <%=reservationID%>;
                        xhr.open("GET", updateUrl, true);
                        xhr.onreadystatechange = function () {
                            if (xhr.readyState === 4 && xhr.status === 200) {
                                const responseText = xhr.responseText; // Text string from response
                                if (responseText === "Choose date again") {
                                    alert("This slot is no longer available. Please choose again.");
                                } else {
                                    alert("Successfully update the reservation");
                                    const url = "reservation?id=" + <%=reservationID%>;
                                    window.location.href = url;
                                }
                            }
                        };
                        xhr.send();
                    }
                }
            }
            <% } %>
            // ====================================================================
            // Create a timetable for scheduling
            function generateTimetable() {
                const staffID = <%=staffID%>;
                const scheduletable = document.getElementById("schedule-table");
                let name = "schedule-table";
                let html = "<table class=" + name + "><tr>";

                // Create header row
                const daysOfWeek = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
                for (let day of daysOfWeek) {
                    html += "<th>" + day + "</th>";
                }
                html += "</tr><tr>";

                // Fill in the days of the month
                let dayCount = 1;
                // Status of the day
                let not_work = "not_work";
                let work = "work";
                let full = "full";
                
                for (let i = 0; i < 42; i++) { // 6 rows of 7 days
                    if (i < firstDayOfMonth || dayCount > daysInMonth) {
                        html += '<td><p class=empty></td>';
                    } else {
                        if (staffID !== null) {
                            // Check whether day in the past or in working day or not
                            if (currentDate.getHours() >= 16 && currentDate.getDate() === dayCount && (currentDate.getMonth() + 1) === selectedMonth && currentDate.getFullYear() === currentYear) {
                                html += "<td><p title='This date is not available for booking' class=" + not_work + ">" + dayCount + "</p></td>";
                            } else if (Workday.includes(dayCount) && (dayCount >= currentDate.getDate() || currentMonth > currentDate.getMonth())) {
                                // Check whether dayCount is in the list of day that full
                                if (fullDay.includes(dayCount)) {
                                    html += "<td><p title='This date is fully booked' class=" + full + ">" + dayCount + "</p></td>";
                                } else {
                                    html += "<td><p title='This date is available for booking' class=" + work + " onclick='selectDate(this)'>" + dayCount + "</p></td>";
                                }
                            } else {
                                html += "<td><p title='This date is not available for booking' class=" + not_work + ">" + dayCount + "</p></td>";
                            }
                        } else {
                            if (currentDate.getHours() >= 16 && currentDate.getDate() === dayCount && (currentDate.getMonth() + 1) === selectedMonth && currentDate.getFullYear() === currentYear) {
                                html += "<td><p title='This date is not available for booking' class=" + not_work + ">" + dayCount + "</p></td>";
                            } else if (fullDay.includes(dayCount) && (dayCount >= currentDate.getDate() || currentMonth > currentDate.getMonth())) {
                                html += "<td><p title='This date is fully booked' class=" + full + ">" + dayCount + "</p></td>";
                            } else if (Workday.includes(dayCount) && (dayCount >= currentDate.getDate() || currentMonth > currentDate.getMonth())) {
                                html += "<td><p title='This date is available for booking' class=" + work + " onclick='selectDate(this)'>" + dayCount + "</p></td>";
                            } else {
                                html += "<td><p title='This date is not available for booking' class=" + not_work + ">" + dayCount + "</p></td>";
                            }
                        }
                        dayCount++;
                    }
                    // 
                    if (i % 7 === 6) {
                        html += "</tr><tr>";
                    }
                }
                // Get down the line
                html += "</tr></table>";
                // Assign value to scheduletable
                scheduletable.innerHTML = html;
            }

            // Function to handle when the user clicks on a date
            function selectDate(day) {
                if (selectedDate) {
                    // Reset the previous day's background color (if any)
                    selectedDate.style.backgroundColor = "";
                }

                // Save the selected date and change the background color
                selectedDate = day;
                selectedDate.style.backgroundColor = "deepskyblue";

                // Create and display time slot buttons
                selectedSlot = null;
                btnCheckOut.disabled = true;

                if (Workday.includes(parseInt(selectedDate.textContent, 10)) && !fullDay.includes(parseInt(selectedDate.textContent, 10))) {
                    generateTimeSlots();
                }
            }

            // Function to create and display time slot buttons
            function generateTimeSlots() {

                const timeSlotDiv = document.getElementById("time-slot-div");
                timeSlotDiv.innerHTML = "";
                const title = document.createElement("h2");
                title.textContent = "Select a slot";
                title.className = "title";
                timeSlotDiv.appendChild(title);

                // Match data to each slot
                fetchSlotStatus(timeSlotDiv);
            }

            function fetchSlotStatus(timeSlotDiv) {
                const xhr = new XMLHttpRequest();
                const serviceID = <%=service.getServiceID()%>;
                let url = null;
                let staffID = null;
            <% if (staffID != null) { %>
                staffID = <%=staffID%>;
                url = "reservationdetailcontroller?selectedDate=" + selectedDate.textContent + "&selectedMonth=" + selectedMonth + "&selectedYear=" + currentYear + "&staffID=" + staffID + "&action=checkSlot&ChildID=" + childID;
            <% } else { %>
                url = "reservationdetailcontroller?selectedDate=" + selectedDate.textContent + "&selectedMonth=" + selectedMonth + "&selectedYear=" + currentYear + "&staffID=all&action=checkSlotForService&serviceID=" + serviceID + "&ChildID=" + childID;
            <% } %>
                xhr.open("GET", url, true);

                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        const responseText = xhr.responseText; // Text string from response
                        // This contain two part, part one is work slot and part two is booked slot
                        const tempStringOne = responseText.split("&");
                        // Split the String separated by commas
                        workSlots = tempStringOne[0].split(',').map(item => parseInt(item, 10));
                        bookedSlots = tempStringOne[1].split(',').map(item => parseInt(item, 10));
                        selfBooked = tempStringOne[2].split(',').map(item => parseInt(item, 10));
                        // Check the received number sequence and change the class of the slots
                        for (let i = 0; i < timeSlots.length; i++) {
                            const slot = document.createElement("div");
                            slot.textContent = timeSlots[i];

                            // Check if time slot is in the past
                            if (parseInt(selectedDate.textContent, 10) === currentDate.getDate()) {
                                if (currentDate.getHours() >= 7 && i === 0) {
                                    slot.className = "not_work";
                                    slot.title = "This slot is not available to book";
                                    timeSlotDiv.appendChild(slot);
                                    continue;
                                } else if (currentDate.getHours() >= 8 && i === 1) {
                                    slot.className = "not_work";
                                    slot.title = "This slot is not available to book";
                                    timeSlotDiv.appendChild(slot);
                                    continue;
                                } else if (currentDate.getHours() >= 9 && i === 2) {
                                    slot.className = "not_work";
                                    slot.title = "This slot is not available to book";
                                    timeSlotDiv.appendChild(slot);
                                    continue;
                                } else if (currentDate.getHours() >= 10 && i === 3) {
                                    slot.className = "not_work";
                                    slot.title = "This slot is not available to book";
                                    timeSlotDiv.appendChild(slot);
                                    continue;
                                } else if (currentDate.getHours() >= 14 && i === 4) {
                                    slot.className = "not_work";
                                    slot.title = "This slot is not available to book";
                                    timeSlotDiv.appendChild(slot);
                                    continue;
                                } else if (currentDate.getHours() >= 15 && i === 5) {
                                    slot.className = "not_work";
                                    slot.title = "This slot is not available to book";
                                    timeSlotDiv.appendChild(slot);
                                    continue;
                                }
                            }
                            // Check it condition to class slot
                            if (selfBooked.includes(i + 1)) {
                                slot.className = "selfbooked";
                                slot.title = "This slot has been booked for this children";
                            } else if (bookedSlots.includes(i + 1)) {
                                slot.className = "full";
                                slot.title = "This slot has been booked";
                            } else if (workSlots.includes(i + 1)) {
                                slot.className = "work";
                                slot.title = "You can book this slot";
                                slot.addEventListener("click", function () {
                                    selectTimeSlot(slot, i + 1);
                                });
                            } else {
                                slot.title = "This slot is not available to book";
                                slot.className = "not_work";
                            }
                            timeSlotDiv.appendChild(slot);
                        }
                    }
                };
                xhr.send();
            }

            // Function to handle when the user clicks on a time slot
            function selectTimeSlot(slot, index) {
                if (selectedSlot) {
                    // Reset the background color of the previous time slot (if any)
                    selectedSlot.style.backgroundColor = "";
                }

                // Save the selected time slot and change the background color
                selectedSlot = slot;
                selectedSlot.style.backgroundColor = "deepskyblue";

                // Check the condition that selected slot must be in work slot and not in booked slot
                if (workSlots.includes(index) && !bookedSlots.includes(index)) {
                    // Turn on the "Check out" button
                    const btnCheckOut = document.getElementById("btnCheckOut");
                    btnCheckOut.disabled = false;
                }
            }

            // Function to handle when the user clicks the "Check out" button
            function checkout() {
                if (selectedDate && selectedSlot) {
                    // Get the selected date and time slot
                    const selectedDateValue = selectedDate.textContent;
                    // Check not null
                    if (selectedDateValue === null) {
                        return;
                    }
                    const selectedSlotValue = selectedSlot.textContent;
                    if (selectedSlotValue === null) {
                        return;
                    }
                    // Check the right condition to send the signal to the servlet
                    if (workSlots.includes(timeSlots.indexOf(selectedSlotValue) + 1) && !bookedSlots.includes(timeSlots.indexOf(selectedSlotValue) + 1)
                            && Workday.includes(parseInt(selectedDateValue, 10)) && !fullDay.includes(parseInt(selectedDateValue, 10))) {
                        // Send date and time slot information to the servlet ...
                        const xhr = new XMLHttpRequest();
                        const serviceID = <%=service.getServiceID()%>;
                        const staffID = <%=staffID%>;
                        let checkOutURL = null;
                        if (staffID === null || staffID === 'all') {
                            checkOutURL = "reservationdetailcontroller?selectedDate=" + selectedDate.textContent + "&selectedMonth=" + (currentMonth + 1) + "&selectedYear=" + currentYear + "&staffID=all&action=save&serviceID=" + <%=service.getServiceID()%> + "&slot=" + (timeSlots.indexOf(selectedSlotValue) + 1) + "&ChildID=" + childID;

                        } else {
                            checkOutURL = "reservationdetailcontroller?selectedDate=" + selectedDate.textContent + "&selectedMonth=" + (currentMonth + 1) + "&selectedYear=" + currentYear + "&staffID=" + staffID + "&action=save&serviceID=" + <%=service.getServiceID()%> + "&slot=" + (timeSlots.indexOf(selectedSlotValue) + 1) + "&ChildID=" + childID;
                        }
                        xhr.open("GET", checkOutURL, true);
                        xhr.onreadystatechange = function () {
                            if (xhr.readyState === 4 && xhr.status === 200) {
                                const responseText = xhr.responseText; // Text string from response
                                if (responseText === "Duplicate reservation") {
                                    alert("Duplicate reservation. Please choose again.");
                                } else if (responseText === "Choose date again") {
                                    alert("This slot is no longer available. Please choose again.");
                                } else if (responseText === "Double book at one time") {
                                    alert("This children cannot duplicate slots with other services. Please choose again.");
                                } else if (responseText === "Exceed the limit") {
                                    alert("You have exceeded the limit for making more reservations.");
                                } else {
                                    const url = "reservation?action=confirm&reservationID=" + responseText;
                                    window.location.href = url;
                                }
                            }
                        };
                        xhr.send();
                    }
                }
            }

            // Function to send signal to servlet and receive data
            function changeMonth(month) {
                // Reset value
                selectedDate = null;
                selectedSlot = null;
                workSlots = null;
                // For service scheduling only
                bookedSlots = null;

                // Validate the month, the month only can be the current month or the next month
                if (month !== (currentDate.getMonth() + 1) && month !== (currentDate.getMonth() + 2)) {
                    return;
                }

                // Set the right property of the variable
                if (month === 13) {
                    month = 1;
                    currentMonth = 0;
                    selectedMonth = 1;
                    currentYear = currentYear + 1;
                } else if (month === 0) {
                    month = 12;
                    currentMonth = 11;
                    selectedMonth = 12;
                    currentYear = currentYear - 1;
                } else {
                    currentMonth = month - 1;
                    selectedMonth = month;
                }

                // Show the right month and year
                document.getElementById("time").textContent = month + " / " + currentYear;


                daysInMonth = new Date(currentYear, currentMonth + 1, 0).getDate();
                firstDayOfMonth = new Date(currentYear, currentMonth, 1).getDay();

                // Check the condition to enable previous or next button
                if (month === (currentDate.getMonth() + 1)) {
                    document.getElementById("previousMonth").disabled = true;
                    document.getElementById("nextMonth").disabled = false;
                } else if (month === (currentDate.getMonth() + 2)) {
                    document.getElementById("previousMonth").disabled = false;
                    document.getElementById("nextMonth").disabled = true;
                }


                const xhr = new XMLHttpRequest();
                const serviceID = <%=service.getServiceID()%>;
                const staffID = <%=staffID%>;
                let urlChangeMonth = "";
                if (staffID === null) {
                    urlChangeMonth = "reservationdetailcontroller?selectedDate=null&selectedMonth=" + (currentMonth + 1) + "&selectedYear=" + currentYear + "&staffID=all&action=changeMonth&serviceID=" + serviceID;
                } else {
                    urlChangeMonth = "reservationdetailcontroller?selectedDate=null&selectedMonth=" + (currentMonth + 1) + "&selectedYear=" + currentYear + "&staffID=" + staffID + "&action=changeMonth&serviceID=null";
                }
                xhr.open("GET", urlChangeMonth, true);

                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        const responseText = xhr.responseText; // Text string from response

                        // This contain two part, part one is work day and part two is full day
                        const tempStringTwo = responseText.split("&");

                        // Split the String separated by commas
                        Workday = tempStringTwo[0].split(',').map(item => parseInt(item, 10));
                        fullDay = tempStringTwo[1].split(',').map(item => parseInt(item, 10));

                        // Disable check out button
                        const btnCheckOut = document.getElementById("btnCheckOut");
                        btnCheckOut.disabled = true;

                        // Reset the content of time slot and schedule table
                        document.getElementById("time-slot-div").textContent = "";
                        document.getElementById("schedule-table").textContent = "";

                        generateTimetable();
                    }
                };
                xhr.send();
            }

            // Initial generation of the timetable when the page loads
            window.onload = generateTimetable();
        </script>
        <br><br><br><br><br><br>
        <jsp:include page="layout/footer.jsp"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

    </body>
</html>
