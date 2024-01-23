package com.kkyoungs.news.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
// 추가함으로써 이 API가 변경될 수도 있고 필요에 따라 업데이트를 준비해야할 수도 있다는 것을 강조할 수 있게 된다.
@ExperimentalCoroutinesApi
class NewsRepository constructor(
    private val remoteNewsData: RemoteNewsData
) : DataSource {
    override fun getAllNews(): Flow<News> {
        return remoteNewsData.getAllNews()
    }

    companion object{
        @Volatile private var instance :NewsRepository ?=null

        fun getInstance(remoteNewsData: RemoteNewsData) = instance?: synchronized(this){
            instance ?: NewsRepository(remoteNewsData).also { instance = it }
        }
    }
}