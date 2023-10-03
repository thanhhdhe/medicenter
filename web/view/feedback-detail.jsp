<%-- 
    Document   : feedback-detail
    Created on : 28-Sep-2023, 21:28:47
    Author     : pc
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "model.*" %>
<%@page import = "Database.*" %>
<%@page import = "java.util.*" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Feedback Details</title>
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
        <jsp:include page="../view/layout/Header.jsp" />
        <div style="background-color: #e1dada">
            <div class="container pb-5">
                <h1 class="mb-4">Feedback Details</h1>
                <div>
                    <c:set var="fe" value="${requestScope.feedbackdetail}" />
                    <div class="card">
                        <div class="card-header">
                            Contact Information
                        </div>
                        <div class="card-body">
                            <h5 class="card-title">${fe.getFullName()}</h5>
                            <p class="card-text">${fe.getUserEmail()}</p>
                            <p class="card-text">${fe.getFPhone()}</p>
                        </div>
                    </div>

                    <div class="card mt-4 ">
                        <div class="card-header">
                            Feedback Information
                        </div>
                        <!-- get name service  -->

                        <c:set var="ser" value="${requestScope.ser}" />
                        <div class="card-body">
                            <!-- Status -->
                            <c:set var="FDid" value="${requestScope.FDid}" />
                            <div class="dropdown">
                                <button style="border: 0px; background-color: #ffffff;" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                                    <span class="badge bg-primary"  id="statusBadge-${FDid}">${fe.getFstatus()}</span>
                                </button>
                                <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                                    <li><a class="dropdown-item status-change" href="#" onclick="changestatus(this, ${FDid})">Appored</a></li>
                                    <li><a class="dropdown-item status-change" href="#" onclick="changestatus(this, ${FDid})">Unresolved</a></li>
                                    <li><a class="dropdown-item status-change" href="#" onclick="changestatus(this, ${FDid}})">Resolved</a></li>
                                    <li><a class="dropdown-item status-change" href="#" onclick="changestatus(this, ${FDid})">Reviewed</a></li>
                                </ul>

                            </div>
                            <div class="d-flex justify-content-between">
                                <div>
                                    <h5 class="card-title">Service: ${ser.getTitle()}</h5>
                                    <c:forEach var="star" begin="1" end="${fe.getRatestar()}" step="1">
                                        <img style="width: 24px; height: 12px" src="./resources/img/icon/star.png" alt="alt"/>
                                    </c:forEach>
                                    <p class="card-text">Feedback:</p>
                                    <p class="card-text">${fe.getContent()}</p>
                                    <div style="width: 50px; height: 30px; border: solid 1px;
                                         display: flex; justify-content: center;
                                         border-radius: 5px; background-color: #e1dada; color: white">
                                        <a style="text-decoration: none;" href="/ChildrenCare/feedback?action=accessfeedback">Home</a>
                                    </div>
                                </div>


                                <div style="margin-right: 100px; width: 400px;height: 300px;">
                                    <img style="object-fit: contain; height: 100%"  src="${ser.getThumbnail()}" alt="alt"/>

                                </div>
                            </div>

                        </div>
                    </div>


                    <!-- Thêm hình ảnh nếu cần -->

                </div>
            </div>
        </div>
        <jsp:include page="layout/footer.jsp" />
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
        <script>
                                        function changestatus(a, uid) {
                                            var text = a.textContent;
                                            var textchange = document.getElementById("statusBadge-" + uid);
                                            textchange.textContent = text;

                                            // Gửi yêu cầu Ajax đến servlet
                                            var xhr = new XMLHttpRequest();
                                            xhr.open("POST", "feedback?event=updatestatus&Fstatus=" + text + "&UID=" + uid, true);

                                            xhr.onload = function () {

                                            };

                                            xhr.onerror = function () {

                                            };

                                            xhr.send();
                                        }
        </script>

        <!-- Thêm các tệp JS của Bootstrap 5.0.2 tại đây (nếu cần) -->
        <script src="path-to-bootstrap-js/bootstrap.bundle.min.js"></script>
    </body>
</html>

