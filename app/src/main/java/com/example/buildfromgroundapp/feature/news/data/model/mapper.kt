package com.example.buildfromgroundapp.feature.news.data.model

import com.example.buildfromgroundapp.feature.news.data.model.domain.Article
import com.example.buildfromgroundapp.feature.news.data.model.domain.Headline
import com.example.buildfromgroundapp.feature.news.data.model.domain.Source
import com.example.buildfromgroundapp.feature.news.data.model.entity.ArticleEntity
import com.example.buildfromgroundapp.feature.news.data.model.entity.SourceEntity
import com.example.buildfromgroundapp.feature.news.data.model.response.ArticleResponse
import com.example.buildfromgroundapp.feature.news.data.model.response.HeadlineResponse
import com.example.buildfromgroundapp.feature.news.data.model.response.SourceResponse
import java.util.UUID


fun ArticleResponse.toEntity(): ArticleEntity {
    return ArticleEntity(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt ?: "",
        source = source.toEntity(),
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}

fun SourceResponse.toEntity(): SourceEntity {
    return SourceEntity(
        id = id
            ?: "", // Handle null ID by providing a default value or adjusting the domain model to accept nullable ID
        name = name
    )
}

fun ArticleEntity.toDomain(): Article {
    return Article(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt ?: "",
        source = source.toDomain(),
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}

fun SourceEntity.toDomain(): Source {
    return Source(
        id = id
            ?: "", // Handle null ID by providing a default value or adjusting the domain model to accept nullable ID
        name = name
    )
}