package com.example.buildfromgroundapp.feature.news.data.repository

import com.example.buildfromgroundapp.CoroutineTestRule
import com.example.buildfromgroundapp.feature.news.data.datasource.local.NewsFeedLocalDataSource
import com.example.buildfromgroundapp.feature.news.data.datasource.remote.NewsFeedRemoteDataSource
import com.example.buildfromgroundapp.feature.news.data.model.entity.ArticleEntity
import com.example.buildfromgroundapp.utils.TestNewsFeedFactory
import com.example.buildfromgroundapp.utils.network.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString

@RunWith(JUnit4::class)
class NewsFeedRepositoryImplTest{

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineTestRule= CoroutineTestRule(testDispatcher)

    private lateinit var repository: NewsFeedRepositoryImpl

    private val localDataSource: NewsFeedLocalDataSource = mockk()
    private val remoteDataSource: NewsFeedRemoteDataSource = mockk()

    @Before
    fun setup() {
        repository = NewsFeedRepositoryImpl(localDataSource, remoteDataSource, testDispatcher)
    }

    @Test
    fun `refreshNewsFeed emits Success when remote source succeeds`() = runTest {
        val mockArticles = TestNewsFeedFactory.generateMockHeadlineResponse()
        coEvery { remoteDataSource.getNewsFeed(any(), any())}.returns(Resource.Success(mockArticles))
        coEvery { localDataSource.saveArticlesLocally(any()) } returns Unit

        val result = repository.refreshNewsFeed().first()

        coVerify { localDataSource.saveArticlesLocally(any()) }
        assertTrue(result is Resource.Success)
    }

    @Test
    fun `refreshNewsFeed emits failure when remote source failure`() = runTest {
        coEvery { remoteDataSource.getNewsFeed(any(), any())}.returns(Resource.Failure(false, null, "network call occurred"))
        coEvery { localDataSource.saveArticlesLocally(any()) } returns Unit

        val result = repository.refreshNewsFeed().first()

        coVerify(exactly = 0) { localDataSource.saveArticlesLocally(any()) }
        assertTrue(result is Resource.Failure)
    }

    @Test
    fun `getNewsFeedFromCache emits Success with articles when cache is not empty`() = runTest {
        val mockArticles = listOf(TestNewsFeedFactory.generateMockArticleEntity(1))
        coEvery { localDataSource.getArticlesLocally() } returns flowOf(mockArticles)

        val result = repository.getNewsFeedFromCache().first()
        testDispatcher.scheduler.advanceUntilIdle()

        assert(result is Resource.Success && result.value.isNotEmpty())
    }


    @Test
    fun `getNewsFeedFromCache emits Failure when cache is empty`() = runTest {
        coEvery { localDataSource.getArticlesLocally() } returns flowOf(emptyList())

        val result = repository.getNewsFeedFromCache().first()
        testDispatcher.scheduler.advanceUntilIdle()

        assert(result is Resource.Failure)
    }
}
