package com.example.buildfromgroundapp.feature.news.data.datasource.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.buildfromgroundapp.core.data.database.AppDatabase
import com.example.buildfromgroundapp.utils.TestNewsFeedFactory
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArticlesDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var articlesDao: ArticlesDao

    @Before
    fun setup() {
        // Create an in-memory version of the Room database
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        articlesDao = database.getArticlesDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndReadArticles() = runBlocking {
        val articleList = listOf(
            TestNewsFeedFactory.generateMockArticleEntity(1),
            TestNewsFeedFactory.generateMockArticleEntity(2)
        )
        articlesDao.insert(articleList)

        val allArticles = articlesDao.getArticles().first()
        assertEquals(articleList, allArticles)
    }

    @Test
    fun cleanAndCacheArticles() = runBlocking {
        val initialArticles = listOf(
            TestNewsFeedFactory.generateMockArticleEntity(1),
        )
        val newArticles = listOf(
            TestNewsFeedFactory.generateMockArticleEntity(2),
            TestNewsFeedFactory.generateMockArticleEntity(3),
        )

        articlesDao.insert(initialArticles)
        articlesDao.cleanAndCacheArticles(newArticles)

        val allArticles = articlesDao.getArticles().first()
        assertEquals(newArticles, allArticles)
    }

    @Test
    fun deleteAllArticles() = runBlocking {
        val articleList = listOf(
            TestNewsFeedFactory.generateMockArticleEntity(1),
            TestNewsFeedFactory.generateMockArticleEntity(2),
        )
        articlesDao.insert(articleList)
        articlesDao.deleteAll()

        val allArticles = articlesDao.getArticles().first()
        assertTrue(allArticles.isEmpty())
    }
}