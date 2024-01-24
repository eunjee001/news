package com.kkyoungs.news

import com.kkyoungs.news.data.NewsRepository
import com.kkyoungs.news.viewModel.NewsListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

import org.koin.dsl.module


@OptIn(ExperimentalCoroutinesApi::class)
val repositoryModule = module {
    factory {
        NewsRepository(get())
    }
}
@OptIn(ExperimentalCoroutinesApi::class)
var viewModelPart = module {

}

var myDiModule = listOf( repositoryModule)