package kz.petproject.gts_tz.data.network.response

import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    @SerializedName("_id") val id: String,
    val title: String,
    val content: String,
    val author: AuthorResponse,
    val status: String,
    @SerializedName("moderatorComments")
    val rejectionReason: String? = null,
    val createdAt: String,
    val publishedAt: String? = null,
    val updatedAt: String
)

data class AuthorResponse(
    @SerializedName("_id") val id: String,
    val name: String
)