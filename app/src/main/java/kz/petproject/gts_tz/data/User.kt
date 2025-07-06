package kz.petproject.gts_tz.data

/**
 * Represents a user in the system.
 * @property id The unique identifier for the user.
 * @property name The full name of the user.
 * @property login The unique login username.
 * @property role The role of the user ("ADMIN", "AUTHOR", "MODERATOR").
 */
data class User(
    val id: String,
    val name: String,
    val login: String,
    val role: String
)