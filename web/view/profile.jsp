<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "model.*" %>
<%@page import = "Database.*" %>
<%@page import = "java.util.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <%
    String email = (String) session.getAttribute("email");
    UserDAO dao = new UserDAO();
    User curUser = dao.getUser(email);
    %>

    <style>
        .inputfile {
            width: 0.1px;
            height: 0.1px;
            opacity: 0;
            overflow: hidden;
            position: absolute;
            z-index: -1;
            border: 1px solid #000;
        }
        .inputfile + label {
            font-size: 1.25em;
            font-weight: 700;
            display: inline-block;
            cursor: pointer;
        }

        .inputfile:focus + label,
        .inputfile + label:hover {
        }
    </style>

    <body>       
        <!-- Modal -->
        <form action="user?action=updateprofile&userId=<%=curUser.getUserID()%>" method="post" enctype="multipart/form-data">
            <div class="modal fade" id="myProfile" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Profile information</h5>

                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
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
                            <div class="p-4">


                                <div class="row">

                                    <div class="row mb-3">
                                        <h5 class="mb-0">Avatar Image</h5>
                                        <div class="text-center">

                                            <input type="file" name="images" id="file" class="inputfile" onchange="readURL(this)" accept="image/*"/>
                                            <label for="file"><img id="img-preview" style="height: 160px;width: 160px;" 
                                                                   class="rounded-circle mx-auto d-block" 
                                                                   src="<%=curUser.getProfileImage()%>"  />
                                                <i class="bi bi-pencil-square "></i>
                                            </label>


                                        </div>

                                    </div>
                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <label class="form-label">First Name</label>
                                            <input name="firstname_raw" oninvalid="CheckFullName(this);" oninput="CheckFullName(this);" value="<%=curUser.getFirstName()%>" id="firstName" type="text" class="form-control">
                                        </div>
                                    </div>

                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <label class="form-label">Last Name</label>
                                            <input name="lastname_raw" oninvalid="CheckFullName(this);" oninput="CheckFullName(this);" value="<%=curUser.getLastName()%>" id="name2" type="text" class="form-control">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <label class="form-label">Email</label>
                                            <input name="email" readonly="" value="<%=curUser.getEmail()%>" id="email" type="email" class="form-control">
                                        </div>
                                    </div>

                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <label class="form-label">Phone number</label>
                                            <input name="phone_raw" oninvalid="CheckPhone(this);" oninput="CheckPhone(this);" value="<%=curUser.getPhoneNumber()%>" id="number" type="text" class="form-control">
                                        </div>                                                                               
                                    </div>

                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <label class="form-label">Gender</label>
                                            <div class="my-3">
                                                <div class="form-check">
                                                    <input id="credit" name="gender" value="Male" type="radio" class="form-check-input" 
                                                           <%= curUser.getGender().equals("Male") ? "checked" : "" %> required>
                                                    <label class="form-check-label">Male</label>
                                                </div>
                                                <div class="form-check">
                                                    <input id="debit" name="gender" value="Female" type="radio" class="form-check-input" 
                                                           <%= curUser.getGender().equals("Female") ? "checked" : "" %> required>
                                                    <label class="form-check-label">Female</label>
                                                </div>
                                            </div>
                                        </div>


                                    </div>
                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <label class="form-label">Address</label>
                                            <input name="address" value="<%=curUser.getAddress()%>" id="address" type="text" class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary">Save changes</button>
                        </div>                                      
                    </div>
                </div>
            </div>
        </form>

        <script>

            let noimage =
                    "https://ami-sni.com/wp-content/themes/consultix/images/no-image-found-360x250.png";

            function readURL(input) {
                console.log(input.files);
                if (input.files && input.files[0]) {
                    var reader = new FileReader();
                    reader.onload = function (e) {
                        $("#img-preview").attr("src", e.target.result);
                    };

                    reader.readAsDataURL(input.files[0]);
                } else {
                    $("#img-preview").attr("src", noimage);
                }
            }

            function CheckFullName(text) {
                var name = /^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễếệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ ]{4,}(?:[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễếệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]+){0,2}$/;
                if (!name.test(text.value)) {
                    text.setCustomValidity('Name is not valid');
                } else {
                    text.setCustomValidity('');
                }
                return true;
            }

            function CheckPhone(text) {
                var phone = /(84|0[3|5|7|8|9])+([0-9]{8})\b/;
                if (!phone.test(text.value)) {
                    text.setCustomValidity('Phone is not valid');
                } else {
                    text.setCustomValidity('');
                }
                return true;
            }
        </script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </body>

</html>
