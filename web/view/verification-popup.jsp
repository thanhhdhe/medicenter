<%-- 
    Document   : verification-popup
    Created on : Sep 21, 2023, 2:03:47 PM
    Author     : hbich
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Verification</title>
        <style>
            .verification-form {
                width: 35%;
                background-color: white;
                padding: 40px;
                border-radius: 5px;
                box-shadow: 0px 0px 10px 0px #000;
                position: relative;
            }
            .verification-heading {
                text-align:center;
                color: goldenrod;
            }
            .verification-popup {
                display: none;
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.7);
                z-index: 1000;
                justify-content: center;
                align-items: center;
                overflow: hidden;
                font-size: 15px;
            }
            .close-button {
                position: absolute;
                top: 10px;
                right: 10px;
                cursor: pointer;
                font-size: 24px;
            }
        </style>
    </head>
    <body>
        <%String email = (String) request.getAttribute("email");
        String verifyStatus = (String) request.getAttribute("verifyStatus");%>
        <div id="verificationPopup" class="verification-popup">
            <div class="verification-form">
                <span class="close-button" onclick="hideVerificationPopup()">&#x2716;</span>
                <h2 class="verification-heading">Verification notification</h2>
                <p class="verification-contents">Your email : <%=email%></p>
                <p class="verification-contents"><%=verifyStatus%></p>
            </div>
        </div>
    </body>
    <script>
        function showVerificationPopup() {
            // Open the login pop-up
            var verificationPopup = document.getElementById('verificationPopup');
            verificationPopup.style.display = 'flex';

            // If user click outside the popup, it also close
            var overlay = document.querySelector('.verification-popup');
            overlay.addEventListener('click', overlayVerificationClickHandler(overlay));
        }
        // Function to close popup if user click outside popup
        function overlayVerificationClickHandler(overlay) {
            return function (event) {
                if (event.target === overlay) {
                    hideVerificationPopup()();
                }
            };
        }
        function hideVerificationPopup() {
            // Remove overlay click event listener
            var overlay = document.querySelector('.verification-popup');
            overlay.removeEventListener('click', overlayVerificationClickHandler(overlay));
            document.getElementById('verificationPopup').style.display = 'none';
            window.location.href = "<%=request.getContextPath()%>/home";
        }
        <% if (email != null) { %>
        showVerificationPopup();
        <%  } %>
    </script>
</html>
