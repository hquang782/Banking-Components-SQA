// Lấy thông tin đã nhập từ localStorage
var accountInfo = JSON.parse(localStorage.getItem('accountInfo'));

// Kiểm tra xem userInfo có giá trị hay không
if (!accountInfo || Object.keys(accountInfo).length === 0) {
    // Nếu userInfo trống, chuyển hướng đến trang đăng nhập
    window.location.href = "/enter";
} else {
    // Hiển thị thông tin người dùng trong trang

    document.getElementById('accountName').innerText =  accountInfo.accountName;
    document.getElementById('accountType').innerText =  accountInfo.accountType;
    // xét Giá trị cho từng trường ID
    document.getElementById('accountName').value = accountInfo.accountName ;
    document.getElementById('accountType').value = accountInfo.accountType;
    // Chuỗi ngày cần được định dạng lại
    var dateString =  accountInfo.dipositDate ;

// Tạo một đối tượng Date từ chuỗi ngày
    var date = new Date(dateString);

// Lấy ngày, tháng, năm từ đối tượng Date
    var day = date.getDate();
    var month = date.getMonth() + 1; // Tháng trong JavaScript bắt đầu từ 0, nên cần cộng thêm 1
    var year = date.getFullYear();

    // Định dạng lại thành "dd/MM/yyyy"
    var formattedDate = (day < 10 ? '0' : '') + day + '/' + (month < 10 ? '0' : '') + month + '/' + year;

    console.log(day); // Kết quả: 02/12/2024
    document.getElementById('dipositDate').innerText = formattedDate ;
    document.getElementById('dipositDate').value = formattedDate ;
        // Biến đồi ngày đáo hạn
    document.getElementById('dispositAmount').innerText = accountInfo.dispositAmount;
    document.getElementById('dispositAmount').value = accountInfo.dispositAmount
    document.getElementById('term').innerText =  accountInfo.term;
    document.getElementById('term').value = accountInfo.term ;
    document.getElementById('interestPaymentMethod').innerText = accountInfo.interestPaymentMethod;
    document.getElementById('interestPaymentMethod').value = accountInfo.interestPaymentMethod ;
    document.getElementById('interestRateId').innerText =  accountInfo.interestRateId;
    document.getElementById('interestRateId').value =  accountInfo.interestRateId;
    document.getElementById('term').value = accountInfo.term ;
    var term = document.getElementById('term').value;
    var isMonthTerm = term.includes("tháng") ;
    if(isMonthTerm) {
        var termValue = parseInt(term);
        month += termValue

        if(month > 12){
            month -= 12 ;
            year += 1 ;
        }
    }
    else{
        var termValue = parseInt(term) ;
        year += termValue ;
    }
    console.log(day) ;
    console.log(month) ;
    console.log(year) ;
    var formattedDateNew = (day < 10 ? '0' : '') + day + '/' + (month < 10 ? '0' : '') + month + '/' + year;
    document.getElementById('maturityDate').innerText =  formattedDateNew;
    document.getElementById('maturityDate').value =  formattedDateNew;




}


