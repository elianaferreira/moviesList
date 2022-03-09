package com.github.elianaferreira.movieslist.stories.detail.tvshow

import android.util.Log
import com.github.elianaferreira.movieslist.utils.RequestManager

class ShowDetailPresenterImpl(var repository: TVShowRepository): ShowDetailPresenter {

    private lateinit var showDetailView: ShowDetailView

    private val successCallback = RequestManager.OnSuccessRequestResult<TVShowDetail> {
            response ->
        showDetailView.showProgressBar(false)
        showDetailView.showTVShowDetail(response as TVShowDetail)
    }

    private val errorCallback = RequestManager.OnErrorRequestResult { error ->
        Log.e(ShowDetailPresenterImpl::class.simpleName, error.cause?.message, error)
        showDetailView.showProgressBar(false)
        showDetailView.showErrorMessage()
        false
    }

    override fun getShowDetail(showID: String) {
        showDetailView.showProgressBar(true)
        repository.getTVShow(showID, successCallback, errorCallback)
    }

    override fun setView(view: ShowDetailView) {
        showDetailView = view
    }
}