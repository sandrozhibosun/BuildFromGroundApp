package com.example.buildfromgroundapp.utils

import com.example.buildfromgroundapp.feature.news.data.model.domain.Headline
import com.example.buildfromgroundapp.feature.news.data.model.domain.Article
import com.example.buildfromgroundapp.feature.news.data.model.domain.Source
import com.example.buildfromgroundapp.feature.news.data.model.entity.ArticleEntity
import com.example.buildfromgroundapp.feature.news.data.model.entity.SourceEntity
import com.example.buildfromgroundapp.feature.news.data.model.response.ArticleResponse
import com.example.buildfromgroundapp.feature.news.data.model.response.HeadlineResponse
import com.example.buildfromgroundapp.feature.news.data.model.response.SourceResponse

object TestNewsFeedFactory {
    fun generateMockHeadlineResponse(): HeadlineResponse {
        val mockArticles = listOf(
            ArticleResponse(
                author = "John Doe",
                content = "Sample content of the article goes here. This is just a mock representation.",
                description = "This is a brief description of the article content.",
                publishedAt = "2022-01-01T12:00:00Z",
                source = SourceResponse(id = "1", name = "Mock Source"),
                title = "Sample Article Title",
                url = "https://www.example.com/article1",
                urlToImage = "https://www.example.com/images/article1.jpg"
            ),
            ArticleResponse(
                author = "Jane Smith",
                content = "Another sample content of the article for testing purposes.",
                description = "A short description of the second article's content.",
                publishedAt = "2022-01-02T12:00:00Z",
                source = SourceResponse(id = "2", name = "Another Mock Source"),
                title = "Another Sample Article Title",
                url = "https://www.example.com/article2",
                urlToImage = "https://www.example.com/images/article2.jpg"
            )
            // Add more articles as needed
        )

        return HeadlineResponse(
            articles = mockArticles,
            status = "ok",
            totalResults = mockArticles.size
        )
    }

    fun generateMockHeadline(): Headline {
        val mockArticles = listOf(
            Article(
                author = "John Doe",
                content = "Here is some sample content for the first news article. This is just mock data for testing.",
                description = "A brief description of the first news article.",
                publishedAt = "2022-01-01T12:00:00Z",
                source = Source(id = "1", name = "Mock News Source"),
                title = "First Mock News Title",
                url = "https://www.example.com/news1",
                urlToImage = "https://www.example.com/images/news1.jpg"
            ),
            Article(
                author = "Jane Smith",
                content = "This is the sample content for the second news article, created for mock data purposes.",
                description = "A short description for the second news article.",
                publishedAt = "2022-01-02T12:00:00Z",
                source = Source(id = "2", name = "Another Mock News Source"),
                title = "Second Mock News Title",
                url = "https://www.example.com/news2",
                urlToImage = "https://www.example.com/images/news2.jpg"
            )
            // Add more NewsArticle instances as needed
        )

        return Headline(
            articles = mockArticles,
            status = "ok",
            totalResults = mockArticles.size
        )
    }

    fun generateMockArticleEntity(id: Long): ArticleEntity {
        return ArticleEntity(
            author = "John Doe",
            content = "Here is some sample content for the first news article. This is just mock data for testing.",
            description = "A brief description of the first news article.",
            publishedAt = "2022-01-01T12:00:00Z",
            source = generateMockSourceEntity(),
            title = "First Mock News Title",
            url = "https://www.example.com/news1",
            urlToImage = "https://www.example.com/images/news1.jpg"
        ).apply { this.id = id }
    }

    private fun generateMockSourceEntity(): SourceEntity {
        return SourceEntity(
            id = "1",
            name = "Mock News Source"
        )
    }


    fun generateMockArticleDomain(): Article {
        return Article(
            author = "John Doe",
            content = "Here is some sample content for the first news article. This is just mock data for testing.",
            description = "A brief description of the first news article.",
            publishedAt = "2022-01-01T12:00:00Z",
            source = generateMockSourceDomain(),
            title = "First Mock News Title",
            url = "https://www.example.com/news1",
            urlToImage = "https://www.example.com/images/news1.jpg"
        )
    }

    private fun generateMockSourceDomain(): Source {
        return Source(
            id = "1",
            name = "Mock News Source"
        )
    }
}