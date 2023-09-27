<%-- 
    Document   : post-list-manage
    Created on : Sep 26, 2023, 11:11:37 AM
    Author     : quanh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- Google Fonts -->
        <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Raleway:300,300i,400,400i,500,500i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
            crossorigin="anonymous"
            />

        <link rel="stylesheet" href="./resources/css/services-style.css">
        <title>Medilab</title>
    </head>
    <body>
        <jsp:include page="./layout/Header.jsp" />
        <div class="d-flex flex-column align-items-center justify-content-center mt-5">

            <input type="text" name="postTitle" placeholder="Search Title" class="form-control mb-2" />
            <select class="form-select text-primary mt-3 mb-4" name="Category" >
                <option selected value="">Category</option>

            </select>
            <select class="form-select text-primary mt-3 mb-4" name="Author" >
                <option selected value="">Author</option>

            </select>
            <select class="form-select text-primary mt-3 mb-4" name="Status" >
                <option selected value="">Status</option>

            </select>
            <select class="form-select text-primary mt-3 mb-4" name="sortBy" >
                <option selected value="">Sort By</option>
                <option value="title">Title</option>
                <option value="category">Category</option>
                <option value=" author">Author</option>
                <option value="featured">Featured</option>
                <option value="status">Status</option>
            </select>

        </div>
        
        <div class="d-flex justify-content-center" id="pagination-container">
            <% int numOfPage= postDAO.getPostCount()/6;
            if(postDAO.getPostCount()%6 != 0) numOfPage+= 1;
            for (int i = 1; i <=numOfPage; i++) {%>
            <button class="pagination-btn ms-2 inactive" data-page="<%=i%>" onclick="loadPagePosts(<%=i%>)"> <%=i%></button>
            <%}%>
        </div>
        <jsp:include page="layout/footer.jsp" />
    </body>
</html>
