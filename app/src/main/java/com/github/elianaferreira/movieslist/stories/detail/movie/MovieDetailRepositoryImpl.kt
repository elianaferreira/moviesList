package com.github.elianaferreira.movieslist.stories.detail.movie

import android.content.Context
import com.android.volley.Request
import com.github.elianaferreira.movieslist.utils.RequestManager

class MovieDetailRepositoryImpl(context: Context): RequestManager(context), MovieDetailRepository {

    override fun getMovieByID(
        movieID: String,
        successCallback: OnSuccessRequestResult<MovieDetail>,
        errorCallback: OnErrorRequestResult
    ) {
        makeRequest(getAbsoluteURL("movie/$movieID", null), Request.Method.GET,  MovieDetail::class.java, successCallback, errorCallback)
    }

    override fun getVideos(
        movieID: String,
        successCallback: OnSuccessRequestResult<Videos>,
        errorCallback: OnErrorRequestResult
    ) {
        makeRequest(getAbsoluteURL("movie/$movieID/videos", null), Request.Method.GET, Videos::class.java, successCallback, errorCallback)
    }

    override fun cancelRequests() {
        cancelAllRequest()
    }
}