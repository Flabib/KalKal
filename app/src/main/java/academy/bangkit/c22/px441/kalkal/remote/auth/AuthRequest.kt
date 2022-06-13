package academy.bangkit.c22.px441.kalkal.remote.auth

import academy.bangkit.c22.px441.kalkal.toInt
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.annotations.SerializedName

data class AuthRequest(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

) {
	companion object {
		fun createFromInput(
			txtUser: TextInputEditText,
			txtPassword: TextInputEditText,
		) : AuthRequest = AuthRequest(
			txtUser.text.toString(),
			txtPassword.text.toString(),
		)
	}
}