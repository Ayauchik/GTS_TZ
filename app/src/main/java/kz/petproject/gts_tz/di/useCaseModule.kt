package kz.petproject.gts_tz.di

import kz.petproject.gts_tz.domain.use_cases.PostSiginInUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { PostSiginInUseCase(userRepository = get()) }
}