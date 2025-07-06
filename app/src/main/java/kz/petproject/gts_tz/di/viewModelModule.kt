package kz.petproject.gts_tz.di

import kz.petproject.gts_tz.ui.presentation.AuthViewModel
import kz.petproject.gts_tz.ui.presentation.NewsFeedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AuthViewModel(postSiginInUseCase = get(), tokenManager = get()) }
    viewModel { NewsFeedViewModel(tokenManager = get()) }
}