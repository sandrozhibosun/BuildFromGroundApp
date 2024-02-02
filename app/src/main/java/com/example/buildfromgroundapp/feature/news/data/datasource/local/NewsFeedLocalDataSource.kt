package com.example.buildfromgroundapp.feature.news.data.datasource.local

import com.example.buildfromgroundapp.core.data.database.AppDatabase
import com.example.buildfromgroundapp.feature.news.data.model.entity.ArticleEntity
import com.example.buildfromgroundapp.utils.network.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsFeedLocalDataSource  @Inject constructor(
    private val database: AppDatabase
) {
    fun getArticlesLocally(): Flow<List<ArticleEntity>> {
        return database.getArticlesDao().getArticles()
    }


    suspend fun saveArticlesLocally(articleEntities: List<ArticleEntity>) = database.getArticlesDao().insert(articleEntities)
}