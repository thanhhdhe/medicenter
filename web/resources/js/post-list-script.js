/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

document.addEventListener('DOMContentLoaded', function () {
    // Xử lý sự kiện khi người dùng thay đổi giá trị search, category, page
    var searchInputs = document.querySelectorAll(' select[name="postCategory"]');
    searchInputs.forEach(function (input) {
        input.addEventListener('change', function () {
            // Gọi hàm loadPosts() để tải danh sách bài vi?t
            loadPosts(1);
        });
    });
    document.querySelector('input[name="postTitle"]').addEventListener('input', function () {
        // Gọi hàm loadPosts() để tải danh sách bài vi?t
        loadPosts(1);
    });

    // Xử lý khi người dùng nhấp vào nút phân trang
    var paginationButtons = document.querySelectorAll('.pagination-btn');
    paginationButtons.forEach(function (button) {
        button.addEventListener('click', function () {
            //n?u chua active thì b?t dóng nh?ng cái khác
            if (!this.classList.contains('active')) {
                document.querySelectorAll('.pagination-btn').forEach(function (paginationBtn) {
                    if (paginationBtn.classList.contains('active')) {
                        paginationBtn.classList.remove('active');
                    }
                });
                this.classList.remove('inactive');
                this.classList.add('active');

                var page = this.dataset.page;
                // Gọi hàm loadPosts() để tải danh sách dịch vụ của trang được chọn
                loadPosts(page); 
            }
        });
    });

    // Hàm tải danh sách dịch vụ
    function loadPosts(page = 1) {
        var postTitle = document.querySelector('input[name="postTitle"]').value;
        var postCategory = document.querySelector('select[name="postCategory"]').value;
        // Gửi yêu cầu Ajax đến máy chủ để lấy danh sách dịch vụ
        var xhr = new XMLHttpRequest();
        xhr.open('GET', 'postPage?event=post-list-userchoose&postTitle=' + encodeURIComponent(postTitle) +
                '&postCategory=' + encodeURIComponent(postCategory) +
                '&page=' + encodeURIComponent(page));

        xhr.onload = function () {
            if (xhr.status === 200) {
                // Xử lý dữ liệu trả về từ máy chủ và cập nhật nội dung trang
                document.querySelector('#post-list').innerHTML = xhr.responseText;
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

});
function loadPagePosts(page) {
    var postTitle = document.querySelector('input[name="postTitle"]').value;
    var postCategory = document.querySelector('select[name="postCategory"]').value;

    // Gửi yêu cầu Ajax đến máy chủ để lấy danh sách dịch vụ
    var xhr = new XMLHttpRequest();
    xhr.open('GET', 'postPage?event=post-list-userchoose&postTitle=' + encodeURIComponent(postTitle) +
            '&postCategory=' + encodeURIComponent(postCategory) +
            '&page=' + encodeURIComponent(page));

    xhr.onload = function () {
        if (xhr.status === 200) {
            // Xử lý dữ liệu trả về từ máy chủ và cập nhật nội dung trang
            document.querySelector('#post-list').innerHTML = xhr.responseText;
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