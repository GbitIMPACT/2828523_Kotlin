package com.example.aviacionapp.data.local.dao

import androidx.room.*
import com.example.aviacionapp.data.local.entities.StudentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    @Query("SELECT * FROM students WHERE isLoggedIn = 1 LIMIT 1")
    fun getCurrentStudent(): Flow<StudentEntity?>

    @Query("SELECT * FROM students WHERE isLoggedIn = 1 LIMIT 1")
    suspend fun getCurrentStudentSync(): StudentEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(student: StudentEntity)

    @Update
    suspend fun update(student: StudentEntity)

    @Query("UPDATE students SET isLoggedIn = 0")
    suspend fun logoutAll()

    @Query("UPDATE students SET isLoggedIn = 1 WHERE id = :studentId")
    suspend fun setLoggedIn(studentId: Int)

    @Query("DELETE FROM students")
    suspend fun deleteAll()
}