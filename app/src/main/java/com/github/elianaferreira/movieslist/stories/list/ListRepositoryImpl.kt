package com.github.elianaferreira.movieslist.stories.list

import android.content.Context
import com.android.volley.Request
import com.github.elianaferreira.movieslist.utils.RequestManager

class ListRepositoryImpl(context: Context): RequestManager(context), ListRepository {

    override fun getMovies(
        category: String,
        page: Int,
        successCallback: OnSuccessRequestResult<MoviesList>,
        errorCallback: OnErrorRequestResult
    ) {
        val queryParams = hashMapOf<String, String>()
        queryParams["page"] = page.toString()
        makeRequest(getAbsoluteURL(category, queryParams), Request.Method.GET, MoviesList::class.java, successCallback, errorCallback)
    }

    override fun cancelRequests() {
        cancelAllRequest()
    }
}