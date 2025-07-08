package kz.petproject.gts_tz.domain.repository

import kz.petproject.gts_tz.data.Article
import kz.petproject.gts_tz.data.network.request.CreateArticleRequest

interface ArticleRepository {
    suspend fun getMyArticles(): Result<List<Article>>
    suspend fun createArticle(request: CreateArticleRequest): Result<Article>
    suspend fun submitArticle(articleId: String): Result<Article>
    suspend fun deleteArticle(articleId: String): Result<Unit>

    suspend fun getPublishedArticles(): Result<List<Article>>
    suspend fun getArticleById(articleId: String): Result<Article>
    suspend fun editArticle(articleId: String, request: CreateArticleRequest): Result<Article>


    suspend fun getModerationQueue(): Result<List<Article>>
    suspend fun approveArticle(articleId: String): Result<Article>
    suspend fun rejectArticle(articleId: String, reason: String): Result<Article>
}