
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

// Lấy đối tượng input có id là "depositDate"
var depositDateInput = document.getElementById("depositDate");

// Tạo một đối tượng Date đại diện cho ngày hiện tại
var currentDate = new Date();

// Lấy thông tin về năm, tháng và ngày từ đối tượng Date
var year = currentDate.getFullYear();
var month = ("0" + (currentDate.getMonth() + 1)).slice(-2); // Thêm "0" phía trước và chỉ lấy hai chữ số cuối cùng
var day = ("0" + currentDate.getDate()).slice(-2); // Thêm "0" phía trước và chỉ lấy hai chữ số cuối cùng

// Ghép năm, tháng và ngày thành chuỗi theo định dạng "YYYY-MM-DD"
var formattedCurrentDate = year + "-" + month + "-" + day;

// Gán giá trị của chuỗi định dạng vào trường input
depositDateInput.value = formattedCurrentDate;




// lấy thoog tin lãi suất tương ứng với kì hạn

function searchInterestRate() {
    var term = document.getElementById("term").value;
    var encodedTerm = encodeURIComponent(term);
    // Gọi API để lấy thông tin lãi suất
    fetch('/api/v1/interest-rate/term=' + encodedTerm)
        .then(response => response.json())
        .then(data => {
            // Cập nhật nội dung của phần tử hiển thị lãi suất
            var interestRateElement = document.getElementById("interestRateValue");
            interestRateElement.innerHTML = data.rate + "%";
            document.getElementById("interestRateValue").value = data.rate ;
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
    document.getElementById('depositAmount').addEventListener('input', function() {
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
    var savingsType = document.getElementById('accountType').value;
    var depositDate = document.getElementById('depositDate').value;
    var depositAmount = document.getElementById('depositAmount').value;
    var term = document.getElementById('term').value;
    var interestRateValue = document.getElementById('interestRateValue').value;
    var interestPaymentMethod = document.getElementById('interestPaymentMethod').value;


    // Lưu dữ liệu vào localStorage
    var savingsAccountInfo = {
        accountName: accountName,
        savingsType: savingsType,
        depositDate: depositDate,
        maturityDate: formatMaturityDate(calculateMaturityDate(depositDate, term)),
        depositAmount: depositAmount,
        term: term,
        status:"active",
        interestRateValue: interestRateValue,
        interestPaymentMethod: interestPaymentMethod
    };
    localStorage.setItem('savingsAccountInfo', JSON.stringify(savingsAccountInfo));

    // Hiển thị thông báo
    // alert('Dữ liệu đã được lưu vào localStorage!');
    window.location.href = "/confirmSA";

    // Có thể chuyển hướng trang sau khi lưu dữ liệu nếu cần
    // window.location.href = "new-page.html";
});

// ===============================function to calculate maturity date===========================
function calculateMaturityDate(depositDate, term) {
    // Chuyển đổi ngày gửi thành đối tượng Date
    var startDate = new Date(depositDate);

    // Sao chép ngày gửi để tránh thay đổi ngày gốc
    var maturityDate = new Date(startDate);

    // Tách số lượng và đơn vị từ term
    var termValue = parseInt(term);
    var termUnit = term.split(' ')[1]; // Lấy đơn vị (tháng, năm) từ term

    // Dựa vào đơn vị, tính toán ngày đáo hạn
    switch (termUnit) {
        case 'tháng':
            maturityDate.setMonth(maturityDate.getMonth() + termValue);
            break;
        case 'năm':
            maturityDate.setFullYear(maturityDate.getFullYear() + termValue);
            break;
        default:
            // Xử lý nếu đơn vị không hợp lệ
            throw new Error('Đơn vị term không hợp lệ.');
    }

    return maturityDate;
}

function formatMaturityDate(maturityDate) {
    // Lấy thông tin về năm, tháng và ngày từ ngày đáo hạn
    var year = maturityDate.getFullYear();
    var month = ("0" + (maturityDate.getMonth() + 1)).slice(-2); // Thêm "0" phía trước và chỉ lấy hai chữ số cuối cùng
    var day = ("0" + maturityDate.getDate()).slice(-2); // Thêm "0" phía trước và chỉ lấy hai chữ số cuối cùng

    // Ghép năm, tháng và ngày thành chuỗi theo định dạng "YYYY-MM-DD"
    var formattedDate = year + "-" + month + "-" + day;

    return formattedDate;
}
