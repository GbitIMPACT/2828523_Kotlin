const db = require('../config/database');

// Obtener todos los estudiantes (solo para profesores)
const getAllStudents = async (req, res) => {
    try {
        const [students] = await db.execute(
            `SELECT id, nombre, apellido, edad, tipo_identificacion, 
                    numero_identificacion, correo_electronico, fecha_registro 
             FROM estudiantes
             ORDER BY apellido, nombre`
        );

        res.json(students);
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Error al obtener estudiantes' });
    }
};

// Obtener información del estudiante actual
const getStudentProfile = async (req, res) => {
    try {
        const studentId = req.user.id;

        const [students] = await db.execute(
            `SELECT id, nombre, apellido, edad, tipo_identificacion, 
                    numero_identificacion, correo_electronico, fecha_registro 
             FROM estudiantes 
             WHERE id = ?`,
            [studentId]
        );

        if (students.length === 0) {
            return res.status(404).json({ error: 'Estudiante no encontrado' });
        }

        res.json(students[0]);
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Error al obtener perfil del estudiante' });
    }
};

// Obtener cursos matriculados del estudiante con sus notas
const getStudentCourses = async (req, res) => {
    try {
        const studentId = req.params.studentId || req.user.id;

        const [courses] = await db.execute(
            `SELECT 
                c.id as curso_id,
                c.nombre as curso_nombre,
                c.descripcion,
                c.duracion_horas,
                m.id as matricula_id,
                m.fecha_matricula,
                m.estado as estado_matricula,
                COALESCE(AVG(n.valor), 0) as promedio_notas
             FROM matriculas m
             INNER JOIN cursos c ON m.curso_id = c.id
             LEFT JOIN notas n ON n.matricula_id = m.id
             WHERE m.estudiante_id = ?
             GROUP BY m.id, c.id`,
            [studentId]
        );

        // Para cada curso, obtener las notas individuales
        for (let course of courses) {
            const [grades] = await db.execute(
                `SELECT id, descripcion, valor, fecha_registro 
                 FROM notas 
                 WHERE matricula_id = ?
                 ORDER BY fecha_registro DESC`,
                [course.matricula_id]
            );
            course.notas = grades;
        }

        res.json(courses);
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Error al obtener cursos del estudiante' });
    }
};

module.exports = {
    getAllStudents,
    getStudentProfile,
    getStudentCourses
};

