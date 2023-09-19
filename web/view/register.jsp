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
                z-index: 1000;
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
            .genderBox,
            .register-form input[type="password"] {
                padding: 16px;
                margin-top: 5px;
                margin-bottom: 5px;
                width: 100%;
            }

            .Register-button {
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
                <div>
                    <div class="name-input-container">
                        <input placeholder="First name" type="text" id="rfirstname" name="rfirstname" required>
                        <input placeholder="Last name" type="text" id="rlastname" name="rlastname" required>
                    </div>  
                    <select class="genderBox" id="rgender" name="rgender">
                        <option value="Male">Male</option>
                        <option value="Female">Female</option>
                        <option value="Others">Others</option>
                    </select>
                    <input placeholder="Your email address" type="email" id="remail" name="remail" required>
                    <input placeholder="Your phone number" type="text" id="rphonenumber" name="rphonenumber" required>
                    <input placeholder="Your address" type="text" id="raddress" name="raddress" required>
                    <input placeholder="Password" type="password" id="rpassword" name="rpassword" required>
                    <input placeholder="Repeat Password" type="password" id="rrepeat_password" name="rrepeat_password" required>
                    <button class="Register-button" onclick="attemptRegister()">Register</button> <br><br>
                    <p style="color:red;" id="registerResult" value=""></p>
                    Already have an account, <a onclick="showLoginPopup()" href="#">login now!</a><br>
                </div>
            </div>
        </div>

        <script>
            function attemptRegister() {

                var firstname = document.getElementById("rfirstname").value;
                var lastname = document.getElementById("rlastname").value;
                var email = document.getElementById("remail").value;
                var address = document.getElementById("raddress").value;
                var genderBox = document.getElementById("rgender");
                var gender = genderBox.options[genderBox.selectedIndex].value;
                var phonenumber = document.getElementById("rphonenumber").value;
                var password = document.getElementById("rpassword").value;
                var repeat_password = document.getElementById("rrepeat_password").value;

                // Create an AJAX request to check login
                var xhr = new XMLHttpRequest();
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        var result = xhr.responseText;
                        document.getElementById("registerResult").textContent = result;
                        if (result === "Your email should receive email for verification") {
                            document.getElementById("registerResult").style = "color:green;";
                        }
                    }
                };

                xhr.open("POST", "register", true);
                xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                xhr.send("rfirstname=" + firstname + "&rlastname=" + lastname + "&remail=" + email
                        + "&rgender=" + gender + "&raddress=" + address + "&rphonenumber=" + phonenumber
                        + "&rpassword=" + password + "&rrepeat_password=" + repeat_password);
            }

            function showRegisterPopup() {
                // Close login pop-up by default
                hideLoginPopup();
                // Open the register pop-up
                var registerPopup = document.getElementById('registerPopup');
                registerPopup.style.display = 'flex';
            }

            function hideRegisterPopup() {
                document.getElementById('registerPopup').style.display = 'none';
                document.getElementById("registerResult").textContent = "";
                document.getElementById("rfirstname").value = "";
                document.getElementById("rlastname").value = "";
                document.getElementById("remail").value = "";
                document.getElementById("raddress").value = "";
                document.querySelector(".genderBox").selectedIndex = 0;
                document.getElementById("rphonenumber").value = "";
                document.getElementById("rpassword").value = "";
                document.getElementById("rrepeat_password").value = "";
            }
        </script>

    </body>
</html>
