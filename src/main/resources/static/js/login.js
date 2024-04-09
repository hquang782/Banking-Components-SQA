document.addEventListener('DOMContentLoaded', function() {
    var loginForm = document.getElementById('login-form');
    loginForm.addEventListener('submit', function(event) {
        event.preventDefault();
        var username = document.getElementById('username').value;
        var password = document.getElementById('password').value;

        // Tạo payload cho yêu cầu POST
        var data = {
            username: username,
            password: password
        };

        // Thực hiện yêu cầu POST đến API
        fetch('http://127.0.0.1:8080/api/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (response.status === 401) {
                    throw new Error('Sai tên người dùng hoặc mật khẩu');
                } else if(!response.ok) {
                    throw new Error('Đã xảy ra lỗi khi kết nối đến máy chủ.');
                }
                return response.json();
            })
            .then(data => {
                if (data) {
                    var userInfo = {
                        fullName: data.fullName,
                        age: data.age,
                        gender: data.gender,
                        dob: data.dob,
                        address: data.address,
                        email: data.email,
                        phoneNumber: data.phoneNumber,
                        identificationNumber: data.identificationNumber,
                        bankAccountNumber: data.bankAccountNumber,
                        account: {
                            id: data.account.id,
                            username: data.account.username,
                            balance: data.account.balance,
                            roles: data.account.roles
                        }
                    };
                    localStorage.setItem('userInfo', JSON.stringify(userInfo));
                    window.location.href = "/home";
                } else {
                        alert("Đăng nhập không thành công. Vui lòng thử lại!");
                }
            })
            .catch(error => {
                console.error('Error:', error.message);
                alert(error.message);
            });


    });
});
