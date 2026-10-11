package com.monzo.androidtest.ui.articles.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.monzo.androidtest.R
import com.monzo.androidtest.domain.model.Article
import com.monzo.androidtest.ui.articles.ArticlesState

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun ArticleListScreen(
    state: ArticlesState,
    onRefresh: () -> Unit,
    onArticleClick: (Article) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.refreshing,
        onRefresh = onRefresh,
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) },
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF2F2F7))
                .pullRefresh(pullRefreshState)
                .padding(paddingValues),
        ) {
            val favourites = state.articles.filter { it.isFavourite }
            val nonFavourites = state.articles.filter { !it.isFavourite }

            if (state.articles.isEmpty() && !state.refreshing) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = state.errorMessage ?: stringResource(R.string.no_articles_available),
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                    )
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    if (favourites.isNotEmpty()) {
                        item {
                            SectionHeader(title = stringResource(R.string.section_favourites))
                        }
                        items(favourites) { article ->
                            ArticleCardItem(
                                article = article,
                                onClick = { onArticleClick(article) }
                            )
                        }
                    }

                    if (nonFavourites.isNotEmpty()) {
                        item {
                            SectionHeader(title = stringResource(R.string.section_this_week))
                        }
                        items(nonFavourites) { article ->
                            ArticleCardItem(
                                article = article,
                                onClick = { onArticleClick(article) }
                            )
                        }
                    }
                }
            }

            if (state.isLoadingDetail) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
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
private fun SectionHeader(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.subtitle1.copy(
                fontWeight = FontWeight.Normal,
                color = Color(0xFF6E6E73),
                fontSize = 15.sp
            )
        )
    }
}

@Composable
private fun ArticleCardItem(
    article: Article,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .clickable { onClick() },
        backgroundColor = Color.White,
        elevation = 1.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = article.thumbnail,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = article.publishedDateFormatted,
                    style = MaterialTheme.typography.caption.copy(color = Color.Gray)
                )
            }
        }
    }
}