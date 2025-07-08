package kz.petproject.gts_tz.di

import kz.petproject.gts_tz.domain.use_cases.ApproveArticleUseCase
import kz.petproject.gts_tz.domain.use_cases.CreateArticleUseCase
import kz.petproject.gts_tz.domain.use_cases.CreateUserUseCase
import kz.petproject.gts_tz.domain.use_cases.DeleteArticleUseCase
import kz.petproject.gts_tz.domain.use_cases.EditArticleUseCase
import kz.petproject.gts_tz.domain.use_cases.GetAllUsersUseCase
import kz.petproject.gts_tz.domain.use_cases.GetArticleByIdUseCase
import kz.petproject.gts_tz.domain.use_cases.GetArticlesForModerationUseCase
import kz.petproject.gts_tz.domain.use_cases.GetMyArticlesUseCase
import kz.petproject.gts_tz.domain.use_cases.GetPublishedArticledUseCase
import kz.petproject.gts_tz.domain.use_cases.PostSiginInUseCase
import kz.petproject.gts_tz.domain.use_cases.RejectArticleUseCase
import kz.petproject.gts_tz.domain.use_cases.SubmitArticleUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { PostSiginInUseCase(userRepository = get()) }
    factory { CreateUserUseCase(userRepository = get()) }
    factory { GetMyArticlesUseCase(repository = get()) }
    factory { CreateArticleUseCase(repository = get()) }
    factory { SubmitArticleUseCase(repository = get()) }
    factory { ApproveArticleUseCase(repository = get()) }
    factory { GetArticleByIdUseCase(repository = get()) }
    factory { RejectArticleUseCase(repository = get()) }
    factory { GetArticlesForModerationUseCase(repository = get()) }
    factory { GetPublishedArticledUseCase(repository = get()) }
    factory { EditArticleUseCase(articleRepository = get()) }
    factory { DeleteArticleUseCase(articleRepository = get()) }
    factory { GetAllUsersUseCase(userRepository = get()) }
}