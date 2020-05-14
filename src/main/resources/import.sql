INSERT INTO usuarios (id_usuario, nombre, apellido, usuario, email, password, create_at) VALUES(1, 'Alberto', 'Díaz', 'diaz1', 'diaz@gmail.com', '', '2019-08-28');

INSERT INTO categorias (id_categoria, nombre, tipo, fk_id_usuario, create_at) VALUES(1, 'Alimentos', '', 1, '2020-05-12')
INSERT INTO categorias (id_categoria, nombre, tipo, fk_id_usuario, create_at) VALUES(2, 'Vivienda', '', 1, '2020-05-12')
INSERT INTO categorias (id_categoria, nombre, tipo, fk_id_usuario, create_at) VALUES(3, 'Vestuario', '', 1, '2020-05-12')
INSERT INTO categorias (id_categoria, nombre, tipo, fk_id_usuario, create_at) VALUES(4, 'Transporte', '', 1, '2020-05-12')
INSERT INTO categorias (id_categoria, nombre, tipo, fk_id_usuario, create_at) VALUES(5, 'Otros', '', 1, '2020-05-12')

INSERT INTO sub_categorias (id_sub_categoria, nombre, fk_id_categoria, create_at) VALUES(1, 'Mercado', 1, '2020-05-12')
INSERT INTO sub_categorias (id_sub_categoria, nombre, fk_id_categoria, create_at) VALUES(2, 'Comida fuera de casa', 1, '2020-05-12')
INSERT INTO sub_categorias (id_sub_categoria, nombre, fk_id_categoria, create_at) VALUES(3, 'Agua', 2, '2020-05-14')
INSERT INTO sub_categorias (id_sub_categoria, nombre, fk_id_categoria, create_at) VALUES(4, 'Luz', 2, '2020-05-14')
INSERT INTO sub_categorias (id_sub_categoria, nombre, fk_id_categoria, create_at) VALUES(5, 'Teléfono', 2, '2020-05-14')
INSERT INTO sub_categorias (id_sub_categoria, nombre, fk_id_categoria, create_at) VALUES(6, 'Uber', 4, '2020-05-14')
INSERT INTO sub_categorias (id_sub_categoria, nombre, fk_id_categoria, create_at) VALUES(7, 'Gasolina', 4, '2020-05-14')
INSERT INTO sub_categorias (id_sub_categoria, nombre, fk_id_categoria, create_at) VALUES(8, 'Seguro de auto', 4, '2020-05-14')
INSERT INTO sub_categorias (id_sub_categoria, nombre, fk_id_categoria, create_at) VALUES(9, 'Alcohol', 5, '2020-05-14')
INSERT INTO sub_categorias (id_sub_categoria, nombre, fk_id_categoria, create_at) VALUES(10, 'Otro', 5, '2020-05-14')