package com.monzo.androidtest.di

import android.content.Context
import android.content.res.Resources
import com.monzo.androidtest.R
import com.monzo.androidtest.data.api.GuardianService
import com.monzo.androidtest.data.mapper.ArticleMapper
import com.monzo.androidtest.data.repository.ArticlesRepository
import com.monzo.androidtest.ui.articles.ArticlesViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Date

class ArticlesModule {
    fun inject(context: Context): ArticlesViewModel {
        return ArticlesViewModel(ArticlesRepository(createGuardianService(context), ArticleMapper()))
    }

    private fun createGuardianService(context: Context): GuardianService {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(createOkHttpClient(context.resources))
            .build()
            .create(GuardianService::class.java)
    }

    private fun createOkHttpClient(resources: Resources): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor(getAuthInterceptor(resources))
        clientBuilder.addInterceptor(loggingInterceptor)
        return clientBuilder.build()
    }

    private fun getAuthInterceptor(resources: Resources): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val hb = original.headers.newBuilder()
            hb.add(HEADER_API_KEY, resources.getString(R.string.guardian_api_key))
            chain.proceed(original.newBuilder().headers(hb.build()).build())
        }
    }

    companion object {
        private const val BASE_URL = "https://content.guardianapis.com/"
        private const val HEADER_API_KEY = "api-key"
    }
}