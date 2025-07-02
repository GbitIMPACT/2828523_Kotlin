package com.example.aviacionapp.presentation.professor.students

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aviacionapp.databinding.ItemStudentBinding

class StudentsAdapter(
    private val onStudentClick: (StudentItem) -> Unit
) : ListAdapter<StudentItem, StudentsAdapter.StudentViewHolder>(StudentsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemStudentBinding.inflate(
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
        private val binding: ItemStudentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onStudentClick(getItem(position))
                }
            }
        }

        fun bind(student: StudentItem) {
            binding.textStudentName.text = "${student.nombre} ${student.apellido}"
            binding.textStudentEmail.text = student.correoElectronico
            binding.textStudentId.text = "ID: ${student.numeroIdentificacion}"
            binding.textStudentAge.text = "${student.edad} años"
        }
    }
}

class StudentsDiffCallback : DiffUtil.ItemCallback<StudentItem>() {
    override fun areItemsTheSame(oldItem: StudentItem, newItem: StudentItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: StudentItem, newItem: StudentItem): Boolean {
        return oldItem == newItem
    }
}