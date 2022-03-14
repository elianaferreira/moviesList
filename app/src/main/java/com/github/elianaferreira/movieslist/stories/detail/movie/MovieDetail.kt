package com.github.elianaferreira.movieslist.stories.detail.movie

import androidx.annotation.Keep
import com.github.elianaferreira.movieslist.utils.MovieHeader
import com.google.gson.annotations.SerializedName
import java.lang.StringBuilder


@Keep
data class MovieDetail(
    @SerializedName("backdrop_path")
   override val backdropPath: String,
    @SerializedName("genres")
    override val genres: List<Genre>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("overview")
    override val overview: String,
    @SerializedName("spoken_languages")
    override val spokenLanguages: List<SpokenLanguage>,
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_average")
    override val voteAverage: Double,
    @SerializedName("vote_count")
    override val voteCount: Int
): MovieHeader

@Keep
data class Genre(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
) {
    companion object {
        fun getGenresNames(genres: List<Genre>): List<String> {
            return genres.map { it.name }
        }
    }
}

@Keep
data class SpokenLanguage(
    @SerializedName("english_name")
    val englishName: String
) {
    companion object {
        fun getLanguagesConcat(languages: List<SpokenLanguage>?): String {
            val result = StringBuilder()
            result.append("Languages: ")
            result.append(if (languages != null && languages.isNotEmpty()) languages.map { it.englishName }.joinToString( ", " ) else "--")
            return result.toString()
        }
    }
}