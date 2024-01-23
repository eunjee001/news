package com.kkyoungs.news.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkyoungs.news.data.News
import com.kkyoungs.news.data.NewsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
@ExperimentalCoroutinesApi
class NewsListViewModel(private val repository: NewsRepository): ViewModel() {
    private val _newsList = MutableLiveData<MutableList<News>>()
    val newsList : LiveData<MutableList<News>>
        get() = _newsList

    private val _progress = MutableLiveData<Boolean>()
    val progress : LiveData<Boolean>
        get() = _progress

    init {
        updateNewsData()
    }

    // collect : 이 흐름의 항목을 수신하고 각 항목에 대해 이 함수를 실행
    fun updateNewsData(){
        _progress.value = true
        _newsList.value = mutableListOf()
        viewModelScope.launch {
            val data = repository.getAllNews()
            data.onCompletion { _progress.value = false }
                .collect{_newsList.value = _newsList.value?.apply { add(it) }?: mutableListOf(it)}
        }
    }
}