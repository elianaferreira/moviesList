package com.github.elianaferreira.movieslist.stories.detail.tvshow

import androidx.annotation.Keep
import com.github.elianaferreira.movieslist.stories.detail.movie.Genre
import com.github.elianaferreira.movieslist.stories.detail.movie.SpokenLanguage
import com.google.gson.annotations.SerializedName


@Keep
data class TVShowDetail(
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("seasons")
    val seasons: List<Season>,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
)


@Keep
data class Season(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String
)
