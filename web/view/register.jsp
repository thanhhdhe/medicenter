<!DOCTYPE html>
<html>
    <head>
        <title>Register</title>
        <link rel="stylesheet" href="./resources/css/register-style.css">
    </head>
    <body>
        <div id="registerPopup" class="register-popup">
            <div class="register-form">
                <span class="close-button" onclick="hideRegisterPopup()">&#x2716;</span>
                <h2 class="register-heading">Register</h2>
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
                    <input maxlength="30" placeholder="Your email address" type="email" id="remail" name="remail" required>
                    <input maxlength="30" placeholder="Your phone number" type="text" id="rphonenumber" name="rphonenumber" required>
                    <input maxlength="40" placeholder="Your address" type="text" id="raddress" name="raddress" required>
                    <input maxlength="30" placeholder="Password" type="password" id="rpassword" name="rpassword" required>
                    <input maxlength="30" placeholder="Repeat Password" type="password" id="rrepeat_password" name="rrepeat_password" required>
                    <button class="Register-button" onclick="attemptRegister()">Register</button> <br>
                    <p id="registerResult" value=""></p>
                    Already have an account, <a onclick="showLoginPopup()" href="#">login now!</a><br>
                </div>
            </div>
        </div>
        <script src="./resources/js/register-script.js"></script>
    </body>
</html>
