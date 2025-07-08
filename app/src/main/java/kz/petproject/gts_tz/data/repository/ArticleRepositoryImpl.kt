package kz.petproject.gts_tz.data.repository

import kz.petproject.gts_tz.data.Article
import kz.petproject.gts_tz.data.network.api.Api
import kz.petproject.gts_tz.data.network.mapper.ArticleMapper
import kz.petproject.gts_tz.data.network.request.CreateArticleRequest
import kz.petproject.gts_tz.data.network.request.RejectRequest
import kz.petproject.gts_tz.domain.repository.ArticleRepository

class ArticleRepositoryImpl(
    private val api: Api,
    private val mapper: ArticleMapper
) : ArticleRepository {
    override suspend fun getMyArticles(): Result<List<Article>> = try {
        val response = api.getMyArticles()
        if (response.isSuccessful && response.body() != null) {
            Result.success(mapper.fromRemoteListToDomain(response.body()!!))
        } else {
            Result.failure(Exception(response.errorBody()?.string() ?: "Failed to get articles"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun createArticle(request: CreateArticleRequest): Result<Article> = try {
        val response = api.createArticle(request)
        if (response.isSuccessful && response.body() != null) {
            Result.success(mapper.fromRemoteToDomain(response.body()!!))
        } else {
            Result.failure(Exception(response.errorBody()?.string() ?: "Failed to create article"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun submitArticle(articleId: String): Result<Article> = try {
        val response = api.submitArticle(articleId)
        if (response.isSuccessful && response.body() != null) {
            // UNWRAP the article from the response body
            val articleResponse = response.body()!!.article
            Result.success(mapper.fromRemoteToDomain(articleResponse))
        } else {
            Result.failure(Exception(response.errorBody()?.string() ?: "Failed to submit article"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getPublishedArticles(): Result<List<Article>> = try {
        val response = api.getPublishedArticles()
        if (response.isSuccessful && response.body() != null) {
            Result.success(mapper.fromRemoteListToDomain(response.body()!!))
        } else {
            Result.failure(
                Exception(
                    response.errorBody()?.string() ?: "Failed to get published articles"
                )
            )
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getArticleById(articleId: String): Result<Article> = try {
        val response = api.getArticleById(articleId)
        if (response.isSuccessful && response.body() != null) {
            Result.success(mapper.fromRemoteToDomain(response.body()!!))
        } else {
            Result.failure(
                Exception(
                    response.errorBody()?.string() ?: "Failed to get article by id"
                )
            )
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun editArticle(
        articleId: String,
        request: CreateArticleRequest // <-- Use specific request
    ): Result<Article> = try {
        val response = api.editArticle(articleId, request)
        if (response.isSuccessful && response.body() != null) {
            // UNWRAP the article from the response body
            val articleResponse = response.body()!!.article
            Result.success(mapper.fromRemoteToDomain(articleResponse))
        } else {
            Result.failure(Exception(response.errorBody()?.string() ?: "Failed to edit article"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getModerationQueue(): Result<List<Article>> = try {
        val response = api.getModerationQueue()
        if (response.isSuccessful && response.body() != null) {
            Result.success(mapper.fromRemoteListToDomain(response.body()!!))
        } else {
            Result.failure(
                Exception(
                    response.errorBody()?.string() ?: "Failed to get articles for moderation"
                )
            )
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun approveArticle(articleId: String): Result<Article> = try {
        val response = api.approveArticle(articleId)
        if (response.isSuccessful && response.body() != null) {
            Result.success(mapper.fromRemoteToDomain(response.body()!!))
        } else {
            Result.failure(Exception(response.errorBody()?.string() ?: "Failed to approve article"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun rejectArticle(articleId: String, reason: String): Result<Article> = try {
        val response = api.rejectArticle(articleId, RejectRequest(reason))
        if (response.isSuccessful && response.body() != null) {
            Result.success(mapper.fromRemoteToDomain(response.body()!!))
        } else {
            Result.failure(Exception(response.errorBody()?.string() ?: "Failed to reject article"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}