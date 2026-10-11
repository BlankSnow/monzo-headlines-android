package com.monzo.androidtest.data.repository

import com.monzo.androidtest.data.api.GuardianService
import com.monzo.androidtest.data.mapper.ArticleMapper
import com.monzo.androidtest.domain.model.Article

class ArticlesRepository(
    private val guardianService: GuardianService,
    private val articleMapper: ArticleMapper
) {
    suspend fun latestFintechArticles(): List<Article> {
        val response = guardianService.searchArticles("fintech,brexit")
        return articleMapper.map(response)
    }

    suspend fun getArticle(articleUrl: String): Article {
        val response = guardianService.getArticle(articleUrl, "main,body,headline,thumbnail")
        return articleMapper.mapSingle(response.response.content)
    }
}
