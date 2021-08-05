--countries
INSERT INTO country (id,name) VALUES (1,'Argentina'),
								  (2,'Peru'),
								  (3,'Brasil'),
								  (4,'Chile'),
								  (5,'Paraguay'),
								  (6,'Uruguay'),
								  (7,'Bolivia');

--provinces
INSERT INTO province (id, name, country_id) VALUES
(1, 'Buenos Aires', '1'),
(2, 'Buenos Aires-GBA', '1'),
(3, 'Capital Federal', '1'),
(4, 'Catamarca', '1'),
(5, 'Chaco', '1'),
(6, 'Chubut', '1'),
(7, 'Córdoba', '1'),
(8, 'Corrientes', '1'),
(9, 'Entre Ríos', '1'),
(10, 'Formosa', '1'),
(11, 'Jujuy', '1'),
(12, 'La Pampa', '1'),
(13, 'La Rioja', '1'),
(14, 'Mendoza', '1'),
(15, 'Misiones', '1'),
(16, 'Neuquén', '1'),
(17, 'Río Negro', '1'),
(18, 'Salta', '1'),
(19, 'San Juan', '1'),
(20, 'San Luis', '1'),
(21, 'Santa Cruz', '1'),
(22, 'Santa Fe', '1'),
(23, 'Santiago del Estero', '1'),
(24, 'Tierra del Fuego', '1'),
(25, 'Tucumán', '1');


--city
INSERT INTO city (id, name, postal_code, province_id) VALUES
(1, 'Quilmes', '1879', '1'),
(2, 'Avellaneda', '1920', '1'),
(3, 'Capital Federal', '1414', '1');

--Address
INSERT INTO address (id, street, latitude, longitude, city_id) VALUES ( 1, 'calle 811, Quilmes Oeste', '7234.0', '6321.0', '1'),
														 ( 2, 'calle 808, Quilmes Oeste', '1234.0', '4321.0', '1'),
														 ( 3, 'calle 810, Quilmes Oeste', '6234.0', '5321.0', '1'),
														 ( 4, 'calle 809, Quilmes Oeste', '1237.0', '4325.0', '1');

--Branches
INSERT INTO branch (address_id) VALUES ( 1),
									   ( 2),
								       ( 3),
								       ( 4);

/*
								       */