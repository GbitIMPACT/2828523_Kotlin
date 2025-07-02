package com.example.aviacionapp.presentation.student.courses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aviacionapp.databinding.FragmentCoursesBinding
import com.example.aviacionapp.utils.Result
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CoursesFragment : Fragment() {

    private var _binding: FragmentCoursesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CoursesViewModel by viewModels()
    private lateinit var coursesAdapter: CoursesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadCourses()
        }

        viewModel.loadCourses()
    }

    private fun setupRecyclerView() {
        coursesAdapter = CoursesAdapter { course ->
            showEnrollDialog(course)
        }

        binding.recyclerViewCourses.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = coursesAdapter
        }
    }

    private fun showEnrollDialog(course: CourseItem) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Matricularse en ${course.nombre}")
            .setMessage("¿Deseas matricularte en este curso?\n\nDuración: ${course.duracionHoras} horas")
            .setPositiveButton("Matricular") { _, _ ->
                viewModel.enrollInCourse(course.id)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun observeViewModel() {
        viewModel.coursesState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerViewCourses.visibility = View.GONE
                    binding.textEmpty.visibility = View.GONE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false

                    if (result.data.isEmpty()) {
                        binding.recyclerViewCourses.visibility = View.GONE
                        binding.textEmpty.visibility = View.VISIBLE
                    } else {
                        binding.recyclerViewCourses.visibility = View.VISIBLE
                        binding.textEmpty.visibility = View.GONE
                        coursesAdapter.submitList(result.data)
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

        viewModel.enrollResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    Toast.makeText(context, "¡Matriculado exitosamente!", Toast.LENGTH_SHORT).show()
                    viewModel.loadCourses() // Recargar cursos
                }
                is Result.Error -> {
                    Toast.makeText(
                        context,
                        "Error al matricular: ${result.exception.message}",
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