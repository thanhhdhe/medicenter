<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : post-list-manage
    Created on : Sep 26, 2023, 11:11:37 AM
    Author     : quanh
--%>
<%@page import = "model.*" %>
<%@page import = "Database.*" %>
<%@page import = "java.util.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <!-- Google Fonts -->
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
        <div class="mb-4 px-4 py-3 border-bottom d-flex justify-content-between align-items-center">
            <h4>Post MANAGEMENT</h4>
            <a href="postDetailManage?postId=-1&event=add" class="ms-text-primary font-weight-bold">Add Post</a>
        </div>
        <div class="d-flex justify-content-center mt-5">
            <div class="col-md-9" id="blog-list">
                <div class="container py-5">
                    <div class="row g-5">
                        <c:forEach var="l" items="${list}">
                            <div class="col-xl-4 col-lg-6">
                                <div class="bg-light rounded overflow-hidden">
                                    <img class="img-fluid w-100" src="${l.getThumbnail()}" alt="">
                                    <div class="p-4">
                                        Post ID: ${l.getPostID()}
                                        <a class="h3 d-block mb-3" href="/ChildrenCare/blogDetail?ID=${l.getPostID()}">${l.getTitle()}</a>
                                        <p class="m-0">${l.getBriefInfo()}</p>
                                        <div class="d-flex h-50 align-content-center flex-wrap d-flex" >
                                            <form action="postManage" method="POST">
                                                <div>
                                                    <input type="text" name="postId" value="${l.getPostID()}" hidden=""/>
                                                    <c:if test="${l.isStatusPost()}">
                                                        <button class="button-icon me-2" value="hide" name="event"><img src="resources/img/icon/hide.png" alt="alt"/></button> Hide
                                                        </c:if>
                                                        <c:if test="${!l.isStatusPost()}">
                                                        <button class="button-icon me-2"value="show" name="event"><img src="resources/img/icon/visual.png" alt="alt"/></button> Show
                                                        </c:if>
                                                </div>
                                            </form>
                                            <div>
                                                <button class="button-icon me-2" value="update" name="event"><a href="postDetailManage?postId=${l.getPostID()}&event=update"><img src="resources/img/icon/pen.png" alt="alt"/></a></button>  Update
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </c:forEach>
                    </div>
                </div>
            </div>
            <form action="postManage">
                <div class="container row mt-5 mb-4">
                    <div class="mb-5">
                        Search by:
                        <input type="text" name="postTitle" placeholder="Search" class="form-select text-primary mt-3 search" value="${postTitle}"/>
                        <select class="form-select text-primary mt-3" name="postCategory">
                            <c:forEach var="c" items="${categoryList}">
                                <option value="${c}">${c}</option>
                            </c:forEach>
                        </select>
                        <select class="form-select text-primary mt-3" name="postAuthor">
                            <c:forEach var="a" items="${authorList}">
                                <option value="${a.getUserID()}">${a.getLastName()} ${a.getFirstName()}</option>
                            </c:forEach>
                        </select>
                        <br>
                        Sort by:
                        <select class="form-select text-primary mt-3" name="sortBy">
                            <c:forEach var="s" items="${sortList}">
                                <option value="${s}">${s}</option>
                            </c:forEach>
                        </select>
                        <br>
                        <a href="service?event=to-contact-link" class="mt-3 ms-2">Contact Us</a>
                    </div>
                </div>
        </div>
        <div class="d-flex justify-content-center" id="pagination-container">
            <c:forEach var="p" begin="1" end="${numOfPage}">
                <input type="submit" name="page" value="${p}" /> 
            </c:forEach>
        </form>
    </div>
    <jsp:include page="layout/footer.jsp" />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="./resources/js/posts-manage-script.js"></script>
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
</html>
