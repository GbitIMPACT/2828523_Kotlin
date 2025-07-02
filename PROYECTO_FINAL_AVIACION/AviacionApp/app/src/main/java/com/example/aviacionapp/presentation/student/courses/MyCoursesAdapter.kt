package com.example.aviacionapp.presentation.student.courses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aviacionapp.R
import com.example.aviacionapp.databinding.ItemMyCourseBinding

class MyCoursesAdapter : ListAdapter<MyCourseItem, MyCoursesAdapter.MyCourseViewHolder>(MyCoursesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCourseViewHolder {
        val binding = ItemMyCourseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyCourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyCourseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MyCourseViewHolder(
        private val binding: ItemMyCourseBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(myCourse: MyCourseItem) {
            try {
                binding.textCourseName.text = myCourse.courseName
                binding.textCourseDescription.text = myCourse.courseDescription
                binding.textEnrollmentDate.text = "Matriculado: ${formatDate(myCourse.fechaMatricula)}"
                binding.textAverage.text = String.format("Promedio: %.1f", myCourse.promedioNotas)

                // Color del promedio según la nota
                val color = when {
                    myCourse.promedioNotas >= 4.0 -> R.color.success_green
                    myCourse.promedioNotas >= 3.0 -> R.color.accent_orange
                    else -> R.color.error_red
                }
                binding.textAverage.setTextColor(binding.root.context.getColor(color))

                // Mostrar/ocultar notas
                if (myCourse.grades.isEmpty()) {
                    binding.layoutGrades.visibility = View.GONE
                    binding.textNoGrades.visibility = View.VISIBLE
                } else {
                    binding.layoutGrades.visibility = View.VISIBLE
                    binding.textNoGrades.visibility = View.GONE

                    // Limpiar y agregar notas
                    binding.layoutGrades.removeAllViews()
                    myCourse.grades.forEach { grade ->
                        val gradeView = LayoutInflater.from(binding.root.context)
                            .inflate(R.layout.item_grade_simple, binding.layoutGrades, false)

                        gradeView.findViewById<TextView>(R.id.textGradeDescription)?.text = grade.descripcion
                        gradeView.findViewById<TextView>(R.id.textGradeValue)?.text = String.format("%.1f", grade.valor)

                        binding.layoutGrades.addView(gradeView)
                    }
                }
            } catch (e: Exception) {
                android.util.Log.e("MyCoursesAdapter", "Error binding course", e)
            }
        }

        private fun formatDate(dateString: String?): String {
            if (dateString.isNullOrEmpty()) return "Fecha no disponible"
            return try {
                val parts = dateString.split("T")[0].split("-")
                if (parts.size >= 3) {
                    "${parts[2]}/${parts[1]}/${parts[0]}"
                } else {
                    dateString
                }
            } catch (e: Exception) {
                dateString
            }
        }
    }
}

class MyCoursesDiffCallback : DiffUtil.ItemCallback<MyCourseItem>() {
    override fun areItemsTheSame(oldItem: MyCourseItem, newItem: MyCourseItem): Boolean {
        return oldItem.courseId == newItem.courseId
    }

    override fun areContentsTheSame(oldItem: MyCourseItem, newItem: MyCourseItem): Boolean {
        return oldItem == newItem
    }
}