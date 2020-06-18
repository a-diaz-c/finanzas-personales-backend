INSERT INTO usuarios (id_usuario, nombre, apellido, usuario, email, password, create_at) VALUES(1, 'Alberto', 'Díaz', 'diaz1', 'diaz@gmail.com', '', '2019-08-28');
INSERT INTO usuarios (id_usuario, nombre, apellido, usuario, email, password, create_at) VALUES(2, 'Melchor', 'Díaz', 'MelDiaz', 'mel.diaz@gmail.com', '', '2019-08-28');

INSERT INTO categorias (id_categoria, nombre, fk_id_usuario, create_at) VALUES(1, 'Alimentos',1 , '2020-05-12')
INSERT INTO categorias (id_categoria, nombre, fk_id_usuario, create_at) VALUES(2, 'Vivienda',1 , '2020-05-12')
INSERT INTO categorias (id_categoria, nombre, fk_id_usuario, create_at) VALUES(3, 'Vestuario',1 , '2020-05-12')
INSERT INTO categorias (id_categoria, nombre, fk_id_usuario, create_at) VALUES(4, 'Transporte',1, '2020-05-12')
INSERT INTO categorias (id_categoria, nombre, fk_id_usuario, create_at) VALUES(5, 'Otros',1, '2020-05-12')

INSERT INTO gastos (id_gasto, nombre, fk_id_categoria, create_at) VALUES(1, 'Mercado', 1, '2020-05-12')
INSERT INTO gastos (id_gasto, nombre, fk_id_categoria, create_at) VALUES(2, 'Comida fuera de casa', 1, '2020-05-12')
INSERT INTO gastos (id_gasto, nombre, fk_id_categoria, create_at) VALUES(3, 'Agua', 2, '2020-05-14')
INSERT INTO gastos (id_gasto, nombre, fk_id_categoria, create_at) VALUES(4, 'Luz', 2, '2020-05-14')
INSERT INTO gastos (id_gasto, nombre, fk_id_categoria, create_at) VALUES(5, 'Teléfono', 2, '2020-05-14')
INSERT INTO gastos (id_gasto, nombre, fk_id_categoria, create_at) VALUES(6, 'Uber', 4, '2020-05-14')
INSERT INTO gastos (id_gasto, nombre, fk_id_categoria, create_at) VALUES(7, 'Gasolina', 4, '2020-05-14')
INSERT INTO gastos (id_gasto, nombre, fk_id_categoria, create_at) VALUES(8, 'Seguro de auto', 4, '2020-05-14')
INSERT INTO gastos (id_gasto, nombre, fk_id_categoria, create_at) VALUES(9, 'Alcohol', 5, '2020-05-14')
INSERT INTO gastos (id_gasto, nombre, fk_id_categoria, create_at) VALUES(10, 'Otro', 5, '2020-05-14')

INSERT INTO dias (id_dia, fecha, saldo_inicial, saldo_final, fk_id_usuario) VALUES(1, '20-05-14', 500, 300, 1)
INSERT INTO dias (id_dia, fecha, saldo_inicial, saldo_final, fk_id_usuario) VALUES(2, '20-05-15', 300, 250, 1)
INSERT INTO dias (id_dia, fecha, saldo_inicial, saldo_final, fk_id_usuario) VALUES(3, '20-05-12', 500, 200, 2)
INSERT INTO dias (id_dia, fecha, saldo_inicial, saldo_final, fk_id_usuario) VALUES(4, '20-05-16', 250, 355, 1)
INSERT INTO dias (id_dia, fecha, saldo_inicial, saldo_final, fk_id_usuario) VALUES(5, '20-05-14', 300, 250, 2)
INSERT INTO dias (id_dia, fecha, saldo_inicial, saldo_final, fk_id_usuario) VALUES(6, '20-05-15', 250, 355, 2)

INSERT INTO rel_dia_gasto (id_cantidad, cantidad, fk_id_dia, fk_id_gasto) VALUES(1, 20.00, 1, 1)
INSERT INTO rel_dia_gasto (id_cantidad, cantidad, fk_id_dia, fk_id_gasto) VALUES(2, 30.00, 1, 3)
INSERT INTO rel_dia_gasto (id_cantidad, cantidad, fk_id_dia, fk_id_gasto) VALUES(3, 50.00, 1, 5)
INSERT INTO rel_dia_gasto (id_cantidad, cantidad, fk_id_dia, fk_id_gasto) VALUES(4, 30.00, 2, 1)