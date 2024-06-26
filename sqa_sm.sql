CREATE DATABASE  IF NOT EXISTS `sqa_sm` ;
USE `sqa_sm`;

CREATE TABLE `interest_rates` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rate` double NOT NULL,
  `term` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ;
INSERT INTO `interest_rates` VALUES (1,1.7,'1 tháng'),(2,3,'6 tháng'),(3,3,'9 tháng'),(4,0.1,'Không kỳ hạn'),(5,1.7,'2 tháng'),(6,2,'3 tháng'),(7,2,'5 tháng'),(8,4.7,'12 tháng'),(9,4.7,'13 tháng'),(10,4.7,'15 tháng'),(11,4.7,'18 tháng'),(12,4.7,'24 tháng'),(13,4.7,'36 tháng');
