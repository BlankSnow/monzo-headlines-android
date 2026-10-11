package com.monzo.androidtest.ui.articles

import androidx.lifecycle.viewModelScope
import com.monzo.androidtest.data.repository.ArticlesRepository
import com.monzo.androidtest.domain.model.Article
import com.monzo.androidtest.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class ArticlesViewModel(
    private val repository: ArticlesRepository
) : BaseViewModel<ArticlesState>(ArticlesState()) {

    init {
        loadArticles()
    }

    fun onRefresh() {
        setState { copy(refreshing = true, errorMessage = null) }
        loadArticles()
    }

    private fun loadArticles() {
        viewModelScope.launch {
            try {
                val articles = repository.latestFintechArticles()
                setState {
                    copy(
                        refreshing = false,
                        articles = articles,
                        errorMessage = null
                    )
                }
            } catch (_: Exception) {
                setState {
                    copy(
                        refreshing = false,
                        errorMessage = "Error loading articles"
                    )
                }
            }
        }
    }

    fun onArticleClicked(article: Article) {
        viewModelScope.launch {
            setState { copy(isLoadingDetail = true) }
            try {
                val fullArticle = repository.getArticle(article.url)
                setState {
                    copy(
                        selectedArticle = fullArticle,
                        isLoadingDetail = false
                    )
                }
            } catch (_: Exception) {
                setState {
                    copy(
                        selectedArticle = article,
                        isLoadingDetail = false
                    )
                }
            }
        }
    }

    fun onBackFromDetail() {
        setState { copy(selectedArticle = null) }
    }
}

data class ArticlesState(
    val refreshing: Boolean = true,
    val articles: List<Article> = emptyList(),
    val selectedArticle: Article? = null,
    val isLoadingDetail: Boolean = false,
    val errorMessage: String? = null
)