package com.github.elianaferreira.movieslist.stories.detail.movie

import com.github.elianaferreira.movieslist.utils.BasePresenter

interface MovieDetailPresenter: BasePresenter<MovieDetailView> {
    fun getMovieDetail(movieID: String)
    fun getTrailerPath(movieDetail: MovieDetail)
}