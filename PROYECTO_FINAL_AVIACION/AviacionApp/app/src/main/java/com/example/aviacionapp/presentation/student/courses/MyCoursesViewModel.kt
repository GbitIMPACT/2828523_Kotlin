package com.example.aviacionapp.presentation.student.courses

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.aviacionapp.data.local.database.AviacionDatabase
import com.example.aviacionapp.data.local.entities.EnrollmentEntity
import com.example.aviacionapp.data.local.entities.GradeEntity
import com.example.aviacionapp.data.local.entities.SyncStatus
import com.example.aviacionapp.di.NetworkModule
import com.example.aviacionapp.utils.NetworkUtils
import com.example.aviacionapp.utils.PreferencesManager
import com.example.aviacionapp.utils.Result
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class MyCourseItem(
    val courseId: Int,
    val courseName: String,
    val courseDescription: String,
    val matriculaId: Int,
    val fechaMatricula: String,
    val promedioNotas: Double,
    val grades: List<GradeItem>
)

data class GradeItem(
    val id: Int,
    val descripcion: String,
    val valor: Double,
    val fecha: String
)

class MyCoursesViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService = NetworkModule.apiService
    private val database = AviacionDatabase.getDatabase(application)
    private val courseDao = database.courseDao()
    private val studentDao = database.studentDao()
    private val preferencesManager = PreferencesManager(application)

    private val _myCoursesState = MutableLiveData<Result<List<MyCourseItem>>>()
    val myCoursesState: LiveData<Result<List<MyCourseItem>>> = _myCoursesState

    fun loadMyCourses() {
        viewModelScope.launch {
            _myCoursesState.value = Result.Loading

            try {
                if (NetworkUtils.isNetworkAvailable(getApplication())) {
                    val token = preferencesManager.getToken() ?: ""
                    val response = apiService.getStudentCourses(token)

                    if (response.isSuccessful) {
                        response.body()?.let { courses ->
                            val currentStudent = studentDao.getCurrentStudentSync()
                            currentStudent?.let { student ->
                                // Guardar enrollments y grades en local
                                courses.forEach { course ->
                                    if (course.matriculaId != null) {
                                        val enrollment = EnrollmentEntity(
                                            studentId = student.id,
                                            courseId = course.id,
                                            matriculaId = course.matriculaId,
                                            fechaMatricula = course.fechaMatricula ?: "",
                                            estado = course.estadoMatricula ?: "activo",
                                            promedioNotas = course.promedioNotas ?: 0.0,
                                            syncStatus = SyncStatus.SYNCED
                                        )
                                        courseDao.insertEnrollment(enrollment)

                                        // Guardar notas
                                        course.notas?.forEach { grade ->
                                            val gradeEntity = GradeEntity(
                                                matriculaId = course.matriculaId,
                                                descripcion = grade.descripcion,
                                                valor = grade.valor,
                                                fechaRegistro = grade.fechaRegistro,
                                                syncStatus = SyncStatus.SYNCED
                                            )
                                            courseDao.insertGrade(gradeEntity)
                                        }
                                    }
                                }
                            }

                            // Convertir a MyCourseItem
                            val myCourses = courses.filter { it.matriculaId != null }.map { course ->

                                MyCourseItem(

                                    courseId = course.id,

                                    courseName = course.nombre ?: "Sin nombre",

                                    courseDescription = course.descripcion ?: "Sin descripción",

                                    matriculaId = course.matriculaId ?: 0,

                                    fechaMatricula = course.fechaMatricula ?: "",

                                    promedioNotas = course.promedioNotas?.toDouble() ?: 0.0,

                                    grades = course.notas?.map { grade ->

                                        GradeItem(

                                            id = grade.id,

                                            descripcion = grade.descripcion ?: "",

                                            valor = grade.valor,

                                            fecha = grade.fechaRegistro ?: ""

                                        )

                                    } ?: emptyList()
                                )
                            }

                            _myCoursesState.value = Result.Success(myCourses)
                        } ?: run {
                            _myCoursesState.value = Result.Success(emptyList())
                        }
                    } else {
                        _myCoursesState.value = Result.Error(Exception("Error al cargar cursos"))
                    }
                } else {
                    loadFromLocal()
                }
            } catch (e: Exception) {
                _myCoursesState.value = Result.Error(e)
            }
        }
    }

    private suspend fun loadFromLocal() {
        try {
            val currentStudent = studentDao.getCurrentStudentSync()
            if (currentStudent != null) {
                val enrollments = courseDao.getStudentEnrollments(currentStudent.id).first()

                val myCourses = enrollments.mapNotNull { enrollment ->
                    val course = courseDao.getCourseById(enrollment.courseId)
                    if (course != null) {
                        val grades = courseDao.getGradesByMatricula(enrollment.matriculaId).first()

                        MyCourseItem(
                            courseId = enrollment.courseId,
                            courseName = course.nombre,
                            courseDescription = course.descripcion,
                            matriculaId = enrollment.matriculaId,
                            fechaMatricula = enrollment.fechaMatricula,
                            promedioNotas = enrollment.promedioNotas,
                            grades = grades.map { grade ->
                                GradeItem(
                                    id = grade.id,
                                    descripcion = grade.descripcion,
                                    valor = grade.valor,
                                    fecha = grade.fechaRegistro
                                )
                            }
                        )
                    } else null
                }

                _myCoursesState.value = Result.Success(myCourses)
            } else {
                _myCoursesState.value = Result.Success(emptyList())
            }
        } catch (e: Exception) {
            _myCoursesState.value = Result.Error(e)
        }
    }
}