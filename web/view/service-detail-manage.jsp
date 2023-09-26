<%@page import = "model.*" %>
<%@page import = "Database.*" %>
<%@page import = "java.util.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <jsp:include page="./layout/Head.jsp"/> 
    <link rel="stylesheet" href="./resources/css/services-style.css">

    <body>
        <jsp:include page="./layout/Header.jsp"/>

        <%ServiceDAO serviceDAO = new ServiceDAO();
        Service service = (Service) request.getAttribute("service");
   CategoryServiceDAO categoryServiceDAO = new CategoryServiceDAO();
   StaffDAO staffDAO = new StaffDAO();  %>   
        <section class="section">
            <div class="container">
                <div class="row bg-light align-items-center my-5">

                    <div class="col-md-3">
                        <img src="<%=service.getThumbnail()%>" alt="img" class="w-100 h-100 object-contain" />
                    </div>
                    <div class="col-md-9 p-5">
                        <div class="row mb-3">
                            <h3 class="col-md-3 align-self-center">ID: <%=service.getServiceID()%></h3>
                            <h3 class="col-md-6 align-self-center"><%=service.getTitle()%></h3>
                            <div class="col-md-1 d-flex justify-content-center">
                                <button class="button-edit rounded-0">
                                    <a class="text-decoration-none" href="service?event=edit&id=<%=service.getServiceID()%>">
                                        <img src="resources/img/icon/edit-tool.png" alt="alt"/>
                                    </a>
                                </button>
                            </div>
                        </div>
                        <div class="d-flex mt-1  mb-3">
                            <h5 class="pe-2">Category:</h5><p class="fw-normal m-0"><%=categoryServiceDAO.getCategoryServiceByID(service.getCategoryID()+"").getCategoryName()%></p>
                        </div>
                        <div class="row mb-3">
                            <div class="price-wrap col-md-3">
                                <%if(service.getSalePrice()<=0){%>
                                <span class="price h5"> $<%=service.getOriginalPrice()%> </span>
                                <%}else{%>
                                <span class="price h5"> $<%=service.getSalePrice()%> </span>
                                <del class="price-old"> $<%=service.getOriginalPrice()%></del>
                                <%}%>
                            </div>
                            <div class="col-md-6 mb-3">
                                <div class="form-check form-switch d-flex align-items-center">
                                    <input class="form-check-input me-2" type="checkbox" id="flexSwitchCheckDefault" <%=service.getStatus()?"checked":""%> style="height: 24px;width: 47px;">
                                    <%if(service.getStatus()){%><p class="status bg-success text-white m-0 p-1 fit-content-width rounded-1 text-center">Active</p> 
                                    <%}else{%> 
                                    <p class="status bg-danger text-light m-0 p-1 fit-content-width rounded-1 text-center">Inactive</p><%}%>
                                </div>

                            </div>
                        </div>
                        <div class="mb-3">
                            <h5>QUICK OVERVIEW:</h5>
                            <p class="text-black-50">
                                <%=service.getBrief()%>
                            </p>
                        </div>
                            
                    </div>


                </div>

            </div>
        </div>


    </section>

    <jsp:include page="./layout/footer.jsp"/>

</body>

</html>