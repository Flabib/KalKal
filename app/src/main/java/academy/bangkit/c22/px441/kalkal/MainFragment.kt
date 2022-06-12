package academy.bangkit.c22.px441.kalkal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import academy.bangkit.c22.px441.kalkal.databinding.FragmentMainBinding
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.message
import com.skydoves.sandwich.request
import retrofit2.Call
import retrofit2.Callback

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSubmit.setOnClickListener {
            NetworkConfig().getService()
                .getPrediction(Request.createFromInput(
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