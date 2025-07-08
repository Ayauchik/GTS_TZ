package kz.petproject.gts_tz.data.network.request

data class CreateUserRequest(
    val name: String,
    val login: String,
    val password: String,
    val role: String
)