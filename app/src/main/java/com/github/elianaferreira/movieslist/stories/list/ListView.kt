package com.github.elianaferreira.movieslist.stories.list

import com.github.elianaferreira.movieslist.utils.ProgressBarView

interface ListView: ProgressBarView {
    fun showList(list: MoviesList)
    fun addMoreItems(list: MutableList<Movie>)
    fun onMovieSelected(movie: Movie)
    fun showErrorMessage()
}