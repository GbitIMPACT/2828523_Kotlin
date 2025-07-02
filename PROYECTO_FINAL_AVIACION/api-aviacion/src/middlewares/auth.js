const jwt = require('jsonwebtoken');

const verifyToken = (req, res, next) => {
    const token = req.headers['authorization'];
    
    if (!token) {
        return res.status(403).json({ error: 'No se proporcionó token' });
    }
    
    try {
        const decoded = jwt.verify(token.replace('Bearer ', ''), process.env.JWT_SECRET);
        req.user = decoded;
        next();
    } catch (error) {
        return res.status(401).json({ error: 'Token inválido' });
    }
};

const verifyProfessor = (req, res, next) => {
    verifyToken(req, res, () => {
        if (req.user.tipo !== 'profesor') {
            return res.status(403).json({ error: 'Acceso denegado. Se requiere rol de profesor' });
        }
        next();
    });
};

const verifyStudent = (req, res, next) => {
    verifyToken(req, res, () => {
        if (req.user.tipo !== 'estudiante') {
            return res.status(403).json({ error: 'Acceso denegado. Se requiere rol de estudiante' });
        }
        next();
    });
};

module.exports = { verifyToken, verifyProfessor, verifyStudent };