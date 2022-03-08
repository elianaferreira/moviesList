package com.github.elianaferreira.movieslist.stories.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Category  (
    val categoryValue: String,
    val categoryName: String,
    val drawable: Int
) : Parcelable {
    fun categoryIsMovie() : Boolean {
        return categoryValue.lowercase(Locale.getDefault()).contains("movie")
    }
}