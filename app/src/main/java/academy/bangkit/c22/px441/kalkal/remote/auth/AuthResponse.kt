package academy.bangkit.c22.px441.kalkal.remote.auth

import academy.bangkit.c22.px441.kalkal.toInt
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.annotations.SerializedName

data class AuthResponse(

	@field:SerializedName("message")
	val message: String = "",

)