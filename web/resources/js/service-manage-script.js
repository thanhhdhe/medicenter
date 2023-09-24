/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */



document.addEventListener('DOMContentLoaded', function () {
    document.querySelector('select[name="sortBy"]').addEventListener('change', function () {
            loadServices(1); // Gọi hàm loadServices() để tải danh sách dịch vụ
        });
        
    document.querySelector('input[name="keywordSearch"]').addEventListener('input', function () {
            loadServices(1); // Gọi hàm loadServices() để tải danh sách dịch vụ
        });

    // Xử lý sự kiện khi người dùng nhấp vào nút phân trang
    var paginationButtons = document.querySelectorAll('.pagination-btn');
    paginationButtons.forEach(function (button) {
        button.addEventListener('click', function () {
            if (!this.classList.contains('active')) {
                document.querySelectorAll('.pagination-btn').forEach(function (paginationBtn) {
                    if (paginationBtn.classList.contains('active')) {
                        paginationBtn.classList.remove('active');
                    }
                });
                this.classList.remove('inactive');
                this.classList.add('active');

                var page = this.dataset.page;
                loadServices(page); // Gọi hàm loadServices() để tải danh sách dịch vụ của trang được chọn
            }
        });
    });

    // Hàm tải danh sách dịch vụ
    function loadServices(page = 1) {
        var serviceTitle = document.querySelector('input[name="keywordSearch"]').value;
        var sortType = document.querySelector('select[name="sortBy"]').value;
        // Gửi yêu cầu Ajax đến máy chủ để lấy danh sách dịch vụ
        var xhr = new XMLHttpRequest();
        xhr.open('GET', 'service?event=service-list-managerchoose&keyword=' + encodeURIComponent(serviceTitle) +
                '&sortType=' + encodeURIComponent(sortType) +
                '&page=' + encodeURIComponent(page));

        xhr.onload = function () {
            if (xhr.status === 200) {
                // Xử lý dữ liệu trả về từ máy chủ và cập nhật nội dung trang
                document.querySelector('#service-list').innerHTML = xhr.responseText;
                document.querySelector('#pagination-container').innerHTML = xhr.getResponseHeader('pagination');
            } else {
                console.error('Error:', xhr.status);
            }
        };

        xhr.onerror = function () {
            console.error('Error:', xhr.status);
        };

        xhr.send();
    }
    
    let hideButtons = document.getElementsByClassName("hide-service-button");
    let showButtons = document.getElementsByClassName("show-service-button");

        for (let i = 0; i < hideButtons.length; i++) {
            hideButtons[i].addEventListener("click", function() {
                let serviceId = this.getAttribute("data-service-id");
                hideService(serviceId);
                let serviceElement = document.getElementById(serviceId);
                serviceElement.classList.toggle("inactives");
                
                // Thay đổi status thành "Inactive"
                let statusElement = serviceElement.querySelector('.status');
                statusElement.classList.remove('text-success');
                statusElement.classList.add('text-black-50');
                statusElement.textContent = 'Inactive';
                
                this.classList.remove("hide-service-button");
                this.classList.add("show-service-button");
                this.querySelector('img').src = "resources/img/icon/visual.png";
                
                
            });
        }

        for (let i = 0; i < showButtons.length; i++) {
            showButtons[i].addEventListener("click", function() {
                let serviceId = this.getAttribute("data-service-id");
                showService(serviceId);
                let serviceElement = document.getElementById(serviceId);
                serviceElement.classList.toggle("inactives");
                
                // Thay đổi status thành "Active"
                let statusElement = serviceElement.querySelector('.status');
                statusElement.classList.remove('text-black-50');
                statusElement.classList.add('text-success');
                statusElement.textContent = 'Active';
                
                this.classList.remove("show-service-button");
                this.classList.add("hide-service-button");
                this.querySelector('img').src ="resources/img/icon/hide.png";
                
                
            });
        }
        
function hideService(serviceId) {
    console.log("hide");
    $.ajax({
        url: "/ChildrenCare/service?event=hide&serviceId=" + serviceId,
        type: "POST",
        success: function(response) {
            // Xử lý thành công, ví dụ: thông báo thành công hoặc cập nhật giao diện người dùng
            return;
        },
        error: function(xhr, status, error) {
            // Xử lý lỗi, ví dụ: thông báo lỗi hoặc ghi log
            console.error(error);
        }
    });
}
function showService(serviceId) {
    console.log("show");
    $.ajax({
        url: "/ChildrenCare/service?event=show&serviceId=" + serviceId,
        type: "POST",
        success: function(response) {
            // Xử lý thành công, ví dụ: thông báo thành công hoặc cập nhật giao diện người dùng
            return;
        },
        error: function(xhr, status, error) {
            // Xử lý lỗi, ví dụ: thông báo lỗi hoặc ghi log
            console.error(error);
        }
    });
}
    
});
    function loadPageServices(page) {
        var serviceTitle = document.querySelector('input[name="keywordSearch"]').value;
        var sortType = document.querySelector('select[name="sortBy"]').value;

        // Gửi yêu cầu Ajax đến máy chủ để lấy danh sách dịch vụ
        var xhr = new XMLHttpRequest();
        xhr.open('GET', 'service?event=service-list-managerchoose&keyword=' + encodeURIComponent(serviceTitle) +
                '&sortType=' + encodeURIComponent(sortType) +
                '&page=' + encodeURIComponent(page));


        xhr.onload = function () {
            if (xhr.status === 200) {
                // Xử lý dữ liệu trả về từ máy chủ và cập nhật nội dung trang
                document.querySelector('#service-list').innerHTML = xhr.responseText;
                document.querySelector('#pagination-container').innerHTML = xhr.getResponseHeader('pagination');
            } else {
                console.error('Error:', xhr.status);
            }
        };

        xhr.onerror = function () {
            console.error('Error:', xhr.status);
        };

        xhr.send();
    }
