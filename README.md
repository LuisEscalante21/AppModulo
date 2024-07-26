CREATE TABLE Usuarios(
id_usuario INT PRIMARY KEY,
email VARCHAR2(100) UNIQUE NOT NULL,
contrasena VARCHAR2(50) NOT NULL
);

CREATE SEQUENCE sec_usuarios
START WITH 1
INCREMENT BY 1;

CREATE TRIGGER TrigUsuarios
BEFORE INSERT ON Usuarios
FOR EACH ROW
BEGIN
SELECT sec_usuarios.NEXTVAL INTO : NEW.id_usuario
FROM DUAL;
END;

INSERT INTO Usuarios (email, contrasena) VALUES ('enfermero@gmail.com', 'Enfermero12.');
---------------------------------------------------------------------------------------

CREATE TABLE Pacientes(
id_paciente INT PRIMARY KEY,
nombres VARCHAR2(100) NOT NULL,
apellidos VARCHAR2(100) NOT NULL,
edad INT NOT NULL,
enfermedad VARCHAR2(200) NOT NULL,
num_habitacion INT NOT NULL,
num_cama INT NOT NULL,
medicamentos Varchar2(100) not null,
fecha_ingreso DATE NOT NULL,
hora_aplicacion varchar2(5)not null
);

CREATE SEQUENCE sec_pacientes
START WITH 1
INCREMENT BY 1;

CREATE TRIGGER TrigPacientes
BEFORE INSERT ON Pacientes
FOR EACH ROW
BEGIN
SELECT sec_pacientes.NEXTVAL INTO : NEW.id_paciente
FROM DUAL;
END;

INSERT ALL
INTO Pacientes(nombres, apellidos, edad, enfermedad, num_habitacion, num_cama, medicamentos, fecha_ingreso, hora_aplicacion) VALUES ('Pablo', 'Hernandez', 22, 'Gripe', 14, 69,'Vitamina C', '13/12/2024', '02:25')
INTO Pacientes(nombres, apellidos, edad, enfermedad, num_habitacion, num_cama, medicamentos, fecha_ingreso, hora_aplicacion) VALUES ('Rocio', 'Torres', 6, 'Denge', 574, 96,'Acetaminofen', '07/02/2028', '12:01')
SELECT * FROM dual;
SELECT * FROM Pacientes;
