package com.github.elianaferreira.movieslist.stories.detail.movie

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class Videos(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results: List<Video>
) {
    fun getTrailerKey(): String {
        //get only trailer
        val trailers = results.filter { it.type.lowercase() == "trailer" }
        if (trailers.isNotEmpty()) {
            return trailers.first().key
        } else {
            //if there is no trailer, search for teaser
            val teasers = results.filter { it.type.lowercase() == "teaser" }
            return if (teasers.isNotEmpty()) {
                teasers.first().key
            } else {
                //search for clips
                val clips = results.filter { it.type.lowercase() == "clip" }
                if (clips.isNotEmpty()) {
                    clips.first().key
                } else {
                    //extreme case: return the first element's key
                    results.first().key
                }
            }
        }
    }
}

@Keep
data class Video(
    @SerializedName("id")
    val id: String,
    @SerializedName("key")
    val key: String,
    @SerializedName("type")
    val type: String
)