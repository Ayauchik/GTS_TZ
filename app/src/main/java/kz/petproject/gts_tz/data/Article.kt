package kz.petproject.gts_tz.data
/**
 * Represents an Article.
 *
 * @property id The unique identifier for the article.
 * @property title The title of the article.
 * @property content The full text content of the article.
 * @property author The [Author] who wrote the article.
 * @property status The current moderation status ("PUBLISHED", "ON_MODERATION", "REJECTED").
 * @property rejectionReason An optional reason for why the article was rejected. Only present if status is "REJECTED".
 * @property createdAt The timestamp (in milliseconds) when the article was created.
 * @property publishedAt An optional timestamp (in milliseconds) when the article was published.
 */
data class Article(
    val id: String,
    val title: String,
    val content: String,
    val author: Author,
    val status: String,
    val rejectionReason: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val publishedAt: Long? = null
)