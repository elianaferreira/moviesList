package com.github.elianaferreira.movieslist.stories.list

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Keep
data class MoviesList(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int
) : Serializable

@Keep
data class Movie(
    @SerializedName("id")
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("first_air_date")
    val firstAirDate: String
) : Serializable