package kz.petproject.gts_tz.data// In data/Model.kt
/**
 * Represents a user in the system.
 * @property id The unique identifier for the user.
 * @property name The full name of the user.
 * @property login The unique login username.
 * @property password The user's password (in a real app, this would be a hash).
 * @property role The role of the user ("ADMIN", "AUTHOR", "MODERATOR").
 */
data class User(
    val id: Int,
    val name: String,
    val login: String,
    val password: String, // For UI demo purposes. In a real app, never store plain text passwords.
    val role: String
)