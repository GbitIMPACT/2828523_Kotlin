package com.example.aviacionapp.data.local.dao

import androidx.room.*
import com.example.aviacionapp.data.local.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Query("SELECT * FROM courses")
    fun getAllCourses(): Flow<List<CourseEntity>>

    @Query("SELECT * FROM courses WHERE id = :courseId")
    suspend fun getCourseById(courseId: Int): CourseEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(courses: List<CourseEntity>)

    @Query("DELETE FROM courses")
    suspend fun deleteAll()

    // Enrollments
    @Query("SELECT * FROM enrollments WHERE studentId = :studentId")
    fun getStudentEnrollments(studentId: Int): Flow<List<EnrollmentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEnrollment(enrollment: EnrollmentEntity)

    @Query("SELECT * FROM enrollments WHERE syncStatus = :status")
    suspend fun getEnrollmentsBySyncStatus(status: SyncStatus): List<EnrollmentEntity>

    @Update
    suspend fun updateEnrollment(enrollment: EnrollmentEntity)

    // Grades
    @Query("SELECT * FROM grades WHERE matriculaId = :matriculaId")
    fun getGradesByMatricula(matriculaId: Int): Flow<List<GradeEntity>>

    @Insert
    suspend fun insertGrade(grade: GradeEntity)

    @Update
    suspend fun updateGrade(grade: GradeEntity)

    @Delete
    suspend fun deleteGrade(grade: GradeEntity)

    @Query("SELECT * FROM grades WHERE syncStatus = :status")
    suspend fun getGradesBySyncStatus(status: SyncStatus): List<GradeEntity>
}