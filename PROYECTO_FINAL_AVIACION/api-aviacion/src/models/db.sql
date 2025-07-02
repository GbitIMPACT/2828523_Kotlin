-- Crear base de datos
CREATE DATABASE IF NOT EXISTS aviacion_db;
USE aviacion_db;

-- Tabla de estudiantes
CREATE TABLE IF NOT EXISTS estudiantes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    edad INT NOT NULL,
    tipo_identificacion ENUM('CC', 'TI', 'Extranjero') NOT NULL,
    numero_identificacion VARCHAR(50) UNIQUE NOT NULL,
    correo_electronico VARCHAR(100) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de profesores
CREATE TABLE IF NOT EXISTS profesores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    codigo_instructor VARCHAR(50) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL
);

-- Tabla de cursos
CREATE TABLE IF NOT EXISTS cursos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL,
    descripcion TEXT,
    duracion_horas INT NOT NULL,
    estado ENUM('activo', 'inactivo') DEFAULT 'activo'
);

-- Tabla de matriculas (relación estudiante-curso)
CREATE TABLE IF NOT EXISTS matriculas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    estudiante_id INT,
    curso_id INT,
    fecha_matricula TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('activo', 'completado', 'cancelado') DEFAULT 'activo',
    FOREIGN KEY (estudiante_id) REFERENCES estudiantes(id),
    FOREIGN KEY (curso_id) REFERENCES cursos(id),
    UNIQUE KEY unique_matricula (estudiante_id, curso_id)
);

-- Tabla de notas
CREATE TABLE IF NOT EXISTS notas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    matricula_id INT,
    descripcion VARCHAR(200),
    valor DECIMAL(2,1) CHECK (valor >= 0.0 AND valor <= 5.0),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (matricula_id) REFERENCES matriculas(id)
);

-- Insertar profesor de ejemplo
INSERT INTO profesores (nombre, codigo_instructor, contrasena) 
VALUES ('Profesor Demo', '12345', '$2a$10$YourHashedPasswordHere');

-- Insertar cursos de ejemplo
INSERT INTO cursos (nombre, descripcion, duracion_horas) VALUES
('Fundamentos de Aviación', 'Introducción a los principios básicos de la aviación y navegación aérea', 40),
('Meteorología Aeronáutica', 'Estudio de las condiciones meteorológicas y su impacto en la aviación', 60),
('Navegación Aérea', 'Técnicas y procedimientos de navegación para pilotos', 80),
('Comunicaciones Aeronáuticas', 'Protocolos y procedimientos de comunicación en aviación', 30),
('Regulaciones Aéreas', 'Normativas nacionales e internacionales de aviación civil', 50);