<!DOCTYPE html>
<html>
    <head>
        <title>Register Pop-up</title>
        <style>
            .register-popup {
                display: none;
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.7);
                z-index: 101;
                justify-content: center;
                align-items: center;
                overflow: hidden;
                font-weight: 15px;
            }

            .register-form {
                width: 25%;
                background-color: white;
                padding: 40px;
                border-radius: 5px;
                box-shadow: 0px 0px 10px 0px #000;
                position: relative;
            }

            .register-form input[type="text"],
            .register-form input[type="email"],
            .register-form input[type="password"] {
                padding: 16px;
                margin-top: 5px;
                margin-bottom: 5px;
                width: 100%;
            }

            .register-form input[type="submit"]{
                margin-top: 5px;
                width: 100%;
            }

            .close-button {
                position: absolute;
                top: 10px;
                right: 10px;
                cursor: pointer;
                font-size: 24px;
            }
            /* Added CSS for the two-input layout */
            .name-input-container {
                display: flex;
                gap: 10px;
            }

            .name-input-container input[type="text"] {
                flex: 1;
            }
        </style>
    </head>
    <body>
        <div id="registerPopup" class="register-popup">
            <div class="register-form">
                <span class="close-button" onclick="hideRegisterPopup()">&#x2716;</span>
                <h2 style="text-align:center;color:gray;">Register</h2>
                <form action="register" method="post">
                    <div class="name-input-container">
                        <input placeholder="First name" type="text" id="firstname" name="firstname" required>
                        <input placeholder="Last name" type="text" id="lastname" name="lastname" required>
                    </div>  
                    <input placeholder="Your email address" type="email" id="email" name="email" required>
                    <input placeholder="Numberphone" type="text" id="numberphone" name="numberphone" required>
                    <input placeholder="Password" type="password" id="password" name="password" required>
                    <input placeholder="Repeat Password" type="password" id="repeat_password" name="repeat_password" required>
                    <input type="submit" value="Register"> <br><br>
                    Already have an account, <a onclick="showLoginPopup()" href="#">login now!</a><br>
                    <% String error = I(String) request.getAttribute("error");
                    if (error != null) { %>
                    <p> <%= error %> </p>
                    <% } %>
                </form>
            </div>
        </div>

        <script>
            function showRegisterPopup() {
                document.getElementById('loginPopup').style.display = 'none';
                var registerPopup = document.getElementById('registerPopup');
                registerPopup.style.display = 'flex';

                var overlay = document.querySelector('.register-popup');
                overlay.addEventListener('click', function (event) {
                    if (event.target === overlay) {
                        hideRegisterPopup();
                    }
                });
            }

            function hideRegisterPopup() {
                document.getElementById('registerPopup').style.display = 'none';
            }
        </script>

    </body>
</html>
