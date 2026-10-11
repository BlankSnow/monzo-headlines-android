package com.monzo.androidtest.articles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.monzo.androidtest.HeadlinesApp
import com.monzo.androidtest.theme.GuardianTheme

class ArticlesActivity : ComponentActivity() {
    private val viewModel: ArticlesViewModel by lazy {
        HeadlinesApp.from(applicationContext).inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuardianTheme {
                ArticleListScreen(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ArticleListScreen(
    viewModel: ArticlesViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.refreshing,
        onRefresh = viewModel::onRefresh,
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "The Guardian") },
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
                .padding(paddingValues),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
            ) {
                state.articles.forEach { article ->
                    ArticleItem(
                        imageUrl = article.thumbnail,
                        title = article.title,
                    )
                }
            }

            PullRefreshIndicator(
                refreshing = state.refreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
            )
        }
    }
}

@Composable
private fun ArticleItem(
    imageUrl: String,
    title: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = imageUrl,
            modifier = Modifier
                .size(60.dp),
            contentDescription = null,
        )

        Spacer(modifier = Modifier.size(16.dp))

        Text(text = title)
    }
}

@Preview
@Composable
private fun Preview_ArticleItem() {
    GuardianTheme {
        ArticleItem(
            imageUrl = "https://example.com/image.jpg",
            title = "Article Title",
        )
    }
}
