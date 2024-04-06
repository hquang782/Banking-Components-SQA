// Lấy thông tin người dùng từ localStorage
var userInfo = JSON.parse(localStorage.getItem('userInfo'));

// Kiểm tra xem userInfo có giá trị hay không
if (!userInfo || Object.keys(userInfo).length === 0) {
    // Nếu userInfo trống, chuyển hướng đến trang đăng nhập
    window.location.href = "/login";
} else {
    // Hiển thị thông tin người dùng trong trang
    document.getElementById('user-avatar').src = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQV3XeKXPIxJP-R6Hg0d2x2DCxnKV_sT04umGCOTuiNIQ&s";
    document.getElementById('welcome-message').innerText = 'Welcome, ' + userInfo.fullName;
    document.getElementById('account-number').innerText = 'Account Number: ' + userInfo.bankAccountNumber;
    document.getElementById('balance').innerText = 'Balance: $' + userInfo.account.balance;
}

function redirectToSavingsList() {
    window.location.href = "/savings-list";
}
document.addEventListener("DOMContentLoaded", function() {
    var logoutButton = document.getElementById("logout-button");
    logoutButton.addEventListener("click", function() {
        localStorage.removeItem('userInfo');
        window.location.href = "/login"; // Chuyển hướng đến trang đăng nhập sau khi đăng xuất
    });
});