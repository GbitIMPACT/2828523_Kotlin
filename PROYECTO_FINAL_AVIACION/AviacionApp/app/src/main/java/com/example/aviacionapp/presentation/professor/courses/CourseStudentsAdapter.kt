package com.example.aviacionapp.presentation.professor.courses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aviacionapp.R
import com.example.aviacionapp.databinding.ItemCourseStudentBinding

class CourseStudentsAdapter(
    private val onStudentClick: (CourseStudentItem) -> Unit,
    private val onAddGradeClick: (CourseStudentItem) -> Unit
) : ListAdapter<CourseStudentItem, CourseStudentsAdapter.StudentViewHolder>(StudentsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemCourseStudentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class StudentViewHolder(
        private val binding: ItemCourseStudentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onStudentClick(getItem(position))
                }
            }

            binding.btnAddGrade.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onAddGradeClick(getItem(position))
                }
            }
        }

        fun bind(student: CourseStudentItem) {
            binding.textStudentName.text = "${student.nombre} ${student.apellido}"
            binding.textStudentEmail.text = student.correoElectronico
            binding.textStudentId.text = "ID: ${student.numeroIdentificacion}"
            binding.textAverage.text = String.format("Promedio: %.1f", student.promedioNotas)

            // Color del promedio
            val color = when {
                student.promedioNotas >= 4.0 -> R.color.success_green
                student.promedioNotas >= 3.0 -> R.color.accent_orange
                else -> R.color.error_red
            }
            binding.textAverage.setTextColor(binding.root.context.getColor(color))
        }
    }
}

class StudentsDiffCallback : DiffUtil.ItemCallback<CourseStudentItem>() {
    override fun areItemsTheSame(oldItem: CourseStudentItem, newItem: CourseStudentItem): Boolean {
        return oldItem.estudianteId == newItem.estudianteId
    }

    override fun areContentsTheSame(oldItem: CourseStudentItem, newItem: CourseStudentItem): Boolean {
        return oldItem == newItem
    }
}