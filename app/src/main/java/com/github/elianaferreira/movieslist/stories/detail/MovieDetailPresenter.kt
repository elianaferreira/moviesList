package com.github.elianaferreira.movieslist.stories.detail

import com.github.elianaferreira.movieslist.models.MovieDetail

interface MovieDetailPresenter {
    fun getMovieDetail(movieID: String)
    fun getTrailerPath(movieDetail: MovieDetail)
}