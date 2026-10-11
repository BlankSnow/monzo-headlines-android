package com.monzo.androidtest.data.api

import com.monzo.androidtest.data.api.model.ApiArticleDetailResponse
import com.monzo.androidtest.data.api.model.ApiArticleListResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface GuardianService {
    @GET("search?show-fields=headline,thumbnail&page-size=50")
    suspend fun searchArticles(@Query("q") searchTerm: String): ApiArticleListResponse

    @GET
    suspend fun getArticle(
        @Url articleUrl: String,
        @Query("show-fields") fields: String
    ): ApiArticleDetailResponse
}