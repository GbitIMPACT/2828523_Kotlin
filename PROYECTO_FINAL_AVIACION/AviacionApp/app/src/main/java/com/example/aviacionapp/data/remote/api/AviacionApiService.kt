package com.example.aviacionapp.data.remote.api

import com.example.aviacionapp.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.*

interface AviacionApiService {

    // Auth endpoints
    @POST(ApiConstants.REGISTER)
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST(ApiConstants.LOGIN)
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST(ApiConstants.LOGIN_PROFESSOR)
    suspend fun loginProfessor(@Body request: ProfessorLoginRequest): Response<AuthResponse>

    // Course endpoints
    @GET(ApiConstants.GET_COURSES)
    suspend fun getCourses(@Header("Authorization") token: String): Response<List<CourseDto>>

    @POST(ApiConstants.ENROLL_COURSE)
    suspend fun enrollCourse(
        @Header("Authorization") token: String,
        @Body request: EnrollRequest
    ): Response<EnrollResponse>

    // Student endpoints
    @GET(ApiConstants.GET_STUDENT_COURSES)
    suspend fun getStudentCourses(@Header("Authorization") token: String): Response<List<CourseDto>>

    @GET(ApiConstants.GET_STUDENT_PROFILE)
    suspend fun getStudentProfile(@Header("Authorization") token: String): Response<StudentProfileDto>

    @GET(ApiConstants.GET_ALL_STUDENTS)
    suspend fun getAllStudents(@Header("Authorization") token: String): Response<List<StudentDto>>

    @GET(ApiConstants.GET_COURSE_STUDENTS)
    suspend fun getCourseStudents(
        @Header("Authorization") token: String,
        @Path("courseId") courseId: Int
    ): Response<List<StudentWithGradesDto>>

    // Grade endpoints
    @POST(ApiConstants.ADD_GRADE)
    suspend fun addGrade(
        @Header("Authorization") token: String,
        @Body request: AddGradeRequest
    ): Response<MessageResponse>

    @PUT(ApiConstants.UPDATE_GRADE)
    suspend fun updateGrade(
        @Header("Authorization") token: String,
        @Path("id") gradeId: Int,
        @Body request: UpdateGradeRequest
    ): Response<MessageResponse>

    @DELETE(ApiConstants.DELETE_GRADE)
    suspend fun deleteGrade(
        @Header("Authorization") token: String,
        @Path("id") gradeId: Int
    ): Response<MessageResponse>


}