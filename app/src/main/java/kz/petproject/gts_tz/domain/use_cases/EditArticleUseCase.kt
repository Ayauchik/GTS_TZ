package kz.petproject.gts_tz.domain.use_cases

import kz.petproject.gts_tz.data.Article
import kz.petproject.gts_tz.data.network.request.CreateArticleRequest
import kz.petproject.gts_tz.domain.repository.ArticleRepository

class EditArticleUseCase(private val articleRepository: ArticleRepository) {
    suspend operator fun invoke(articleId: String, title: String, content: String): Result<Article> {
        val request = CreateArticleRequest(title, content)
        return articleRepository.editArticle(articleId, request)
    }
}