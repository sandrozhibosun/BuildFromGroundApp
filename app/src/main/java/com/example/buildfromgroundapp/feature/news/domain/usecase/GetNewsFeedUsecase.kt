package com.example.buildfromgroundapp.feature.news.domain.usecase

import com.example.buildfromgroundapp.feature.news.data.repository.NewsFeedRepository
import javax.inject.Inject

class GetNewsFeedUsecase @Inject constructor(
    private val newsFeedRepository: NewsFeedRepository
) {
    suspend operator fun invoke() = newsFeedRepository.getNewsFeedFromCache()
}