package kz.petproject.gts_tz.di

import kz.petproject.gts_tz.data.repository.ArticleRepositoryImpl
import kz.petproject.gts_tz.data.repository.UserRepositoryImpl
import kz.petproject.gts_tz.domain.repository.ArticleRepository
import kz.petproject.gts_tz.domain.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(api = get(), userMapper = get()) }
    single<ArticleRepository> { ArticleRepositoryImpl(api = get(), mapper = get()) }
}