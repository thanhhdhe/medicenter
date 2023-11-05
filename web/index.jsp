<%-- 
    Document   : HomePage
    Created on : 12-Sep-2023, 20:29:49
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Medilab</title>

        <!-- Favicon -->
        <link rel="apple-touch-icon" sizes="76x76" href="./resources/favicon/apple-touch-icon.png" />
        <link rel="icon" type="image/png" sizes="32x32" href="./resources/favicon/favicon-32x32.png" />
        <link rel="icon" type="image/png" sizes="16x16" href="./resources/favicon/favicon-16x16.png" />
        <link rel="manifest" href="./resources/favicon/site.webmanifest" />
        <meta name="msapplication-TileColor" content="#da532c" />
        <meta name="theme-color" content="#ffffff" />
        <!--Font awesome-->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" />
        <!-- Vendor CSS Files -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <link href="./resources/css/style.css" rel="stylesheet">
    </head>
    <jsp:include page="view/verification-popup.jsp"/>

    <body>
        <jsp:include page="view/layout/Header.jsp"/>
        <div class="container px-4">
            <div class="row mt-5">
                <!-- Start  Slider  -->
                <div id="carouselExampleIndicators" class="carousel col-8" data-bs-ride="carousel">
                    <div class="carousel-indicators">
                        <c:forEach items="${requestScope.slider}" var="sli" varStatus="loop"> 
                            <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="${loop.index}" 
                                    class="${loop.first ? 'active' : ''}" aria-current="${loop.first ? 'true' : 'false'}" 
                                    aria-label="Slide ${loop.index + 1}"></button>
                        </c:forEach>
                    </div>

                    <div class="carousel-inner">
                        <c:forEach items="${requestScope.slider}" var="sli" varStatus="loop"> 
                            <div class="carousel-item ${loop.first ? 'active' : ''}">
                                <img name="currImage" class="bd-placeholder-img bd-placeholder-img-lg d-block w-100" width="860" height="615" id="carouselImage" src="${sli.image}" alt="Slider Image">
                                <div class="carousel-caption">
                                    <h5 class="fw-bold">${sli.title}</h5>
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
                <div class="col-md-4">
                    <div class="position-sticky" style="top: 2rem;">
                        <div>
                            <div class="d-flex align-items-lg-center justify-content-between">
                                <h4 class="fst-italic">Recent posts</h4>
                                <h5 class="fst-italic"><a href="service?event=to-contact-link">Contact Us</a></h5>
                            </div>
                            <ul class="list-unstyled">
                                <c:forEach items="${requestScope.last3post}" var="p" varStatus="loopPost"> 
                                    <li>
                                        <a class="d-flex flex-column flex-lg-row gap-3 align-items-start align-items-lg-center py-3 link-body-emphasis text-decoration-none border-top" href="blogDetail?ID=${p.postID}">
                                            <img src="${p.thumbnail}" class="bd-placeholder-img" style="object-fit: cover; min-width: 140px;" height="110">
                                            <div>
                                                <h6 class="fw-bold">${p.title}</h6>
                                                <small class="text-body-secondary fw-light">${p.createdDate}</small>
                                                <p class="">${p.briefInfo}</p>
                                            </div>
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
                <!--End sider -->
            </div>
            <div class="row second-row">
                <section class="explore-section section-padding" id="section_2">
                    <div class="container">

                        <div class="col-12 text-center">
                            <h2 class="mb-4 big-title">Our Services</h2>
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
                                                        <div class="col-lg-4 col-md-6 col-12 mb-4">
                                                            <div class="custom-block bg-white shadow-lg">

                                                                <div class="d-flex">
                                                                    <div>
                                                                        <h4><strong>${s.title}</strong></h4>

                                                                        <p class="mb-0">${s.serviceDetail}</p>
                                                                    </div>

                                                                    <span class="badge bg-design rounded-pill ms-auto">14</span>
                                                                </div>

                                                                <img src="${s.thumbnail}" class="custom-block-image img-fluid" alt="">
                                                                <div class="text-center mt-4"> 
                                                                    <form action="service?event=detail" method="POST">
                                                                        <input type="hidden" name="serviceID" value="${s.serviceID}">
                                                                        <input type="submit"  class="btn btn-primary btn-block" value="Details"/>
                                                                    </form>
                                                                </div>

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
                        <h2 class="mb-4 big-title">Hot Post</h2>
                    </div>


                    <!--Section: News of the day-->
                    <section class="border-bottom pb-4 mb-5">

                        <div class="row gx-5">
                            <div class="col-md-6 mb-4">
                                <div class="bg-image hover-overlay ripple shadow-2-strong rounded-5" data-mdb-ripple-color="light">
                                    <img src="${requestScope.hotest.thumbnail}" class="img-fluid" />
                                    <a href="#!">
                                        <div class="mask" style="background-color: rgba(251, 251, 251, 0.15);"></div>
                                    </a>
                                </div>
                            </div>

                            <div class="col-md-6 mb-4">
                                <span class="badge bg-danger px-2 py-1 shadow-1-strong mb-3">Hottest</span>
                                <h4><strong>${requestScope.hotest.title}</strong></h4>
                                <p class="text-muted overflow-hidden" style="max-height: 8.8rem;">
                                    ${requestScope.hotest.briefInfo}
                                </p>

                                <a href="blogDetail?ID=${hotest.postID}" class="">
                                    Continue reading
                                </a>
                            </div>
                        </div>
                    </section>
                    <!--Section: News of the day-->

                    <!--Section: Content-->
                    <section>

                        <div class="row gx-lg-5">
                            <c:forEach items="${requestScope.hot3pot}" var="po">
                                <div class="col-lg-4 col-md-12 mb-4 mb-lg-0">
                                    <!-- News block -->
                                    <div>
                                        <!-- Featured image -->
                                        <div class="bg-image hover-overlay shadow-1-strong ripple rounded-5 mb-4"
                                             data-mdb-ripple-color="light">
                                            <img src="${po.thumbnail}" class="bd-placeholder-img" style="object-fit: cover; min-width: 100%;" width="210" height="200">
                                            <a href="/ChildrenCare/blogDetail?ID=${po.postID}">
                                                <div class="mask" style="background-color: rgba(251, 251, 251, 0.15);"></div>
                                            </a>
                                        </div>
                                        <!-- Article data -->
                                        <div class="row mb-3">
                                            <div class="col-6">
                                                <a href="" class="text-info text-nowrap">
                                                    <i class="fas fa-plane"></i>
                                                    ${po.categoryPost}
                                                </a>
                                            </div>

                                            <div class="col-6 text-end">
                                                <u> ${po.createdDate}</u>
                                            </div>
                                        </div>

                                        <!-- Article title and description -->
                                        <a href="/ChildrenCare/blogDetail?ID=${po.postID}" class="text-dark">
                                            <h5>${po.title}</h5>

                                            <p>
                                                ${po.briefInfo}
                                            </p>
                                        </a>

                                        <hr />


                                    </div>
                                    <!-- News block -->
                                </div>
                            </c:forEach>
                        </div>

                    </section>

                </div>
                <!--Main layout-->

            </div>
        </div>

        <jsp:include page="view/layout/footer.jsp"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
            $(document).ready(function () {
                var url = window.location.href;

                function getParameterByName(name) {
                    name = name.replace(/[\[\]]/g, "\\$&");
                    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
                            results = regex.exec(url);
                    if (!results)
                        return null;
                    if (!results[2])
                        return '';
                    return decodeURIComponent(results[2].replace(/\+/g, " "));
                }

                var showmodal = getParameterByName("showmodal");
            });
        </script>
    </body>
</html>


