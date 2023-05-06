-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: mariadb
-- Tiempo de generación: 06-05-2023 a las 14:39:36
-- Versión del servidor: 10.11.2-MariaDB-1:10.11.2+maria~ubu2204-log
-- Versión de PHP: 8.1.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bank_test`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `contrasena` varchar(255) DEFAULT NULL,
  `estado` varchar(255) DEFAULT NULL,
  `id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`contrasena`, `estado`, `id`) VALUES
('1234', '1', 1),
('5678', '1', 2),
('1245', '1', 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cuentas`
--

CREATE TABLE `cuentas` (
  `id` bigint(20) NOT NULL,
  `estado` varchar(255) DEFAULT NULL,
  `numero_cuenta` bigint(20) NOT NULL,
  `saldo_inicial` double NOT NULL,
  `tipo_cuenta` varchar(255) DEFAULT NULL,
  `id_cliente` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `cuentas`
--

INSERT INTO `cuentas` (`id`, `estado`, `numero_cuenta`, `saldo_inicial`, `tipo_cuenta`, `id_cliente`) VALUES
(1, '1', 478758, 2000, 'ahorro', 1),
(2, '1', 225487, 100, 'corriente', 2),
(3, '1', 495878, 0, 'ahorro', 3),
(4, '1', 496825, 540, 'ahorro', 2),
(5, '1', 585545, 1000, 'corriente', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `movimientos`
--

CREATE TABLE `movimientos` (
  `id` bigint(20) NOT NULL,
  `fecha` date DEFAULT NULL,
  `saldo` double NOT NULL,
  `tipo_movimiento` varchar(255) DEFAULT NULL,
  `valor` double NOT NULL,
  `cuenta_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `movimientos`
--

INSERT INTO `movimientos` (`id`, `fecha`, `saldo`, `tipo_movimiento`, `valor`, `cuenta_id`) VALUES
(1, '2023-06-02', 1425, 'retiro', -575, 1),
(2, '2023-06-02', 700, 'deposito', 600, 2),
(3, '2023-06-02', 150, 'deposito', 150, 3),
(4, '2023-06-02', 0, 'retiro', -540, 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personas`
--

CREATE TABLE `personas` (
  `id` bigint(20) NOT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `edad` int(11) NOT NULL,
  `genero` varchar(255) DEFAULT NULL,
  `identificacion` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `telefono` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `personas`
--

INSERT INTO `personas` (`id`, `direccion`, `edad`, `genero`, `identificacion`, `nombre`, `telefono`) VALUES
(1, 'Otavalo sn y principal', 34, 'M', '1144149500', 'Jose Lema', '098254785'),
(2, 'Amazonas y NNUU', 40, 'F', '1144149501', 'Marianela Montalvo', '097548965'),
(3, '13 junio y Equinoccial', 40, 'M', '1144149502', 'Juan Osorio', '098874587');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `cuentas`
--
ALTER TABLE `cuentas`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_7h7mqvcau3mcl0mbrkdrt7fnh` (`numero_cuenta`),
  ADD KEY `FKl7qjco9qn0mai4bxjxee01vwr` (`id_cliente`);

--
-- Indices de la tabla `movimientos`
--
ALTER TABLE `movimientos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK4moe88hxuohcysas5h70mdc09` (`cuenta_id`);

--
-- Indices de la tabla `personas`
--
ALTER TABLE `personas`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_dpxdn543sbyt8xkvsqha0l1li` (`identificacion`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cuentas`
--
ALTER TABLE `cuentas`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `movimientos`
--
ALTER TABLE `movimientos`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `personas`
--
ALTER TABLE `personas`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD CONSTRAINT `FKtk4yna9cqo54xshjc9dpgbmc8` FOREIGN KEY (`id`) REFERENCES `personas` (`id`);

--
-- Filtros para la tabla `cuentas`
--
ALTER TABLE `cuentas`
  ADD CONSTRAINT `FKl7qjco9qn0mai4bxjxee01vwr` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id`);

--
-- Filtros para la tabla `movimientos`
--
ALTER TABLE `movimientos`
  ADD CONSTRAINT `FK4moe88hxuohcysas5h70mdc09` FOREIGN KEY (`cuenta_id`) REFERENCES `cuentas` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
