package com.github.elianaferreira.movieslist.utils

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.github.elianaferreira.movieslist.models.Genre
import com.github.elianaferreira.movieslist.models.SpokenLanguage
import com.github.elianaferreira.movieslist.models.Video
import java.lang.StringBuilder
import java.util.*

class Utils {

    companion object {

        private val POSTER_URL_BASE = "https://image.tmdb.org/t/p/w500/"

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
            return POSTER_URL_BASE + filePath
        }

        fun categoryIsMovie(category: String) : Boolean {
            return category.lowercase(Locale.getDefault()).contains("movie")
        }

        fun getGreeting(): String {
            val calendar = Calendar.getInstance()
            when (calendar.get(Calendar.HOUR_OF_DAY)) {
                in 0 until 12 -> return "Good morning,"
                in 12 until 16 -> return "Good afternoon,"
                in 16 until 21 -> return "Good evening,"
                in 21..24 -> return "Good Night,"
                else -> return "Hi,"
            }
        }

        fun getTrailerKey(videos: List<Video>): String {
            //get only trailer
            val trailers = videos.filter { it.type.lowercase() == "trailer" }
            if (trailers != null && trailers.isNotEmpty()) {
                return trailers.first().key
            } else {
                //if there is no trailer, search for teaser
                val teasers = videos.filter { it.type.lowercase() == "teaser" }
                if (teasers != null && teasers.isNotEmpty()) {
                    return teasers.first().key
                } else {
                    //ssearch for clips
                    val clips = videos.filter { it.type.lowercase() == "clip" }
                    if (clips != null && clips.isNotEmpty()) {
                        return clips.first().key
                    } else {
                        //extreme case: return the first element's key
                        return videos.first().key
                    }
                }
            }
        }
    }
}