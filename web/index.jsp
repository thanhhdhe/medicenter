<%-- 
    Document   : HomePage
    Created on : 12-Sep-2023, 20:29:49
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

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
        <div class="row row-third">
            <!--Main layout-->
            <div class="container">


                <div class="col-12 text-center">
                    <h2 class="mb-4">Hot Post</h2>
                </div>


                <!--Section: News of the day-->
                <section class="border-bottom pb-4 mb-5">

                    <div class="row gx-5">
                        <div class="col-md-6 mb-4">
                            <div class="bg-image hover-overlay ripple shadow-2-strong rounded-5" data-mdb-ripple-color="light">
                                <img src="https://mdbcdn.b-cdn.net/img/new/slides/080.webp" class="img-fluid" />
                                <a href="#!">
                                    <div class="mask" style="background-color: rgba(251, 251, 251, 0.15);"></div>
                                </a>
                            </div>
                        </div>

                        <div class="col-md-6 mb-4">
                            <span class="badge bg-danger px-2 py-1 shadow-1-strong mb-3">News of the day</span>
                            <h4><strong>Facilis consequatur eligendi</strong></h4>
                            <p class="text-muted">
                                Lorem ipsum dolor sit amet consectetur adipisicing elit. Facilis consequatur
                                eligendi quisquam doloremque vero ex debitis veritatis placeat unde animi laborum
                                sapiente illo possimus, commodi dignissimos obcaecati illum maiores corporis.
                            </p>
                            <button type="button" class="appointment-btn">Read more</button>
                        </div>
                    </div>
                </section>
                <!--Section: News of the day-->

                <!--Section: Content-->
                <section>
                    <div class="row gx-lg-5">
                        <div class="col-lg-4 col-md-12 mb-4 mb-lg-0">
                            <!-- News block -->
                            <div>
                                <!-- Featured image -->
                                <div class="bg-image hover-overlay shadow-1-strong ripple rounded-5 mb-4"
                                     data-mdb-ripple-color="light">
                                    <img src="https://mdbcdn.b-cdn.net/img/new/fluid/city/113.webp" class="img-fluid" />
                                    <a href="#!">
                                        <div class="mask" style="background-color: rgba(251, 251, 251, 0.15);"></div>
                                    </a>
                                </div>

                                <!-- Article data -->
                                <div class="row mb-3">
                                    <div class="col-6">
                                        <a href="" class="text-info">
                                            <i class="fas fa-plane"></i>
                                            Travels
                                        </a>
                                    </div>

                                    <div class="col-6 text-end">
                                        <u> 15.07.2020</u>
                                    </div>
                                </div>

                                <!-- Article title and description -->
                                <a href="" class="text-dark">
                                    <h5>This is title of the news</h5>

                                    <p>
                                        Lorem ipsum dolor sit amet consectetur adipisicing elit. Odit, iste aliquid. Sed
                                        id nihil magni, sint vero provident esse numquam perferendis ducimus dicta
                                        adipisci iusto nam temporibus modi animi laboriosam?
                                    </p>
                                </a>

                                <hr />

                                <!-- News -->
                                <a href="" class="text-dark">
                                    <div class="row mb-4 border-bottom pb-2">
                                        <div class="col-3">
                                            <img src="https://mdbcdn.b-cdn.net/img/new/standard/city/041.webp"
                                                 class="img-fluid shadow-1-strong rounded" alt="Hollywood Sign on The Hill" />
                                        </div>

                                        <div class="col-9">
                                            <p class="mb-2"><strong>Lorem ipsum dolor sit amet</strong></p>
                                            <p>
                                                <u> 15.07.2020</u>
                                            </p>
                                        </div>
                                    </div>
                                </a>

                                <!-- News -->
                                <a href="" class="text-dark">
                                    <div class="row mb-4 border-bottom pb-2">
                                        <div class="col-3">
                                            <img src="https://mdbcdn.b-cdn.net/img/new/standard/city/042.webp"
                                                 class="img-fluid shadow-1-strong rounded" alt="Palm Springs Road" />
                                        </div>

                                        <div class="col-9">
                                            <p class="mb-2"><strong>Lorem ipsum dolor sit amet</strong></p>
                                            <p>
                                                <u> 15.07.2020</u>
                                            </p>
                                        </div>
                                    </div>
                                </a>


                            </div>
                            <!-- News block -->
                        </div>
                    </div>
                </section>

            </div>
            <!--Main layout-->

        </div>
    </div>

    <jsp:include page="view/layout/footer.jsp"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

</body>


