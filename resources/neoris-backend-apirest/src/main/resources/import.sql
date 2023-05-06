/* Populate tabla clientes */

INSERT INTO personas (id, direccion, edad, genero, identificacion, nombre, telefono) VALUES(1, 'Otavalo sn y principal', 34, 'M', '1144149500', 'Jose Lema', '098254785');
INSERT INTO personas (id, direccion, edad, genero, identificacion, nombre, telefono) VALUES(2, 'Amazonas y NNUU', 40, 'F', '1144149501', 'Marianela Montalvo', '097548965');
INSERT INTO personas (id, direccion, edad, genero, identificacion, nombre, telefono) VALUES(3, '13 junio y Equinoccial', 40, 'M', '1144149502', 'Juan Osorio', '098874587');

INSERT INTO clientes (contrasena, estado, id) VALUES('1234', '1', 1);
INSERT INTO clientes (contrasena, estado, id) VALUES('5678', '1', 2);
INSERT INTO clientes (contrasena, estado, id) VALUES('1245', '1', 3);

INSERT INTO cuentas (id, estado, numero_cuenta, saldo_inicial, tipo_cuenta, id_cliente) VALUES(1, '1', 478758, 2000.0, 'ahorro', 1);
INSERT INTO cuentas (id, estado, numero_cuenta, saldo_inicial, tipo_cuenta, id_cliente) VALUES(2, '1', 225487, 100.0, 'corriente', 2);
INSERT INTO cuentas (id, estado, numero_cuenta, saldo_inicial, tipo_cuenta, id_cliente) VALUES(3, '1', 495878, 0.0, 'ahorro', 3);
INSERT INTO cuentas (id, estado, numero_cuenta, saldo_inicial, tipo_cuenta, id_cliente) VALUES(4, '1', 496825, 540.0, 'ahorro', 2);
INSERT INTO cuentas (id, estado, numero_cuenta, saldo_inicial, tipo_cuenta, id_cliente) VALUES(5, '1', 585545, 1000.0, 'corriente', 1);

INSERT INTO movimientos (id, fecha, saldo, tipo_movimiento, valor, cuenta_id) VALUES(1, '2023-06-02', 1425.0, 'retiro', -575.0, 1);
INSERT INTO movimientos (id, fecha, saldo, tipo_movimiento, valor, cuenta_id) VALUES(2, '2023-06-02', 700.0, 'deposito', 600.0, 2);
INSERT INTO movimientos (id, fecha, saldo, tipo_movimiento, valor, cuenta_id) VALUES(3, '2023-06-02', 150.0, 'deposito', 150.0, 3);
INSERT INTO movimientos (id, fecha, saldo, tipo_movimiento, valor, cuenta_id) VALUES(4, '2023-06-02', 0.0, 'retiro', -540.0, 4);
commit;
