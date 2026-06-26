INSERT INTO roles (id, name, created_at) VALUES 
(1, 'ADMINISTRATOR', NOW()), 
(2, 'WAREHOUSEMAN', NOW()), 
(3, 'USER', NOW());

INSERT INTO users (id, role_id, email, name, password, status) VALUES
(1, 1, 'admin@gmail.com', 'ADMINISTRATOR', '$2a$14$vb5z1uMhhdeQqeYr7oscjuXQ0Af3WuFwuoSJWwyXNe1BsTLrR1Kmi', b'1'),
(2, 2, 'worker@gmail.com', 'WAREHOUSEMAN', '$2a$14$KpZg6OYAdpsNgALu.nBz1eRPAh60WNkFxaiUssHXd06oOoV51WGIG', b'1'),
(3, 3, 'user@gmail.com', 'USER', '$2a$14$7UaHvknOSyjLpdhYXC7jb.OHKFuNw1ZkQmftkhZK6bAAHBLm0M9U6', b'1');

INSERT INTO products (id, name, stock, price, is_available) VALUES  
(1, 'LAPTOP', 10, 3000.00, true),
(2, 'PC', 10, 4000.00, true),
(3, 'MOUSE', 10, 100.00, true),
(4, 'TECLADO', 10, 150.00, false),
(5, 'MONITOR', 10, 2000.00, true),
(6, 'MICROFONO', 10, 350.00, false),
(7, 'AUDIFONOS', 10, 450.00, true);

INSERT INTO sales (id, product_id, amount) VALUES  
(1, 5, 8),
(2, 1, 15),
(3, 6, 13),
(4, 6, 4),
(5, 2, 3),
(6, 5, 1),
(7, 4, 5),
(8, 2, 5),
(9, 6, 2),
(10, 1, 8);