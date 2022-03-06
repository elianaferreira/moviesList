package com.github.elianaferreira.movieslist.utils

import android.content.Context
import android.widget.Toast
import com.github.elianaferreira.movieslist.BuildConfig
import com.github.elianaferreira.movieslist.stories.detail.movie.Genre
import com.github.elianaferreira.movieslist.stories.detail.movie.SpokenLanguage
import com.github.elianaferreira.movieslist.models.Video
import java.lang.StringBuilder
import java.util.*

class Utils {

    companion object {

        fun showErrorMessage(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }

        fun getLanguagesConcat(languages: List<SpokenLanguage>?): String {
            val result = StringBuilder()
            result.append("Languages: ")
            result.append(if (languages != null && languages.isNotEmpty()) languages.map { it.englishName }.joinToString( ", " ) else "--")
            return result.toString()
        }

        fun getGenresNames(genres: List<Genre>): List<String> {
            return genres.map { it.name }
        }

        fun getPosterURL(filePath: String?): String {
            return BuildConfig.POSTER_URL_BASE + filePath
        }

        fun categoryIsMovie(category: String) : Boolean {
            return category.lowercase(Locale.getDefault()).contains("movie")
        }

        fun getGreeting(): String {
            val calendar = Calendar.getInstance()
            return when (calendar.get(Calendar.HOUR_OF_DAY)) {
                in 0 until 12 -> "Good morning,"
                in 12 until 16 -> "Good afternoon,"
                in 16 until 21 -> "Good evening,"
                in 21..24 -> "Good Night,"
                else -> "Hi,"
            }
        }

        fun getTrailerKey(videos: List<Video>): String {
            //get only trailer
            val trailers = videos.filter { it.type.lowercase() == "trailer" }
            if (trailers.isNotEmpty()) {
                return trailers.first().key
            } else {
                //if there is no trailer, search for teaser
                val teasers = videos.filter { it.type.lowercase() == "teaser" }
                return if (teasers.isNotEmpty()) {
                    teasers.first().key
                } else {
                    //search for clips
                    val clips = videos.filter { it.type.lowercase() == "clip" }
                    if (clips.isNotEmpty()) {
                        clips.first().key
                    } else {
                        //extreme case: return the first element's key
                        videos.first().key
                    }
                }
            }
        }
    }
}