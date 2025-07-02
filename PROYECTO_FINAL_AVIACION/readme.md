# Sistema de Gestión Académica para Estudiantes de Aviación
📋 Descripción General

Este proyecto consiste en un sistema para la gestión académica diseñado para escuelas de aviación, pero que se podria utilizar en otros ambitos. La aplicación movil permite a los estudiantes registrarse, matricularse en cursos y consultar sus notas, mientras que los profesores pueden gestionar las calificaciones de sus estudiantes. La solución incluye una API REST desarrollada con Node.js y una aplicación móvil Android desarrollada con Kotlin.

La aplicación contiene datos de prueba con los cuales se puede probar el funcionamiento de la aplicación, estos se almacenaron de esta manera para facilitar la navegacion por la aplicacion, ya que de momento no es posible registrar un nuevo profesor, se debe realizar manualmente.

Profesor:
Codigo instructor: 12345
Contraseña: 12345


Asimismo cuenta con cursos de prueba, los cuales se pueden ver en la aplicacion.

## 🚀 Características Principales
Para Estudiantes:

    Registro y autenticación: Los estudiantes pueden crear una cuenta proporcionando sus datos personales
    Visualización de cursos: Acceso a todos los cursos disponibles relacionados con aviación
    Matriculación: Posibilidad de inscribirse en los cursos de su interés
    Consulta de notas: Visualización de calificaciones y promedio por curso
    Perfil personal: Acceso a información personal y estadísticas académicas

Para Profesores:

    Autenticación especial: Acceso mediante código de instructor
    Gestión de estudiantes: Visualización de todos los estudiantes registrados
    Administración de notas: Capacidad para asignar y modificar calificaciones (escala 0.0 - 5.0)
    Vista por cursos: Acceso organizado a estudiantes por curso

Funcionalidades Offline:

    Almacenamiento local: Los datos se guardan en el dispositivo cuando no hay conexión
    Sincronización automática: Al recuperar la conexión, los datos se sincronizan con el servidor
    Modo offline completo: La app funciona sin conexión usando los últimos datos disponibles

🛠️ Tecnologías Utilizadas
Backend - API REST Javascript:

    Node.js: Entorno de ejecución para JavaScript
    Express.js: Framework web minimalista y flexible
    MySQL: Sistema de gestión de base de datos relacional
    JWT (JSON Web Tokens): Para autenticación y autorización
    bcryptjs: Para encriptación de contraseñas
    cors: Para permitir solicitudes cross-origin
    dotenv: Para gestión de variables de entorno

Estructura de la API:

api-aviacion/

├── src/

│   ├── config/

│   │   └── database.js          # Configuración de conexión MySQL

│   ├── controllers/

│   │   ├── authController.js    # Lógica de autenticación

│   │   ├── studentController.js # Operaciones de estudiantes

│   │   ├── courseController.js  # Gestión de cursos

│   │   └── gradeController.js   # Administración de notas

│   ├── middlewares/

│   │   └── auth.js             # Middleware de autenticación JWT

│   ├── models/

│   │   └── db.sql              # Script de base de datos

│   └── routes/

│       ├── authRoutes.js       # Rutas de autenticación

│       ├── studentRoutes.js    # Rutas de estudiantes

│       ├── courseRoutes.js     # Rutas de cursos

│       └── gradeRoutes.js      # Rutas de notas

├── .env                        # Variables de entorno

└── server.js                   # Punto de entrada del servidor

## 💻Frontend - Aplicación Android

    Kotlin: Lenguaje de programación principal
    Android SDK: Kit de desarrollo de Android
    View System: Sistema tradicional de vistas de Android con XML
    Material Design 3: Sistema de diseño de Google
    Jetpack Compose: Framework de UI declarativo
    Android Studio: Entorno de desarrollo integrado

Durante el desarrollo de la aplicacion se utilizaron las siguientes bibliotecas y Frameworks:

    Retrofit 2: Cliente HTTP type-safe para Android
    OkHttp3: Cliente HTTP eficiente
    Gson: Serialización/deserialización JSON
    Room: Biblioteca de persistencia que proporciona una capa de abstracción sobre SQLite
    DataStore: Para almacenar preferencias de usuario de forma asíncrona

Para el apartado de UI:

        ViewModel: Para gestión de datos UI-related
        LiveData: Para datos observables
        Navigation Component: Para navegación entre pantallas
        View Binding: Para referencias de vistas type-safe

UI Components:

    RecyclerView: Para listas eficientes
    CardView: Para diseño de tarjetas
    SwipeRefreshLayout: Para actualización pull-to-refresh
    Tambien se utilizó Material Components: Botones, campos de texto, diálogos, etc.

📱 Estructura de la Aplicación Android

AviacionApp/

├── data/

│   ├── local/

│   │   ├── dao/              # Data Access Objects para Room

│   │   ├── database/         # Configuración de Room Database

│   │   └── entities/         # Entidades de base de datos

│   ├── remote/

│   │   ├── api/              # Definición de endpoints

│   │   └── dto/              # Data Transfer Objects

│   └── repository/           # Implementación del patrón Repository

├── di/

│   └── NetworkModule.kt      # Configuración de Retrofit

├── domain/

│   └── model/                # Modelos de dominio

├── presentation/

│   ├── auth/                 # Pantallas de autenticación

│   │   ├── login/           

│   │   └── register/        

│   ├── student/              # Módulo de estudiantes

│   │   ├── courses/         

│   │   └── profile/         

│   └── professor/            # Módulo de profesores

│       ├── students/        

│       └── courses/         

└── utils/                    # Utilidades y helpers

🗄️ Base de Datos
Esquema de Base de Datos MySQL:
Tabla estudiantes:

    id: Identificador único
    nombre: Nombre del estudiante
    apellido: Apellido del estudiante
    edad: Edad
    tipo_identificacion: Tipo de documento (CC, TI, Extranjero)
    numero_identificacion: Número de documento
    correo_electronico: Email único
    contrasena: Contraseña encriptada
    fecha_registro: Timestamp de registro

Tabla profesores:

    id: Identificador único
    nombre: Nombre del profesor
    codigo_instructor: Código único de instructor
    contrasena: Contraseña encriptada

Tabla cursos:

    id: Identificador único
    nombre: Nombre del curso
    descripcion: Descripción detallada
    duracion_horas: Duración en horas
    estado: Estado del curso (activo/inactivo)

Tabla matriculas:

    id: Identificador único
    estudiante_id: FK a estudiantes
    curso_id: FK a cursos
    fecha_matricula: Fecha de inscripción
    estado: Estado de la matrícula

Tabla notas:

    id: Identificador único
    matricula_id: FK a matriculas
    descripcion: Descripción de la evaluación
    valor: Calificación (0.0 - 5.0)
    fecha_registro: Fecha de la nota

Base de Datos Local (Room):

La aplicación mantiene una copia local de los datos con las siguientes entidades:

    StudentEntity: Información del estudiante
    CourseEntity: Cursos disponibles
    EnrollmentEntity: Matrículas con estado de sincronización
    GradeEntity: Notas con estado de sincronización

🔄 Flujo de Funcionamiento
1. Autenticación:
   
    El usuario (estudiante/profesor) inicia sesión
    La API valida las credenciales
    Se genera un JWT token
    El token se almacena localmente usando DataStore
    Todas las peticiones posteriores incluyen este token

2. Sincronización de Datos:

    Online: Los datos se obtienen directamente de la API
    Offline: Se utilizan los datos almacenados en Room
    Sincronización: WorkManager ejecuta tareas periódicas para sincronizar datos pendientes

3. Gestión de Estados:

    Loading: Mientras se cargan datos
    Success: Cuando la operación es exitosa
    Error: Cuando ocurre un error (con mensaje descriptivo)

## 🚦 Endpoints de la API
Autenticación:

    POST /api/auth/register - Registro de estudiantes
    POST /api/auth/login - Login de estudiantes
    POST /api/auth/login-professor - Login de profesores

Estudiantes:

    GET /api/students/profile - Perfil del estudiante actual
    GET /api/students/courses - Cursos del estudiante
    GET /api/students/all - Todos los estudiantes (solo profesores)

Cursos:

    GET /api/courses - Lista de cursos disponibles
    POST /api/courses/enroll - Matricularse en un curso
    GET /api/courses/:id/students - Estudiantes de un curso

Notas:

    POST /api/grades - Agregar nota (profesores)
    PUT /api/grades/:id - Actualizar nota
    DELETE /api/grades/:id - Eliminar nota


En cuanto al Diseño, se utilzaron las siguientes bibliotecas y Frameworks

    Material Design 3: Siguiendo las últimas guías de diseño de Google
    Tema personalizado: Colores azul aviación y naranja acento
    Modo claro: Interfaz optimizada para uso diurno
    Animaciones suaves: Transiciones fluidas entre pantallas



## Instalación y Configuración
Requisitos Previos:

    Node.js v14 o superior
    MySQL 5.7 o superior
    Android Studio Arctic Fox o superior
    JDK 8 o superior
    Dispositivo Android con API 24+ preferiblemente, pero puede funcionar con versiones más antiguas o emulado

Configuración del Backend:

    Clonar el repositorio  y navegar a la carpeta de la API utilizado cmd:

                        "cd api-aviacion"

    2. Para Instalar dependencias necesarias:
                        "npm install"

    3.Configurar variables de entorno (.env):

"PORT=3000

DB_HOST=localhost

DB_USER=root

DB_PASSWORD=tu_contraseña

DB_NAME=aviacion_db

JWT_SECRET=tu_clave_secreta_super_segura"


    4. Crear la base de datos:

                    "mysql -u root -p "tucontraseña" < src/models/db.sql"

    5. Inicializar datos de prueba:


                    "npm run init-db"

    6. Iniciar el servidor:


                    "npm run dev"

    7. Configuración de la App Android:

    Abrir el proyecto en Android Studio

    Configurar la URL de la API:
        Para emulador: http://10.0.2.2:3000/api/
        Para dispositivo físico: http://[IP_DE_TU_PC]:3000/api/

    Modificar en ApiConstants.kt si es necesario.

    Sincronizar el proyecto:
        File → Sync Project with Gradle Files

    Ejecutar la aplicación:
        Run → Run 'app'

Recomiendo hacer un clean project tras actualizar las dependencias para evitar errores.

### 🔐 Seguridad
Medidas Implementadas:

    Encriptación de contraseñas: Usando bcrypt con salt rounds
    Tokens JWT: Con expiración de 24 horas
    Validación de entrada: En cliente y servidor
    Prepared statements: Prevención de SQL injection
    Middleware de autenticación: Protección de rutas sensibles
    Los tokens se almacenan de forma segura en DataStore
    Las contraseñas nunca se almacenan en texto plano
    Validación de tipos de usuario (estudiante/profesor)
    Timeout de sesión automático
