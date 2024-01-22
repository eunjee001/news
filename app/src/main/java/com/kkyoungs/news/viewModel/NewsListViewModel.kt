package com.kkyoungs.news.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewsListViewModel(private val repository: NewsRepository): ViewModel() {
    private val _newsList = MutableLiveData<MutableList<News>>()
}