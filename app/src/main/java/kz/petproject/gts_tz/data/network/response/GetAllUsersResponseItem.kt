package kz.petproject.gts_tz.data.network.response

data class GetAllUsersResponseItem(
    val _id: String,
    val login: String,
    val name: String,
    val role: String
)