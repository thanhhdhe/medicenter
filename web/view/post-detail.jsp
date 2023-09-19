<%-- 
    Document   : newjsp
    Created on : Sep 17, 2023, 6:27:42 PM
    Author     : quanh
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>'
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
        <link rel="stylesheet" href="./resources/css/posts-detail-style.css">
        <title>Post Page</title>
    </head>
    <body>
        <jsp:include page="./layout/Header.jsp" />
        <%PostDAO postDAO = new PostDAO();%>
        <div class="d-flex justify-content-center mt-5">
            <div class="container row mt-5 mb-4">
                <div class="col-md-3">
                    <form action="postPage">
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
                    </form>

                </div>
                <div class="row p-3 mb-2">
                    <div class="title">
                        <h3>${title}</h3>
                    </div>
                    <div class="author">
                        ${author}
                    </div>
                    <div class="">
                        <a href="${thumbnail}" class="thumbnail"></a>
                    </div>
                    <div class="date">
                        ${update-date}
                    </div>
                    <div class="category">
                        ${category}
                    </div>
                    <div class="post-detail">
                        ${post-detail}
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>
