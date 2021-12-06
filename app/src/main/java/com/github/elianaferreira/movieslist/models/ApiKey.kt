package com.github.elianaferreira.movieslist.models
import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName

@Keep
data class ApiKey(
    @SerializedName("apiKey")
    val apiKey: String
)