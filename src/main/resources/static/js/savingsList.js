
// Lấy thông tin người dùng từ localStorage
var userInfo = JSON.parse(localStorage.getItem('userInfo'));

// Kiểm tra xem userInfo có giá trị hay không
if (!userInfo || Object.keys(userInfo).length === 0) {
    // Nếu userInfo trống, chuyển hướng đến trang đăng nhập
    window.location.href = "/login";
}

document.addEventListener("DOMContentLoaded", function() {
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
            <p>Tên tài khoản: ${saving.accountName}</p>
            <p>Số dư: ${saving.depositAmount}</p>
            <p>Lãi suất: ${saving.interestRateValue}</p>
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

        // Gửi yêu cầu DELETE đến API
        fetch(`/api/savings/${selectedSavingsAccountId}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to delete savings account');
                }
                // Xử lý khi xóa thành công
                alert('Đã tất toán sổ tiết kiệm!');
                modal.style.display = 'none';

                // Remove the withdrawn savings account from the array
                savingsAccounts = savingsAccounts.filter(account => account.id !== selectedSavingsAccountId);
                // Re-render the savings accounts list
                renderSavingsList();
            })
            .catch(error => {
                console.error('Error deleting savings account:', error);
                // Xử lý khi xảy ra lỗi
                alert('Đã xảy ra lỗi khi tất toán sổ tiết kiệm');
            });
        modal.style.display = "none";
    });

    // Close modal when clicking outside of it
    window.addEventListener("click", (event) => {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    });
});
function redirectToHome() {
    window.location.href = "/home";
}