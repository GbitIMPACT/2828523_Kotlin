package com.example.aviacionapp.presentation.student.courses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aviacionapp.databinding.FragmentMyCoursesBinding
import com.example.aviacionapp.utils.Result

class MyCoursesFragment : Fragment() {

    private var _binding: FragmentMyCoursesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyCoursesViewModel by viewModels()
    private lateinit var myCoursesAdapter: MyCoursesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadMyCourses()
        }

        viewModel.loadMyCourses()
    }

    private fun setupRecyclerView() {
        myCoursesAdapter = MyCoursesAdapter()

        binding.recyclerViewMyCourses.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = myCoursesAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.myCoursesState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerViewMyCourses.visibility = View.GONE
                    binding.textEmpty.visibility = View.GONE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false

                    if (result.data.isEmpty()) {
                        binding.recyclerViewMyCourses.visibility = View.GONE
                        binding.textEmpty.visibility = View.VISIBLE
                    } else {
                        binding.recyclerViewMyCourses.visibility = View.VISIBLE
                        binding.textEmpty.visibility = View.GONE
                        myCoursesAdapter.submitList(result.data)
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