<!DOCTYPE html>
<html>
    <head>
        <title>Reservation Detail</title>
        <link rel="stylesheet" href="./resources/css/reservation-detail-style.css">
        <jsp:include page="layout/Head.jsp"/>
    </head>
    <body>
        <jsp:include page="layout/Header.jsp"/>

        <%@ page import="java.util.List,java.util.ArrayList,model.Service" %>
        <% List<Integer> WorkDay = (List<Integer>) request.getAttribute("WorkDay");
           Service service = (Service) request.getAttribute("service");
           String staffID = (String) request.getAttribute("staffID");
        %>
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                    <h1><%=service.getTitle()%></h1>
                    <img src="<%=service.getThumbnail()%>" alt="Service Thumbnail">
                    <p>Service Details: <%=service.getServiceDetail()%></p>
                    <p>Brief Info: <%=service.getBrief()%></p>
                </div>
                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
                    <!-- Timetable code -->
                    <h2 style="font-size: 40px;text-align: center;" class="Title">Choose a Date for Examination</h2>
                    <div id="schedule-table"> </div>
                    <div id="time-slot-div">
                        <!--Process the time-slot -->
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

            // Variable store selected date and selected slot
            let selectedDate = null;
            let selectedSlot = null;

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
                generateTimeSlots();
            }
            function fetchSlotStatus(timeSlotDiv, selectedDateValue, staffID) {
                const xhr = new XMLHttpRequest();
                const serviceID = <%=service.getServiceID()%>;
                const url = "SlotStatusServlet?day=" + selectedDate.textContent + "&selectedDate=" + selectedDateValue + "&staffID=" + staffID + "&serviceID=" + serviceID;

                xhr.open("GET", url, true);

                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        const responseText = xhr.responseText; // Text string from response

                        // split string into JavaScript array
                        const workSlots = responseText.split(",").map(slot => parseInt(slot, 10));

                        // Check the received number sequence and change the class of the slots
                        for (let i = 0; i < timeSlots.length; i++) {
                            const slot = document.createElement("div");
                            slot.textContent = timeSlots[i];

                            if (workSlots.includes(i + 1)) {
                                slot.className = "work";
                                slot.addEventListener("click", function () {
                                    selectTimeSlot(slot);
                                });
                            } else {
                                slot.className = "not_work";
                            }
                            timeSlotDiv.appendChild(slot);
                        }
                    }
                };

                xhr.send();
            }
            // Function to create and display time slot buttons
            function generateTimeSlots() {
                const timeSlotDiv = document.getElementById("time-slot-div");
                timeSlotDiv.innerHTML = "";
                const title = document.createElement("h2");
                title.textContent = "Select a slot";
                title.className = "title";
                timeSlotDiv.appendChild(title);

                // Determine the day of week of that day
                const currentDate = new Date();
                const currentMonth = currentDate.getMonth();
                const currentYear = currentDate.getFullYear();
                const selectedDateValue = getDayOfWeek(currentYear, currentMonth, selectedDate.textContent);

                fetchSlotStatus(timeSlotDiv, selectedDateValue, <%=staffID%>);
            }

            // Function to handle when the user clicks on a time slot
            function selectTimeSlot(slot) {
                if (selectedSlot) {
                    // Reset the background color of the previous time slot (if any)
                    selectedSlot.style.backgroundColor = "";
                }

                // Save the selected time slot and change the background color
                selectedSlot = slot;
                selectedSlot.style.backgroundColor = "deepskyblue";

                // Turn on the "Check out" button
                const btnCheckOut = document.getElementById("btnCheckOut");
                btnCheckOut.disabled = false;
            }

            // Function to handle when the user clicks the "Check out" button
            function checkout() {
                if (selectedDate && selectedSlot) {
                    // Get the selected date and time slot
                    const selectedDateValue = selectedDate.textContent;
                    if (selectedDateValue === null)
                        return;
                    const selectedSlotValue = selectedSlot.textContent;
                    if (selectedSlotValue === null)
                        return;

                    // Send date and time slot information to the servlet
                    const url = "ConservationContact?selectedDate=" + selectedDateValue + "&selectedSlot=" + selectedSlotValue;
                    window.location.href = url;
                }
            }

            function generateTimetable() {
                const currentDate = new Date();
                const currentMonth = currentDate.getMonth();
                const currentYear = currentDate.getFullYear();

                const daysInMonth = new Date(currentYear, currentMonth + 1, 0).getDate();
                const firstDayOfWeek = new Date(currentYear, currentMonth, 1).getDay();

                const scheduletable = document.getElementById("schedule-table");
                let name = "schedule-table";
                let html = "<table class=" + name + "><tr>";

                // Create header row
                const daysOfWeek = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
                for (let day of daysOfWeek) {
                    html += "<th>" + day + "</th>";
                }
                html += "</tr><tr>";
                const WorkDay = <%=WorkDay%>;

                // Fill in the days of the month
                let dayCount = 1;
                let not_work = "not_work";
                let work = "work";

                // FirstDayOfWeek = 4 because current month is September 
                for (let i = 0; i < 42; i++) { // 6 rows of 7 days
                    if (i < firstDayOfWeek || dayCount > daysInMonth) {
                        html += '<td><p class=empty></td>';
                    } else {
                        const DayOfWeek = getDayOfWeek(currentYear, currentMonth, dayCount);
                        // Check whether dayCount is working day or not
                        if (WorkDay.includes(DayOfWeek) && (dayCount >= currentDate.getDate())) {
                            html += "<td><p class=" + work + " onclick='selectDate(this)'>" + dayCount + "</p></td>";
                        } else {
                            html += "<td><p class=" + not_work + ">" + dayCount + "</p></td>";
                        }
                        dayCount++;
                    }
                    if (i % 7 === 6) {
                        html += "</tr><tr>";
                    }
                }

                html += "</tr></table>";
                scheduletable.innerHTML = html;
            }

            function getDayOfWeek(year, month, day) {
                // Convert month and day to YYYY-MM-DD
                const dateString = year + '-' + (month < 9 ? '0' : '') + (month + 1) + '-' + (day < 10 ? '0' : '') + day;

                // Create object date
                const date = new Date(dateString);

                // Convert to day of week (0 = Sunday, 1 = Monday, ..., 6 = Saturday)
                const dayOfWeek = date.getDay();

                return dayOfWeek;
            }

            // Initial generation of the timetable when the page loads
            window.onload = generateTimetable();
        </script>
        <br><br><br><br><br><br>
        <jsp:include page="layout/footer.jsp"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

    </body>
</html>
