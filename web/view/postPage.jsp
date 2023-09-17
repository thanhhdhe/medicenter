<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : postPage
    Created on : Sep 14, 2023, 8:22:13 PM
    Author     : quanh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Post Page</title>
    </head>
    <body>
        <form action="postPage" method="POST">
            <div class="category">
                <c:forEach var="c" items="${categoryList}">
                    <a href="#">${c}</a>
                </c:forEach>
            </div>
            <div class="search-bar">
                <input type="text" name="search" value="" />
                <input type="submit" value="Search" />
            </div>
            <div class="posts">
                <c:forEach var="i" items="${list}">
                    <a href="#">${i.title}</a>
                    <img src="./resources/img/image1.jpg" width="120" height="120" alt="image1"/>
                    
                </c:forEach>
            </div>
            <div class="paging">
                <c:forEach var="i" begin="1" end="${endPage}">
                    <a href="#">${i}</a>
                </c:forEach>
            </div>
            
        </form>

    </body>
</html>

