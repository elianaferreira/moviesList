package com.github.elianaferreira.movieslist.home

import java.io.Serializable

data class Category (
    val categoryValue: String,
    val categoryName: String,
    val drawable: Int
) : Serializable