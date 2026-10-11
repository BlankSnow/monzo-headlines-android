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
}

data class ArticlesState(
    val refreshing: Boolean = true,
    val articles: List<Article> = emptyList(),
    val errorMessage: String? = null
)