<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : staff-list
    Created on : Oct 10, 2023, 12:44:35 AM
    Author     : quanh
--%>
<%@page import = "model.*" %>
<%@page import = "Database.*" %>
<%@page import = "java.util.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Raleway:300,300i,400,400i,500,500i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
            crossorigin="anonymous"
            />
        <link rel="stylesheet" href="./resources/css/staff-style.css">
    </head>
    <body>
        <jsp:include page="./layout/Header.jsp" />
        <% ServiceStaffDAO ssDao= new ServiceStaffDAO(); %>
        <% ServiceDAO sDao= new ServiceDAO(); %>
        <!--start doctor detail-->
        <div class="list-doctor">
            <div class="content-page single-content">
                <div class="container">
                    <div class="row">
                        <div class="col-md-8">
                            <div class="row">
                                <div class="col-md-4"> 
                                    <img src="https://hongngochospital.vn/wp-content/uploads/2023/09/DSC03128-scaled.jpg">
                                    <div class="text-center">
                                        <button class="more" data-toggle="modal" data-target="#exampleModal5">Đặt lịch hẹn khám</button>
                                    </div>
                                    <div class="modal fade" id="exampleModal5" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                        <div class="modal-dialog" role="document">
                                            <div class="modal-content">
                                                <button class="modal-close modal-toggle " data-dismiss="modal" aria-label="Close">X</button>
                                                <div class="modal-body">
                                                    <h2 class="modal-title">Đặt lịch hẹn</h2>
                                                    <div class="appointment-form-popup">
                                                        <p class="wpcf7-contact-form-not-found"><strong>Error:</strong> Contact form not found.</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-8">
                                    <!--<input type="type" name="${staff}">-->
                                    <h1 class="page-title">${staff.getRank()} ${staff.getFullName()}</h1>
                                    <div class="list-info">
                                        <table cellpadding="10">
                                            <tbody>
                                                <tr>
                                                    <td> 
                                                        <strong>Chuyên Khoa</strong>
                                                    </td>
                                                    <td>
                                                        <p class="detail">Khoa Ngoại Chung</p>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td> <strong>Ngôn ngữ</strong></td>
                                                    <td><p class="detail">Tiếng Anh</p></td>
                                                </tr>
                                                <tr>
                                                    <td> <strong>Học vấn</strong></td>
                                                    <td>
                                                        <p class="detail"></p>
                                                        <p>- Năm 2014 : Tốt nghiệp Bác sĩ đa khoa tại trường Đại học Y Dược Thái Bình
                                                            <br>
                                                            - Năm 2022 : Tốt nghiệp Bác sĩ chuyên khoa cấp I Ngoại khoa chuyên ngành Chấn thương chỉnh hình, Đại học Y Hà Nội</p>
                                                        <p></p>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td> 
                                                        <strong>Hoạt động chuyên ngành</strong>
                                                    </td>
                                                    <td>
                                                        <p class="detail"></p>
                                                        <p>- Năm 2014 – 2020: Bác sĩ điều trị ngoại tổng hợp , chuyên sâu chấn thương chỉnh hình, Khoa Ngoại Tổng hợp Bệnh viện đa khoa Ý Yên
                                                            <br>
                                                            - Năm 2015: Bác sĩ phẫu thuật điều trị gãy xương cẳng bàn tay, bàn chân, Khoa Chấn thương chỉnh hình, Bệnh viện đa khoa tỉnh Nam Định<br> - Năm 2016: Bác sĩ phẫu thuật điều trị gãy xương đùi cẳng chân, Khoa Chấn thương chi dưới, Bệnh viện Việt Đức<br> - Năm 2017: Bác sĩ phẫu thuật nội soi cơ bản, Bệnh viện Việt Đức<br> - Năm 2020 – 2022: Bác sĩ chuyên khoa cấp I Ngoại khoa chuyên ngành chấn thương, Bệnh viện Việt Đức, Bệnh viện đại học Y Hà Nội, Bệnh viện Xanh Pôn,...</p><p></p>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td> 
                                                        <strong>Nghiên cứu chuyên sâu</strong>
                                                    </td>
                                                    <td>
                                                        <p class="detail"></p>
                                                        <p>- Gãy xương do chấn thương: gãy tay, gãy chân, gãy xương đòn,...<br> - Dị tật xương khớp bẩm sinh: thừa ngón, dính ngón, ngón cái chẻ đôi,...<br> - Di chứng sau điều trị chấn thương: chậm liền xương, khớp giả, liền lệch, viêm xương tủy xương,...<br> - Đứt dây chằng chéo, thoái hóa, hoại tử khớp,...</p><p></p>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <div class="achievement">
                                <h3>Thành tựu chuyên môn</h3>
                                <div class="detail">
                                    <p><span data-sheets-value="{&quot;1&quot;:2,&quot;2&quot;:&quot;- Năm 2016 : Chứng chỉ hành nghề khám chữa bệnh chuyên khoa ngoại\r\n- 2 Đề tài khoa học cấp cơ sở: Phẫu thuật kết hợp xương cẳng bàn tay năm 2019 và phẫu thuật chuyển vạt da điều trị thừa ngón 2022\r\n - 2 Danh hiệu chiến sĩ thi đua cơ sở\r&quot;}" data-sheets-userformat="{&quot;2&quot;:957,&quot;3&quot;:{&quot;1&quot;:0},&quot;5&quot;:{&quot;1&quot;:[{&quot;1&quot;:2,&quot;2&quot;:0,&quot;5&quot;:{&quot;1&quot;:2,&quot;2&quot;:0}},{&quot;1&quot;:0,&quot;2&quot;:0,&quot;3&quot;:3},{&quot;1&quot;:1,&quot;2&quot;:0,&quot;4&quot;:1}]},&quot;6&quot;:{&quot;1&quot;:[{&quot;1&quot;:2,&quot;2&quot;:0,&quot;5&quot;:{&quot;1&quot;:2,&quot;2&quot;:0}},{&quot;1&quot;:0,&quot;2&quot;:0,&quot;3&quot;:3},{&quot;1&quot;:1,&quot;2&quot;:0,&quot;4&quot;:1}]},&quot;7&quot;:{&quot;1&quot;:[{&quot;1&quot;:2,&quot;2&quot;:0,&quot;5&quot;:{&quot;1&quot;:2,&quot;2&quot;:0}},{&quot;1&quot;:0,&quot;2&quot;:0,&quot;3&quot;:3},{&quot;1&quot;:1,&quot;2&quot;:0,&quot;4&quot;:1}]},&quot;8&quot;:{&quot;1&quot;:[{&quot;1&quot;:2,&quot;2&quot;:0,&quot;5&quot;:{&quot;1&quot;:2,&quot;2&quot;:0}},{&quot;1&quot;:0,&quot;2&quot;:0,&quot;3&quot;:3},{&quot;1&quot;:1,&quot;2&quot;:0,&quot;4&quot;:1}]},&quot;10&quot;:1,&quot;11&quot;:4,&quot;12&quot;:0}">– Năm 2016 : Chứng chỉ hành nghề khám chữa bệnh chuyên khoa ngoại<br> – 2 Đề tài khoa học cấp cơ sở: Phẫu thuật kết hợp xương cẳng bàn tay năm 2019 và phẫu thuật chuyển vạt da điều trị thừa ngón 2022<br>
                                        </span></p>
                                </div>
                            </div>
                            <div class="advisory">
                                <h3>Tư vấn của bác sĩ</h3>
                                <ul></ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--doctor detail end-->
    </body>
</html>
