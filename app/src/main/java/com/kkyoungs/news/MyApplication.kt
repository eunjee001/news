package com.kkyoungs.news

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 의존성 주입
        startKoin {
            androidContext(this@MyApplication)
            modules(module {
                myDiModule
            })
        }
    }
}