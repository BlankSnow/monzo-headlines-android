package com.monzo.androidtest.data.mapper

import com.monzo.androidtest.data.api.model.ApiArticle
import com.monzo.androidtest.data.api.model.ApiArticleListResponse
import com.monzo.androidtest.domain.model.Article

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
            title = headline,
            url = apiArticle.apiUrl,
        )
    }
}