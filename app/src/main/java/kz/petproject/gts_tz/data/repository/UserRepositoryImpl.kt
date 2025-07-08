package kz.petproject.gts_tz.data.repository

import com.google.gson.Gson
import kz.petproject.gts_tz.data.User
import kz.petproject.gts_tz.data.network.api.Api
import kz.petproject.gts_tz.data.network.mapper.UserMapper
import kz.petproject.gts_tz.data.network.request.CreateUserRequest
import kz.petproject.gts_tz.data.network.request.LoginRequest
import kz.petproject.gts_tz.data.network.response.ErrorResponse
import kz.petproject.gts_tz.data.network.response.UserResponse
import kz.petproject.gts_tz.domain.repository.UserRepository
import retrofit2.Response

class UserRepositoryImpl(
    private val api: Api,
    private val userMapper: UserMapper
) : UserRepository {

    override suspend fun login(loginRequest: LoginRequest): Result<Pair<User, String>> {
        return try {
            val response = api.login(loginRequest)
            if (response.isSuccessful && response.body() != null) {
                val responseBody = response.body()!!
                val user = userMapper.fromRemoteLoginToDomain(responseBody)
                val token = responseBody.userResponse.token
                Result.success(user to token)
            } else {
                // Use the helper to get the clean error message
                val errorMessage = parseErrorMessage(response)
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createUser(createUserRequest: CreateUserRequest): Result<User> {
        return try {
            val response = api.createUser(createUserRequest)
            if (response.isSuccessful && response.body() != null) {
                Result.success(userMapper.fromRemoteCreateToDomain(response.body()!!))
            } else {
                // Reuse the same helper for consistent error handling
                val errorMessage = parseErrorMessage(response)
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllUsers(): Result<List<User>> = try {
        val response = api.getAllUsers()
        if (response.isSuccessful && response.body() != null) {
            val userResponses = response.body()!!
            val domainUsers = userResponses.map { userMapper.fromRemoteToDomain(it) }
            Result.success(domainUsers)
        } else {
            val errorMessage = parseErrorMessage(response)
            Result.failure(Exception(errorMessage))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    /**
     * A private helper function to parse the error body from a Retrofit Response.
     */
    private fun parseErrorMessage(response: Response<*>): String {
        val errorBody = response.errorBody()?.string()
        if (errorBody != null) {
            return try {
                // Use Gson to parse the JSON error string into our ErrorResponse object
                val gson = Gson()
                val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                // Return the clean message
                errorResponse.message
            } catch (e: Exception) {
                // If the error body is not a valid JSON, return the raw string
                errorBody
            }
        }
        // Fallback if there's no error body
        return "An unknown error occurred"
    }
}