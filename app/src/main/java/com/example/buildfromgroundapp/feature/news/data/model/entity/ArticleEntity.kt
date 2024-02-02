package com.example.buildfromgroundapp.feature.news.data.model.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String, // Consider providing a default value or handling nulls
    @Embedded(prefix = "source_")
    val source: SourceEntity, // This will reference the SourceEntity's ID
    val title: String,
    val url: String,
    val urlToImage: String?
){
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}
