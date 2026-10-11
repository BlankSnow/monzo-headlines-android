package com.monzo.androidtest.data.api.model
import java.util.Date

data class ApiArticleListResponse(val response: ApiArticleList)

data class ApiArticleList(val results: List<ApiArticle>)

data class ApiArticleDetailResponse(val response: ApiArticleDetailContent)

data class ApiArticleDetailContent(val content: ApiArticle)

data class ApiArticle(
    val id: String,
    val sectionId: String,
    val sectionName: String,
    val webPublicationDate: Date,
    val webTitle: String,
    val webUrl: String,
    val apiUrl: String,
    val fields: ApiArticleFields?
)

data class ApiArticleFields(
    val headline: String?,
    val main: String?,
    val body: String?,
    val thumbnail: String?
)