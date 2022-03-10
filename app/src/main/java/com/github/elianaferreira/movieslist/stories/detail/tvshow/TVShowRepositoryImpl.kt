package com.github.elianaferreira.movieslist.stories.detail.tvshow

import android.content.Context
import com.android.volley.Request
import com.github.elianaferreira.movieslist.utils.RequestManager

class TVShowRepositoryImpl(context: Context): RequestManager(context), TVShowRepository {

    override fun getTVShow(
        showID: String,
        successCallback: OnSuccessRequestResult<TVShowDetail>,
        errorCallback: OnErrorRequestResult
    ) {
        makeRequest(getAbsoluteURL("tv/$showID", null), Request.Method.GET, TVShowDetail::class.java, successCallback, errorCallback)
    }

    override fun cancelRequests() {
        cancelAllRequest()
    }
}