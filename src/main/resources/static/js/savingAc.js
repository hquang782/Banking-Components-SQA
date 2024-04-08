
// format số tiền nhập
function formatCurrency(input) {
    // Lấy giá trị đang nhập
    var value = input.value;

    // Xóa bỏ tất cả ký tự không phải số
    value = value.replace(/[^0-9]/g, '');
    // Chèn dấu phẩy sau mỗi 3 chữ số từ cuối chuỗi lên đầu
    // var formattedValue = '';
    // for (var i = value.length - 1, j = 1; i >= 0; i--, j++) {
    //     formattedValue = value.charAt(i) + formattedValue;
    //     if (j % 3 === 0 && i > 0) {
    //         formattedValue = ',' + formattedValue;
    //     }
    // }
    var numericValue = parseFloat(value);
    // Gán giá trị đã định dạng vào trường nhập
    input.value = numericValue;
}



// xử lý OTP
// Hàm tạo số OTP ngẫu nhiên có 6 chữ số
function generateOTP() {
    var otp = '';
    for (var i = 0; i < 6; i++) {
        otp += Math.floor(Math.random() * 10); // Tạo số từ 0 đến 9
    }
    return otp;
}

// Hiển thị mã OTP lên trang
// document.getElementById('otpDisplay').innerText = generateOTP();

// Hàm xác nhận OTP
function verifyOTP() {
    // Lấy giá trị từ các ô nhập
    var digit1 = document.getElementById('digit1').value;
    var digit2 = document.getElementById('digit2').value;
    var digit3 = document.getElementById('digit3').value;
    var digit4 = document.getElementById('digit4').value;
    var digit5 = document.getElementById('digit5').value;
    var digit6 = document.getElementById('digit6').value;

    // Ghép các giá trị lại thành một mã OTP
    var otpEntered = digit1 + digit2 + digit3 + digit4 + digit5 + digit6;

    // Lấy OTP từ localStorage
    var otpStored = localStorage.getItem('otp');

    // So sánh mã OTP nhập vào với mã OTP được tạo ra
    if (otpEntered === otpStored) {
        alert('Mở thành công!');
    } else {
        alert('Xác nhận không thành công. Vui lòng thử lại.');
    }
}


// lấy thoog tin lãi suất tương ứng với kì hạn

function searchInterestRate() {
    var term = document.getElementById("term").value;
    var encodedTerm = encodeURIComponent(term);
    // Gọi API để lấy thông tin lãi suất
    fetch('/api/v1/interest-rate/term=' + encodedTerm)
        .then(response => response.json())
        .then(data => {
            // Cập nhật nội dung của phần tử hiển thị lãi suất
            var interestRateElement = document.getElementById("interestRateId");
            interestRateElement.innerHTML = data.rate + "%";
            document.getElementById("interestRateId").value = data.rate ;
            console.log(interestRateElement.innerHTML) ;

        })
        .catch(error => {
            console.error('Error:', error);
        });
}

// Lấy thông tin người dùng từ localStorage
var userInfo = JSON.parse(localStorage.getItem('userInfo'));

// Kiểm tra xem userInfo có giá trị hay không
if (!userInfo || Object.keys(userInfo).length === 0) {
    // Nếu userInfo trống, chuyển hướng đến trang đăng nhập
    window.location.href = "/login";
} else {
    // Hiển thị thông tin người dùng trong trang

    document.getElementById('test').innerText = 'Account Number: ' + userInfo.account.balance;
    var result = userInfo.account.balance ;
    console.log(result) ;
    document.getElementById('dispositAmount').addEventListener('input', function() {
        var inputValue = parseFloat(this.value); // Chuyển đổi giá trị nhập vào thành số kiểu double

        // Kiểm tra nếu giá trị nhập vào thỏa mãn điều kiện
        if (inputValue <= parseFloat(result)) {
            document.getElementById('error').innerText = ''; // Xóa thông báo lỗi nếu có
            // Tiếp tục xử lý hoặc thực hiện các hành động khác ở đây
        } else {
            // Nếu không thỏa mãn điều kiện, hiển thị thông báo lỗi màu đỏ
            document.getElementById('error').innerText = 'Vui Lòng Nhập Giá Trị Nhỏ Hơn Số Dư';
        }
    });
}

// lưu dữ liệu được nhập vào localStorage
document.getElementById('saveAccountForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Ngăn chặn gửi form mặc định

    // Lấy các giá trị từ các trường input
    var accountName = document.getElementById('accountName').value;
    var accountType = document.getElementById('accountType').value;
    var dipositDate = document.getElementById('dipositDate').value;
    var dispositAmount = document.getElementById('dispositAmount').value;
    var term = document.getElementById('term').value;
    var interestRateId = document.getElementById('interestRateId').value;
    var interestPaymentMethod = document.getElementById('interestPaymentMethod').value;


    // Lưu dữ liệu vào localStorage
    var accountInfo = {
        accountName: accountName,
        accountType: accountType,
        dipositDate: dipositDate,
        dispositAmount: dispositAmount,
        term: term,
        interestRateId: interestRateId,
        interestPaymentMethod: interestPaymentMethod
    };
    localStorage.setItem('accountInfo', JSON.stringify(accountInfo));

    // Hiển thị thông báo
    // alert('Dữ liệu đã được lưu vào localStorage!');
    window.location.href = "/confirmSA";

    // Có thể chuyển hướng trang sau khi lưu dữ liệu nếu cần
    // window.location.href = "new-page.html";
});