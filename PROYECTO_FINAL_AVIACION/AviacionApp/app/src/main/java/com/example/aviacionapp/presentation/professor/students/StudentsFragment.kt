package com.example.aviacionapp.presentation.professor.students

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
import com.example.aviacionapp.databinding.FragmentStudentsBinding
import com.example.aviacionapp.utils.Result

class StudentsFragment : Fragment() {

    private var _binding: FragmentStudentsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StudentsViewModel by viewModels()
    private lateinit var studentsAdapter: StudentsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadStudents()
        }

        viewModel.loadStudents()
    }

    private fun setupRecyclerView() {
        studentsAdapter = StudentsAdapter { student ->
            // Usar Navigation para navegar al detalle del estudiante
            val bundle = Bundle().apply {
                putInt("student_id", student.id)
            }
            findNavController().navigate(R.id.navigation_student_detail, bundle)
        }

        binding.recyclerViewStudents.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = studentsAdapter
        }
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}