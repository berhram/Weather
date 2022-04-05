package com.velvet.weather

import android.app.Application
import com.velvet.domain.domainModule
import com.velvet.data.di.dataModule
import com.velvet.weather.di.appModule
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@App)
            modules(appModule, domainModule, dataModule)
        }
    }
}