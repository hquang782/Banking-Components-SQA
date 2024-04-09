// Lấy thông tin người dùng từ localStorage
var userInfo = JSON.parse(localStorage.getItem('userInfo'));

// Kiểm tra xem userInfo có giá trị hay không
if (!userInfo || Object.keys(userInfo).length === 0) {
    // Nếu userInfo trống, chuyển hướng đến trang đăng nhập
    window.location.href = "/login";
}

document.addEventListener("DOMContentLoaded", function () {
    const savingsList = document.getElementById("savingsList");
    const savingsInfo = document.getElementById("savingsInfo");
    const modal = document.getElementById("modal");
    const closeModal = document.getElementById("closeModal");
    const withdrawButton = document.getElementById("withdraw");


    let selectedSavingsAccountId; // Biến để lưu trữ ID của sổ tiết kiệm được chọn
    let savingsAccounts = []; // Mảng để lưu trữ danh sách sổ tiết kiệm
    // lấy customer id
    let identificationNumber = userInfo.identificationNumber

    // Function to render savings accounts list
    function renderSavingsList() {
        // Clear the existing list
        savingsList.innerHTML = "";
        // Render the updated list
        savingsAccounts.forEach(saving => {
            const listItem = document.createElement("li");
            listItem.textContent = saving.accountName;
            listItem.dataset.id = saving.id; // Set data-id attribute with the ID
            listItem.addEventListener("click", () => {
                selectedSavingsAccountId = saving.id;
                showSavingsInfo(saving);
            });
            savingsList.appendChild(listItem);
        });
    }

    // Fetch data from server
    fetch(`http://127.0.0.1:8080/api/v1/savings-accounts/list/${identificationNumber}`)
        .then(response => response.json())
        .then(data => {
            savingsAccounts = data; // Save the fetched data to the array
            // Render the savings accounts list
            renderSavingsList();
        })
        .catch(error => console.error("Error fetching data:", error));

    // Show savings info in modal
    function showSavingsInfo(saving) {
        savingsInfo.innerHTML = `
            <p>Tên sổ tiết kiệm: ${saving.accountName}</p>
            <p>Số tiền: ${formatCurrency(saving.depositAmount)} VND</p>
            <p>Lãi suất: ${saving.interestRateValue}%</p>
            <p>Ngày gửi: ${saving.depositDate}</p>
            <p>Kỳ hạn: ${saving.term}</p>
            <p>Ngày đáo hạn: ${saving.maturityDate}</p>
        `;
        console.log(selectedSavingsAccountId)
        modal.style.display = "block";
    }

    // Close modal
    closeModal.addEventListener("click", () => {
        modal.style.display = "none";
    });

    // Withdraw button action
    withdrawButton.addEventListener("click", () => {
        if (!selectedSavingsAccountId) {
            alert("Vui lòng chọn một sổ tiết kiệm.");
            return;
        }

        // Hiển thị hộp thoại nhập mật khẩu
        const password = prompt("Vui lòng nhập mật khẩu để tất toán sổ tiết kiệm:");

        // Kiểm tra xem mật khẩu đã nhập có hợp lệ không
        if (password === null || password === '') {
            // Người dùng đã hủy hoặc không nhập mật khẩu
            return;
        }

        // Gửi mật khẩu đến server để kiểm tra
        fetch(`http://127.0.0.1:8080/api/auth/verifyPassword`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: userInfo.account.username,
                password: password }) // Gửi mật khẩu đến server
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to verify password');
                }
                // Xác thực mật khẩu thành công, tiếp tục tất toán sổ tiết kiệm
                return fetch(`http://127.0.0.1:8080/api/v1/savings-accounts/${selectedSavingsAccountId}`, {
                    method: 'DELETE'
                });
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to maturity savings account');
                }
                // Xử lý khi xóa thành công
                alert('Đã tất toán sổ tiết kiệm!');
                modal.style.display = 'none';
                fetchAndUpdateUserInfo(userInfo); // Gọi hàm để lấy thông tin mới của khách hàng
                // Remove the withdrawn savings account from the array
                savingsAccounts = savingsAccounts.filter(account => account.id !== selectedSavingsAccountId);
                // Re-render the savings accounts list
                renderSavingsList();
            })
            .catch(error => {
                console.error('Error:', error);
                // Xử lý khi xảy ra lỗi
                alert('Đã xảy ra lỗi khi tất toán sổ tiết kiệm hoặc xác thực mật khẩu');
            });
        modal.style.display = "none";
    });


    // Close modal when clicking outside of it
    window.addEventListener("click", (event) => {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    });
});

function redirectToHome() {
    window.location.href = "/home";
}

function fetchAndUpdateUserInfo(userInfo) {
    // Thực hiện yêu cầu GET mới để lấy thông tin mới của khách hàng
    fetch('http://127.0.0.1:8080/api/v1/customers/updateCustomer/' + userInfo.account.id, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch customer information');
            }
            // Parse dữ liệu JSON nhận được từ response
            return response.json();
        })
        .then(data => {
            // Định dạng lại dữ liệu thành cấu trúc userInfo
            var formattedUserInfo = {
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
            // Lưu userInfo vào localStorage
            localStorage.setItem('userInfo', JSON.stringify(formattedUserInfo));
            // Gọi hàm để cập nhật thông tin trên giao diện người dùng nếu cần
        })
        .catch(error => {
            console.error('Error:', error);
            // Xử lý khi xảy ra lỗi
            alert('Đã xảy ra lỗi khi lấy thông tin mới của khách hàng');
        });
}

function formatCurrency(amount) {
    // Chuyển đổi số tiền sang chuỗi
    var amountString = amount.toString();

    // Tạo một biến để lưu trữ chuỗi đã định dạng
    var formattedAmount = '';

    // Lặp qua từng ký tự của chuỗi số tiền
    for (var i = amountString.length - 1, j = 1; i >= 0; i--, j++) {
        formattedAmount = amountString.charAt(i) + formattedAmount;
        if (j % 3 === 0 && i > 0) {
            formattedAmount = ',' + formattedAmount;
        }
    }

    return formattedAmount;
}