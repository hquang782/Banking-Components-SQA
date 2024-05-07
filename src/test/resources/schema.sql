
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
