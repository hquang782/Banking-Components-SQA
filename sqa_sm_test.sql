
CREATE DATABASE  IF NOT EXISTS `sqa_sm_test` ;
USE `sqa_sm_test`;

CREATE TABLE IF NOT EXISTS `accounts` (
    `id` BINARY(16) NOT NULL,
    `balance` DOUBLE DEFAULT NULL,
    `password` VARCHAR(255) NOT NULL,
    `username` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE (`username`)
);


CREATE TABLE IF NOT EXISTS `customers` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `address` VARCHAR(255) DEFAULT NULL,
    `age` INT(11) DEFAULT NULL,
    `bank_account_number` VARCHAR(255) DEFAULT NULL,
    `dob` VARCHAR(255) DEFAULT NULL,
    `email` VARCHAR(255) NOT NULL,
    `full_name` VARCHAR(255) NOT NULL,
    `gender` VARCHAR(255) DEFAULT NULL,
    `identification_number` VARCHAR(255) DEFAULT NULL,
    `phone_number` VARCHAR(255) DEFAULT NULL,
    `account_id` BINARY(16) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY (`bank_account_number`),
    UNIQUE KEY (`identification_number`),
    UNIQUE KEY (`account_id`),
    FOREIGN KEY (`account_id`)
        REFERENCES `accounts` (`id`)
);

CREATE TABLE IF NOT EXISTS `interest_rates` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `rate` DOUBLE NOT NULL,
    `term` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
);



CREATE TABLE IF NOT EXISTS `savings_accounts` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `account_name` VARCHAR(255) NOT NULL,
    `account_number` VARCHAR(255) DEFAULT NULL,
    `deposit_amount` DOUBLE DEFAULT NULL,
    `deposit_date` DATE DEFAULT NULL,
    `interest_payment_method` VARCHAR(255) DEFAULT NULL,
    `interestrate_value` DOUBLE DEFAULT NULL,
    `maturity_date` DATE DEFAULT NULL,
    `savings_type` VARCHAR(255) DEFAULT NULL,
    `status` VARCHAR(255) DEFAULT NULL,
    `term` VARCHAR(255) DEFAULT NULL,
    `total_amount` DOUBLE DEFAULT NULL,
    `customer_id` BIGINT(20) DEFAULT NULL,
    `interestrate_id` BIGINT(20) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE (`account_number`),
    FOREIGN KEY (`customer_id`)
        REFERENCES `customers` (`id`),
    FOREIGN KEY (`interestrate_id`)
        REFERENCES `interest_rates` (`id`)
);
INSERT INTO interest_rates (id, rate, term) VALUES
                                                (1, 1.7, '1 tháng'),
                                                (2, 3, '6 tháng'),
                                                (3, 3, '9 tháng'),
                                                (4, 0.1, 'Không kỳ hạn'),
                                                (5, 1.7, '2 tháng'),
                                                (6, 2, '3 tháng'),
                                                (7, 2, '5 tháng'),
                                                (8, 4.7, '12 tháng'),
                                                (9, 4.7, '13 tháng'),
                                                (10, 4.7, '15 tháng'),
                                                (11, 4.7, '18 tháng'),
                                                (12, 4.7, '24 tháng'),
                                                (13, 4.7, '36 tháng');

INSERT INTO accounts (id, username, password, balance)
VALUES (UUID(), 'test', '$2a$12$CE3biMgMJMs75lDgt5TbrudWbg1obADCh.TRdIjNrblKw.WGhxz5S', 1000.0),
       (UUID(), 'jane_doe', 'password123', 1500.0),
       (UUID(), 'jim_smith', 'test123', 2000.0);

-- Lấy id của các tài khoản từ bảng accounts
-- Thay thế UUID1, UUID2, UUID3 bằng các giá trị UUID tương ứng từ bảng accounts
SET @UUID1 = (SELECT id FROM accounts WHERE username = 'test');
SET @UUID2 = (SELECT id FROM accounts WHERE username = 'jane_doe');
SET @UUID3 = (SELECT id FROM accounts WHERE username = 'jim_smith');

-- Insert dữ liệu vào bảng customers với các account_id tương ứng
INSERT INTO customers (id, full_name, age, dob, gender, address, email, phone_number, identification_number, bank_account_number, account_id) VALUES
                                                                                                                                                  (1, 'John Doe', 30, '1992-05-15', 'Male', '123 Main Street', 'john.doe@example.com', '123456789', '123456789', '987654321', @UUID1),
                                                                                                                                                  (2, 'Jane Doe', 28, '1994-08-20', 'Female', '456 Elm Street', 'jane.doe@example.com', '987654321', '987654321', '123456789', @UUID2),
                                                                                                                                                  (3, 'Jim Smith', 35, '1989-12-10', 'Male', '789 Oak Street', 'jim.smith@example.com', '456789123', '456789123', '654321987', @UUID3);