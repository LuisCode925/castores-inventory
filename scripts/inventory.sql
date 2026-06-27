-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Servidor: mysql:3306
-- Tiempo de generación: 27-06-2026 a las 15:49:04
-- Versión del servidor: 8.0.41
-- Versión de PHP: 8.2.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `inventory`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inventory_transactions`
--

CREATE TABLE `inventory_transactions` (
  `movement` tinyint DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `id` bigint NOT NULL,
  `product_id` bigint DEFAULT NULL,
  `registered_at` datetime(6) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL
) ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `products`
--

CREATE TABLE `products` (
  `is_available` bit(1) DEFAULT NULL,
  `price` decimal(16,2) DEFAULT NULL,
  `stock` int DEFAULT NULL,
  `id` bigint NOT NULL,
  `name` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `products`
--

INSERT INTO `products` (`is_available`, `price`, `stock`, `id`, `name`) VALUES
(b'1', 3000.00, 10, 1, 'LAPTOP'),
(b'1', 4000.00, 10, 2, 'PC'),
(b'1', 100.00, 10, 3, 'MOUSE'),
(b'0', 150.00, 10, 4, 'TECLADO'),
(b'1', 2000.00, 10, 5, 'MONITOR'),
(b'0', 350.00, 10, 6, 'MICROFONO'),
(b'1', 450.00, 10, 7, 'AUDIFONOS');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

CREATE TABLE `roles` (
  `created_at` datetime(6) NOT NULL,
  `id` bigint NOT NULL,
  `name` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `roles`
--

INSERT INTO `roles` (`created_at`, `id`, `name`) VALUES
('2026-06-27 15:44:35.000000', 1, 'ADMINISTRATOR'),
('2026-06-27 15:44:35.000000', 2, 'WAREHOUSEMAN'),
('2026-06-27 15:44:35.000000', 3, 'USER');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sales`
--

CREATE TABLE `sales` (
  `amount` int DEFAULT NULL,
  `id` bigint NOT NULL,
  `product_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `sales`
--

INSERT INTO `sales` (`amount`, `id`, `product_id`) VALUES
(8, 1, 5),
(15, 2, 1),
(13, 3, 6),
(4, 4, 6),
(3, 5, 2),
(1, 6, 5),
(5, 7, 4),
(5, 8, 2),
(2, 9, 6),
(8, 10, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tokens`
--

CREATE TABLE `tokens` (
  `expired` bit(1) NOT NULL,
  `revoked` bit(1) NOT NULL,
  `id` bigint NOT NULL,
  `user_id` bigint DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `token_type` enum('BEARER') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE `users` (
  `status` bit(1) NOT NULL,
  `id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`status`, `id`, `role_id`, `email`, `name`, `password`) VALUES
(b'1', 1, 1, 'admin@gmail.com', 'ADMINISTRATOR', '$2a$14$vb5z1uMhhdeQqeYr7oscjuXQ0Af3WuFwuoSJWwyXNe1BsTLrR1Kmi'),
(b'1', 2, 2, 'worker@gmail.com', 'WAREHOUSEMAN', '$2a$14$KpZg6OYAdpsNgALu.nBz1eRPAh60WNkFxaiUssHXd06oOoV51WGIG'),
(b'1', 3, 3, 'user@gmail.com', 'USER', '$2a$14$7UaHvknOSyjLpdhYXC7jb.OHKFuNw1ZkQmftkhZK6bAAHBLm0M9U6');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `inventory_transactions`
--
ALTER TABLE `inventory_transactions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKrm9aaxuvvmp9ehvxwe936ar04` (`product_id`),
  ADD KEY `FK8cm8f2fdpwsk02joqyuhrfrvk` (`user_id`);

--
-- Indices de la tabla `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `sales`
--
ALTER TABLE `sales`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKkxc13g7l4ioljxqyoo15nh051` (`product_id`);

--
-- Indices de la tabla `tokens`
--
ALTER TABLE `tokens`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKna3v9f8s7ucnj16tylrs822qj` (`token`),
  ADD KEY `FK2dylsfo39lgjyqml2tbe0b0ss` (`user_id`);

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  ADD KEY `FKp56c1712k691lhsyewcssf40f` (`role_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `inventory_transactions`
--
ALTER TABLE `inventory_transactions`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `products`
--
ALTER TABLE `products`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `roles`
--
ALTER TABLE `roles`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `sales`
--
ALTER TABLE `sales`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `tokens`
--
ALTER TABLE `tokens`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `inventory_transactions`
--
ALTER TABLE `inventory_transactions`
  ADD CONSTRAINT `FK8cm8f2fdpwsk02joqyuhrfrvk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKrm9aaxuvvmp9ehvxwe936ar04` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

--
-- Filtros para la tabla `sales`
--
ALTER TABLE `sales`
  ADD CONSTRAINT `FKkxc13g7l4ioljxqyoo15nh051` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

--
-- Filtros para la tabla `tokens`
--
ALTER TABLE `tokens`
  ADD CONSTRAINT `FK2dylsfo39lgjyqml2tbe0b0ss` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Filtros para la tabla `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `FKp56c1712k691lhsyewcssf40f` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
