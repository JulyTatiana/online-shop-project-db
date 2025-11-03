-- DATABASE CREATION
-- -------------------------

CREATE DATABASE IF NOT EXISTS online_shop_project
CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;
USE online_shop_project;

SET FOREIGN_KEY_CHECKS = 0;

-- 1) users
CREATE TABLE IF NOT EXISTS users (
user_id INT UNSIGNED NOT NULL PRIMARY KEY,
name VARCHAR(255) NOT NULL,
username VARCHAR(150) NOT NULL,
user_type VARCHAR(50) NOT NULL,
priority INT NOT NULL
);

-- 2) user_profiles
CREATE TABLE IF NOT EXISTS user_profiles (
user_id INT UNSIGNED NOT NULL PRIMARY KEY,
loyalty_points INT DEFAULT 0,
preferences JSON NULL,
CONSTRAINT fk_user_profiles_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- 3) addresses
CREATE TABLE IF NOT EXISTS addresses (
address_id INT UNSIGNED NOT NULL PRIMARY KEY,
user_id INT UNSIGNED NOT NULL,
street VARCHAR(255) NULL,
city VARCHAR(100) NULL,
CONSTRAINT fk_addresses_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- 4) categories
CREATE TABLE IF NOT EXISTS categories (
category_id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(200) NOT NULL,
parent_id INT UNSIGNED NULL,
CONSTRAINT fk_categories_parent FOREIGN KEY (parent_id) REFERENCES categories(category_id) ON DELETE SET NULL ON UPDATE CASCADE
);

-- 5) products
CREATE TABLE IF NOT EXISTS products (
product_id VARCHAR(64) NOT NULL PRIMARY KEY,
name VARCHAR(255) NOT NULL,
price DECIMAL(12,2) NOT NULL DEFAULT 0.00,
category_id INT UNSIGNED NULL,
status ENUM('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',
CONSTRAINT fk_products_category FOREIGN KEY (category_id) REFERENCES categories(category_id) ON DELETE SET NULL ON UPDATE CASCADE
);

-- 6) discounts
CREATE TABLE IF NOT EXISTS discounts (
discount_id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
code VARCHAR(100) NOT NULL,
percentage DECIMAL(5,2) NOT NULL DEFAULT 0.00,
minimum_purchase DECIMAL(12,2) NOT NULL DEFAULT 0.00,
description TEXT NULL
);

-- 7) product_discounts (many-to-many)
CREATE TABLE IF NOT EXISTS product_discounts (
id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
product_id VARCHAR(64) NOT NULL,
discount_id INT UNSIGNED NOT NULL,
CONSTRAINT fk_product_discounts_product FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT fk_product_discounts_discount FOREIGN KEY (discount_id) REFERENCES discounts(discount_id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- 8) cart
CREATE TABLE IF NOT EXISTS cart (
cart_id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
user_id INT UNSIGNED NOT NULL,
total_cart_items INT UNSIGNED DEFAULT 0,
CONSTRAINT fk_cart_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- 9) cart_items
CREATE TABLE IF NOT EXISTS cart_items (
id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
cart_id INT UNSIGNED NOT NULL,
product_id VARCHAR(64) NOT NULL,
quantity INT UNSIGNED NOT NULL DEFAULT 1,
CONSTRAINT fk_cartitems_cart FOREIGN KEY (cart_id) REFERENCES cart(cart_id) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT fk_cartitems_product FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- 10) orders
CREATE TABLE IF NOT EXISTS orders (
order_id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
order_number VARCHAR(64) NOT NULL,
user_id INT UNSIGNED NOT NULL,
billing_address_id INT UNSIGNED NULL,
shipping_address_id INT UNSIGNED NULL,
status VARCHAR(50) DEFAULT 'PENDING',
payment_method VARCHAR(50) NULL,
total_amount DECIMAL(14,2) DEFAULT 0.00,
CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT fk_orders_billing_address FOREIGN KEY (billing_address_id) REFERENCES addresses(address_id) ON DELETE SET NULL ON UPDATE CASCADE,
CONSTRAINT fk_orders_shipping_address FOREIGN KEY (shipping_address_id) REFERENCES addresses(address_id) ON DELETE SET NULL ON UPDATE CASCADE
);

-- 11) order_items
CREATE TABLE IF NOT EXISTS order_items (
id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
order_id INT UNSIGNED NOT NULL,
product_id VARCHAR(64) NOT NULL,
quantity INT UNSIGNED NOT NULL DEFAULT 1,
unit_price DECIMAL(12,2) NOT NULL DEFAULT 0.00,
discount_applied DECIMAL(12,2) NOT NULL DEFAULT 0.00,
CONSTRAINT fk_orderitems_order FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT fk_orderitems_product FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- 12) payments
CREATE TABLE IF NOT EXISTS payments (
payment_id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
order_id INT UNSIGNED NOT NULL,
payment_method VARCHAR(50) NULL,
amount DECIMAL(14,2) NOT NULL DEFAULT 0.00,
transaction_reference VARCHAR(255) NULL,
status VARCHAR(50) NULL,
CONSTRAINT fk_payments_order FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- 13) inventory (1:1 with products)
CREATE TABLE IF NOT EXISTS inventory (
product_id VARCHAR(64) NOT NULL PRIMARY KEY,
stock INT UNSIGNED DEFAULT 0,
reserved INT UNSIGNED DEFAULT 0,
CONSTRAINT fk_inventory_product FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- 14) reviews
CREATE TABLE IF NOT EXISTS reviews (
review_id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
product_id VARCHAR(64) NOT NULL,
user_id INT UNSIGNED NOT NULL,
rating INT NOT NULL,
comment TEXT NULL,
CONSTRAINT fk_reviews_product FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT fk_reviews_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- 15) shipments
CREATE TABLE IF NOT EXISTS shipments (
shipment_id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
order_id INT UNSIGNED NOT NULL,
tracking_number VARCHAR(255) NULL,
carrier VARCHAR(100) NULL,
status VARCHAR(100) NULL,
CONSTRAINT fk_shipments_order FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE ON UPDATE CASCADE
);

SET FOREIGN_KEY_CHECKS = 1;

-- -------------------------
-- INSERTS 
-- -------------------------

-- 1) users
INSERT INTO users (user_id, name, username, user_type, priority) VALUES
(1, 'James Smith', 'jamesmith', 'Customer', 2),
(2, 'Alice Johnson', 'alicej', 'Customer', 2),
(3, 'Bob Smith', 'bobsmith', 'VIP Customer', 3),
(4, 'Isabella Ramirez', 'isabellar', 'VIP Customer', 3),
(5, 'Charlie Admin', 'charliea', 'Admin', 4),
(6, 'Thomas A', 'thomasa', 'Cashier', 5);

-- 2) user_profiles
INSERT INTO user_profiles (user_id, loyalty_points, preferences) VALUES
(1, 120, JSON_OBJECT('theme','dark','language','en')),
(3, 500, JSON_OBJECT('theme','light','language','en')),
(5, 1000, JSON_OBJECT('theme','system')),
(6, 700, JSON_OBJECT('theme','system'));

-- 3) addresses
INSERT INTO addresses (address_id, user_id, street, city) VALUES
(1, 1, '123 Main St', 'New York'),
(2, 2, '456 Work Ave', 'New York'),
(3, 3, '789 Luxury Rd', 'Miami'),
(4, 4, '7th Avenue', 'Orlando'),
(5, 5, 'Street 80', 'Orlando'),
(6, 6, 'Street 32, Av 12', 'Miami');

-- 4) categories
INSERT INTO categories (category_id, name, parent_id) VALUES
(1, 'Electronics', NULL),
(2, 'Accessories', NULL);

-- 5) products
INSERT INTO products (product_id, name, price, category_id, status) VALUES
('SKU-1001', 'Cellphone Gen 26', 1100.00, 1, 'ACTIVE'),
('SKU-1002', 'Laptop Gen 26', 1950.00, 1, 'ACTIVE'),
('SKU-1003', 'Laptop Case', 29.99, 2, 'ACTIVE'),
('SKU-1004', 'Deluxe Laptop Case', 39.99, 2, 'ACTIVE'),
('SKU-1005', 'Retro Headphones', 499.00, 2, 'ACTIVE');

-- 6) discounts
INSERT INTO discounts (code, percentage, minimum_purchase, description) VALUES
('TEN_OFF', 10.00, 500.00, '10% off on orders above $500'),
('FIFTEEN_OFF', 15.00, 500.00, '15% off on orders above $500'),
('TWENTY_OFF', 20.00, 750.00, '20% off on orders above $750'),
('VIP_THIRTY_OFF', 30.00, 200.00, '30% off on orders above $200, only for VIP members');

-- 7) product_discounts
INSERT INTO product_discounts (product_id, discount_id) VALUES
('SKU-1001', 1),
('SKU-1002', 2);

-- 8) cart
INSERT INTO cart (cart_id, user_id, total_cart_items) VALUES
(1, 1, 3);

-- 9) cart_items
INSERT INTO cart_items (cart_id, product_id, quantity) VALUES
(1, 'SKU-1001', 1),
(1, 'SKU-1002', 2);

-- 10) orders
INSERT INTO orders (order_id, order_number, user_id, billing_address_id, shipping_address_id, status, payment_method, total_amount) VALUES
(1, 'ORD-10001', 1, 1, 1, 'PENDING', 'Card', 3079.99),
(2, 'ORD-10002', 2, 3, 3, 'PROCESSING', 'Cash', 539.98);

-- 11) order_items (reference order_id numeric)
INSERT INTO order_items (order_id, product_id, quantity, unit_price, discount_applied) VALUES
(1, 'SKU-1001', 2000, 1100.00, 0.00),
(1, 'SKU-1002', 3000, 1950.00, 0.00),
(1, 'SKU-1003', 756, 29.99, 0.00),
(2, 'SKU-1004', 756, 39.99, 0.00),
(2, 'SKU-1005', 756, 499.99, 0.00);

-- 12) payments
INSERT INTO payments (order_id, payment_method, amount, transaction_reference, status) VALUES
(1, 'cash', 3079.99, 'TXN-ABC123', 'COMPLETED'),
(2, 'card', 539.98, 'TXN-XYZ789', 'PENDING');

-- 13) inventory (1:1 with products)
INSERT INTO inventory (product_id, stock, reserved) VALUES
('SKU-1001', 200, 5),
('SKU-1002', 567, 10),
('SKU-1003', 499, 2),
('SKU-1004', 45, 1),
('SKU-1005', 456, 10);

-- 14) reviews
INSERT INTO reviews (product_id, user_id, rating, comment) VALUES
('SKU-1001', 1, 10, 'Amazing cell phone!'),
('SKU-1002', 3, 6, 'The laptop could be better');

-- 15) shipments
INSERT INTO shipments (order_id, tracking_number, carrier, status) VALUES
(1, 'TRACK123', 'UPS', 'IN_TRANSIT'),
(2, 'TRACK456', 'FedEx', 'LABEL_CREATED');
