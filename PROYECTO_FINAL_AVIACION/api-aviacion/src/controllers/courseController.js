const db = require('../config/database');

// Obtener todos los cursos disponibles
// Obtener todos los cursos disponibles

const getAllCourses = async (req, res) => {

    try {

        const [courses] = await db.execute(

            `SELECT id, nombre, descripcion, duracion_horas, estado 

             FROM cursos 

             WHERE estado = 'activo'

             ORDER BY nombre`

        );


        console.log('Cursos encontrados:', courses.length);

        res.json(courses);

    } catch (error) {

        console.error(error);

        res.status(500).json({ error: 'Error al obtener cursos' });

    }

};

// Matricular estudiante en un curso
// Matricular estudiante en un curso
const enrollStudent = async (req, res) => {
    try {
        const { curso_id } = req.body;
        const estudiante_id = req.user.id;
        
        console.log('Intentando matricular:', { estudiante_id, curso_id });
        
        // Verificar que el curso existe
        const [courseExists] = await db.execute(
            'SELECT id FROM cursos WHERE id = ?',
            [curso_id]
        );
        
        if (courseExists.length === 0) {
            return res.status(404).json({ error: 'El curso no existe' });
        }
        
        // Verificar si ya está matriculado
        const [existing] = await db.execute(
            'SELECT id FROM matriculas WHERE estudiante_id = ? AND curso_id = ?',
            [estudiante_id, curso_id]
        );

        if (existing.length > 0) {
            return res.status(400).json({ error: 'Ya estás matriculado en este curso' });
        }

        // Matricular al estudiante
        const [result] = await db.execute(
            'INSERT INTO matriculas (estudiante_id, curso_id) VALUES (?, ?)',
            [estudiante_id, curso_id]
        );

        res.status(201).json({
            message: 'Matriculado exitosamente',
            matricula_id: result.insertId
        });
    } catch (error) {
        console.error('Error en enrollStudent:', error);
        res.status(500).json({ error: 'Error al matricular estudiante' });
    }
};

// Obtener estudiantes matriculados en un curso (para profesores)
const getCourseStudents = async (req, res) => {
    try {
        const { cursoId } = req.params;

        const [students] = await db.execute(
            `SELECT 
                e.id as estudiante_id,
                e.nombre,
                e.apellido,
                e.numero_identificacion,
                e.correo_electronico,
                m.id as matricula_id,
                m.fecha_matricula,
                m.estado,
                COALESCE(AVG(n.valor), 0) as promedio_notas
             FROM matriculas m
             INNER JOIN estudiantes e ON m.estudiante_id = e.id
             LEFT JOIN notas n ON n.matricula_id = m.id
             WHERE m.curso_id = ?
             GROUP BY m.id, e.id
             ORDER BY e.apellido, e.nombre`,
            [cursoId]
        );

        res.json(students);
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Error al obtener estudiantes del curso' });
    }
};

module.exports = {
    getAllCourses,
    enrollStudent,
    getCourseStudents
};