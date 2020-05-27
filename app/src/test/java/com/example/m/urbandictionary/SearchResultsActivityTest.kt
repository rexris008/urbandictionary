package com.example.m.urbandictionary

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchResultsActivityTest {


    @Test
    fun check_if_result_success_soecial_characters() {
        val myObjectUnderTest: SearchResultsActivity = SearchResultsActivity()
        val result: Boolean = myObjectUnderTest.getJsonData("*")
        assertEquals(result, true)

    }
}