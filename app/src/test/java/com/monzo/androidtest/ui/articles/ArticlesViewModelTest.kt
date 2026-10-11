package com.monzo.androidtest.articles

import com.monzo.androidtest.data.repository.ArticlesRepository
import com.monzo.androidtest.domain.model.Article
import com.monzo.androidtest.ui.articles.ArticlesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
class ArticlesViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val repository: ArticlesRepository = mock()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadArticles success updates state with articles and resets refreshing`() = runTest {
        val dummyArticles = listOf(
            Article(
                id = "1",
                thumbnail = "http://image.jpg",
                sectionId = "fintech",
                sectionName = "Fintech",
                published = Date(),
                publishedDateFormatted = "11/07/2016",
                title = "Fintech News",
                url = "http://api.com/1"
            )
        )
        whenever(repository.latestFintechArticles()).thenReturn(dummyArticles)

        val viewModel = ArticlesViewModel(repository)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertFalse(state.refreshing)
        assertEquals(dummyArticles, state.articles)
    }

    @Test
    fun `loadArticles error updates state with error message and resets refreshing`() = runTest {
        whenever(repository.latestFintechArticles()).thenThrow(RuntimeException("Network failure"))

        val viewModel = ArticlesViewModel(repository)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertFalse(state.refreshing)
        assertEquals("Error loading articles", state.errorMessage)
    }
}