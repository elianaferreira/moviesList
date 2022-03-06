package com.github.elianaferreira.movieslist.stories.list

import com.github.elianaferreira.movieslist.utils.RequestManager.OnSuccessRequestResult
import com.github.elianaferreira.movieslist.utils.RequestManager.OnErrorRequestResult


interface ListRepository {

    //used for Movies and TV Shows
    fun getMovies(category: String, page: Int, successCallback: OnSuccessRequestResult<MoviesList>, errorCallback: OnErrorRequestResult)
}