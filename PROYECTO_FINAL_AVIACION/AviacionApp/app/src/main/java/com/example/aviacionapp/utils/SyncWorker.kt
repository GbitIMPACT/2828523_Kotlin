package com.example.aviacionapp.utils

import android.content.Context
import androidx.work.*
import com.example.aviacionapp.data.local.database.AviacionDatabase
import com.example.aviacionapp.data.local.entities.SyncStatus
import com.example.aviacionapp.data.remote.dto.AddGradeRequest
import com.example.aviacionapp.data.remote.dto.EnrollRequest
import com.example.aviacionapp.di.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class SyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val database = AviacionDatabase.getDatabase(context)
    private val courseDao = database.courseDao()
    private val apiService = NetworkModule.apiService
    private val preferencesManager = PreferencesManager(context)

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val token = preferencesManager.getToken() ?: return@withContext Result.failure()

            // Sincronizar matrículas pendientes
            val pendingEnrollments = courseDao.getEnrollmentsBySyncStatus(SyncStatus.PENDING_SYNC)
            pendingEnrollments.forEach { enrollment ->
                try {
                    val response = apiService.enrollCourse(
                        token,
                        EnrollRequest(enrollment.courseId)
                    )

                    if (response.isSuccessful) {
                        courseDao.updateEnrollment(
                            enrollment.copy(syncStatus = SyncStatus.SYNCED)
                        )
                    }
                } catch (e: Exception) {
                    // Mantener como pendiente para reintentar
                }
            }

            // Sincronizar notas pendientes
            val pendingGrades = courseDao.getGradesBySyncStatus(SyncStatus.PENDING_SYNC)
            pendingGrades.forEach { grade ->
                try {
                    val response = apiService.addGrade(
                        token,
                        AddGradeRequest(
                            matriculaId = grade.matriculaId,
                            descripcion = grade.descripcion,
                            valor = grade.valor
                        )
                    )

                    if (response.isSuccessful) {
                        courseDao.updateGrade(
                            grade.copy(syncStatus = SyncStatus.SYNCED)
                        )
                    }
                } catch (e: Exception) {
                    // Mantener como pendiente para reintentar
                }
            }

            // Eliminar notas marcadas para eliminar
            val gradesToDelete = courseDao.getGradesBySyncStatus(SyncStatus.PENDING_DELETE)
            gradesToDelete.forEach { grade ->
                try {
                    val response = apiService.deleteGrade(token, grade.id)

                    if (response.isSuccessful) {
                        courseDao.deleteGrade(grade)
                    }
                } catch (e: Exception) {
                    // Mantener para reintentar
                }
            }

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    companion object {
        private const val SYNC_WORK_NAME = "sync_work"

        fun setupPeriodicSync(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(
                15, TimeUnit.MINUTES
            )
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(
                    SYNC_WORK_NAME,
                    ExistingPeriodicWorkPolicy.KEEP,
                    syncRequest
                )
        }

        fun syncNow(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val syncRequest = OneTimeWorkRequestBuilder<SyncWorker>()
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(context)
                .enqueue(syncRequest)
        }
    }
}