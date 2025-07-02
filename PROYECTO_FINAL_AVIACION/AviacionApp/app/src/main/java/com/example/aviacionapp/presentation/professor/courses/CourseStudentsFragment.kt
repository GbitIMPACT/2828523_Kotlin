package com.example.aviacionapp.presentation.professor.courses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aviacionapp.R
import com.example.aviacionapp.databinding.DialogAddGradeBinding
import com.example.aviacionapp.databinding.FragmentCourseStudentsBinding
import com.example.aviacionapp.utils.Result
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CourseStudentsFragment : Fragment() {

    private var _binding: FragmentCourseStudentsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CourseStudentsViewModel by viewModels()
    private lateinit var studentsAdapter: CourseStudentsAdapter

    private var courseId: Int = 0
    private var courseName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        courseId = arguments?.getInt("course_id") ?: 0
        courseName = arguments?.getString("course_name") ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCourseStudentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textCourseName.text = courseName

        setupRecyclerView()
        observeViewModel()

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadCourseStudents(courseId)
        }

        if (courseId > 0) {
            viewModel.loadCourseStudents(courseId)
        }
    }

    private fun setupRecyclerView() {
        studentsAdapter = CourseStudentsAdapter(
            onStudentClick = { student ->
                // Usar Navigation para navegar al detalle del estudiante
                val bundle = Bundle().apply {
                    putInt("student_id", student.estudianteId)
                }
                findNavController().navigate(R.id.navigation_student_detail, bundle)
            },
            onAddGradeClick = { student ->
                showAddGradeDialog(student)
            }
        )

        binding.recyclerViewStudents.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = studentsAdapter
        }
    }

    private fun showAddGradeDialog(student: CourseStudentItem) {
        val dialogBinding = DialogAddGradeBinding.inflate(layoutInflater)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Agregar Nota - ${student.nombre} ${student.apellido}")
            .setView(dialogBinding.root)
            .setPositiveButton("Agregar") { _, _ ->
                val description = dialogBinding.etDescription.text.toString().trim()
                val value = dialogBinding.etValue.text.toString().toDoubleOrNull() ?: 0.0

                if (description.isNotEmpty() && value in 0.0..5.0) {
                    viewModel.addGrade(student.matriculaId, description, value)
                } else {
                    Toast.makeText(context, "Datos inválidos. La nota debe estar entre 0.0 y 5.0", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun observeViewModel() {
        viewModel.studentsState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerViewStudents.visibility = View.GONE
                    binding.textEmpty.visibility = View.GONE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false

                    if (result.data.isEmpty()) {
                        binding.recyclerViewStudents.visibility = View.GONE
                        binding.textEmpty.visibility = View.VISIBLE
                    } else {
                        binding.recyclerViewStudents.visibility = View.VISIBLE
                        binding.textEmpty.visibility = View.GONE
                        studentsAdapter.submitList(result.data)
                    }
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false

                    Toast.makeText(
                        context,
                        "Error: ${result.exception.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        viewModel.addGradeResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    Toast.makeText(context, "Nota agregada exitosamente", Toast.LENGTH_SHORT).show()
                    viewModel.loadCourseStudents(courseId)
                }
                is Result.Error -> {
                    Toast.makeText(
                        context,
                        "Error al agregar nota: ${result.exception.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {}
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}