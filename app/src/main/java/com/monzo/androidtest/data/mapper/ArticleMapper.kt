package com.monzo.androidtest.data.mapper

import com.monzo.androidtest.data.api.model.ApiArticle
import com.monzo.androidtest.data.api.model.ApiArticleListResponse
import com.monzo.androidtest.domain.model.Article
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ArticleMapper {
    fun map(apiArticleListResponse: ApiArticleListResponse): List<Article> {
        return apiArticleListResponse.response.results.map { apiArticle ->
            mapSingle(apiArticle)
        }
    }

    fun mapSingle(apiArticle: ApiArticle): Article {
        val thumbnail = apiArticle.fields?.thumbnail ?: ""
        val headline = apiArticle.fields?.headline ?: apiArticle.webTitle

        return Article(
            id = apiArticle.id,
            thumbnail = thumbnail,
            sectionId = apiArticle.sectionId,
            sectionName = apiArticle.sectionName,
            published = apiArticle.webPublicationDate,
            publishedDateFormatted = formatDate(apiArticle.webPublicationDate),
            title = headline,
            url = apiArticle.apiUrl
        )
    }

    private fun formatDate(date: Date): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(date)
    }
}