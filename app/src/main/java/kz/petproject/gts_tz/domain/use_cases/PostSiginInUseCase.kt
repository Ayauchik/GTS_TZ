package kz.petproject.gts_tz.domain.use_cases

import kz.petproject.gts_tz.data.User
import kz.petproject.gts_tz.data.network.request.LoginRequest
import kz.petproject.gts_tz.domain.repository.UserRepository

class PostSiginInUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(loginRequest: LoginRequest): Result<Pair<User, String>> {
        return userRepository.login(loginRequest)
    }
}