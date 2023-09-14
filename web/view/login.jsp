<!DOCTYPE html>
<html>
    <head>
        <title>Login Pop-up</title>
        <style>
            .login-popup {
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
            }

            .login-form {
                width: 25%;
                background-color: white;
                padding: 40px;
                border-radius: 5px;
                box-shadow: 0px 0px 10px 0px #000;
                position: relative;
            }

            .login-form input[type="text"],
            .login-form input[type="password"] {
                padding: 16px;
                width: 100%;
            }
            
            .login-form input[type="submit"]{
                width: 100%;
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
        <div id="loginPopup" class="login-popup">
            <div class="login-form">
                <span class="close-button" onclick="hideLoginPopup()">&#x2716;</span>
                <h2 style="text-align:center;color:gray;">Login</h2>
                <br>
                <form action="login" method="post">
                    <input placeholder="Your email address" type="text" id="email" name="email" required><br><br>
                    <input placeholder="Password" type="password" id="password" name="password" required><br><br>
                    <a href="forgetpassword">Forgot password?</a><br><br>
                    <input type="submit" value="Login"> <br><br>
                    Not a member, <a href="#" onclick="showRegisterPopup()">register here!</a><br><br><br>
                    <% String error = (String) request.getAttribute("error");
                    if (error != null) { %>
                    <p> <%= error %> </p>
                    <% } %>
    }%
                </form>
            </div>
        </div>

        <script>
            function showLoginPopup() {
                document.getElementById('registerPopup').style.display = 'none';
                var loginPopup = document.getElementById('loginPopup');
                loginPopup.style.display = 'flex';

                var overlay = document.querySelector('.login-popup');
                overlay.addEventListener('click', function (event) {
                    if (event.target === overlay) {
                        hideLoginPopup();
                    }
                });
            }

            function hideLoginPopup() {
                document.getElementById('loginPopup').style.display = 'none';
            }
        </script>

    </body>
</html>
