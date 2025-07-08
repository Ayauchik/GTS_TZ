package kz.petproject.gts_tz.di

import kz.petproject.gts_tz.data.network.mapper.ArticleMapper
import kz.petproject.gts_tz.data.network.mapper.UserMapper
import org.koin.dsl.module

val mapperModule = module {
    factory { UserMapper() }
    factory { ArticleMapper() }
}