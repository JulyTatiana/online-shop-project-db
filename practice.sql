-- LEFT JOIN queries (10)
-- L1
SELECT u.user_id, u.name, up.loyalty_points
FROM users u
LEFT JOIN user_profiles up ON u.user_id = up.user_id;

-- L2 (uses AVG)
SELECT u.user_id, u.name, AVG(r.rating) AS avg_rating
FROM users u
LEFT JOIN reviews r ON u.user_id = r.user_id
GROUP BY u.user_id;

-- L3
SELECT c.cart_id, c.user_id, ci.product_id, ci.quantity
FROM cart c
LEFT JOIN cart_items ci ON c.cart_id = ci.cart_id;

-- L4 (uses SUM)
SELECT c.user_id, SUM(ci.quantity) AS total_items_in_carts
FROM cart c
LEFT JOIN cart_items ci ON c.cart_id = ci.cart_id
GROUP BY c.user_id;

-- L5
SELECT p.product_id, p.name, i.stock, i.reserved
FROM products p
LEFT JOIN inventory i ON p.product_id = i.product_id;

-- L6 (uses MAX)
SELECT cat.category_id, cat.name, MAX(p.price) AS max_price_in_category
FROM categories cat
LEFT JOIN products p ON cat.category_id = p.category_id
GROUP BY cat.category_id;

-- L7
SELECT o.order_id, o.order_number, s.tracking_number, s.status AS shipment_status
FROM orders o
LEFT JOIN shipments s ON o.order_id = s.order_id;

-- L8 (uses SUM)
SELECT o.order_id, SUM(oi.unit_price * oi.quantity - oi.discount_applied) AS computed_order_total
FROM orders o
LEFT JOIN order_items oi ON o.order_id = oi.order_id
GROUP BY o.order_id;

-- L9
SELECT pd.id, pd.product_id, d.code, d.percentage
FROM product_discounts pd
LEFT JOIN discounts d ON pd.discount_id = d.discount_id;

-- L10 (uses MIN)
SELECT u.user_id, u.name, MIN(p.price) AS cheapest_in_wishlist_like
FROM users u
LEFT JOIN cart c ON u.user_id = c.user_id
LEFT JOIN cart_items ci ON c.cart_id = ci.cart_id
LEFT JOIN products p ON ci.product_id = p.product_id
GROUP BY u.user_id;

-----------------------------------------------------------------------

-- RIGHT JOIN queries (10)
-- R1
SELECT up.user_id, up.loyalty_points, u.name
FROM user_profiles up
RIGHT JOIN users u ON up.user_id = u.user_id;

-- R2 (uses AVG)
SELECT p.product_id, p.name, AVG(r.rating) AS avg_rating
FROM reviews r
RIGHT JOIN products p ON r.product_id = p.product_id
GROUP BY p.product_id;

-- R3
SELECT a.address_id, a.user_id, u.username
FROM addresses a
RIGHT JOIN users u ON a.user_id = u.user_id;

-- R4 (uses SUM)
SELECT d.discount_id, d.code, SUM(pd.product_id IS NOT NULL) AS products_using_discount
FROM discounts d
RIGHT JOIN product_discounts pd ON d.discount_id = pd.discount_id
GROUP BY d.discount_id;

-- R5
SELECT p.product_id, p.name, inv.stock
FROM inventory inv
RIGHT JOIN products p ON inv.product_id = p.product_id;

-- R6 (uses MAX)
SELECT o.order_id, o.order_number, MAX(s.shipment_id) AS last_shipment_id
FROM shipments s
RIGHT JOIN orders o ON s.order_id = o.order_id
GROUP BY o.order_id;

-- R7
SELECT oi.id, oi.order_id, o.order_number
FROM order_items oi
RIGHT JOIN orders o ON oi.order_id = o.order_id;

-- R8 (uses SUM)
SELECT u.user_id, u.name, SUM(oi.quantity) AS total_qty_ordered
FROM users u
RIGHT JOIN orders o ON u.user_id = o.user_id
RIGHT JOIN order_items oi ON o.order_id = oi.order_id
GROUP BY u.user_id;

-- R9
SELECT cd.category_id, cd.name, p.product_id
FROM categories cd
RIGHT JOIN products p ON cd.category_id = p.category_id;

-- R10 (uses MIN)
SELECT p.product_id, p.name, MIN(inv.stock) AS min_stock_for_product_record
FROM products p
RIGHT JOIN inventory inv ON p.product_id = inv.product_id
GROUP BY p.product_id;

-----------------------------------------------------------------------

-- INNER JOIN queries (10)
-- I1
SELECT o.order_id, o.order_number, u.user_id, u.name
FROM orders o
INNER JOIN users u ON o.user_id = u.user_id;

-- I2 (uses SUM)
SELECT o.order_id, o.order_number, SUM(oi.quantity * oi.unit_price) AS items_total
FROM orders o
INNER JOIN order_items oi ON o.order_id = oi.order_id
GROUP BY o.order_id;

-- I3
SELECT ci.id, ci.cart_id, c.user_id
FROM cart_items ci
INNER JOIN cart c ON ci.cart_id = c.cart_id;

-- I4 (uses AVG)
SELECT p.product_id, p.name, AVG(oi.unit_price) AS avg_unit_price_in_orders
FROM products p
INNER JOIN order_items oi ON p.product_id = oi.product_id
GROUP BY p.product_id;

-- I5
SELECT pd.id, p.product_id, p.name, d.code
FROM product_discounts pd
INNER JOIN products p ON pd.product_id = p.product_id
INNER JOIN discounts d ON pd.discount_id = d.discount_id;

-- I6
SELECT r.review_id, r.product_id, p.name, r.rating
FROM reviews r
INNER JOIN products p ON r.product_id = p.product_id;

-- I7 (uses MAX)
SELECT u.user_id, u.name, MAX(pay.amount) AS max_payment
FROM users u
INNER JOIN orders o ON u.user_id = o.user_id
INNER JOIN payments pay ON o.order_id = pay.order_id
GROUP BY u.user_id;

-- I8
SELECT o.order_id, o.order_number, pay.payment_id, pay.status
FROM orders o
INNER JOIN payments pay ON o.order_id = pay.order_id;

-- I9 (uses MIN)
SELECT p.product_id, p.name, MIN(oi.quantity) AS min_ordered_quantity
FROM products p
INNER JOIN order_items oi ON p.product_id = oi.product_id
GROUP BY p.product_id;

-- I10
SELECT s.shipment_id, s.order_id, o.order_number, s.carrier
FROM shipments s
INNER JOIN orders o ON s.order_id = o.order_id;

-----------------------------------------------------------------------

-- Big query joining the whole database
SELECT
  u.user_id,
  u.name AS user_name,
  up.loyalty_points,
  a.address_id,
  a.street,
  a.city,
  c.cart_id,
  COALESCE(SUM(ci.quantity),0) AS cart_items_total,
  o.order_id,
  o.order_number,
  o.total_amount,
  COALESCE(SUM(oi.quantity * oi.unit_price - oi.discount_applied),0) AS computed_order_value,
  pay.payment_id,
  pay.amount AS payment_amount,
  s.shipment_id,
  s.tracking_number,
  p.product_id,
  p.name AS product_name,
  inv.stock,
  inv.reserved,
  AVG(r.rating) AS avg_product_rating
FROM users u
LEFT JOIN user_profiles up ON u.user_id = up.user_id
LEFT JOIN addresses a ON u.user_id = a.user_id
LEFT JOIN cart c ON u.user_id = c.user_id
LEFT JOIN cart_items ci ON c.cart_id = ci.cart_id
LEFT JOIN products p ON ci.product_id = p.product_id
LEFT JOIN inventory inv ON p.product_id = inv.product_id
LEFT JOIN orders o ON u.user_id = o.user_id
LEFT JOIN order_items oi ON o.order_id = oi.order_id
LEFT JOIN payments pay ON o.order_id = pay.order_id
LEFT JOIN shipments s ON o.order_id = s.order_id
LEFT JOIN reviews r ON p.product_id = r.product_id
LEFT JOIN product_discounts pd ON p.product_id = pd.product_id
LEFT JOIN discounts d ON pd.discount_id = d.discount_id
LEFT JOIN categories cat ON p.category_id = cat.category_id
GROUP BY u.user_id, o.order_id, p.product_id, pay.payment_id, s.shipment_id, a.address_id, c.cart_id;

-----------------------------------------------------------------------

-- GROUP BY queries (3)
-- G1
SELECT p.category_id, COUNT(*) AS products_count, AVG(p.price) AS avg_price
FROM products p
GROUP BY p.category_id;

-- G2 (uses SUM and MIN)
SELECT o.user_id, COUNT(o.order_id) AS orders_count, SUM(o.total_amount) AS total_spent, MIN(o.total_amount) AS smallest_order
FROM orders o
GROUP BY o.user_id;

-- G3
SELECT pd.discount_id, d.code, COUNT(pd.product_id) AS products_covered
FROM product_discounts pd
LEFT JOIN discounts d ON pd.discount_id = d.discount_id
GROUP BY pd.discount_id;

-----------------------------------------------------------------------

-- HAVING queries (3)
-- H1 
SELECT p.product_id, p.name, AVG(r.rating) AS avg_rating
FROM products p
LEFT JOIN reviews r ON p.product_id = r.product_id
GROUP BY p.product_id
HAVING AVG(r.rating) >= 4.5;

-- H2
SELECT u.user_id, u.name, SUM(oi.quantity * oi.unit_price) AS total_spent
FROM users u
JOIN orders o ON u.user_id = o.user_id
JOIN order_items oi ON o.order_id = oi.order_id
GROUP BY u.user_id
HAVING SUM(oi.quantity * oi.unit_price) > 1000.00;

-- H3 
SELECT d.discount_id, d.code, COUNT(pd.product_id) AS uses
FROM discounts d
LEFT JOIN product_discounts pd ON d.discount_id = pd.discount_id
GROUP BY d.discount_id
HAVING COUNT(pd.product_id) >= 1;

-----------------------------------------------------------------------

-- Additional aggregate queries
-- A1 (SUM)
SELECT o.order_id, SUM(oi.quantity) AS total_units_in_order, SUM(oi.unit_price * oi.quantity) AS gross_amount
FROM order_items oi
JOIN orders o ON oi.order_id = o.order_id
GROUP BY o.order_id;

-- A2 (AVG)
SELECT p.product_id, AVG(r.rating) AS avg_rating, COUNT(r.review_id) AS reviews_count
FROM products p
LEFT JOIN reviews r ON p.product_id = r.product_id
GROUP BY p.product_id;

-- A3 (MAX)
SELECT product_id, MAX(stock) AS max_stock_across_inventory_record
FROM inventory
GROUP BY product_id;

-- A4 (MIN)
SELECT pay.payment_id, MIN(pay.amount) AS min_payment_amount
FROM payments pay
GROUP BY pay.payment_id;

-- A5 (SUM)
SELECT u.user_id, SUM(ci.quantity) AS sum_cart_qty
FROM users u
LEFT JOIN cart c ON u.user_id = c.user_id
LEFT JOIN cart_items ci ON c.cart_id = ci.cart_id
GROUP BY u.user_id;

-- A6 (AVG)
SELECT cat.category_id, AVG(p.price) AS avg_price_category
FROM categories cat
LEFT JOIN products p ON cat.category_id = p.category_id
GROUP BY cat.category_id;

-- A7 (MAX)
SELECT u.user_id, MAX(up.loyalty_points) AS max_loyalty_points
FROM users u
LEFT JOIN user_profiles up ON u.user_id = up.user_id
GROUP BY u.user_id;

-- A8 (MIN)
SELECT d.discount_id, MIN(d.minimum_purchase) AS min_requirement
FROM discounts d
GROUP BY d.discount_id;

-- A9 (SUM)
SELECT p.product_id, SUM(oi.quantity) AS total_sold_quantity
FROM products p
LEFT JOIN order_items oi ON p.product_id = oi.product_id
GROUP BY p.product_id;

-- A10 (AVG)
SELECT o.order_id, AVG(oi.unit_price) AS avg_item_price_in_order
FROM orders o
LEFT JOIN order_items oi ON o.order_id = oi.order_id
GROUP BY o.order_id;