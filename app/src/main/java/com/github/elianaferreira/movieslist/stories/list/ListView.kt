package com.github.elianaferreira.movieslist.stories.list

import android.view.View
import com.github.elianaferreira.movieslist.utils.ProgressBarView

interface ListView: ProgressBarView {
    fun showList(list: MoviesList)
    fun addMoreItems(list: MutableList<Movie>)
    fun onMovieSelected(movie: Movie, view: View)
    fun showErrorMessage()
}