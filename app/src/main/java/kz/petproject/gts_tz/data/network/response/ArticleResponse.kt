package kz.petproject.gts_tz.data.network.response

import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    @SerializedName("_id") val id: String,
    val title: String,
    val content: String,
    val author: AuthorResponse, // <-- CRITICAL FIX: Changed back to a nested object
    val status: String,
    @SerializedName("moderatorComments") // Match the backend model field
    val rejectionReason: String? = null,
    val createdAt: String,
    val publishedAt: String? = null,
    val updatedAt: String
)

data class AuthorResponse(
    @SerializedName("_id") val id: String,
    val name: String
)