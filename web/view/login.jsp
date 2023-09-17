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

            .Login-button {
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
                <form>
                    <input placeholder="Your email address" type="text" id="email" name="email" required><br><br>
                    <input placeholder="Password" type="password" id="password" name="password" required><br><br>
                    <a href="forgetpassword">Forgot password?</a><br><br>
                    <button class="Login-button" onclick="attemptLogin()">Login</button>
                    <p style="color:red;" id="loginResult" value=""></p>
                    Not a member, <a href="#" onclick="showRegisterPopup()">register here!</a><br><br><br>
                </form>
            </div>
        </div>

        <script>
            function attemptLogin() {
                var email = document.getElementById("email").value;
                var password = document.getElementById("password").value;

                // Create an AJAX request to check login
                var xhr = new XMLHttpRequest();
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        var result = xhr.responseText;
                        document.getElementById("loginResult").textContent = result;

                        if (result === "Login successful!") {
                            popupTimeout = setTimeout(hideLoginPopup, 8000);
                        }

                    }
                };

                xhr.open("POST", "login", true);
                xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                xhr.send("email=" + email + "&password=" + password);
            }

            function showLoginPopup() {
                // Close resgister pop-up by default
                hideRegisterPopup();
                // Open the login pop-up
                var loginPopup = document.getElementById('loginPopup');
                loginPopup.style.display = 'flex ';
            }

            function hideLoginPopup() {
                document.getElementById("loginResult").textContent = "";
                document.getElementById('loginPopup').style.display = 'none';
            }
        </script>

    </body>
</html>
