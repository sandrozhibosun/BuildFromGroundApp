package com.example.buildfromgroundapp.feature.news.presentation.viewmodel

import com.example.buildfromgroundapp.CoroutineTestRule
import com.example.buildfromgroundapp.feature.news.domain.usecase.GetNewsFeedUsecase
import com.example.buildfromgroundapp.feature.news.domain.usecase.RefreshNewsFeedUsecase
import com.example.buildfromgroundapp.utils.network.Resource
import com.example.buildfromgroundapp.utils.TestNewsFeedFactory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class NewsFeedViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineTestRule= CoroutineTestRule(testDispatcher)

    private val getNewsFeedFromRemoteUsecase: GetNewsFeedUsecase = mockk()
    private val refreshNewsFeedUsecase: RefreshNewsFeedUsecase = mockk()

    private lateinit var viewModel: NewsFeedViewModel


    @Test
    fun `Given Mocked articles When viewModel init Then verify update state flow`() = runTest {
        //Given
        val mockData = TestNewsFeedFactory.generateMockHeadline() // Replace with your mock data
        val flow = flowOf(Resource.Success(mockData.articles))

        //When
        coEvery { getNewsFeedFromRemoteUsecase() } returns flow
        viewModel = NewsFeedViewModel(getNewsFeedFromRemoteUsecase, refreshNewsFeedUsecase)
        testDispatcher.scheduler.advanceUntilIdle()

        //Then
        coVerify { getNewsFeedFromRemoteUsecase() }
        assertFalse(viewModel.showProgress.value)
        assertEquals(mockData.articles, viewModel.newsState.value)
        assertFalse(viewModel.showError.value)
    }

    @Test
    fun `Given Mocked Loading When viewModel init Then verify update loading state`() = runTest {
        //Given
        val flow = flowOf(Resource.Loading)
        coEvery { getNewsFeedFromRemoteUsecase() } returns flow

        //When
        viewModel = NewsFeedViewModel(getNewsFeedFromRemoteUsecase, refreshNewsFeedUsecase)
        testDispatcher.scheduler.advanceUntilIdle()

        //Then
        coVerify { getNewsFeedFromRemoteUsecase() }
        assertTrue(viewModel.showProgress.value)
    }

    @Test
    fun `Given Mocked failure resource When viewModel init Then verify update show error state`() = runTest {
        //Given
        val flow = flowOf(Resource.Failure(false, null))

        coEvery { getNewsFeedFromRemoteUsecase() } returns flow

        //When
        viewModel = NewsFeedViewModel(getNewsFeedFromRemoteUsecase, refreshNewsFeedUsecase)
        testDispatcher.scheduler.advanceUntilIdle()

        //Then
        coVerify { getNewsFeedFromRemoteUsecase() }
        assertTrue(viewModel.showError.value)
    }
}