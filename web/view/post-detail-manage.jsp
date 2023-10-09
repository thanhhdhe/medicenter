<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : newjsp
    Created on : Sep 17, 2023, 6:27:42 PM
    Author     : quanh
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "model.*" %>
<%@page import = "Database.*" %>
<%@page import = "java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Raleway:300,300i,400,400i,500,500i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
            crossorigin="anonymous"
            />
        <link rel="stylesheet" href="./resources/css/post-style.css">
        <title>Medilab</title>
    </head>
    <body>
        <jsp:include page="./layout/Header.jsp" />
        <!-- Blog Start -->
        <div class="container py-5" id="blog-list">
            <div class="row g-5" >
                <div class="col-lg-8">
                    <!-- Blog Detail Start -->
                    <form action="postDetailManage" method="Post">

                        <div class="mb-5">
                            <div class="form-group mt-3">
                                <label for="Thumbnail">Post Thumbnail:</label>
                                <input type="text" class="form-control" id="Thumbnail" name="Thumbnail" value="${post.getThumbnail()}">
                            </div>
                            <img class="img-fluid w-100 rounded mb-5" src="${post.getThumbnail()}" alt="thumbnail">

                            <div>
                                <div class="d-flex align-items-baseline">
                                    <p class="text-muted me-2">ID: </p>
                                    <input class="form-control text-muted" type="text" name="postID" value="${post.getPostID()}" readonly  />
                                </div>
                                <div class="d-flex align-items-baseline">
                                    <p class="text-muted me-2">Title: </p>
                                    <input class="form-control" type="text" name="Title" value="${post.getTitle()}"  />

                                </div>
                                <c:if test="${! empty errorMessage1}">
                                    <h5  class="text-danger text-start fw-bold">${errorMessage1}</h5>
                                </c:if>
                                <div>
                                    <p class="text-muted">Brief: </p>
                                    <textarea class="form-control text-muted" rows="4" cols="50" name="Brief" value="${post.getBriefInfo()}">${post.getBriefInfo()}</textarea>
                                </div>
                                <c:if test="${! empty errorMessage2}">
                                    <h5  class="text-danger text-start fw-bold">${errorMessage2}</h5>
                                </c:if>
                            </div>
                            <div class="row">
                                <div class="col-md-4">
                                    <c:if test="${post.isStatusPost()}">

                                        <select class="form-select text-primary mt-3 mb-4 w-75" name="status" >
                                            <option value="true" selected>Active</option>
                                            <option value="false">Inactive</option>
                                        </select>
                                    </c:if>
                                    <c:if test="${!post.isStatusPost()}">

                                        <select class="form-select text-primary mt-3 mb-4 w-75" name="status" >
                                            <option value="false">Inactive</option>
                                            <option value="true" selected>Active</option>
                                        </select>
                                    </c:if>
                                </div>
                                <div class="col-md-4">
                                    <select class="form-select text-primary mt-3 mb-4 w-75" name="postCategory"  class="form-select">
                                        <c:forEach var="c" items="${categoryList}">
                                            <option value="${c}">${c}</option>
                                        </c:forEach>
                                    </select>
                                </div>   
                            </div>
                            <div>
                                <p class="text-muted">Content: </p>
                                <textarea class="form-control text-muted" rows="6" cols="50" value="${post.getContent()}" name="Content">${post.getContent()}</textarea>
                            </div>
                            <c:if test="${! empty errorMessage2}">
                                <h5  class="text-danger text-start fw-bold">${errorMessage3}</h5>
                            </c:if>

                            <div class="d-flex justify-content-center">
                                <input class="btn btn-primary mt-3 w-25" type="submit" value="Update" />
                            </div>

                            <div class="d-flex justify-content-between bg-light rounded p-4 mt-4 mb-4">
                                <div class="d-flex align-items-center">
                                    <img class="rounded-circle me-2 avatar" src="${avatar}" alt="">
                                    <span>${author}</span>
                                </div>
                            </div>
                        </div>
                    </form>
                    <!-- Blog Detail End -->
                </div>
            </div>
        </div>

        <!-- Blog End -->
        <jsp:include page="layout/footer.jsp" />
    </body>
</html>

