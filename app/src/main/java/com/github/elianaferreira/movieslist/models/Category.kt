package com.github.elianaferreira.movieslist.models

import java.io.Serializable

data class Category (
    val categoryValue: String,
    val categoryName: String,
    val drawable: Int
) : Serializable