package kz.petproject.gts_tz.domain.use_cases

import kz.petproject.gts_tz.data.Article
import kz.petproject.gts_tz.data.network.request.CreateArticleRequest
import kz.petproject.gts_tz.domain.repository.ArticleRepository

class CreateArticleUseCase(private val repository: ArticleRepository) {
    suspend operator fun invoke(title: String, content: String): Result<Article> {
        val request = CreateArticleRequest(title, content)
        return repository.createArticle(request)
    }
}