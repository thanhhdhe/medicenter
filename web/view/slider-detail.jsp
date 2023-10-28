<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Slider Detail</title>
        <!-- Include Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>

            .carousel-caption {

                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background: rgba(0, 0, 0, .5);
                z-index: 1;
            }
            .carousel-caption h5 {
                font-size: 45px;
                text-transform: uppercase;
                letter-spacing: 2px;
                margin: 200px 15px 0;
            }
            .carousel-caption p {
                margin: 0 10px;
                width: 70%;
                margin: auto;
                font-size: 18px;
                line-height: 1.9;
            }

        </style>
    </head>
    <body>
        <div class="container mt-4">
            <h1>Slider Detail</h1>
            <% 
            String message = (String) request.getSession().getAttribute("message");
            String alertClass = "alert-success";

            if (message != null) { 
                if (message.contains("error")) {
                    alertClass = "alert-danger";
                }
                        %>
            <div class="alert <%= alertClass %> alert-dismissible fade show" role="alert">
                <strong><%= message %></strong>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <%
                request.getSession().removeAttribute("message");
            }
            %>
            <div class="row">
                <div id="carouselExampleCaptions" class="carousel slide col-md-8">
                    <div class="carousel-indicators">
                        <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                    </div>
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <img name="currImage" class="bd-placeholder-img bd-placeholder-img-lg d-block w-100" width="860" height="615" id="carouselImage" src="<c:out value="${slider.image}" />" alt="Slider Image" />
                            <div class="carousel-caption d-none d-md-block">
                                <h5 id="carouselTitle"><c:out value="${slider.title}" /></h5>
                                <p id="carouselBrief"><c:out value="${slider.brief}" /></p>
                                <p><a id="carouselLink" href="<c:out value="${slider.backlink}" />" class="btn btn-warning mt-3">Learn More</a></p>
                            </div>
                        </div>
                    </div>
                    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Previous</span>
                    </button>
                    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Next</span>
                    </button>
                </div>

                <!-- Slider Details -->
                <div class="col-md-4">
                    <form action="slider?action=update" id="frmUpdateSlide" method="post" enctype="multipart/form-data">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Slider Title: <input type="text" id="titleInput" name="title" value="<c:out value="${slider.title}" />" onkeyup="updateCarousel('titleInput', 'carouselTitle');" class="form-control" /></h5>
                                <p class="card-text">Slider ID: <input readonly type="text" id="sliderid" name="sliderID" value="<c:out value="${slider.sliderID}" />" class="form-control" /></p>
                                <p class="card-text">Slider Brief: <input type="text" id="briefInput" name="brief" value="<c:out value="${slider.brief}" />" onkeyup="updateCarousel('briefInput', 'carouselBrief');" class="form-control" /></p>
                                <p class="card-text">Slider Status:
                                    <select name="status" class="form-select">
                                        <option value="Active" <c:if test="${slider.status == 'Active'}">selected</c:if>>Active</option>
                                        <option value="Inactive" <c:if test="${slider.status == 'Inactive'}">selected</c:if>>Inactive</option>
                                        </select>
                                    </p>
                                    <p class="card-text">Slider Backlink: <input type="text" id="backlinkInput" name="backlink" value="<c:out value="${slider.backlink}" />" class="form-control" /></p>
                                <input type="file" id="imageInput" name="image" accept="image/*" onchange="updateCarouselImage('imageInput');" class="form-control" />
                                <a href="<c:out value="${slider.backlink}" />" class="btn btn-primary mt-3">Go to Backlink</a>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-primary mt-3">Update</button>
                        <a href="slider?action=all" class="btn btn-secondary mt-3">Back to List</a>
                    </form>
                </div>



            </div>

        </div>
        <script>
            function updateCarousel(inputId, elementId) {
                const inputValue = document.getElementById(inputId).value;
                const element = document.getElementById(elementId);
                element.textContent = inputValue;
            }

            function updateCarouselImage(inputId) {
                const selectedImage = document.getElementById(inputId).files[0];
                const element = document.getElementById("carouselImage");

                if (selectedImage) {
                    const reader = new FileReader();
                    reader.onload = function (e) {
                        element.src = e.target.result;
                    };
                    reader.readAsDataURL(selectedImage);
                }
            }
        </script>
        <!-- Include Bootstrap JavaScript and jQuery -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
