const express = require('express');
const router = express.Router();
const studentController = require('../controllers/studentController');
const { verifyToken, verifyProfessor, verifyStudent } = require('../middlewares/auth');

// Rutas de estudiantes
router.get('/all', verifyProfessor, studentController.getAllStudents); // Solo profesores
router.get('/profile', verifyStudent, studentController.getStudentProfile); // Solo estudiantes
router.get('/courses', verifyStudent, studentController.getStudentCourses); // Estudiante ve sus cursos
router.get('/:studentId/courses', verifyProfessor, studentController.getStudentCourses); // Profesor ve cursos de un estudiante


module.exports = router;