package kz.petproject.gts_tz.data.network.mapper

import kz.petproject.gts_tz.data.Article
import kz.petproject.gts_tz.data.Author
import kz.petproject.gts_tz.data.network.response.ArticleResponse
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class ArticleMapper {
    private fun parseIsoDate(dateString: String?): Long? {
        if (dateString == null) return null
        return try {
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            format.parse(dateString)?.time
        } catch (e: Exception) {
            null
        }
    }

    fun fromRemoteToDomain(response: ArticleResponse): Article {
        return Article(
            id = response.id, // Direct String mapping
            title = response.title,
            content = response.content,
            // Map the nested AuthorResponse object to the domain Author object
            author = Author(id = response.author.id.hashCode(), name = response.author.name),
            status = response.status.uppercase(),
            rejectionReason = response.rejectionReason,
            createdAt = parseIsoDate(response.createdAt) ?: System.currentTimeMillis(),
            publishedAt = parseIsoDate(response.publishedAt) ?: System.currentTimeMillis(),
            updatedAt = parseIsoDate(response.updatedAt) ?: System.currentTimeMillis()
        )
    }

    fun fromRemoteListToDomain(list: List<ArticleResponse>): List<Article> {
        return list.map { fromRemoteToDomain(it) }
    }
}