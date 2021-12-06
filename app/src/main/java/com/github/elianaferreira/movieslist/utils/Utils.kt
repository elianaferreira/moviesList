package com.github.elianaferreira.movieslist.utils

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.github.elianaferreira.movieslist.models.Genre
import com.github.elianaferreira.movieslist.models.SpokenLanguage
import java.lang.StringBuilder
import java.util.*

class Utils {

    companion object {

        private val POSTER_URL_BASE = "https://image.tmdb.org/t/p/w500/"

        fun showErrorMessage(context: Context, messsage: String) {
            Toast.makeText(context, messsage, Toast.LENGTH_LONG).show()
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
    }
}