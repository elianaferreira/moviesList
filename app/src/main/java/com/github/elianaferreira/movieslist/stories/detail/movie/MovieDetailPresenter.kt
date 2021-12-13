package com.github.elianaferreira.movieslist.stories.detail.movie

import com.github.elianaferreira.movieslist.models.MovieDetail

interface MovieDetailPresenter {
    fun getMovieDetail(movieID: String)
    fun getTrailerPath(movieDetail: MovieDetail)
}