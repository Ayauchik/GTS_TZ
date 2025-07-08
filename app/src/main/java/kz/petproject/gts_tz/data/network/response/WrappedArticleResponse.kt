package kz.petproject.gts_tz.data.network.response

import com.google.gson.annotations.SerializedName

/**
 * Represents the server's standard wrapped response for actions
 * that return both a message and an article object.
 * Used for 'edit' and 'submit' operations.
 */
data class WrappedArticleResponse(
    @SerializedName("message") val message: String,
    @SerializedName("article") val article: ArticleResponse
)