<%-- 
    Document   : service-manage
    Created on : Sep 24, 2023, 8:10:25 AM
    Author     : Admin
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

        <link rel="stylesheet" href="./resources/css/services-style.css">
        <title>Medilab</title>
    </head>
    <body>
        <jsp:include page="./layout/Header.jsp" />
        <%ServiceDAO serviceDAO = new ServiceDAO();
        CategoryServiceDAO categoryServiceDAO = new CategoryServiceDAO();
        StaffDAO staffDAO = new StaffDAO();  %>
        <div class="d-flex justify-content-center mt-5">
            <div class="container row mt-5 mb-4">
                <div class="col-md-3">
                    <div class="card p-3 mt-3">
                        <input type="text" name="keywordSearch" placeholder="Search Title or Brief" class="form-control mb-2" />
                        <select class="form-select text-primary mt-3 mb-4" name="sortBy" >
                            <option selected value="">Sort By</option>
                            <option value="title">Title</option>
                            <option value="category">Category</option>
                            <option value="listPrice">List price</option>
                            <option value="salePrice">Sale price</option>
                            <option value="status">Status</option>
                        </select>
                        
                    </div>
                </div>
                <div class="col-md-9" id="service-list">

                    <!-- Services List -->
                    <%List<Service> list = serviceDAO.getSortedPaged(0, 5);
            if(!list.isEmpty())   { for (Service service : list) {System.out.println(service.getTitle());%>
            <div id="<%=service.getServiceID()%>" class="service row p-3 <%=!service.getStatus()?"inactives":"" %>">
                        <div class="col-md-3">
                            <img src="<%=service.getThumbnail()%>" alt="ìmg" class="w-100 h-100 object-contain" />
                        </div>
                        <div class="col-md-6">
                            <div>
                                <h3>ID: <%=service.getServiceID()%></h3>
                                <h3><%=service.getTitle()%></h3>
                            </div>
                            <div class="d-flex mt-1 mb-2 align-items-end">
                                <h5 class="pe-2">Category:<p class="fw-normal"><%=categoryServiceDAO.getCategoryServiceByID(service.getCategoryID()+"").getCategoryName()%></p></h5>
                            </div>
                            
                            <div class="text-black-50">
                                <p class="clamp">
                                    <%=service.getBrief()%>
                                </p>
                            </div>
                        </div>
                        <div class="info-aside col-md-3">
                            <div class="price-wrap">
                                <%if(service.getSalePrice()<=0){%>
                                <span class="price h5"> $<%=service.getOriginalPrice()%> </span>
                                <%}else{%>
                                <span class="price h5"> $<%=service.getSalePrice()%> </span>
                                <del class="price-old"> $<%=service.getOriginalPrice()%></del>
                                <%}%>
                            </div>
                                <%if(service.getStatus()){%><h5 class="status text-success mt-2">Active</h5> <%}else{%> <h5 class="status text-black-50 mt-2">Inactive</h5><%}%>
                            <!-- info-price-detail // -->
                            <br />
                            <div class="d-flex h-50 align-content-center flex-wrap" >
                            <div class="d-flex">
                                <%if(service.getStatus()){%><button class="button-icon me-2 showhide hide-service-button" data-service-id="<%=service.getServiceID()%>"><img src="resources/img/icon/hide.png" alt="alt"/></button> 
                                <%}else{%>
                                <button class="button-icon me-2 showhide show-service-button" data-service-id="<%=service.getServiceID()%>"><img src="resources/img/icon/visual.png" alt="alt"/></button> 
                                <%}%>
                                <button class="button-icon me-2"><a href="service?event=detail&id=<%=service.getServiceID()%>"><img src="resources/img/icon/detail.png" alt="alt"/></a></button>
                                <button class="button-icon"><a href="service?event=edit&id=<%=service.getServiceID()%>"><img src="resources/img/icon/pen.png" alt="alt"/></a></button>
                            </div></div>
                        </div>
                    </div>
                    <%}}%>


                </div>
            </div>
        </div>
        <div class="d-flex justify-content-center mb-5" id="pagination-container">
            <button class="pagination-btn ms-2 active" data-page="1">1</button>
            <%for (int i = 2; i <=(serviceDAO.getServiceCount()+4)/5; i++) {%>
            <button class="pagination-btn ms-2 inactive" data-page="<%=i%>"><%=i%></button>
            <%}%>
        </div>
        <jsp:include page="layout/footer.jsp" />
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="./resources/js/services-manage-script.js"></script>
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
