
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <jsp:include page="./layout/Head.jsp"/> 
    <link rel="stylesheet" href="./resources/css/services-style.css">

    <body>
        <jsp:include page="./layout/Header.jsp"/>
        <div><img class="object-contain" src="resources/img/contact.jpg" style="
                  height: 20rem; width: 100%;
                  " alt="alt"/></div>

        <div class="row mb-5 mt-5 justify-content-center">
            <p class="text-center text-primary fw-bold mb-2">OUR CONTACTS</p>
            <h2 class="text-center mb-4 fw-bold">We’re Here to Help You</h2>
            <p class="col-md-5 text-black-50 fs-6 text-center mb-4">We are an online children care center that makes healthcare management easy and convenient for families. Our system allows customers to track their children's health and book appointments remotely. Medical staff also benefit from tools that optimize operations. We empower families through technology to support children's wellness.</p>
        </div>

        <div class="d-flex mb-5  justify-content-center">
            <div class="row container justify-content-center">
                <div class="col-md-3 me-4 bg-light border-1 p-3 d-flex flex-column align-items-center justify-content-center">
                    <img src="resources/img/icon/map.png" style="width: 3rem;" class="object-contain rounded-0 mb-2" alt="alt"/>
                    <b class="mb-2">Visit Us Daily:</b>
                    <p class="text-black-50">FPT University Hola Ha Noi</p>
                </div>
                <div class="col-md-3 bg-light border-1 p-3 d-flex flex-column align-items-center justify-content-center">
                    <img src="resources/img/icon/phone-call.png" style="width: 3rem;" class="object-contain rounded-0 mb-2" alt="alt"/>
                    <b class="mb-2">Phone Us 24/7:</b>
                    <p class="text-black-50">0834398269</p>
                </div>
                <div class="col-md-3 ms-4 bg-light border-1 p-3 d-flex flex-column align-items-center justify-content-center">
                    <img src="resources/img/icon/mail.png" style="width: 3rem;" class="object-contain rounded-0 mb-2" alt="alt"/>
                    <b class="mb-2">Mail Us 24/7:</b>
                    <p class="text-black-50">lethangd@gmail.com</p>
                </div>
            </div>
        </div>
        <jsp:include page="./layout/footer.jsp"/>

    </body>

</html>