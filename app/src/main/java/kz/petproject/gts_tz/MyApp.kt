package kz.petproject.gts_tz

import android.app.Application
import kz.petproject.gts_tz.di.mapperModule
import kz.petproject.gts_tz.di.networkModule
import kz.petproject.gts_tz.di.repositoryModule
import kz.petproject.gts_tz.di.storageModule
import kz.petproject.gts_tz.di.useCaseModule
import kz.petproject.gts_tz.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf

class MyApp : Application() {

    private val modulesToUse = listOf(
        viewModelModule,
        mapperModule,
        repositoryModule,
        networkModule,
        useCaseModule,
        storageModule,
    )

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApp)
            parametersOf(Constants.BASE_URL)
            modules(modulesToUse)
        }
    }
}
