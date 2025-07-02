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
import com.example.aviacionapp.databinding.FragmentProfessorCoursesBinding
import com.example.aviacionapp.utils.Result

class ProfessorCoursesFragment : Fragment() {

    private var _binding: FragmentProfessorCoursesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfessorCoursesViewModel by viewModels()
    private lateinit var coursesAdapter: ProfessorCoursesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfessorCoursesBinding.inflate(inflater, container, false)
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
        coursesAdapter = ProfessorCoursesAdapter { course ->
            // Usar Navigation para navegar a la lista de estudiantes del curso
            val bundle = Bundle().apply {
                putInt("course_id", course.id)
                putString("course_name", course.nombre)
            }
            findNavController().navigate(R.id.navigation_course_students, bundle)
        }

        binding.recyclerViewCourses.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = coursesAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.coursesState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerViewCourses.visibility = View.GONE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false
                    binding.recyclerViewCourses.visibility = View.VISIBLE
                    coursesAdapter.submitList(result.data)
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}