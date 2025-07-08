package kz.petproject.gts_tz.domain.use_cases

import kz.petproject.gts_tz.data.Article
import kz.petproject.gts_tz.domain.repository.ArticleRepository

class GetMyArticlesUseCase(private val repository: ArticleRepository) {
    suspend operator fun invoke(): Result<List<Article>> = repository.getMyArticles()
}