package academy.bangkit.c22.px441.kalkal.ui.main

import academy.bangkit.c22.px441.kalkal.NetworkConfig
import academy.bangkit.c22.px441.kalkal.dataStore
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import academy.bangkit.c22.px441.kalkal.databinding.FragmentMainBinding
import academy.bangkit.c22.px441.kalkal.remote.predict.PredictRequest
import academy.bangkit.c22.px441.kalkal.twoPreciseAbs
import academy.bangkit.c22.px441.kalkal.utils.DATA_STORE_USERNAME
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.message
import com.skydoves.sandwich.request
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            val preferences = binding.root.context.dataStore.data.first()
            try {
                username = preferences[DATA_STORE_USERNAME] ?: ""
            } catch (e: Exception) {
                username = ""
            }
        }

        super.onViewCreated(view, savedInstanceState)

        binding.textView.text = "Selamat datang, ${username}!"

        binding.buttonSubmit.setOnClickListener {
            NetworkConfig().getPredictService()
                .getPrediction(
                    PredictRequest.createFromInput(
                    binding.txtAge,
                    binding.txtRate,
                    binding.txtWeight,
                    binding.txtTemperature,
                    binding.txtHeight,
                    binding.txtDuration
                )).request { response ->
                    when (response) {
                        is ApiResponse.Success -> {
                            binding.txtResult.text =
                                "Kalori yang terbakar adalah ${response.data.prediction?.twoPreciseAbs()}"
                        }
                        is ApiResponse.Failure.Error -> {
                            showMessage(response.message())
                        }
                        is ApiResponse.Failure.Exception -> {
                            showMessage(response.message())
                        }
                    }
                }
        }
    }

    private fun showMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}