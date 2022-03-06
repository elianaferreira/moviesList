package com.github.elianaferreira.movieslist.stories.detail.movie

import com.android.volley.Request
import com.github.elianaferreira.movieslist.utils.RequestManager

interface MovieDetailRepository {

    fun getMovieByID(movieID: String, successCallback: RequestManager.OnSuccessRequestResult<MovieDetail>, errorCallback: RequestManager.OnErrorRequestResult)
    fun getVideos(movieID: String, successCallback: RequestManager.OnSuccessRequestResult<Videos>, errorCallback: RequestManager.OnErrorRequestResult)
}