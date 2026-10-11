package com.monzo.androidtest.data.mapper

import com.monzo.androidtest.data.api.model.ApiArticle
import com.monzo.androidtest.data.api.model.ApiArticleFields
import com.monzo.androidtest.data.api.model.ApiArticleList
import com.monzo.androidtest.data.api.model.ApiArticleListResponse
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Calendar
import java.util.TimeZone

class ArticleMapperTest {

    private val mapper = ArticleMapper()

    @Test
    fun `map correctly transforms ApiArticleListResponse to Article list and formats date`() {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
            set(2016, Calendar.JULY, 11, 12, 0, 0)
        }
        val testDate = calendar.time

        val apiArticle = ApiArticle(
            id = "environment/2016/jul/11/sea-otters",
            sectionId = "environment",
            sectionName = "Environment",
            webPublicationDate = testDate,
            webTitle = "How sea otters help save the planet",
            webUrl = "http://guardian.com/1",
            apiUrl = "http://api.guardian.com/1",
            fields = ApiArticleFields(
                headline = "How sea otters help save the planet",
                main = null,
                body = null,
                thumbnail = "http://image.jpg"
            )
        )

        val response = ApiArticleListResponse(
            response = ApiArticleList(results = listOf(apiArticle))
        )

        val result = mapper.map(response)

        assertEquals(1, result.size)
        val article = result.first()
        assertEquals("environment/2016/jul/11/sea-otters", article.id)
        assertEquals("How sea otters help save the planet", article.title)
        assertEquals("http://image.jpg", article.thumbnail)
        assertEquals("11/07/2016", article.publishedDateFormatted)
    }
}