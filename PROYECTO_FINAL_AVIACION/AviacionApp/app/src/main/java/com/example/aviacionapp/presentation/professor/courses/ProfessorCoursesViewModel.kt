package com.example.aviacionapp.presentation.professor.courses

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.aviacionapp.data.local.database.AviacionDatabase
import com.example.aviacionapp.data.local.entities.CourseEntity
import com.example.aviacionapp.di.NetworkModule
import com.example.aviacionapp.utils.NetworkUtils
import com.example.aviacionapp.utils.PreferencesManager
import com.example.aviacionapp.utils.Result
import kotlinx.coroutines.launch

data class ProfessorCourseItem(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val duracionHoras: Int,
    val estado: String
)

class ProfessorCoursesViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService = NetworkModule.apiService
    private val database = AviacionDatabase.getDatabase(application)
    private val courseDao = database.courseDao()
    private val preferencesManager = PreferencesManager(application)

    private val _coursesState = MutableLiveData<Result<List<ProfessorCourseItem>>>()
    val coursesState: LiveData<Result<List<ProfessorCourseItem>>> = _coursesState

    fun loadCourses() {
        viewModelScope.launch {
            _coursesState.value = Result.Loading

            try {
                if (NetworkUtils.isNetworkAvailable(getApplication())) {
                    val token = preferencesManager.getToken() ?: ""
                    val response = apiService.getCourses(token)

                    if (response.isSuccessful) {
                        response.body()?.let { courses ->
                            // Guardar en base de datos local
                            val courseEntities = courses.map { course ->
                                CourseEntity(
                                    id = course.id,
                                    nombre = course.nombre ?: "",
                                    descripcion = course.descripcion ?: "",
                                    duracionHoras = course.duracionHoras ?: 0,
                                    estado = course.estado ?: "activo"
                                )
                            }
                            courseDao.deleteAll()
                            courseDao.insertAll(courseEntities)

                            // Convertir a ProfessorCourseItem
                            val courseItems = courses.map { course ->
                                ProfessorCourseItem(
                                    id = course.id,
                                    nombre = course.nombre ?: "",
                                    descripcion = course.descripcion ?: "",
                                    duracionHoras = course.duracionHoras ?: 0,
                                    estado = course.estado ?: "activo"
                                )
                            }

                            _coursesState.value = Result.Success(courseItems)
                        }
                    } else {
                        loadFromLocal()
                    }
                } else {
                    loadFromLocal()
                }
            } catch (e: Exception) {
                loadFromLocal()
            }
        }
    }

    private suspend fun loadFromLocal() {
        courseDao.getAllCourses().collect { courses ->
            val courseItems = courses.map { entity ->
                ProfessorCourseItem(
                    id = entity.id,
                    nombre = entity.nombre,
                    descripcion = entity.descripcion,
                    duracionHoras = entity.duracionHoras,
                    estado = entity.estado
                )
            }
            _coursesState.value = Result.Success(courseItems)
        }
    }
}