package kz.petproject.gts_tz.data.network.response

import com.google.gson.annotations.SerializedName

data class CreateUserResponse(
    val message: String,
    val user: CreatedUser
)

data class CreatedUser(
    @SerializedName("_id")
    val id: String,
    val name: String,
    val login: String,
    val role: String
)