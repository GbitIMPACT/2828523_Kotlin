package com.example.aviacionapp.data.remote.api

object ApiConstants {
    // Para emulador Android usar 10.0.2.2, para dispositivo físico usar IP de tu PC
    const val BASE_URL = "http://10.0.2.2:3000/api/"

    // Endpoints
    const val REGISTER = "auth/register"
    const val LOGIN = "auth/login"
    const val LOGIN_PROFESSOR = "auth/login-professor"
    const val GET_COURSES = "courses"
    const val ENROLL_COURSE = "courses/enroll"
    const val GET_STUDENT_COURSES = "students/courses"
    const val GET_STUDENT_PROFILE = "students/profile"
    const val GET_ALL_STUDENTS = "students/all"
    const val GET_COURSE_STUDENTS = "courses/{courseId}/students"
    const val ADD_GRADE = "grades"
    const val UPDATE_GRADE = "grades/{id}"
    const val DELETE_GRADE = "grades/{id}"
}