
create database hotelAlura;
use hotelAlura;


CREATE TABLE usuario (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) NOT NULL,
  `password` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO usuario VALUES (1,'gus','1234');

select * from usuario;

CREATE TABLE reserva (
  `id_reserva` int NOT NULL AUTO_INCREMENT,
  `fecha_entrada` date NOT NULL,
  `fecha_salida` date NOT NULL,
  `valor` decimal(9,2) NOT NULL,
  `forma_pago` varchar(20) NOT NULL,
  PRIMARY KEY (`id_reserva`)
);

select * from reserva;

CREATE TABLE huesped (
  `id_huesped` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) NOT NULL,
  `apellido` varchar(25) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `nacionalidad` varchar(35) NOT NULL,
  `telefono` varchar(30) NOT NULL,
  `id_reserva` int DEFAULT NULL,
  PRIMARY KEY (`id_huesped`),
  KEY id_reserva(`id_reserva`),
  CONSTRAINT `huespedes_ibfk_1` FOREIGN KEY (`id_reserva`) REFERENCES `reserva` (`id_reserva`)
) ;

select * from huesped;

drop database hotelAlura;
