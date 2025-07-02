# API de Gestión de Estudiantes de Aviación

## Base URL
`http://localhost:3000/api`

## Autenticación
La API utiliza JWT para la autenticación. Incluye el token en el header:
uthorization: Bearer


## Endpoints


### Autenticación


#### Registro de Estudiante

POST /auth/register
Body: {
"nombre": "string",
"apellido": "string",
"edad": number,
"tipo_identificacion": "CC|TI|Extranjero",
"numero_identificacion": "string",
"correo_electronico": "string",
"contrasena": "string"
}


#### Login de Estudiante

POST /auth/login
Body: {
"correo_electronico": "string",
"contrasena": "string"
}


#### Login de Profesor

POST /auth/login-professor
Body: {
"codigo_instructor": "string",
"contrasena": "string"
}


### Estudiantes


#### Obtener todos los estudiantes (Solo profesores)

GET /students/all
Headers: Authorization: Bearer


#### Obtener perfil del estudiante actual

GET /students/profile
Headers: Authorization: Bearer


#### Obtener cursos del estudiante

GET /students/courses
Headers: Authorization: Bearer


### Cursos


#### Obtener todos los cursos

GET /courses
Headers: Authorization: Bearer


#### Matricularse en un curso

POST /courses/enroll
Headers: Authorization: Bearer
Body: {
"curso_id": number
}


#### Obtener estudiantes de un curso (Solo profesores)

GET /courses/:cursoId/students
Headers: Authorization: Bearer


### Notas


#### Agregar nota (Solo profesores)

POST /grades
Headers: Authorization: Bearer
Body: {
"matricula_id": number,
"descripcion": "string",
"valor": number (0.0 - 5.0)
}


#### Actualizar nota (Solo profesores)

PUT /grades/:id
Headers: Authorization: Bearer
Body: {
"descripcion": "string",
"valor": number (0.0 - 5.0)
}


#### Obtener notas de una matrícula

GET /grades/enrollment/:matriculaId
Headers: Authorization: Bearer


#### Eliminar nota (Solo profesores)

DELETE /grades/:id
Headers: Authorization: Bearers