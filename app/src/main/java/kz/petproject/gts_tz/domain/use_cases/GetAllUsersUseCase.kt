package kz.petproject.gts_tz.domain.use_cases

import kz.petproject.gts_tz.data.User
import kz.petproject.gts_tz.domain.repository.UserRepository

class GetAllUsersUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(): Result<List<User>> {
        return userRepository.getAllUsers()
    }
}