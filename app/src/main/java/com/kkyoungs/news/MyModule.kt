package com.kkyoungs.news

import com.kkyoungs.news.InjectorUtil.provideNewsListViewModelFactory
import com.kkyoungs.news.data.DataSource
import com.kkyoungs.news.data.NewsRepository
import com.kkyoungs.news.data.RemoteNewsData
import com.kkyoungs.news.viewModel.NewsListViewModel
import com.kkyoungs.news.viewModel.NewsListViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


@OptIn(ExperimentalCoroutinesApi::class)
var modelPart = module {

    factory {
        provideNewsListViewModelFactory()
    }
}
@OptIn(ExperimentalCoroutinesApi::class)
val repositoryModule = module {
    single { InjectorUtil}
}
@OptIn(ExperimentalCoroutinesApi::class)
var viewModelPart = module {
    viewModel{
        NewsListViewModel(get())
    }
}

var myDiModule = listOf(modelPart, repositoryModule, viewModelPart)