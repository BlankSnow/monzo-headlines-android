package com.monzo.androidtest.data.repository

import com.monzo.androidtest.data.api.GuardianService
import com.monzo.androidtest.data.mapper.ArticleMapper
import com.monzo.androidtest.domain.model.Article

class ArticlesRepository(
    private val guardianService: GuardianService,
    private val articleMapper: ArticleMapper
) {
    private val favouriteArticleIds = mutableSetOf<String>()

    suspend fun latestFintechArticles(): List<Article> {
        val response = guardianService.searchArticles("fintech,brexit")
        return articleMapper.map(response, favouriteArticleIds)
    }

    suspend fun getArticle(articleUrl: String): Article {
        val response = guardianService.getArticle(articleUrl, "main,body,headline,thumbnail")
        return articleMapper.mapSingle(response.response.content, favouriteArticleIds)
    }

    fun toggleFavourite(articleId: String): Boolean {
        return if (favouriteArticleIds.contains(articleId)) {
            favouriteArticleIds.remove(articleId)
            false
        } else {
            favouriteArticleIds.add(articleId)
            true
        }
    }
}