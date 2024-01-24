package com.kkyoungs.news

import android.app.Application
import com.kkyoungs.news.data.NewsRepository
import com.kkyoungs.news.data.RemoteNewsData
import com.kkyoungs.news.viewModel.NewsListViewModel
import com.kkyoungs.news.viewModel.NewsListViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin

import org.koin.core.module.Module
import org.koin.dsl.module

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 의존성 주입
        startKoin {
            printLogger()
            androidContext(this@MyApplication)
            modules(appModule, viewModelModule,factoryModule)
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
val appModule: Module = module {
    single { RemoteNewsData() } // 예시: RemoteNewsData의 의존성 주입
    single { NewsRepository(get()) }
}

@OptIn(ExperimentalCoroutinesApi::class)
val viewModelModule = module {
    viewModel { NewsListViewModel(get()) }
}
    // 다른 의존성들도 필요에 따라 정의 가능

@OptIn(ExperimentalCoroutinesApi::class)
val factoryModule = module {
    single { NewsListViewModelFactory(get()) }
}