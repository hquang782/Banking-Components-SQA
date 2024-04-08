let savingsAccountInfo = JSON.parse(localStorage.getItem('savingsAccountInfo'));
if (!savingsAccountInfo || Object.keys(savingsAccountInfo).length === 0) {
    window.location.href = "/enter";
}

// Hiển thị mã OTP lên trang
document.getElementById('otpDisplay').innerText = generateOTP();

// Lấy đối tượng nút "Xác nhận" bằng id
var confirmButton = document.getElementById('confirmButton');

// Thêm sự kiện click vào nút "Xác nhận"
confirmButton.addEventListener('click', function(event) {
    // Ngăn chặn hành vi mặc định của nút "submit" là gửi form
    event.preventDefault();
    // Gọi hàm xác nhận OTP
    verifyOTP();
});


// Hàm tạo số OTP ngẫu nhiên có 6 chữ số
function generateOTP() {
    var otp = '';
    for (var i = 0; i < 6; i++) {
        otp += Math.floor(Math.random() * 10); // Tạo số từ 0 đến 9
    }
    return otp;
}



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
    var otpStored = document.getElementById('otpDisplay').innerText;

    // So sánh mã OTP nhập vào với mã OTP được tạo ra
    if (otpEntered === otpStored) {
        // Lấy thông tin tài khoản từ localStorage và update số dư
        let userInfo = JSON.parse(localStorage.getItem('userInfo'));
        userInfo.account.balance -= savingsAccountInfo.depositAmount;
        localStorage.setItem('userInfo', JSON.stringify(userInfo));

        let updatedAccountInfo = {
            id: userInfo.account.id,
            balance: userInfo.account.balance,
            username: userInfo.account.username,
        };
        // Gửi yêu cầu POST đến API bằng fetch
        fetch('http://127.0.0.1:8080/api/v1/savings-accounts/' + userInfo.bankAccountNumber, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(savingsAccountInfo)
        })
            .then(function(response) {
                // Kiểm tra mã trạng thái của phản hồi
                if (response.ok) {
                    // Phản hồi thành công từ API tạo tài khoản tiết kiệm
                    alert('Tạo tài khoản thành công!');
                    localStorage.removeItem('savingsAccountInfo');
                    console.log(userInfo.account)
                    // Gửi yêu cầu PUT đến API để cập nhật thông tin tài khoản
                    return fetch('http://127.0.0.1:8080/api/v1/customers/updateBalance/' + userInfo.account.id, {
                        method: 'PUT',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(updatedAccountInfo)
                    });
                } else {
                    // Xử lý khi có lỗi từ server
                    localStorage.removeItem('savingsAccountInfo');
                    throw new Error('Có lỗi từ server khi tạo tài khoản tiết kiệm.');
                }
            })
            .then(function(response) {
                // Kiểm tra mã trạng thái của phản hồi
                if (response.ok) {
                    // Phản hồi thành công từ API cập nhật thông tin tài khoản
                    alert('Cập nhật thông tin tài khoản thành công!');
                    // Chuyển hướng người dùng sau khi tạo tài khoản và cập nhật thông tin thành công
                    window.location.href = "/savings-list";
                } else {
                    // Xử lý khi có lỗi từ server
                    throw new Error('Có lỗi từ server khi cập nhật thông tin tài khoản.');
                }
            })
            .catch(function(error) {
                // Xử lý lỗi khi gửi yêu cầu hoặc nhận phản hồi
                console.error('Có lỗi xảy ra:', error);
                alert('Có lỗi xảy ra. Vui lòng thử lại sau.');
            });
    } else {
        alert('Xác nhận không thành công. Vui lòng thử lại.');
    }
}