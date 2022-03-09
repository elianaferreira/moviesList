package com.github.elianaferreira.movieslist.stories.detail.tvshow

import androidx.annotation.Keep
import com.github.elianaferreira.movieslist.stories.detail.movie.Genre
import com.github.elianaferreira.movieslist.utils.MovieHeader
import com.github.elianaferreira.movieslist.stories.detail.movie.SpokenLanguage
import com.google.gson.annotations.SerializedName


@Keep
data class TVShowDetail(
    @SerializedName("backdrop_path")
    override val backdropPath: String,
    @SerializedName("genres")
    override val genres: List<Genre>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("overview")
    override val overview: String,
    @SerializedName("seasons")
    val seasons: List<Season>,
    @SerializedName("spoken_languages")
    override val spokenLanguages: List<SpokenLanguage>,
    @SerializedName("vote_average")
    override val voteAverage: Double,
    @SerializedName("vote_count")
    override val voteCount: Int
): MovieHeader


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
