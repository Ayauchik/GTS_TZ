package kz.petproject.gts_tz.data.network.response

data class UserResponse(
    val id: String,
    val login: String,
    val name: String,
    val role: String,
    val token: String
)