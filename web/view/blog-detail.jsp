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
        <title>Medilab</title>
    </head>
    <body>
        <jsp:include page="./layout/Header.jsp" />
        <!-- Blog Start -->
        <div class="container py-5" id="blog-list">
            <div class="row g-5" >
                <div class="col-lg-8">
                    <!-- Blog Detail Start -->
                    <h1>${notFound}</h1>
                        <div class="mb-5">
                            <img class="img-fluid w-100 rounded mb-5" src="${thumbnail}" alt="thumbnail">
                            <h1 class="mb-4">${title}</h1>
                            <h2>${categoryOfPost}</h2>
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
                </div>
                <!-- Sidebar Start -->
                <div class="col-lg-4">
                    <!-- Category Start -->
                    <div class="mb-5">
                        <%PostDAO postDAO = new PostDAO();%>
                        <form action="blogPage">
                            <input id="post-title" type="text" name="postTitle" placeholder="Search" class="form-select text-primary mt-3 search"/>
                            <select class="form-select text-primary mt-3" name="postCategory">
                                <option selected value="">Post Category</option>
                                <%List<String> categoryPostList = postDAO.allCategoryPost();
            for (String categoryPost : categoryPostList) {%>
                                <option value="<%=categoryPost%>"><%=categoryPost%></option>
                                <%}%>
                            </select>
                        </form>
                        <br>
                        <a href="service?event=to-contact-link" class="mt-3 ms-2">Contact Us</a>
                    </div>
                    <!-- Category End -->
                </div>
                <!-- Sidebar End -->
            </div>
        </div>
        <!-- Blog End -->
        <jsp:include page="layout/footer.jsp" />
    </body>
</html>

