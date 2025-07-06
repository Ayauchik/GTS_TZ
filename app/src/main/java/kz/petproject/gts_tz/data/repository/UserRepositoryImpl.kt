package kz.petproject.gts_tz.data.repository

import kz.petproject.gts_tz.data.User
import kz.petproject.gts_tz.data.network.api.Api
import kz.petproject.gts_tz.data.network.mapper.UserMapper
import kz.petproject.gts_tz.data.network.request.LoginRequest
import kz.petproject.gts_tz.domain.repository.UserRepository

class UserRepositoryImpl(
    private val api: Api,
    private val userMapper: UserMapper
) : UserRepository {
    override suspend fun login(loginRequest: LoginRequest): Result<Pair<User, String>> {
        return try {
            val response = api.login(loginRequest)
            if (response.isSuccessful && response.body() != null) {
                val responseBody = response.body()!!
                val user = userMapper.fromRemoteToDomain(responseBody)
                val token = responseBody.userResponse.token
                Result.success(user to token)
            } else {
                val errorMsg = response.errorBody()?.string() ?: response.message()
                Result.failure(Exception("Login failed: $errorMsg"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}