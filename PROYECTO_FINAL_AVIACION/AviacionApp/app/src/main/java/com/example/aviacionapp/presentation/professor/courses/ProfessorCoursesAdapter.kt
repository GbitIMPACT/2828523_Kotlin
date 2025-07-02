package com.example.aviacionapp.presentation.professor.courses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aviacionapp.databinding.ItemProfessorCourseBinding

class ProfessorCoursesAdapter(
    private val onCourseClick: (ProfessorCourseItem) -> Unit
) : ListAdapter<ProfessorCourseItem, ProfessorCoursesAdapter.CourseViewHolder>(CoursesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemProfessorCourseBinding.inflate(
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
        private val binding: ItemProfessorCourseBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onCourseClick(getItem(position))
                }
            }
        }

        fun bind(course: ProfessorCourseItem) {
            binding.textCourseName.text = course.nombre
            binding.textCourseDescription.text = course.descripcion
            binding.textDuration.text = "${course.duracionHoras} horas"
            binding.textViewStudents.text = "Ver estudiantes"
        }
    }
}

class CoursesDiffCallback : DiffUtil.ItemCallback<ProfessorCourseItem>() {
    override fun areItemsTheSame(oldItem: ProfessorCourseItem, newItem: ProfessorCourseItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProfessorCourseItem, newItem: ProfessorCourseItem): Boolean {
        return oldItem == newItem
    }
}