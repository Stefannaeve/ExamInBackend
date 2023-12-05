CREATE TABLE IF NOT EXISTS customer
(
    customer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(255),
    customer_email VARCHAR(255),
    customer_phone VARCHAR(255),
    address_id BIGINT,
    FOREIGN KEY (address_id) REFERENCES factory.address(address_id)
);

CREATE SEQUENCE customer_seq;