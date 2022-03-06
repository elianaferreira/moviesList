package com.github.elianaferreira.movieslist.stories.detail.movie

import com.github.elianaferreira.movieslist.utils.RequestManager
import com.github.elianaferreira.movieslist.utils.Utils

class MovieDetailPresenterImpl(private val movieDetailView: MovieDetailView, private val repository: MovieDetailRepository):
    MovieDetailPresenter {

    override fun getMovieDetail(movieID: String) {
        val successCallback = RequestManager.OnSuccessRequestResult<MovieDetail> {
                response ->
            movieDetailView.showProgressBar(false)
            movieDetailView.showMovieDetail(response as MovieDetail)
        }

        val errorCallback = RequestManager.OnErrorRequestResult { error ->
            error.printStackTrace()
            movieDetailView.showProgressBar(false)
            movieDetailView.showErrorMessage()
            false
        }
        movieDetailView.showProgressBar(true)
        repository.getMovieByID(movieID, successCallback, errorCallback)
    }



    override fun getTrailerPath(movieDetail: MovieDetail) {
        val successCallback = RequestManager.OnSuccessRequestResult<Videos> {
                response ->
            movieDetailView.showProgressBar(false)
            val videosList = response as Videos
            if (videosList.results.isNotEmpty()) {
                movieDetailView.showTrailerView(true)
                movieDetailView.showTrailer(videosList.getTrailerKey())
            }
        }

        val errorCallback = RequestManager.OnErrorRequestResult { error ->
            error.printStackTrace()
            movieDetailView.showProgressBar(false)
            movieDetailView.showTrailerView(false)
            false
        }
        movieDetailView.showProgressBar(true)
        repository.getVideos(movieDetail.id.toString(), successCallback, errorCallback)
    }
}