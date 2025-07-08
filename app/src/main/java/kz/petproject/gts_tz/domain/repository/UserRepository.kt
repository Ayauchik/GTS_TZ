package kz.petproject.gts_tz.domain.repository

import kz.petproject.gts_tz.data.User
import kz.petproject.gts_tz.data.network.request.CreateUserRequest
import kz.petproject.gts_tz.data.network.request.LoginRequest

interface UserRepository {
    suspend fun login(loginRequest: LoginRequest): Result<Pair<User, String>>
    suspend fun createUser(createUserRequest: CreateUserRequest): Result<User>
}