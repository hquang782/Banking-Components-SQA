``// Lấy thông tin đã nhập từ localStorage
let savingsAccountInfo = JSON.parse(localStorage.getItem('savingsAccountInfo'));
if (!savingsAccountInfo || Object.keys(savingsAccountInfo).length === 0) {
    window.location.href = "/enter";
} else {
    // Hiển thị thông tin người dùng trong trang

    document.getElementById('accountName').innerText =  savingsAccountInfo.accountName;
    document.getElementById('accountType').innerText =  savingsAccountInfo.savingsType;

    // console.log(day); // Kết quả: 02/12/2024
    document.getElementById('depositDate').innerText = savingsAccountInfo.depositDate ;
    document.getElementById("maturityDate").innerText = savingsAccountInfo.maturityDate;
    document.getElementById('depositAmount').innerText = savingsAccountInfo.depositAmount;
    document.getElementById('term').innerText =  savingsAccountInfo.term;
    document.getElementById('interestPaymentMethod').innerText = savingsAccountInfo.interestPaymentMethod;
    document.getElementById('interestRateValue').innerText =  savingsAccountInfo.interestRateValue;
}

var backButton = document.getElementById('backButton');

// Thêm sự kiện click vào nút "Quay lại"
backButton.addEventListener('click', function(event) {
    // Ngăn chặn hành vi mặc định của nút "submit" là gửi form
    event.preventDefault();

    // Xóa 'savingsAccountInfo' từ localStorage
    localStorage.removeItem('savingsAccountInfo');

    // Chuyển hướng người dùng trở lại trang "/enter"
    window.location.href = "/enter";
});

function navigateToOTPPage() {
    // Chuyển hướng người dùng sang trang "/nhapOTP"
    window.location.href = "/nhapOTP";
}

