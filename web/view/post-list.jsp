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
        <link rel="stylesheet" href="./resources/css/posts-style.css">
        <title>Post Page</title>
    </head>
    <body>
        <jsp:include page="./layout/Header.jsp" />
        <%PostDAO postDAO = new PostDAO();%>
        <div class="d-flex justify-content-center mt-5">
            <div class="container row mt-5 mb-4">
                    <div class="mb-5">
                        <input type="text" name="postTitle" placeholder="Search" class="form-select text-primary mt-3" id="search" value="${postTitle}"/>
                        <select class="form-select text-primary mt-3" name="postCategory">
                            <option selected value="">Post Category</option>
                            <%List<String> categoryPostList = postDAO.allCategoryPost();
            for (String categoryPost : categoryPostList) {%>
                            <option value="<%=categoryPost%>"><%=categoryPost%></option>
                            <%}%>
                        </select>

                    </div>
                    <!-- Category End -->
                </div>
                <div class="col-md-9" id="post-list">
                    <div class="container py-5">
                        <div class="row g-5">
                            <%List<Post> list = postDAO.getPostedPaged(0, 6);
                for (Post post : list) {%>
                            <div class="col-xl-4 col-lg-6">
                                <div class="bg-light rounded overflow-hidden">
                                    <img class="img-fluid w-100" src="<%=post.getThumbnail()%>" alt="">
                                    <div class="p-4">
                                        <a class="h3 d-block mb-3" href="/ChildrenCare/postDetail?ID=<%=post.getPostID()%>"><%=post.getTitle()%></a>
                                        <p class="m-0"><%=post.getBriefInfo()%></p>
                                    </div>
                                    <div class="d-flex justify-content-between border-top p-4">
                                        <div class="d-flex align-items-center">
                                           <small> <img class="rounded-circle me-2" src="<%=postDAO.getAvatarByUserID(post.getAuthorID())%>" id="avatar" alt=""></small>
                                            <small><%=postDAO.getNameByUserID(post.getAuthorID())%></small>
                                        </div>
                                        <!-- <div class="d-flex align-items-center">
                                        
                                                 luot xem
                                         </div> -->
                                    </div>
                                </div>
                            </div>
                            <%}%>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="d-flex justify-content-center" id="pagination-container">
            <% int numOfPage= postDAO.getPostCount()/6;
            if(postDAO.getPostCount()%6 != 0) numOfPage+= 1;
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
