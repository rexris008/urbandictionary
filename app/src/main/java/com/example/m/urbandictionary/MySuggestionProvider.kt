package com.example.m.urbandictionary

import android.content.SearchRecentSuggestionsProvider

class MySuggestionProvider : SearchRecentSuggestionsProvider(), Comparable<String> {
    override fun compareTo(other: String): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object {
        const val AUTHORITY = "com.example.MySuggestionProvider"
        const val MODE: Int = SearchRecentSuggestionsProvider.DATABASE_MODE_QUERIES
    }
}