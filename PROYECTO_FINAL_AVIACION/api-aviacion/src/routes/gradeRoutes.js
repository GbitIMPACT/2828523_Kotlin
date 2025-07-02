const express = require('express');
const router = express.Router();
const gradeController = require('../controllers/gradeController');
const { verifyToken, verifyProfessor } = require('../middlewares/auth');

// Rutas de notas
router.post('/', verifyProfessor, gradeController.addGrade); // Solo profesores
router.put('/:id', verifyProfessor, gradeController.updateGrade); // Solo profesores
router.delete('/:id', verifyProfessor, gradeController.deleteGrade); // Solo profesores
router.get('/enrollment/:matriculaId', verifyToken, gradeController.getGradesByEnrollment); // Todos autenticados

module.exports = router;