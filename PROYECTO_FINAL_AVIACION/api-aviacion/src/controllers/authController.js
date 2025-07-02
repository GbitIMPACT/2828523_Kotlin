const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const db = require('../config/database');

// Registro de estudiantes
const registerStudent = async (req, res) => {
    try {
        const { 
            nombre, 
            apellido, 
            edad, 
            tipo_identificacion, 
            numero_identificacion, 
            correo_electronico, 
            contrasena 
        } = req.body;

        // Verificar si el estudiante ya existe
        const [existingStudent] = await db.execute(
            'SELECT id FROM estudiantes WHERE correo_electronico = ? OR numero_identificacion = ?',
            [correo_electronico, numero_identificacion]
        );

        if (existingStudent.length > 0) {
            return res.status(400).json({ 
                error: 'El correo electrónico o número de identificación ya está registrado' 
            });
        }

        // Hashear la contraseña
        const hashedPassword = await bcrypt.hash(contrasena, 10);

        // Insertar nuevo estudiante
        const [result] = await db.execute(
            `INSERT INTO estudiantes 
            (nombre, apellido, edad, tipo_identificacion, numero_identificacion, correo_electronico, contrasena) 
            VALUES (?, ?, ?, ?, ?, ?, ?)`,
            [nombre, apellido, edad, tipo_identificacion, numero_identificacion, correo_electronico, hashedPassword]
        );

        // Generar token JWT
        const token = jwt.sign(
            { 
                id: result.insertId, 
                correo_electronico, 
                tipo: 'estudiante' 
            },
            process.env.JWT_SECRET,
            { expiresIn: '24h' }
        );

        res.status(201).json({
            message: 'Estudiante registrado exitosamente',
            token,
            estudiante: {
                id: result.insertId,
                nombre,
                apellido,
                correo_electronico
            }
        });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Error al registrar estudiante' });
    }
};

// Login de estudiantes
const loginStudent = async (req, res) => {
    try {
        const { correo_electronico, contrasena } = req.body;

        // Buscar estudiante
        const [students] = await db.execute(
            'SELECT * FROM estudiantes WHERE correo_electronico = ?',
            [correo_electronico]
        );

        if (students.length === 0) {
            return res.status(401).json({ error: 'Credenciales inválidas' });
        }

        const student = students[0];

        // Verificar contraseña
        const isValidPassword = await bcrypt.compare(contrasena, student.contrasena);
        
        if (!isValidPassword) {
            return res.status(401).json({ error: 'Credenciales inválidas' });
        }

        // Generar token JWT
        const token = jwt.sign(
            { 
                id: student.id, 
                correo_electronico: student.correo_electronico, 
                tipo: 'estudiante' 
            },
            process.env.JWT_SECRET,
            { expiresIn: '24h' }
        );

        res.json({
            message: 'Inicio de sesión exitoso',
            token,
            estudiante: {
                id: student.id,
                nombre: student.nombre,
                apellido: student.apellido,
                correo_electronico: student.correo_electronico
            }
        });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Error al iniciar sesión' });
    }
};

// Login de profesores
const loginProfessor = async (req, res) => {
    try {
        const { codigo_instructor, contrasena } = req.body;

        // Para el profesor de ejemplo (12345)
        if (codigo_instructor === '12345' && contrasena === '12345') {
            // Buscar profesor en la base de datos
            const [professors] = await db.execute(
                'SELECT * FROM profesores WHERE codigo_instructor = ?',
                [codigo_instructor]
            );

            if (professors.length === 0) {
                // Si no existe, crear el profesor de ejemplo
                const hashedPassword = await bcrypt.hash('12345', 10);
                await db.execute(
                    'INSERT INTO profesores (nombre, codigo_instructor, contrasena) VALUES (?, ?, ?)',
                    ['Profesor Demo', '12345', hashedPassword]
                );
            }
        }

        // Buscar profesor
        const [professors] = await db.execute(
            'SELECT * FROM profesores WHERE codigo_instructor = ?',
            [codigo_instructor]
        );

        if (professors.length === 0) {
            return res.status(401).json({ error: 'Credenciales inválidas' });
        }

        const professor = professors[0];

        // Para el profesor demo, permitir login directo con 12345
        let isValidPassword = false;
        if (codigo_instructor === '12345' && contrasena === '12345') {
            isValidPassword = true;
        } else {
            isValidPassword = await bcrypt.compare(contrasena, professor.contrasena);
        }
        
        if (!isValidPassword) {
            return res.status(401).json({ error: 'Credenciales inválidas' });
        }

        // Generar token JWT
        const token = jwt.sign(
            { 
                id: professor.id, 
                codigo_instructor: professor.codigo_instructor, 
                tipo: 'profesor' 
            },
            process.env.JWT_SECRET,
            { expiresIn: '24h' }
        );

        res.json({
            message: 'Inicio de sesión exitoso',
            token,
            profesor: {
                id: professor.id,
                nombre: professor.nombre,
                codigo_instructor: professor.codigo_instructor
            }
        });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Error al iniciar sesión' });
    }
};

module.exports = {
    registerStudent,
    loginStudent,
    loginProfessor
};