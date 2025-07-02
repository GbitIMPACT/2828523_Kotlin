package com.example.aviacionapp.presentation.student.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.aviacionapp.databinding.FragmentProfileBinding
import com.example.aviacionapp.utils.Result

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadProfile()
        }

        viewModel.loadProfile()
    }

    private fun observeViewModel() {
        viewModel.profileState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.cardProfile.visibility = View.GONE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false
                    binding.cardProfile.visibility = View.VISIBLE

                    val profile = result.data
                    binding.apply {
                        textName.text = "${profile.nombre} ${profile.apellido}"
                        textEmail.text = profile.correoElectronico
                        textAge.text = "Edad: ${profile.edad} años"
                        textIdType.text = "Tipo ID: ${profile.tipoIdentificacion}"
                        textIdNumber.text = "Número ID: ${profile.numeroIdentificacion}"
                        textRegistrationDate.text = "Registrado: ${formatDate(profile.fechaRegistro)}"

                        // Estadísticas
                        textCoursesCount.text = profile.coursesCount.toString()
                        textAverageGrade.text = String.format("%.2f", profile.averageGrade)

                        // Color del promedio
                        val color = when {
                            profile.averageGrade >= 4.0 -> com.example.aviacionapp.R.color.success_green
                            profile.averageGrade >= 3.0 -> com.example.aviacionapp.R.color.accent_orange
                            else -> com.example.aviacionapp.R.color.error_red
                        }
                        textAverageGrade.setTextColor(requireContext().getColor(color))
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

    private fun formatDate(dateString: String): String {
        return try {
            val parts = dateString.split("T")[0].split("-")
            "${parts[2]}/${parts[1]}/${parts[0]}"
        } catch (e: Exception) {
            dateString
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}