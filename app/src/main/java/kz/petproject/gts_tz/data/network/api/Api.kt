package kz.petproject.gts_tz.data.network.api// data/api/AuthApi.kt
import kz.petproject.gts_tz.data.network.request.CreateArticleRequest
import kz.petproject.gts_tz.data.network.request.CreateUserRequest
import kz.petproject.gts_tz.data.network.request.LoginRequest
import kz.petproject.gts_tz.data.network.request.RejectRequest
import kz.petproject.gts_tz.data.network.response.ArticleResponse
import kz.petproject.gts_tz.data.network.response.CreateUserResponse
import kz.petproject.gts_tz.data.network.response.PostSignInResponse
import kz.petproject.gts_tz.data.network.response.WrappedArticleResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface Api {
    @POST("/api/v1/user/signin")
    suspend fun login(@Body request: LoginRequest): Response<PostSignInResponse>

    @POST("/api/v1/user/create")
    suspend fun createUser(@Body request: CreateUserRequest): Response<CreateUserResponse>


    @GET("/api/v1/articles/my-articles")
    suspend fun getMyArticles(): Response<List<ArticleResponse>>

    @POST("/api/v1/articles")
    suspend fun createArticle(@Body request: CreateArticleRequest): Response<ArticleResponse>

    @PATCH("/api/v1/articles/{articleId}/submit")
    suspend fun submitArticle(@Path("articleId") articleId: String): Response<WrappedArticleResponse>

    @GET("/api/v1/articles/published")
    suspend fun getPublishedArticles(): Response<List<ArticleResponse>>

    @GET("/api/v1/articles/{id}")
    suspend fun getArticleById(@Path("id") articleId: String): Response<ArticleResponse>

    @PATCH("/api/v1/articles/{id}/edit")
    suspend fun editArticle(@Path("id") articleId: String, @Body request: CreateArticleRequest): Response<WrappedArticleResponse>


    @GET("/api/v1/articles/moderation-queue")
    suspend fun getModerationQueue(): Response<List<ArticleResponse>>

    @PATCH("/api/v1/articles/{id}/approve")
    suspend fun approveArticle(@Path("id") articleId: String): Response<ArticleResponse>

    @PATCH("/api/v1/articles/{id}/reject")
    suspend fun rejectArticle(@Path("id") articleId: String, @Body request: RejectRequest): Response<ArticleResponse>
}
