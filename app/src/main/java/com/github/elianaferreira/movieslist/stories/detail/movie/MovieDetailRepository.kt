package com.github.elianaferreira.movieslist.stories.detail.movie

import com.github.elianaferreira.movieslist.utils.BaseRepository
import com.github.elianaferreira.movieslist.utils.RequestManager

interface MovieDetailRepository: BaseRepository {

    fun getMovieByID(movieID: String, successCallback: RequestManager.OnSuccessRequestResult<MovieDetail>, errorCallback: RequestManager.OnErrorRequestResult)
    fun getVideos(movieID: String, successCallback: RequestManager.OnSuccessRequestResult<Videos>, errorCallback: RequestManager.OnErrorRequestResult)
}