<%-- 
    Document   : newjsp
    Created on : Sep 17, 2023, 6:27:42 PM
    Author     : quanh
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "model.*" %>
<%@page import = "Database.*" %>
<%@page import = "java.util.*" %>
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
        <title>Post Page</title>
    </head>
    <body>
        <jsp:include page="./layout/Header.jsp" />
        <!-- Blog Start -->
        <div class="container py-5">
            <div class="row g-5">
                <div class="col-lg-8">
                    <!-- Blog Detail Start -->
                    <div class="mb-5">
                        <img class="img-fluid w-100 rounded mb-5" src="${thumbnail}" alt="thumbnail">
                        <h1 class="mb-4">${title}</h1>
                        <p>
                            ${postdetail}
                        </p>
                        <div class="d-flex justify-content-between bg-light rounded p-4 mt-4 mb-4">
                            <div class="d-flex align-items-center">
                                <img class="rounded-circle me-2 avatar" src="${avatar}" alt="">
                                <span>${author}</span>
                            </div>
                            <div class="d-flex align-items-center">
                                <!--                            <span class="ms-3"><i class="far fa-eye text-primary me-1"></i>12345</span>
                                                            <span class="ms-3"><i class="far fa-comment text-primary me-1"></i>123</span>-->
                            </div>
                        </div>
                    </div>
                    <!-- Blog Detail End -->

                    <!-- Comment List Start -->
                    <div class="mb-5">
                        <h4 class="d-inline-block text-primary text-uppercase border-bottom border-5 mb-4">Comments</h4>
                        <div class="d-flex mb-4">
                            <img src="img/user.jpg" class="img-fluid rounded-circle" style="width: 45px; height: 45px;">
                            <div class="ps-3">
                                <h6><a href="">author</a> <small><i>date</i></small></h6>
                                <p>comment</p>
                                <button class="btn btn-sm btn-light">Reply</button>
                            </div>
                        </div>
                    </div>
                    <!-- Comment List End -->

                    <!-- Comment Form Start -->
                    <div class="bg-light rounded p-5">
                        <h4 class="d-inline-block text-primary text-uppercase border-bottom border-5 border-white mb-4">Leave a comment</h4>
                        <form>
                            <div class="row g-3">
                                <div class="col-12">
                                    <textarea class="form-control bg-white border-0" rows="5" placeholder="Comment"></textarea>
                                </div>
                                <div class="col-12">
                                    <button class="btn btn-primary w-100 py-3" type="submit">Leave Your Comment</button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <!-- Comment Form End -->
                </div>

                <!-- Sidebar Start -->
                <div class="col-lg-4">
                    <!-- Category Start -->
                    <div class="mb-5">
                        <%PostDAO postDAO = new PostDAO();%>
                        <form action="postDetail" method="POST">
                            <input id="post-title" type="text" name="postTitle" placeholder="Search" class="form-select text-primary mt-3"/>
<!--                            <select class="form-select text-primary mt-3" name="postCategory">
                                <option selected value="">Post Category</option>
                                <%List<String> categoryPostList = postDAO.allCategoryPost();
            for (String categoryPost : categoryPostList) {%>
                                <option value="<%=categoryPost%>"><%=categoryPost%></option>
                                <%}%>
                            </select>-->
                        </form>
                    </div>
                    <!-- Category End -->
                </div>
                <!-- Sidebar End -->
            </div>
        </div>
        <!-- Blog End -->

    </body>
</html>

