package com.example.aviacionapp.presentation.professor.students

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aviacionapp.R
import com.example.aviacionapp.databinding.ItemStudentCourseBinding

class StudentCoursesAdapter(
    private val onAddGradeClick: (CourseWithGrades) -> Unit
) : ListAdapter<CourseWithGrades, StudentCoursesAdapter.CourseViewHolder>(CoursesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemStudentCourseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CourseViewHolder(
        private val binding: ItemStudentCourseBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(courseWithGrades: CourseWithGrades) {
            binding.textCourseName.text = courseWithGrades.courseName
            binding.textAverage.text = String.format("Promedio: %.1f", courseWithGrades.promedio)

            // Color del promedio
            val color = when {
                courseWithGrades.promedio >= 4.0 -> R.color.success_green
                courseWithGrades.promedio >= 3.0 -> R.color.accent_orange
                else -> R.color.error_red
            }
            binding.textAverage.setTextColor(binding.root.context.getColor(color))

            // Configurar botón de agregar nota
            binding.btnAddGrade.setOnClickListener {
                onAddGradeClick(courseWithGrades)
            }

            // Mostrar/ocultar notas
            if (courseWithGrades.grades.isEmpty()) {
                binding.layoutGrades.visibility = View.GONE
                binding.textNoGrades.visibility = View.VISIBLE
            } else {
                binding.layoutGrades.visibility = View.VISIBLE
                binding.textNoGrades.visibility = View.GONE

                // Limpiar y agregar notas
                binding.layoutGrades.removeAllViews()
                courseWithGrades.grades.forEach { grade ->
                    val gradeView = LayoutInflater.from(binding.root.context)
                        .inflate(R.layout.item_grade_editable, binding.layoutGrades, false)

                    gradeView.findViewById<TextView>(R.id.textGradeDescription).text = grade.descripcion
                    gradeView.findViewById<TextView>(R.id.textGradeValue).text = String.format("%.1f", grade.valor)
                    gradeView.findViewById<TextView>(R.id.textGradeDate).text = formatDate(grade.fecha)

                    binding.layoutGrades.addView(gradeView)
                }
            }
        }

        private fun formatDate(dateString: String): String {
            return try {
                val parts = dateString.split("T")[0].split("-")
                "${parts[2]}/${parts[1]}/${parts[0]}"
            } catch (e: Exception) {
                dateString
            }
        }
    }
}

class CoursesDiffCallback : DiffUtil.ItemCallback<CourseWithGrades>() {
    override fun areItemsTheSame(oldItem: CourseWithGrades, newItem: CourseWithGrades): Boolean {
        return oldItem.courseId == newItem.courseId
    }

    override fun areContentsTheSame(oldItem: CourseWithGrades, newItem: CourseWithGrades): Boolean {
        return oldItem == newItem
    }
}