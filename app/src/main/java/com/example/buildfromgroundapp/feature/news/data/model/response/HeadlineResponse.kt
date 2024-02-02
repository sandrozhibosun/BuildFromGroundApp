package com.example.buildfromgroundapp.feature.news.data.model.response

import com.google.gson.annotations.SerializedName

data class HeadlineResponse(
    @SerializedName("articles")
    val articles: List<ArticleResponse>,

    @SerializedName("status")
    val status: String,

    @SerializedName("totalResults")
    val totalResults: Int
)

data class ArticleResponse(
    @SerializedName("author")
    val author: String?,

    @SerializedName("content")
    val content: String? = null,

    @SerializedName("description")
    val description: String?,

    @SerializedName("publishedAt")
    val publishedAt: String? = null,

    @SerializedName("source")
    val source: SourceResponse,

    @SerializedName("title")
    val title: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("urlToImage")
    val urlToImage: String?
)

data class SourceResponse(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("name")
    val name: String
)