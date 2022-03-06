package com.github.elianaferreira.movieslist.stories.detail.movie

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class MovieDetail(
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
)

@Keep
data class Genre(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)

@Keep
data class SpokenLanguage(
    @SerializedName("english_name")
    val englishName: String
)