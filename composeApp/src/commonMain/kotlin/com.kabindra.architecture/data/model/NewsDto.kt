package com.kabindra.architecture.data.model

import com.kabindra.architecture.domain.entity.Article
import com.kabindra.architecture.domain.entity.News
import com.kabindra.architecture.domain.entity.Source
import kotlinx.serialization.Serializable

@Serializable
data class NewsDto(
    val articles: List<ArticleDto>?,
    val status: String?,
    val totalResults: Int?
)

@Serializable
data class ArticleDto(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: SourceDto?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)

@Serializable
data class SourceDto(
    val id: String?,
    val name: String?
)

// Mapper function
fun NewsDto.toDomain(): News {
    return News(
        articles = articles?.map { it.toDomain() },
        status = status,
        totalResults = totalResults
    )
}

fun ArticleDto.toDomain(): Article {
    return Article(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source?.toDomain(),
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}

fun SourceDto.toDomain(): Source {
    return Source(
        id = id,
        name = name
    )
}