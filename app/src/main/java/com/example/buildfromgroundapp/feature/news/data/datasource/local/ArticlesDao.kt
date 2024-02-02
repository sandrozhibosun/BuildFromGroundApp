package com.example.buildfromgroundapp.feature.news.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.buildfromgroundapp.feature.news.data.model.domain.Article
import com.example.buildfromgroundapp.feature.news.data.model.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticlesDao {
    @Query("SELECT * FROM articles")
    fun getArticles(): Flow<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(article: List<ArticleEntity>)

    @Query("DELETE FROM articles")
    suspend fun deleteAll()

    @Transaction
    suspend fun cleanAndCacheArticles(articles: List<ArticleEntity>) {
        deleteAll()
        insert(articles)
    }
}