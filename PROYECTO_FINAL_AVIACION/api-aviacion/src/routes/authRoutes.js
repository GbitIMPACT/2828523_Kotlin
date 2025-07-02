const express = require('express');
const router = express.Router();
const authController = require('../controllers/authController');

// Rutas de autenticación
router.post('/register', authController.registerStudent);
router.post('/login', authController.loginStudent);
router.post('/login-professor', authController.loginProfessor);

module.exports = router;