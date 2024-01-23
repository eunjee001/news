package com.kkyoungs.news.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kkyoungs.news.data.NewsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Suppress("UNCHECKED_CAST")
class NewsListViewModelFactory (private val repository : NewsRepository) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsListViewModel(repository) as T
    }

}