package com.example.aviacionapp.presentation.student.courses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aviacionapp.databinding.ItemCourseBinding

class CoursesAdapter(
    private val onCourseClick: (CourseItem) -> Unit
) : ListAdapter<CourseItem, CoursesAdapter.CourseViewHolder>(CoursesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemCourseBinding.inflate(
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
        private val binding: ItemCourseBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onCourseClick(getItem(position))
                }
            }
        }

        fun bind(course: CourseItem) {
            binding.textCourseName.text = course.nombre
            binding.textCourseDescription.text = course.descripcion
            binding.textDuration.text = "${course.duracionHoras} horas"
        }
    }
}

class CoursesDiffCallback : DiffUtil.ItemCallback<CourseItem>() {
    override fun areItemsTheSame(oldItem: CourseItem, newItem: CourseItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CourseItem, newItem: CourseItem): Boolean {
        return oldItem == newItem
    }
}