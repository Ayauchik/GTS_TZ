package kz.petproject.gts_tz.domain.use_cases

import kz.petproject.gts_tz.data.User
import kz.petproject.gts_tz.data.network.request.CreateUserRequest
import kz.petproject.gts_tz.domain.repository.UserRepository

class CreateUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(request: CreateUserRequest): Result<User> {
        return userRepository.createUser(request)
    }
}