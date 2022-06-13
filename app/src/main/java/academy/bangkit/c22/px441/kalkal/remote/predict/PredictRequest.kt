package academy.bangkit.c22.px441.kalkal.remote.predict

import academy.bangkit.c22.px441.kalkal.toInt
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.annotations.SerializedName

data class PredictRequest(

	@field:SerializedName("duration")
	val duration: Int? = null,

	@field:SerializedName("temperature")
	val temperature: Int? = null,

	@field:SerializedName("weight")
	val weight: Int? = null,

	@field:SerializedName("age")
	val age: Int? = null,

	@field:SerializedName("heart")
	val heart: Int? = null,

	@field:SerializedName("height")
	val height: Int? = null
) {
	companion object {
		fun createFromInput(
			txtAge: TextInputEditText,
			txtRate: TextInputEditText,
			txtWeight: TextInputEditText,
			txtTemperature: TextInputEditText,
			txtHeight: TextInputEditText,
			txtDuration: TextInputEditText
		) : PredictRequest = PredictRequest(
			txtAge.toInt(),
			txtRate.toInt(),
			txtWeight.toInt(),
			txtTemperature.toInt(),
			txtHeight.toInt(),
			txtDuration.toInt()
		)
	}
}