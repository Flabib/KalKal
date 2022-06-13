package academy.bangkit.c22.px441.kalkal.ui.register

import academy.bangkit.c22.px441.kalkal.NetworkConfig
import academy.bangkit.c22.px441.kalkal.databinding.ActivityRegisterBinding
import academy.bangkit.c22.px441.kalkal.remote.auth.AuthRequest
import academy.bangkit.c22.px441.kalkal.ui.login.LoginActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.message
import com.skydoves.sandwich.request

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAction.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finishAffinity()
        }

        binding.buttonSubmit.setOnClickListener {
            NetworkConfig().getAuthService()
                .postRegister(
                    AuthRequest.createFromInput(
                        binding.txtUser,
                        binding.txtPassword,
                    )).request { response ->
                    when (response) {
                        is ApiResponse.Success -> {
                            showMessage(response.data.message)

                            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
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