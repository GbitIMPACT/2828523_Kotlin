package com.example.aviacionapp.presentation.professor.students

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aviacionapp.R
import com.example.aviacionapp.databinding.DialogAddGradeBinding
import com.example.aviacionapp.databinding.FragmentStudentDetailBinding
import com.example.aviacionapp.utils.Result
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class StudentDetailFragment : Fragment() {

    private var _binding: FragmentStudentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StudentDetailViewModel by viewModels()
    private lateinit var coursesAdapter: StudentCoursesAdapter

    private var studentId: Int = 0

    companion object {
        private const val ARG_STUDENT_ID = "student_id"

        fun newInstance(studentId: Int): StudentDetailFragment {
            return StudentDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_STUDENT_ID, studentId)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        studentId = arguments?.getInt(ARG_STUDENT_ID) ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        if (studentId > 0) {
            viewModel.loadStudentDetails(studentId)
        }
    }

    private fun setupRecyclerView() {
        coursesAdapter = StudentCoursesAdapter { courseWithGrades ->
            // Mostrar diálogo para agregar nota
            showAddGradeDialog(courseWithGrades)
        }

        binding.recyclerViewCourses.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = coursesAdapter
            isNestedScrollingEnabled = false
        }
    }

    private fun observeViewModel() {
        viewModel.studentDetails.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE

                    val details = result.data
                    binding.textStudentName.text = "${details.nombre} ${details.apellido}"
                    binding.textStudentEmail.text = details.correoElectronico
                    binding.textStudentDetails.text = buildString {
                        appendLine("Edad: ${details.edad} años")
                        appendLine("Tipo ID: ${details.tipoIdentificacion}")
                        appendLine("Número ID: ${details.numeroIdentificacion}")
                    }

                    if (details.courses.isEmpty()) {
                        binding.recyclerViewCourses.visibility = View.GONE
                        binding.textNoCourses.visibility = View.VISIBLE
                    } else {
                        binding.recyclerViewCourses.visibility = View.VISIBLE
                        binding.textNoCourses.visibility = View.GONE
                        coursesAdapter.submitList(details.courses)
                    }
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
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
                    viewModel.loadStudentDetails(studentId) // Recargar datos
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

    private fun showAddGradeDialog(courseWithGrades: CourseWithGrades) {
        val dialogBinding = DialogAddGradeBinding.inflate(layoutInflater)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Agregar Nota - ${courseWithGrades.courseName}")
            .setView(dialogBinding.root)
            .setPositiveButton("Agregar") { _, _ ->
                val description = dialogBinding.etDescription.text.toString().trim()
                val value = dialogBinding.etValue.text.toString().toDoubleOrNull() ?: 0.0

                if (description.isNotEmpty() && value in 0.0..5.0) {
                    viewModel.addGrade(courseWithGrades.matriculaId, description, value)
                } else {
                    Toast.makeText(context, "Datos inválidos", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}