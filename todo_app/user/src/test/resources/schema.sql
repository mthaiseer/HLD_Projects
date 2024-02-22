CREATE TABLE  `users_table` (
`customer_id`  int AUTO_INCREMENT  PRIMARY KEY,
`customer_name` varchar(100) DEFAULT NULL,
`email` varchar(100) DEFAULT NULL,
`password` varchar(100) DEFAULT NULL,
`created_at` date  NULL,
`created_by` varchar(20)  NULL,
`updated_at` date DEFAULT NULL,   
`updated_by` varchar(20) DEFAULT NULL


);