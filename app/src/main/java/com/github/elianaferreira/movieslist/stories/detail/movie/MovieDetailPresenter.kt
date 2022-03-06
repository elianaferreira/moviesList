package com.github.elianaferreira.movieslist.stories.detail.movie

interface MovieDetailPresenter {
    fun getMovieDetail(movieID: String)
    fun getTrailerPath(movieDetail: MovieDetail)
}