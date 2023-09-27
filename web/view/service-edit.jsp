<%-- 
    Document   : service-edit
    Created on : Sep 25, 2023, 6:46:53 AM
    Author     : Admin
--%>
<%@page import = "model.*" %>
<%@page import = "Database.*" %>
<%@page import = "java.util.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <jsp:include page="./layout/Head.jsp"/>
    <body>
        <jsp:include page="./layout/Header.jsp"/>
        <%  Service service = (Service) request.getAttribute("service");
        CategoryServiceDAO categoryServiceDAO = new CategoryServiceDAO();
        String titleErr = (request.getAttribute("titleErr") + "").equals("null") ? "" : (request.getAttribute("titleErr") + "");
        String originalPriceErr = (request.getAttribute("originalPriceErr") + "").equals("null") ? "" : (request.getAttribute("originalPriceErr") + "");
        String salePriceErr = (request.getAttribute("salePriceErr") + "").equals("null") ? "" : (request.getAttribute("salePriceErr") + "");
        %>
        <div class="d-flex flex-column align-items-center justify-content-center mt-5 mb-5">
            <div class="row w-75 mb-3">
                <div class="col-md-6">
                    <%String validate = request.getAttribute("validate")+"";
                    if(!validate.equals("null")&&validate.equals("false")){%>
                    <h4  class="text-danger text-start fw-bold">Update Fail!!</h4>
                    <%}%>
                </div>
                
            </div>
            <form action="service?event=edit-service&ServiceID=<%=service.getServiceID()%>" method="POST" class="w-75" enctype="multipart/form-data">
                <div class="row">    
                    <div class="col-md-4">
                        <div>
                            <img src="<%=service.getThumbnail()%>" alt="ìmg" class="w-100 h-100 object-contain" /></div>
                        <div class="form-group mt-3">
                            <label for="serviceImage">Service Image:</label>
                            <input type="file" class="form-control-file" id="serviceImage" name="serviceImage">
                        </div>
                        <h5 class="d-flex justify-content-center mt-3">- Or -</h5>
                        <div class="form-group mt-3">
                            <label for="serviceURL">Service Image:</label>
                            <input type="text" class="form-control" id="serviceURL" name="serviceURL">
                        </div>
                    </div>
                    <div class="col-md-8 ps-5">
                        <div class="d-flex align-items-baseline">
                            <p class="text-muted me-2">ID: </p>
                            <input class="form-control text-muted" type="text"  value="<%=service.getServiceID()%>" readonly  />
                        </div>
                        <div>
                        <div class="d-flex align-items-baseline">
                            <p class="text-muted me-2">Title: </p>
                            <input class="form-control" type="text" name="Title" value="<%=service.getTitle()%>"  />
                        </div>
                    <%if(!titleErr.equals("null")){%>    <p class="text-danger"><%=titleErr%></p><%}%>
                        </div>
                        <div>
                            <p class="text-muted">Brief: </p>
                            <textarea class="form-control text-muted" rows="4" cols="50" name="Brief" value="<%=service.getBrief()%>"><%=service.getBrief()%></textarea>
                        </div>
                        <div class="row">
                            <div class="col-md-4">
                                <select class="form-select text-primary mt-3 mb-4 w-75" name="status" >
                                    <%if(service.getStatus()){%>
                                    <option value="active" selected>Active</option>
                                    <option value="inactive">Inactive</option>
                                    <%}else{%>
                                    <option value="active">Active</option>
                                    <option value="inactive" selected>Inactive</option>
                                    <%}%>
                                </select>
                            </div>
                            <div class="col-md-4">
                                <select class="form-select text-primary mt-3 mb-4 w-75" name="serviceType"  class="form-select">
                                    <%List<CategoryService> categoryServiceList = categoryServiceDAO.getAllCategoryServices();
                    for (CategoryService categoryService : categoryServiceList) {
                    if(service.getCategoryID() == categoryService.getCategoryID()){ %>
                                    <option value="<%=categoryService.getCategoryID()%>" selected><%=categoryService.getCategoryName()%></option>
                                    <%}else{%>
                                    <option value="<%=categoryService.getCategoryID()%>"><%=categoryService.getCategoryName()%></option>
                                    <%}%>
                                    <%}%>

                                </select>
                            </div>   
                        </div>
                        <div class="d-flex">
                            <div class="col-md-6"><p class="text-muted">Price: </p>
                                <input class="form-control w-50" type="number" value="<%=service.getOriginalPrice()%>" name="OriginalPrice" />
                                <%if(!originalPriceErr.equals("null")){%>    <p class="text-danger"><%=originalPriceErr%></p><%}%>
                            </div>
                            <div class="col-md-6"><p class="text-muted">Sale price: </p>
                                <input class="form-control w-50" type="number" value="<%=service.getSalePrice()%>" name="SalePrice" />
                                <%if(!salePriceErr.equals("null")){%>    <p class="text-danger"><%=salePriceErr%></p><%}%>
                            </div>
                        </div>

                        <div>
                            <p class="text-muted">Description: </p>
                            <textarea class="form-control text-muted" rows="6" cols="50" value="<%=service.getServiceDetail()%>" name="Description"><%=service.getServiceDetail()%></textarea>
                        </div>
                            
                        <div class="d-flex justify-content-center">
                            <input class="btn btn-primary mt-3 w-25" type="submit" value="Update" />
                        </div>
                    </div>
            </form>
        </div>
    </div>



    <jsp:include page="./layout/footer.jsp"/>


</body>

</html>