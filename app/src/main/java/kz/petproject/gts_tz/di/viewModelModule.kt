package kz.petproject.gts_tz.di

import kz.petproject.gts_tz.ui.presentation.AuthViewModel
import kz.petproject.gts_tz.ui.presentation.MainViewModel
import kz.petproject.gts_tz.ui.presentation.NewsFeedViewModel
import kz.petproject.gts_tz.ui.presentation.admin.AdminViewModel
import kz.petproject.gts_tz.ui.presentation.author.AuthorDashboardViewModel
import kz.petproject.gts_tz.ui.presentation.author.CreatePostViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AuthViewModel(postSiginInUseCase = get(), sessionManager = get()) }
    viewModel { NewsFeedViewModel(sessionManager = get()) }
    viewModel { AdminViewModel(createUserUseCase = get()) }
    viewModel { MainViewModel(sessionManager = get()) }
    viewModel { AuthorDashboardViewModel(getMyArticlesUseCase = get()) }
    viewModel {
        CreatePostViewModel(
            createArticleUseCase = get(),
            submitArticleUseCase = get(),
            savedStateHandle = get(),
            getArticleByIdUseCase = get(),
            editArticleUseCase = get()
        )
    }
}