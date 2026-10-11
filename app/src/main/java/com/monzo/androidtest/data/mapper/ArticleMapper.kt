package com.monzo.androidtest.data.mapper

import androidx.core.text.HtmlCompat
import com.monzo.androidtest.data.api.model.ApiArticle
import com.monzo.androidtest.data.api.model.ApiArticleListResponse
import com.monzo.androidtest.domain.model.Article
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ArticleMapper {
    fun map(apiArticleListResponse: ApiArticleListResponse, favouriteIds: Set<String>): List<Article> {
        return apiArticleListResponse.response.results.map { apiArticle ->
            mapSingle(apiArticle, favouriteIds)
        }
    }

    fun mapSingle(apiArticle: ApiArticle, favouriteIds: Set<String>): Article {
        val thumbnail = apiArticle.fields?.thumbnail ?: ""
        val headline = apiArticle.fields?.headline ?: apiArticle.webTitle
        val cleanBody = apiArticle.fields?.body?.let { stripHtml(it) }

        return Article(
            id = apiArticle.id,
            thumbnail = thumbnail,
            sectionId = apiArticle.sectionId,
            sectionName = apiArticle.sectionName,
            published = apiArticle.webPublicationDate,
            publishedDateFormatted = formatDate(apiArticle.webPublicationDate),
            title = headline,
            url = apiArticle.apiUrl,
            isFavourite = favouriteIds.contains(apiArticle.id),
            bodyText = cleanBody
        )
    }

    private fun stripHtml(htmlText: String): String {
        return HtmlCompat.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_COMPACT)
            .toString()
            .trim()
    }

    private fun formatDate(date: Date): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(date)
    }
}