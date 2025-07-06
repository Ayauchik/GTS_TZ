package kz.petproject.gts_tz.data.network.api// data/api/AuthApi.kt
import kz.petproject.gts_tz.data.network.request.LoginRequest
import kz.petproject.gts_tz.data.network.response.PostSignInResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

interface Api {
    @POST("/api/v1/user/signin")
    suspend fun login(@Body request: LoginRequest): Response<PostSignInResponse>
}
