<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>
        <link rel="stylesheet" href="./resources/css/login-style.css">
    </head>
    <body>
        <!-- Login pop-up container -->
        <div id="loginPopup" class="login-popup">
            <div class="login-form">
                <!-- Close button -->
                <span class="close-button" onclick="hideLoginPopup()">&#x2716;</span>
                <!-- Login heading -->
                <h2 class="login-heading">Login</h2>
                <br>
                <div>
                    <!-- Email and password input fields -->
                    <input maxlength="30" placeholder="Your email address" type="text" id="lemail" name="lemail" required><br><br>
                    <input maxlength="30" placeholder="Password" type="password" id="lpassword" name="lpassword" required><br><br>
                    <!-- Forgot password link -->
                    <a href="resetpassword?action=forgetpassword">Forgot password?</a><br><br>
                    <!-- Login button -->
                    <button id="Login-button" class="Login-button" onclick="attemptLogin()">Login</button>
                    <!-- Login result message -->
                    <p id="loginResult" value=""></p>
                    <!-- Registration link -->
                    Not a member, <a href="#" onclick="showRegisterPopup()">register here!</a><br><br><br>
                </div>
            </div>
        </div>
        <script src="./resources/js/login-script.js"></script>
    </body>
</html>
