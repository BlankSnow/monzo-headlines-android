package com.monzo.androidtest.ui.articles.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.monzo.androidtest.R
import com.monzo.androidtest.domain.model.Article

@Composable
internal fun ArticleDetailScreen(
    article: Article,
    onBack: () -> Unit,
    onToggleFavourite: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            if (article.thumbnail.isNotEmpty()) {
                AsyncImage(
                    model = article.thumbnail,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                )
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = article.title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = article.publishedDateFormatted,
                    style = MaterialTheme.typography.caption.copy(color = Color.Gray)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = article.bodyText ?: article.title,
                    fontSize = 15.sp,
                    lineHeight = 22.sp
                )
            }
        }

        TopAppBar(
            title = { },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.cd_back_button),
                        tint = Color.White
                    )
                }
            },
            actions = {
                IconButton(onClick = onToggleFavourite) {
                    Icon(
                        imageVector = if (article.isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = stringResource(R.string.cd_favourite_button),
                        tint = Color.White
                    )
                }
            },
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}