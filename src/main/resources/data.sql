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
(3, 'Capital Federal', '1414', '1'),
(4, 'Posadas', '1414', '15');

--Address
INSERT INTO address (id, street, latitude, longitude, city_id) VALUES ( 1, 'calle Mascherano 1000', '7234.0', '6321.0', '1'),
														 ( 2, 'calle Juan Roman Riquelme 1500', '1234.0', '4321.0', '1'),
														 ( 3, 'calle Ronaldinho 200 ', '6234.0', '5321.0', '1'),
														 ( 4, 'calle El Diego 10', '1237.0', '4325.0', '1'),
														 ( 5, 'calle Bochini 1600', '3721.0', '2543.0', '2'),
														 ( 6, 'calle Riestra 1330', '2767.0', '3245.0', '3'),
 														 ( 7, 'calle Facundo Manes 22', '7676.0', '3238.0', '4');

--Branches
INSERT INTO branch (address_id) VALUES ( 1),
									   ( 2),
								       ( 3),
								       ( 7),
								       ( 4),
								       ( 5),
								       ( 6);
