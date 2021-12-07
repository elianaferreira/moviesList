package com.github.elianaferreira.movieslist.models

import android.graphics.drawable.Drawable
import java.io.Serializable

data class Category (
    val categoryValue: String,
    val categoryName: String,
    val drawable: Int
) : Serializable