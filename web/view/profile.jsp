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
    
    <style></style>
    <body>       

        <!-- Modal -->
        <form action="user?action=updateprofile" method="post">
            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Profile information</h5>
                            <p style="color: blue; align-content: center;">
                                ${requestScope.updatesuccess}
                            </p>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="p-4">


                                <div class="row">
                                    <input type="hidden" name="curID" value="<%=curUser.getUserID()%>">
                                    <input type="hidden" name="images" value="<%=curUser.getProfileImage()%>">
                                    <h5 class="mb-0">Avatar Image</h5>
                                    <p style="color: blue; align-content: center;">
                                        ${requestScope.updatesuccess}
                                    </p>
                                    <div>
                                        <div id="thumbbox">
                                            <img class="rounded" height="20%" width="30%" alt="Thumb image" style="width: 150px;" id="thumbimage" src="<%=curUser.getProfileImage()%>">
                                            <a class="removeimg" href="javascript:"></a>
                                        </div>
                                        <div id="myfileupload">
                                            <input type="file" id="uploadfile" name="ImageUpload" onchange="readURL(this);">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <label class="form-label">First Name</label>
                                            <input name="firstname_raw" value="<%=curUser.getFirstName()%>" id="firstName" type="text" class="form-control">
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
                                                    <input id="credit" name="gender" value="male" type="radio" class="form-check-input" checked="" required="">
                                                    <label class="form-check-label">Male</label>
                                                </div>
                                                <div class="form-check">
                                                    <input id="debit" name="gender" value="female" type="radio" class="form-check-input" required="">
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
            function readURL(input, thumbimage) {
                if (input.files && input.files[0]) { //Sử dụng  cho Firefox - chrome
                    var reader = new FileReader();
                    reader.onload = function (e) {
                        $("#thumbimage").attr('src', e.target.result);
                    }
                    reader.readAsDataURL(input.files[0]);
                } else { // Sử dụng cho IE
                    $("#thumbimage").attr('src', input.value);

                }
                $("#thumbimage").show();
                $('.filename').text($("#uploadfile").val());
                $(".Choicefile").hide();
                $(".Update").show();
                $(".removeimg").show();
            }
            $(document).ready(function () {
                $(".Choicefile").bind('click', function () {
                    $("#uploadfile").click();

                });
                $(".removeimg").click(function () {
                    $("#thumbimage").attr('src', '').hide();
                    $("#myfileupload").html('<input type="file" id="uploadfile"  onchange="readURL(this);" />');
                    $(".removeimg").hide();
                    $(".Choicefile").show();
                    $(".Update").hide();
                    $(".filename").text("");
                });
            })
        </script>
    </body>

</html>
