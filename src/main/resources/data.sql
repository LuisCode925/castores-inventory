INSERT INTO roles (id, name, created_at) VALUES  (1, 'ADMINISTRATOR', NOW());
INSERT INTO roles (id, name, created_at) VALUES  (2, 'WAREHOUSEMAN', NOW());
INSERT INTO roles (id, name, created_at) VALUES  (3, 'USER', NOW());

INSERT INTO products (id, name, stock, price, is_available) VALUES  (1, 'LAPTOP', 10, 3000.00, true);
INSERT INTO products (id, name, stock, price, is_available) VALUES  (2, 'PC', 10, 4000.00, true);
INSERT INTO products (id, name, stock, price, is_available) VALUES  (3, 'MOUSE', 10, 100.00, true);
INSERT INTO products (id, name, stock, price, is_available) VALUES  (4, 'TECLADO', 10, 150.00, false);
INSERT INTO products (id, name, stock, price, is_available) VALUES  (5, 'MONITOR', 10, 2000.00, true);
INSERT INTO products (id, name, stock, price, is_available) VALUES  (6, 'MICROFONO', 10, 350.00, false);
INSERT INTO products (id, name, stock, price, is_available) VALUES  (7, 'AUDIFONOS', 10, 450.00, true);

INSERT INTO sales (id, product_id, amount) VALUES  (1, 5, 8);
INSERT INTO sales (id, product_id, amount) VALUES  (2, 1, 15);
INSERT INTO sales (id, product_id, amount) VALUES  (3, 6, 13);
INSERT INTO sales (id, product_id, amount) VALUES  (4, 6, 4);
INSERT INTO sales (id, product_id, amount) VALUES  (5, 2, 3);
INSERT INTO sales (id, product_id, amount) VALUES  (6, 5, 1);
INSERT INTO sales (id, product_id, amount) VALUES  (7, 4, 5);
INSERT INTO sales (id, product_id, amount) VALUES  (8, 2, 5);
INSERT INTO sales (id, product_id, amount) VALUES  (9, 6, 2);
INSERT INTO sales (id, product_id, amount) VALUES  (10, 1, 8);