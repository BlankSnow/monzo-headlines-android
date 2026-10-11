package com.monzo.androidtest.ui.articles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.monzo.androidtest.HeadlinesApp
import com.monzo.androidtest.theme.GuardianTheme
import com.monzo.androidtest.ui.articles.screen.ArticleDetailScreen
import com.monzo.androidtest.ui.articles.screen.ArticleListScreen

class ArticlesActivity : ComponentActivity() {
    private val viewModel: ArticlesViewModel by lazy {
        HeadlinesApp.from(applicationContext).inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuardianTheme {
                MainContent(viewModel = viewModel)
            }
        }
    }
}

@Composable
private fun MainContent(viewModel: ArticlesViewModel) {
    val state by viewModel.state.collectAsState()
    val selectedArticle = state.selectedArticle

    if (selectedArticle != null) {
        BackHandler { viewModel.onBackFromDetail() }
        ArticleDetailScreen(
            article = selectedArticle,
            onBack = viewModel::onBackFromDetail,
            onToggleFavourite = { viewModel.onToggleFavourite(selectedArticle) }
        )
    } else {
        ArticleListScreen(
            state = state,
            onRefresh = viewModel::onRefresh,
            onArticleClick = viewModel::onArticleClicked
        )
    }
}