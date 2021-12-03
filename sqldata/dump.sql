#Creamos el usuario localhost
CREATE USER 'dev'@'localhost' IDENTIFIED BY 'dev';
GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP ON *.* TO 'dev'@'localhost';

#Creamos la database
CREATE DATABASE IF NOT EXISTS `Alumnos_db` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `Alumnos_db`;

#Creamos la tabla 
CREATE TABLE IF NOT EXISTS `alumnos_tbl` ( `nombre` varchar(50),  `id` varchar(50),`edad` int(11),`email` varchar(50)) 
ENGINE=InnoDB DEFAULT CHARSET=utf8;