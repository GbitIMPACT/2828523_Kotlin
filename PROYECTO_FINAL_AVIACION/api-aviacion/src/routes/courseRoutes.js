const express = require('express');
const router = express.Router();
const courseController = require('../controllers/courseController');
const { verifyToken, verifyProfessor, verifyStudent } = require('../middlewares/auth');

// Rutas de cursos
router.get('/', verifyToken, courseController.getAllCourses); // Todos pueden ver los cursos
router.post('/enroll', verifyStudent, courseController.enrollStudent); // Solo estudiantes pueden matricularse
router.get('/:cursoId/students', verifyProfessor, courseController.getCourseStudents); // Solo profesores

module.exports = router;