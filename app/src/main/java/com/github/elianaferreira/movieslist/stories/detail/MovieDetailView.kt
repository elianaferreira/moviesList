package com.github.elianaferreira.movieslist.stories.detail

import com.github.elianaferreira.movieslist.models.MovieDetail
import com.github.elianaferreira.movieslist.utils.ProgressBarView

interface MovieDetailView: ProgressBarView {
    fun showMovieDetail(movie: MovieDetail)
    fun showTrailer(path: String)
    fun showErrorMessage()
    fun showTrailerView(show: Boolean)
}