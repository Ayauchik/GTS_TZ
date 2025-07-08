package kz.petproject.gts_tz.domain.use_cases

import kz.petproject.gts_tz.domain.repository.ArticleRepository

class DeleteArticleUseCase(private val articleRepository: ArticleRepository) {
    suspend operator fun invoke(articleId: String): Result<Unit> {
        return articleRepository.deleteArticle(articleId)
    }
}