package academy.bangkit.c22.px441.kalkal.ui.login

import academy.bangkit.c22.px441.kalkal.NetworkConfig
import academy.bangkit.c22.px441.kalkal.dataStore
import academy.bangkit.c22.px441.kalkal.databinding.ActivityLoginBinding
import academy.bangkit.c22.px441.kalkal.remote.auth.AuthRequest
import academy.bangkit.c22.px441.kalkal.remote.predict.PredictRequest
import academy.bangkit.c22.px441.kalkal.twoPreciseAbs
import academy.bangkit.c22.px441.kalkal.ui.main.MainActivity
import academy.bangkit.c22.px441.kalkal.ui.register.RegisterActivity
import academy.bangkit.c22.px441.kalkal.utils.DATA_STORE_USERNAME
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.message
import com.skydoves.sandwich.request
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            val preferences = dataStore.data.first()
            try {
                if ((preferences[DATA_STORE_USERNAME] ?: "").isNotEmpty())
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            } finally {
            }
        }

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAction.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finishAffinity()
        }

        binding.buttonSubmit.setOnClickListener {
            NetworkConfig().getAuthService()
                .postLogin(
                    AuthRequest.createFromInput(
                        binding.txtUser,
                        binding.txtPassword,
                    )).request { response ->
                    when (response) {
                        is ApiResponse.Success -> {
                            lifecycleScope.launch {
                                this@LoginActivity.dataStore.edit { settings ->
                                    settings[DATA_STORE_USERNAME] = response.data.message.drop(5)
                                }
                            }

                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finishAffinity()
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
}