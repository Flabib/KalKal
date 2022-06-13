package academy.bangkit.c22.px441.kalkal.remote.predict

import com.google.gson.annotations.SerializedName

data class PredictResponse(

	@field:SerializedName("prediction")
	val prediction: Float? = null,

)
