package com.kkyoungs.news

import com.kkyoungs.news.data.NewsRepository
import com.kkyoungs.news.data.RemoteNewsData
import com.kkyoungs.news.viewModel.NewsListViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
object InjectorUtil {
    private fun getNewsRepository() :NewsRepository{
        return NewsRepository.getInstance(RemoteNewsData.getInstance())
    }

    fun provideNewsListViewModelFactory(): NewsListViewModelFactory{
        val repository = getNewsRepository()
        return NewsListViewModelFactory(repository)
    }
}