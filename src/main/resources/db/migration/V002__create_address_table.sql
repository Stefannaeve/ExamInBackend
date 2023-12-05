CREATE TABLE IF NOT EXISTS address
(
    address_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    address VARCHAR(255),
    customer_id BIGINT,
    FOREIGN KEY (customer_id) REFERENCES factory.customer(customer_id)
);