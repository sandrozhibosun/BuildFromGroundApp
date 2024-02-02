package com.example.buildfromgroundapp.feature.news.data.repository

import com.example.buildfromgroundapp.feature.news.data.model.domain.Article
import com.example.buildfromgroundapp.utils.network.Resource
import kotlinx.coroutines.flow.Flow

interface NewsFeedRepository {

     suspend fun refreshNewsFeed(): Flow<Resource<Unit>>

     suspend fun getNewsFeedFromCache(): Flow<Resource<List<Article>>>
}