package com.github.elianaferreira.movieslist.utils

import android.app.Activity
import android.content.Context
import android.widget.*
import androidx.annotation.ColorRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.elianaferreira.movieslist.BuildConfig
import com.github.elianaferreira.movieslist.R
import com.github.elianaferreira.movieslist.stories.detail.movie.Genre
import com.github.elianaferreira.movieslist.stories.detail.movie.SpokenLanguage
import com.github.elianaferreira.movieslist.stories.detail.tvshow.GenresAdapter
import java.util.Calendar


class Utils {

    companion object {

        fun showErrorMessage(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }

        fun getPosterURL(filePath: String?): String {
            return BuildConfig.POSTER_URL_BASE + filePath
        }

        fun getGreeting(): Int {
            val calendar = Calendar.getInstance()
            return when (calendar.get(Calendar.HOUR_OF_DAY)) {
                in 0 until 12 -> R.string.good_morning
                in 12 until 16 -> R.string.good_afternoon
                in 16 until 21 -> R.string.good_evening
                in 21..24 -> R.string.good_night
                else -> R.string.hi
            }
        }

        fun loadDataIntoMovieHeader(
            context: Activity,
            title: String,
            movieHeader: MovieHeader,
            wrapperView: RelativeLayout) {

            //load data in view
            ImageLoader.loadImage(getPosterURL(movieHeader.backdropPath), wrapperView.findViewById(R.id.img_movie))
            (wrapperView.findViewById<TextView>(R.id.tv_movie)).text = title
            (wrapperView.findViewById<TextView>(R.id.tv_overview)).text = movieHeader.overview
            (wrapperView.findViewById<TextView>(R.id.txt_languages)).text = SpokenLanguage.getLanguagesConcat(movieHeader.spokenLanguages)

            val rate = movieHeader.voteAverage
            val rateCount = movieHeader.voteCount
            (wrapperView.findViewById<RatingBar>(R.id.item_rate)).rating = rate.toFloat()
            (wrapperView.findViewById<TextView>(R.id.item_rate_value)).text = context.getString(R.string.rate, rate.toString(), rateCount.toString())

            val rvGenres: RecyclerView = wrapperView.findViewById(R.id.rv_genres)
            rvGenres.layoutManager = GridLayoutManager(context, 3)
            rvGenres.adapter = GenresAdapter(Genre.getGenresNames(movieHeader.genres))
        }

        fun setColorToSwipeRefreh(swipeRefreshLayout: SwipeRefreshLayout) {
            swipeRefreshLayout.setColorScheme(R.color.peach, R.color.dark_peach, R.color.light_peach)
        }
    }
}