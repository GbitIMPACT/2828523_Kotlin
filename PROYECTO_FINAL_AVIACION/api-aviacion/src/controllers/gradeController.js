const db = require('../config/database');

// Agregar nota a un estudiante (solo profesores)
const addGrade = async (req, res) => {
    try {
        const { matricula_id, descripcion, valor } = req.body;

        // Validar que el valor esté entre 0.0 y 5.0
        if (valor < 0 || valor > 5) {
            return res.status(400).json({ 
                error: 'El valor de la nota debe estar entre 0.0 y 5.0' 
            });
        }

        // Verificar que la matrícula existe
        const [matriculas] = await db.execute(
            'SELECT id FROM matriculas WHERE id = ?',
            [matricula_id]
        );

        if (matriculas.length === 0) {
            return res.status(404).json({ error: 'Matrícula no encontrada' });
        }

        // Insertar la nota
        const [result] = await db.execute(
            'INSERT INTO notas (matricula_id, descripcion, valor) VALUES (?, ?, ?)',
            [matricula_id, descripcion, valor]
        );

        res.status(201).json({
            message: 'Nota agregada exitosamente',
            nota_id: result.insertId
        });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Error al agregar nota' });
    }
};

// Actualizar una nota existente (solo profesores)
const updateGrade = async (req, res) => {
    try {
        const { id } = req.params;
        const { descripcion, valor } = req.body;

        // Validar que el valor esté entre 0.0 y 5.0
        if (valor < 0 || valor > 5) {
            return res.status(400).json({ 
                error: 'El valor de la nota debe estar entre 0.0 y 5.0' 
            });
        }

        const [result] = await db.execute(
            'UPDATE notas SET descripcion = ?, valor = ? WHERE id = ?',
            [descripcion, valor, id]
        );

        if (result.affectedRows === 0) {
            return res.status(404).json({ error: 'Nota no encontrada' });
        }

        res.json({ message: 'Nota actualizada exitosamente' });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Error al actualizar nota' });
    }
};

// Obtener notas de una matrícula
const getGradesByEnrollment = async (req, res) => {
    try {
        const { matriculaId } = req.params;

        const [grades] = await db.execute(
            `SELECT id, descripcion, valor, fecha_registro 
             FROM notas 
             WHERE matricula_id = ?
             ORDER BY fecha_registro DESC`,
            [matriculaId]
        );

        res.json(grades);
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Error al obtener notas' });
    }
};

// Eliminar una nota (solo profesores)
const deleteGrade = async (req, res) => {
    try {
        const { id } = req.params;

        const [result] = await db.execute(
            'DELETE FROM notas WHERE id = ?',
            [id]
        );

        if (result.affectedRows === 0) {
            return res.status(404).json({ error: 'Nota no encontrada' });
        }

        res.json({ message: 'Nota eliminada exitosamente' });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Error al eliminar nota' });
    }
};

module.exports = {
    addGrade,
    updateGrade,
    getGradesByEnrollment,
    deleteGrade
};