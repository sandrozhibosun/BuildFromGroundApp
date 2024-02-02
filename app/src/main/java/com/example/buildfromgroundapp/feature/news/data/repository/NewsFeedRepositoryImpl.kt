package com.example.buildfromgroundapp.feature.news.data.repository

import com.example.buildfromgroundapp.feature.news.data.datasource.local.NewsFeedLocalDataSource
import com.example.buildfromgroundapp.feature.news.data.datasource.remote.NewsFeedRemoteDataSource
import com.example.buildfromgroundapp.feature.news.data.model.domain.Article
import com.example.buildfromgroundapp.feature.news.data.model.entity.ArticleEntity
import com.example.buildfromgroundapp.feature.news.data.model.toDomain
import com.example.buildfromgroundapp.feature.news.data.model.toEntity
import com.example.buildfromgroundapp.utils.Constants
import com.example.buildfromgroundapp.utils.IoDispatcher
import com.example.buildfromgroundapp.utils.network.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsFeedRepositoryImpl @Inject constructor(
    private val localDataSource: NewsFeedLocalDataSource,
    private val remoteDataSource: NewsFeedRemoteDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : NewsFeedRepository {

    override suspend fun refreshNewsFeed(): Flow<Resource<Unit>> {
        return flow {
            when (val resource =
                remoteDataSource.getNewsFeed(Constants.NEWS_API_COUNTRY, Constants.NEWS_API_KEY)) {
                is Resource.Success -> {
                    cacheNewsFeed(resource.value.articles.map { it.toEntity() })
                    emit(Resource.Success(Unit))
                }

                else -> {
                    emit(Resource.Failure(false, null, "network call occurred"))
                }
            }
        }.catch {
            emit(Resource.Failure(false, null, "Error occurred"))
        }
            .flowOn(ioDispatcher)
    }


    override suspend fun getNewsFeedFromCache(): Flow<Resource<List<Article>>> {
        return localDataSource.getArticlesLocally().map { articles ->
            if (articles.isEmpty()) {
                Resource.Failure(false, null, "No data found")
            } else {
                Resource.Success(articles.map { it.toDomain() })
            }
        }.catch {
            emit(Resource.Failure(false, null, "Error occurred"))
        }
            .flowOn(ioDispatcher)
    }

    private suspend fun cacheNewsFeed(articleEntities: List<ArticleEntity>) {
        localDataSource.saveArticlesLocally(articleEntities)
    }

}