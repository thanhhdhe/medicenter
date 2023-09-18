<%-- 
    Document   : HomePage
    Created on : 12-Sep-2023, 20:29:49
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>


<jsp:include page="view/login.jsp"/>

<jsp:include page="view/register.jsp"/>
<jsp:include page="view/layout/Head.jsp"/>
<body>
    <jsp:include page="view/layout/Header.jsp"/>
    <div class="container-md px-4">
        <div class="row first-row">
            <!-- Start  Slider  -->
            <div id="carouselExampleIndicators" class="carousel slide col-8 shadow" data-bs-ride="carousel">
                <div class="carousel-indicators">
                    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
                    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
                </div>
                <div class="carousel-inner">
                    <c:forEach items="${requestScope.slider}" var="sli" varStatus="loop"> 
                        <div class="carousel-item ${loop.first ? 'active' : ''}">
                            <img src="${sli.image}" class="d-block w-100" alt="...">
                            <div class="carousel-caption">
                                <h5>${sli.title}</h5>
                                <p>${sli.brief}</p>
                                <p><a href="${sli.backlink}" class="btn btn-warning mt-3">Learn More</a></p>
                            </div>
                        </div>
                    </c:forEach>


                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
            <!-- End  Slider  -->
            <!--Start sider -->
            <div class="sider col-4">
                <h3>Lastest Post</h3>
                <c:forEach items="${requestScope.post}" var="p" varStatus="loopPost"> 
                    <a class="card article shadow" href="#">
                        <div >
                            <div class="row no-gutters">
                                <div class="col-md-4">
                                    <img src="${p.thumbnail}" class="card-img" alt="Thumbnail">
                                </div>
                                <div class="col-md-8">
                                    <div class="card-body">
                                        <h5 class="card-title">${p.title}</h5>
                                        <p class="card-text">${p.briefInfo}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </a>

                </c:forEach>

            </div>
            <!--End sider -->
        </div>
        <div class="row second-row">
            <section class="explore-section section-padding" id="section_2">
                <div class="container">

                    <div class="col-12 text-center">
                        <h2 class="mb-4">Our Services</h2>
                    </div>

                </div>


                <div class="container-fluid">
                    <div class="row">
                        <ul class="nav nav-tabs" id="myTab" role="tablist">
                            <c:forEach items="${requestScope.category}" var="cate">
                                <li class="nav-item" role="presentation">
                                    <button class="nav-link" id="tab-${cate.categoryID}" data-bs-toggle="tab" data-bs-target="#pane-${cate.categoryID}" type="button" role="tab" aria-controls="pane-${cate.categoryID}" aria-selected="false" tabindex="-1">${cate.categoryName}</button>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>

                <div class="container">
                    <div class="row">

                        <div class="col-12">
                            <div class="tab-content" id="myTabContent">
                                <c:forEach items="${requestScope.category}" var="cate" varStatus="looppane">
                                    <div class="tab-pane fade ${looppane.first ? 'active show' : ''}" id="pane-${cate.categoryID}" role="tabpanel" aria-labelledby="tab-${cate.categoryID}" tabindex="0">
                                        <div class="row">
                                            <c:forEach items="${requestScope.services}" var="s">
                                                <c:if test="${s.categoryID == cate.categoryID}">
                                                    <div class="col-lg-4 col-md-6 col-12 mb-4 mb-lg-0">
                                                        <div class="custom-block bg-white shadow-lg">
                                                            <a href="topics-detail.html">
                                                                <div class="d-flex">
                                                                    <div>
                                                                        <h5 class="mb-2">${s.title}</h5>

                                                                        <p class="mb-0">${s.serviceDetail}</p>
                                                                    </div>

                                                                    <span class="badge bg-design rounded-pill ms-auto">14</span>
                                                                </div>

                                                                <img src="${s.thumbnail}" class="custom-block-image img-fluid" alt="">
                                                            </a>
                                                        </div>
                                                    </div>
                                                </c:if>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>

                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>
    <jsp:include page="view/layout/footer.jsp"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

</body>


