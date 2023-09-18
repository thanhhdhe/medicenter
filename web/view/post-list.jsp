<%-- 
    Document   : newjsp
    Created on : Sep 17, 2023, 6:27:42 PM
    Author     : quanh
--%>
<%@page import = "model.*" %>
<%@page import = "Database.*" %>
<%@page import = "java.util.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
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
        <link rel="stylesheet" href="./resources/css/posts-list-style.css">
        <title>Post Page</title>
    </head>
    <body>
        <jsp:include page="./layout/Header.jsp" />
        <%PostDAO postDAO = new PostDAO();%>
        <div class="d-flex justify-content-center mt-5">
            <div class="container row mt-5 mb-4">
                <div class="col-md-3">
                    <div class="card p-3 mt-3">
                        <input type="text" name="postTitle" placeholder="Search" />
                        <select class="form-select text-primary mt-3" name="postCategory">
                            <option selected value="">Post Category</option>
                            <%List<String> categoryPostList = postDAO.allCategoryPost();
            for (String categoryPost : categoryPostList) {%>
                            <option value="<%=categoryPost%>"><%=categoryPost%></option>
                            <%}%>
                        </select>
                    </div>
                </div>
                <div class="col-md-9" id="post-list">

                    Posts List 
                    <%List<Post> list = postDAO.getPostedPaged(0, 5);
                for (Post post : list) {%>
                    <div class="row p-3 mb-2">
                        <div class="col-md-3">
                            <img src="<%=post.getAuthorID()%>" alt="img" class="w-100 h-100 object-contain" />
                        </div>
                        <div class="col-md-3">
                            <img src="<%=post.getThumbnail()%>" alt="img" class="w-100 h-100 object-contain" />
                        </div>
                        <div class="col-md-6">
                            <h3><%=post.getTitle()%></h3>
                            <p class="truncate">
                                <%=post.getBriefInfo()%>
                            </p>
                        </div>
                        <div class="info-aside col-md-3">
                            <br />
                            <p>
                                <a href="#" class="btn btn-primary btn-block"> Details </a>
                            </p>
                        </div>
                    </div>
                    <%}%>
                </div>
            </div>
        </div>
        <div class="d-flex justify-content-center" id="pagination-container">
            <% int numOfPage= postDAO.getPostCount()/5;
            if(postDAO.getPostCount()%5 != 0) numOfPage+= 1;
            for (int i = 1; i <=numOfPage; i++) {%>
            <button class="pagination-btn ms-2 inactive" data-page="<%=i%>" onclick="loadPagePosts(<%=i%>)"> <%=i%></button>
            <%}%>
        </div>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="./resources/js/post-list-script.js"></script>
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
    </body>
</html>
