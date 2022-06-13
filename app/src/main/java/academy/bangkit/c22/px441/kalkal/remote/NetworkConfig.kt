package academy.bangkit.c22.px441.kalkal

import academy.bangkit.c22.px441.kalkal.remote.auth.AuthRequest
import academy.bangkit.c22.px441.kalkal.remote.auth.AuthResponse
import academy.bangkit.c22.px441.kalkal.remote.predict.PredictRequest
import academy.bangkit.c22.px441.kalkal.remote.predict.PredictResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

class NetworkConfig {
    private fun getInterceptor(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    private fun getRetrofit(baseUrl: String) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(getInterceptor())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getPredictService(): PredictService =
        getRetrofit("https://us-east1-bangkit-352714.cloudfunctions.net/")
            .create(PredictService::class.java)

    fun getAuthService(): AuthService =
        getRetrofit("https://bangkit-352714.et.r.appspot.com/")
            .create(AuthService::class.java)
}

interface PredictService {
    @POST("bangkitcf_0")
    fun getPrediction(
        @Body data: PredictRequest
    ): Call<PredictResponse>
}

interface AuthService {
    @POST("createUser")
    fun postRegister(
        @Body data: AuthRequest
    ): Call<AuthResponse>

    @POST("login")
    fun postLogin(
        @Body data: AuthRequest
    ): Call<AuthResponse>
}
