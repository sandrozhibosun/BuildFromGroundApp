package com.example.buildfromgroundapp.feature.news.data.datasource.remote

import com.example.buildfromgroundapp.feature.news.data.model.response.HeadlineResponse
import com.example.buildfromgroundapp.utils.network.Resource
import com.example.buildfromgroundapp.utils.network.toResource
import javax.inject.Inject

class NewsFeedRemoteDataSource @Inject constructor(private val newsFeedService: NewsFeedService) {

    suspend fun getNewsFeed(
        country: String,
        apiKey: String
    ): Resource<HeadlineResponse> {
        return newsFeedService.getNewsFeed(country, apiKey).toResource()
    }
}

// why need datasource
/**
 * Easier Maintenance and Scalability
 * Changes to the database schema or the API only affect their respective data source classes and not the entire data layer.
 * This containment makes maintaining and scaling the app more manageable. For example, if the API endpoint changes, you only need to update the remote data source class.
 *
 *
 *
 */
