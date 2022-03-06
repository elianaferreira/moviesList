package com.github.elianaferreira.movieslist.stories.home

import java.io.Serializable
import java.util.*

data class Category (
    val categoryValue: String,
    val categoryName: String,
    val drawable: Int
) : Serializable {
    fun categoryIsMovie() : Boolean {
        return categoryValue.lowercase(Locale.getDefault()).contains("movie")
    }
}