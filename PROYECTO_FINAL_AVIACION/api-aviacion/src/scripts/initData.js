const bcrypt = require('bcryptjs');
const db = require('../config/database');

const initializeData = async () => {
    try {
        console.log('Inicializando datos de prueba...');

        // Verificar si el profesor demo existe
        const [professors] = await db.execute(
            'SELECT id FROM profesores WHERE codigo_instructor = ?',
            ['12345']
        );

        if (professors.length === 0) {
            // Crear profesor demo
            const hashedPassword = await bcrypt.hash('12345', 10);
            await db.execute(
                'INSERT INTO profesores (nombre, codigo_instructor, contrasena) VALUES (?, ?, ?)',
                ['Profesor Demo', '12345', hashedPassword]
            );
            console.log('Profesor demo creado');
        }

        // Verificar si hay cursos
        const [courses] = await db.execute('SELECT COUNT(*) as count FROM cursos');
        
        if (courses[0].count === 0) {
            // Insertar cursos de ejemplo
            await db.execute(`
                INSERT INTO cursos (nombre, descripcion, duracion_horas) VALUES
                ('Fundamentos de Aviación', 'Introducción a los principios básicos de la aviación y navegación aérea', 40),
                ('Meteorología Aeronáutica', 'Estudio de las condiciones meteorológicas y su impacto en la aviación', 60),
                ('Navegación Aérea', 'Técnicas y procedimientos de navegación para pilotos', 80),
                ('Comunicaciones Aeronáuticas', 'Protocolos y procedimientos de comunicación en aviación', 30),
                ('Regulaciones Aéreas', 'Normativas nacionales e internacionales de aviación civil', 50)
            `);
            console.log('Cursos de ejemplo creados');
        }

        console.log('Datos inicializados correctamente');
        process.exit(0);
    } catch (error) {
        console.error('Error al inicializar datos:', error);
        process.exit(1);
    }
};

initializeData();