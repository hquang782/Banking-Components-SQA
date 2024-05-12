


document.getElementById("registrationForm").addEventListener("submit", function(event) {
    event.preventDefault();

    const form = event.target;
    const formData = new FormData(form);

    const registerDto = {};
    formData.forEach(function(value, key) {
        registerDto[key] = value;
    });

    fetch("http://127.0.0.1:8080/api/auth/signup", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(registerDto)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text();
        })
        .then(data => {
            // Kiểm tra xem dữ liệu phản hồi là thông báo lỗi hay không
            if (data === "Username, identification number, or bank account number already exists") {
                alert(data); // Hiển thị thông báo lỗi
            } else {
                alert("Registration successful"); // Hiển thị thông báo đăng ký thành công
                // Chuyển hướng người dùng đến trang chủ sau khi đăng ký thành công
                window.location.href = "/login";
            }
        })
        .catch(error => {
            console.error("Error:", error);
            alert("An error occurred. Please try again later.");
        });
});

document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("fullName").focus();
    // ===========================================Function to validate full name===========================================
    // Function to show error message
    function showError(fieldId, errorMessage) {
        let errorDiv = document.getElementById(fieldId + "Error");
        errorDiv.textContent = errorMessage;
        errorDiv.classList.add("error-message");
    }


    // Function to validate full name field
    function validateFullName(fullName) {
        // Check if fullName contains numbers or special characters
        if (/[\d!@#$%^&*()_+=[\]{};':"\\|,.<>/?~]/.test(fullName)) {
            showError("fullName", "Vui lòng nhập tên đầy đủ hợp lệ, không có số hoặc ký tự đặc biệt.");
            return false;
        }
        return true;
    }

    // Function to format full name
    function formatFullName(fullName) {
        let words = fullName.split(" ");
        let formattedFullName = words.map(word => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase()).join(" ");
        return formattedFullName;
    }

    // Function to hide error message
    function hideError(fieldId) {
        let errorDiv = document.getElementById(fieldId + "Error");
        errorDiv.textContent = "";
        errorDiv.classList.remove("error-message");
    }

    // Event listener for full name field blur event
    document.getElementById("fullName").addEventListener("blur", function() {
        let fullName = this.value.trim();
        if (!validateFullName(fullName)) {
            this.value = "";
            this.focus();
        } else {
            hideError("fullName");
            // Format full name
            let formattedFullName = formatFullName(fullName);
            // Set the formatted full name back to the field
            this.value = formattedFullName;
        }
    });

    // ================================Function to validate age field========================================
    document.getElementById('dob').addEventListener('change', function() {
        var dob = new Date(this.value);
        var today = new Date();
        var age = today.getFullYear() - dob.getFullYear();
        var m = today.getMonth() - dob.getMonth();

        if (m < 0 || (m === 0 && today.getDate() < dob.getDate())) {
            age--;
        }

        if (age >= 18 ) {
            document.getElementById('age').value = age;
            document.getElementById('ageError').innerText = "";
        } else {
            document.getElementById('age').value = "";
            showError("age", "Người tạo chưa đủ 18 tuổi.");
        }
    });
    //=========================== Function to validate email field================================
    function validateAddress(email) {
        // Regular expression to validate email format
        let emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

        // Check if address matches email format
        if (!emailRegex.test(email)) {
            showError("email", "Vui lòng nhập địa chỉ email hợp lệ.");
            return false;
        }

        // Check if address starts with a letter or number
        if (!/^[a-zA-Z0-9]/.test(email[0])) {
            showError("email", "Địa chỉ email không được bắt đầu bằng ký tự đặc biệt.");
            return false;
        }
        return true;
    }

    // Event listener for address field blur event
    document.getElementById("email").addEventListener("blur", function() {
        let email = this.value.trim();
        if (!validateAddress(email)) {
            this.value = "";
            // this.focus();
        } else {
            hideError("email");
        }
    });

    // ==================================================Function to validate phoneNumber and identificationNumber field==================================================

    function validatePhoneNumber(phoneNumber) {
        // Kiểm tra xem dữ liệu nhập vào có phải là toàn số không
        if (!/^\d+$/.test(phoneNumber)) {
            return false;
        }
        return true;
    }

    function validateIdentificationNumber(identificationNumber) {
        // Kiểm tra xem dữ liệu nhập vào có phải là toàn số không
        identificationNumber = identificationNumber.toString(); // Chuyển số thành chuỗi
        // Kiểm tra xem dữ liệu nhập vào có phải là toàn số không và có độ dài là 12 hoặc 10
        return /^\d+$/.test(identificationNumber) && (identificationNumber.length === 12 || identificationNumber.length === 10);
    }

// Sử dụng sự kiện blur để kiểm tra dữ liệu khi mất focus khỏi trường nhập liệu
    document.getElementById("phoneNumber").addEventListener("blur", function() {
        let phoneNumber = this.value.trim();
        if (!validatePhoneNumber(phoneNumber)) {
            showError("phoneNumber", "Số điện thoại không hợp lệ. Vui lòng chỉ nhập số.");
            this.value = "";
            this.focus();
        } else {
            hideError("phoneNumber");
        }
    });

    document.getElementById("identificationNumber").addEventListener("blur", function() {
        let identificationNumber = this.value.trim();
        if (!validateIdentificationNumber(identificationNumber)) {
            showError("identificationNumber", "Số không hợp lệ.(CMND:10 số/ CCCD:12 số)");
            this.value = "";
            this.focus();
        } else {
            hideError("identificationNumber");
        }
    });

    // =====================================validate password =======================================
    document.getElementById("password").addEventListener('blur',function (){
        var password = this.value;
        if (!validatePassword(password)) {
            // Mật khẩu không hợp lệ, thực hiện hành động tương ứng ở đây
            showError("password1","Mật khẩu không hợp lệ! Mật khẩu phải có ít nhất 8 kí tự và phải có chữ hoa, chữ thường và kí tự đặc biệt.");
            // Ví dụ: reset lại giá trị của ô nhập mật khẩu
            this.value = "";
            this.focus();
        } else {
            hideError("password1");
        }
    });

    function validatePassword(password) {
        // Kiểm tra độ dài
        if (password.length < 8) {
            return false; // Mật khẩu không đủ dài
        }

        // Kiểm tra chứa ít nhất một chữ hoa, một chữ thường và một ký tự đặc biệt
        var hasUppercase = /[A-Z]/.test(password);
        var hasLowercase = /[a-z]/.test(password);
        var hasSpecialChar = /[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]/.test(password);

        if (!hasUppercase || !hasLowercase || !hasSpecialChar) {
            return false; // Mật khẩu không đủ mạnh
        }

        return true; // Mật khẩu hợp lệ
    }
    // ============================================END===============================================


    // Set default value to phoneNumber
    document.getElementById("bankAccountType").value = "phoneNumber";

    // Function to generate random account number
    function generateRandomAccountNumber() {
        // Generate random number between 10^9 and 10^12
        let min = Math.pow(10, 9);
        let max = Math.pow(10, 12);
        let randomNumber = Math.floor(Math.random() * (max - min + 1)) + min;
        return randomNumber.toString();
    }
    // Function to update bank account number field
    function updateBankAccountNumberField(value) {
        document.getElementById("bankAccountNumber").value = value;
    }
    // Event listener for select change
    document.getElementById("bankAccountType").addEventListener("change", function() {
        let selectedOption = this.value;
        if (selectedOption === "phoneNumber") {
            // Show phone number in bank account number field
            let phoneNumber = document.getElementById("phoneNumber").value;
            updateBankAccountNumberField(phoneNumber);
        } else if (selectedOption === "random") {
            // Generate random account number
            let randomAccountNumber = generateRandomAccountNumber();
            updateBankAccountNumberField(randomAccountNumber);
        }
    });
    // Event listener for input change in phone number field
    document.getElementById("phoneNumber").addEventListener("input", function() {
        let selectedOption = document.getElementById("bankAccountType").value;
        if (selectedOption === "phoneNumber") {
            // Update bank account number field with the entered phone number
            updateBankAccountNumberField(this.value);
        }
    });
    document.getElementById("confirmPassword").addEventListener("input", function() {
        let password = document.getElementById("password").value;
        let confirmPassword = this.value;

        if (password !== confirmPassword) {
            document.getElementById("passwordError").style.display = "block";
        } else {
            document.getElementById("passwordError").style.display = "none";
        }
    });
});

