INSERT INTO usuarios (id_usuario, nombre, apellido, usuario, email, password, create_at) VALUES(1, 'Alberto', 'Díaz', 'diaz1', 'diaz@gmail.com', '', '2019-08-28');

INSERT INTO categorias (id_categoria, nombre, tipo, fk_id_usuario, create_at) VALUES(1, 'Alimentos', '', 1, '2019-08-28')
INSERT INTO categorias (id_categoria, nombre, tipo, fk_id_usuario, create_at) VALUES(2, 'Vivienda', '', 1, '2019-08-28')
INSERT INTO categorias (id_categoria, nombre, tipo, fk_id_usuario, create_at) VALUES(3, 'Vestuario', '', 1, '2019-08-28')
INSERT INTO categorias (id_categoria, nombre, tipo, fk_id_usuario, create_at) VALUES(4, 'Transporte', '', 1, '2019-08-28')
INSERT INTO categorias (id_categoria, nombre, tipo, fk_id_usuario, create_at) VALUES(5, 'Otros', '', 1, '2019-08-28')

INSERT INTO sub_categorias (id_sub_categoria, nombre, fk_id_categoria, create_at) VALUES(1, 'Mercado', 1, '2019-08-28')
INSERT INTO sub_categorias (id_sub_categoria, nombre, fk_id_categoria, create_at) VALUES(2, 'Comida fuera de casa', 1, '2019-08-28')