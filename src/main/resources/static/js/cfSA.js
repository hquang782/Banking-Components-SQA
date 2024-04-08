// Lấy thông tin đã nhập từ localStorage
let savingsAccountInfo = JSON.parse(localStorage.getItem('savingsAccountInfo'));
if (!savingsAccountInfo || Object.keys(savingsAccountInfo).length === 0) {
    window.location.href = "/enter";
} else {
    // Hiển thị thông tin người dùng trong trang

    document.getElementById('accountName').innerText =  savingsAccountInfo.accountName;
    document.getElementById('accountType').innerText =  savingsAccountInfo.savingsType;
    // xét Giá trị cho từng trường ID
    // document.getElementById('accountName').value = accountInfo.accountName ;
    // document.getElementById('accountType').value = accountInfo.accountType;
    // Chuỗi ngày cần được định dạng lại
    // var dateString =  accountInfo.depositDate ;

// Tạo một đối tượng Date từ chuỗi ngày
//     var date = new Date(dateString);

// Lấy ngày, tháng, năm từ đối tượng Date
//     var day = date.getDate();
//     var month = date.getMonth() + 1; // Tháng trong JavaScript bắt đầu từ 0, nên cần cộng thêm 1
//     var year = date.getFullYear();

    // Định dạng lại thành "dd/MM/yyyy"
    // var formattedDate = (day < 10 ? '0' : '') + day + '/' + (month < 10 ? '0' : '') + month + '/' + year;

    // console.log(day); // Kết quả: 02/12/2024
    document.getElementById('depositDate').innerText = savingsAccountInfo.depositDate ;
    // document.getElementById('depositDate').value = formattedDate ;
    document.getElementById("maturityDate").innerText = savingsAccountInfo.maturityDate;
    document.getElementById('depositAmount').innerText = savingsAccountInfo.depositAmount;
    // document.getElementById('depositAmount').value = accountInfo.depositAmount
    document.getElementById('term').innerText =  savingsAccountInfo.term;
    // document.getElementById('term').value = accountInfo.term ;
    document.getElementById('interestPaymentMethod').innerText = savingsAccountInfo.interestPaymentMethod;
    // document.getElementById('interestPaymentMethod').value = accountInfo.interestPaymentMethod ;
    document.getElementById('interestRateValue').innerText =  savingsAccountInfo.interestRateValue;
    // document.getElementById('interestRateId').value =  accountInfo.interestRateId;
    // var term = document.getElementById('term').value;
    // var isMonthTerm = term.includes("tháng") ;
    // if(isMonthTerm) {
    //     var termValue = parseInt(term);
    //     month += termValue
    //
    //     if(month > 12){
    //         month -= 12 ;
    //         year += 1 ;
    //     }
    // }
    // else{
    //     var termValue = parseInt(term) ;
    //     year += termValue ;
    // }
    // console.log(day) ;
    // console.log(month) ;
    // console.log(year) ;
    // var formattedDateNew = (day < 10 ? '0' : '') + day + '/' + (month < 10 ? '0' : '') + month + '/' + year;
    // document.getElementById('maturityDate').innerText =  formattedDateNew;
    // document.getElementById('maturityDate').value =  formattedDateNew;

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

