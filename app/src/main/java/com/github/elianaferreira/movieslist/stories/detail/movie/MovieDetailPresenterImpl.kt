package com.github.elianaferreira.movieslist.stories.detail.movie

import android.util.Log
import com.github.elianaferreira.movieslist.utils.RequestManager

class MovieDetailPresenterImpl(var repository: MovieDetailRepository):
    MovieDetailPresenter {

    private lateinit var movieDetailView: MovieDetailView

    override fun getMovieDetail(movieID: String) {
        val successCallback = RequestManager.OnSuccessRequestResult<MovieDetail> {
                response ->
            movieDetailView.showProgressBar(false)
            movieDetailView.showMovieDetail(response as MovieDetail)
        }

        val errorCallback = RequestManager.OnErrorRequestResult { error ->
            Log.e(MovieDetailPresenterImpl::class.simpleName, error.cause?.message, error)
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
            Log.e(MovieDetailPresenterImpl::class.simpleName, error.cause?.message, error)
            movieDetailView.showProgressBar(false)
            movieDetailView.showTrailerView(false)
            false
        }
        movieDetailView.showProgressBar(true)
        repository.getVideos(movieDetail.id.toString(), successCallback, errorCallback)
    }

    override fun setView(view: MovieDetailView) {
        movieDetailView = view
    }

    override fun cancelRequests() {
        repository.cancelRequests()
    }
}