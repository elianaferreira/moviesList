package com.github.elianaferreira.movieslist.utils

import android.content.Context
import android.widget.Toast
import com.github.elianaferreira.movieslist.BuildConfig
import java.util.Calendar


class Utils {

    companion object {

        fun showErrorMessage(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }

        fun getPosterURL(filePath: String?): String {
            return BuildConfig.POSTER_URL_BASE + filePath
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
    }
}