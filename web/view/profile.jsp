<%-- 
    Document   : profile
    Created on : Jan 20, 2022, 10:49:05 PM
    Author     : Khuong Hung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <jsp:include page="./layout/Head.jsp"/>
    <body>
        <style>
            .Choicefile{
                display: block;
                background: #396CF0;
                border: 1px solid #fff;
                color: #fff;
                width: 150px;
                text-align: center;
                text-decoration: none;
                cursor: pointer;
                padding: 5px 0px;
                border-radius: 5px;
                font-weight: 500;
                align-items: center;
                justify-content: center;
            }

            .Choicefile:hover {
                text-decoration: none;
                color: white;
            }

            #uploadfile,
            .removeimg {
                display: none;
            }

            #thumbbox {
                position: relative;
                width: 100%;
                margin-bottom: 20px;
            }

            .removeimg {
                height: 25px;
                position: absolute;
                background-repeat: no-repeat;
                top: 5px;
                left: 5px;
                background-size: 25px;
                width: 25px;
                border-radius: 50%;

            }

            .removeimg::before {
                -webkit-box-sizing: border-box;
                box-sizing: border-box;
                content: '';
                border: 1px solid red;
                background: red;
                text-align: center;
                display: block;
                margin-top: 11px;
                transform: rotate(45deg);
            }

            .removeimg::after {
                content: '';
                background: red;
                border: 1px solid red;
                text-align: center;
                display: block;
                transform: rotate(-45deg);
                margin-top: -2px;
            }
        </style>
        <section class="bg-dashboard">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-xl-8 col-lg-8 col-md-7 mt-4 pt-2 mt-sm-0 pt-sm-0">
                        <h3 class="mb-0"></h3>
                        <div class="rounded shadow mt-4">
                            <div class="p-4 border-bottom">
                                <h5 class="mb-0">Profile information</h5>
                                <p style="color: blue; align-content: center;">
                                    ${requestScope.updatesuccess}
                                </p>
                            </div>
                            <div class="p-4">
                                <form action="user?action=update_image" method="POST" enctype="multipart/form-data">
                                    <h5 class="mb-0">Avatar Image</h5>
                                    <div>
                                        <div id="myfileupload">
                                            <input type="file" id="uploadfile" name="ImageUpload" onchange="readURL(this);" />
                                        </div>
                                        <div id="thumbbox">
                                            <img class="rounded" height="20%" width="30%" alt="Thumb image" id="thumbimage" style="display: none" />
                                            <a class="removeimg" href="javascript:"></a>
                                        </div>
                                        <div id="boxchoice">
                                            <a href="" class="Choicefile"><i class="fas fa-cloud-upload-alt"></i> Up Image</a>
                                            <p style="clear:both"></p>
                                            <input type="submit" id="submit" style="display: none" name="send" class="Update btn btn-primary"
                                                   value="Cập nhật">
                                            <p style="clear:both"></p>
                                        </div> 
                                    </div>
                                </form>
                                <form action="user?action=updateprofile" method="POST">
                                    <div class="row">
                                        <input type="hidden" name="userId" value="${sessionScope.user.userID}">
                                        <input type="hidden" name="userId" value="${sessionScope.user.profileImage}">
                                        <div class="col-md-6">
                                            <div class="mb-3">
                                                <label class="form-label">First Name</label>
                                                <input name="firstname" value="${sessionScope.user.firstName}" id="firstName" type="text" class="form-control">
                                            </div>
                                        </div>

                                        <div class="col-md-6">
                                            <div class="mb-3">
                                                <label class="form-label">Last Name</label>
                                                <input name="lastname" oninvalid="CheckFullName(this);" oninput="CheckFullName(this);"  value="${sessionScope.user.lastName}" id="name2" type="text" class="form-control" >
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="mb-3">
                                                <label class="form-label">Email</label>
                                                <input name="email" readonly value="${sessionScope.user.Email}" id="email" type="email" class="form-control">
                                            </div>
                                        </div>

                                        <div class="col-md-6">
                                            <div class="mb-3">
                                                <label class="form-label">Phone number</label>
                                                <input name="phone" oninvalid="CheckPhone(this);" oninput="CheckPhone(this);" value="0${sessionScope.user.phoneNumber}" id="number" type="text" class="form-control">
                                            </div>                                                                               
                                        </div>

                                        <div class="col-md-6">
                                            <div class="mb-3">
                                                <label class="form-label">Gender</label>
                                                <div class="my-3">
                                                    <div class="form-check">
                                                        <input id="credit" name="gender" ${sessionScope.user.gender==true?"checked":""} value="true" type="radio" class="form-check-input"
                                                               checked required >
                                                        <label class="form-check-label">Nam</label>
                                                    </div>
                                                    <div class="form-check">
                                                        <input id="debit" name="gender" ${sessionScope.user.gender==false?"checked":""} value="false" type="radio" class="form-check-input"
                                                               required>
                                                        <label class="form-check-label">Nữ</label>
                                                    </div>
                                                </div>
                                            </div> 

                                        </div>
                                        <div class="col-md-6">
                                            <div class="mb-3">
                                                <label class="form-label">Address</label>
                                                <input name="address" value="${sessionScope.user.address}" id="address" type="text" class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <input type="submit" id="submit" name="send" class="btn btn-primary" value="Lưu">
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <script src="../resources/js/feather.min.js"></script>
        <script src="../resources/js/app.js"></script>
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
                                                    $('.Choicefile').css('background', '#14142B');
                                                    $('.Choicefile').css('cursor', 'default');
                                                    $(".removeimg").show();
                                                    $(".Choicefile").unbind('click');

                                                }
                                                $(document).ready(function () {
                                                    $(".Choicefile").bind('click', function () {
                                                        $("#uploadfile").click();

                                                    });
                                                    $(".removeimg").click(function () {
                                                        $("#thumbimage").attr('src', '').hide();
                                                        $("#myfileupload").html('<input type="file" id="uploadfile"  onchange="readURL(this);" />');
                                                        $(".removeimg").hide();
                                                        $(".Choicefile").bind('click', function () {
                                                            $("#uploadfile").click();
                                                        });
                                                        $('.Choicefile').css('background', '#14142B');
                                                        $('.Choicefile').css('cursor', 'pointer');
                                                        $(".filename").text("");
                                                    });
                                                })
        </script>
    </body>

</html>
