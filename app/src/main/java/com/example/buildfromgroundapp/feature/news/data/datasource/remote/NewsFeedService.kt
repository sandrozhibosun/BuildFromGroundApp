package com.example.buildfromgroundapp.feature.news.data.datasource.remote

import com.example.buildfromgroundapp.feature.news.data.model.response.HeadlineResponse
import com.example.buildfromgroundapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsFeedService {

    @GET(Constants.HEADLINES_NEWS)
    suspend fun getNewsFeed(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): Response<HeadlineResponse>
}