<%-- 
    Document   : servicedetail.jsp
    Created on : Feb 27, 2022, 8:31:51 PM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <jsp:include page="./layout/Head.jsp"/>
    <body>
        <jsp:include page="./layout/Header.jsp"/>


        <section class="section">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-md-5">
                        <div class="slider slider-for">
                            <div><img src="${service.thumbnail}" class="img-fluid rounded" alt=""></div>
                        </div>
                    </div>


                    <div class="col-md-7 mt-4 mt-sm-0 pt-2 pt-sm-0">
                        <div class="section-title ms-md-4">
                            <h4 class="title">${service.title}</h4>
                            <p class="text-muted">${service.brief}</p>
                            <h5 class="text-muted">${service.salePrice}đ</h5>
                            <ul class="list-unstyled text-warning h5 mb-0">


                            </ul>

                            <h5 class="mt-4 py-2">Mô tả :</h5>
                            <p class="text-muted">${service.serviceDetail}</p>
                            <div class="mt-4 pt-2">
                            </div>
                        </div>
                    </div>
                </div>
            </div>

         
        </section>

        <jsp:include page="./layout/footer.jsp"/>


    </body>

</html>