package com.kkyoungs.news.data

import kotlinx.coroutines.flow.Flow

interface DataSource {
    fun getAllNews(): Flow<News>

}
