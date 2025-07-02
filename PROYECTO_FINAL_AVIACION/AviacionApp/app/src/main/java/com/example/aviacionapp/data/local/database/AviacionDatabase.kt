package com.example.aviacionapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aviacionapp.data.local.dao.CourseDao
import com.example.aviacionapp.data.local.dao.StudentDao
import com.example.aviacionapp.data.local.entities.*

@Database(
    entities = [
        StudentEntity::class,
        CourseEntity::class,
        EnrollmentEntity::class,
        GradeEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AviacionDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao
    abstract fun courseDao(): CourseDao

    companion object {
        @Volatile
        private var INSTANCE: AviacionDatabase? = null

        fun getDatabase(context: Context): AviacionDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AviacionDatabase::class.java,
                    "aviacion_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}