<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>
        <style>
            /* Styling for the login pop-up */
            .login-popup {
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
            }

            /* Styling for the login form */
            .login-form {
                width: 25%;
                background-color: white;
                padding: 40px;
                border-radius: 5px;
                box-shadow: 0px 0px 10px 0px #000;
                position: relative;
            }

            /* Styling for input fields */
            .login-form input[type="text"],
            .login-form input[type="password"] {
                padding: 16px;
                width: 100%;
            }

            /* Styling for the login button */
            .Login-button {
                width: 100%;
            }

            /* Styling for the close button */
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
        <!-- Login pop-up container -->
        <div id="loginPopup" class="login-popup">
            <div class="login-form">
                <!-- Close button -->
                <span class="close-button" onclick="hideLoginPopup()">&#x2716;</span>
                <!-- Login heading -->
                <h2 style="text-align:center;color:gray;">Login</h2>
                <br>
                <div>
                    <!-- Email and password input fields -->
                    <input placeholder="Your email address" type="text" id="lemail" name="lemail" required><br><br>
                    <input placeholder="Password" type="password" id="lpassword" name="lpassword" required><br><br>
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

        <script>
            var loginAttempts = 0; // Track login attempts
            var loginTimeout; // Store login timeout reference

            function attemptLogin() {
                var email = document.getElementById("lemail").value;
                var password = document.getElementById("lpassword").value;

                // Check if user input email
                if (email.trim() === "") {
                    document.getElementById("loginResult").textContent = "You should input your email";
                    document.getElementById("loginResult").style.color = "red";
                    document.getElementById("loginResult").style.fontWeight = "bold";
                    return;
                }
                // Check if user input password
                if (password.trim() === "") {
                    document.getElementById("loginResult").textContent = "You should input your password";
                    document.getElementById("loginResult").style.color = "red";
                    document.getElementById("loginResult").style.fontWeight = "bold";
                    return;
                }
                // Check user's mail valid 
                if (!email.match("^[A-Za-z0-9+_.-]+@(.+)$")) {
                    document.getElementById("loginResult").textContent = "Your email is invalid";
                    document.getElementById("loginResult").style.color = "red";
                    document.getElementById("loginResult").style.fontWeight = "bold";
                    return;
                }

                // Check if the user has exceeded login attempts exactly 3 times
                if (loginAttempts === 3) {
                    // Display a message and disable the login button
                    document.getElementById("loginResult").textContent = "Login attempts exceeded. Please try again later.";
                    document.getElementById("loginResult").style.color = "red";
                    document.getElementById("loginResult").style.fontWeight = "bold";
                    document.getElementById("Login-button").disabled = true;

                    // Set a timeout to re-enable the login button after 5 minutes
                    loginTimeout = setTimeout(resetLoginAttempts, 10000); // 300,000 milliseconds = 5 minutes
                } else if (loginAttempts > 3) {
                    // Display a message and disable the login button
                    document.getElementById("loginResult").textContent = "Login attempts exceeded. Please try again later.";
                    document.getElementById("loginResult").style.color = "red";
                    document.getElementById("loginResult").style.fontWeight = "bold";
                    document.getElementById("Login-button").disabled = true;
                } else {
                    // Create an AJAX request to check login
                    var xhr = new XMLHttpRequest();
                    xhr.onreadystatechange = function () {
                        if (xhr.readyState === 4 && xhr.status === 200) {
                            var result = xhr.responseText;

                            if (result === "success") {
                                // Display the messasge
                                document.getElementById("loginResult").textContent = "Login successful!\nThis popup will automatically close in 5 seconds!";
                                document.getElementById("loginResult").style.color = "gold";
                                document.getElementById("loginResult").style.fontWeight = "normal";

                                // Reset login attempts if login is successful
                                resetLoginAttempts();

                                // Close the login pop-up after 5 seconds
                                popupTimeout = setTimeout(hideLoginPopup, 5000);
                            } else {
                                // Variable times available
                                // Display the messasge
                                document.getElementById("loginResult").textContent = "Wrong email or password. You have " + (3 - loginAttempts) + " times to wrong";
                                document.getElementById("loginResult").style.color = "red";
                                document.getElementById("loginResult").style.fontWeight = "bold";
                                // Increment login attempts on failed login
                                loginAttempts++;
                            }
                        }
                    };

                    xhr.open("POST", "login", true);
                    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                    xhr.send("lemail=" + email + "&lpassword=" + password);
                }
            }

            function showLoginPopup() {
                // Close register pop-up by default
                hideRegisterPopup();
                // Open the login pop-up
                var loginPopup = document.getElementById('loginPopup');
                loginPopup.style.display = 'flex';
            }

            function hideLoginPopup() {
                // Remove the announcement
                document.getElementById("loginResult").textContent = "";
                // Remove the email value
                document.getElementById("lemail").value = "";
                // Remove the password value
                document.getElementById("lpassword").value = "";
                // Hide the popup
                document.getElementById('loginPopup').style.display = 'none';
            }

            function resetLoginAttempts() {
                // Reset login attempts, re-enable login button, and clear timeout
                loginAttempts = 0;
                document.getElementById("Login-button").disabled = false;
                clearTimeout(loginTimeout);
            }
        </script>
    </body>
</html>
