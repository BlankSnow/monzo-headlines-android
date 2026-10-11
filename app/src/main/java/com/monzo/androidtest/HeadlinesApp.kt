package com.monzo.androidtest

import android.app.Application
import android.content.Context
import com.monzo.androidtest.di.ArticlesModule

class HeadlinesApp : Application() {
    private val articlesModule = ArticlesModule()

    companion object {
        fun from(applicationContext: Context): ArticlesModule {
            return (applicationContext as HeadlinesApp).articlesModule
        }
    }
}
